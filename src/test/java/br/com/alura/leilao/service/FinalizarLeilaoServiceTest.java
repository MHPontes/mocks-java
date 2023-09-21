package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService service;    //Instancia da classe a ser testada.

    @Mock
    private LeilaoDao leilaoDao;      //Mockando a classe LeiladoDao por anotação, a classe foi mockada pois o metodo de finalizar leilao da classe FinalizarLeilaoService, chama o metodo buscarLeiloesExpirados

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);           //Iniciando os mocks
        this.service = new FinalizarLeilaoService(leilaoDao);  //Instanciando a service e passando leilaoDao como parametro.
    }

    @Test
    void deveriaFinalizarUmLeilao() {
        service = new FinalizarLeilaoService(leilaoDao);
    }

    // Trecho de código omitido

    private List<Leilao> leiloes() {
        List<Leilao> lista = new ArrayList<>();

        Leilao leilao = new Leilao("Celular",
                new BigDecimal("500"),
                new Usuario("Fulano"));

        Lance primeiro = new Lance(new Usuario("Beltrano"),
                new BigDecimal("600"));
        Lance segundo = new Lance(new Usuario("Ciclano"),
                new BigDecimal("900"));

        leilao.propoe(primeiro);
        leilao.propoe(segundo);

        lista.add(leilao);

        return lista;


        //Esse é um método que cria uma lista de leilões em memória. Ele pega uma lista de leilões (List<Leilao> lista = new ArrayList<>()),
        // cria um leilão com um nome qualquer ("Celular"), um lance inicial de R$500 e um usuário chamado Fulano.
        // Em seguida, cria dois lances para esse leilão, um do Beltrano no valor de R$600 e um do Ciclano de R$900.
        // Por fim, ele adiciona esses dois lances ao leilão, acrescenta o leilão à lista e retorna a mesma atualizada.
    }
}