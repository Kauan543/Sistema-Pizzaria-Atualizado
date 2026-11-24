package com.mycompany.projetofinalpizzaria.View;

import com.mycompany.projetofinalpizzaria.Controller.PedidoController;
import com.mycompany.projetofinalpizzaria.Model.Cliente;
import com.mycompany.projetofinalpizzaria.Model.Pedido;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class PreecherTabelaPedidoCliente {

    public DefaultTableModel gerarModelo2(Cliente cliente) throws Exception {
        String[] colunas = {"N° Pedido", "Valor", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        PedidoController controller = new PedidoController();
        ArrayList<Pedido> pedidos = controller.listarPedidosDoCliente(cliente.getIdCliente());

        for (Pedido p : pedidos) {
            modelo.addRow(new Object[]{
                p.getIdPedido(),
                String.format("R$ %.2f", p.getPrecoTotal()),
                p.getEstado().getNome()
            });
        }
        return modelo;
    }
}