package main.service;

import main.models.Clientes;
import main.models.Endereco;

import java.io.*;
import java.util.Scanner;

import static main.util.ArquivoUtil.gravarId;
import static main.util.ArquivoUtil.lerId;

public class ClienteService {
    public static void cadastrarCliente (String arqIdCliente, String arqCliente, Scanner sc){
        int id = lerId(arqIdCliente);
        if(id == -1){
            System.err.println("Erro ao obter ID do cliente");
            return;
        }
        Clientes cliente = criarCliente(id,sc);

        salvarCliente(cliente,arqCliente);

        gravarId(id+1, arqIdCliente);
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
        cliente.setEndereco(endereco);
        sc.nextLine();
        return cliente;
    }

    private static Clientes buscarPorId(int id, String arqCliente) {
        try (BufferedReader br = new BufferedReader(new FileReader(arqCliente))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int idArquivo = Integer.parseInt(dados[0]);
                if (idArquivo == id) {
                    Clientes cliente = getClientes(idArquivo, dados);
                    return cliente; //Aqui retorna os dados do cliente se o ID digitado for encontrado no arquivo
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao buscar cliente por ID");
            e.printStackTrace();
        }
        return null; //Aqui vai retornar nulo se nao encontrar NADA
    }

    //Complemento de buscarPorId
    private static Clientes getClientes(int idArquivo, String[] dados) {
        Clientes cliente = new Clientes();
        cliente.setId(idArquivo);
        cliente.setNome(dados[1]);
        cliente.setEmail(dados[2]);
        //cliente.setSenha(dados[3]);
        cliente.setTelefone(dados[4]);

        Endereco endereco = new Endereco();
        endereco.setRua(dados[5]);
        endereco.setNumero(Integer.parseInt(dados[6]));
        endereco.setBairro(dados[7]);
        endereco.setEstado(dados[8]);
        cliente.setEndereco(endereco);
        return cliente;
    }

    public static void listarClientes (String arqCliente){
        for(int id = 0; ;id++) {
            Clientes cliente = buscarPorId(id,arqCliente);
            if (cliente == null){
                break;
            }
            Endereco endereco = cliente.getEndereco();
            System.out.println("ID: "+cliente.getId()+"\nCliente: " + cliente.getNome());
            System.out.println("Endereço: \nRua: "+endereco.getRua()
            +"\nNúmero: "+ endereco.getNumero()
            +"\nBairro: "+ endereco.getBairro()
            +"\nEstado: "+ endereco.getEstado()
            +"\n--------------------------------");
        }
    }
}
