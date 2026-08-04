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


  **Pós-condição:** A tarefa está registrada na agenda e disponível para listagem