package com.mycompany.projetofinalpizzaria.Controller;

import com.mycompany.projetofinalpizzaria.ModelDao.EstadoDAO;
import com.mycompany.projetofinalpizzaria.ModelDao.PedidoDAO;
import com.mycompany.projetofinalpizzaria.ModelDao.SaborDAO;
import com.mycompany.projetofinalpizzaria.Model.*;
import java.util.ArrayList;

public class PedidoController {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final EstadoDAO estadoDAO = new EstadoDAO();
    private final SaborDAO saborDAO = new SaborDAO();

    public double calcularPrecoPizza(double area, ArrayList<Sabor> sabores) {
        if (sabores == null || sabores.isEmpty()) return 0;
        if (sabores.size() == 1) {
            return area * sabores.get(0).getTipo().getPrecoPorCentimentroQuadrado();
        } else {
            double p1 = sabores.get(0).getTipo().getPrecoPorCentimentroQuadrado();
            double p2 = sabores.get(1).getTipo().getPrecoPorCentimentroQuadrado();
            return area * ((p1 + p2) / 2.0);
        }
    }

    public void salvarNovoPedido(Cliente cliente, ArrayList<Pizza> pizzas, double valorTotal) throws Exception {
        if (cliente == null) {
            throw new Exception("Cliente não encontrado para atribuir ao pedido.");
        }
        if (pizzas == null || pizzas.isEmpty()) {
            throw new Exception("O pedido não pode ser criado sem nenhum item.");
        }
        Estado estadoInicial = estadoDAO.buscarPorNome("Aberto");
        if (estadoInicial == null) {
            throw new Exception("Estado inicial 'Aberto' não encontrado no banco.");
        }
        Pedido pedido = new Pedido(0, valorTotal, pizzas, estadoInicial, cliente);
        pedidoDAO.inserir(pedido);
    }

    public void atualizarPedidoExistente(int idPedido, ArrayList<Pizza> pizzas, double valorTotal) throws Exception {
        if (pizzas == null || pizzas.isEmpty()) {
            throw new Exception("O pedido não pode ficar sem nenhum item.");
        }
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            throw new Exception("Pedido não encontrado.");
        }
        pedido.setPizza(pizzas);
        pedido.setPrecoTotal(valorTotal);
        pedidoDAO.atualizarPedido(pedido);
    }

    public void alterarEstadoPedido(int idPedido, String nomeNovoEstado) throws Exception {
        Estado estado = estadoDAO.buscarPorNome(nomeNovoEstado);
        if (estado == null) {
            throw new Exception("Estado inválido.");
        }
        pedidoDAO.atualizarEstado(idPedido, estado.getIdEstado());
    }

    public ArrayList<Pedido> listarTodosPedidos() throws Exception {
        return pedidoDAO.listarTodos();
    }

    public ArrayList<Pedido> listarPedidosDoCliente(int idCliente) throws Exception {
        return pedidoDAO.listarPorCliente(idCliente);
    }

    public Pedido buscarPedidoPorId(int idPedido) throws Exception {
        return pedidoDAO.buscarPorId(idPedido);
    }

    public ArrayList<Estado> listarEstados() throws Exception {
        return estadoDAO.listarTodos();
    }
}