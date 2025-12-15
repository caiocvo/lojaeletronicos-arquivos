package main.models;

public class Carrinho {
    private ItemCarrinho[] itens;
    private double valorTotal;

    public ItemCarrinho[] getItens() {
        return itens;
    }

    public void setItens(ItemCarrinho[] itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
