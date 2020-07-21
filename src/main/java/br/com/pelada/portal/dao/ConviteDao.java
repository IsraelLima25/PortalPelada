package br.com.pelada.portal.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.pelada.portal.model.Convite;
import br.com.pelada.portal.model.Usuario;

public class ConviteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Convite> dao;

	@Inject
	private EntityManager manager;
	
	@Inject
	private UsuarioDao daoUsuario;

	@PostConstruct
	private void init() {
		this.dao = new Dao<Convite>(manager, Convite.class);
	}

	public void adiciona(Convite convite) {
		this.dao.adiciona(convite);
	}

	public void atualiza(Convite convite) {
		this.dao.atualiza(convite);
	}

	public Convite buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

	public void remove(Convite convite) {
		dao.remove(convite);
	}

	public List<Convite> listaTodos() {
		return dao.listaTodos();
	}

	public List<Convite> convitesUsuarioLogado() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);

		String jpql = "select c from Convite c where c.usuario.id = :idUsuarioLogado";
		TypedQuery<Convite> typedQuery = this.manager.createQuery(jpql, Convite.class).setParameter("idUsuarioLogado",
				usuarioLogado.getId());

		List<Convite> convites = typedQuery.getResultList();

		return convites;
	
	}

}
