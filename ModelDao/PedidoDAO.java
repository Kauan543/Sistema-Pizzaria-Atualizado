package com.mycompany.projetofinalpizzaria.ModelDao;

import com.mycompany.projetofinalpizzaria.Model.*;
import java.sql.*;
import java.util.ArrayList;

public class PedidoDAO {

    private final String SQL_INSERIR_PEDIDO = 
            "INSERT INTO pedido (preco_total, id_cliente, id_estado) VALUES (?, ?, ?) RETURNING id_pedido";
    private final String SQL_ATUALIZAR_PEDIDO = 
            "UPDATE pedido SET preco_total = ? WHERE id_pedido = ?";
    private final String SQL_DELETAR_PIZZAS_PEDIDO = 
            "DELETE FROM pizza WHERE id_pedido = ?";
    private final String SQL_INSERIR_PIZZA = 
            "INSERT INTO pizza (id_pedido, forma, dimensao, area, valor_unitario) VALUES (?, ?, ?, ?, ?) RETURNING id_pizza";
    private final String SQL_INSERIR_PIZZA_SABOR = 
            "INSERT INTO pizza_sabor (id_pizza, id_sabor) VALUES (?, ?)";
    private final String SQL_ATUALIZAR_ESTADO = 
            "UPDATE pedido SET id_estado = ? WHERE id_pedido = ?";
    private final String SQL_LISTAR_TODOS = 
            "SELECT p.id_pedido, p.preco_total, " +
            "c.id_cliente, c.nome AS nome_cli, c.sobrenome, c.telefone, " +
            "e.id_estado, e.nome AS nome_est " +
            "FROM pedido p " +
            "INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
            "INNER JOIN estado e ON p.id_estado = e.id_estado " +
            "ORDER BY p.id_pedido DESC";
    private final String SQL_LISTAR_POR_CLIENTE = 
            "SELECT p.id_pedido, p.preco_total, " +
            "c.id_cliente, c.nome AS nome_cli, c.sobrenome, c.telefone, " +
            "e.id_estado, e.nome AS nome_est " +
            "FROM pedido p " +
            "INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
            "INNER JOIN estado e ON p.id_estado = e.id_estado " +
            "WHERE p.id_cliente = ? " +
            "ORDER BY p.id_pedido DESC";
    private final String SQL_BUSCAR_POR_ID = 
            "SELECT p.id_pedido, p.preco_total, " +
            "c.id_cliente, c.nome AS nome_cli, c.sobrenome, c.telefone, " +
            "e.id_estado, e.nome AS nome_est " +
            "FROM pedido p " +
            "INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
            "INNER JOIN estado e ON p.id_estado = e.id_estado " +
            "WHERE p.id_pedido = ?";
    private final String SQL_BUSCAR_PIZZAS_PEDIDO = 
            "SELECT id_pizza, forma, area, valor_unitario FROM pizza WHERE id_pedido = ?";
    private final String SQL_BUSCAR_SABORES_PIZZA = 
            "SELECT s.id_sabor, s.nome, t.id_tipo_pizza, t.categoria, t.preco_cm2 " +
            "FROM pizza_sabor ps " +
            "INNER JOIN sabor s ON ps.id_sabor = s.id_sabor " +
            "INNER JOIN tipo_pizza t ON s.id_tipo_pizza = t.id_tipo_pizza " +
            "WHERE ps.id_pizza = ?";

