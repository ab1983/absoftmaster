/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.entity.Funcionario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Agnaldo
 */
@ManagedBean
@SessionScoped
public class FuncionarioBean  extends GenericBean<Funcionario> {

    /**
     * Creates a new instance of UsuarioBean
     */
    public FuncionarioBean() {
        super.url = "views/cadastro/funcionario/funcionarioList.xhtml";
        super.urlForm = "funcionarioForm.xhtml";
        super.namedFindAll = "findAllFuncionario";
        super.type = Funcionario.class;  
        super.subtitle = "Cadastro | Funcionário";
    }
}
