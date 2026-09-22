# Requisitos - v0.3

## Objetivo

Substituir a interface de console por uma interface gráfica (JavaFX com FXML). O usuário passa a interagir com a agenda por meio de telas: um calendário como tela principal e, ao selecionar um dia, a lista de tarefas daquele dia, com as ações de criar, editar, concluir e remover. As regras de negócio e a persistência (v0.2) permanecem inalteradas.

## Contexto da mudança

Até a v0.2, a interação era por menu de texto no console. A v0.3 troca essa camada de apresentação por uma interface gráfica, reaproveitando todo o domínio (Tarefa, Agenda) e a persistência (TarefaRepository) já existentes. A mudança é na "cara" do sistema; o motor continua o mesmo.

## Requisitos funcionais

- **RF14** - O sistema deve exibir um calendário mensal como tela principal, permitindo navegar entre os meses.
- **RF15** - O sistema deve permitir selecionar um dia no calendário para visualizar as tarefas daquele dia.
- **RF16** - O sistema deve indicar visualmente, no calendário, os dias que possuem tarefas.
- **RF17** - O sistema deve exibir as tarefas do dia selecionado em uma lista, ordenadas por horário, indicando as concluídas.
- **RF18** - O sistema deve permitir criar uma tarefa por meio de um formulário gráfico.
- **RF19** - O sistema deve permitir editar uma tarefa existente por meio de um formulário gráfico.
- **RF20** - O sistema deve permitir marcar uma tarefa como concluída pela interface gráfica.
- **RF21** - O sistema deve permitir remover uma tarefa pela interface gráfica, com confirmação.
- **RF22** - O sistema deve exibir mensagens de erro e de confirmação na própria interface gráfica (sem depender do console).

## Requisitos não funcionais

- **RNF08** - A interface deve ser construída com JavaFX, usando FXML para descrever as telas, separando a aparência da lógica.
- **RNF09** - A interface deve ter aparência cuidada e consistente, com estilos aplicados via CSS do JavaFX.
- **RNF10** - As regras de negócio e a persistência existentes não devem ser alteradas pela introdução da interface gráfica; a GUI deve consumir a Agenda da mesma forma que o console consumia.
- **RNF11** - Uma falha de acesso ao banco deve ser informada ao usuário na interface, sem encerrar a aplicação de forma abrupta.

## Fora do escopo desta versão

- Post-its flutuantes na área de trabalho (planejado para a v0.4)
- Notificações/alertas por horário (planejado para a v0.4)
- Comando por voz e linguagem natural
- Arrastar e soltar tarefas entre dias
- Visualizações de semana ou ano (apenas mês nesta versão)

## Notas de arquitetura

A interface gráfica é uma nova camada de apresentação, equivalente ao antigo MenuConsole, construída sobre a mesma Agenda. Cada tela em FXML tem uma classe controladora (controller) que reage aos eventos do usuário (cliques, seleções) e chama a Agenda. A aplicação gráfica tem seu próprio ponto de entrada, separado da classe Main do console.

A construção seguirá uma ordem incremental: primeiro a base do JavaFX funcionando, depois telas simples, depois a lista de tarefas, depois o calendário completo, depois a ligação com a Agenda e, por fim, o acabamento visual com CSS. Cada etapa entrega algo funcional antes da seguinte.