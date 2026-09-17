# Relatório Técnico e Desafio Prático: Análise, Correção e Melhoria do Sistema da Loja[cite: 1]

## Identificação Acadêmica
* **Instituição de Ensino:** Centro Universitário do Planalto Central Apparecido dos Santos (UNICEPLAC)[cite: 2]
* **Curso:** Engenharia de Software[cite: 2]
* **Disciplina:** Elicitação e Programação[cite: 2]
* **Orientador:** Profº Hudson Neves[cite: 2]
* **Desenvolvedora:** Brenda Sousa Costa[cite: 2]

---

## Descrição
Este projeto consiste em um relatório técnico de reformulação de um software de gerenciamento de loja desenvolvido em Java[cite: 1]. A atividade exigiu a execução, análise e correção do sistema original (baseado na classe `MenuLoja` e `Produto`), que apresentava falhas lógicas e de validação[cite: 1]. Além disso, o documento engloba a documentação do "Desafio 2", que aplica estruturas de repetição para automatizar o cadastro e a contabilidade de múltiplos produtos[cite: 1]. 

A proposta desta atividade foi exercitar o raciocínio crítico sobre fluxos de código, validar entradas rigorosamente utilizando estruturas condicionais e de repetição, e organizar a arquitetura de um sistema para evitar dados fantasmas e falhas de domínio[cite: 1].

## Objetivos
**Objetivo geral:** Transformar um código com falhas de validação em um sistema de console simples, robusto e confiável para o gerenciamento de entrada e saída de produtos[cite: 1].
**Problema que resolve:** O sistema original permitia cadastros sem sentido (como estoque e preço negativos, além de nomes vazios) e exibia dados inconsistentes antes mesmo de o usuário cadastrar um item[cite: 1]. O novo sistema bloqueia essas falhas e garante a integridade dos dados da loja[cite: 1].

---

## Arquitetura da Solução e Limpeza de Código
Durante as etapas iniciais de análise (Etapas 1 e 2), identificou-se que além do sistema principal (`MenuLoja`), o pacote continha duas classes soltas: `Principal.java` e `VerificarEstoque.java`[cite: 1]. Cada uma possuía seu próprio método `main()` e instâncias isoladas, sem nenhuma ligação com o fluxo principal[cite: 1]. 

As funcionalidades úteis dessas classes (consultar produto e verificar estoque) foram migradas, corrigidas e centralizadas na classe principal (`MenuLoja`)[cite: 1]. As classes avulsas foram descartadas na versão final para garantir uma arquitetura coesa e centralizada[cite: 1].

---

## Etapas 3 e 4: Problemas Encontrados e Classificação
O sistema original foi submetido a testes de fronteira, incluindo valores negativos e campos em branco[cite: 1]. Os resultados e suas classificações estão registrados abaixo:

| Teste Realizado | Comportamento Encontrado | Comportamento Esperado | Classificação do Problema |
| :--- | :--- | :--- | :--- |
| Cadastrar produto com preço negativo (-10) | Sistema aceitou normalmente e armazenou preco = -10.0.[cite: 1] | Sistema deveria rejeitar e pedir um novo valor.[cite: 1] | Falta de validação[cite: 1] |
| Cadastrar produto com quantidade negativa (-5) | Sistema aceitou normalmente e armazenou quantidade = -5[cite: 1] | Sistema deveria rejeitar e pedir um novo valor.[cite: 1] | Falta de validação[cite: 1] |
| Consultar produto antes de qualquer cadastro | Exibiu "Nome: null", "Preço: R$ 0.0", "Quantidade: 0"[cite: 1] | Deveria avisar que nenhum produto foi cadastrado.[cite: 1] | Falta de tratamento de situação[cite: 1] |
| Cadastrar produto deixando o nome em branco | Sistema aceitou string vazia como nome.[cite: 1] | Sistema deveria rejeitar nome vazio.[cite: 1] | Falta de validação[cite: 1] |
| Informar texto ("abc") em campo de preço decimal | Encerrou abruptamente com `java.util.InputMismatchException` (crash).[cite: 1] | Idealmente avisaria que o valor é inválido sem travar.[cite: 1] | Falta de tratamento de situação[cite: 1] |
| Escolher uma opção inexistente no menu (ex.: 9) | Sistema exibiu "Opção inválida!" (bloco default).[cite: 1] | Mesmo comportamento já estava correto.[cite: 1] | - |
| Vender quantidade maior que estoque disponível | Exibiu "Estoque insuficiente!" e não alterou estoque.[cite: 1] | Mesmo comportamento já estava correto.[cite: 1] | - |
| Registrar entrada de mercadoria (reposição) | Funcionalidade não existia no sistema original.[cite: 1] | Deveria permitir aumentar o estoque controladamente.[cite: 1] | Funcionalidade incompleta[cite: 1] |

