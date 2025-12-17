package main.service;

import main.exception.ClienteValidator;
import main.models.Clientes;
import main.models.Endereco;

import java.io.*;
import java.util.Scanner;

import static main.util.ArquivoUtil.gravarId;
import static main.util.ArquivoUtil.lerId;

public class ClienteService {
    public static void cadastrarCliente(String arqIdCliente, String arqCliente, Scanner sc) {
        try {
            int id = lerId(arqIdCliente);
            Clientes cliente = criarCliente(id, sc);
            salvarCliente(cliente, arqCliente);
            gravarId((id + 1), arqIdCliente);

            System.out.println("Cliente cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
        }
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
        cliente.setId(id);

        System.out.print("\nNome: ");
        cliente.setNome(sc.nextLine());
        ClienteValidator.validarNome(cliente);

        System.out.print("\nEmail: ");
        cliente.setEmail(sc.nextLine());
        ClienteValidator.validarEmail(cliente);

        System.out.print("\nSenha: ");
        cliente.setSenha(sc.nextLine());

        System.out.print("\nTelefone: ");
        cliente.setTelefone(sc.nextLine());
        ClienteValidator.validarTelefone(cliente);


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
        cliente.setEndereco(endereco);
        sc.nextLine();

        ClienteValidator.validarNome(cliente);
        ClienteValidator.validarEmail(cliente);

        return cliente;
    }
    public static Clientes login(String email, String senha, String arqCliente) {

        try (BufferedReader br = new BufferedReader(new FileReader(arqCliente))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados[2].equals(email) && dados[3].equals(senha)) {
                    Clientes cliente = new Clientes();
                    cliente.setId(Integer.parseInt(dados[0]));
                    cliente.setNome(dados[1]);
                    cliente.setEmail(dados[2]);
                    cliente.setSenha(dados[3]);
                    return cliente;
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao fazer login");
            e.printStackTrace();
        }

        return null;
    }
    public static void listarClientes (String arqCliente){
        System.out.println("Lista de todos os clientes: ");
        try (BufferedReader br = new BufferedReader(new FileReader(arqCliente))){
            String linha;
            while((linha=br.readLine()) != null){
                String[] dados = linha.split(";");
                System.out.println("ID: " + dados[0] +
                        "\nNome do cliente: "+ dados[1] + "\nEmail: " +
                        dados[2]);
            }
        } catch (IOException e){
            System.err.println("Erro ao listar os clientes.");
            e.printStackTrace();
        }
    }
}
