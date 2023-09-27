package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class GeradorDePagamentoTest {
    
    private GeradorDePagamento gerador;   //Instancia da classe a ser testada.
    
    @Mock
    private PagamentoDao pagamentoDao;   //Mockando a classe PagamentoDao por anotação

    @Captor
    private ArgumentCaptor<Pagamento> captor;      /* Existe um conceito no Mockito chamado Captor, para capturar
                                                    um determinado objeto.
                                                    Pagamento pagamento = new Pagamento(lanceVencedor, vencimento);
                                                    Portanto, sempre que quisermos capturar um objeto que foi passado por um método de um Mock, podemos utilizar um Captor. */


    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);           //Iniciando os mocks
        this.gerador = new GeradorDePagamento(pagamentoDao);  
    }

    @Test
    void deveriaCriarPagamentoParaVencedorDoLeilao(){
        Leilao leilao = leilao();
        Lance vencedor = leilao.getLanceVencedor();
        gerador.gerarPagamento(vencedor);

        Mockito.verify(pagamentoDao).salvar(captor.capture()); // passamos o método .salvar(captor.capture()), que captura o objeto passado como parâmetro para o método salvar().

        Pagamento pagamento = captor.getValue();   // Na sequência, precisamos pegar o objeto capturado para fazer os Asserts com ele.

        Assertions.assertEquals(LocalDate.now().plusDays(1),pagamento.getVencimento()); //Validando se a data de pagamento sera para o dia posterior.
        Assertions.assertEquals(vencedor.getValor(),pagamento.getValor()); //Validando se o valor do lance e o mesmo do valor a ser pago no gerador de pagamento
        Assertions.assertFalse(pagamento.getPago()); //Validando como False que o pagamento ainda nao foi efetuado
        Assertions.assertEquals(vencedor.getUsuario(), pagamento.getUsuario()); //Validando se o usuario do lance e o mesmo que fara o pagamento
        Assertions.assertEquals(leilao,pagamento.getLeilao()); //Validando se os leiloes sao os mesmos.
    }

    private Leilao leilao() {
        Leilao leilao = new Leilao("Celular",
                new BigDecimal("500"),
                new Usuario("Fulano"));

        Lance lance = new Lance(new Usuario("Ciclano"),
                new BigDecimal("900"));

        leilao.propoe(lance);
        leilao.setLanceVencedor(lance);

        return leilao;
    }
}