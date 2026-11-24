package com.mycompany.projetofinalpizzaria.ModelDao;

import com.mycompany.projetofinalpizzaria.Model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClienteDAO {

    private final String SQL_INSERIR = "INSERT INTO cliente (nome, sobrenome, telefone) VALUES (?, ?, ?)";
    private final String SQL_ATUALIZAR = "UPDATE cliente SET nome = ?, sobrenome = ?, telefone = ? WHERE id_cliente = ?";
    private final String SQL_DELETAR_TELEFONE = "DELETE FROM cliente WHERE telefone = ?";
    private final String SQL_LISTAR_TODOS = "SELECT id_cliente, nome, sobrenome, telefone FROM cliente ORDER BY nome";
    private final String SQL_BUSCAR_TELEFONE = "SELECT id_cliente, nome, sobrenome, telefone FROM cliente WHERE telefone = ?";
    private final String SQL_BUSCAR_SOBRENOME = "SELECT id_cliente, nome, sobrenome, telefone FROM cliente WHERE sobrenome ILIKE ? ORDER BY nome";

    public void inserir(Cliente cliente) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Cliente cliente) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt(4, cliente.getIdCliente());
            stmt.executeUpdate();
        }
    }

    public void deletarPorTelefone(String telefone) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETAR_TELEFONE)) {
            stmt.setString(1, telefone);
            stmt.executeUpdate();
        }
    }

    public ArrayList<Cliente> listarTodos() throws Exception {
        ArrayList<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("sobrenome"),
                    rs.getString("telefone")
                ));
            }
        }
        return lista;
    }

    public Cliente buscarPorTelefone(String telefone) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_TELEFONE)) {
            stmt.setString(1, telefone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("telefone")
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Cliente> buscarPorSobrenome(String termo) throws Exception {
        ArrayList<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_SOBRENOME)) {
            stmt.setString(1, "%" + termo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("telefone")
                    ));
                }
            }
        }
        return lista;
    }
}