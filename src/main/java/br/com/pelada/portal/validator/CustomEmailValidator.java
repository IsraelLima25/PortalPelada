package br.com.pelada.portal.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.pelada.portal.dao.UsuarioDao;
import br.com.pelada.portal.model.Usuario;

@Named("br.com.portal.pelada.email_validator")
public class CustomEmailValidator implements Validator {

	@Inject
	private UsuarioDao daoUsuario;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String email = String.valueOf(value);

		boolean existe = daoUsuario.usuarioExiste(new Usuario(email));

		if (existe) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, null, "Email já cadastrado");
			((UIInput) component).setValid(false);
			context.addMessage(component.getClientId(), message);
		} else if (!existe && !email.equals("")) {
			((UIInput) component).setValid(true);

		}

	}

}
