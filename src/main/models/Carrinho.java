package main.models;

public class Carrinho {
    private ItemCarrinho[] itens;

    public Carrinho() {
        this.itens = new ItemCarrinho[0]; // inicializa com array vazio
    }
    public ItemCarrinho[] getItens() {
        return itens;
    }
    public void setItens(ItemCarrinho[] itens) {
        this.itens = itens;
    }
    public double getValorTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.getSubtotal(); // soma os subtotais
        }
        return total;
    }
}

