/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.dao.GrtProjetoDao;
import com.algoboss.erp.entity.CrcDocumento;
import com.algoboss.erp.entity.CrcItemDocumento;
import com.algoboss.erp.entity.GerCliente;
import com.algoboss.erp.entity.GrtProjeto;
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

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class CrcDocumentoBean extends GenericBean<CrcDocumento> {

    @Inject
    private BaseDao baseDao;
    @Inject
    private CrcTipoReceitaBean tipoReceitaBean;
    @Inject
    private CentroCustoBean centroCustoBean;
    @Inject
    private GerTipoDocumentoBean tipoDocumentoBean;
    @Inject
    private GerClienteBean clienteBean;
    private CrcItemDocumento crcItemDocumento;
    private BigDecimal totalItem;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public CrcDocumentoBean() {
        super.url = "views/contas-receber/crc-documento/crcDocumentoList.xhtml";
        super.namedFindAll = "findAllCrcDocumento";
        super.type = CrcDocumento.class;
        super.urlForm = "crcDocumentoForm.xhtml";
        super.subtitle = "Financeiro | Contas a Receber | CRC - Documento";
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

            clienteBean.doBeanList();
            tipoReceitaBean.doBeanList();
            centroCustoBean.doBeanList();
            tipoDocumentoBean.doBeanList();

            crcItemDocumento = new CrcItemDocumento();
        } catch (Throwable ex) {
            Logger.getLogger(CrcDocumentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        crcItemDocumento = new CrcItemDocumento();
        super.doBeanList();
    }

    @Override
    public void doBeanForm() {
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addItem() {
        String strMsg = "";
        if (crcItemDocumento.getValor() == null) {
            strMsg = "Valor obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (crcItemDocumento.getCentroCusto() == null) {
            strMsg = "Centro de Custo obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (crcItemDocumento.getCrcTipoReceita() == null) {
            strMsg = "Tipo de Receita obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (strMsg.isEmpty()) {
            if (crcItemDocumento.getItemDocumentoId() == null) {
                Long seqnum = 1L;
                if (!bean.getCrcItemDocumentoList().isEmpty()) {
                    seqnum = (Long) bean.getCrcItemDocumentoList().get(bean.getCrcItemDocumentoList().size() - 1).getSeqnum() + 1L;
                }
                crcItemDocumento.setSeqnum(seqnum);
                crcItemDocumento.setInstantiatesSite(bean.getInstantiatesSite());
                bean.getCrcItemDocumentoList().add(crcItemDocumento);
            }
            calcularTotalItem();
            GrtProjeto projeto = GrtProjetoDao.findByCentroCusto(crcItemDocumento.getCentroCusto().getCentroCustoId());
            GerCliente clie = bean.getCliente();
            crcItemDocumento.getCronograma().setInstantiatesSite(bean.getInstantiatesSite());
            crcItemDocumento.getCronograma().setProjeto(projeto);
            crcItemDocumento.getCronograma().setDataHoraInicioPrevisto(bean.getDataVencimento());
            crcItemDocumento.getCronograma().setDataHoraTerminoPrevisto(bean.getDataVencimento());
            crcItemDocumento.getCronograma().setTipoEvento("Contas a Receber");
            crcItemDocumento.getCronograma().setDescricao("Receber Cliente:" + clie.getNome() + "/N. Docto:" + bean.getNumeroDocto() + "/ R$ " + new DecimalFormat("#.00").format(crcItemDocumento.getValor()));
            crcItemDocumento = new CrcItemDocumento();
        }
    }

    public void removeItem() {
        bean.getCrcItemDocumentoList().remove(crcItemDocumento);
        calcularTotalItem();
        crcItemDocumento = new CrcItemDocumento();
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelCrc");
    }

    public void cancelEditItem() {
        crcItemDocumento = new CrcItemDocumento();
    }

    public CrcItemDocumento getCrcItemDocumento() {
        return crcItemDocumento;
    }

    public void setCrcItemDocumento(CrcItemDocumento crcItemDocumento) {
        this.crcItemDocumento = crcItemDocumento;
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    private void calcularTotalItem() {
        totalItem = new BigDecimal(0);
        for (CrcItemDocumento item : bean.getCrcItemDocumentoList()) {
            if (item.getValor() != null) {
                totalItem = totalItem.add(new BigDecimal(item.getValor()));
            }
        }
    }
}
