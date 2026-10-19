# Modelagem — v0.2

Nesta versão eu adicionei a persistência das tarefas em um banco PostgreSQL. O domínio (Tarefa e Agenda) continua o mesmo; o que mudou foi que agora as tarefas são salvas no banco em vez de ficarem só na memória, e criei uma camada nova só pra cuidar disso.

## Como o sistema está dividido

Separei o código em três partes, cada uma com sua função:

- **MenuConsole**: conversa com o usuário. Não sabe nada sobre banco.
- **Tarefa e Agenda**: as regras de negócio. Conhecem só o contrato de persistência, não como ele funciona por dentro.
- **TarefaRepository e a implementação com JDBC**: a parte que fala com o banco.

Cada parte recebe o que precisa pela injeção de dependência, e é a classe Main que junta tudo.

## O que mudou na classe Tarefa

**Atributo novo:**

- `id` (Long): um número único que identifica a tarefa, gerado pelo próprio banco. Uma tarefa que ainda não foi salva tem id nulo; depois de salvar, ela recebe o id. Esse id é só interno, o usuário nunca vê.

**Métodos novos:**

- `getId` e `setId`: pra ler e escrever o id. O setId é usado quando o banco devolve o id gerado.
- `reconstruir(...)`: um método que remonta uma tarefa que veio do banco. Ele é diferente dos construtores normais porque não valida se a data está no passado. Faz sentido: uma tarefa que já existe pode ter ficado no passado com o tempo, e isso é normal. Ele só valida o título.

**Mudança nos setters:**

Os métodos `setData` e `setHorario` não checam mais se a data está no passado. Antes eles checavam, mas isso dava problema: uma tarefa que atrasou não podia mais ser editada. Agora essa checagem só acontece na criação de uma tarefa nova. O `setTitulo` continua validando o título, isso não mudou.

A ideia é separar dois momentos: **criar** uma tarefa nova (aí sim não pode ser no passado) e **editar** uma tarefa que já existe (aí pode, porque ela é real e pode ter atrasado).

## A interface TarefaRepository

É o contrato da persistência. Ela diz o que dá pra fazer, sem dizer como. Assim o domínio depende só dessa interface, e não fica preso ao PostgreSQL.

Os métodos são:

- `salvar(tarefa)`: insere uma tarefa nova e devolve ela já com o id do banco.
- `buscarTodas()`: traz todas as tarefas salvas.
- `atualizar(tarefa)`: grava as mudanças de uma tarefa que já existe.
- `remover(id)`: apaga a tarefa daquele id.

## A classe TarefaRepositoryJdbc

É a implementação do repositório usando JDBC pra conversar com o PostgreSQL.

Ela recebe a url, o usuário e a senha do banco pelo construtor, em vez de buscar isso sozinha. Assim dá pra usar ela em ambientes diferentes: no programa ela aponta pro banco de verdade, nos testes ela aponta pro banco descartável do Testcontainers.

Alguns pontos:

- Usa PreparedStatement em tudo, o que protege contra SQL Injection.
- No salvar, ela recupera o id que o banco gerou.
- Ao ler do banco, usa o `Tarefa.reconstruir`, pra não barrar tarefas que estão no passado.
- Quando dá algum erro de SQL, ela transforma isso numa RepositorioException com uma mensagem clara, pra não espalhar detalhes de banco pelo resto do código.

## A classe ConexaoBanco

Cuida de abrir a conexão com o banco, num lugar só.

Tem dois jeitos de pedir conexão:

- `obterConexao()`: usado pelo programa. Lê o usuário e a senha do arquivo `.env` e usa a url padrão do banco local.
- `obterConexao(url, usuario, senha)`: usado pelos testes, que passam os dados na mão pra apontar pro banco do Testcontainers.

## O que mudou na classe Agenda

A Agenda não guarda mais a lista de tarefas na memória. Agora ela recebe um TarefaRepository pelo construtor e repassa pra ele a parte de salvar e buscar. As regras de negócio continuam nela: filtrar por dia, ordenar por horário, validar o número da tarefa e lançar as exceções.

Como ficaram os métodos:

- `adicionar`: manda o repositório salvar.
- `listarPorDia`: pega todas as tarefas do banco e filtra as do dia, ordenando por horário.
- `concluir`: marca a tarefa como feita e manda o repositório atualizar.
- `remover`: manda o repositório remover, usando o id da tarefa.
- `atualizar(tarefa)`: método novo, usado quando o menu salva uma edição. Ele manda o repositório gravar as mudanças.

## Como tudo se junta (classe Main)

A Main é onde as peças se encaixam:

1. Lê as credenciais do `.env`.
2. Cria o TarefaRepositoryJdbc com os dados de conexão.
3. Cria a Agenda passando o repositório.
4. Cria o MenuConsole passando a Agenda.
5. Inicia o menu.

Só a Main conhece as classes concretas. As outras dependem só dos contratos. Se um dia eu quiser trocar o banco por outra coisa, mudo só a Main e escrevo uma nova implementação do TarefaRepository, sem mexer no resto.

## Como testei

- **Tarefa**: testes simples, só do domínio, sem depender de nada externo.
- **Agenda**: testada com Mockito. Troquei o repositório de verdade por um falso, pra testar as regras de negócio (ordenação, exceções) e conferir se ela chama salvar, atualizar e remover na hora certa, tudo sem tocar no banco.
- **TarefaRepositoryJdbc**: testado com Testcontainers, que sobe um PostgreSQL descartável e limpo a cada rodada de testes. Isso garante que cada teste começa do zero. Precisa do Docker rodando.

## Sobre o banco

- PostgreSQL 16, rodando num container Docker descrito no `docker-compose.yml`.
- A tabela `tarefas` tem as colunas: id (chave primária, gerada pelo banco), titulo, data, horario, descricao e concluido.
- As credenciais ficam no `.env`, que não vai pro Git. Um `.env.example` mostra quais variáveis são necessárias.