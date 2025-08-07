## Prompts Utilizados
Contexto:<br>
Eu estava em dúvida se eu deveria fazer um save toda vez que o status do drone era atualizado durante o seu tempo de vôo.
- O drone possui outros status também, como CARREGANDO, RETORNANDO, entre outros. Como faço esse controle de status? Vou precisar dar um save para cada alteração de status no banco de dados?<br>
Contexto: <br>
Eu estava em dúvida sobre a separação de responsabilidades, eu inicialmente tinha pensado em criar um DistanciaService pra calcular a distância do pedido até a base dos drones, pois não queria fazer no DroneService.
- Eu digo esse DistânciaService, faz sentido? Eu até tinha pensado em fazer no DroneService, mas não faz muito sentido e daria um loop, né? Digo, o DroneService chamaria o TempoService e vice-versa.<br>
Contexto: <br>
O fato de eu estar trabalhando com coordenadas com km não me permitia visualizar um tempo estimado palpável de entrega, pois eu precisava setar valores muito pequenos (< 0, por exemplo) para visualizar essa estimativa de tempo.
- Vou fazer o seguinte: vou trabalhar com coordenadas em metros, porque assim o valor não é arredondado para baixo quando o número é muito pequeno. Por isso, preciso refatorar a fórmula.
  
