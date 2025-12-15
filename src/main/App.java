package main;

import main.util.ArquivoUtil;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Anotações:
        //Agora com um vetor no carrinho você fala quais items do carrinho você quer comprar

        String raiz = "c://lojaTec/";
        String raizClientes = raiz + "clientes/";
        String raizProdutos = raiz + "produtos/";
        String raizPedido = raiz + "pedidos/";
        String raizEndereco = raiz + "enderecos/";
        String raizCarrinho = raiz + "carrinho/";
        String arqIdCliente = raiz + "idCliente.txt";

        //Login ADM
        String idADM = "457";
        String senhaADM = "1234";
        boolean acessoADM = false;
        Scanner sc = new Scanner(System.in);

        //LOGIN
        entrar();
        char opLogin = sc.nextLine().trim().charAt(0);
        if(Objects.equals(opLogin,'2'))
            acessoADM = funcaoADM(idADM,senhaADM,sc);

        //else
        //Aqui você chama o menu de cadastro de cliente

        char opcao = 0;

//CHAMADA DOS MENUS QUE VOCE AINDA VAI CRIAR

        while(true){
            if(acessoADM){
                menuADM();
                opcao = sc.nextLine().charAt(0);
                if(Objects.equals(opcao,'5'))
                    break;
                switch (opcao){
                    //Aqui coloca as opções do menu do ADM
                }
            }
            //Aqui voce precisa chamar a função cliente para ele fazer o Big loggin ou entao chamar o menu
        }
    }

//PRECISA CRIAR CADASTRO DE CLIENTE
    private static void iniciarReset (String raiz,String raizClientes, String raizProdutos, String raizPedido,
                                      String raizEndereco, String raizCarrinho, String arqIdCliente){
        File dir=new File(raiz);
        if(!dir.exists()) {
            dir.mkdir();
        }
        dir=new File(raizClientes);
        if(!dir.exists()){//Se não existir cria pasta clientes
            dir.mkdir();
        }
        dir = new File(raizProdutos);
        if(!dir.exists()){//Se não existir cria pasta produtos
            dir.mkdir();
        }
        dir = new File(raizPedido);
        if(!dir.exists()){
            dir.mkdir();
        }
        else
            apagarArquivos(dir);
        dir = new File(raizEndereco);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagarArquivos(dir);
        }

        dir = new File(raizCarrinho);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagarArquivos(dir);
        }
        ArquivoUtil.gravarId(0,arqIdCliente);
    }

    //Para criar cliente, é necessário ler o id e após isso,
    // gravar no arquivo


    //Apagar
    private static void apagarArquivos (File dir) {
        String []arquivos = dir.list();
        for(String arq:arquivos){
            File f = new File(arq);
            f.delete();
        }
    }

    private static boolean funcaoADM (String id, String senha, Scanner sc){

        System.out.print("\nPor favor digite o ID do ADM: ");
        String idTentativa = sc.nextLine().trim();

        System.out.print("\nPor favor digite a senha: ");
        String senhaTentativa = sc.nextLine().trim();

        if(!Objects.equals(idTentativa, id) || !Objects.equals(senha, senhaTentativa)){
            System.err.print("Email ou senha estao incorretos!");
        } else {
            System.out.println("Bem vindo! Por favor digite uma das seguintes opções: \n");
            return true;
        }
        return false;
    }

    private static void menuADM() {
        System.out.println("Menu do Administrador: ");
        System.out.println("1) Cadastrar Produtos" +
                        "\n2) Listar todos produtos" +
                        "\n3) Listar todos clientes" +
                        "\n4) Listar todos os pedidos" +
                        "\n5) Sair");
        System.out.print("\nDigite uma das opções: ");
    }

    private static void menuCliente() {
        System.out.println("Menu do Cliente: ");
        System.out.println("1) Ver carrinho" +
                        "\n2) Ver produtos" +
                        "\n3) ..." +
                        "\n4) Sair");
        //Pensei em fazer tambem um para pedido, aí os items no carrinho tivessem IDs, onde voce digita o ID do que deseja comprar
        System.out.print("\nDigite uma das opções: ");
    }

    private static void entrar(){
        System.out.println("Deseja entrar como Adminstrador ou usuário?: " +
                "\n1)Usuário" +
                "\n2)ADM");
    }
}
