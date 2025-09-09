import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Zoom from '@mui/material/Zoom';
import { Button, Form, InputNumber } from 'antd';
import AlertDrone from './AlertDrone';
import { useState } from 'react';

export default function DroneForm({ open, onClose }) {
    const [form] = Form.useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [alertOpen, setAlertOpen] = useState(false);

    const handleCloseForm = () => {
        setAlertOpen(false);
    }

    const onFinish = async (values) => {
        setIsLoading(true);

        const dataToSend = {
            pesoMax: values.peso,
            kmMax: values.alcance,
            bateria: values.bateria,
        };

        console.log(dataToSend);

        try {
            const response = await fetch(`http://localhost:8080/drones/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataToSend),
            });

            if (!response.ok) {
                throw new Error("Erro na requisição!");
            }

            console.log("Drone criado com sucesso!");
            form.resetFields();
            onClose();

            setTimeout(function () {
                window.location.reload();
            }, 2000)
        } catch (e) {
            console.error(e);
            // Aqui você poderia mostrar uma notificação de erro ao usuário
        } finally {
            setIsLoading(false);
            setAlertOpen(true);
        }
    };

    return (
        <>
            {alertOpen &&
                <div className='font-oxygen my-2'>
                    <AlertDrone status={"success"} message={"Drone criado com sucesso!"} title={"Drone Criado"} onClose={handleCloseForm} />
                </div>
            }
            <div>
                <Modal open={open} onClose={onClose}>
                    <Box
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            height: '100vh',
                        }}
                    >
                    <Zoom in={open}>
                        <Box sx={{
                            margin: '0 auto',
                            transitionDelay: '2000',
                            width: { xs: '90%', sm: '70%', md: '500px' },
                            bgcolor: 'background.paper',
                            boxShadow: 24, p: 4, borderRadius: 2
                        }}>

                            <div>
                                <h1 className='text-3xl font-oxygen mb-6'>Adicionar Novo Drone</h1>
                                {/* 4. Conectar a instância do formulário e usar layout vertical */}
                                <Form
                                    form={form}
                                    name="drone_form"
                                    layout="vertical"
                                    onFinish={onFinish}
                                    initialValues={{ bateria: 100 }} // Valor inicial padrão
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label="Peso máximo (kg)"
                                        name="peso"
                                        rules={[{ required: true, message: 'Por favor, insira o peso!' }]}
                                    >
                                        <InputNumber
                                            min={0}
                                            size='large'
                                            style={{ width: '100%' }}
                                            addonAfter="kg"
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        label="Alcance máximo (km)"
                                        name="alcance"
                                        rules={[{ required: true, message: 'Por favor, insira o alcance!' }]}
                                    >
                                        <InputNumber
                                            min={0}
                                            size='large'
                                            style={{ width: '100%' }}
                                            addonAfter="km"
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        label="Bateria (%)"
                                        name="bateria"
                                        rules={[{ required: true, message: 'Por favor, insira a bateria!' }]}
                                    >
                                        <InputNumber
                                            min={0}
                                            max={100}
                                            size='large'
                                            style={{ width: '100%' }}
                                            addonAfter="%"
                                        />
                                    </Form.Item>
                                    <Form.Item className='mt-6'>
                                        <Button type="primary" htmlType="submit" size='large' block danger loading={isLoading}>
                                            {isLoading ? 'Enviando...' : 'Criar Drone'}
                                        </Button>
                                    </Form.Item>
                                </Form>
                            </div>

                        </Box>
                    </Zoom>
                    </Box>
                </Modal>
                
            </div>
        </>
    );
}