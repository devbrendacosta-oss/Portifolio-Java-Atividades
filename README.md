# Relatório Técnico e Desafio Prático: Análise, Correção e Melhoria do Sistema da Loja

## Identificação Acadêmica

- **Instituição de Ensino:** Centro Universitário do Planalto Central Apparecido dos Santos (UNICEPLAC)
- **Curso:** Engenharia de Software
- **Disciplina:** Elicitação e Programação
- **Orientador:** Profº Hudson Neves
- **Desenvolvedora:** Brenda Sousa Costa

---

## Descrição

Este projeto consiste em um relatório técnico de reformulação de um software de gerenciamento de loja desenvolvido em Java. A atividade exigiu a execução, análise e correção do sistema original (baseado na classe `MenuLoja` e `Produto`), que apresentava falhas lógicas e de validação. Além disso, o documento engloba a documentação do **"Desafio 2"**, que aplica estruturas de repetição para automatizar o cadastro e a contabilidade de múltiplos produtos.

A proposta desta atividade foi exercitar o raciocínio crítico sobre fluxos de código, validar entradas rigorosamente utilizando estruturas condicionais e de repetição, e organizar a arquitetura de um sistema para evitar dados fantasmas e falhas de domínio.

---

## Objetivos

**Objetivo geral:** Transformar um código com falhas de validação em um sistema de console simples, robusto e confiável para o gerenciamento de entrada e saída de produtos.

**Problema que resolve:** O sistema original permitia cadastros sem sentido, como estoque e preço negativos, além de nomes vazios, e exibia dados inconsistentes antes mesmo de o usuário cadastrar um item. O novo sistema bloqueia essas falhas e garante a integridade dos dados da loja.

---

## Arquitetura da Solução e Limpeza de Código

Durante as etapas iniciais de análise (Etapas 1 e 2), identificou-se que além do sistema principal (`MenuLoja`), o pacote continha duas classes soltas: `Principal.java` e `VerificarEstoque.java`. Cada uma possuía seu próprio método `main()` e instâncias isoladas, sem nenhuma ligação com o fluxo principal.

As funcionalidades úteis dessas classes (consultar produto e verificar estoque) foram migradas, corrigidas e centralizadas na classe principal (`MenuLoja`). As classes avulsas foram descartadas na versão final para garantir uma arquitetura coesa e centralizada.

---

## Etapas 3 e 4: Problemas Encontrados e Classificação

O sistema original foi submetido a testes de fronteira, incluindo valores negativos e campos em branco. Os resultados e suas classificações estão registrados abaixo:

| Teste Realizado | Comportamento Encontrado | Comportamento Esperado | Classificação do Problema |
| :--- | :--- | :--- | :--- |
| Cadastrar produto com preço negativo (-10) | Sistema aceitou normalmente e armazenou `preco = -10.0`. | Sistema deveria rejeitar e pedir um novo valor. | Falta de validação |
| Cadastrar produto com quantidade negativa (-5) | Sistema aceitou normalmente e armazenou `quantidade = -5`. | Sistema deveria rejeitar e pedir um novo valor. | Falta de validação |
| Consultar produto antes de qualquer cadastro | Exibiu "Nome: null", "Preço: R$ 0.0", "Quantidade: 0". | Deveria avisar que nenhum produto foi cadastrado. | Falta de tratamento de situação |
| Cadastrar produto deixando o nome em branco | Sistema aceitou string vazia como nome. | Sistema deveria rejeitar nome vazio. | Falta de validação |
| Informar texto ("abc") em campo de preço decimal | Encerrou abruptamente com `java.util.InputMismatchException`. | Idealmente avisaria que o valor é inválido sem travar. | Falta de tratamento de situação |
| Escolher uma opção inexistente no menu (ex.: 9) | Sistema exibiu "Opção inválida!". | Mesmo comportamento já estava correto. | - |
| Vender quantidade maior que estoque disponível | Exibiu "Estoque insuficiente!" e não alterou estoque. | Mesmo comportamento já estava correto. | - |
| Registrar entrada de mercadoria (reposição) | Funcionalidade não existia no sistema original. | Deveria permitir aumentar o estoque controladamente. | Funcionalidade incompleta |

