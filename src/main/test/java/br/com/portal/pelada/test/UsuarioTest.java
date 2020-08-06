package br.com.portal.pelada.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.pelada.portal.dao.Dao;
import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;
import br.com.portal.pelada.util.UsuarioLogadoUtil;

public class UsuarioTest {

	private Usuario usuarioLogado;
	private EntityManager manager;
	private UsuarioDao daoUsuario;

	@Before
	public void configurationInitial() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("portal");
		this.manager = entityManagerFactory.createEntityManager();

		this.daoUsuario = new UsuarioDao(manager);
		this.daoUsuario.setDao(new Dao<Usuario>(manager, Usuario.class));

		this.usuarioLogado = UsuarioLogadoUtil.getUsuarioLogado(13);
	}

	@Test
	public void usuarioExiste() {
		boolean usuarioExiste = this.daoUsuario.usuarioExiste(this.usuarioLogado);
		assertTrue(usuarioExiste);
	}

	@Test
	public void buscarUsuarioByEmail() {

		Usuario usuarioRetorno = this.daoUsuario.buscaPorEmail(this.usuarioLogado);

		boolean usuarioValido;

		if (usuarioRetorno.getEmail().equals("israelslf22@gmail.com")) {
			usuarioValido = true;
		} else {
			usuarioValido = false;
		}

		assertTrue(usuarioValido);
	}

	@Test
	public void listarUsuariosDeslogados() {

		Map<String, String> listaUsuariosDeslogados = this.daoUsuario.listaUsuariosDeslogados(this.usuarioLogado);
		List<Usuario> listaTodosUsuarios = this.daoUsuario.listaTodos();
		assertTrue(listaUsuariosDeslogados.size() == listaTodosUsuarios.size() - 1);
	}

	@Test
	public void emailCadastroUsuarioRepetido() {

		Usuario usuario = new Usuario("Israel Santos", "liminha", "israelslf22@gmail.com", "30230584");
		List<Usuario> listaTodosPrevAdd = this.daoUsuario.listaTodos();

		try {
			this.manager.getTransaction().begin();
			this.daoUsuario.adiciona(usuario);
			this.manager.getTransaction().commit();
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			throw new RuntimeException(e);
		}

		List<Usuario> listaTodosNextAdd = this.daoUsuario.listaTodos();

		if (listaTodosNextAdd.size() > listaTodosPrevAdd.size()) {
			Assert.fail();
		}

	}

}