---

## Etapas 5 a 8: Correções, Validações e Melhorias Implementadas

Para solucionar as falhas, foram aplicadas as seguintes lógicas estruturais, utilizando estritamente `while`, `if/else if/else` e `switch/case`[cite: 1]:

**Regras de Validação:**
* **Nome em branco:** Um laço `while` repete a pergunta enquanto a string digitada for vazia[cite: 1].
* **Preço negativo ou zero:** Um laço `while` exige a redigitação enquanto o valor for <= 0[cite: 1].
* **Quantidade negativa:** Um laço `while` impede valores menores que 0, mas permite iniciar zerado[cite: 1].

**Controle de Estado:**
* Criação da variável booleana `produto.cadastrado`[cite: 1]. Todas as opções secundárias testam `if (produto.cadastrado == false)` para impedir a exibição de "dados fantasmas" antes do cadastro oficial[cite: 1].

**Novas Funcionalidades (Melhorias):**
* **Opção 4 (Reposição):** Permite adicionar itens ao estoque com validação `while` para aceitar apenas valores maiores que zero[cite: 1].
* **Confirmação de Operação (A):** Exibe resumo antes da venda e pede confirmação (S/N)[cite: 1].
* **Alerta de Estoque (B):** Emissão de aviso se o estoque cair para 5 ou menos após uma venda[cite: 1].
* **Cálculo de Valores (C):** Exibe o valor total acumulado (preço x quantidade) na consulta[cite: 1].

### Novos Testes Após Correções

| Problema Original | Correção Realizada | Resultado Após Novo Teste |
| :--- | :--- | :--- |
| Preço negativo/zero era aceito | Laço `while` repete a leitura enquanto preco <= 0.[cite: 1] | Valor -10 foi rejeitado; 15.90 foi aceito com sucesso.[cite: 1] |
| Quantidade negativa era aceita | Laço `while` repete a leitura enquanto quantidade < 0.[cite: 1] | Valor -5 foi rejeitado; 0 e 8 foram aceitos normalmente.[cite: 1] |
| Nome vazio era aceito | Laço `while` repete a leitura enquanto nome.equals("").[cite: 1] | Enter em branco foi rejeitado; "Produto Teste" foi aceito.[cite: 1] |
| Dados "fantasmas" | Verificação de `produto.cadastrado == false`.[cite: 1] | Opções 2, 3, 4 e 5 bloqueadas com aviso amigável antes do cadastro.[cite: 1] |
| Sem funcionalidade de reposição | Implementada Opção 4 com validação positiva via `while`.[cite: 1] | Entrada de 0 rejeitada; entrada de 15 somou corretamente ao estoque.[cite: 1] |

---

## Desafio de Investigação (Crash do Sistema)
* **Cenário:** Entrada da string "abc" no campo de preço (`double`)[cite: 1].
* **O que aconteceu?** O programa foi encerrado pela JVM com a mensagem `Exception in thread "main" java.util.InputMismatchException`[cite: 1].
* **Por que?** O método `Scanner.nextDouble()` tentou converter texto em número sem sucesso[cite: 1].
* **Como tratar futuramente?** Envolvendo a leitura em um bloco `try/catch` para capturar a exceção sem encerrar a aplicação[cite: 1].

---

## Questões para Reflexão - Sistema da Loja (Desafio 1)

