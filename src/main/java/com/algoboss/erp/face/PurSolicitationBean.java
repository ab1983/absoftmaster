/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.dao.StkMovementDao;
import com.algoboss.erp.entity.PurSolicitation;
import com.algoboss.erp.entity.PurSolicitationItem;
import com.algoboss.erp.entity.StkSupplyItem;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class PurSolicitationBean extends GenericBean<PurSolicitation> {
    @Inject
    private BaseDao baseDao;
    private PurSolicitationItem solicitationItem;
    private PurSolicitation solicitationStock;
    private BigDecimal totalItem;
    private Long supplyItemId;
    @Inject
    private StkSupplyItemBean supplyItemBean;
    @Inject
    private PurParameterBean parameterBean;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public PurSolicitationBean() {
        super.url = "views/purchase/solicitation/solicitationList.xhtml";
        super.namedFindAll = "findAllPurSolicitation";
        super.type = PurSolicitation.class;
        super.urlForm = "solicitationForm.xhtml";
        super.subtitle = "Suprimentos | Compra | Solicitacao";
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);

        } catch (Throwable ex) {
            Logger.getLogger(PurSolicitationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initBean() {
        super.initBean();
            supplyItemBean.doBeanList();
            parameterBean.doBeanList();
            //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
            //tipoReceitaList = (List<CrcTipoReceita>) (Object) baseDao.findAll("findAllCrcTipoReceita");
            //centroCustoList = (List<CentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
            //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");
            solicitationItem = new PurSolicitationItem();
            solicitationStock = new PurSolicitation();
            supplyItemId = 0l;        
    }
    
    @Override
    public void doBeanList() {
        solicitationItem = new PurSolicitationItem();
        solicitationStock = new PurSolicitation();
        supplyItemId = 0l;
        super.doBeanList();
    }

    @Override
    public void doBeanForm() {
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addItem() {
        String strMsg = "";
        if (solicitationItem.getAmount() == null) {
            strMsg = "Quantidade obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (solicitationItem.getSupplyItem()==null || solicitationItem.getSupplyItem().getSupplyItemId() == null) {
            strMsg = "Item de suprimento obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (strMsg.isEmpty()) {
            if (solicitationItem.getSolicitationItemId() == null) {
                Long seqnum = 1L;
                if (!bean.getSolicitationItemList().isEmpty()) {
                    seqnum = (Long) bean.getSolicitationItemList().get(bean.getSolicitationItemList().size() - 1).getSeqnum() + 1L;
                }
                solicitationItem.setSeqnum(seqnum);
                StkMovementDao.updateCurrentBalance(solicitationItem.getSupplyItem());
                if(solicitationItem.getSupplyItem().getMovement().getCurrentBalance().floatValue()>solicitationItem.getAmount().floatValue()){
                    if (!parameterBean.getBean().isStockSolicitationApproveRequired()) {
                        solicitationItem.setStatus("approved");
                    }                
                    solicitationStock.getSolicitationItemList().add(solicitationItem);                    
                }else{
                    if (!parameterBean.getBean().isPurchaseSolicitationApproveRequired()) {
                        solicitationItem.setStatus("approved");
                    }
                    bean.getSolicitationItemList().add(solicitationItem);
                }
            }
            calcularTotalItem();
            solicitationItem = new PurSolicitationItem();
            /*
             * GrtProjeto projeto =
             * GrtProjetoDao.findByCentroCusto(crcItemDocumento.getCentroCusto().getCentroCustoId());
             * GerCliente clie =
             * clienteList.get(clienteList.indexOf(bean.getCliente()));
             * crcItemDocumento.getCronograma().setProjeto(projeto);
             * crcItemDocumento.getCronograma().setDataHoraInicioPrevisto(bean.getDataVencimento());
             * crcItemDocumento.getCronograma().setDataHoraTerminoPrevisto(bean.getDataVencimento());
             * crcItemDocumento.getCronograma().setTipoEvento("Contas a
             * Receber"); crcItemDocumento.getCronograma().setDescricao("Receber
             * Cliente:"+clie.getNome()+"/N. Docto:"+bean.getNumeroDocto()+"/ R$
             * "+new DecimalFormat("#.00").format(crcItemDocumento.getValor()));
             * crcItemDocumento = new CrcItemDocumento();
             *
             */
        }
    }

    public void removeItem() {
        bean.getSolicitationItemList().remove(solicitationItem);
        calcularTotalItem();
        solicitationItem = new PurSolicitationItem();
        supplyItemId = 0l;
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":purSolicitationBeanItemPanel");
    }

    public void cancelEditItem(String container) {
        solicitationItem = new PurSolicitationItem();
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":purSolicitationBeanItemPanel");
    }

    public void updateSupplyItem() {
        if (supplyItemId != null && supplyItemId > 0) {
            StkSupplyItem supplyItemSelected = new StkSupplyItem();
            supplyItemSelected.setSupplyItemId(supplyItemId);
            solicitationItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(supplyItemSelected)));
        }
    }

    public PurSolicitationItem getSolicitationItem() {
        return solicitationItem;
    }

    public void setSolicitationItem(PurSolicitationItem solicitationItem) {
        this.solicitationItem = solicitationItem;

    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    private void calcularTotalItem() {
        totalItem = new BigDecimal(0);
        //for (CrcItemDocumento item : bean.getCrcItemDocumentoList()) {
        //  totalItem = totalItem.add(new BigDecimal(item.getValor()));
        //}
    }

    public Long getSupplyItemId() {
        return supplyItemId;
    }

    public void setSupplyItemId(Long supplyItemId) {
        this.supplyItemId = supplyItemId;
    }

    @Override
    public void doBeanSave() {
        try {
            if(!solicitationStock.getSolicitationItemList().isEmpty()){
                if (!parameterBean.getBean().isStockSolicitationApproveRequired()) {
                    solicitationStock.setStatus("approved");
                }       
                baseDao.save(solicitationStock);
                if (parameterBean.getBean().getEmailForStockSolicitation() != null) {
                    emailBuilder(solicitationStock,"stock");
                }
            }
            if(!bean.getSolicitationItemList().isEmpty()){
                if (!parameterBean.getBean().isPurchaseSolicitationApproveRequired()) {
                    bean.setStatus("approved");
                }
                super.doBeanSave();
                if (parameterBean.getBean().getEmailForPurchaseSolicitation() != null) {
                    emailBuilder(bean,"purchase");
                }
                
            }
            // }
        } catch (Throwable ex) {
            Logger.getLogger(PurSolicitationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void emailBuilder(PurSolicitation beanAux,String solicitationType){
            String title = (beanAux.getSolicitationId() != null ? "Alterado" : "Criado") + " "+BaseBean.getBundle(solicitationType+"Solicitation", "msg")+": " + beanAux.getNumber();
            String msg = title;
            msg += "\nData: " + new SimpleDateFormat("dd/MM/yyyy").format(beanAux.getDateRegistration());
            msg += "\nObservação: " + beanAux.getObservation();
            msg += "\nQuantidade de Itens: " + beanAux.getSolicitationItemList().size();
            msg += "\nDetalhes Item/Quantidade: ";
            for (int i = 0; i < beanAux.getSolicitationItemList().size(); i++) {
                PurSolicitationItem item = beanAux.getSolicitationItemList().get(i);
                msg += "\n" + (i + 1) + ") " + item.getSupplyItem().getDescription() + "/" + item.getAmount();
            }
            //super.doBeanSave();
            if (parameterBean.getBean().getEmailForPurchaseSolicitation() != null) {
            try {
                List<String[]> destinatariosAdmin = new ArrayList<String[]>();
                destinatariosAdmin.add(new String[]{parameterBean.getBean().getEmailForPurchaseSolicitation(), BaseBean.getBundle(solicitationType, "msg")});
                //if (!erroEnvio.equals("")) {
                GenericBean.sendEmail(destinatariosAdmin, "atendimento@algoboss.com", baseBean.getTitle(), title, msg, null);
            } catch (Exception ex) {
                Logger.getLogger(PurSolicitationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        
    }
}
