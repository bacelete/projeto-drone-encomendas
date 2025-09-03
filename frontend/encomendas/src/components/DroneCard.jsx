import DroneIcon from '../assets/icons/drone.png';
import BateriaDrone from './BateriaDrone';
import DroneInfos from './DroneInfos';
import StatusDrone from './StatusDrone';
import { useState } from 'react';

export default function DroneCard({ drone }) {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleOpenModal = () => {
        setIsModalOpen(true); // Abre o modal
    };

    const handleCloseModal = () => {
        setIsModalOpen(false); // Fecha o modal
    };

    return (
        <>
            <div onClick={handleOpenModal} 
            commandfor="dialog"
            className='bg-white p-4 rounded-md flex gap-3 text-gray-800 opacity-80 shadow-lg cursor-pointer
         transition-transform duration-300 ease-in-out hover:scale-105'>
                <img
                    src={DroneIcon}
                    alt="Drone Icon"
                    className="w-18 h-18 object-contain my-auto mx-2"
                />
                <div id="card-drone" className='mx-3'>
                    <p id='titulo' className='font-oxygen font-bold text-2xl'>Drone {drone.id}</p>
                    <div id="infos" className='p-1 mx-2'>
                        <BateriaDrone battery={drone.bateria} />
                        <StatusDrone status={drone.statusDrone} />
                    </div>
                </div>
            </div>
            {
                isModalOpen && (
                    <DroneInfos
                        drone={drone}
                        onClose={handleCloseModal}
                    />
                )
            }
        </>
    );
}