1. **Um programa que executa sem apresentar erro necessariamente está correto?** Não. O sistema original compilava e rodava, mas aceitava dados semanticamente incorretos (estoque negativo, preços zerados). A ausência de falhas de compilação não garante a correção das regras de negócio[cite: 1].
2. **Diferença entre erro de sintaxe e erro de lógica?** Erros de sintaxe violam a gramática da linguagem e impedem a compilação. Erros de lógica permitem compilação e execução, mas produzem resultados incoerentes[cite: 1].
3. **Por que validar dados?** Para garantir a integridade dos dados e do fluxo da aplicação, impedindo cálculos falhos ou quebra do sistema[cite: 1].
4. **Problema mais importante?** O crash decorrente de entrada de texto em campos numéricos, que interrompia totalmente o serviço[cite: 1].
5. **Correção mais difícil?** O controle de estado global (`produto.cadastrado`), demandando revisão de todos os fluxos para não quebrar a navegação[cite: 1].
6. **Onde utilizou if/else if/else?** Nas validações do cadastro, verificação de elegibilidade de vendas, comparação de estoque e respostas S/N[cite: 1].
7. **Função do while?** Manter o loop do menu ativo e insistir na leitura de dados válidos nos cadastros[cite: 1].
8. **Função do switch/case?** Mapear a escolha do usuário diretamente para a funcionalidade correspondente[cite: 1].
9. **Por que testar valores atípicos?** Porque as falhas de validação ocorrem predominantemente em cenários atípicos (testes de borda)[cite: 1].
10. **Melhorias e motivos?** Confirmação de vendas, alerta de estoque baixo, cálculo de saldo, bloqueio pré-cadastro e reposição. Escolhidas para tornar o software completo e seguro[cite: 1].
11. **O que ainda pode ser melhorado?** Tratamento de exceções com `try/catch`, suporte a múltiplos produtos (coleções) e persistência em banco de dados[cite: 1].

---

## Desafio 2: Cadastro de Produto - Documentação

A segunda etapa da atividade focou no uso de estruturas de repetição e acumuladores para o processamento em lote de produtos.

### Registro dos Testes

| Teste | Entrada | Resultado esperado | Resultado obtido | Correto? |
| :--- | :--- | :--- | :--- | :--- |
| Estoque vazio | 0 | Sem estoque | Situação: Sem estoque[cite: 1] | Sim[cite: 1] |
| Estoque mínimo | 1 | Estoque baixo | Situação: Estoque baixo[cite: 1] | Sim[cite: 1] |
| Limite estoque baixo | 19 | Estoque baixo | Situação: Estoque baixo[cite: 1] | Sim[cite: 1] |
| Limite estoque suficiente | 20 | Estoque suficiente | Situação: Estoque suficiente[cite: 1] | Sim[cite: 1] |
| Preço negativo | -100 | Não aceitar | Laço bloqueia e pede novo valor[cite: 1] | Sim[cite: 1] |
| Quantidade negativa | -2 | Não aceitar | Laço bloqueia e pede novo valor[cite: 1] | Sim[cite: 1] |
| Qtd. de produtos negativa | -2 | Não aceitar | Encerra o programa imediatamente[cite: 1] | Sim[cite: 1] |

### Questões para Reflexão Resolvidas (Desafio 2)

1. **Por que um laço de repetição foi necessário?** Para evitar a duplicação de código. Ele permite solicitar dados e calcular para vários produtos automatizadamente[cite: 1].
2. **Por que o `for` é adequado aqui?** Projetado para iterações finitas. Como o usuário informa a quantidade inicial, o `for` estabelece início, parada e incremento na mesma linha[cite: 1].
3. **O que ocorreria sem o incremento do `for`?** O programa entraria em um *loop infinito*, travando a execução[cite: 1].
4. **Função do contador:** Registrar a frequência de eventos. No código, variáveis contam produtos em cada categoria de estoque[cite: 1].
5. **Função do acumulador:** Somar e armazenar valores progressivamente (ex: `valorTotalEstoque`)[cite: 1].
6. **Diferença entre os dois:** Contador aumenta constante (geralmente +1); acumulador recebe incrementos variáveis (como o preço)[cite: 1].
7. **Por que contadores começam em 0?** Pois antes de iniciar, nenhum evento que se deseja contar ocorreu ainda[cite: 1].
8. **Por que o valor total começa em 0?** É o elemento neutro da adição; qualquer outro valor corromperia o cálculo[cite: 1].
9. **Por que o relatório fica fora do laço?** Se estivesse dentro, exibiria os totais a cada repetição, em vez do consolidado final[cite: 1].
10. **`if/else` dentro do `for`?** Foi necessário para classificar individualmente a quantidade em estoque e avaliar o `maiorValor`[cite: 1].
11. **Melhoria implementada?** Validações com `while` que impedem entradas negativas de preço e quantidade (Testes Inválidos)[cite: 1].
12. **Maior dificuldade?** Gerenciar o buffer do `Scanner`. Foi preciso um `nextLine()` vazio após `nextInt()` para não pular a leitura do "Nome"[cite: 1].

---

## Licença
Atividade acadêmico desenvolvido para fins educacionais na disciplina de Elicitação e Programação — UNICEPLAC[cite: 2]. Uso restrito aos fins do curso[cite: 2].
