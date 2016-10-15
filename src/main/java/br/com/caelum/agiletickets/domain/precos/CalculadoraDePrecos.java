package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		
		BigDecimal preco = calculaPrecoGeral(sessao); 
		
		BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
		
		return preco.multiply(quantidadeBigDecimal);
	}

	private static BigDecimal calculaPrecoGeral(Sessao sessao) {
		BigDecimal preco;
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoCinemaShow(sessao);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPrecoBallet(sessao);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
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