package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		
		BigDecimal preco = calculaPrecoGeral(sessao); 
		
		BigDecimal quantidadeIngressosSolicitados = BigDecimal.valueOf(quantidade);
		
		return preco.multiply(quantidadeIngressosSolicitados);
	}

	private static BigDecimal calculaPrecoGeral(Sessao sessao) {
		BigDecimal preco;
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoCinemaShow(sessao);
		} else if(tipo.equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPrecoBallet(sessao);
		} else if(tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPrecoBallet(sessao);
		}  else {
			preco = sessao.getPreco();
		}
		return preco;
	}

	private static BigDecimal calculaPrecoBallet(Sessao sessao) {
		BigDecimal preco;
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.50) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
		} else {
			preco = sessao.getPreco();
		}
		
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		//quando estiver acabando os ingressos... 
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.05) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

}