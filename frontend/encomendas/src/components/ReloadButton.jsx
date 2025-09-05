import {Button} from "antd";
import RefreshIcon from '../assets/icons/refresh.png'

export default function ReloadButton() {
    const handleRefresh = () => {
        window.location.reload();
    };

    return (
        <Button type="primary" onClick={handleRefresh}><span className="font-oxygen-regular flex gap-3 items-center">
           <img src={RefreshIcon} className="w-4 h-4" alt="" /> Atualizar página
            </span>
        </Button>
    );
}