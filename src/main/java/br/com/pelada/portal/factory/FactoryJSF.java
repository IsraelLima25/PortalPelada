package br.com.pelada.portal.factory;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FactoryJSF implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@RequestScoped
	public FacesContext context() {
		return FacesContext.getCurrentInstance();
	}

}
