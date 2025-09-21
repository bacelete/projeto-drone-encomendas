// src/components/AppFooter.jsx
import { Layout, Typography } from 'antd';

const { Footer } = Layout;
const { Link, Text } = Typography;

const AppFooter = () => {
  const currentYear = new Date().getFullYear();

  return (
    <Footer style={{ textAlign: 'center', backgroundColor: '#f0f2f5' }}>
      <Text type="secondary">
        ©{currentYear} | Desenvolvido por{' '}
        <Link href="https://github.com/bacelete" target="_blank">
          Arthur Bacelete
        </Link>
      </Text>
    </Footer>
  );
};

export default AppFooter;