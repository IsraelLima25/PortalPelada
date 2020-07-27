package br.com.pelada.portal.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.PeladaDao;
import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Pelada;
import br.com.pelada.portal.model.Usuario;
import br.com.pelada.portal.security.UsuarioLogBean;
import br.com.pelada.portal.tx.Transactional;

@Named
@RequestScoped
public class PeladaBean {

	private Pelada pelada = new Pelada();

	@Inject
	private FacesContext context;

	@SuppressWarnings("unused")
	private List<Pelada> peladas = new ArrayList<>();

	@SuppressWarnings("unused")
	private Map<String, String> peladasMap = new HashMap<>();

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

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Convite Aceito com sucesso, prepare as chuteiras"));

		return "/pelada/minhasPeladas?faces-redirect=true";

	}

	public Pelada getPelada() {
		return pelada;
	}

	public void setPelada(Pelada pelada) {
		this.pelada = pelada;
	}

	public List<Pelada> getPeladas() {
		return this.dao.peladasDisponiveis();
	}

	public void setPeladas(List<Pelada> peladas) {
		this.peladas = peladas;
	}

	public Map<String, String> getPeladasMap() {
		return this.dao.peladasMap();
	}

	public void setPeladasMap(Map<String, String> peladasMap) {
		this.peladasMap = peladasMap;
	}

	public List<Pelada> getPeladasUsuarioLogado() {
		return this.dao.peladasUsuarioLogado();
	}

	public void setPeladasUsuarioLogado(List<Pelada> peladasUsuarioLogado) {
		this.peladasUsuarioLogado = peladasUsuarioLogado;
	}

}
