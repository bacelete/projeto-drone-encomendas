import Alert from "@mui/material/Alert"
import AlertTitle from "@mui/material/AlertTitle"

export default function AlertDrone({ message, status, title, onClose }) {
    return (
        <>
            <Alert severity={status} onClose={onClose}>
                <AlertTitle><strong>{title}</strong></AlertTitle>
                {message}
            </Alert>
        </>
    )
}