package br.com.caelum.agiletickets.models;

import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;
import br.com.caelum.vraptor.util.jpa.EntityManagerFactoryCreator;

public class EspetaculoTest {

	private Estabelecimento estabelecimento = new Estabelecimento();
	private Espetaculo espetaculo = new Espetaculo();
	private EntityManager manager = null;

	@Test
	public void deveCriarNumeroDeSessoesCorretasParaPeriodoDiario()
			throws Exception {

		LocalDate inicio = new LocalDate(2012, 8, 5);
		LocalDate fim = inicio.plusDays(12);
		LocalTime horario = new LocalTime(21, 30);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		List<Sessao> listaSessao = espetaculo.criaListaSessoes(inicio, fim,
				horario, periodicidade);

		Assert.assertEquals(12, listaSessao.size());
	}

	@Before
	public void criarRegistroEspetaculo() throws Exception {

		EntityManagerFactoryCreator creator = new EntityManagerFactoryCreator();
		creator.create();
		EntityManagerCreator managerCreator = new EntityManagerCreator(
				creator.getInstance());
		managerCreator.create();
		manager = managerCreator.getInstance();

		manager.getTransaction().begin();

		estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Casa de shows");
		estabelecimento.setEndereco("Rua dos Silveiras, 12345");

		espetaculo = new Espetaculo();
		espetaculo.setEstabelecimento(estabelecimento);
		espetaculo.setNome("Depeche Mode");
		espetaculo.setTipo(TipoDeEspetaculo.SHOW);

		manager.persist(estabelecimento);
		manager.persist(espetaculo);

	}

	@After
	public void removerRegistroEspetaculo() throws Exception {

		manager.remove(espetaculo);
		manager.remove(estabelecimento);

		manager.getTransaction().commit();
		manager.close();
	}

	@Test
	public void deveInserirSessoes() throws Exception {

		LocalDate inicio = new LocalDate(2012, 8, 5);
		LocalDate fim = inicio.plusDays(12);
		LocalTime horario = new LocalTime(21, 30);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		boolean sucesso = espetaculo.criaSessoes(inicio, fim, horario,
				periodicidade);

		Assert.assertTrue(sucesso);
	}
}
