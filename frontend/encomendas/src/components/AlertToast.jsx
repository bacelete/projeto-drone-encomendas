import { useEffect } from 'react';
import Alert from '@mui/material/Alert';
import Grow from '@mui/material/Grow';

export default function AlertToast({ open, message, severity, onClose }) {
    useEffect(() => {
        if (open) {
            const timer = setTimeout(() => {
                onClose();
            }, 4000);
            return () => clearTimeout(timer);
        }
    }, [open, onClose]);

    if (!open) return null;

    return (
        <Grow in={open}>
            <div className="fixed top-5 right-5 z-[9999] w-auto max-w-sm font-sans">
                <Alert
                    onClose={onClose}
                    severity={severity} // Pode ser 'success', 'error', 'warning', 'info'
                    sx={{ width: '100%' }}
                    variant="filled"
                >
                    {message}
                </Alert>
            </div>
        </Grow>
    );
}
