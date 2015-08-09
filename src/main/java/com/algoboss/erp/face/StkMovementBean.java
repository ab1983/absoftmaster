/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.dao.StkMovementDao;
import com.algoboss.erp.entity.StkMovement;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkMovementBean extends GenericBean<StkMovement> {


    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkMovementBean() {
        super.url = "views/stock/movement/movementList.xhtml";
        //super.namedFindAll = "findAllPurSolicitation";
        super.type = StkMovement.class;
        //super.urlForm = "solicitationForm.xhtml";
        super.subtitle = "Suprimentos | Estoque | Movimentação";
        super.formRendered = false;
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        for (int i = 0; i < beanList.size(); i++) {
            StkMovement stkMovement = beanList.get(i);
            StkMovementDao.updateCurrentBalance(stkMovement.getSupplyItem());            
        }
    }

}
