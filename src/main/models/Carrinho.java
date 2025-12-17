package main.models;

public class Carrinho {

    private final int idCliente;
    private ItemCarrinho[] itens;

    public Carrinho(int idCliente) {
        this.idCliente = idCliente;
        this.itens = new ItemCarrinho[0];
    }

    public int getIdCliente() {
        return idCliente;
    }

    public ItemCarrinho[] getItens() {
        return itens;
    }

    public void adicionarItem(ItemCarrinho item) {
        ItemCarrinho[] novo = new ItemCarrinho[itens.length + 1];

        for (int i = 0; i < itens.length; i++) {
            novo[i] = itens[i];
        }

        novo[itens.length] = item;
        itens = novo;
    }

    public void removerItem(int indice) {
        if (indice < 0 || indice >= itens.length)
            return;
        ItemCarrinho[] novo = new ItemCarrinho[itens.length - 1];
        int j = 0;
        for (int i = 0; i<itens.length; i++) {
            if (i != indice) {
                novo[j++] = itens[i];
            }
        }

        itens = novo;
    }

    public double getValorTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }
}