---

## Etapas 5 a 8: Correções, Validações e Melhorias Implementadas

Para solucionar as falhas, foram aplicadas as seguintes lógicas estruturais, utilizando estritamente `while`, `if/else if/else` e `switch/case`:

### Regras de Validação

- **Nome em branco:** Um laço `while` repete a pergunta enquanto a string digitada for vazia.
- **Preço negativo ou zero:** Um laço `while` exige a redigitação enquanto o valor for `<= 0`.
- **Quantidade negativa:** Um laço `while` impede valores menores que `0`, mas permite iniciar zerado.

### Controle de Estado

- Criação da variável booleana `produto.cadastrado`. Todas as opções secundárias testam `if (produto.cadastrado == false)` para impedir a exibição de "dados fantasmas" antes do cadastro oficial.

### Novas Funcionalidades

- **Opção 4 (Reposição):** Permite adicionar itens ao estoque com validação `while` para aceitar apenas valores maiores que zero.
- **Confirmação de Operação (A):** Exibe resumo antes da venda e pede confirmação (S/N).
- **Alerta de Estoque (B):** Emissão de aviso se o estoque cair para 5 ou menos após uma venda.
- **Cálculo de Valores (C):** Exibe o valor total acumulado (preço x quantidade) na consulta.

### Novos Testes Após Correções

| Problema Original | Correção Realizada | Resultado Após Novo Teste |
| :--- | :--- | :--- |
| Preço negativo/zero era aceito | Laço `while` repete a leitura enquanto `preco <= 0`. | Valor -10 foi rejeitado; 15.90 foi aceito com sucesso. |
| Quantidade negativa era aceita | Laço `while` repete a leitura enquanto `quantidade < 0`. | Valor -5 foi rejeitado; 0 e 8 foram aceitos normalmente. |
| Nome vazio era aceito | Laço `while` repete a leitura enquanto `nome.equals("")`. | Enter em branco foi rejeitado; "Produto Teste" foi aceito. |
| Dados "fantasmas" | Verificação de `produto.cadastrado == false`. | Opções 2, 3, 4 e 5 bloqueadas com aviso amigável antes do cadastro. |
| Sem funcionalidade de reposição | Implementada Opção 4 com validação positiva via `while`. | Entrada de 0 rejeitada; entrada de 15 somou corretamente ao estoque. |

---

## Desafio de Investigação (Crash do Sistema)

- **Cenário:** Entrada da string `"abc"` no campo de preço (`double`).
- **O que aconteceu?** O programa foi encerrado pela JVM com a mensagem `Exception in thread "main" java.util.InputMismatchException`.
- **Por que?** O método `Scanner.nextDouble()` tentou converter texto em número sem sucesso.
- **Como tratar futuramente?** Envolvendo a leitura em um bloco `try/catch` para capturar a exceção sem encerrar a aplicação.

---

## Questões para Reflexão - Sistema da Loja (Desafio 1)

1. **Um programa que executa sem apresentar erro necessariamente está correto?**  
   Não. O sistema original compilava e rodava, mas aceitava dados semanticamente incorretos, como estoque negativo e preços zerados. A ausência de falhas de compilação não garante a correção das regras de negócio.

2. **Diferença entre erro de sintaxe e erro de lógica?**  
   Erros de sintaxe violam a gramática da linguagem e impedem a compilação. Erros de lógica permitem compilação e execução, mas produzem resultados incoerentes.

3. **Por que validar dados?**  
   Para garantir a integridade dos dados e do fluxo da aplicação, impedindo cálculos falhos ou quebra do sistema.

4. **Problema mais importante?**  
   O crash decorrente de entrada de texto em campos numéricos, que interrompia totalmente o serviço.

5. **Correção mais difícil?**  
   O controle de estado global (`produto.cadastrado`), demandando revisão de todos os fluxos para não quebrar a navegação.

