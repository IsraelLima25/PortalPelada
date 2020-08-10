package br.com.pelada.portal.bean;

import javax.inject.Named;

@Named
public class NavigationBean {

	public String cadastrarPelada() {
		return "/pelada/cadastro?faces-redirect=true";
	}

	public String listarPeladas() {
		return "/pelada/lista?faces-redirect=true";
	}

	public String enviarConvite() {
		return "/convite/enviarConvite?faces-redirect=true";
	}

	public String inicio() {
		return "/home?faces-redirect=true";
	}

	public String minhasPeladas() {
		return "/pelada/minhasPeladas?faces-redirect=true";
	}

	public String signup() {
		return "/login/signup?faces-redirect=true";
	}

	public String signin() {
		return "/login/signin?faces-redirect=true";
	}

}
