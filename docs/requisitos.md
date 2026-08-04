# Requisitos — v0.1

## Objetivo

Versão inicial da agenda em console, permitindo gerenciar tarefas com data e horário. Os dados existem apenas durante a execução (sem persistência) e não há interface gráfica.

## Requisitos funcionais

- **RF01** — O sistema deve permitir criar uma tarefa com título, descrição, data e horário.
- **RF02** — O sistema deve permitir listar as tarefas de uma data específica, ordenadas por horário.
- **RF03** — O sistema deve permitir editar o título, a descrição, a data e o horário de uma tarefa existente.
- **RF04** — O sistema deve permitir apagar uma tarefa existente.
- **RF05** — O sistema deve permitir marcar tarefas concluídas.
- **RF06** — O sistema deve impedir a criação de tarefas com data/horário no passado.
- **RF07** — O sistema deve impedir a criação de tarefas sem títulos ou com títulos em branco.
- **RF08** — O sistema deve rejeitar datas e horários em formato inválido.

## Requisitos não funcionais

- **RNF01** — O sistema deve funcionar via console (linha de comando).
- **RNF02** — O sistema deve continuar a rodar caso o usuário digite algo inválido.

## Fora do escopo desta versão

- Interface gráfica (JavaFX)
- Post-its flutuantes na área de trabalho
- Notificações/alertas por horário
- Persistência de dados (salvar/carregar)
- Animações e transições (virada de página, hover)
- Aviso de tarefas com mesmo horário 