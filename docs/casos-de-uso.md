# Casos de uso — v0.1

## UC01 — Criar tarefa

**Ator:** Usuário

**Pré-condição:** O sistema está em execução e exibindo o menu principal.

**Fluxo principal:**
1. O usuário escolhe a opção de criar tarefa no menu.
2. O sistema solicita título, descrição, data e horário.
3. O usuário digita título, descrição, data e horário.
4. O sistema valida título, data e horário.
5. O sistema cria a tarefa e exibe mensagem de confirmação.
   
**Fluxos de exceção:**
- **E1:** Se título estiver em branco ou somente com espaços, o sistema informa que o título é obrigatório e solicita novamente.
- **E2:** Se a data ou horário estiverem em formato inválido (ex: '32/13/2026', '99:99'), o sistema informa que o formato é inválido e solicita novamente.
- **E3:** Se data e horário estiver no passado, o sistema informa que a data/horário não pode estar no passado e solicita novamente.


**Pós-condição:** A tarefa está registrada na agenda e disponível para listagem.

## UC02 — Listar tarefas do dia

**Ator:** Usuário

**Pré-condição:** O sistema está em execução e exibindo o menu principal.

**Fluxo principal:**
1. O usuário escolhe a opção listar tarefas do dia no menu.
2. O sistema solicita a data.
3. O usuário informa a data.
4. O sistema exibe a lista com as tarefas do dia ordenadas por horário, indicando as concluídas com a marcação [✓].

**Fluxos de exceção:**
- **E1:** Se a data estiver em formato inválido (ex: '32/13/2026'), o sistema informa que o formato é inválido e solicita novamente.
- **E2:** Se não existir tarefa nenhuma no dia, o sistema avisa o usuário que não existem tarefas para este dia e retorna para o menu principal.

**Pós-condição:** As tarefas do dia informado foram exibidas ao usuário.

## UC03 — Editar tarefa

**Ator:** Usuário

**Pré-condição:** O sistema está em execução e exibindo o menu principal.

**Fluxo principal:**
1. O usuário escolhe a opção editar tarefa no menu.
2. O sistema solicita a data.
3. O usuário informa a data.
4. O sistema exibe a lista numerada de tarefas do dia.
5. O sistema solicita o número da tarefa.
6. O usuário digita o número da tarefa.
7. A tarefa é exibida ao usuário com a frase "Deseja editar esta tarefa?" (Sim ou Não).
8. O usuário digita sim.
9. O sistema exibe 3 opções: 1.Editar título, 2.Editar descrição, 3.Editar data e horário.
10. O usuário escolhe uma das opções.
11. O sistema solicita o valor do campo escolhido.
12. O usuário informa o valor.
13. O sistema exibe a tarefa editada ao usuário com a frase "Deseja salvar a edição?" (Sim ou Não).
14. O usuário digita sim.
15. O sistema exibe a mensagem "Tarefa atualizada!"

**Fluxos alternativos:**
- **A1:** Se no passo 8 o usuário responder 'não', o sistema exibe a mensagem "Operação cancelada!" e retorna ao menu principal.
- **A2:** Se no passo 14 o usuário responder 'não' o sistema exibe a mensagem "Operação cancelada!" e retorna ao menu principal.

**Fluxos de exceção:**
- **E1:** Se a data estiver em formato inválido (ex: '32/13/2026'), o sistema informa que o formato é inválido e solicita novamente.
- **E2:** Se não existir tarefa nenhuma no dia, o sistema avisa o usuário que não existem tarefas para este dia e retorna para o menu principal.
- **E3:** Se número digitado for inválido, o sistema informa que o formato é inválido e solicita novamente.
- **E4:** Se número digitado não existir na lista, o sistema informa que o número informado não existe na lista e solicita novamente.
- **E5:** Se a resposta às confirmações (passos 8 e 14) não for 'sim' nem 'não', o sistema informa "Digite (Sim ou Não)".
- **E6:** Se título estiver em branco ou somente com espaços, o sistema informa que o título é obrigatório e solicita novamente.
- **E7:** Se a data ou horário estiverem em formato inválido (ex: '32/13/2026', '99:99'), o sistema informa que o formato é inválido e solicita novamente.
- **E8:** Se data e horário estiver no passado, o sistema informa que a data/horário não pode estar no passado e solicita novamente.

**Pós-condição:** A tarefa escolhida foi atualizada.

## UC04 — Remover tarefa

**Ator:** Usuário

**Pré-condição:** O sistema está em execução e exibindo o menu principal.

**Fluxo principal:**
1. O usuário escolhe a opção remover tarefa no menu.
2. O sistema solicita a data.
3. O usuário informa a data.
4. O sistema exibe a lista numerada de tarefas do dia.
5. O sistema solicita o número da tarefa.
6. O usuário digita o número da tarefa.
7. A tarefa é exibida ao usuário com a frase "Deseja remover esta tarefa?" (Sim ou Não).
8. O usuário digita sim.
9. O sistema exibe a mensagem "Tarefa removida com sucesso!"

**Fluxos alternativos:**
- **A1:** Se no passo 8 o usuário responder 'não', o sistema exibe a mensagem "Operação cancelada!" e retorna ao menu principal.

**Fluxos de exceção:**
- **E1:** Se a data estiver em formato inválido (ex: '32/13/2026'), o sistema informa que o formato é inválido e solicita novamente.
- **E2:** Se não existir tarefa nenhuma no dia, o sistema avisa o usuário que não existem tarefas para este dia e retorna para o menu principal.
- **E3:** Se número digitado for inválido, o sistema informa que o formato é inválido e solicita novamente.
- **E4:** Se número digitado não existir na lista, o sistema informa que o número informado não existe na lista e solicita novamente.
- **E5:** Se a resposta às confirmações (passo 8) não for 'sim' nem 'não', o sistema informa "Digite (Sim ou Não)".

**Pós-condição:** A tarefa escolhida foi removida.

## UC05 — Concluir tarefa

**Ator:** Usuário

**Pré-condição:** O sistema está em execução e exibindo o menu principal.

**Fluxo principal:**
1. O usuário escolhe a opção concluir tarefa no menu.
2. O sistema solicita a data.
3. O usuário informa a data.
4. O sistema exibe a lista numerada de tarefas do dia.
5. O sistema solicita o número da tarefa.
6. O usuário digita o número da tarefa.
7. A tarefa é exibida ao usuário com a frase "Deseja marcar tarefa como concluída?" (Sim ou Não).
8. O usuário digita sim.
9. O sistema exibe a mensagem "Tarefa concluída!"

**Fluxos alternativos:**
- **A1:** Se no passo 8 o usuário responder 'não', o sistema exibe a mensagem "Operação cancelada!" e retorna ao menu principal.

**Fluxos de exceção:**
- **E1:** Se a data estiver em formato inválido (ex: '32/13/2026'), o sistema informa que o formato é inválido e solicita novamente.
- **E2:** Se não existir tarefa nenhuma no dia, o sistema avisa o usuário que não existem tarefas para este dia e retorna para o menu principal.
- **E3:** Se número digitado for inválido, o sistema informa que o formato é inválido e solicita novamente.
- **E4:** Se número digitado não existir na lista, o sistema informa que o número informado não existe na lista e solicita novamente.
- **E5:** Se a resposta às confirmações (passo 8) não for 'sim' nem 'não', o sistema informa "Digite (Sim ou Não)".

**Pós-condição:** A tarefa escolhida foi marcada como concluída e passa a ser exibida com a marcação [✓] nas listagens.