6. **Onde utilizou if/else if/else?**  
   Nas validações do cadastro, verificação de elegibilidade de vendas, comparação de estoque e respostas S/N.

7. **Função do while?**  
   Manter o loop do menu ativo e insistir na leitura de dados válidos nos cadastros.

8. **Função do switch/case?**  
   Mapear a escolha do usuário diretamente para a funcionalidade correspondente.

9. **Por que testar valores atípicos?**  
   Porque as falhas de validação ocorrem predominantemente em cenários atípicos, conhecidos como testes de borda.

10. **Melhorias e motivos?**  
    Confirmação de vendas, alerta de estoque baixo, cálculo de saldo, bloqueio pré-cadastro e reposição. Essas melhorias foram escolhidas para tornar o software mais completo e seguro.

11. **O que ainda pode ser melhorado?**  
    Tratamento de exceções com `try/catch`, suporte a múltiplos produtos utilizando coleções e persistência em banco de dados.

---

## Desafio 2: Cadastro de Produto - Documentação

A segunda etapa da atividade focou no uso de estruturas de repetição e acumuladores para o processamento em lote de produtos.

### Registro dos Testes

| Teste | Entrada | Resultado esperado | Resultado obtido | Correto? |
| :--- | :--- | :--- | :--- | :--- |
| Estoque vazio | 0 | Sem estoque | Situação: Sem estoque | Sim |
| Estoque mínimo | 1 | Estoque baixo | Situação: Estoque baixo | Sim |
| Limite estoque baixo | 19 | Estoque baixo | Situação: Estoque baixo | Sim |
| Limite estoque suficiente | 20 | Estoque suficiente | Situação: Estoque suficiente | Sim |
| Preço negativo | -100 | Não aceitar | Laço bloqueia e pede novo valor | Sim |
| Quantidade negativa | -2 | Não aceitar | Laço bloqueia e pede novo valor | Sim |
| Qtd. de produtos negativa | -2 | Não aceitar | Encerra o programa imediatamente | Sim |

### Questões para Reflexão Resolvidas (Desafio 2)

1. **Por que um laço de repetição foi necessário?**  
   Para evitar a duplicação de código. Ele permite solicitar dados e calcular para vários produtos automaticamente.

2. **Por que o `for` é adequado aqui?**  
   Ele é projetado para iterações finitas. Como o usuário informa a quantidade inicial, o `for` estabelece início, parada e incremento na mesma linha.

3. **O que ocorreria sem o incremento do `for`?**  
   O programa entraria em um *loop infinito*, travando a execução.

4. **Função do contador:**  
   Registrar a frequência de eventos. No código, variáveis contam produtos em cada categoria de estoque.

5. **Função do acumulador:**  
   Somar e armazenar valores progressivamente, como ocorre com `valorTotalEstoque`.

6. **Diferença entre os dois:**  
   O contador aumenta de forma constante, geralmente em `+1`, enquanto o acumulador recebe incrementos variáveis, como o preço.

7. **Por que contadores começam em 0?**  
   Porque antes de iniciar, nenhum evento que se deseja contar ocorreu ainda.

8. **Por que o valor total começa em 0?**  
   É o elemento neutro da adição. Qualquer outro valor inicial poderia alterar o cálculo.

9. **Por que o relatório fica fora do laço?**  
   Se estivesse dentro, exibiria os totais a cada repetição, em vez de apresentar o resultado consolidado final.

10. **`if/else` dentro do `for`?**  
    Foi necessário para classificar individualmente a quantidade em estoque e avaliar o `maiorValor`.

11. **Melhoria implementada?**  
    Validações com `while` que impedem entradas negativas de preço e quantidade.

12. **Maior dificuldade?**  
    Gerenciar o buffer do `Scanner`. Foi preciso um `nextLine()` vazio após `nextInt()` para não pular a leitura do "Nome".

---

## Licença

Atividade acadêmica desenvolvido para fins educacionais na disciplina de Elicitação e Programação — UNICEPLAC.

Uso restrito aos fins do curso.
