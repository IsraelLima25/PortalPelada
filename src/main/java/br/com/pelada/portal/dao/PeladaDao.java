package br.com.pelada.portal.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;

public class PeladaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Pelada> dao;

	@Inject
	private EntityManager manager;

	@PostConstruct
	private void init() {
		this.dao = new Dao<Pelada>(manager, Pelada.class);
	}

	public PeladaDao() {

	}

	public PeladaDao(EntityManager entityManager) {
		this.manager = entityManager;
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

	public List<Pelada> peladasDisponiveis(Usuario usuarioLogado) {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Pelada> cq = cb.createQuery(Pelada.class);

		Subquery<Integer> subquery = cq.subquery(Integer.class);

		Root<Pelada> rootPelada = cq.from(Pelada.class);
		Root<Pelada> rootSubQuery = subquery.from(Pelada.class);

		Join<Pelada, List<Usuario>> join = rootSubQuery.join("usuarios");
		subquery.where(cb.equal(join.get("id"), usuarioLogado.getId()));

		subquery.select(rootSubQuery.get("id"));
		cq.select(rootPelada);

		cq.where(cb.not(rootPelada.<Integer>get("id").in(subquery)));

		TypedQuery<Pelada> typedQuery = manager.createQuery(cq);

		List<Pelada> peladasDiponiveisPorUsuario = typedQuery.getResultList();

		return peladasDiponiveisPorUsuario;

	}

	public Map<String, String> peladasMap() {
		Map<String, String> mapaPeladas = new HashMap<>();
		List<Pelada> peladas = listaTodos();
		for (Pelada pelada : peladas) {
			mapaPeladas.put(pelada.getNome(), pelada.getId().toString());
		}

		return mapaPeladas;

	}

	public List<Pelada> peladasUsuarioLogado(Usuario usuarioLogado) {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Pelada> cq = cb.createQuery(Pelada.class);

		Root<Pelada> root = cq.from(Pelada.class);
		Join<Pelada, List<Usuario>> join = root.join("usuarios");
		cq.select(root).distinct(true).where(cb.equal(join.<Integer>get("id"), usuarioLogado.getId()));

		TypedQuery<Pelada> typedQuery = this.manager.createQuery(cq);
		List<Pelada> listaPeladasUsuarioLogado = typedQuery.getResultList();

		return listaPeladasUsuarioLogado;

	}

	public void setDao(Dao<Pelada> dao) {
		this.dao = dao;
	}

}
