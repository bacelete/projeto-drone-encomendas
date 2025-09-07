import { useState } from "react";
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Zoom from '@mui/material/Zoom';

export default function FormDrone({open, onClose}) {
    {/* Form do Drone */ }
    <div>
        <Modal
            open={open}
            onClose={onClose}
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
                        <div>Opened!</div>
                    </Box>
                </Zoom>
            </Box>
        </Modal>

    </div>
}