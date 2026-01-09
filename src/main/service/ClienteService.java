package main.service;

import main.exception.ClienteValidator;
import main.models.Clientes;
import main.models.Endereco;
import main.util.FileUtil;

import java.io.*;
import java.util.Scanner;
public class ClienteService {

    //cadastra o cliente e retorna o objeto criado
    public static Clientes cadastrarClienteRetorna(String arqIdCliente, String arqCliente, Scanner sc) {
        try {
            int id = FileUtil.lerId(arqIdCliente);
            Clientes cliente = criarCliente(id, sc, arqCliente);
            salvarCliente(cliente, arqCliente);
            FileUtil.gravarId(id + 1, arqIdCliente);

            System.out.println("Cliente cadastrado com sucesso!");
            return cliente;
        } catch (IllegalArgumentException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
            return null;
        }
    }

    //salva cliente no arquivo
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

    //cria cliente com validação de email único
    private static Clientes criarCliente(int id, Scanner sc, String arqCliente) {
        Clientes cliente = new Clientes();
        Endereco endereco = new Endereco();
        cliente.setId(id);

        System.out.print("\nNome: ");
        cliente.setNome(sc.nextLine());
        ClienteValidator.validarNome(cliente);

        //validação de email único
        while (true) {
            System.out.print("\nEmail: ");
            String email = sc.nextLine();
            cliente.setEmail(email);
            try {
                ClienteValidator.validarEmail(cliente);
                if (emailJaExiste(email, arqCliente)) {
                    System.out.println("Esse email já está em uso. Tente outro.");
                } else {
                    break; //email válido e não usado
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

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

        return cliente;
    }

    //checa se email já existe no arquivo
    private static boolean emailJaExiste(String email, String arqCliente) {
        try (BufferedReader br = new BufferedReader(new FileReader(arqCliente))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                //verifico se possui mais de 2 dados a minha linha de arquivo e se o email digitado foi escrito nessa linha
                //até que percorra todas as linhas
                if (dados.length > 2 && dados[2].equalsIgnoreCase(email)) {
                    return true; // Email já existe
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar emails existentes.");
            e.printStackTrace();
        }
        return false; // Email ainda não foi usado
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

    //lista todos os clientes
    public static void listarClientes(String arqCliente) {
        System.out.println("Lista de todos os clientes: ");
        try (BufferedReader br = new BufferedReader(new FileReader(arqCliente))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                System.out.println("ID: " + dados[0] +
                        "\nNome do cliente: " + dados[1] +
                        "\nEmail: " + dados[2] + "\nTelefone: "+dados[4]
                +"\nRua: " + dados[5]+"\nBairro: "+dados[6] + "\nEstado: " + dados[7]);
                System.out.println("======================");
            }
        } catch (IOException e) {
            System.err.println("Erro ao listar os clientes.");
            e.printStackTrace();
        }
    }
}
