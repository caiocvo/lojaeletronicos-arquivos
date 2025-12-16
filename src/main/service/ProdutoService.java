package main.service;
import main.models.Produto;
import java.io.*;
import java.util.Scanner;

import static main.util.ArquivoUtil.gravarId;

public class ProdutoService {
    public static void cadastrarProduto (String arqIdProduto,String arqProduto,Scanner sc){
        int id = lerIdProduto(arqIdProduto);
        if(id == -1){
            System.err.println("Erro ao obter ID do produto.");
            return;
        }
        Produto produto = criarProduto(id,sc);
        salvarProduto(produto,arqProduto);
        gravarId(id+1, arqProduto);
    }
    private static Produto criarProduto (int id, Scanner sc){
        Produto produto = new Produto();
        System.out.print("Digite o nome do produto: ");
        produto.setNome(sc.nextLine().trim());//Precisa fazer uma excessão para nao criar produtos com o mesmo nome

        System.out.print("\nDigite o preço do produto: ");
        produto.setPreco(sc.nextDouble());//Outra excessão para tornar o preço sempre positivo > 0

        System.out.print("\nDigite a quantidade de estoque: ");//Outra excessão para tornar o estoque sempre positivo
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

    private static void salvarProduto (Produto p, String arq){
        try (PrintWriter pw = new PrintWriter(new FileWriter(arq, true))){
            pw.println(
                    p.getId() +
                    p.getNome() + ";" +
                    p.getPreco() + ";" +
                    p.getEstoque()
            );
        } catch (IOException e){
            System.err.println("Erro ao salvar produto.");
            e.printStackTrace();
        }
    }
}
