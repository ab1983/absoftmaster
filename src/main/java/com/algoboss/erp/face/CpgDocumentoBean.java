/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.dao.GrtProjetoDao;
import com.algoboss.erp.entity.CpgDocumento;
import com.algoboss.erp.entity.CpgItemDocumento;
import com.algoboss.erp.entity.GerFornecedor;
import com.algoboss.erp.entity.GrtProjeto;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class CpgDocumentoBean extends GenericBean<CpgDocumento> {
    @Inject
    private BaseDao baseDao;
    @Inject
    private CpgTipoDespesaBean tipoDespesaBean;
    @Inject
    private CentroCustoBean centroCustoBean;
    @Inject
    private GerTipoDocumentoBean tipoDocumentoBean;
    @Inject
    private GerFornecedorBean fornecedorBean;

    private CpgItemDocumento cpgItemDocumento;

    private BigDecimal totalItem;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public CpgDocumentoBean() {
        super.url = "views/contas-pagar/cpg-documento/cpgDocumentoList.xhtml";
        super.namedFindAll = "findAllCpgDocumento";
        super.type = CpgDocumento.class;
        super.urlForm = "cpgDocumentoForm.xhtml";
        super.subtitle = "Financeiro | Contas a Pagar | Documento CP";
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
            Long siteId = null;
            if (getLoginBean().getInstantiatesSiteContract() != null && getLoginBean().getInstantiatesSiteContract().getInstantiatesSiteId() != null) {
                siteId = getLoginBean().getInstantiatesSiteContract().getInstantiatesSiteId();
            }
            List<Long> siteIdList = new ArrayList();
            siteIdList.add(siteId);

            fornecedorBean.doBeanList();
            tipoDespesaBean.doBeanList();
            centroCustoBean.doBeanList();
            tipoDocumentoBean.doBeanList();
            cpgItemDocumento = new CpgItemDocumento();
        } catch (Throwable ex) {
            Logger.getLogger(CpgDocumentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        cpgItemDocumento = new CpgItemDocumento();
        super.doBeanList();
    }

    @Override
    public void doBeanForm() {
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addItem() {
        String strMsg = "";
        if (cpgItemDocumento.getValor() == null) {
            strMsg = "Valor obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (cpgItemDocumento.getCentroCusto() == null) {
            strMsg = "Centro de Custo obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        if (cpgItemDocumento.getTipoDespesa() == null) {
            strMsg = "Tipo de Despesa obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        if (strMsg.isEmpty()) {
            if (cpgItemDocumento.getItemDocumentoId() == null) {
                Long seqnum = 1L;
                if (!bean.getCpgItemDocumentoList().isEmpty()) {
                    seqnum = (Long) bean.getCpgItemDocumentoList().get(bean.getCpgItemDocumentoList().size() - 1).getSeqnum() + 1L;
                }
                cpgItemDocumento.setSeqnum(seqnum);
                bean.getCpgItemDocumentoList().add(cpgItemDocumento);
            }
            calcularTotalItem();
            GrtProjeto projeto = GrtProjetoDao.findByCentroCusto(cpgItemDocumento.getCentroCusto().getCentroCustoId());
            GerFornecedor forn = bean.getGerFornecedor();
            cpgItemDocumento.getCronograma().setProjeto(projeto);
            cpgItemDocumento.getCronograma().setDataHoraInicioPrevisto(bean.getDataVencimento());
            cpgItemDocumento.getCronograma().setDataHoraTerminoPrevisto(bean.getDataVencimento());
            cpgItemDocumento.getCronograma().setTipoEvento("Contas a Pagar");
            cpgItemDocumento.getCronograma().setDescricao("Pagar Fornec:" + forn.getRazaoSocial() + "/N.Docto:" + bean.getNumeroDocto() + "/ R$ " + new DecimalFormat("#.00").format(cpgItemDocumento.getValor()));
            cpgItemDocumento = new CpgItemDocumento();
        }
    }

    public void removeItem() {
        bean.getCpgItemDocumentoList().remove(cpgItemDocumento);
        calcularTotalItem();
        cpgItemDocumento = new CpgItemDocumento();
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelCpg");
    }

    public void cancelEditItem() {
        cpgItemDocumento = new CpgItemDocumento();
    }

    public CpgItemDocumento getCpgItemDocumento() {
        return cpgItemDocumento;
    }

    public void setCpgItemDocumento(CpgItemDocumento cpgItemDocumento) {
        this.cpgItemDocumento = cpgItemDocumento;
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    private void calcularTotalItem() {
        totalItem = new BigDecimal(0);
        for (CpgItemDocumento item : bean.getCpgItemDocumentoList()) {
            totalItem = totalItem.add(new BigDecimal(item.getValor()));
        }
    }
}
