package main.service;

import main.models.Carrinho;
import main.models.ItemCarrinho;
import main.models.Pedido;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static main.util.ArquivoUtil.gravarId;
import static main.util.ArquivoUtil.lerId;

public class PedidoService {

    public static void finalizarPedido(Carrinho carrinho, String arqIdPedido, String arqPedido, String arqProduto) {

        if (carrinho.getItens().length == 0) {
            System.err.println("Carrinho vazio. Não é possível finalizar o pedido.");
            return;
        }

        int idPedido = lerId(arqIdPedido);
        if (idPedido == -1) {
            System.err.println("Erro ao gerar ID do pedido");
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdCliente(carrinho.getIdCliente());
        pedido.setItens(carrinho.getItens());
        for (ItemCarrinho item : carrinho.getItens()) {
            ProdutoService.baixarEstoque(
                    item.getProduto().getId(),
                    item.getQuantidade(),
                    arqProduto
            );
        }

        pedido.setData(new Date());

        salvarPedido(pedido, arqPedido);
        gravarId(idPedido + 1, arqIdPedido);

        System.out.println("Pedido finalizado com sucesso!");
        System.out.println("Valor total: R$ " + pedido.getValorTotal());
    }

    private static void salvarPedido(Pedido p, String arq) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(arq, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(p.getId()).append(";")
                    .append(p.getIdCliente()).append(";");

            ItemCarrinho[] itens = p.getItens();

            for (int i = 0; i < itens.length; i++) {
                sb.append(itens[i].getProduto().getId())
                        .append("x")
                        .append(itens[i].getQuantidade());

                if (i < itens.length - 1) {
                    sb.append(",");
                }
            }

            pw.println(sb);

        } catch (IOException e) {
            System.err.println("Erro ao salvar pedido.");
            e.printStackTrace();
        }
    }
}
