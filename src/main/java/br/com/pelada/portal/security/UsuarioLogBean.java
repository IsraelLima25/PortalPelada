package br.com.pelada.portal.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;

@Named
@SessionScoped
public class UsuarioLogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;

	@Inject
	private UsuarioDao daoUsuario;

	public Usuario getUserLog() {
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		return usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
	}

	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login/signin?faces-redirect=true";
	}

}
