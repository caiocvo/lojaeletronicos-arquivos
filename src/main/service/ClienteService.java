package main.service;

import main.models.Clientes;
import main.models.Endereco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ClienteService {
    public static void cadastrarCliente (String arqIdCliente){
        int id = lerIdCliente(arqIdCliente);
        //Clientes cliente = cria
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
