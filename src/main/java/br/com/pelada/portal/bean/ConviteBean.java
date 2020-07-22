package br.com.pelada.portal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.ConviteDao;
import br.com.pelada.portal.dao.PeladaDao;
import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Convite;
import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;
import br.com.pelada.portal.tx.Transactional;

@Named
@RequestScoped
public class ConviteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Convite convite = new Convite();
	private Usuario usuario = new Usuario();
	private Pelada pelada = new Pelada();

	@Inject
	private FacesContext context;

	@SuppressWarnings("unused")
	private List<Convite> convites = new ArrayList<>();

	@Inject
	private UsuarioDao daoUsuario;

	@Inject
	private PeladaDao daoPelada;

	@Inject
	private ConviteDao daoConvite;
	
	@Inject
	private UsuarioLogBean userLog;

	@Transactional
	public void enviarConvite() {
		this.convite.setPeladaConvite(daoPelada.buscaPorId(this.pelada.getId()));
		this.convite.setUsuario(daoUsuario.buscaPorId(this.usuario.getId()));
		this.daoConvite.adiciona(this.convite);

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Enviado"));
	}

	@Transactional
	public void recusarConvite(Convite convite) {

		this.daoConvite.remove(convite);
		context.getExternalContext().getFlash().setKeepMessages(true);

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Recusado"));
	}

	@Transactional
	public String aceitarConvite(Convite convite) {

		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = this.daoUsuario.buscaPorEmail(usuarioLogado);

		Pelada pelada = convite.getPeladaConvite();

		usuarioLogado.getPeladas().add(pelada);
		pelada.getUsuarios().add(usuarioLogado);

		this.daoUsuario.atualiza(usuarioLogado);
		this.daoPelada.atualiza(pelada);

		this.daoConvite.remove(convite);

		context.getExternalContext().getFlash().setKeepMessages(true);

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Aceito"));

		return "/pelada/minhasPeladas?faces-redirect=true";

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pelada getPelada() {
		return pelada;
	}

	public void setPelada(Pelada pelada) {
		this.pelada = pelada;
	}

	public Convite getConvite() {
		return convite;
	}

	public void setConvite(Convite convite) {
		this.convite = convite;
	}

	public List<Convite> getConvites() {
		return this.daoConvite.convitesUsuarioLogado();
	}

	public void setConvites(List<Convite> convites) {
		this.convites = convites;
	}
}
