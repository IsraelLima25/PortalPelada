package br.com.pelada.portal.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pelada.portal.model.Convite;
import br.com.pelada.portal.model.Usuario;

public class ConviteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Convite> dao;

	@Inject
	private EntityManager manager;

	public ConviteDao() {

	}

	public ConviteDao(EntityManager entityManager) {
		this.manager = entityManager;
	}

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

	public List<Convite> getConvitesUsuarioLogado(Usuario usuarioLogado) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Convite> criteriaQuery = criteriaBuilder.createQuery(Convite.class);
		Root<Convite> root = criteriaQuery.from(Convite.class);

		criteriaQuery.select(root);

		Path<Integer> usuarioPath = root.join("usuario").get("id");

		List<Predicate> predicates = new ArrayList<>();

		Predicate usuarioIgual = criteriaBuilder.equal(usuarioPath, usuarioLogado.getId());
		predicates.add(usuarioIgual);

		criteriaQuery.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Convite> typedQuery = manager.createQuery(criteriaQuery);

		List<Convite> convites = typedQuery.getResultList();

		return convites;

	}

	public void setDao(Dao<Convite> dao) {
		this.dao = dao;
	}

}
