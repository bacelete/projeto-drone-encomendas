import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import BateriaDrone from "../components/BateriaDrone";
import InfoIcon from '../assets/icons/info.png'
import OrderIcon from '../assets/icons/order.png'
import StatusDrone from "../components/StatusDrone";
import NoOrderIcon from '../assets/icons/no-order.png'
import Zoom from '@mui/material/Zoom';
import Box from '@mui/material/Box';


export default function ModalDrone({open, handleClose, infoDrone}) {
    return (
        <>
            <Modal
                open={open}
                onClose={handleClose}
                onClick={(e) => e.stopPropagation()}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
                className={`
                            ${open ? 'scale-100 opacity-100' : 'scale-95 opacity-0'}
                            `}
            >
                <Box sx={{ display: 'flex', justifyContent: 'center', marginY: '15px' }}>
                    <Zoom in={open}>
                        <Box sx={{
                            position: 'absolute',
                            width: { xs: '90%', sm: '70%', md: '50%', lg: '40%' },
                            minHeight: 400,
                            bgcolor: 'background.paper',
                            boxShadow: 24,
                            overflowY: 'auto',
                            p: 5,
                            borderRadius: 2,
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
                                    <p className="text-lg mt-3">Alcance máximo: <strong>{infoDrone.kmMax} km</strong></p>
                                    <p className="text-lg">Peso máximo: <strong>{infoDrone.pesoMax} kg</strong></p>
                                </div>
                            </div>
                            <div id="drone-pedidos" className="my-3">
                                <Typography variant="h4" component="h2" className="flex items-center gap-3 mb-7 font-oxygen">
                                    <img src={OrderIcon} className="w-8 h-8" alt="" />
                                    <span className="font-oxygen mb-5">Pedidos</span>
                                </Typography>
                                {infoDrone.pedidos && infoDrone.pedidos.length > 0 ? (
                                    <div className="infos max-h-48 overflow-y-auto bg-slate-50 rounded-lg p-4 my-6">
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
                    </Zoom>
                </Box>
            </Modal>

        </>
    )
}