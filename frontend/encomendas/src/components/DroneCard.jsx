import DroneIcon from '../assets/icons/drone.png';

export default function DroneCard({drone}) {
    return (
        <div className='bg-white p-2 rounded-md flex gap-3 text-gray-800 w-lg shadow-lg cursor-pointer'>
            <img 
                src={DroneIcon} 
                alt="Drone Icon" 
                className="w-12 h-12 object-contain" 
            />
            <div id="infos" className='p-2'>
                <p id='titulo' className='font-bold text-xl'>Drone {drone.id}</p>
                <p>Bateria: {drone.bateria}</p>
                <p>Status: {drone.statusDrone}</p>
            </div>
        </div>
    )
}