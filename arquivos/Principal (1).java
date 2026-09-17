package br.edu.aula;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Produto produto = new Produto();

        System.out.println("=== CADASTRO DE PRODUTO ===");

        // Correção: o nome digitado agora é realmente lido e guardado,
        // e não é mais aceito em branco.
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
            System.out.print("Preço do produto: R$ ");
            preco = entrada.nextDouble();
            entrada.nextLine();
            if (preco <= 0) {
                System.out.println("O preço deve ser maior que zero. Digite novamente.");
            }
        }
        produto.preco = preco;

        // Correção: quantidade negativa não é mais aceita.
        int quantidade = -1;
        while (quantidade < 0) {
            System.out.print("Quantidade: ");
            quantidade = entrada.nextInt();
            entrada.nextLine();
            if (quantidade < 0) {
                System.out.println("A quantidade não pode ser negativa. Digite novamente.");
            }
        }
        produto.quantidade = quantidade;
        produto.cadastrado = true;

        System.out.println("\n=== PRODUTO CADASTRADO ===");
        System.out.println("Nome: " + produto.nome);
        System.out.println("Preço: R$ " + produto.preco);
        System.out.println("Quantidade: " + produto.quantidade);

        double valorTotal = produto.preco * produto.quantidade;
        System.out.println("Valor total em estoque: R$ " + valorTotal);

        entrada.close();
    }
}
