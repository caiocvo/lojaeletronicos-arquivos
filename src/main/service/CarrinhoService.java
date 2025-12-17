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

    public static void listarItens(Carrinho carrinho) {
        ItemCarrinho[] itens = carrinho.getItens();

        for (int i = 0; i < itens.length; i++) {
            System.out.println(
                    "[" + i + "] " +
                            itens[i].getProduto().getNome() +
                            " - Qtd: " + itens[i].getQuantidade() +
                            " - Subtotal: " + itens[i].getSubtotal()
            );
        }

    }
}
