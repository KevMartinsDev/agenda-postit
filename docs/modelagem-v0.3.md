# Modelagem — v0.3

Nesta versão eu troquei a interface de console por uma interface gráfica, feita com JavaFX e FXML. O domínio (Tarefa, Agenda) e a persistência (TarefaRepository) continuam os mesmos da v0.2 — a interface gráfica é só uma nova "cara" por cima do que já existia. Toda a arquitetura em camadas que montei antes permitiu isso: a GUI conversa com a Agenda do mesmo jeito que o console conversava.

## Como a interface gráfica se organiza

A interface usa dois tipos de arquivo que trabalham juntos:

- **Arquivos FXML**: descrevem as telas (quais componentes, onde ficam). É parecido com HTML, separado do código Java.
- **Controllers**: classes Java que reagem ao que o usuário faz na tela (cliques, seleções) e chamam a Agenda.

A ideia é separar a aparência (FXML) do comportamento (controller), do mesmo jeito que separei domínio de interface antes.

## As telas e seus controllers

**Tela do calendário (tela-calendario.fxml + TelaCalendarioController)**

É a tela principal. Mostra um calendário mensal onde dá pra navegar entre meses, e ao clicar num dia aparecem as tarefas dele num painel ao lado. Os dias que têm tarefa ficam destacados.

O controller monta o calendário em código (cada dia é um botão numa grade), porque a quantidade de dias muda a cada mês. Ele reage aos cliques nos dias, abre o formulário pra criar/editar, e chama a Agenda pra concluir e remover.

**Tela do formulário (tela-formulario.fxml + TelaFormularioController)**

Um formulário em janela separada (modal) pra criar ou editar uma tarefa. O mesmo formulário serve pros dois casos: se ele recebe uma tarefa, está editando (vem preenchido); se não recebe, está criando. As validações de negócio (título, data no passado) continuam na Tarefa — o formulário só mostra os erros numa janelinha.

## A classe AppGrafica

É o ponto de entrada da interface gráfica, equivalente à Main do console. Ela é o composition root da GUI: lê as credenciais do banco, cria o repositório, cria a Agenda e injeta ela no controller da tela. Também carrega o CSS e define o ícone da janela.

Repare que a AppGrafica e a Main coexistem — são dois jeitos de iniciar o programa (gráfico ou console), ambos sobre o mesmo domínio.

## Como a GUI conversa com a Agenda

Aqui apareceu um detalhe do JavaFX: ele cria os controllers sozinho, então não dá pra passar a Agenda pelo construtor como fiz no resto do projeto. A solução foi injetar a Agenda depois que a tela carrega, por um método setAgenda. A AppGrafica carrega o FXML, pega o controller e injeta a Agenda nele.

As regras de negócio continuam todas na Agenda. O controller só chama agenda.listarPorDia, agenda.adicionar, agenda.concluir, etc. — igual o menu de console fazia. Nenhuma regra foi parar na interface.

## O que mudou na Agenda

Adicionei um método novo: diasComTarefa(mes). Ele retorna o conjunto de dias de um mês que têm pelo menos uma tarefa. O calendário usa isso pra saber quais dias destacar. É uma consulta só (busca todas e filtra por mês), em vez de perguntar dia por dia.

## O visual (CSS)

A aparência fica num arquivo estilos.css, separada do código — mesma ideia do FXML. Lá ficam as cores (tema escuro), os cantos arredondados, o destaque do dia de hoje e dos dias com tarefa, e as cores dos botões de ação.

Os ícones dos botões são desenhados como SVGPath (vetores), a partir dos caminhos dos SVGs, com uma classe utilitária (Icones) que monta cada um. A ilustração do estado vazio é uma imagem PNG.

## Responsividade

A interface se adapta ao tamanho da janela usando os recursos de layout do JavaFX (VBox, HBox, crescimento proporcional com hgrow/vgrow, e limites de largura). Há um breakpoint: quando a janela fica pequena, o painel lateral encolhe; quando fica grande, ele cresce — ajustado por um listener que observa a largura da janela.

## Como testei

Os testes automatizados continuam sendo do domínio e da persistência (Tarefa, Agenda com Mockito, repositório com Testcontainers), como na v0.2 — incluindo testes novos pro método diasComTarefa. A interface gráfica em si foi validada por teste manual: todos os fluxos (criar, listar, editar, concluir, remover), o estado vazio, a persistência entre execuções e o redimensionamento da janela.