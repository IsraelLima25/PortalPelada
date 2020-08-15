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
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao daoUsuario;

	@Inject
	private UsuarioLogBean userLog;

	@Inject
	private ConviteDao daoConvite;

	@Inject
	private PeladaDao daoPelada;

	@Inject
	private FacesContext context;

	@SuppressWarnings("unused")
	private List<Convite> convites = new ArrayList<>();

	public List<Convite> getConvites() {
		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
		this.convites = this.daoConvite.getConvitesUsuarioLogado(usuarioLogado);
		return this.convites;
	}

	public void setConvites(List<Convite> convites) {
		this.convites = convites;
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

}
