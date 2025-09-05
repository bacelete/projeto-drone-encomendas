import { Card } from "antd";

export default function PedidoCard({id, peso, prioridade}) {
    return (
        <>
            <Card title="Default size card" extra={<a href="#">More</a>} style={{ width: 300 }} key={id}>
                <p>Card content</p>
            </Card>
        </>
    )
}