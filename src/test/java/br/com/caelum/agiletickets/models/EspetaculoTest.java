package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void criaUmaSessaoSeADataInicioEDataFimForemIguais(){
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate dataInicio = new LocalDate().withYear(2016).withMonthOfYear(10).withDayOfMonth(22);
		LocalDate dataFinal  = dataInicio;
		LocalTime horario = new LocalTime().withHourOfDay(10).withMillisOfDay(30);
		
		java.util.List<Sessao> listaSessoes = espetaculo.criaSessoes(dataInicio, dataFinal, horario, Periodicidade.DIARIA);
		
		Assert.assertTrue(listaSessoes.size() == 1);		
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void impedeCriacaoDeSessaoComDataFinalMenorQueADataIniciar(){
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate dataInicio = new LocalDate().withYear(2016).withMonthOfYear(10).withDayOfMonth(22);
		LocalDate dataFinal  = dataInicio.minusDays(10);
		LocalTime horario = new LocalTime().withHourOfDay(10).withMillisOfDay(30);
		
		espetaculo.criaSessoes(dataInicio, dataFinal, horario, Periodicidade.DIARIA);
	
	}
	
	@Test
	public void criaCincoSessoesDePeriodicidadeDiaria(){
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate dataInicio = new LocalDate().withYear(2016).withMonthOfYear(10).withDayOfMonth(22);
		LocalDate dataFinal  = dataInicio.plusDays(4);
		LocalTime horario = new LocalTime().withHourOfDay(10).withMillisOfDay(30);
		
		java.util.List<Sessao> listaSessoes = espetaculo.criaSessoes(dataInicio, dataFinal, horario, Periodicidade.DIARIA);
		
		Assert.assertTrue(listaSessoes.size() == 5);		
		
	}
	
	@Test
	public void criaUmaSessaoSemanalSeADataInicioEDataFimForemIguais(){
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate dataInicio = new LocalDate().withYear(2016).withMonthOfYear(10).withDayOfMonth(22);
		LocalDate dataFinal  = dataInicio;
		LocalTime horario = new LocalTime().withHourOfDay(10).withMillisOfDay(30);
		
		java.util.List<Sessao> listaSessoes = espetaculo.criaSessoes(dataInicio, dataFinal, horario, Periodicidade.SEMANAL);
		
		Assert.assertTrue(listaSessoes.size() == 1);		
		
	}
	
	@Test
	public void criaCincoSessoesSemanais(){
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate dataInicio = new LocalDate().withYear(2016).withMonthOfYear(10).withDayOfMonth(22);
		LocalDate dataFinal  = dataInicio.plusWeeks(4);
		LocalTime horario = new LocalTime().withHourOfDay(10).withMillisOfDay(30);
		
		java.util.List<Sessao> listaSessoes = espetaculo.criaSessoes(dataInicio, dataFinal, horario, Periodicidade.SEMANAL);
		
		Assert.assertTrue(listaSessoes.size() == 4);		
		
	}

}
