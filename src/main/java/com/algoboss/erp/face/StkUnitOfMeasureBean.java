/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.StkUnitOfMeasure;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkUnitOfMeasureBean extends GenericBean<StkUnitOfMeasure> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkUnitOfMeasureBean() {        
        super.url = "views/cadastro/stk/unit-of-measure/unitOfMeasureList.xhtml";
        super.namedFindAll = "findAllStkUnitOfMeasure";
        super.type = StkUnitOfMeasure.class;
        super.urlForm = "unitOfMeasureForm.xhtml";
        super.subtitle = "Cadastro | Estoque | Unidade de Medida";
    }
}
