# Projeto: Sistema de Entrega com Drones 🚁

## 📌 Descrição
Sistema que gerencia as entregas, drones e seus respectivos voos, respeitando regras de capacidade, distância e prioridade de entrega. O sistema aloca os pacotes nos drones com o menor número de viagens possível, respeitando as regras.

## ✅ Funcionalidades
- [x] Cadastro de pedidos
- [x] Cadastro de drones
- [x] Ordenação dos pedidos por peso
- [x] Alocação automática de drones
- [X] Gerenciamento do tempo de entrega
- [ ] ...

## 🧠 Tecnologias utilizadas
- Java 21 + Spring Boot 3.4.3
- JPA + Hibernate
- MySQL
- ...

## 🚀 Como executar
⚠️ Instruções provisórias

## 📂 Estrutura do projeto
<img width="549" height="631" alt="image" src="https://github.com/user-attachments/assets/72b093c8-c645-4bd3-87f3-1fb87c2eb08e" />

## 🔍 Lógicas Aplicadas
### Lógica de ordenação:
**Quem faz?**<br>
A lógica de ordenação ficou sob a responsabilidade da classe **Sort**. A função `public void ordenarPedidosPorPeso(List<Pedido> pedido)` deverá ser chamada, recebendo uma lista de pedidos a serem ordenados.
<br><br>
**O que faz?**<br>
Ordena os pedidos com base no **peso**. 

### Lógica de alocação de pedidos:
**Quem faz?**<br>
A lógica de alocação ficou sob a responsabilidade da classe **ProdutoService**. A função `private List<Pedido> alocarPedidos(List<Pedido> pedidos, List<Drone> drones,
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
Realizada através da função `private List<Pedido> alocarPedidos(List<Pedido> pedidos, List<Drone> drones,
                               Map<Drone, List<PedidoDTO>> mapPedidos,
                               Map<Drone, Double> mapKm,
                               Map<Drone, Double> mapPeso)`<br>

Para cada pedido enviado na requisição (array de pedidos), é avaliado se há um drone disponível dentro dos valores do **peso** e **alcance** do pedido. Isso é realizado através da sentença: <br>
`(pesoPedido <= pesoRestante) && (distanciaPedido <= kmRestante)`<br><br>
Se há um drone disponível: <br>
- É sétado esse drone ao pedido através da linha `pedido.setDrone(drone)`
- É adicionado esse pedido a lista de pedidos alocados no `mapPedidos.get(drone).add(pedido).`
- É atualizado os valores de peso e alcance atual do drone através do `mapPeso.put(drone, pesoRestante - pesoPedido)` e `mapKm.put(drone, kmRestante - distanciaPedido);`

### Lógica de Entregas
**Quem faz?**<br>
A lógica de entregas ficou sob a responsabilidade da classe **DroneService**. A função `public void iniciarEntregas(Map<Drone, List<PedidoDTO>> mapDronePedidos)` deverá ser chamada.<br><br>
**O que faz?**<br>
Seta os pedidos para os drones e chama a função de gerenciar o tempo. 

- A função percorre de drone em drone através do `mapDronePedidos.keySet()`, que retorna um obj. do tipo `Set`.
- Ela busca os pedidos alocados para aquele drone através do **Repository** do pedido, na seguinte linha: `List<Pedido> pedidosReais = pedidoRepository.findByDrone(drone)` pois o mapDronePedidos retorna uma lista do tipo **PedidoDTO**, e não **Pedido**, como esperamos.
- Para cada drone, é setado uma lista de pedidos a ele: `drone.setPedidos(pedidosReais)`
- A função de gerenciar tempo de entrega é chamada: `tempoService.gerenciarTempoDeVoo(mapDronePedidos)`

### Lógica de Gerenciamento de Tempo de Entrega
Realizada pelo método <br>
`@Async
public void gerenciarTempoDeVoo(Map<Drone, List<PedidoDTO>> entregas)`

- A função percorre cada drone do `Map<Drone, List<PedidoDTO>> entregas` e cria uma instância do tipo **Entrega**, passando aquele drone como parâmetro.
- O status do drone é atualizado para **EM_VOO** e salvo no banco de dados.
- O tempo estimado de entrega dos pedidos de cada drone é calculado a partir do método `private long calcularTempoTotalEntrega(double distancia)`, onde o parâmetro distância é a soma das distâncias dos pedidos. Além disso, tomei como base que a velocidade média dos drones é constante e de **80km/h**.
- Utilização de `Thread.sleep(tempoEstimado)` para simulação do tempo de vôo e o mesmo para as outras mudanças de estado do drone. 
> Velocidade média representada como `public static final long VELOCIDADE_MEDIA = 80`

## 📸 Prints (opcional por enquanto)

## 👨‍💻 Autor
Arthur Bacelete
