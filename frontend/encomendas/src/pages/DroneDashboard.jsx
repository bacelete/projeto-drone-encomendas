import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
import Title from "../components/Title";
import ReloadButton from '../components/ReloadButton';

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

        setTimeout(function() {
            window.location.reload();
        }, 8000);

    }, []);

    return (
        <>
            <Title text={"Dashboard"} />
            <ReloadButton />
            <div className="grid grid-cols-3 gap-6 bg-gray-200 p-5 rounded-lg shadow-lg w-full">
                {drones.map((drone) => (
                    <DroneCard key={drone.id} drone={drone}/>
                ))}
            </div>
        </>
    )
}