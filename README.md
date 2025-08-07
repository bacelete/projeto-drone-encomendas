# Projeto: Sistema de Entrega com Drones 🚁

## 📌 Descrição
Sistema que gerencia as entregas, drones e seus respectivos voos, respeitando regras de capacidade, distância e prioridade de entrega. O sistema aloca os pacotes nos drones com o menor número de viagens possível, respeitando as regras.

## ✅ Funcionalidades
- [x] Cadastro de pedidos
- [x] Cadastro de drones
- [x] Ordenação dos pedidos por peso
- [x] Alocação automática de drones
- [X] Gerenciamento do tempo de entrega
- [X] Geração de Histórico de Pedidos
- [x] Geração de Relatórios
- [x] Tratamento de Erros 
- [X] Cobertura de Testes Unitários

## 🧠 Tecnologias utilizadas
- Java 21 + Spring Boot 3.4.3
- JPA + Hibernate
- MySQL
- Postman

## 🚀 Como executar
Siga as instruções abaixo para executar o projeto em sua máquina local. <br>

### Pré-requisitos
- Java 21: Certifique-se de ter o JDK 21 instalado.
- Maven: O projeto utiliza o Maven para gerenciamento de dependências. Ele já vem incluído no wrapper do Maven (mvnw), então não é necessária uma instalação manual.
- MySQL: É necessário ter um servidor MySQL em execução.
- Postman (ou similar): Uma ferramenta para testar os endpoints da API.

1. Clone este repositório
`git clone https://github.com/seu-usuario/projeto-dti-encomendas.git
cd projeto-dti-encomendas/backend/encomendas/encomendas`<br>

2. Configure o banco de dados
- Crie um banco de dados com nome de **encomendas_dti**
- No arquivo application.properties, altere as configurações de acordo com o usuário e senha do seu banco de dados (o meu por padrão, o usuário e senha é root).<br>

<img width="682" height="143" alt="image" src="https://github.com/user-attachments/assets/bfd69c96-f951-4a5e-9ed1-67cc37cc5e6c" /><br>

3. Execute a aplicação SpringBoot em **EncomendasApplication**. Ele vai gerar um servidor local com a URL **http://localhost:8080/** por padrão<br>

4. Teste de ENDPOINTS pelo Postman: <br>
- `POST /drones`
- `POST /pedidos`
- `GET /drones/{id}`
- `GET /entregas`
- `GET /drones/status`
- `GET /relatorio`

## 📂 Estrutura do projeto
<img width="394" height="278" alt="image" src="https://github.com/user-attachments/assets/44b42511-1db0-496a-bc96-947008a965f1" />


## 🔍 Lógicas Aplicadas
### Lógica de ordenação:
**Quem faz?**<br>
A lógica de ordenação ficou sob a responsabilidade da classe **Sort**. A função `public void ordenarPedidosPorPeso(List<Pedido> pedido)` deverá ser chamada, recebendo uma lista de pedidos a serem ordenados.
<br><br>
**O que faz?**<br>
Ordena os pedidos com base no **peso**. Para isso, utilizei uma interface **Comparator** que me permite fazer ordenações customizadas na linha de código: `Collections.sort(pedidos, new Comparator<Pedido>()`

### Lógica de alocação de pedidos:
**Quem faz?**<br>
A lógica de alocação ficou sob a responsabilidade da classe **ProdutoService**. A função `private PedidosResponseDTO alocarPedidos(List<Pedido> pedidos, List<Drone> drones,
                               Map<Drone, List<PedidoDTO>> mapPedidos,
                               Map<Drone, Double> mapKm,
                               Map<Drone, Double> mapPeso)` deverá ser chamada. <br><br>
**O que faz?**<br>
Cada pedido com seu respectivo peso e distância, deve ser alocado para um drone disponível que possui capacidade e alcance possível para aquele pedido e se baseando no **menor número de viagens possíveis**.

**1. O Uso de Map**<br>
Para guardar os estados de cada drone (i.e, o peso, alcance e lista de pedidos), foi utilizado uma estratégia baseada na interface **Map** do Java. Foi utilizado essa estratégia pois assim, para cada pedido que respeite as condições daquele drone, o estado do peso e alcance do drone são atualizados para o próximo pedido. No projeto são instanciados três estruturas do tipo map: 

- `Map<Drone, List<PedidoDTO>> mapDronePedidos = new HashMap<>()`
- `Map<Drone, Double> mapDronePeso = new HashMap<>()`
- `Map<Drone, Double> mapDroneKm = new HashMap<>()`<br>

