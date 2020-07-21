package br.com.pelada.portal.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.pelada.portal.model.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Usuario> dao;

	@Inject
	private EntityManager manager;

	@PostConstruct
	private void init() {
		this.dao = new Dao<Usuario>(manager, Usuario.class);
	}

	public void adiciona(Usuario usuario) {
		this.dao.adiciona(usuario);
	}

	public void atualiza(Usuario usuario) {
		this.dao.atualiza(usuario);
	}

	public Usuario buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario buscaPorEmail(Usuario usuario) {
		String jpql = "select u from Usuario u where u.email = :email";
		TypedQuery<Usuario> typedQuery = this.manager.createQuery(jpql, Usuario.class).setParameter("email",
				usuario.getEmail());

		Usuario usuarioBusca = typedQuery.getSingleResult();

		return usuarioBusca;
	}

	public Map<String, String> listaUsuariosDeslogados() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		String jpql = "select u from Usuario u where u.email <> :usuarioLogadoEmail";
		TypedQuery<Usuario> typedQuery = this.manager.createQuery(jpql, Usuario.class);
				
		List<Usuario> usuarios = typedQuery.getResultList();

		Map<String, String> mapUsuarios = new HashMap<>();

		for (Usuario usuario : usuarios) {
			mapUsuarios.put(usuario.getNome(), usuario.getId().toString());
		}

		return mapUsuarios;

	}

	public boolean usuarioExiste(Usuario usuario) {

		String jpql = "select u from Usuario u where u.email = :email and u.senha = :senha";
		TypedQuery<Usuario> typedQuery = this.manager.createQuery(jpql, Usuario.class)
				.setParameter("email", usuario.getEmail()).setParameter("senha", usuario.getSenha());

		List<Usuario> lista = typedQuery.getResultList();

		return lista.size() > 0 ? true : false;

	}

}
