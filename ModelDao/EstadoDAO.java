package com.mycompany.projetofinalpizzaria.ModelDao;

import com.mycompany.projetofinalpizzaria.Model.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EstadoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT id_estado, nome FROM estado ORDER BY id_estado";
    private final String SQL_BUSCAR_NOME = "SELECT id_estado, nome FROM estado WHERE nome = ?";

    public ArrayList<Estado> listarTodos() throws Exception {
        ArrayList<Estado> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estado(
                    rs.getInt("id_estado"),
                    rs.getString("nome")
                ));
            }
        }
        return lista;
    }

    public Estado buscarPorNome(String nome) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCAR_NOME)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Estado(
                        rs.getInt("id_estado"),
                        rs.getString("nome")
                    );
                }
            }
        }
        return null;
    }
}