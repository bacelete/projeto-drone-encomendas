# Projeto: Sistema de Entrega com Drones 🚁

## 📌 Descrição
Sistema que gerencia as entregas, drones e seus respectivos voos, respeitando regras de capacidade, distância e prioridade de entrega. O sistema aloca os pacotes nos drones com o menor número de viagens possível, respeitando as regras. O projeto conta com um backend robusto para gerenciar a lógica e uma interface de frontend para visualização e interação.

## ✅ Funcionalidades
- [x] CRUD de Pedidos
- [x] CRUD de Drones
- [x] Ordenação dos Pedidos por Peso
- [x] Alocação Automática de Drones
- [X] Gerenciamento do Tempo de Entrega
- [X] Geração de Histórico de Pedidos
- [x] Geração de Relatórios de Eficiência
- [x] Tratamento de Erros
- [x] Simulação da Bateria do Drone
- [X] Cobertura de Testes Unitários
- [x] Interface Gráfica com React para monitoramento e interação.

## 🧠 Tecnologias utilizadas
- **Backend**: Java 21 + Spring Boot 3.4.3
- **Frontend**: Vite.js + React
- **Estilização**: TailwindCSS, Ant Design, Material UI
- **Banco de Dados**: MySQL
- **Ferramentas**: IntelliJ IDEA, Postman

## 🚀 Como executar
Siga as instruções abaixo para executar o projeto em sua máquina local.

### Pré-requisitos
- Java 21: Certifique-se de ter o JDK 21 instalado.
- Maven: O projeto utiliza o Maven para gerenciamento de dependências. Ele já vem incluído no wrapper (mvnw), então não é necessária uma instalação manual.
- Node.js e npm (ou yarn): Para executar a aplicação frontend.
- MySQL: É necessário ter um servidor MySQL em execução.

### Backend
1.  **Clone este repositório**
    ```bash
    git clone [https://github.com/bacelete/projeto-drone-encomendas.git](https://github.com/bacelete/projeto-drone-encomendas.git)
    ```

2.  **Configure o banco de dados**
    - Crie um banco de dados com nome de **encomendas_dti**.
    - No arquivo `application.properties`, altere as configurações de acordo com o usuário e senha do seu banco de dados.

3.  **Execute a aplicação**
    Navegue até a pasta `backend/encomendas/encomendas` e execute a aplicação Spring Boot. O servidor iniciará em `http://localhost:8080/`.

### Frontend
1.  **Navegue até a pasta do frontend**
    ```bash
    cd frontend/encomendas
    ```
2.  **Instale as dependências**
    ```bash
    npm install
    ```
3.  **Execute a aplicação**
    ```bash
    npm run dev
    ```
    A aplicação React estará disponível em `http://localhost:5173`.

### Endpoints da API
- `POST /drones`: Cria um ou mais drones.
- `GET /drones/{id}`: Busca um drone pelo seu ID.
- `GET /drones/status`: Retorna a lista de drones com seus status atuais.
- `POST /pedidos`: Cria um ou mais pedidos e os aloca aos drones disponíveis.
- `GET /pedidos`: Retorna a lista de todos os pedidos.
- `DELETE /pedidos/{id}`: Deleta um pedido pelo seu ID.
- `GET /entregas`: Retorna o histórico de todas as entregas realizadas.
- `GET /relatorio`: Gera um relatório com métricas de entregas e eficiência dos drones.

## 🔍 Lógicas Aplicadas

### Ordenação e Alocação de Pedidos
- **Responsabilidade**: A lógica de ordenação está na classe `Sort`, enquanto a alocação é gerenciada pelo `PedidoService`.
- **Funcionamento**: Os pedidos são primeiramente ordenados por peso em ordem decrescente para otimizar a capacidade dos drones. Em seguida, cada pedido é alocado a um drone disponível que possua capacidade de peso e alcance (distância de ida e volta) suficientes. A distância é calculada considerando a base como ponto (0,0). O estado de cada drone (peso e alcance restantes) é atualizado dinamicamente usando estruturas de `Map` para garantir o menor número de viagens.

### Simulação de Voo e Entrega
- **Responsabilidade**: As classes `TempoService`, `EntregaService` e `BateriaService` gerenciam o ciclo de vida de uma entrega.
- **Funcionamento**:
    1.  O `DroneService` inicia o processo, passando os drones com pedidos alocados para o `TempoService`.
    2.  O `TempoService`, de forma assíncrona (`@Async`), simula o tempo de voo e entrega usando `Thread.sleep()`. Durante este processo, o status do drone é atualizado (e.g., `EM_VOO`, `ENTREGANDO`) e salvo no banco de dados.
    3.  O `EntregaService` é responsável por criar um registro de `Entrega` no início do voo e finalizá-lo ao término, calculando a duração total.

### Simulação de Bateria
- **Responsabilidade**: `BateriaService`.
- **Funcionamento**: Um método agendado (`@Scheduled`) executa periodicamente para simular o consumo e recarga da bateria. A taxa de consumo varia conforme o status do drone (e.g., `EM_VOO` consome mais que `ENTREGANDO`). Quando a bateria está baixa, o drone entra automaticamente em modo `CARREGANDO`.

## 📸 Interface
A interface do sistema, desenvolvida em React, oferece uma visão em tempo real da frota de drones e da fila de pedidos.

### Dashboard Principal
- **Visualização de Drones**: Apresenta cards para cada drone, exibindo seu ID, nível de bateria com ícones representativos e status atual (Disponível, Em Voo, Entregando, Carregando).
- **Fila de Pedidos**: Mostra os pedidos pendentes e em andamento, detalhando seu ID, peso, prioridade e o status atual do processo de entrega (Aguardando, Enviado, Entregue).
- **Interatividade**: Permite criar novos drones e pedidos através de formulários modais, além de visualizar detalhes de um drone específico e seus pedidos associados.
- **Feedback ao Usuário**: Notificações (toasts) são exibidas para informar sobre eventos importantes, como a saída de um drone para entrega ou o sucesso na criação/exclusão de um pedido.

## 👨‍💻 Autor
Arthur Bacelete
