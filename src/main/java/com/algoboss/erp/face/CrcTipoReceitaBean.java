/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.CrcTipoReceita;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class CrcTipoReceitaBean extends GenericBean<CrcTipoReceita> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public CrcTipoReceitaBean() {        
        super.url = "views/cadastro/crc-tipo-receita/crcTipoReceitaList.xhtml";
        super.namedFindAll = "findAllCrcTipoReceita";
        super.type = CrcTipoReceita.class;
        super.urlForm = "crcTipoReceitaForm.xhtml";
        super.subtitle = "Cadastro | Tipo Receita";
    }
}
