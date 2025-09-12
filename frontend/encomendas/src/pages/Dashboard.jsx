import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
import Title from "../components/Title";
import ReloadButton from '../components/ReloadButton';
import Box from '@mui/material/Box';
import Alert from "../components/AlertDrone";
import PedidoCard from "../components/PedidoCard";
import EmptyCartIcon from '../assets/icons/empty-cart.png'
import { Button } from 'antd';
import NoDronesIcon from '../assets/icons/no-drones.png'
import Grow from '@mui/material/Grow';
import DroneForm from "../components/DroneForm";
import { Divider } from "antd";
import PedidoForm from '../components/PedidoForm';
import ModalDrone from "../components/ModalDrone";
import { Skeleton } from 'antd';
import { PlusOutlined } from "@ant-design/icons";
import DroneCardSkeleton from "../components/DroneCardSkeleton";


export default function Dashboard() {
    const [drones, setDrones] = useState([]);
    const [pedidos, setPedidos] = useState([]);

    const [infoDrone, setInfoDrone] = useState({})
    const [alert, setAlert] = useState(null); // {status, title, message}

    const [openModal, setOpenModal] = useState(false);
    const [openForm, setOpenForm] = useState(false);
    const [openPedidoForm, setOpenPedidoForm] = useState(false);

    const [isDronesLoading, setIsDronesLoading] = useState(true);
    const [isPedidosLoading, setIsPedidosLoading] = useState(true);


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
        finally {
            setTimeout(() => {
                setIsDronesLoading(false)
            }, 2000);
        }
    }

    async function fetchPedidos() {
        try {
            const response = await fetch(`http://localhost:8080/pedidos`, {
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
            setPedidos(data);
        }
        catch (e) {
            console.log(e);
        }
        finally {
            //...
        }
    }

    const handleOpen = (id) => {
        setOpenModal(true)
        fetchDroneById(id);
    };

    const handleClose = () => setOpenModal(false);

    useEffect(() => {
        fetchPedidos();
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
        }, [show, onClose]);

        if (!show) return null;

        return (
            <Grow in={show}>
                <div className="fixed top-5 right-5 z-50 w-96 font-oxygen-regular">
                    <Alert {...props} />
                </div>
            </Grow>
        );
    }

    const handleOpenForm = () => {
        setOpenForm(true);
    }

    const handleCloseForm = () => {
        setOpenForm(false);
    }

    const handleOpenPedidoForm = () => {
        setOpenPedidoForm(true);
    }

    const handleClosePedidoForm = () => {
        setOpenPedidoForm(false);
    }

    return (
        <>

            {/* Form do Drone */}
            <DroneForm open={openForm} onClose={handleCloseForm}></DroneForm>

            <ReloadButton />
            {/* Alerta */}
            <AlertToast show={!!alert} onClose={() => setAlert(null)} {...alert} />

            {/* Card do Drone */}
            <Box className="my-10">
                <div className="flex items-center gap-5">
                    <Title text={"Drones"} />
                    <Button type="primary" size='medium' onClick={handleOpenForm}><span className="font-oxygen-regular flex gap-1"><PlusOutlined />Criar</span></Button>
                </div>

                <Divider />
                <div className="grid grid-cols-3 gap-6 my-10 bg-gray-20 p-7 rounded-lg shadow-sm w-full">
                    {isDronesLoading ? (
                        Array.from(new Array(3)).map((_, index) => <DroneCardSkeleton key={index} />) //ia que gerou essa parte.
                    ) : drones.length > 0 ? (
                        drones.map((drone) => (
                            <DroneCard key={drone.id} drone={drone} onClick={() => {
                                handleOpen(drone.id);
                            }} />
                        ))
                    ) : (
                        <div className="w-110 m-auto text-center col-span-3">
                            <img src={NoDronesIcon} alt="" />
                            <p className="text-2xl font-oxygen-regular my-5">Não há drones disponíveis no momento...</p>
                        </div>
                    )}
                </div>


                {/* Form do Pedido */}
                <PedidoForm open={openPedidoForm} onClose={handleClosePedidoForm}></PedidoForm>

                {/* Card do Pedidos */}
                <div className="flex items-center gap-5 mt-20">
                    <Title text={"Pedidos"} />
                    <Button type="primary" size="medium" onClick={handleOpenPedidoForm}><span className="font-oxygen-regular flex gap-1"><PlusOutlined />Criar </span></Button>
                </div>
                <Divider />
                {pedidos.length > 0 ? (
                    <div className="flex gap-4">
                        {pedidos.map((pedido) => (
                            <PedidoCard id={pedido.id} key={pedido.id} peso={pedido.peso} prioridade={pedido.prioridade}></PedidoCard>
                        ))}
                    </div>
                ) : (
                    <div className="w-110 m-auto text-center">
                        <img src={EmptyCartIcon} alt="" />
                        <p className="text-2xl font-oxygen-regular my-5">Não há pedidos no momento...</p>
                    </div>
                )}


                {/* Modal do Drone */}
                <div>
                    <ModalDrone open={openModal} handleClose={handleClose} infoDrone={infoDrone}></ModalDrone>
                </div>
            </Box >
        </>
    )
}