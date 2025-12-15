package main.models;

import java.util.Date;

public class Pedido {
    private Clientes id;
    private double valorTotal;
    private int garantia;
    private Date data;
    private Produto[] produtos;

    public Produto[] getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto[] produtos) {
        this.produtos = produtos;
    }



    public Clientes getId() {
        return id;
    }

    public void setId(Clientes id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
