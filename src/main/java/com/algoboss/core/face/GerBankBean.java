/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import com.algoboss.core.entity.GerBank;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class GerBankBean extends GenericBean<GerBank> {

    /**
     * Creates a new instance of CentroCustoBean
     */
    public GerBankBean() {        
        //super.url = "views/cadastro/centro-custo/centroCustoList.xhtml";
        //super.namedFindAll = "findAllCentroCusto";
        super.type = GerBank.class;
        super.subtitle = "Cadastro | Banco";
    }
}
