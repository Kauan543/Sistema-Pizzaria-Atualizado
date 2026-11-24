package com.mycompany.projetofinalpizzaria.View;

import com.mycompany.projetofinalpizzaria.Controller.PedidoController;
import com.mycompany.projetofinalpizzaria.Model.Pedido;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class PreencherTabelaListaPedidos {

    public DefaultTableModel gerarModelo() throws Exception {
        String[] colunas = {"N° Pedido", "Quantidade Pizza(s)", "Estado", "Valor"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        PedidoController controller = new PedidoController();
        ArrayList<Pedido> pedidos = controller.listarTodosPedidos();

        for (Pedido p : pedidos) {
            modelo.addRow(new Object[]{
                p.getIdPedido(),
                p.getPizza().size(),
                p.getEstado().getNome(),
                String.format("R$ %.2f", p.getPrecoTotal())
            });
        }
        return modelo;
    }
}