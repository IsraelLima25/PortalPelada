package br.com.pelada.portal.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.pelada.portal.model.Usuario;

public class Authorization implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();

		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		if (usuarioLogado != null && nomePagina.equals("/login/signin.xhtml")) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/home?faces-redirect=true");
			context.renderResponse();
			return;
		}

		if ("/login/signin.xhtml".equals(nomePagina)) {
			return;
		}

		if (usuarioLogado != null) {
			return;
		}

		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login/signin?faces-redirect=true");
		context.renderResponse();

	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
