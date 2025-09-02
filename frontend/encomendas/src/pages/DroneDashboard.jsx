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
                if (!response.ok) {
                    throw new Error("Erro na requisição!"); 
                }
                const data = await response.json();
                console.log(data);
                setDrones(data);
            }
            catch (e) {
                console.log(e);
            }
        }
        fetchDrones();
    }, []);

    return (
        <div className="grid grid-cols-3 gap-4 bg-gray-200 p-10 rounded-lg shadow-lg w-full">
            {drones.map((drone) => (
                <DroneCard key={drone.id} drone={drone} />
            ))}
        </div>
    )
}