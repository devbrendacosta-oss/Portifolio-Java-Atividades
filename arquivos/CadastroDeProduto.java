package br.edu.aula;

import java.util.Scanner;

public class CadastroDeProduto {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Quantos produtos deseja analisar? ");
        int quantidadeProdutos = entrada.nextInt();
        
        // Validação (Melhoria): Impede quantidade negativa ou zero de produtos a analisar
        if (quantidadeProdutos <= 0) {
            System.out.println("Quantidade inválida. Encerrando o programa.");
            entrada.close();
            return;
        }

        int estoqueSuficiente = 0;
        int estoqueBaixo = 0;
        int semEstoque = 0;
        double valorTotalEstoque = 0;
        
        // Variáveis para o Desafio Avançado
        double maiorValor = 0;
        String produtoMaiorValor = "";

        for (int i = 1; i <= quantidadeProdutos; i++) {
            System.out.println("\n======================");
            System.out.println("PRODUTO " + i);
            System.out.println("======================");
            entrada.nextLine(); // Consome a quebra de linha pendente
            
            System.out.print("Nome: ");
            String nome = entrada.nextLine();
            
            System.out.print("Preço: R$ ");
            double preco = entrada.nextDouble();
            // Validação (Melhoria): Impede preço negativo
            while (preco < 0) {
                System.out.print("Preço inválido! Digite um valor positivo: R$ ");
                preco = entrada.nextDouble();
            }
            
            System.out.print("Quantidade em estoque: ");
            int quantidade = entrada.nextInt();
            // Validação (Melhoria): Impede quantidade negativa em estoque
            while (quantidade < 0) {
                System.out.print("Quantidade inválida! Digite um valor igual ou maior que zero: ");
                quantidade = entrada.nextInt();
            }

            double valorProduto = preco * quantidade;
            valorTotalEstoque = valorTotalEstoque + valorProduto;
            
            // Lógica do Desafio Avançado
            if (valorProduto > maiorValor) {
                maiorValor = valorProduto;
                produtoMaiorValor = nome;
            }

            if (quantidade >= 20) {
                System.out.println("Situação: Estoque suficiente.");
                estoqueSuficiente++;
            } else if (quantidade > 0) {
                System.out.println("Situação: Estoque baixo.");
                estoqueBaixo++;
            } else {
                System.out.println("Situação: Sem estoque.");
                semEstoque++;
            }
            System.out.println("Valor em estoque: R$ " + valorProduto);
        }

        System.out.println("\n==========================");
        System.out.println(" RELATÓRIO DO ESTOQUE");
        System.out.println("==========================");
        System.out.println("Produtos analisados: " + quantidadeProdutos);
        System.out.println("Estoque suficiente: " + estoqueSuficiente);
        System.out.println("Estoque baixo: " + estoqueBaixo);
        System.out.println("Sem estoque: " + semEstoque);
        System.out.println("Valor total do estoque: R$ " + valorTotalEstoque);
        
        // Relatório do Desafio Avançado
        if (maiorValor > 0) {
            System.out.println("\nProduto com maior valor em estoque:");
            System.out.println(produtoMaiorValor);
            System.out.println("R$ " + maiorValor);
        }

        entrada.close();
    }
}
