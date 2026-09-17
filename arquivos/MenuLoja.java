package br.edu.aula;

import java.util.Scanner;

public class MenuLoja {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Produto produto = new Produto();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DA LOJA ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Consultar produto");
            System.out.println("3 - Verificar estoque");
            System.out.println("4 - Entrada de estoque (reposição)");
            System.out.println("5 - Realizar venda");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {

                case 1:
                    // ===================== CADASTRO =====================
                    System.out.println("\n=== CADASTRO ===");

                    // Correção: nome vazio não é mais aceito.
                    // O while repete a pergunta enquanto o nome digitado for vazio.
                    String nome = "";
                    while (nome.equals("")) {
                        System.out.print("Nome do produto: ");
                        nome = entrada.nextLine();
                        if (nome.equals("")) {
                            System.out.println("O nome não pode ficar vazio. Digite novamente.");
                        }
                    }
                    produto.nome = nome;

                    // Correção: preço zero ou negativo não é mais aceito.
                    double preco = -1;
                    while (preco <= 0) {
                        System.out.print("Preço: R$ ");
                        preco = entrada.nextDouble();
                        entrada.nextLine();
                        if (preco <= 0) {
                            System.out.println("O preço deve ser maior que zero. Digite novamente.");
                        }
                    }
                    produto.preco = preco;

                    // Correção: quantidade negativa não é mais aceita (zero pode).
                    int quantidadeCadastro = -1;
                    while (quantidadeCadastro < 0) {
                        System.out.print("Quantidade: ");
                        quantidadeCadastro = entrada.nextInt();
                        entrada.nextLine();
                        if (quantidadeCadastro < 0) {
                            System.out.println("A quantidade não pode ser negativa. Digite novamente.");
                        }
                    }
                    produto.quantidade = quantidadeCadastro;

                    // Etapa 6: marca que já existe um produto cadastrado.
                    produto.cadastrado = true;

                    // Etapa 7: mensagem mais completa.
                    System.out.println("\nProduto cadastrado com sucesso!");
                    System.out.println("Nome: " + produto.nome);
                    System.out.println("Preço: R$ " + produto.preco);
                    System.out.println("Quantidade em estoque: " + produto.quantidade);
                    if (produto.quantidade > 0 && produto.quantidade <= 5) {
                        System.out.println("Atenção: o produto já começa com estoque baixo.");
                    }
                    break;

                case 2:
                    // ===================== CONSULTAR =====================
                    System.out.println("\n=== PRODUTO ===");
                    // Correção da Etapa 6: antes mostrava nome=null, preço=0.0
                    if (produto.cadastrado == false) {
                        System.out.println("Nenhum produto foi cadastrado ainda.");
                        System.out.println("Escolha a opção 1 no menu para cadastrar um produto.");
                    } else {
                        double valorTotalEstoque = produto.preco * produto.quantidade;
                        System.out.println("Nome: " + produto.nome);
                        System.out.println("Preço: R$ " + produto.preco);
                        System.out.println("Quantidade: " + produto.quantidade);
                        System.out.println("Valor total em estoque: R$ " + valorTotalEstoque);
                    }
                    break;

                case 3:
                    // ================= VERIFICAR ESTOQUE =================
                    System.out.println("\n=== SITUAÇÃO DO ESTOQUE ===");
                    if (produto.cadastrado == false) {
                        System.out.println("Nenhum produto foi cadastrado ainda. Cadastre um produto primeiro (opção 1).");
                    } else if (produto.quantidade >= 20) {
                        System.out.println("Estoque suficiente.");
                    } else if (produto.quantidade > 0) {
                        System.out.println("Estoque baixo.");
                    } else {
                        System.out.println("Produto sem estoque.");
                    }
                    break;

                case 4:
                    // ================ ENTRADA DE ESTOQUE ================
                    // Funcionalidade nova: o sistema original só tinha saída (venda).
                    System.out.println("\n=== ENTRADA DE ESTOQUE (REPOSIÇÃO) ===");
                    if (produto.cadastrado == false) {
                        System.out.println("Nenhum produto foi cadastrado ainda. Cadastre um produto primeiro (opção 1).");
                    } else {
                        int quantidadeEntrada = -1;
                        while (quantidadeEntrada <= 0) {
                            System.out.print("Quantidade a adicionar ao estoque: ");
                            quantidadeEntrada = entrada.nextInt();
                            entrada.nextLine();
                            if (quantidadeEntrada <= 0) {
                                System.out.println("A quantidade deve ser maior que zero. Digite novamente.");
                            }
                        }
                        int estoqueAnterior = produto.quantidade;
                        produto.quantidade = produto.quantidade + quantidadeEntrada;

                        System.out.println("\nEntrada registrada com sucesso!");
                        System.out.println("Quantidade anterior: " + estoqueAnterior);
                        System.out.println("Quantidade adicionada: " + quantidadeEntrada);
                        System.out.println("Quantidade atual: " + produto.quantidade);
                    }
                    break;

                case 5:
                    // ===================== VENDA =====================
                    System.out.println("\n=== REALIZAR VENDA ===");
                    if (produto.cadastrado == false) {
                        // Correção da Etapa 6: antes era possível "vender" um produto inexistente
                        System.out.println("Nenhum produto foi cadastrado ainda. Cadastre um produto primeiro (opção 1).");
                    } else {
                        System.out.println("Estoque atual: " + produto.quantidade);
                        System.out.print("Quantidade desejada: ");
                        int quantidadeDesejada = entrada.nextInt();
                        entrada.nextLine();

                        if (quantidadeDesejada <= 0) {
                            // Correção: quantidade zero ou negativa numa venda não faz sentido
                            System.out.println("Quantidade inválida! A venda deve ser de pelo menos 1 unidade.");
                        } else if (quantidadeDesejada > produto.quantidade) {
                            System.out.println("Estoque insuficiente! Disponível: " + produto.quantidade
                                    + ", solicitado: " + quantidadeDesejada + ".");
                        } else {
                            double valorVenda = quantidadeDesejada * produto.preco;

                            // Melhoria A — Confirmação de operação
                            System.out.println("\nResumo da venda:");
                            System.out.println("Produto: " + produto.nome);
                            System.out.println("Quantidade: " + quantidadeDesejada);
                            System.out.println("Valor total: R$ " + valorVenda);
                            System.out.print("Confirmar venda? (S/N): ");
                            String confirmacao = entrada.nextLine();

                            if (confirmacao.equalsIgnoreCase("S")) {
                                int quantidadeAnterior = produto.quantidade;
                                produto.quantidade = produto.quantidade - quantidadeDesejada;

                                // Etapa 7 — mensagem detalhada
                                System.out.println("\nVenda realizada com sucesso!");
                                System.out.println("Quantidade anterior: " + quantidadeAnterior);
                                System.out.println("Quantidade vendida: " + quantidadeDesejada);
                                System.out.println("Quantidade atual: " + produto.quantidade);
                                System.out.println("Valor total da venda: R$ " + valorVenda);

                                // Melhoria B — Alerta de estoque baixo
                                if (produto.quantidade == 0) {
                                    System.out.println("ALERTA: o produto ficou sem estoque.");
                                } else if (produto.quantidade <= 5) {
                                    System.out.println("ALERTA: o estoque está baixo (" + produto.quantidade
                                            + " unidades restantes). Considere fazer uma reposição.");
                                }
                            } else {
                                System.out.println("Venda cancelada pelo usuário.");
                            }
                        }
                    }
                    break;

                case 0:
                    System.out.println("\nSistema encerrado.");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
            }
        }
        entrada.close();
    }
}