> Por que **PedidoDTO** em **Map<Drone, List<PedidoDTO>>**?<br>
> Foi utilizado o DTO do Pedido pois eu precisava setar a **distância** calculada em algum lugar, já que a entidade Pedido não possui esse campo. A distância é necessária para calcular o tempo estimado da entrega, é usada no controle da bateria do drone, etc. 

**2. Distância do Pedido**<br>
Calculada apartir da função `private double calcularDistancia(int x, int y)`<br>

Para o cálculo da distância de um pedido, tomei como referência as coordenadas **(0, 0)** como a "base" dos drones, assim, usei a fórmula da distância entre dois pontos multiplicado por 2 (considerando ida e volta), porém adaptada para este contexto. Isto é: <br>

`2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))`

> Por padrão a fórmula da distância entre dois pontos é `Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))`, porém assumi que (x1, y1) = (0, 0);<br>

**3. Alocação dos Pedidos**<br>
Para cada pedido enviado na requisição (array de pedidos), é avaliado se há um drone disponível dentro dos valores do **peso** e **alcance** do pedido. Isso é realizado através da sentença: <br>
`(pesoPedido <= pesoRestante) && (distanciaPedido <= kmRestante)`<br><br>
Se há um drone disponível que satisfaça as condições: <br>
- É sétado esse drone ao pedido através da linha `pedido.setDrone(drone)`
- É criada setada a variável de controle `foiAlocado = true`
- É adicionado esse pedido a lista de pedidos alocados no drone através do `mapPedidos.get(drone).add(pedido).`
- É atualizado os valores de peso e alcance atual do drone através do `mapPeso.put(drone, pesoRestante - pesoPedido)` e `mapKm.put(drone, kmRestante - distanciaPedido)`<br><br>
Se não há um drone disponível:
- O pedido é adicionado para a lista de pacotes rejeitados

**4. Retorno como ProdutosResponseDTO**<br>
Para cada pedido aprovado ou não, eles são adicionados em listas do tipo `List<Pedido> pedidos_alocados` e `List<Pedido> pedidos_rejeitados` na classe **ProdutosResponseDTO** que serão retornadas como resposta da requisição. 

### Lógica de Setar Pedidos
**Quem faz?**<br>
A lógica de entregas ficou sob a responsabilidade da classe **DroneService**. A função `public void iniciarEntregas(Map<Drone, List<PedidoDTO>> mapDronePedidos)` deverá ser chamada.<br><br>
**O que faz?**<br>
Seta os pedidos para os drones e chama a função de gerenciar o tempo. 

- A função percorre de drone em drone através do `mapDronePedidos.keySet()`, que retorna um obj. do tipo `Set`.
- Ela busca os pedidos alocados para aquele drone através do **Repository** do pedido, na seguinte linha: `List<Pedido> pedidosReais = pedidoRepository.findByDrone(drone)`, pois o mapDronePedidos retorna uma lista do tipo **PedidoDTO**, e não **Pedido**, como esperamos.
- Para cada drone, é setado uma lista de pedidos a ele: `drone.setPedidos(pedidosReais)`
- A função de gerenciar tempo de entrega é chamada: `tempoService.gerenciarTempoDeVoo(mapDronePedidos)`

### Lógica de Simulação de Tempo de Entrega
**Quem faz?**<br>
A lógica de gerenciamento de tempo de entrega ficou sob a responsabilidade da classe **TempoService**. A função 
`@Async
public void gerenciarTempoDeVoo(Map<Drone, List<PedidoDTO>> entregas)` deverá ser chamada.<br><br>
**O que faz?**<br>

- A função percorre cada drone do `Map<Drone, List<PedidoDTO>> entregas` e cria uma instância do tipo **Entrega**, passando aquele drone como parâmetro.
- O status do drone é atualizado para **EM_VOO** e salvo no banco de dados.
- O tempo estimado de entrega dos pedidos de cada drone é calculado a partir do método `private long calcularTempoTotalEntrega(double distancia)`, onde o parâmetro distância é a soma das distâncias dos pedidos. Além disso, tomei como base que a velocidade média dos drones é constante e de **80km/h**.
- Utilização de `Thread.sleep()` para simulação do tempo de vôo e tempo de entrega.
> Velocidade média representada como `public static final long VELOCIDADE_MEDIA = 80`

### Lógica de Gerenciamento da Entrega
**Quem faz?**<br>
A lógica de criação/finalização da entrega ficou sob a responsabilidade da classe **EntregaService**.<br><br>
**O que faz?**<br>
- O método `public Entrega criarEntrega(Drone drone)` seta um drone para aquela entrega, um instante de início dessa entrega e a quantidaed de pedidos.
- O método `public void finalizarEntrega(Entrega entrega)` seta o instante final da entrega e a duração total da entrega.


## 📸 Prints (opcional por enquanto)

## 👨‍💻 Autor
Arthur Bacelete
