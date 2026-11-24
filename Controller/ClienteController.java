package com.mycompany.projetofinalpizzaria.Controller;

import com.mycompany.projetofinalpizzaria.ModelDao.ClienteDAO;
import com.mycompany.projetofinalpizzaria.Model.Cliente;
import java.util.ArrayList;

public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrarCliente(String nome, String sobrenome, String telefone) throws Exception {
        if (nome.isBlank() || sobrenome.isBlank() || telefone.isBlank()) {
            throw new Exception("Nenhum campo pode estar vazio.");
        }
        if (clienteDAO.buscarPorTelefone(telefone) != null) {
            throw new Exception("Esse telefone já está cadastrado.");
        }
        Cliente novo = new Cliente(nome, sobrenome, telefone);
        clienteDAO.inserir(novo);
    }

    public void atualizarCliente(Cliente cliente, String novoNome, String novoSobrenome, String novoTelefone) throws Exception {
        if (novoNome.isBlank() || novoSobrenome.isBlank() || novoTelefone.isBlank()) {
            throw new Exception("Nenhum campo pode estar vazio.");
        }
        // Se mudou o telefone, verifica se o novo já pertence a outro cliente
        if (!cliente.getTelefone().equals(novoTelefone) && clienteDAO.buscarPorTelefone(novoTelefone) != null) {
            throw new Exception("Esse telefone já está cadastrado para outro cliente.");
        }
        cliente.setNome(novoNome);
        cliente.setSobrenome(novoSobrenome);
        cliente.setTelefone(novoTelefone);
        clienteDAO.atualizar(cliente);
    }

    public void deletarPorTelefone(String telefone) throws Exception {
        clienteDAO.deletarPorTelefone(telefone);
    }

    public ArrayList<Cliente> listarTodos() throws Exception {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorTelefone(String telefone) throws Exception {
        return clienteDAO.buscarPorTelefone(telefone);
    }

    public ArrayList<Cliente> buscarPorSobrenome(String termo) throws Exception {
        return clienteDAO.buscarPorSobrenome(termo);
    }
}