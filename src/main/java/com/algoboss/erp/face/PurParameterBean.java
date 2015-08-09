/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.PurParameter;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class PurParameterBean extends GenericBean<PurParameter> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public PurParameterBean() {        
        super.url = "views/purchase/parameter/parameterList.xhtml";
        super.namedFindAll = "findAllPurParameter";
        super.type = PurParameter.class;
        super.urlForm = "parameterForm.xhtml";
        super.subtitle = "Suprimentos | Compra | Parametro";
    }

    @Override
    public void indexBean(Long nro) throws Throwable {
        super.indexBean(nro);
    }

    @Override
    public void doBeanSave() {
        try {
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(PurParameterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        if(!getBeanList().isEmpty()){
            bean = getBeanList().get(0);
        }
        super.doBeanForm();        
    }
    
}
