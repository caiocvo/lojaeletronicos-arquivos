package main.service;
import main.exception.ProdutoValidator;
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
        gravarId((id+1), arqIdProduto);
    }
    private static Produto criarProduto (int id, Scanner sc){
        Produto produto = new Produto();
        produto.setId(id);
        System.out.print("Digite o nome do produto: ");
        produto.setNome(sc.nextLine().trim());

        System.out.print("\nDigite o preço do produto: ");
        produto.setPreco(sc.nextDouble());

        System.out.print("\nDigite a quantidade de estoque: ");
        produto.setEstoque(sc.nextInt());
        sc.nextLine();
        ProdutoValidator.validar(produto);
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
    public static Produto buscarPorId(int id, String arqProduto){
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

    public static void listarProdutos(String arqProduto) {
        System.out.println("Listar Produtos:");
        try (BufferedReader br = new BufferedReader(new FileReader(arqProduto))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                System.out.println("ID:       " + dados[0]);
                System.out.println("Nome:     " + dados[1]);
                System.out.printf("Preço:    R$ %.2f%n", Double.parseDouble(dados[2]));
                System.out.println("Estoque:  " + dados[3] + " unidades");
                System.out.println("==============================");

            }

        } catch (IOException e) {
            System.err.println("Erro ao listar produtos");
            e.printStackTrace();
        }
    }

    public static boolean excluirProduto(int idExcluir, String arqProduto) {
        File arquivo = new File(arqProduto);
        File temp = new File("temp.txt");
        boolean excluiu = false;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo));
             PrintWriter pw = new PrintWriter(new FileWriter(temp))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);

                if (id == idExcluir) {
                    excluiu = true;
                    continue; // Pula o produto
                }

                pw.println(linha); // Mantém os produtos não excluídos
            }
        } catch (IOException e) {
            System.err.println("Erro ao excluir produto");
            e.printStackTrace();
            return false;
        }

        if (arquivo.delete()) {
            temp.renameTo(arquivo); // Substitui o arquivo original
        }
        return excluiu;
    }
    public static boolean atualizarProduto(int id, String arqProduto, Scanner sc) {
        File arquivo = new File(arqProduto);
        File temp = new File("temp.txt");

        boolean atualizado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo));
             PrintWriter pw = new PrintWriter(new FileWriter(temp))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int idProduto = Integer.parseInt(dados[0]);

                if (idProduto == id) {
                    atualizado = true;

                    System.out.print("Novo nome do produto: ");
                    String nome = sc.nextLine().trim();

                    System.out.print("Novo preço: ");
                    double preco = sc.nextDouble();
                    if (preco <= 0) {
                        throw new IllegalArgumentException("O preço deve ser maior que 0.");
                    }
                    sc.nextLine();

                    System.out.print("Novo estoque: ");
                    int estoque = sc.nextInt();
                    if (estoque < 0) {
                        throw new IllegalArgumentException("O estoque não pode ser negativo.");
                    }
                    sc.nextLine();

                    Produto produto = new Produto();
                    produto.setId(idProduto);
                    produto.setNome(nome);
                    produto.setPreco(preco);
                    produto.setEstoque(estoque);

                    ProdutoValidator.validar(produto);

                    pw.println(idProduto + ";" + nome + ";" + preco + ";" + estoque);

                } else {
                    pw.println(linha);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        if (arquivo.delete()) {
            temp.renameTo(arquivo);
        }

        return atualizado;
    }

    public static void baixarEstoque(int idProduto, int qtdVendida, String arqProduto) {
        File arquivo = new File(arqProduto);
        File temp = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo));
             PrintWriter pw = new PrintWriter(new FileWriter(temp))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);

                if (id == idProduto) {
                    int estoqueAtual = Integer.parseInt(dados[3]);
                    int novoEstoque = estoqueAtual - qtdVendida;

                    if (novoEstoque < 0) {
                        throw new IllegalArgumentException("Estoque insuficiente.");
                    }

                    pw.println(id + ";" + dados[1] + ";" + dados[2] + ";" + novoEstoque);
                } else {
                    pw.println(linha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao baixar estoque.");
            e.printStackTrace();
        }

        arquivo.delete();
        temp.renameTo(arquivo);
    }


}
