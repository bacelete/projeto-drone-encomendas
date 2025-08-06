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

## 📸 Prints (opcional por enquanto)

## 👨‍💻 Autor
Arthur Bacelete
