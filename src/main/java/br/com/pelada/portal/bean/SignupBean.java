package br.com.pelada.portal.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.tx.Transactional;

@Named
@RequestScoped
public class SignupBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	@Inject
	private FacesContext context;

	@Inject
	private UsuarioDao dao;

	@Transactional
	public String registrarUsuario() {

		this.dao.adiciona(usuario);
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Cadastrado",
				"Seja Bem Vindo " + usuario.getNome()));
		this.usuario = new Usuario();
		return "/login/signup?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
