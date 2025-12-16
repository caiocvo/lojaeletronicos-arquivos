package main.service;

import main.models.Clientes;
import main.models.Endereco;

import java.io.*;
import java.util.Scanner;

import static main.util.ArquivoUtil.gravarId;

public class ClienteService {
    public static void cadastrarCliente (String arqIdCliente, String arqCliente, Scanner sc){
        int id = lerIdCliente(arqIdCliente);
        if(id == -1){
            System.err.println("Erro ao obter ID do cliente");
            return;
        }
        Clientes cliente = criarCliente(id,sc);

        salvarCliente(cliente,arqCliente);

        gravarId(id+1, arqCliente);
        //id+1 para partir do 1 nao do 0
    }

    private static int lerIdCliente(String arq)  {
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

    private static void salvarCliente(Clientes c, String arq) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arq, true))) {

            pw.println(
                    c.getId() + ";" +
                    c.getNome() + ";" +
                    c.getEmail() + ";" +
                    c.getSenha() + ";" +
                    c.getTelefone() + ";" +
                    c.getEndereco().getRua() + ";" +
                    c.getEndereco().getNumero() + ";" +
                    c.getEndereco().getBairro() + ";" +
                    c.getEndereco().getEstado()
            );

        } catch (IOException e) {
            System.err.println("Erro ao salvar cliente");
            e.printStackTrace();
        }
    }

    private static Clientes criarCliente (int id, Scanner sc) {
        Clientes cliente = new Clientes();
        Endereco endereco = new Endereco();
        boolean criacao = true;
        cliente.setId(id);
        //Atribuir valores

        System.out.print("\nNome: ");
        cliente.setNome(sc.nextLine());

        System.out.print("\nEmail: ");
        cliente.setEmail(sc.nextLine());

        System.out.print("\nSenha: ");
        cliente.setSenha(sc.nextLine());

        System.out.print("\nTelefone: ");
        cliente.setTelefone(sc.nextLine());

        System.out.println("\n---Endereço---");
        System.out.print("\nRua: ");
        endereco.setRua(sc.nextLine());

        System.out.print("\nNúmero: ");
        endereco.setNumero(sc.nextInt());
        sc.nextLine();

        System.out.print("\nBairro: ");
        endereco.setBairro(sc.nextLine());

        System.out.print("\nEstado: ");
        endereco.setEstado(sc.nextLine());
        //Cliente recebe vetor de endereço
        cliente.setEndereco(endereco);
        return cliente;
    }
}
