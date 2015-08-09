/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.dao.UsuarioDao;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.face.GerLoginBean;

/**
 *	
 * @author Agnaldo
 */
@FacesValidator(value = "LoginValidator")
public class LoginValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;
    @Inject
    protected BaseDao baseDao;
    @Inject
    protected GerLoginBean loginBean;

    public LoginValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        SecUser user = new UsuarioDao(baseDao).findByEmailAndPassword(loginBean.getUser().getEmail().toUpperCase(), String.valueOf(value));
        boolean isValidate = false;
        if (user != null) {
            if (!user.isInactive()) {
                isValidate = true;
            }
        }
        matcher = pattern.matcher(value.toString());
        if (!isValidate) {

            FacesMessage msg =
                    new FacesMessage("Login validation failed.",
                    "Invalid Login format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
