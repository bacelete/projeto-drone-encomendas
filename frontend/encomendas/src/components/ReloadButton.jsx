import {Button} from "antd";
import React from 'react';

export default function ReloadButton() {
    const handleRefresh = () => {
        window.location.reload();
    };

    return (
        <Button type="primary" onClick={handleRefresh} className="mt-4 font-oxygen">Atualizar página</Button>
    );
}