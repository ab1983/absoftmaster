/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.GerFornecedor;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class GerFornecedorBean extends GenericBean<GerFornecedor> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public GerFornecedorBean() {        
        super.url = "views/cadastro/ger/fornecedor/fornecedorList.xhtml";
        super.namedFindAll = "findAllGerFornecedor";
        super.type = GerFornecedor.class;
        super.urlForm = "fornecedorForm.xhtml";
        super.subtitle = "Cadastro | Fornecedor";
                
    }
}
