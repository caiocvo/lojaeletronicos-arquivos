package main;

import main.models.*;
import main.service.*;
import main.util.FileUtil;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String raiz = "c://lojaTec/";
        String raizClientes = raiz + "clientes/";
        String raizProdutos = raiz + "produtos/";
        String raizPedidos  = raiz + "pedidos/";

        String arqClientes  = raizClientes + "clientes.txt";
        String arqProdutos  = raizProdutos + "produtos.txt";
        String arqPedidos   = raizPedidos + "pedidos.txt";

        String arqIdCliente = raiz + "idCliente.txt";
        String arqIdProduto = raiz + "idProduto.txt";
        String arqIdPedido  = raiz + "idPedido.txt";

        iniciarSistema(raiz, raizClientes, raizProdutos, raizPedidos,
                arqIdCliente, arqIdProduto, arqIdPedido);
        //Login
        System.out.println("1) Usuário");
        System.out.println("2) ADM");
        System.out.print("Escolha: ");
        char tipoLogin = sc.nextLine().charAt(0);

        //ADM
        if (tipoLogin == '2') {
            boolean acessoADM = false;

            while (!acessoADM) {
                acessoADM = loginADM(sc);

                if (!acessoADM) {
                    System.out.println("Email ou senha incorretos. Tente novamente.\n");
                }
            }
            while (true) {
                menuADM();
                char op = sc.nextLine().charAt(0);

                if (op == '6') break;

                switch (op) {
                    case '1':
                        ProdutoService.cadastrarProduto(arqIdProduto, arqProdutos, sc);
                        break;

                    case '2':
                        ProdutoService.listarProdutos(arqProdutos);
                        break;

                    case '3':
                        ClienteService.listarClientes(arqClientes);
                        break;

                    case '4':
                        System.out.print("ID do produto: ");
                        int idAlt = sc.nextInt();
                        sc.nextLine();
                        ProdutoService.atualizarProduto(idAlt, arqProdutos, sc);
                        break;

                    case '5':
                        System.out.print("ID do produto: ");
                        int idExc = sc.nextInt();
                        sc.nextLine();
                        ProdutoService.excluirProduto(idExc, arqProdutos);
                        break;
                }
            }
        }
        //Cliente
        else {
            System.out.println("1) Login");
            System.out.println("2) Cadastrar");
            char op = sc.nextLine().charAt(0);
            Clientes cliente = null;

            if (op == '2') {
                // Cadastro do cliente e retorno do objeto criado
                cliente = ClienteService.cadastrarClienteRetorna(arqIdCliente, arqClientes, sc);
            }

            if (op == '1' || cliente == null) {
                // Login normal (ou se o cadastro falhou)
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();

                cliente = ClienteService.login(email, senha, arqClientes);

                if (cliente == null) {
                    System.err.println("Login inválido.");
                    return;
                }
            }

            Carrinho carrinho = CarrinhoService.criarCarrinho(cliente);

            while (true) {
                menuCliente();
                char opc = sc.nextLine().charAt(0);

                if (opc == '6') break;

                switch (opc) {
                    case '1':
                        ProdutoService.listarProdutos(arqProdutos);
                        break;

                    case '2':
                        System.out.print("ID do produto: ");
                        int idProd = sc.nextInt();
                        System.out.print("Quantidade: ");
                        int qtd = sc.nextInt();
                        sc.nextLine();

                        Produto p = ProdutoService.buscarPorId(idProd, arqProdutos);
                        if (p == null) {
                            System.err.println("Produto não encontrado");
                            break;
                        }

                        ItemCarrinho item = new ItemCarrinho();
                        item.setProduto(p);
                        item.setQuantidade(qtd);
                        CarrinhoService.adicionarItem(carrinho, item);
                        break;

                    case '3':
                        CarrinhoService.listarItens(carrinho);
                        System.out.println(String.format("Total: R$ %.2f", carrinho.getValorTotal()));
                        break;

                    case '4':
                        CarrinhoService.listarItens(carrinho);
                        System.out.print("Digite o índice do item para remover: ");
                        int indiceRemover = sc.nextInt();
                        sc.nextLine();
                        CarrinhoService.removerItem(carrinho, indiceRemover);
                        System.out.println("Item removido!");
                        break;

                    case '5':
                        PedidoService.finalizarPedido(carrinho, arqIdPedido, arqPedidos, arqProdutos);
                        carrinho = CarrinhoService.criarCarrinho(cliente);
                        break;
                }
            }
        }

    }


    private static boolean loginADM(Scanner sc) {
        System.out.print("ID ADM: ");
        String id = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        return Objects.equals(id, "457") && Objects.equals(senha, "1234");
    }

    private static void menuADM() {
        System.out.println("\n--- MENU ADM ---");
        System.out.println("1) Cadastrar Produto");
        System.out.println("2) Listar Produtos");
        System.out.println("3) Listar Clientes");
        System.out.println("4) Atualizar Produto");
        System.out.println("5) Excluir Produto");
        System.out.println("6) Sair");
        System.out.print("Opção: ");
    }

    private static void menuCliente() {
        System.out.println("\n--- MENU CLIENTE ---");
        System.out.println("1) Listar Produtos");
        System.out.println("2) Adicionar ao Carrinho");
        System.out.println("3) Ver Carrinho");
        System.out.println("4) Remover Item do Carrinho");
        System.out.println("5) Finalizar Pedido");
        System.out.println("6) Sair");
        System.out.print("Opção: ");
    }


    private static void iniciarSistema(String raiz, String clientes, String produtos, String pedidos,
                                       String idCliente, String idProduto, String idPedido) {

        new File(raiz).mkdirs();
        new File(clientes).mkdirs();
        new File(produtos).mkdirs();
        new File(pedidos).mkdirs();

        File fIdCliente = new File(idCliente);
        if (!fIdCliente.exists()) {
            FileUtil.gravarId(0, idCliente);
        }

        File fIdProduto = new File(idProduto);
        if (!fIdProduto.exists()) {
            FileUtil.gravarId(0, idProduto);
        }

        File fIdPedido = new File(idPedido);
        if (!fIdPedido.exists()) {
            FileUtil.gravarId(0, idPedido);
        }
    }
}
