package main.service;
import main.models.Produto;
import java.io.*;
import java.util.Scanner;

import static main.util.ArquivoUtil.gravarId;
import static main.util.ArquivoUtil.lerId;

public class ProdutoService {
    public static void cadastrarProduto (String arqIdProduto,String arqProduto,Scanner sc){
        int id = lerId(arqIdProduto);
        if(id == -1){
            System.err.println("Erro ao obter ID do produto.");
            return;
        }
        Produto produto = criarProduto(id,sc);
        salvarProduto(produto,arqProduto);
        gravarId(id+1, arqIdProduto);
    }
    private static Produto criarProduto (int id, Scanner sc){
        Produto produto = new Produto();
        produto.setId(id);
        System.out.print("Digite o nome do produto: ");
        produto.setNome(sc.nextLine().trim());//Precisa fazer uma excessão para nao criar produtos com o mesmo nome

        System.out.print("\nDigite o preço do produto: ");
        produto.setPreco(sc.nextDouble());//Outra excessão para tornar o preço sempre positivo > 0

        System.out.print("\nDigite a quantidade de estoque: ");//Outra excessão para tornar o estoque sempre positivo
        produto.setEstoque(sc.nextInt());
        sc.nextLine();
        return produto;
    }

    private static void salvarProduto (Produto p, String arq){
        try (PrintWriter pw = new PrintWriter(new FileWriter(arq, true))){
            pw.println(
                    p.getId() + ";" +
                    p.getNome() + ";" +
                    p.getPreco() + ";" +
                    p.getEstoque()
            );
        } catch (IOException e){
            System.err.println("Erro ao salvar produto.");
            e.printStackTrace();
        }
    }
    private static Produto buscarPorId(int id, String arqProduto){
        try (BufferedReader br = new BufferedReader(new FileReader(arqProduto))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int idArquivo = Integer.parseInt(dados[0]);
                if (idArquivo == id){
                    Produto produto = getProdutos(idArquivo,dados);
                    return produto;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Não encontramos nenhum produto com esse ID");
            e.printStackTrace();
        }
        return null;
    }

    //Estou preenchendo o meu produto com o que está no arquivo, por isso o set
    private static Produto getProdutos(int idArquivo, String[] dados){
        Produto produto = new Produto();
        produto.setId(idArquivo);
        produto.setNome(dados[1]);
        produto.setPreco(Double.parseDouble(dados[2]));
        produto.setEstoque(Integer.parseInt(dados[3]));
        return produto;
    }

    public static void listarProdutos (String arqProduto){
        System.out.println("Listar Produtos: ");
        for(int id = 0; ;id++){
            Produto produto = buscarPorId(id,arqProduto);
            if (produto == null) {
                break;
            }
            System.err.println("ID - " + produto.getId()
                    +"\nNome: " + produto.getNome()
                    +"\nPreço: " + produto.getPreco()
                    +"\nEstoque: " + produto.getEstoque());
        }
    }


}
