package br.com.portal.pelada.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.com.pelada.portal.dao.Dao;
import br.com.pelada.portal.dao.PeladaDao;
import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;
import br.com.portal.pelada.util.UsuarioLogadoUtil;

public class PeladaTest {

	private Usuario usuarioLogado;
	private EntityManager manager;
	private PeladaDao daoPelada;

	@Before
	public void configurationInitial() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("portal");
		this.manager = entityManagerFactory.createEntityManager();

		this.daoPelada = new PeladaDao(manager);
		this.daoPelada.setDao(new Dao<Pelada>(manager, Pelada.class));

		this.usuarioLogado = UsuarioLogadoUtil.getUsuarioLogado(13);
	}

	@Test
	public void listarPeladasNaoRelacionadasUsuarioLogado() {
		List<Pelada> peladasDisponiveis = this.daoPelada.peladasDisponiveis(this.usuarioLogado);
		assertTrue(peladasDisponiveis.isEmpty());
	}

	@Test
	public void listaPeladasUsuarioLogado() {
		List<Pelada> peladasUsuarioLogado = this.daoPelada.peladasUsuarioLogado(this.usuarioLogado);
		assertTrue(peladasUsuarioLogado.size() == 16);
	}
}
