package com.mycompany.projetofinalpizzaria.View;

import com.mycompany.projetofinalpizzaria.Controller.ClienteController;
import com.mycompany.projetofinalpizzaria.Model.Cliente;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class PreencherTabelaCliente {

    public DefaultTableModel gerarModelo() throws Exception {
        String[] colunas = {"Nome", "Sobrenome", "Telefone"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        ClienteController controller = new ClienteController();
        ArrayList<Cliente> clientes = controller.listarTodos();

        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{c.getNome(), c.getSobrenome(), c.getTelefone()});
        }
        return modelo;
    }

    public DefaultTableModel gerarModeloFiltrado(String termo) throws Exception {
        String[] colunas = {"Nome", "Sobrenome", "Telefone"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        ClienteController controller = new ClienteController();
        ArrayList<Cliente> clientes = controller.buscarPorSobrenome(termo);
        if(clientes == null || clientes.isEmpty()){
            Cliente c = controller.buscarPorTelefone(termo);
            modelo.addRow(new Object[]{c.getNome(), c.getSobrenome(), c.getTelefone()});
        }
        else{
            for (Cliente c : clientes) {
                modelo.addRow(new Object[]{c.getNome(), c.getSobrenome(), c.getTelefone()});
            }
        }
        return modelo;
    }
}