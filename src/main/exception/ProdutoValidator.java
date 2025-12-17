package main.exception;

import main.models.Produto;

public class ProdutoValidator {
    public static void validar(Produto p){
        if (p.getNome() == null || p.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (p.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (p.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
    }
}
