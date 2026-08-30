# Requisitos — v0.2

## Objetivo

Adicionar persistência à agenda. As tarefas passam a ser salvas em um banco de dados PostgreSQL, de modo que continuem disponíveis entre execuções do programa. A interface permanece em console; a mudança é interna, na forma como os dados são guardados.

## Contexto da mudança

Na v0.1, as tarefas existiam apenas em memória (uma lista dentro da Agenda) e eram perdidas ao encerrar o programa. A v0.2 introduz um banco de dados PostgreSQL, executado em um container Docker, e uma camada de acesso a dados, sem alterar as regras de negócio já existentes.

## Requisitos funcionais

- **RF09** — O sistema deve salvar cada tarefa criada no banco de dados, de forma que ela persista após o encerramento do programa.
- **RF10** — O sistema deve carregar as tarefas salvas ao iniciar, deixando-as disponíveis para listagem e edição.
- **RF11** — O sistema deve refletir no banco de dados as alterações feitas em uma tarefa (edição de título, descrição, data, horário ou conclusão).
- **RF12** — O sistema deve remover do banco de dados a tarefa apagada pelo usuário.
- **RF13** — Cada tarefa deve possuir um identificador único, gerado automaticamente pelo banco, que a distingue das demais. Esse identificador é de uso interno e não é exibido ao usuário.

## Requisitos não funcionais

- **RNF03** — O banco de dados (PostgreSQL) deve ser executado em um container Docker, subido a partir de um arquivo de configuração incluído no projeto, sem exigir instalação manual do banco na máquina.
- **RNF04** — O acesso ao banco deve ser feito por uma camada dedicada (repositório), isolando o restante do sistema dos detalhes de persistência.
- **RNF05** — As regras de negócio existentes (validações de título e de data/horário no passado) devem permanecer no domínio, sem serem afetadas pela introdução do banco.
- **RNF06** — Uma falha de acesso ao banco não deve encerrar o programa de forma abrupta; o sistema deve informar o erro ao usuário.
- **RNF07** — As credenciais de acesso ao banco não devem ser gravadas no código nem no controle de versão. Devem ficar em variáveis de ambiente, com um arquivo de exemplo versionado documentando as variáveis esperadas.

## Fora do escopo desta versão

- Interface gráfica (JavaFX)
- Post-its flutuantes na área de trabalho
- Notificações/alertas por horário
- Comando por voz e linguagem natural

## Notas de arquitetura

A persistência será introduzida por trás de uma interface (`TarefaRepository`), que define as operações de salvar, buscar e remover sem revelar como isso é feito. A implementação desta versão usará PostgreSQL via JDBC. Essa separação permite que versões futuras substituam a tecnologia de persistência sem alterar o domínio nem a interface de usuário.

O banco roda em um container Docker descrito em `docker-compose.yml`. As credenciais ficam em um arquivo `.env` (fora do controle de versão), e um `.env.example` versionado documenta quais variáveis o projeto espera, permitindo que outra pessoa clone o repositório e suba o ambiente com um único comando.