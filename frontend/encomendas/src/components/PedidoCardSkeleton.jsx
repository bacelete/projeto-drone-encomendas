import { Card } from "antd"
import { Skeleton } from 'antd';

export default function PedidoCardSkeleton() {
    return <>
        <div className="flex">
            <Card
                className="font-oxygen-regular text-gray-800"
                style={{
                    width: 325,
                    height: 380,
                    borderRadius: '8px',
                    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
                    position: 'relative',
                    overflow: 'hidden'
                }}>
                    <Skeleton></Skeleton>
                </Card>
        </div>
    </>
}