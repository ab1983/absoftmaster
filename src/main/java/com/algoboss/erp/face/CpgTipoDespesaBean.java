/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.CpgTipoDespesa;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class CpgTipoDespesaBean extends GenericBean<CpgTipoDespesa> {

    /**
     * Creates a new instance of CpgTipoDespesaBean
     */
    public CpgTipoDespesaBean() {        
        super.url = "views/cadastro/tipo-despesa/tipoDespesaList.xhtml";
        super.namedFindAll = "findAllTipoDespesa";
        super.type = CpgTipoDespesa.class;
        super.subtitle = "Cadastro | Tipo Despesa";
    }
}
