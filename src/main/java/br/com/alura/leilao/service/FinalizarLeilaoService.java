package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinalizarLeilaoService {

	private LeilaoDao leiloes;

	@Autowired
	public FinalizarLeilaoService(LeilaoDao leiloes) {             //Sempre que trabalhamos com testes automatizados, é considerada uma boa prática injetar as dependências sempre pelo construtor.
		                                                         // Isso porque o construtor já deixa óbvio quais as dependências daquela classe e, com ele, conseguimos passar um Mock como parâmetro para os testes. Isso simplifica o processo.
		this.leiloes = leiloes;
	}
	public void finalizarLeiloesExpirados() {
		List<Leilao> expirados = leiloes.buscarLeiloesExpirados();
		expirados.forEach(leilao -> {
			Lance maiorLance = maiorLanceDadoNoLeilao(leilao);
			leilao.setLanceVencedor(maiorLance);
			leilao.fechar();
			leiloes.salvar(leilao);
		});
	}

	private Lance maiorLanceDadoNoLeilao(Leilao leilao) {
//		List<Lance> lancesDoLeilao = leilao.getLances();
		List<Lance> lancesDoLeilao = new ArrayList<>(leilao.getLances()); //Temos que alterar esse código, pois não podemos ter uma lista unmodifiable para o TESTE.
		// Nós criaremos um new ArrayList, passando como parâmetro os elementos do leilao.getLances. Com o ArrayList, conseguimos chamar o método sort().
		lancesDoLeilao.sort((lance1, lance2) -> {
			return lance2.getValor().compareTo(lance1.getValor());
		});
		return lancesDoLeilao.get(0);
	}
	
}
