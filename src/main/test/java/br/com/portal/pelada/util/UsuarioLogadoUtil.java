package br.com.portal.pelada.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pelada.portal.dao.Dao;
import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;

public class UsuarioLogadoUtil {

	private static Usuario usuarioLogado;
	private static EntityManager manager;
	private static UsuarioDao daoUsuario;

	private UsuarioLogadoUtil() {
	}
	
	public static Usuario getUsuarioLogado(Integer id){
		
		daoInitialize();
		
		usuarioLogado = new Usuario();
		usuarioLogado.setId(id);
		usuarioLogado.setEmail("israelslf22@gmail.com");
		usuarioLogado = daoUsuario.buscaPorId(usuarioLogado.getId());
		
		return usuarioLogado;		
	}

	private static void daoInitialize() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("portal");
		manager = entityManagerFactory.createEntityManager();

		daoUsuario = new UsuarioDao();
		daoUsuario.setDao(new Dao<Usuario>(manager, Usuario.class));
	}

}
