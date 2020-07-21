package br.com.pelada.portal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class Dao<T> {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;

	private EntityManager em;

	public Dao(EntityManager em, Class<T> classe) {
		this.em = em;
		this.classe = classe;
	}

	public void adiciona(T t) {
		this.em.persist(t);
	}

	public void remove(T t) {
		this.em.remove(em.merge(t));
	}

	public void atualiza(T t) {
		this.em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = this.em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		List<T> lista = em.createQuery(query).getResultList();
		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = this.em.find(classe, id);
		return instancia;
	}

}
