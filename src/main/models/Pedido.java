package main.models;

public class Pedido {

    private int id;
    private int idCliente;
    private ItemCarrinho[] itens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
            total += item.getSubtotal();
        }
        return total;
    }
}
