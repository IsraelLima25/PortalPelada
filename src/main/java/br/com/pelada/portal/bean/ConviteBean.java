package br.com.pelada.portal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.selectonemenu.SelectOneMenu;

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

	private SelectOneMenu selectUsuarios;

	@Inject
	private FacesContext context;

	@SuppressWarnings("unused")
	private List<Convite> convites = new ArrayList<>();

	private List<Usuario> usuarios = new ArrayList<>();

	@Inject
	private UsuarioDao daoUsuario;

	@Inject
	private PeladaDao daoPelada;

	@Inject
	private ConviteDao daoConvite;

	@Inject
	private UsuarioLogBean userLog;

	@Transactional
	public String enviarConvite() {
		this.convite.setPeladaConvite(daoPelada.buscaPorId(this.pelada.getId()));
		this.convite.setUsuario(daoUsuario.buscaPorId(this.usuario.getId()));
		this.daoConvite.adiciona(this.convite);

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage("messagesGLobal", new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Enviado"));

		return "/convite/enviarConvite?faces-redirect=true";
	}

	@Transactional
	public String recusarConvite(Convite convite) {

		this.daoConvite.remove(convite);
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Recusado"));

		return "/home/faces-redirect=true";
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

	public void carregarUsuariosNaoRelacionadosPeladaSelecionada() {
		if (this.pelada == null) {
			this.selectUsuarios.setDisabled(true);
		} else {
			Pelada pelada = this.daoPelada.buscaPorId(this.pelada.getId());
			this.usuarios = this.daoUsuario.listaUsuariosNaoRelacionadosPelada(pelada);
			this.selectUsuarios.setDisabled(false);
		}
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
		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);

		return this.daoConvite.getConvitesUsuarioLogado(usuarioLogado);
	}

	public void setConvites(List<Convite> convites) {
		this.convites = convites;
	}

	public SelectOneMenu getSelectUsuarios() {
		return selectUsuarios;
	}

	public void setSelectUsuarios(SelectOneMenu selectUsuarios) {
		this.selectUsuarios = selectUsuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
