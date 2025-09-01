import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
export default function DroneDashboard() {
    const [drones, setDrones] = useState([]);
    useEffect(() => {
        async function fetchDrones() {
            try {
                const response = await fetch(`http://localhost:8080/drones/status`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })
                if (response.ok) {
                    const drones = response.json();
                    setDrones(drones);
                }
            }
            catch (e) {
                console.log(e);
            }
        }
        fetchDrones();
    }, []);

    return (
        <div>
            {drones.map(drone => <DroneCard bateria={drone.bateria} status={drone.statusDrone} />)}
        </div>
    )
}