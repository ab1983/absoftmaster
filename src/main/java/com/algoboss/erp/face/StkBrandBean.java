/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.StkBrand;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkBrandBean extends GenericBean<StkBrand> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkBrandBean() {        
        super.url = "views/cadastro/stk/brand/brandList.xhtml";
        super.namedFindAll = "findAllStkBrand";
        super.type = StkBrand.class;
        super.urlForm = "brandForm.xhtml";
        super.subtitle = "Cadastro | Estoque | Marca";
    }
    @Override
    public void doBeanList() {
        super.doBeanList();
        super.getBean().setCode(Integer.toString(super.getBeanList().size()+1));
    }    
}
