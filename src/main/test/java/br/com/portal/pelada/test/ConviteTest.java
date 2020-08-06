package br.com.portal.pelada.test;

import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.com.pelada.portal.dao.ConviteDao;
import br.com.pelada.portal.dao.Dao;
import br.com.pelada.portal.model.Convite;
import br.com.pelada.portal.model.Usuario;
import br.com.portal.pelada.util.UsuarioLogadoUtil;

public class ConviteTest {

	private ConviteDao daoConvite;
	private EntityManager manager;
	private Usuario usuarioLogado;

	@Before
	public void configurationInitial() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("portal");
		this.manager = entityManagerFactory.createEntityManager();

		this.daoConvite = new ConviteDao(manager);
		this.daoConvite.setDao(new Dao<Convite>(manager, Convite.class));
		
		this.usuarioLogado = UsuarioLogadoUtil.getUsuarioLogado(13);
	}

	@Test
	public void listarConvitesUsuarioLogado() {
		List<Convite> convitesUsuarioLogado = this.daoConvite.getConvitesUsuarioLogado(this.usuarioLogado);

		if (convitesUsuarioLogado.isEmpty()) {
			fail();
		}

		for (Convite convite : convitesUsuarioLogado) {
			if (convite.getUsuario().getId() != 13) {
				fail();
			}
		}

	}
}
