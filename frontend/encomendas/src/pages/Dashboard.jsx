import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
import Title from "../components/Title";
import ReloadButton from '../components/ReloadButton';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import BateriaDrone from "../components/BateriaDrone";
import InfoIcon from '../assets/icons/info.png'
import OrderIcon from '../assets/icons/order.png'
import StatusDrone from "../components/StatusDrone";
import NoOrderIcon from '../assets/icons/no-order.png'
import Alert from "../components/AlertDrone";

export default function Dashboard() {
    const [drones, setDrones] = useState([]);
    const [open, setOpen] = useState(false);
    const [infoDrone, setInfoDrone] = useState({})
    const [alert, setAlert] = useState(null); // {status, title, message}

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

    const handleOpen = (id) => {
        setOpen(true)
        fetchDroneById(id);
    };

    const handleClose = () => setOpen(false);

    useEffect(() => {
        fetchDrones();
        const interval = setInterval(fetchDrones, 8000);
        return () => clearInterval(interval);
    }, []);


    useEffect(() => {
        if (drones.length === 0) return;

        if (drones.some(d => d.bateria === 0)) {
            setAlert({ status: "error", title: "Bateria Descarregou", message: "Bateria do drone descarregou!" });
        } else if (drones.some(d => d.statusDrone === "EM_VOO")) {
            setAlert({ status: "warning", title: "Entregando Pacote", message: "Drone saiu para entrega!" });
        } else if (drones.some(d => d.statusDrone === "ENTREGANDO")) {
            setAlert({ status: "success", title: "Pacote Entregue", message: "Drone está entregando o pacote!" });
        } else {
            setAlert(null);
        }
    }, [drones]);

    function AlertToast({ show, onClose, ...props }) {
        useEffect(() => {
            if (show) {
                const timer = setTimeout(() => onClose(), 4000);
                return () => clearTimeout(timer);
            }
        }, [show]);

        if (!show) return null;

        return (
            <div className="fixed top-5 right-5 z-50 w-96">
                <Alert {...props} />
            </div>
        );
    }

    return (
        <>
            <AlertToast show={!!alert} onClose={() => setAlert(null)} {...alert} />

            {/* Card do Drone */}
            <Title text={"Drones"} />
            <hr />
            <ReloadButton />
            <div className="grid grid-cols-3 gap-6 my-10 bg-gray-300 p-5 rounded-lg shadow-lg w-full">
                {drones.map((drone) => (
                    <DroneCard key={drone.id} drone={drone} onClick={() => {
                        handleOpen(drone.id);
                    }} />
                ))}
            </div>

            {/* Modal do Drone */}
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
                        minHeight: 500,
                        bgcolor: 'background.paper',
                        boxShadow: 24,
                        p: 5,
                        borderRadius: 2
                    }}>

                        <div className="flex justify-between items-center mb-4">
                            <Typography id="modal-title" variant="h4" component="h2" className="flex items-center gap-3 font-oxygen">
                                <img src={InfoIcon} className="w-8 h-8" alt="Info Icon" />
                                <span className="font-oxygen">Drone #{infoDrone.id}</span>
                            </Typography>
                            <button onClick={handleClose} className="text-gray-500 cursor-pointer hover:text-gray-800 text-3xl">&times;</button>
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 p-4 bg-slate-50 rounded-lg mb-6">
                            <div>
                                <h3 className="text-xl font-semibold text-gray-700 mb-3 border-b pb-2">Status e Energia</h3>
                                <BateriaDrone battery={infoDrone.bateria} />
                                <StatusDrone status={infoDrone.status} />
                            </div>
                            <div className="p-1">
                                <h3 className="text-xl font-semibold text-gray-700 mb-3 border-b pb-2">Capacidades</h3>
                                <p className="text-lg text-gray-600 mt-3"><strong>Alcance máximo:</strong> {infoDrone.kmMax} km</p>
                                <p className="text-lg text-gray-600"><strong>Peso máximo:</strong> {infoDrone.pesoMax} kg</p>
                            </div>
                        </div>
                        <div id="drone-pedidos" className="my-3">
                            <Typography variant="h4" component="h2" className="flex items-center gap-3 mb-7 font-oxygen">
                                <img src={OrderIcon} className="w-8 h-8" alt="" />
                                <span className="font-oxygen">Pedidos</span>
                            </Typography>
                            {infoDrone.pedidos && infoDrone.pedidos.length > 0 ? (
                                <div className="infos max-h-48 overflow-y-auto bg-slate-50 rounded-lg p-3 my-5">
                                    <ul>
                                        {infoDrone.pedidos.map((pedido) => (
                                            <li key={pedido.id} className="mb-2">
                                                <p className="text-2xl"><strong>ID: {pedido.id}</strong></p>
                                                <div className="mx-3">
                                                    <p className="text-lg text-gray-600"><strong>Peso:</strong> {pedido.peso} kg</p>
                                                    <p className="text-lg text-gray-600"><strong>Localização (X, Y): </strong>
                                                        ({pedido.localizacao.x}, {pedido.localizacao.y})
                                                    </p>
                                                    <p className="text-lg text-gray-600"><strong>Prioridade: </strong>{pedido.prioridade}</p>
                                                </div>
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            ) : (
                                <div className="text-center py-4 bg-slate-50 rounded-lg">
                                    <p className="text-lg">Este drone não possui pedidos associados.</p>
                                    <img src={NoOrderIcon} alt="" className="w-75 m-auto" />
                                </div>
                            )}
                        </div>
                    </Box>
                </Modal>
            </div>
            <Title text={"Pedidos"} />
            <hr></hr>
        </>
    )
}