package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoDaoTest {

    @Test
    void buscarTodos() {
        LeilaoDao mock = Mockito.mock(LeilaoDao.class);     //Mockando a classe LeilaoDao
        List<Leilao> todos = mock.buscarTodos();          //Chamando o metodo buscarTodos de nosso mock e armazenado numa lista Leilao, pois o metodo retorna uma lista.
        Assert.assertTrue(todos.isEmpty());               // Validando se a lista esta vazia, como deveria fazer uma consulta no banco, deve retornar vazia.
    }
}