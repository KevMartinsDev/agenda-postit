# Requisitos — v0.4

## Objetivo

Adicionar lembretes automáticos por horário. Enquanto o programa está em execução, ele vigia os horários das tarefas e, quando uma tarefa está prestes a acontecer, exibe um post-it flutuante no canto da tela para lembrar o usuário. O domínio, a persistência e a interface gráfica da v0.3 continuam inalterados; esta versão adiciona um mecanismo de vigilância em segundo plano e a exibição dos lembretes.

## Contexto da mudança

Até a v0.3, o usuário só via suas tarefas abrindo o calendário e clicando num dia. A v0.4 torna o programa ativo: ele avisa sozinho quando um compromisso se aproxima, sem o usuário precisar consultar. Para isso, introduz um componente que roda em segundo plano (concorrência) verificando os horários, e janelas flutuantes de lembrete.

## Requisitos funcionais

- **RF23** — Enquanto o programa está em execução, o sistema deve verificar periodicamente os horários das tarefas do dia e identificar as que estão próximas do horário marcado.
- **RF24** — O sistema deve exibir um lembrete (post-it flutuante) 10 minutos antes do horário de uma tarefa.
- **RF25** — O sistema deve exibir um lembrete (post-it flutuante) no horário exato de uma tarefa.
- **RF26** — Cada tarefa deve gerar cada lembrete apenas uma vez, sem repetir o mesmo aviso.
- **RF27** — O post-it deve exibir as informações da tarefa (título, horário e, se houver, descrição).
- **RF28** — O post-it deve aparecer no canto inferior direito da tela.
- **RF29** — O post-it deve desaparecer sozinho após alguns segundos e também oferecer um botão para o usuário fechá-lo manualmente.
- **RF30** — Tarefas já concluídas não devem gerar lembretes.

## Requisitos não funcionais

- **RNF12** — A verificação de horários deve rodar em segundo plano, sem travar nem atrapalhar a interface gráfica.
- **RNF13** — A atualização da interface (exibir o post-it) deve ser feita de forma segura a partir do mecanismo de segundo plano, respeitando as regras de threads do JavaFX.
- **RNF14** — O mecanismo de vigilância deve funcionar com o programa aberto, inclusive minimizado. Não há vigilância com o programa fechado.
- **RNF15** — Ao encerrar o programa, o mecanismo de segundo plano deve ser interrompido de forma limpa, sem deixar processos pendentes.

## Fora do escopo desta versão

- Funcionamento com o programa fechado (como um serviço do sistema operacional)
- Som/alerta sonoro no lembrete
- Lembretes configuráveis pelo usuário (tempo de antecedência ajustável)
- Adiar (snooze) o lembrete
- Comando por voz e linguagem natural

## Notas de arquitetura

A vigilância será feita por um componente que roda em segundo plano (uma thread agendada), verificando a cada intervalo quais tarefas do dia estão a 10 minutos ou no horário. Esse componente consulta a Agenda (o mesmo domínio já existente), sem acessar o banco diretamente.

Como no JavaFX apenas a thread da interface pode alterar a tela, o componente de segundo plano não cria janelas diretamente: ele sinaliza à thread da interface que um post-it deve aparecer, respeitando a regra de threads do JavaFX.

O post-it é uma janela sem borda (undecorated), posicionada no canto inferior direito, que se fecha sozinha por um temporizador e também por um botão. Cada lembrete é disparado uma única vez por tarefa, controlado por um registro dos lembretes já exibidos.