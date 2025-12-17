package main.exception;

import main.models.Clientes;

public class ClienteValidator {
    public static void validarEmail(Clientes cliente) {
        String email = cliente.getEmail();

        if (!email.contains("@") ||
                !email.contains(".") ||
                email.indexOf(".") < email.indexOf("@")) {

            throw new IllegalArgumentException("Esse email é inválido");
        }
    }
    public static void validarNome (Clientes cliente){
        String nome = cliente.getNome();

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (!nome.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new IllegalArgumentException(
                    "Nome não pode conter números ou caracteres especiais"
            );
        }
    }

    public static void validarTelefone(Clientes cliente) {
        String tel = cliente.getTelefone();

        if (tel == null || tel.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }

        if (!tel.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido. Use apenas números (10 ou 11 dígitos)");
        }
    }

}
