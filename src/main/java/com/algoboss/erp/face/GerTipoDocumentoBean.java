/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.entity.GerTipoDocumento;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class GerTipoDocumentoBean extends GenericBean<GerTipoDocumento> {

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public GerTipoDocumentoBean() {        
        super.url = "views/cadastro/ger/tipo-documento/tipoDocumentoList.xhtml";
        super.namedFindAll = "findAllGerTipoDocumento";
        super.type = GerTipoDocumento.class;
        super.urlForm = "tipoDocumentoForm.xhtml";   
        super.subtitle = "Cadastro | Tipo Documento";
    }
}
