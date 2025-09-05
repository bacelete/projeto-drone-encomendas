import { Card, Tag, Steps } from "antd";

export default function PedidoCard({ id, peso, prioridade = 'baixa', status = 'aguardando' }) {
    const prioridadeMap = {
        alta: { color: 'volcano', text: 'Alta' },
        media: { color: 'orange', text: 'Media' },
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
        <div className="flex transition-transform duration-300 ease-in-out hover:scale-105">
            <Card
                className="font-oxygen-regular"
                title={
                    <div style={{ fontSize: '24px', fontWeight: 'bold' }}>
                        {`Pedido ${id}`}
                    </div>
                }
                style={{
                    width: 325,
                    height: 220,
                    borderRadius: '8px',
                    boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
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