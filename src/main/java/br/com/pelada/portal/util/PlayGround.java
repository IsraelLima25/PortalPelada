package br.com.pelada.portal.util;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PlayGround implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("portal");
		
		@SuppressWarnings("unused")
		EntityManager manager = createEntityManagerFactory.createEntityManager();

		// TypedQuery<Usuario> createQuery = manager.createQuery("select
		// distinct(u) from Usuario u join fetch u.convites", Usuario.class);
		// List<Usuario> usuarios = createQuery.getResultList();
		//
		// for (Usuario usuario : usuarios) {
		// System.out.println(usuario);
		// System.out.println("Lista Convites");
		// System.out.println(usuario.getConvites().size());
		// System.out.println("------------------------------------------------");
		// }

		// TypedQuery<UsuarioPelada> typedQuery = manager.createQuery("select
		// new br.com.pelada.portal.dto.UsuarioPelada(usuario.id, pelada.id)"
		// + " from Usuario usuario join fetch usuario.pelada pelada",
		// UsuarioPelada.class);
		//
		// List<UsuarioPelada> usuarioPeladas = typedQuery.getResultList();
		//
		// for (UsuarioPelada usuarioPelada : usuarioPeladas) {
		// System.out.println(
		// "Id Usuario " + usuarioPelada.getIdUsuario() + " IdPelada " +
		// usuarioPelada.getIdUsuario());
		// }

		// TypedQuery<Usuario> typedQuery = manager
		// .createQuery("select distinct usuario"
		// + " from Usuario usuario join fetch usuario.pelada", Usuario.class);
		//
		// List<Usuario> usuarios = typedQuery.getResultList();

		/* Usando API do Criteria */

//		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//		criteriaQuery.multiselect(criteriaBuilder.count(criteriaQuery.from(Pelada.class)));
//		System.out.println(manager.createQuery(criteriaQuery).getSingleResult());

		// CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		// CriteriaQuery<Pelada> criteriaQuery =
		// criteriaBuilder.createQuery(Pelada.class);
		// Root<Pelada> root = criteriaQuery.from(Pelada.class);
		// criteriaQuery.select(root);
		// TypedQuery<Pelada> typedQuery = manager.createQuery(criteriaQuery);
		// List<Pelada> peladas = typedQuery.getResultList();
		// System.out.println(peladas.size());

		// CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		// CriteriaQuery<Produto> query = criteriaBuilder
		// .createQuery(Produto.class);
		// Root<Produto> root = query.from(Produto.class);
		//
		// Path<String> nomePath = root.get("nome");
		// Path<Integer> categoriaPath = root.join("categorias").get("id");
		// Path<Integer> lojaPath = root.get("loja").get("id");
		//
		// List<Predicate> predicates = new ArrayList<>();
		//
		// if (!nome.isEmpty()) {
		// Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome +
		// "%");
		// predicates.add(nomeIgual);
		// }
		//
		// if (categoriaId != null) {
		// Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath,
		// categoriaId);
		// predicates.add(categoriaIgual);
		// }
		//
		// if (lojaId != null) {
		// Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
		// predicates.add(lojaIgual);
		// }
		//
		// query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		//
		// TypedQuery<Produto> typedQuery = em.createQuery(query);
		//
		// return typedQuery.getResultList();

		// String sql = "SELECT" + " P.ID, P.NOME, P.DATA, P.HORA, P.LOCAL" + "
		// FROM PELADA P "
		// + "WHERE ID NOT IN (SELECT P.ID " + "FROM USUARIO U INNER JOIN
		// PELADAS_HAS_USUARIO PU "
		// + "ON U.ID = PU.USUARIO_ID INNER JOIN PELADA P " + "ON P.ID =
		// PU.PELADA_ID "
		// + "WHERE U.ID = :idUsuarioLogado)";

//		CriteriaBuilder cb = manager.getCriteriaBuilder();
//		CriteriaQuery<Pelada> cq = cb.createQuery(Pelada.class);
//
//		Subquery<Integer> subquery = cq.subquery(Integer.class);
//
//		Root<Pelada> rootPelada = cq.from(Pelada.class);
//		Root<Pelada> rootSubQuery = subquery.from(Pelada.class);
//
//		Join<Pelada, List<Usuario>> join = rootSubQuery.join("usuarios");
//		subquery.where(cb.equal(join.get("id"), 13));
//		
//		subquery.select(rootSubQuery.get("id"));
//		cq.select(rootPelada);
//		
//		cq.where(cb.not(rootPelada.<Integer>get("id").in(subquery)));
//		
//		TypedQuery<Pelada> typedQuery = manager.createQuery(cq);
//		
//		List<Pelada> lista = typedQuery.getResultList();
//		
//		System.out.println(lista.size());


	}

}