    public void inserir(Pedido pedido) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            int idPedidoGerado = -1;
            try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR_PEDIDO)) {
                stmt.setDouble(1, pedido.getPrecoTotal());
                stmt.setInt(2, pedido.getCliente().getIdCliente());
                stmt.setInt(3, pedido.getEstado().getIdEstado());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idPedidoGerado = rs.getInt("id_pedido");
                        pedido.setIdPedido(idPedidoGerado);
                    }
                }
            }

            inserirPizzasDoPedido(conn, idPedidoGerado, pedido.getPizza());
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void atualizarPedido(Pedido pedido) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR_PEDIDO)) {
                stmt.setDouble(1, pedido.getPrecoTotal());
                stmt.setInt(2, pedido.getIdPedido());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmtDel = conn.prepareStatement(SQL_DELETAR_PIZZAS_PEDIDO)) {
                stmtDel.setInt(1, pedido.getIdPedido());
                stmtDel.executeUpdate();
            }

            inserirPizzasDoPedido(conn, pedido.getIdPedido(), pedido.getPizza());
            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private void inserirPizzasDoPedido(Connection conn, int idPedido, ArrayList<Pizza> pizzas) throws Exception {
        for (Pizza p : pizzas) {
            int idPizzaGerada = -1;
            try (PreparedStatement stmtP = conn.prepareStatement(SQL_INSERIR_PIZZA)) {
                stmtP.setInt(1, idPedido);
                stmtP.setString(2, p.getForma().getClass().getSimpleName());

                double dimensao = extrairDimensao(p.getForma());
                if (dimensao > 0) {
                    stmtP.setDouble(3, dimensao);
                } else {
                    stmtP.setNull(3, Types.NUMERIC);
                }

                stmtP.setDouble(4, p.getArea());
                stmtP.setDouble(5, p.getValorUnitario());

                try (ResultSet rs = stmtP.executeQuery()) {
                    if (rs.next()) {
                        idPizzaGerada = rs.getInt("id_pizza");
                    }
                }
            }

            for (Sabor s : p.getSabor()) {
                try (PreparedStatement stmtPS = conn.prepareStatement(SQL_INSERIR_PIZZA_SABOR)) {
                    stmtPS.setInt(1, idPizzaGerada);
                    stmtPS.setInt(2, s.getIdSabor());
                    stmtPS.executeUpdate();
                }
            }
        }
    }

    private double extrairDimensao(Forma f) {
        if (f instanceof Quadrado) return ((Quadrado) f).getLado();
        if (f instanceof Circulo) return ((Circulo) f).getRaio();
        if (f instanceof Triangulo) return ((Triangulo) f).getLado();
        return 0;
    }

    public void atualizarEstado(int idPedido, int idEstado) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR_ESTADO)) {
            stmt.setInt(1, idEstado);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        }
    }

    public ArrayList<Pedido> listarTodos() throws Exception {
        return consultarPedidos(SQL_LISTAR_TODOS, null);
    }

    public ArrayList<Pedido> listarPorCliente(int idCliente) throws Exception {
        return consultarPedidos(SQL_LISTAR_POR_CLIENTE, idCliente);
    }

    public Pedido buscarPorId(int idPedido) throws Exception {
        ArrayList<Pedido> res = consultarPedidos(SQL_BUSCAR_POR_ID, idPedido);
        return res.isEmpty() ? null : res.get(0);
    }

    private ArrayList<Pedido> consultarPedidos(String sql, Integer parametro) throws Exception {
        ArrayList<Pedido> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (parametro != null) {
                stmt.setInt(1, parametro);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome_cli"),
                        rs.getString("sobrenome"),
                        rs.getString("telefone")
                    );
                    Estado e = new Estado(
                        rs.getInt("id_estado"),
                        rs.getString("nome_est")
                    );
                    int idPedido = rs.getInt("id_pedido");
                    ArrayList<Pizza> pizzas = buscarPizzasDoPedido(conn, idPedido);

                    lista.add(new Pedido(idPedido, rs.getDouble("preco_total"), pizzas, e, c));
                }
            }
        }
        return lista;
    }

    private ArrayList<Pizza> buscarPizzasDoPedido(Connection conn, int idPedido) throws Exception {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_PIZZAS_PEDIDO)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idPizza = rs.getInt("id_pizza");
                    String nomeForma = rs.getString("forma");
                    double area = rs.getDouble("area");
                    double valorUnitario = rs.getDouble("valor_unitario");

                    Forma f;
                    switch (nomeForma) {
                        case "Circulo": f = new Circulo(); break;
                        case "Triangulo": f = new Triangulo(); break;
                        default: f = new Quadrado(); break;
                    }

                    ArrayList<Sabor> sabores = buscarSaboresDaPizza(conn, idPizza);
                    Pizza p = new Pizza(area, valorUnitario, f, sabores);
                    p.setIdPizza(idPizza);
                    pizzas.add(p);
                }
            }
        }
        return pizzas;
    }

    private ArrayList<Sabor> buscarSaboresDaPizza(Connection conn, int idPizza) throws Exception {
        ArrayList<Sabor> sabores = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_SABORES_PIZZA)) {
            stmt.setInt(1, idPizza);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoPizza tp = new TipoPizza(
                        rs.getInt("id_tipo_pizza"),
                        rs.getString("categoria"),
                        rs.getDouble("preco_cm2")
                    );
                    sabores.add(new Sabor(rs.getInt("id_sabor"), rs.getString("nome"), tp));
                }
            }
        }
        return sabores;
    }
}