import Alert from "@mui/material/Alert"
import AlertTitle from "@mui/material/AlertTitle"

export default function AlertDrone({ message, status, title }) {
    return (
        <>
            <Alert severity={status} onClose={() => {}}>
                <AlertTitle><strong>{title}</strong></AlertTitle>
                {message}
            </Alert>
        </>
    )
}