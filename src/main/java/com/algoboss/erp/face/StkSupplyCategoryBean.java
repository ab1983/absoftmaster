/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.entity.StkSupplyCategory;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkSupplyCategoryBean extends GenericBean<StkSupplyCategory> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkSupplyCategoryBean() {        
        super.url = "views/cadastro/stk/supply-category/supplyCategoryList.xhtml";
        super.namedFindAll = "findAllStkSupplyCategory";
        super.type = StkSupplyCategory.class;
        super.urlForm = "supplyCategoryForm.xhtml";
        super.subtitle = "Cadastro | Estoque | Categoria do Suprimento";
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        super.getBean().setCode(Integer.toString(super.getBeanList().size()+1));
    }
    
}
