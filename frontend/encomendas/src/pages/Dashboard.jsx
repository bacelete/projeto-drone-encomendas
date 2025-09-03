import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
import Title from "../components/Title";
import ReloadButton from '../components/ReloadButton';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import BateriaDrone from "../components/BateriaDrone";

export default function Dashboard() {
    const [drones, setDrones] = useState([]);
    const [open, setOpen] = useState(false);
    const [infoDrone, setInfoDrone] = useState({})

    async function fetchDroneById(id) {
        try {
            const response = await fetch(`http://localhost:8080/drones/${id}`, {
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
            setInfoDrone(data)
        }
        catch (e) {
            console.log(e);
        }
    }

    const handleOpen = (id) => {
        setOpen(true)
        fetchDroneById(id);
    };

    const handleClose = () => setOpen(false);

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

        setTimeout(function () {
            window.location.reload();
        }, 8000);

    }, []);

    return (
        <>

            <Title text={"Drones | Dashboard"} />
            <ReloadButton />
            <div className="grid grid-cols-3 gap-6 my-10 bg-gray-300 p-5 rounded-lg shadow-lg w-full">
                {drones.map((drone) => (
                    <DroneCard key={drone.id} drone={drone} onClick={() => {
                        handleOpen(drone.id);
                    }} />
                ))}
            </div>
            <div>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 800,
                        height: 400,
                        bgcolor: 'background.paper',
                        border: '2px solid #000',
                        boxShadow: 24,
                        p: 5,
                        borderRadius: 2
                    }}>
                        <div id="drone-infos">
                            <Typography id="modal-modal-title" variant="h4" component="h2">
                                Informações do Drone
                            </Typography>
                            <div className="infos p-2">
                                <BateriaDrone battery={infoDrone.bateria} />
                                <p className="text-lg">Alcance máximo: {infoDrone.kmMax} km</p>
                                <p className="text-lg">Peso máximo suportado: {infoDrone.pesoMax} kg</p>
                            </div>
                        </div>
                        <div id="drone-pedidos" className="mt-3">
                            <Typography variant="h4" component="h2">
                                Pedidos
                            </Typography>
                            <div className="infos p-2">
                                {infoDrone.pedidos && infoDrone.pedidos.length > 0 ? (
                                    <ul>
                                        {infoDrone.pedidos.map((pedido) => (
                                            <li key={pedido.id} className="mb-2">
                                                <p className="text-2xl"><strong>ID: {pedido.id}</strong></p>
                                                <div className="mx-3">
                                                    <p className="text-lg"><strong>Peso:</strong> {pedido.peso} kg</p>
                                                    <p className="text-lg"><strong>Localização: </strong>
                                                        ({pedido.localizacao.x}, {pedido.localizacao.y})
                                                    </p>
                                                    <p className="text-lg"><strong>Prioridade: </strong>{pedido.prioridade}</p>
                                                </div>
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <p>Este drone não possui pedidos associados.</p>
                                )
                                }
                            </div>
                        </div>
                    </Box>
                </Modal>
            </div >
            <Title text={"Pedidos | Dashboard"} />
        </>
    )
}