package br.com.pelada.portal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;
import br.com.pelada.portal.tx.Transactional;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	@SuppressWarnings("unused")
	private List<Usuario> usuarios = new ArrayList<>();

	@Inject
	private FacesContext context;

	@Inject
	private UsuarioDao dao;

	@Inject
	private UsuarioLogBean userLog;

	@Transactional
	public String salvar() {

		if (this.dao.adiciona(usuario)) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Cadastrado",
					"Seja Bem Vindo " + usuario.getNome()));
			this.usuario = new Usuario();

			return "/login/signin?faces-redirect=true";
		} else {
			
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuario já existe",
					"Usuario " + usuario.getEmail() + " já cadastrado"));
			
			this.usuario = new Usuario();

			return "/login/signin?faces-redirect=true";
		}
	}

	public String logar() {
		boolean existe = this.dao.usuarioExiste(this.usuario);
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "/home?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Acesso Negado", "Usuário Inválido"));

		return "/login/signin?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<String, String> getUsuarios() {
		Usuario usuarioLogado = userLog.getUserLog();
		return this.dao.listaUsuariosDeslogados(usuarioLogado);
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
