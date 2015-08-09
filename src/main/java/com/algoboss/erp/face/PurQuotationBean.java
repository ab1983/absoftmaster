/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.GerFornecedor;
import com.algoboss.erp.entity.PurOrder;
import com.algoboss.erp.entity.PurOrderItem;
import com.algoboss.erp.entity.PurQuotation;
import com.algoboss.erp.entity.PurQuotationBySupplier;
import com.algoboss.erp.entity.PurQuotationBySupplierItem;
import com.algoboss.erp.entity.PurSolicitation;
import com.algoboss.erp.entity.PurSolicitationItem;
import com.algoboss.erp.entity.StkSupplyItem;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class PurQuotationBean extends GenericBean<PurQuotation> {
    @Inject
    private BaseDao baseDao;
    private PurQuotationBySupplier quotationBySupplier;
    private PurQuotationBySupplierItem quotationBySupplierItem;
    private List<PurQuotationBySupplierItem> quotationBySupplierItemList = new ArrayList();
    private BigDecimal totalItem;
    private Long supplyItemId;
    @Inject
    private StkSupplyItemBean supplyItemBean;
    @Inject
    private PurParameterBean parameterBean;
    @Inject
    private GerFornecedorBean fornecedorBean;
    @Inject
    private PurOrderBean orderBean;
    private List<PurSolicitation> solicitationList = new ArrayList();
    private List<PurSolicitationItem> solicitationItemList = new ArrayList();
    private PurSolicitation[] solicitationFilterArray;
    private PurSolicitationItem[] solicitationItemFilterArray;
    private DualListModel<GerFornecedor> fornecedorFilter;
    private List<PurSolicitationItem> solicitationItemGroupList = new ArrayList();
    private PurSolicitationItem solicitationItemSelected;
    private List<PurQuotationBySupplier> quotationBySupplierList;
    private Converter fornecedorConverter = null;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public PurQuotationBean() {
        super.url = "views/purchase/quotation/quotationList.xhtml";
        super.namedFindAll = "findAllPurQuotation";
        super.type = PurQuotation.class;
        super.urlForm = "quotationForm.xhtml";
        super.subtitle = "Suprimentos | Compra | Cotação";
        fornecedorConverter = new Converter() {

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                //throw new UnsupportedOperationException("Not supported yet.");
                GerFornecedor forn = new GerFornecedor();
                forn.setFornecedorId(Long.valueOf(value));
                forn = fornecedorBean.getBeanList().get(fornecedorBean.getBeanList().indexOf(forn));
                return forn;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                //throw new UnsupportedOperationException("Not supported yet.");
                GerFornecedor forn = (GerFornecedor) value;
                return forn.getFornecedorId().toString();
            }
        };

    }

    @Override
    public void initBean() {
        try {
            super.initBean();
            supplyItemBean.doBeanList();
            parameterBean.doBeanList();
            fornecedorBean.doBeanList();
            solicitationList = (List<PurSolicitation>) (Object) baseDao.findAll("findPurSolicitationApproved");
            solicitationFilterArray = solicitationList.toArray(new PurSolicitation[solicitationList.size()]);
            quotationBySupplier = new PurQuotationBySupplier();
            quotationBySupplierItem = new PurQuotationBySupplierItem();
            supplyItemId = 0l;
            quotationBySupplierList = bean.getQuotationBySupplierList();
            solicitationItemSelected = new PurSolicitationItem();
            fornecedorConverter = new Converter() {

                @Override
                public Object getAsObject(FacesContext context, UIComponent component, String value) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    GerFornecedor forn = new GerFornecedor();
                    forn.setFornecedorId(Long.valueOf(value));
                    forn = fornecedorBean.getBeanList().get(fornecedorBean.getBeanList().indexOf(forn));
                    return forn;
                }

                @Override
                public String getAsString(FacesContext context, UIComponent component, Object value) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    GerFornecedor forn = (GerFornecedor) value;
                    return forn.getFornecedorId().toString();
                }
            };

        } catch (Throwable ex) {
            Logger.getLogger(PurQuotationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);

            //solicitationList = (List<PurSolicitation>) (Object) baseDao.findAll("findPurSolicitationApproved");
            //solicitationFilterArray = solicitationList.toArray(new PurSolicitation[solicitationList.size()]);
            //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
            //tipoReceitaList = (List<CrcTipoReceita>) (Object) baseDao.findAll("findAllCrcTipoReceita");
            //centroCustoList = (List<CentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
            //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");
        } catch (Throwable ex) {
            Logger.getLogger(PurQuotationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        try {
            super.doBeanList();
            solicitationList = (List<PurSolicitation>) (Object) baseDao.findAll("findPurSolicitationApproved");
            solicitationFilterArray = solicitationList.toArray(new PurSolicitation[solicitationList.size()]);
            quotationBySupplier = new PurQuotationBySupplier();
            quotationBySupplierItem = new PurQuotationBySupplierItem();
            supplyItemId = 0l;
            quotationBySupplierList = bean.getQuotationBySupplierList();
            solicitationItemSelected = new PurSolicitationItem();
        } catch (Throwable ex) {
            Logger.getLogger(PurQuotationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanForm() {
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addQuotationBySupplier() {
        String strMsg = "";
        /*
         * if (solicitationItem.getAmount() == null) { strMsg = "Quantidade
         * obrigatório;\n"; FacesMessage msg = new
         * FacesMessage(FacesMessage.SEVERITY_ERROR, strMsg, "");
         * FacesContext.getCurrentInstance().addMessage(null, msg); } if
         * (solicitationItem.getSupplyItem().getSupplyItemId() == null) { strMsg
         * = "Item de suprimento obrigatório;\n"; FacesMessage msg = new
         * FacesMessage(FacesMessage.SEVERITY_ERROR, strMsg, "");
         * FacesContext.getCurrentInstance().addMessage(null, msg); } else {
         * solicitationItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(solicitationItem.getSupplyItem())));
         * } if (strMsg.isEmpty()) { if
         * (solicitationItem.getSolicitationItemId() == null) {
         * bean.getSolicitationItemList().add(solicitationItem); }
         * calcularTotalItem(); solicitationItem = new PurSolicitationItem();
         *
         * }
         */
    }

    public void addQuotationBySupplierItem() {
    }

    public void removeQuotationBySupplier() {
        bean.getQuotationBySupplierList().remove(quotationBySupplier);
        calcularTotalItem();
        quotationBySupplier = new PurQuotationBySupplier();
        quotationBySupplierItem = new PurQuotationBySupplierItem();
        supplyItemId = 0l;
    }

    public void removeQuotationBySupplierItem() {
        quotationBySupplier.getQuotationBySupplierItemList().remove(quotationBySupplierItem);
        calcularTotalItem();
        quotationBySupplierItem = new PurQuotationBySupplierItem();
        supplyItemId = 0l;
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelCrc");
    }

    public void cancelEditQuotationBySupplier(String container) {
        quotationBySupplier = new PurQuotationBySupplier();
        quotationBySupplierItem = new PurQuotationBySupplierItem();
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelCrc");
    }

    public void cancelEditQuotationBySupplierItem(String container) {
        quotationBySupplierItem = new PurQuotationBySupplierItem();
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelCrc");
    }

    public void updateSupplyItem() {
        if (supplyItemId != null && supplyItemId > 0) {
            StkSupplyItem supplyItemSelected = new StkSupplyItem();
            supplyItemSelected.setSupplyItemId(supplyItemId);
            //solicitationItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(supplyItemSelected)));
        }
    }

    public PurQuotationBySupplier getQuotationBySupplier() {
        return quotationBySupplier;
    }

    public void setQuotationBySupplier(PurQuotationBySupplier quotationBySupplier) {
        this.quotationBySupplier = quotationBySupplier;
    }

    public PurQuotationBySupplierItem getQuotationBySupplierItem() {
        return quotationBySupplierItem;
    }

    public void setQuotationBySupplierItem(PurQuotationBySupplierItem quotationBySupplierItem) {
        this.quotationBySupplierItem = quotationBySupplierItem;
    }

    public List<PurSolicitation> getSolicitationList() {
        return solicitationList;
    }

    public PurSolicitation[] getSolicitationFilterArray() {
        return solicitationFilterArray;
    }

    public void setSolicitationFilterArray(PurSolicitation[] solicitationFilterArray) {
        this.solicitationFilterArray = solicitationFilterArray;
    }

    public List<PurSolicitationItem> getSolicitationItemList() {
        return solicitationItemList;
    }

    public PurSolicitationItem[] getSolicitationItemFilterArray() {
        return solicitationItemFilterArray;
    }

    public void setSolicitationItemFilterArray(PurSolicitationItem[] solicitationItemFilterArray) {
        this.solicitationItemFilterArray = solicitationItemFilterArray;
    }

    public List<PurQuotationBySupplierItem> getQuotationBySupplierItemList() {
        return quotationBySupplierItemList;
    }

    public List<PurQuotationBySupplier> getQuotationBySupplierList() {
        return quotationBySupplierList;
    }

    public List<PurSolicitationItem> getSolicitationItemGroupList() {
        return solicitationItemGroupList;
    }

    public PurSolicitationItem getSolicitationItemSelected() {
        return solicitationItemSelected;
    }

    public void setSolicitationItemSelected(PurSolicitationItem solicitationItemSelected) {
        this.solicitationItemSelected = solicitationItemSelected;
    }

    public Converter getFornecedorConverter() {
        return fornecedorConverter;
    }

    public void setFornecedorConverter(Converter fornecedorConverter) {
        this.fornecedorConverter = fornecedorConverter;
    }

    public DualListModel<GerFornecedor> getFornecedorFilter() {
        return fornecedorFilter;
    }

    public void setFornecedorFilter(DualListModel<GerFornecedor> fornecedorFilter) {
        this.fornecedorFilter = fornecedorFilter;
    }

    public void setQuotationBySupplierList(List<PurQuotationBySupplier> quotationBySupplierList) {
        this.quotationBySupplierList = quotationBySupplierList;
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
            orderBean.doBeanList();
            Long orderNumber = orderBean.getBean().getNumber();
            for (int i = 0; i < bean.getQuotationBySupplierList().size(); i++) {
                PurQuotationBySupplier quotation = bean.getQuotationBySupplierList().get(i);
                PurOrder order = new PurOrder();
                order.setFornecedor(quotation.getFornecedor());
                order.setNumber(orderNumber);
                orderNumber++;
                for (int j = 0; j < quotation.getQuotationBySupplierItemList().size(); j++) {
                    PurQuotationBySupplierItem item = bean.getQuotationBySupplierList().get(i).getQuotationBySupplierItemList().get(j);
                    if (item.getStatus().equals("VENCEDOR")) {
                        PurOrderItem orderItem = new PurOrderItem();
                        orderItem.setAmount(item.getAmount());
                        orderItem.setBrand(item.getBrand());
                        orderItem.setPrice(item.getPrice());
                        orderItem.setSupplyItem(item.getSupplyItem());
                        Long seqnum = 1L;
                        if (!order.getOrderItemList().isEmpty()) {
                            seqnum = (Long) order.getOrderItemList().get(order.getOrderItemList().size() - 1).getSeqnum() + 1L;
                        }
                        orderItem.setSeqnum(seqnum);
                        order.getOrderItemList().add(orderItem);
                    }
                }
                if (!order.getOrderItemList().isEmpty()) {
                    if (!parameterBean.getBean().isOrderApproveRequired()) {
                        order.setStatus("approved");
                    }
                    order.setSeqnum(1L);
                    quotation.setOrder(order);
                    quotation.setSeqnum(1L);
                }
            }
            bean.setStatus("closed");
            //bean.setQuotationBySupplierList(quotationBySupplierList);
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(PurQuotationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void solicitationItemListUpdate() {
        solicitationItemList = new ArrayList();
        for (int i = 0; i < solicitationFilterArray.length; i++) {
            PurSolicitation purSolicitation = solicitationFilterArray[i];
            List<PurSolicitationItem> item = purSolicitation.getSolicitationItemList();
            for (int j = 0; j < item.size(); j++) {
                PurSolicitationItem purSolicitationItem = item.get(j);
                solicitationItemList.add(purSolicitationItem);
            }
        }
        solicitationItemFilterArray = solicitationItemList.toArray(new PurSolicitationItem[solicitationItemList.size()]);
    }

    public void solicitationItemGroupListUpdate() {
        solicitationItemGroupList = new ArrayList();
        bean.getSolicitationItemList().clear();
        for (int i = 0; i < solicitationItemFilterArray.length; i++) {
            PurSolicitationItem purSolicitationItem = solicitationItemFilterArray[i];
            PurSolicitationItem purSolicitationItemGroup = new PurSolicitationItem();
            purSolicitationItem.setStatus("closed");
            bean.getSolicitationItemList().add(purSolicitationItem);
            for (int j = 0; j < solicitationItemGroupList.size(); j++) {
                if (solicitationItemGroupList.get(j).getSupplyItem().getSupplyItemId().equals(purSolicitationItem.getSupplyItem().getSupplyItemId())) {
                    purSolicitationItemGroup = solicitationItemGroupList.get(j);
                    break;
                }
            }
            if (purSolicitationItemGroup.getAmount() == null) {
                purSolicitationItemGroup.setSupplyItem(purSolicitationItem.getSupplyItem());
                purSolicitationItemGroup.setAmount(purSolicitationItem.getAmount());
                solicitationItemGroupList.add(purSolicitationItemGroup);
            } else {
                purSolicitationItemGroup.setAmount(purSolicitationItemGroup.getAmount().add(purSolicitationItem.getAmount()));
            }

        }
    }

    public void updateFornecedorList() {
        //solicitationItemSelected
        //quotationBySupplierList
        List<GerFornecedor> source = new ArrayList<GerFornecedor>();
        List<GerFornecedor> target = new ArrayList<GerFornecedor>();
        for (int i = 0; i < bean.getQuotationBySupplierList().size(); i++) {
            for (int j = 0; j < bean.getQuotationBySupplierList().get(i).getQuotationBySupplierItemList().size(); j++) {
                StkSupplyItem item = bean.getQuotationBySupplierList().get(i).getQuotationBySupplierItemList().get(j).getSupplyItem();
                if (item.getSupplyItemId().equals(solicitationItemSelected.getSupplyItem().getSupplyItemId())) {
                    target.add(bean.getQuotationBySupplierList().get(i).getFornecedor());
                    break;
                }
            }
        }
        source = new ArrayList(fornecedorBean.getBeanList());
        source.removeAll(target);
        fornecedorFilter = new DualListModel<GerFornecedor>(source, target);
    }

    public void atualizar() {
        for (int i = 0; i < solicitationList.size(); i++) {
            PurSolicitation solicitation = solicitationList.get(i);
            /*
             * if(solicitationIdFilterList.contains(solicitation.getSolicitationId())){
             * for (int j = 0; j <
             * solicitation.getSolicitationItemList().size(); j++) {
             * PurSolicitationItem solicitationItem =
             * solicitation.getSolicitationItemList().get(j);
             * quotationBySupplierItem.setSupplyItem(solicitationItem.getSupplyItem());
             * quotationBySupplierItem.setAmount(solicitationItem.getAmount());
             * quotationBySupplierItemList.add(quotationBySupplierItem); } }
             */

        }
    }

    public void rankingQuotationBySupplier() {
        for (int i = 0; i < bean.getQuotationBySupplierList().size(); i++) {
            for (int j = 0; j < bean.getQuotationBySupplierList().get(i).getQuotationBySupplierItemList().size(); j++) {
                PurQuotationBySupplierItem item = bean.getQuotationBySupplierList().get(i).getQuotationBySupplierItemList().get(j);
                if (item.getPrice() != null) {
                    item.setStatus("VENCEDOR");
                    for (int l = 0; l < bean.getQuotationBySupplierList().size(); l++) {
                        for (int m = 0; m < bean.getQuotationBySupplierList().get(l).getQuotationBySupplierItemList().size(); m++) {
                            PurQuotationBySupplierItem item2 = bean.getQuotationBySupplierList().get(l).getQuotationBySupplierItemList().get(m);
                            if (item.getSupplyItem().getSupplyItemId().equals(item2.getSupplyItem().getSupplyItemId())) {
                                if (item2.getPrice() != null && item.getPrice().floatValue() > item2.getPrice().floatValue()) {
                                    item.setStatus("PERDEDOR");
                                }
                            }
                        }
                    }
                } else {
                    item.setStatus("INDEFINIDO");
                }
            }

        }
    }

    public String onFlowProcess(FlowEvent event) {
        //logger.info("Current wizard step:" + event.getOldStep());
        //logger.info("Next step:" + event.getNewStep());
        //skip = (bean.getStatusAquisicao() == null || bean.getStatusAquisicao().getStatusAquisicaoImovelId() == null || bean.getStatusAquisicao().getStatusAquisicaoImovelId() == 1);
        String next = event.getNewStep();
        String old = event.getOldStep();
        if (old.equals("solicitationWiz")
                && next.equals("solicitationItemWiz")) {
            solicitationItemListUpdate();
        }
        if (old.equals("solicitationItemWiz")
                && next.equals("solicitacaoItemGroupWiz")) {
            solicitationItemGroupListUpdate();
            List<GerFornecedor> source = new ArrayList<GerFornecedor>();
            List<GerFornecedor> target = new ArrayList<GerFornecedor>();
            fornecedorFilter = new DualListModel<GerFornecedor>(source, target);
            //updateFornecedorList();
        }
        if (old.equals("valoresCotacaoWiz")
                && next.equals("winnersWiz")) {
            solicitationItemGroupListUpdate();
            rankingQuotationBySupplier();
        }
        /*
         *
         * if (skip && event.getOldStep().equals("checklistWiz") &&
         * event.getNewStep().equals("complementoWiz")) { next = "statusWiz"; }
         * if (skip && event.getOldStep().equals("statusWiz") &&
         * event.getNewStep().equals("complementoWiz")) { next = "checklistWiz";
         * }
         */
        return next;
    }

    public void onTransfer(TransferEvent event) {
        //StringBuilder builder = new StringBuilder();  
        //for (Object item : event.getItems()) {
        //PurQuotationBySupplier purQuotationBySupplier = new PurQuotationBySupplier();
        //purQuotationBySupplier
        //}
        //fornecedorFilter.getTarget()
        if (solicitationItemSelected != null && solicitationItemSelected.getAmount() != null) {
            for (int i = 0; i < event.getItems().size(); i++) {
                GerFornecedor forn = (GerFornecedor) event.getItems().get(i);
                PurQuotationBySupplier purQuotationBySupplier = new PurQuotationBySupplier();
                Integer purQuotationBySupplierIdx = null;
                for (int j = 0; j < bean.getQuotationBySupplierList().size(); j++) {
                    if (bean.getQuotationBySupplierList().get(j).getFornecedor().getFornecedorId().equals(forn.getFornecedorId())) {
                        purQuotationBySupplier = bean.getQuotationBySupplierList().get(j);
                        purQuotationBySupplierIdx = j;
                        break;
                    }
                }

                if (event.isAdd() && (purQuotationBySupplier.getFornecedor() == null || purQuotationBySupplier.getFornecedor().getFornecedorId() == null)) {
                    purQuotationBySupplier.setFornecedor(forn);
                    bean.getQuotationBySupplierList().add(purQuotationBySupplier);
                }
                PurQuotationBySupplierItem item = new PurQuotationBySupplierItem();
                item.setSupplyItem(solicitationItemSelected.getSupplyItem());
                item.setAmount(solicitationItemSelected.getAmount());
                item.setAmountAvailable(solicitationItemSelected.getAmount());
                item.setDeliveryTimeInDays(1L);
                boolean existe = false;
                Integer itemIdx = null;
                for (int j = 0; j < purQuotationBySupplier.getQuotationBySupplierItemList().size(); j++) {
                    PurQuotationBySupplierItem purQuotationBySupplierItem = purQuotationBySupplier.getQuotationBySupplierItemList().get(j);
                    if (purQuotationBySupplierItem.getSupplyItem().getSupplyItemId().equals(item.getSupplyItem().getSupplyItemId())) {
                        existe = true;
                        itemIdx = j;
                        break;
                    }
                }
                if (event.isAdd() && !existe) {
                    purQuotationBySupplier.getQuotationBySupplierItemList().add(item);
                }

                if (event.isRemove()) {
                    if (existe) {
                        purQuotationBySupplier.getQuotationBySupplierItemList().remove(itemIdx.intValue());
                    }
                    if (purQuotationBySupplierIdx != null && purQuotationBySupplier.getQuotationBySupplierItemList().isEmpty()) {
                        bean.getQuotationBySupplierList().remove(purQuotationBySupplierIdx.intValue());
                    }
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Selecione primeiro o item antes de selecionar o fornecedor.", "");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((PurQuotationBySupplierItem) event.getObject()).getSupplyItem().getDescription());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Cancelled", ((PurQuotationBySupplierItem) event.getObject()).getSupplyItem().getDescription());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
