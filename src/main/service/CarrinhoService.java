package main.service;

import main.models.Carrinho;
import main.models.Clientes;
import main.models.ItemCarrinho;

public class CarrinhoService {

    public static Carrinho criarCarrinho(Clientes cliente) {
        return new Carrinho(cliente.getId());
    }

    public static void adicionarItem(Carrinho carrinho, ItemCarrinho item) {
        carrinho.adicionarItem(item);
    }

    public static void removerItem(Carrinho carrinho, int indice) {
        carrinho.removerItem(indice);
    }

    public static void atualizarQuantidade(Carrinho carrinho, int indice, int novaQtd) {
        carrinho.atualizarQuantidade(indice, novaQtd);
    }

    public static void listarItens(Carrinho carrinho) {
        for (ItemCarrinho item : carrinho.getItens()) {
            System.out.println(
                    item.getProduto().getNome() +
                            " - Qtd: " + item.getQuantidade() +
                            " - Subtotal: " + item.getSubtotal()
            );
        }
    }
}
