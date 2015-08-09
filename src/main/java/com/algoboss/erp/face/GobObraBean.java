/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.dao.GerClienteDao;
import com.algoboss.erp.entity.GerCliente;
import com.algoboss.erp.entity.GobObra;
import com.algoboss.erp.entity.GobUnidadeObra;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class GobObraBean extends GenericBean<GobObra> {
    @Inject
    private BaseDao baseDao;
    private List<GerCliente> clienteList;
    private boolean showChildren = false;
    private String urlChildren;
    private String urlChildrenForm;
    private GobUnidadeObra unidadeObra;

    /**
     * Creates a new instance of ClienteBean
     */
    public GobObraBean() {
        super.url = "views/cadastro/obra/obraList.xhtml";
        super.urlForm = "obraForm.xhtml";
        super.namedFindAll = "findAllGobObra";
        super.type = GobObra.class;
        super.subtitle = "Cadastro | Obra";
        urlChildren = "unidadeObraList.xhtml";
        urlChildrenForm = "unidadeObraForm.xhtml";
        unidadeObra = new GobUnidadeObra();
    }

    @Override
    public void indexBean(Long nro) throws Throwable {
        clienteList = GerClienteDao.findAll();
        showChildren = false;
        unidadeObra = new GobUnidadeObra();
        super.indexBean(nro);
    }

    public List<GerCliente> getClienteList() {
        return clienteList;
    }

    public boolean isShowChildren() {
        return showChildren;
    }

    public String getUrlChildren() {
        return urlChildren;
    }

    public GobUnidadeObra getUnidadeObra() {
        return unidadeObra;
    }

    public void setUnidadeObra(GobUnidadeObra unidadeObra) {
        this.unidadeObra = unidadeObra;
    }

    public String getUrlChildrenForm() {
        return urlChildrenForm;
    }

    public void setUrlChildrenForm(String urlChildrenForm) {
        this.urlChildrenForm = urlChildrenForm;
    }

    @Override
    public void doBeanSave() {
        try {
            bean.setProjeto(bean);
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(GobObraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doUnidadeObraSave() {
        try {
            unidadeObra.setStatus("Andamento");
            unidadeObra.getCronograma().setProjeto(bean.getProjeto());
            unidadeObra.getCronograma().setDataHoraInicioPrevisto(unidadeObra.getDataTerminoPrevisto());
            unidadeObra.getCronograma().setDataHoraTerminoPrevisto(unidadeObra.getDataTerminoPrevisto());
            unidadeObra.getCronograma().setTipoEvento("Cronograma Obra");
            unidadeObra.getCronograma().setDescricao("Concluir Unidade:" + unidadeObra.getSequencia() + "/ Status Imovel:" + unidadeObra.getStatusImovel());

            baseDao.save(unidadeObra);
            unidadeObra = new GobUnidadeObra();
            doUnidadeObraList();
        } catch (Throwable ex) {
            Logger.getLogger(GobObraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doUnidadeObraList() {
        unidadeObra = new GobUnidadeObra();
        List<GobUnidadeObra> unidadeObraList = new ArrayList();
        //if(bean.getUnidadeObraList().isEmpty()){
        for (int i = 0; i < bean.getUnidades(); i++) {
            GobUnidadeObra uo = new GobUnidadeObra();
            uo.setSequencia(i + 1);
            uo.setDataTerminoPrevisto(null);
            uo.setStatus("Pendente");
            uo.setStatusImovel("Pendente");
            uo.setObra(bean);

            for (GobUnidadeObra gobUnidadeObra : bean.getUnidadeObraList()) {
                if (gobUnidadeObra.getSequencia() == uo.getSequencia()) {
                    uo = gobUnidadeObra;
                    break;
                }
            }
            if (uo.getStatus().equals("Pendente")) {
            }
            if (uo.getStatus().equals("Andamento")) {
            }
            if (uo.getStatus().equals("Concluido")) {
            }
            unidadeObraList.add(uo);
        }
        bean.setUnidadeObraList(unidadeObraList);
        //}
        showChildren = true;
    }

    public void doBeanSaveAndShowChildren() {
        try {
            bean.setProjeto(bean);
            bean = (GobObra)baseDao.save(bean);
            doUnidadeObraList();
        } catch (Throwable ex) {
            Logger.getLogger(GobObraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
