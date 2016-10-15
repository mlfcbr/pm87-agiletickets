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
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			return calculaPrecoCinemaShow(sessao);
		} 
		
		if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			return calculaPrecoBalletOrquestra(sessao);
		} 
		
		return sessao.getPreco();
	}

	private static BigDecimal calculaPrecoBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		BigDecimal precoSessao = sessao.getPreco();
		
		if(calculoDeOcupacao(sessao) <= 0.50) { 
			preco = precoSessao.add(precoSessao.multiply(BigDecimal.valueOf(0.20)));
		} else {
			preco = precoSessao;
		}
		
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(precoSessao.multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaPrecoCinemaShow(Sessao sessao) {
		BigDecimal preco;
		BigDecimal precoSessao = sessao.getPreco();
		
		if(calculoDeOcupacao(sessao) <= 0.05) { 
			preco = precoSessao.add(precoSessao.multiply(BigDecimal.valueOf(0.10)));
		} else {
			preco = precoSessao;
		}
		return preco;
	}
	
	private static double calculoDeOcupacao(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
	}

}