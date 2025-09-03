import { useEffect, useState } from "react"
import DroneCard from "../components/DroneCard"
import Title from "../components/Title";
import ReloadButton from '../components/ReloadButton';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';

export default function Dashboard() {
    const [drones, setDrones] = useState([]);
    const [open, setOpen] = useState(false);
    const [selectedDrone, setSelectedDrone] = useState(null);

    const handleOpen = () => setOpen(true);
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
                        setSelectedDrone(drone);
                        handleOpen();
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
                        p: 4,
                        borderRadius: 2
                    }}>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            Text in a modal
                        </Typography>
                        <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
                        </Typography>
                    </Box>
                </Modal>
            </div >
            <Title text={"Pedidos | Dashboard"} />
        </>
    )
}