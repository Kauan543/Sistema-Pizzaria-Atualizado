package com.mycompany.projetofinalpizzaria.Controller;

import com.mycompany.projetofinalpizzaria.ModelDao.SaborDAO;
import com.mycompany.projetofinalpizzaria.ModelDao.TipoPizzaDAO;
import com.mycompany.projetofinalpizzaria.Model.Sabor;
import com.mycompany.projetofinalpizzaria.Model.TipoPizza;
import java.util.ArrayList;

public class SaborController {

    private final SaborDAO saborDAO = new SaborDAO();
    private final TipoPizzaDAO tipoPizzaDAO = new TipoPizzaDAO();

    public void cadastrarSabor(String nomeSabor, String categoriaTipo) throws Exception {
        if (nomeSabor == null || nomeSabor.isBlank()) {
            throw new Exception("Informe o nome do sabor.");
        }
        if (saborDAO.buscarPorNome(nomeSabor) != null) {
            throw new Exception("Sabor já cadastrado.");
        }
        TipoPizza tipo = tipoPizzaDAO.buscarPorCategoria(categoriaTipo);
        if (tipo == null) {
            throw new Exception("Tipo de pizza não encontrado.");
        }
        Sabor novo = new Sabor(nomeSabor, tipo);
        saborDAO.inserir(novo);
    }

    public ArrayList<Sabor> listarSabores() throws Exception {
        return saborDAO.listarTodos();
    }

    public Sabor buscarSaborPorNome(String nome) throws Exception {
        return saborDAO.buscarPorNome(nome);
    }

    public ArrayList<TipoPizza> listarTiposPizza() throws Exception {
        return tipoPizzaDAO.listarTodos();
    }

    public TipoPizza buscarTipoPorCategoria(String categoria) throws Exception {
        return tipoPizzaDAO.buscarPorCategoria(categoria);
    }

    public void atualizarPrecoTipo(String categoria, double novoPreco) throws Exception {
        TipoPizza tipo = tipoPizzaDAO.buscarPorCategoria(categoria);
        if (tipo == null) {
            throw new Exception("Categoria não encontrada.");
        }
        
        tipo.setPrecoPorCentimentroQuadrado(novoPreco);
        tipoPizzaDAO.atualizarPreco(categoria, novoPreco);
    }
}