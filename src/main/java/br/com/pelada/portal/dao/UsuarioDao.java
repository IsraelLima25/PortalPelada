package br.com.pelada.portal.dao;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Usuario> dao;

	@Inject
	private EntityManager manager;

	@Inject
	private UsuarioLogBean userLog;

	public UsuarioDao() {
	}

	public UsuarioDao(EntityManager entityManager) {
		this.manager = entityManager;
	}

	@PostConstruct
	private void init() {
		this.dao = new Dao<Usuario>(manager, Usuario.class);
	}

	public boolean adiciona(Usuario usuario) {
		this.dao.adiciona(usuario);
		return true;
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

		CriteriaBuilder cb = this.manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

		Root<Usuario> root = cq.from(Usuario.class);

		cq.select(root).where(cb.equal(root.get("email"), usuario.getEmail()));

		TypedQuery<Usuario> typedQuery = manager.createQuery(cq);

		Usuario user = typedQuery.getSingleResult();

		return user;
	}

	public Map<String, String> listaUsuariosDeslogados(Usuario usuarioLogado) {

		Map<String, String> mapUsuarios = new HashMap<>();

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

		Root<Usuario> root = cq.from(Usuario.class);

		cq.select(root).distinct(true).where(cb.not(cb.equal(root.get("email"), usuarioLogado.getEmail())));

		TypedQuery<Usuario> typedQuery = this.manager.createQuery(cq);
		List<Usuario> usuarios = typedQuery.getResultList();

		for (Usuario usuario : usuarios) {
			mapUsuarios.put(usuario.getNome(), usuario.getId().toString());
		}

		return mapUsuarios;
	}

	public boolean usuarioExiste(Usuario usuario) {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		Predicate conjuncao = cb.conjunction();

		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

		Root<Usuario> rootUsuario = cq.from(Usuario.class);

		if (usuario.getEmail() != null) {
			Path<String> email = rootUsuario.<String>get("email");
			Predicate predicateEmail = cb.equal(email, usuario.getEmail());
			conjuncao = cb.and(predicateEmail);
		}

		if (usuario.getSenha() != null) {
			Path<String> senha = rootUsuario.<String>get("senha");
			Predicate predicateSenha = cb.equal(senha, usuario.getSenha());
			conjuncao = cb.and(conjuncao, predicateSenha);
		}

		cq.select(rootUsuario).where(conjuncao);

		TypedQuery<Usuario> typedQuery = manager.createQuery(cq);
		List<Usuario> users = typedQuery.getResultList();

		return users.size() > 0;

	}

	public void setDao(Dao<Usuario> dao) {
		this.dao = dao;
	}

	public List<Usuario> listaUsuariosNaoRelacionadosPelada(Pelada pelada) {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

		Root<Usuario> rootUsuario = cq.from(Usuario.class);
		Join<Usuario, List<Pelada>> join = rootUsuario.join("pelada");

		List<Predicate> predicates = new ArrayList<>();

		Predicate usuarioDiferentePelada = cb.notEqual(join.get("id"), pelada.getId());
		predicates.add(usuarioDiferentePelada);

		Predicate usuarioDiferenteLogado = cb.notEqual(rootUsuario.get("id"), userLog.getUserLog().getId());
		predicates.add(usuarioDiferenteLogado);

		cq.select(rootUsuario).where((Predicate[]) predicates.toArray(new Predicate[0]));

		List<Usuario> usuarios = this.manager.createQuery(cq).getResultList();

		return usuarios;
	}

}
