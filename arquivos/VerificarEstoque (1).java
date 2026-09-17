package br.edu.aula;

import java.util.Scanner;

public class VerificarEstoque {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Produto produto = new Produto();

        System.out.println("=== VERIFICAÇÃO DE ESTOQUE ===");

        // Correção: nome vazio não é mais aceito.
        String nome = "";
        while (nome.equals("")) {
            System.out.print("Nome do produto: ");
            nome = entrada.nextLine();
            if (nome.equals("")) {
                System.out.println("O nome não pode ficar vazio. Digite novamente.");
            }
        }
        produto.nome = nome;

        // Correção: quantidade negativa não é mais aceita.
        int quantidade = -1;
        while (quantidade < 0) {
            System.out.print("Quantidade em estoque: ");
            quantidade = entrada.nextInt();
            entrada.nextLine();
            if (quantidade < 0) {
                System.out.println("A quantidade não pode ser negativa. Digite novamente.");
            }
        }
        produto.quantidade = quantidade;
        produto.cadastrado = true;

        if (produto.quantidade > 0) {
            System.out.println("Produto disponível em estoque.");
        } else {
            System.out.println("Produto sem estoque.");
        }

        entrada.close();
    }
}
