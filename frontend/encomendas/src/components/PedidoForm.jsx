import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Zoom from '@mui/material/Zoom';
import { Button, Form, Input, InputNumber } from 'antd';
import AlertDrone from './AlertDrone';
import { useState } from 'react';
import { Select, Space } from 'antd';

export default function PedidoForm({ open, onClose }) {
    const [form] = Form.useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [alertOpen, setAlertOpen] = useState(false);

    const handleCloseForm = () => {
        setAlertOpen(false);
    }

    const onFinish = async (values) => {
        setIsLoading(true);

        const dataToSend = [{
            peso: values.peso,
            prioridade: values.prioridade,
            localizacao: {
                x: values.localizacao.x,
                y: values.localizacao.y,
            },
        }];

        console.log(dataToSend);

        try {
            const response = await fetch(`http://localhost:8080/pedidos`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataToSend),
            });

            if (!response.ok) {
                throw new Error("Erro na requisição!");
            }

            console.log("Pedido criado com sucesso!");
            form.resetFields();
            onClose();

            setTimeout(function() {
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
                    <AlertDrone status={"success"} message={"Pedido criado com sucesso!"} title={"Pedido Criado"} onClose={handleCloseForm} />
                </div>
            }
            <div>
                <Modal open={open} onClose={onClose}>
                    <Box sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: { xs: '90%', sm: '70%', md: '500px' },
                        bgcolor: 'background.paper',
                        boxShadow: 24, p: 4, borderRadius: 2
                    }}>
                        <Zoom in={open}>
                            <div>
                                <h1 className='text-3xl font-oxygen mb-6'>Adicionar Novo Pedido</h1>
                                <Form
                                    form={form}
                                    name="pedido_form"
                                    layout="vertical"
                                    onFinish={onFinish}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label="Peso (kg)"
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

                                    <Space style={{ display: 'flex', marginBottom: 8 }} align="baseline">
                                        <Form.Item
                                            label="Coordenada X"
                                            name={['localizacao', 'x']}
                                            rules={[{ required: true, message: 'Insira a coordenada X!' }]}
                                        >
                                            <InputNumber size='large' style={{ width: '100%' }} />
                                        </Form.Item>
                                        <Form.Item
                                            label="Coordenada Y"
                                            name={['localizacao', 'y']}
                                            rules={[{ required: true, message: 'Insira a coordenada Y!' }]}
                                        >
                                            <InputNumber size='large' style={{ width: '100%' }} />
                                        </Form.Item>
                                    </Space>


                                    <Form.Item
                                        label="Prioridade"
                                        name="prioridade"
                                        rules={[{ required: true, message: 'Por favor, selecione a prioridade!'}]}
                                    >
                                        <Select
                                            size='large'
                                            style={{ width: '100%' }}
                                            options={[
                                                { value: 'ALTA', label: 'Alta' },
                                                { value: 'MEDIA', label: 'Média' },
                                                { value: 'BAIXA', label: 'Baixa' },
                                            ]}
                                            getPopupContainer={triggerNode => triggerNode.parentNode}
                                        />
                                    </Form.Item>
                                    <Form.Item className='mt-6'>
                                        <Button type="primary" htmlType="submit" size='large' block danger loading={isLoading}>
                                            {isLoading ? 'Enviando...' : 'Criar Pedido'}
                                        </Button>
                                    </Form.Item>
                                </Form>
                            </div>
                        </Zoom>
                    </Box>
                </Modal>
            </div>
        </>
    );
}