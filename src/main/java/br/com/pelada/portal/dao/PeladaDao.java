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

import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;

public class PeladaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Pelada> dao;

	@Inject
	private EntityManager manager;

	@Inject
	private UsuarioDao daoUsuario;

	@PostConstruct
	private void init() {
		this.dao = new Dao<Pelada>(manager, Pelada.class);
	}

	public void adiciona(Pelada pelada) {
		this.dao.adiciona(pelada);
	}

	public void atualiza(Pelada pelada) {
		this.dao.atualiza(pelada);
	}

	public Pelada buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

	public void remove(Pelada pelada) {
		dao.remove(pelada);
	}

	public List<Pelada> listaTodos() {
		return dao.listaTodos();
	}

	public List<Pelada> peladasDisponiveis() {

		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
		
		String jpql = "select distinct p from Pelada p join p.usuarios u join u.pelada where u.id <> :idUsuarioLogado";
		TypedQuery<Pelada> typedQuery = this.manager.createQuery(jpql, Pelada.class).setParameter("idUsuarioLogado",
				usuarioLogado.getId());

		List<Pelada> peladas = typedQuery.getResultList();

		return peladas;

	}

	public boolean usuarioExiste(Usuario usuario) {
		String jpql = "select u from Usuario u where u.email = :email and u.senha = :senha";
		TypedQuery<Usuario> typedQuery = this.manager.createQuery(jpql, Usuario.class)
				.setParameter("email", usuario.getEmail()).setParameter("senha", usuario.getSenha());
		Usuario user = typedQuery.getSingleResult();
		return user != null;
	}

	public Map<String, String> peladasMap() {
		Map<String, String> mapaPeladas = new HashMap<>();
		List<Pelada> peladas = listaTodos();
		for (Pelada pelada : peladas) {
			mapaPeladas.put(pelada.getNome(), pelada.getId().toString());
		}

		return mapaPeladas;

	}

	public List<Pelada> peladasUsuarioLogado() {

		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
		String jpql = "select distinct p from Pelada p join fetch p.usuarios u where u.id = :usuarioLogado";
		TypedQuery<Pelada> typedQuery = this.manager.createQuery(jpql, Pelada.class).setParameter("usuarioLogado",
				usuarioLogado.getId());
		List<Pelada> resultList = typedQuery.getResultList();

		return resultList;
	}

}
