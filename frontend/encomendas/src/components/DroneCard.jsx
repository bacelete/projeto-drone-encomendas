import DroneIcon from '../assets/icons/drone.png';
import BateriaDrone from './BateriaDrone';
import StatusDrone from './StatusDrone';

export default function DroneCard({ drone }) {
    return (
        <div className='bg-white p-4 rounded-md flex gap-3 text-gray-800 shadow-lg cursor-pointer'>
            <img
                src={DroneIcon}
                alt="Drone Icon"
                className="w-12 h-12 object-contain"
            />
            <div id="card-drone" className='mx-3'>
                <p id='titulo' className='font-bold text-2xl'>Drone {drone.id}</p>
                <div id="infos" className='p-1 mx-2'>
                    <BateriaDrone battery={drone.bateria} />
                    <StatusDrone status={drone.statusDrone} />
                </div>
            </div>
        </div>
    )
}