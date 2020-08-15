package br.com.pelada.portal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.PeladaDao;
import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;
import br.com.pelada.portal.tx.Transactional;

@Named
@ViewScoped
public class PeladaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pelada pelada = new Pelada();

	@Inject
	private FacesContext context;

	@SuppressWarnings("unused")
	private List<Pelada> peladas = new ArrayList<>();

	private List<Pelada> todasPeladas = new ArrayList<Pelada>();

	@SuppressWarnings("unused")
	private List<Pelada> peladasUsuarioLogado = new ArrayList<>();

	@Inject
	private PeladaDao dao;

	@Inject
	private UsuarioDao daoUsuario;

	@Inject
	private PeladaDao daoPelada;

	@Inject
	private UsuarioLogBean userLog;

	public PeladaBean() {

	}

	@PostConstruct
	public void carregarPeladas() {
		this.todasPeladas = this.dao.listaTodos();
	}

	@Transactional
	public String salvar() {

		Usuario usuarioLogado = userLog.getUserLog();

		this.pelada.getUsuarios().add(usuarioLogado);

		usuarioLogado.getPeladas().add(this.pelada);

		this.dao.adiciona(this.pelada);

		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Pelada adicionada com sucesso, a bola vai rolar."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/pelada/minhasPeladas?faces-redirect=true";
	}

	@Transactional
	public String participar(Pelada pelada) {
		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = this.daoUsuario.buscaPorEmail(usuarioLogado);

		usuarioLogado.getPeladas().add(pelada);
		pelada.getUsuarios().add(usuarioLogado);

		this.daoUsuario.atualiza(usuarioLogado);
		this.daoPelada.atualiza(pelada);

		context.getExternalContext().getFlash().setKeepMessages(true);

		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Aceito com sucesso, prepare as chuteiras"));

		return "/pelada/minhasPeladas?faces-redirect=true";
	}

	public Pelada getPelada() {
		return pelada;
	}

	public void setPelada(Pelada pelada) {
		this.pelada = pelada;
	}

	public List<Pelada> getPeladas() {
		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
		return this.dao.peladasDisponiveis(usuarioLogado);

	}

	public void setPeladas(List<Pelada> peladas) {
		this.peladas = peladas;
	}

	public Map<String, String> getPeladasMap() {
		return this.dao.peladasMap();
	}

	public List<Pelada> getPeladasUsuarioLogado() {
		Usuario usuarioLogado = userLog.getUserLog();
		usuarioLogado = daoUsuario.buscaPorEmail(usuarioLogado);
		return this.dao.peladasUsuarioLogado(usuarioLogado);
	}

	public void setPeladasUsuarioLogado(List<Pelada> peladasUsuarioLogado) {
		this.peladasUsuarioLogado = peladasUsuarioLogado;
	}

	public List<Pelada> getTodasPeladas() {
		return this.todasPeladas;
	}

	public void setTodasPeladas(List<Pelada> todasPeladas) {
		this.todasPeladas = todasPeladas;
	}

}
