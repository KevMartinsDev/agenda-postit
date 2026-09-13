# Agenda Post-it

Um gerenciador de tarefas pra linha de comando, no estilo dos post-its que a gente cola na mesa. Dá pra adicionar tarefas com data e horário, listar as do dia, editar, marcar como concluídas e remover. A partir da v0.2, as tarefas ficam salvas num banco de dados, então elas continuam lá mesmo depois de fechar o programa.

É um projeto de estudo em Java. Fui construindo com foco em orientação a objetos, regras de negócio bem definidas, persistência em banco e testes automatizados.

## Versão atual

A v0.2 roda no console e salva as tarefas num PostgreSQL. A ideia é, mais pra frente, ter uma interface gráfica.

## O que dá pra fazer

- Adicionar uma tarefa com título, data, horário e uma descrição opcional
- Listar as tarefas de um dia, já ordenadas por horário
- Editar o título, a descrição ou a data e horário de uma tarefa
- Marcar uma tarefa como concluída
- Remover uma tarefa
- As tarefas ficam salvas no banco e não se perdem ao fechar o programa

Editar, concluir e remover pedem uma confirmação antes, e dá pra cancelar. Na hora de criar uma tarefa, o programa não deixa:

- Título vazio ou só com espaços
- Data e horário no passado
- Data ou horário digitados num formato errado (aí ele pede de novo)

Uma tarefa que já existe pode ser editada mesmo que o horário dela já tenha passado — afinal, uma pendência atrasada ainda é uma tarefa válida.

## Tecnologias

- Java 21
- Maven
- PostgreSQL 16 (rodando em Docker)
- JDBC
- JUnit 5, Mockito e Testcontainers pros testes

## O que você precisa ter instalado

- Java 21
- Maven
- Docker (pra subir o banco e pra rodar os testes de integração)

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

Você precisa ter o Java 21, o Maven e o Docker instalados, com o Docker rodando.

Clone o repositório:

```
git clone https://github.com/KevMartinsDev/agenda-postit.git
cd agenda-postit
```

Suba o banco de dados:

```
docker compose up -d
```

Rode os testes (precisa do Docker no ar):

```
mvn test
```

Rode o programa:

```
mvn compile
mvn exec:java -Dexec.mainClass="br.com.kevmartins.agendapostit.Main"
```

Se preferir, dá pra abrir o projeto no IntelliJ e rodar a classe `Main` direto por lá.

Quando terminar, pra parar o banco:

```
docker compose stop
```

## Como o projeto está organizado

Dividi o código em três camadas, cada uma cuidando de uma coisa:

- **MenuConsole**: fala com o usuário. Não sabe nada sobre banco.
- **Tarefa e Agenda**: as regras de negócio. Dependem de um contrato de persistência, não do banco em si.
- **TarefaRepository e TarefaRepositoryJdbc**: a parte que acessa o banco, via JDBC.

As camadas se conectam por injeção de dependência: cada uma recebe o que precisa pelo construtor, e é a classe `Main` que monta tudo. Como o domínio depende só da interface `TarefaRepository`, e não da implementação, dá pra trocar o banco por outra coisa sem mexer nas regras de negócio nem na tela.

Os detalhes da modelagem estão em `docs/modelagem-v0.2.md`.

Estrutura das pastas:

```
src/
  main/java/br/com/kevmartins/agendapostit/
    Main.java                     ponto de entrada, monta as peças
    dominio/                      regras de negócio
      Tarefa.java
      Agenda.java
      DataNoPassadoException.java
      DiaSemTarefasException.java
      NumeroListaInexistenteException.java
      TituloInvalidoException.java
    menu/                         interface de console
      MenuConsole.java
    persistencia/                 acesso ao banco
      ConexaoBanco.java
      RepositorioException.java
      TarefaRepository.java
      TarefaRepositoryJdbc.java
  test/java/br/com/kevmartins/agendapostit/
    dominio/
      AgendaTest.java
      TarefaTest.java
    persistencia/
      TarefaRepositoryJdbcTest.java
docs/                             documentação
  requisitos.md
  requisitos-v0.2.md
  casos-de-uso.md
  modelagem.md
  modelagem-v0.2.md
docker-compose.yml                sobe o PostgreSQL
.env.example                      modelo das variaveis de ambiente
```

## Testes

- **Tarefa**: testes simples, só do domínio.
- **Agenda**: testada com Mockito, usando um repositório falso pra checar as regras sem tocar no banco.
- **TarefaRepositoryJdbc**: testado com Testcontainers, que sobe um PostgreSQL descartável e limpo a cada rodada (por isso precisa do Docker).

## Ainda não tem

- Interface gráfica — por enquanto é só console. É o próximo grande passo.