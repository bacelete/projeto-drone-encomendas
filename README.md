# Projeto: Sistema de Entrega com Drones 🚁

## 📌 Descrição
Sistema que gerencia as entregas, drones e seus respectivos voos, respeitando regras de capacidade, distância e prioridade de entrega. O sistema aloca os pacotes nos drones com o menor número de viagens possível, respeitando as regras.

## ✅ Funcionalidades (rascunho)
- [x] Cadastro de pedidos
- [x] Cadastro de drones
- [x] Ordenação dos pedidos por peso
- [x] Alocação automática de drones
- [ ] Relatórios de entregas
- [ ] ...

## 🧠 Tecnologias utilizadas
- Java 21 + Spring Boot 3.4.3
- JPA + Hibernate
- MySQL (ou outro)
- ...

## 🚀 Como executar
⚠️ Instruções provisórias

## 📂 Estrutura do projeto
Explicação dos pacotes e organização

## 🔍 Lógica de alocação
### Lógica de ordenação:
Para a ordenação dos pedidos, foi utilizado a interface **Comparator** do **Collections.sort** do Java, que me permitiu fazer uma ordenação customizada com base no array de pedidos. A lógica de ordenação ficou sob a responsabilidade da classe **Sort**, criada no pacote utils na estrutura do meu projeto.<br><br>Para a ordenação dos pedidos, a função **void ordenarPedidosPorPeso(List<Pedido> pedido)** deverá ser chamada, recebendo uma lista de pedidos a serem ordenados.

### Lógica de alocação de pedidos:
Cada pedido com seu respectivo peso e distância, deve ser alocado para um drone disponível que possui capacidade e alcance possível para aquele pedido. Vale ressaltar que o sistema respeita a lógica de realizar o **menor número de viagens possíveis** e no contexto desse projeto, os drones priorizam os pedidos com o **maior peso** para realizar as entregas, conforme descrito na lógica de ordenação acima.<br>

**1. O Uso de Map**<br>
Para guardar os estados de cada drone (i.e, o peso, alcance e lista de pedidos), foi utilizado uma estratégia baseada na interface **Map** do Java. Foi utilizado essa estratégia pois assim, para cada pedido que respeite as condições daquele drone, o estado do peso e alcance do drone são atualizados para o próximo pedido. No projeto são instanciados três estruturas do tipo map: 

- Map<Drone, List<Pedido>> mapDronePedidos = new HashMap<>();
- Map<Drone, Double> mapDronePeso = new HashMap<>();
- Map<Drone, Double> mapDroneKm = new HashMap<>();

**2. Distância do Pedido**<br>
Para o cálculo da distância de um pedido, tomei como referência as coordenadas **(0, 0)** como a "base" dos drones, assim, usei a fórmula da distância entre dois pontos, porém adaptada para este contexto. Isto é: <br>

`2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))`

## 📸 Prints (opcional por enquanto)

## 👨‍💻 Autor
Arthur Bacelete
