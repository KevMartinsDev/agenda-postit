# Agenda Post-it

Um gerenciador de tarefas no estilo dos post-its que a gente cola na mesa. A partir da v0.3, tem interface gráfica: um calendário onde você vê e gerencia suas tarefas por dia — adicionar, listar, editar, marcar como concluídas e remover. As tarefas ficam salvas num banco de dados, então continuam lá mesmo depois de fechar o programa.

É um projeto de estudo em Java. Fui construindo em versões, com foco em orientação a objetos, regras de negócio bem definidas, persistência em banco, testes automatizados e, na v0.3, interface gráfica.

## Versão atual

A v0.3 tem interface gráfica (JavaFX), com um calendário como tela principal. As tarefas são salvas num PostgreSQL. A versão de console (anterior) continua disponível no código.

## O que dá pra fazer

- Ver um calendário mensal, navegando entre os meses
- Clicar num dia para ver as tarefas dele
- Os dias que têm tarefa aparecem destacados no calendário
- Adicionar uma tarefa com título, data, horário e descrição opcional
- Editar, marcar como concluída e remover tarefas
- As tarefas ficam salvas no banco e não se perdem ao fechar o programa

As operações de editar, concluir e remover pedem confirmação. As validações garantem que nenhuma tarefa seja criada em estado inválido (título vazio ou data/horário no passado). Uma tarefa que já existe pode ser editada mesmo que o horário já tenha passado.

## Tecnologias

- Java 21
- JavaFX (interface gráfica)
- Maven
- PostgreSQL 16 (em Docker)
- JDBC
- JUnit 5, Mockito e Testcontainers (testes)

## O que você precisa ter instalado

- Java 21
- Maven
- Docker (para o banco de dados e para os testes de integração)

## Configuração

As credenciais do banco ficam num arquivo `.env`, que não vai pro repositório. Tem um `.env.example` mostrando o que preencher.

1. Copie o `.env.example` e renomeie a cópia pra `.env`.
2. Preencha os valores:

```
POSTGRES_DB=agenda
POSTGRES_USER=seu_usuario
POSTGRES_PASSWORD=sua_senha
```

## Como rodar

Você precisa ter Java 21, Maven e Docker instalados, com o Docker rodando.

Clone o repositório:

```
git clone https://github.com/KevMartinsDev/agenda-postit.git
cd agenda-postit
```

Suba o banco de dados:

```
docker compose up -d
```

Rode a interface gráfica:

```
mvn javafx:run
```

Rode os testes (precisa do Docker no ar):

```
mvn test
```

Se preferir usar a versão de console, dá pra rodar a classe `Main` em vez da interface gráfica.

Quando terminar, pra parar o banco:

```
docker compose stop
```

## Como o projeto está organizado

O código é separado em camadas, cada uma cuidando de uma coisa:

- **Domínio** (`Tarefa`, `Agenda`): as regras de negócio. Não sabem nada sobre banco nem sobre interface.
- **Persistência** (`TarefaRepository` e `TarefaRepositoryJdbc`): o acesso ao banco, via JDBC.
- **Interface gráfica** (`AppGrafica` e os controllers em `gui/`): as telas em JavaFX, que conversam com a Agenda.
- **Console** (`Main`, `MenuConsole`): a interface de texto da versão anterior, que continua funcionando sobre o mesmo domínio.

As camadas se conectam por injeção de dependência. Como o domínio depende de contratos (a interface `TarefaRepository`), e não de implementações, dá pra ter duas interfaces diferentes (gráfica e console) e trocar a tecnologia de persistência sem mexer nas regras de negócio.

Os detalhes de cada versão estão em `docs/` (requisitos e modelagem de cada uma).

## Testes

- **Domínio** (`Tarefa`): testes puros, sem dependências externas.
- **Agenda**: testada com Mockito, usando um repositório falso pra checar as regras sem tocar no banco.
- **TarefaRepositoryJdbc**: testado com Testcontainers, que sobe um PostgreSQL descartável e limpo a cada rodada (por isso precisa do Docker).

A interface gráfica é validada por teste manual.

## Próximos passos

- Post-its flutuantes que aparecem perto do horário da tarefa (v0.4)
- Comando por voz e linguagem natural, em versões futuras