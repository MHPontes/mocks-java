package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService service;    //Instancia da classe a ser testada.

    @Mock
    private LeilaoDao leilaoDao;      //Mockando a classe LeiladoDao por anotação, a classe foi mockada pois o metodo de finalizar leilao da classe FinalizarLeilaoService, chama o metodo buscarLeiloesExpirados
    @Mock
    private EnviadorDeEmails enviadorDeEmails;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);           //Iniciando os mocks
        this.service = new FinalizarLeilaoService(leilaoDao, enviadorDeEmails);  //Instanciando a service e passando leilaoDao como parametro e enviadorDeEmails
    }

    @Test
    void deveriaFinalizarUmLeilao() {
        List<Leilao> leiloes = leiloes();     //Instanciando a Lista de leiloes numa variavel

        Mockito.when(leilaoDao.buscarLeiloesExpirados())
                .thenReturn(leiloes);        //Quando o metodo do nosso mock leilaoDao, for chamado, retorna a lista de leiloes criada abaixo

        service.finalizarLeiloesExpirados(); //Metodo a ser testado

        Leilao leilao = leiloes.get(0);   //Armazenando o leilao
        Assertions.assertEquals(new BigDecimal("900"), leilao.getLanceVencedor().getValor());
        Assertions.assertTrue(leilao.isFechado());

        Mockito.verify(leilaoDao).salvar(leilao);    //Assertiva no Mock, validando se ira salvar no banco (testando somente a logica)

    }

    @Test
    void deveriaEnviarEmailParaVencedorDoLeilao() {
        List<Leilao> leiloes = leiloes();     //Instanciando a Lista de leiloes numa variavel

        Mockito.when(leilaoDao.buscarLeiloesExpirados())
                .thenReturn(leiloes);        //Quando o metodo do nosso mock leilaoDao, for chamado, retorna a lista de leiloes criada abaixo

        service.finalizarLeiloesExpirados(); //Metodo a ser testado

        Leilao leilao = leiloes.get(0);
        Lance lanceVencedor = leilao.getLanceVencedor();

        Mockito.verify(enviadorDeEmails).enviarEmailVencedorLeilao(lanceVencedor);    //Assertiva no Mock, validando se ira enviar email ao vencedor(testando somente a logica)

    }

    @Test
    void naoDeveriaEnviarEmailParaVencedorDoLeilaoEmCasoDeErrroAoEncerrarOLeilao() {
        List<Leilao> leiloes = leiloes();     //Instanciando a Lista de leiloes numa variavel

        Mockito.when(leilaoDao.buscarLeiloesExpirados())
                .thenReturn(leiloes);        //Quando o metodo do nosso mock leilaoDao, for chamado, retorna a lista de leiloes criada abaixo

        Mockito.when(leilaoDao.salvar(Mockito.any())).
                thenThrow(RuntimeException.class);        //Forçando uma exception ao chamar metodo salvar.

        try {
            service.finalizarLeiloesExpirados();
            Mockito.verifyNoInteractions(enviadorDeEmails);
        } catch (Exception e) {

        }
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