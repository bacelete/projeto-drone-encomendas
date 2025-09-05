// 1. Importe ConfigProvider e Space que estavam faltando
import { Button, ConfigProvider, Space } from "antd";
import RefreshIcon from '../assets/icons/refresh.png';
import { createStyles } from 'antd-style';

const useStyle = createStyles(({ prefixCls, css }) => ({
    linearGradientButton: css`
        &.${prefixCls}-btn-primary:not([disabled]):not(.${prefixCls}-btn-dangerous) {
            // Garante que o conteúdo do botão fique acima do gradiente
            position: relative; 
            overflow: hidden;

            > span {
                // Garante que o texto fique na camada de cima
                position: relative;
                z-index: 1;
            }

            &::before {
                content: '';
                background: linear-gradient(135deg, #6253e1, #04befe);
                position: absolute;
                inset: 0; // Use inset: 0 para preencher o botão
                z-index: 0; // Coloca o gradiente na camada de trás
                opacity: 1;
                transition: opacity 0.3s ease-in-out;
                border-radius: inherit; // Herda o border-radius do botão
            }

            // Ao passar o mouse, o gradiente some e revela a cor original do botão
            &:hover::before {
                opacity: 0;
            }
        }
    `,
}));

export default function ReloadButton() {
    const { styles } = useStyle();

    const handleRefresh = () => {
        window.location.reload();
    };

    return (
        <ConfigProvider
            button={{
                className: styles.linearGradientButton,
            }}
        >
            <Button
                type="primary"
                size="large"
                onClick={handleRefresh}
                icon={<img src={RefreshIcon} className="w-4 h-4" alt="Atualizar" />}
                className="font-oxygen-regular flex items-center"
            >
                Atualizar página
            </Button>
        </ConfigProvider>
    );
}