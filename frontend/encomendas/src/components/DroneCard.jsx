import DroneIcon from '../assets/icons/drone.png';
import BateriaDrone from './BateriaDrone';
import StatusDrone from './StatusDrone';

export default function DroneCard({ drone, onClick }) {
   
    return (
        <>
            <div onClick={onClick} 
            commandfor="dialog"
            className='bg-white p-4 rounded-md flex gap-3 text-gray-800 opacity-90 shadow-lg cursor-pointer
         transition-transform duration-300 ease-in-out hover:scale-105'>
                <img
                    src={DroneIcon}
                    alt="Drone Icon"
                    className="w-18 h-18 object-contain my-auto mx-2 hover:translate-y-2 duration-300 ease-out hover:scale-110"
                />
                <div id="card-drone" className='mx-3'>
                    <p id='titulo' className='font-oxygen font-bold text-2xl'>Drone {drone.id}</p>
                    <div id="infos" className='py-3 mx-2'>
                        <BateriaDrone battery={drone.bateria} />
                        <StatusDrone status={drone.statusDrone} />
                    </div>
                </div>
            </div>
        </>
    );
}