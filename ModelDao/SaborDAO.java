package com.mycompany.projetofinalpizzaria.ModelDao;

import com.mycompany.projetofinalpizzaria.Model.Sabor;
import com.mycompany.projetofinalpizzaria.Model.TipoPizza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SaborDAO {

    private final String SQL_INSERIR = "INSERT INTO sabor (nome, id_tipo_pizza) VALUES (?, ?)";
    private final String SQL_LISTAR_TODOS = 
            "SELECT s.id_sabor, s.nome, t.id_tipo_pizza, t.categoria, t.preco_cm2 " +
            "FROM sabor s " +
            "INNER JOIN tipo_pizza t ON s.id_tipo_pizza = t.id_tipo_pizza " +
            "ORDER BY s.nome";
    private final String SQL_BUSCAR_NOME = 
            "SELECT s.id_sabor, s.nome, t.id_tipo_pizza, t.categoria, t.preco_cm2 " +
            "FROM sabor s " +
            "INNER JOIN tipo_pizza t ON s.id_tipo_pizza = t.id_tipo_pizza " +
            "WHERE s.nome = ?";

    public void inserir(Sabor sabor) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR)) {
            stmt.setString(1, sabor.getSabor());
            stmt.setInt(2, sabor.getTipo().getIdTipoPizza());
            stmt.executeUpdate();
        }
    }

    public ArrayList<Sabor> listarTodos() throws Exception {
        ArrayList<Sabor> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoPizza tipo = new TipoPizza(
                    rs.getInt("id_tipo_pizza"),
                    rs.getString("categoria"),
                    rs.getDouble("preco_cm2")
                );
                lista.add(new Sabor(
                    rs.getInt("id_sabor"),
                    rs.getString("nome"),
                    tipo
                ));
            }
        }
        return lista;
    }

    public Sabor buscarPorNome(String nome) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_NOME)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TipoPizza tipo = new TipoPizza(
                        rs.getInt("id_tipo_pizza"),
                        rs.getString("categoria"),
                        rs.getDouble("preco_cm2")
                    );
                    return new Sabor(
                        rs.getInt("id_sabor"),
                        rs.getString("nome"),
                        tipo
                    );
                }
            }
        }
        return null;
    }
}