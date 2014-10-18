/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.entity.GerCentroCusto;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class CentroCustoBean extends GenericBean<GerCentroCusto> {

    /**
     * Creates a new instance of CentroCustoBean
     */
    public CentroCustoBean() {        
        super.url = "views/cadastro/centro-custo/centroCustoList.xhtml";
        super.namedFindAll = "findAllCentroCusto";
        super.type = GerCentroCusto.class;
        super.subtitle = "Cadastro | Centro Custo";
    }
}
