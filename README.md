# Agenda Post-it

Aplicação de linha de comando para gerenciar compromissos do dia a dia, no estilo de post-its. Permite adicionar tarefas com data e horário, listar as tarefas de um dia, editar, marcar como concluídas e remover.

Este é um projeto de estudo em Java, desenvolvido com foco em orientação a objetos, validação de regras de negócio e testes automatizados.

## Versão

A v0.1 roda inteiramente no console. Versões futuras terão interface gráfica.

## Funcionalidades

- Adicionar tarefa com título, data, horário e descrição opcional
- Listar as tarefas de um dia, ordenadas por horário
- Editar título, descrição ou data e horário de uma tarefa
- Concluir uma tarefa
- Remover uma tarefa

As operações de editar, concluir e remover pedem confirmação antes de aplicar, e podem ser canceladas. As validações garantem que nenhuma tarefa exista em estado inválido:

- Título não pode ser vazio ou conter apenas espaços
- Data e horário não podem estar no passado
- Datas e horários em formato inválido são recusados, com nova solicitação

## Tecnologias

- Java 21
- Maven
- JUnit 5

## Estrutura do projeto

```
src/
  main/java/br/com/kevmartins/agendapostit/
    Main.java                     ponto de entrada
    dominio/                      regras de negócio
      Tarefa.java
      Agenda.java
      exceções customizadas
    menu/                         interface de console
      MenuConsole.java
  test/java/br/com/kevmartins/agendapostit/
    dominio/                      testes automatizados
      TarefaTest.java
      AgendaTest.java
docs/                             documentação do projeto
  requisitos.md
  casos-de-uso.md
  modelagem.md
```

## Como rodar

Pré-requisitos: Java 21 e Maven instalados.

Clonar o repositório:

```
git clone https://github.com/KevMartinsDev/agenda-postit.git
cd agenda-postit
```

Rodar os testes:

```
mvn test
```

Compilar e executar:

```
mvn compile
mvn exec:java -Dexec.mainClass="br.com.kevmartins.agendapostit.Main"
```

Também é possível abrir o projeto no IntelliJ IDEA e executar a classe `Main` diretamente.

## Arquitetura

O projeto separa as responsabilidades em camadas:

- O domínio (`Tarefa` e `Agenda`) concentra as regras de negócio e não conhece nada sobre a interface. A `Tarefa` oferece duas representações: uma resumida para as listagens e uma detalhada para quando é preciso ver todos os campos.
- O menu de console cuida apenas da conversa com o usuário: lê o que é digitado, converte texto em datas e horários, trata erros de formato, pede confirmações e exibe mensagens.

Essa separação permite que a interface gráfica das próximas versões reaproveite todo o domínio sem alterações.

## Limitações conhecidas

- Os dados existem apenas durante a execução; ao fechar o programa, as tarefas são perdidas. A persistência está planejada para a v0.2.