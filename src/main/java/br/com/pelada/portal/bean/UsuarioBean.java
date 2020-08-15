package br.com.pelada.portal.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;

@Named
@RequestScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, String> usuarios = new HashMap<>();

	@Inject
	private UsuarioLogBean userLog;

	private Usuario usuarioLogado;

	@Inject
	private UsuarioDao dao;

	public Map<String, String> getUsuarios() {
		return usuarios;
//		this.usuarioLogado = getUsuarioLogado();
//		return this.dao.listaUsuariosDeslogados(getUsuarioLogado());
	}

	public void setUsuarios(Map<String, String> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioLogado() {
		Usuario usuarioLogado = userLog.getUserLog();
		return usuarioLogado;
	}
}
