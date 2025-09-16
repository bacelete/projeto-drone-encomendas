// 1. Importe o ícone PushpinOutlined
import { Card, Tag, Steps, message } from "antd";
import { QuestionCircleOutlined } from '@ant-design/icons';
import { Button, Popconfirm } from 'antd';
import { useState } from "react";
import AlertToast from "./AlertToast";

export default function PedidoCard({ id, peso, prioridade, status = 'aguardando' }) {
    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);

    const [alertToast, setAlertToast] = useState({
        open: false,
        message: "success",
        severity: "success"
    });

    const showPopconfirm = () => {
        setOpen(true);
    };

    const handleOk = (id) => {
        setConfirmLoading(true);

        deleteDroneById(id);

        setTimeout(() => {
            setOpen(false);
            setConfirmLoading(false);
        }, 2000);
    };

    const handleCancel = () => {
        setOpen(false);
    }

    const handleToastClose = () => {
        setAlertToast(prev => ({
            ...prev,
            open: false
        }));
    };

    const prioridadeMap = {
        alta: { color: 'volcano', text: 'Alta' },
        media: { color: 'orange', text: 'Media' },
        baixa: { color: 'green', text: 'Baixa' },
    };

    const statusSteps = {
        aguardando: 0,
        enviado: 1,
        entregue: 2
    };

    async function deleteDroneById(id) {
        console.log(id);
        try {
            const response = await fetch(`http://localhost:8080/pedidos/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            const data = await response.json();
            console.log(data.message);

            if (response.ok) {
                setAlertToast({
                    open: true,
                    message: "Pedido excluído com sucesso!",
                    severity: "success"
                });
            } else {
                setAlertToast({
                    open: true,
                    message: data.message || "Não foi possível excluir o pedido!",
                    severity: "error"
                });
            }

        }
        catch (e) {
            console.log(e);
            setAlertToast({
                open: true,
                message: "Erro ao excluir o pedido!",
                severity: "error"
            });
        }
        setTimeout(() => {
            window.location.reload();
        }, 3000)
    }

    const prioridadeInfo = prioridadeMap[prioridade.toLowerCase()] || { color: 'default', text: prioridade };
    const currentStep = statusSteps[status.toLowerCase()] || 0;

    return (
        <>
            <AlertToast
                open={alertToast.open}
                message={alertToast.message}
                severity={alertToast.severity}
                onClose={handleToastClose}
            />

            <div className="flex">
                <Card
                    className="font-oxygen-regular text-gray-800"
                    title={
                        <div className="flex justify-between" style={{ fontSize: '24px', fontWeight: 'bold' }}>
                            {`Pedido ${id}`}
                            <Popconfirm
                                open={open}
                                title="Excluir o pedido"
                                description="Tem certeza que deseja excluir o pedido?"
                                onConfirm={() => handleOk(id)}
                                okButtonProps={{ loading: confirmLoading }}
                                onCancel={handleCancel}
                                icon={<QuestionCircleOutlined style={{ color: 'red' }} />}
                            >
                                <Button danger onClick={showPopconfirm}>Excluir</Button>
                            </Popconfirm>
                        </div>
                    }
                    style={{
                        width: 325,
                        height: 380,
                        borderRadius: '8px',
                        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
                        position: 'relative',
                        overflow: 'hidden'
                    }}
                    key={id}
                >


                    <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                                <span className="text-[18px]">Peso: <strong>{peso} kg</strong></span>
                            </div>
                            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                                <span className="text-[18px]">Prioridade:</span>
                                <Tag color={prioridadeInfo.color}>{prioridadeInfo.text}</Tag>
                            </div>
                        </div>

                        <div>
                            <Steps
                                progressDot
                                current={currentStep}
                                direction="vertical"
                                items={[
                                    { title: 'Aguardando' },
                                    { title: 'Enviado' },
                                    { title: 'Entregue' },
                                ]}
                            />
                        </div>
                    </div>
                </Card>
            </div>
        </>

    );
}