package com.mycompany.projetofinalpizzaria.ModelDao;

import com.mycompany.projetofinalpizzaria.Model.TipoPizza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoPizzaDAO {

    private final String SQL_LISTAR_TODOS = "SELECT id_tipo_pizza, categoria, preco_cm2 FROM tipo_pizza ORDER BY id_tipo_pizza";
    private final String SQL_BUSCAR_CATEGORIA = "SELECT id_tipo_pizza, categoria, preco_cm2 FROM tipo_pizza WHERE categoria = ?";
    private final String SQL_ATUALIZAR_PRECO = "UPDATE tipo_pizza SET preco_cm2 = ? WHERE categoria = ?";

    public ArrayList<TipoPizza> listarTodos() throws Exception {
        ArrayList<TipoPizza> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new TipoPizza(
                    rs.getInt("id_tipo_pizza"),
                    rs.getString("categoria"),
                    rs.getDouble("preco_cm2")
                ));
            }
        }
        return lista;
    }

    public TipoPizza buscarPorCategoria(String categoria) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_CATEGORIA)) {
            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoPizza(
                        rs.getInt("id_tipo_pizza"),
                        rs.getString("categoria"),
                        rs.getDouble("preco_cm2")
                    );
                }
            }
        }
        return null;
    }

    public void atualizarPreco(String categoria, double novoPreco) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_ATUALIZAR_PRECO)) {
            stmt.setDouble(1, novoPreco);
            stmt.setString(2, categoria);
            stmt.executeUpdate();
        }
    }
}