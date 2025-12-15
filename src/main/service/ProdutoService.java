package main.service;

import main.models.Produto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProdutoService {
    private static Produto criarProduto (int id, Scanner sc){
        Produto produto = new Produto();
        System.out.print("Digite o nome do produto: ");
        //Precisa fazer uma excessão para nao criar produtos com o mesmo nome
        produto.setNome(sc.nextLine().trim());

        System.out.print("\nDigite o preço do produto: ");
        produto.setPreco(sc.nextDouble());
        //Outra excessão para tornar o preço sempre positivo > 0

        System.out.print("\nDigite a quantidade de estoque: ");
        //Outra excessão para tornar o estoque sempre positivo
        produto.setEstoque(sc.nextInt());
        return produto;
    }
    private static int lerIdProduto(String arq)  {
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(arq));
            int id = Integer.parseInt(br.readLine());
            br.close();
            return id;
        } catch (IOException | NumberFormatException e){
            System.err.println("Erro ao ler o id");
            e.printStackTrace();
        }
        return -1;
    }
}
