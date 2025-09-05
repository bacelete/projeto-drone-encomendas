import { Card, Tag, Steps } from "antd";
import { CarryOutOutlined, ThunderboltOutlined } from '@ant-design/icons';

export default function PedidoCard({ id, peso, prioridade }) {
    const prioridadeMap = {
        alta: { color: 'volcano', text: 'Alta' },
        media: { color: 'orange', text: 'Média' },
        baixa: { color: 'green', text: 'Baixa' },
    };

    const statusSteps = {
        aguardando: 0,
        preparando: 1,
        enviado: 2,
        entregue: 3
    };

    const prioridadeInfo = prioridadeMap[prioridade.toLowerCase()] || { color: 'default', text: prioridade };
    const currentStep = statusSteps[status.toLowerCase()] || 0;

    return (
        <div className="transition-transform duration-300 ease-in-out hover:scale-101 cursor-pointer">
            <Card
                title={`Pedido ${id}`}
                style={{
                    width: 325,
                    height: 220,
                    borderRadius: '8px',
                    boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
                }}
                key={id}
            >
                <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                            <CarryOutOutlined />
                            <span>Peso: <strong>{peso} kg</strong></span>
                        </div>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                            <ThunderboltOutlined />
                            <span>Prioridade:</span>
                            <Tag color={prioridadeInfo.color}>{prioridadeInfo.text}</Tag>
                        </div>
                    </div>

                    {/* Novo Componente de Status do Pedido */}
                    <div>
                        <Steps
                            current={currentStep}
                            size="small"
                            items={[
                                { title: 'Aguardando' },
                                { title: 'Preparando' },
                                { title: 'Enviado' },
                                { title: 'Entregue' },
                            ]}
                        />
                    </div>
                </div>
            </Card>
        </div>
    );
}