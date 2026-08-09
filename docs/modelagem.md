# Modelagem — v0.1

## Classe: Tarefa

**Papel:** Representa um compromisso do usuário com data e horário.
Garante que nenhuma tarefa exista em estado inválido.

**Atributos:**
- titulo : String — nome curto do compromisso. Obrigatório (RF07).
- data : LocalDate — dia do compromisso. Uso LocalDate em vez de
  String porque permite comparar com a data atual para validar
  "no passado" (RF06) e ordenar cronologicamente (RF02).
- descricao : String — detalhamento opcional do compromisso.
- horario : LocalTime — horário do compromisso. Uso LocalTime em vez de
  String porque permite comparar com o horário atual para validar
  "no passado" (RF06) e ordenar cronologicamente (RF02).
- concluido : boolean — indica se a tarefa foi concluída (UC05). Nasce como false.

**Métodos:**

- isConcluido() : boolean — retorna se a tarefa está concluída.
- getTitulo() : String — retorna o título.
- getData() : LocalDate — retorna a data.
- getHorario() : LocalTime — retorna o horário.
- getDescricao() : String — retorna a descrição.
- setTitulo(novoTitulo : String) : void — Altera o valor do título. Valida: recusa em branco ou só espaços (RF07).
- setData(novaData : LocalDate) : void — Altera o valor da data. Valida: recusa data/horário resultante no passado (RF06).
- setHorario(novoHorario : LocalTime) : void — Altera o valor do horário. Valida: recusa data/horário resultante no passado (RF06).
- setDescricao(novaDescricao : String) : void — Altera o valor da descrição. Sem validação: descrição é opcional, pode até ficar vazia. Nada a proteger.
- toString() : String — define como a tarefa é exibida nas listagens (formato com horário, título e marcação [✓] quando concluída)
- concluir() : void — marca a tarefa como concluída (UC05).

**Nota sobre a validação de passado (RF06):** a verificação considera
a combinação data + horário, não cada campo isolado. Ex: alterar o
horário para 08:00 em uma tarefa de hoje, sendo 14:00 agora, deve
ser recusado — a data sozinha é válida, mas o momento combinado já passou.

## Classe: Agenda

**Papel:** Agenda faz o gerenciamento de todas as tarefas (post-its).

**Atributos:**
- tarefas : List<Tarefa> — Guarda todas as tarefas do usuário com data e horário.

**Métodos:**
- adicionar(tarefa : Tarefa) : void — Adiciona uma tarefa.
- listarPorDia(data : LocalDate) : List<Tarefa> — Lista as tarefas do dia informado, ordenadas por horário (RF02). Se não houver tarefas na data, lança exceção (E2 dos UCs).
- concluir(data : LocalDate, numero : int) : void — Marca uma tarefa como concluída.
- remover(data : LocalDate, numero : int) : void — Remove uma tarefa.

**Nota sobre o método editar:** a edição é feita chamando os setters da própria Tarefa,
obtida via listarPorDia. As validações já moram nos setters (RF06/RF07).