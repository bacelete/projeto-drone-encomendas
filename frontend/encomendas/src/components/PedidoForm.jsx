import { useState } from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Zoom from '@mui/material/Zoom';
import { Button, Form, InputNumber, Select, Space } from 'antd';
import AlertToast from './AlertToast';

export default function PedidoForm({ open, onClose }) {
    const [form] = Form.useForm();
    const [isLoading, setIsLoading] = useState(false);

    const [toastState, setToastState] = useState({
        open: false,
        message: '',
        severity: 'success', // Padrão
    });

    // Função para fechar o toast
    const handleToastClose = () => {
        setToastState({ ...toastState, open: false });
    };

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

            const data = await response.json();
            console.log(data);

            if (data.pedidos_rejeitados && data.pedidos_rejeitados.length > 0) {
                setToastState({ 
                    open: true, 
                    message: "Pedido rejeitado! Não há drone disponível para atender a solicitação.", 
                    severity: 'warning' 
                });
            } else {
                setToastState({ 
                    open: true, 
                    message: "Pedido criado com sucesso!", 
                    severity: 'success' 
                });
                form.resetFields();
            }

            onClose(); // Fecha o modal
            setTimeout(() => {
                window.location.reload();
            }, 2000);

        } catch (e) {
            console.error(e);
            setToastState({ 
                open: true, 
                message: "Falha ao criar o pedido. Tente novamente.", 
                severity: 'error' 
            });
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <>
            {/* Componente de alerta padronizado */}
            <AlertToast
                open={toastState.open}
                message={toastState.message}
                severity={toastState.severity}
                onClose={handleToastClose}
            />

            <Modal
                open={open}
                onClose={onClose}
                sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
            >
                <Zoom in={open}>
                    <Box
                        sx={{
                            width: { xs: '90%', sm: '70%', md: '500px' },
                            bgcolor: 'background.paper',
                            boxShadow: 24, p: 4, borderRadius: 2
                        }}
                    >
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
                                    rules={[{ required: true, message: 'Por favor, selecione a prioridade!' }]}
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
                    </Box>
                </Zoom>
            </Modal>
        </>
    );
}
