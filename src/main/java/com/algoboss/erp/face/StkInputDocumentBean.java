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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.dao.PurOrderDao;
import com.algoboss.erp.entity.GerFornecedor;
import com.algoboss.erp.entity.GerTipoDocumento;
import com.algoboss.erp.entity.PurOrder;
import com.algoboss.erp.entity.PurOrderItem;
import com.algoboss.erp.entity.StkInputDocument;
import com.algoboss.erp.entity.StkInputDocumentItem;
import com.algoboss.erp.entity.StkMovementItem;
import com.algoboss.erp.entity.StkSupplyItem;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkInputDocumentBean extends GenericBean<StkInputDocument> {

    private StkInputDocumentItem inputDocumentItem;
    private BigDecimal totalItem;
    private Long supplyItemId;
    @Inject
    private BaseDao baseDao;
    @Inject
    private StkBrandBean brandBean;
    @Inject
    private StkSupplyItemBean supplyItemBean;
    @Inject
    private StkMovementBean movementBean;
    @Inject
    private PurParameterBean parameterBean;
    @Inject
    private GerFornecedorBean fornecedorBean;
    @Inject
    private GerTipoDocumentoBean tipoDocumentoBean;
    private List<GerFornecedor> fornecedorList;
    private List<GerTipoDocumento> gerTipoDocumentoList;
    private List<PurOrder> orderList;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkInputDocumentBean() {
        super.url = "views/stock/input-document/inputDocumentList.xhtml";
        super.namedFindAll = "findAllStkInputDocument";
        super.type = StkInputDocument.class;
        super.urlForm = "inputDocumentForm.xhtml";
        super.subtitle = "Suprimentos | Estoque | Entrada";
    }

    public List<GerFornecedor> getFornecedorList() {
        return fornecedorList;
    }

    public List<GerTipoDocumento> getGerTipoDocumentoList() {
        return gerTipoDocumentoList;
    }

    public List<PurOrder> getOrderList() {
        return orderList;
    }

    public void updateInputItem() {
        bean.getInputDocumentItemList().clear();
        for (int i = 0; i < bean.getOrderList().size(); i++) {
            PurOrder order = bean.getOrderList().get(i);
            order.setStatus("closed");
            List<PurOrderItem> orderItemList = order.getOrderItemList();
            for (int j = 0; j < orderItemList.size(); j++) {
                PurOrderItem purOrderItem = orderItemList.get(j);
                StkInputDocumentItem inputDocumentItemAux = new StkInputDocumentItem();
                inputDocumentItemAux.setAmount(purOrderItem.getAmount());
                inputDocumentItemAux.setBrand(purOrderItem.getBrand());
                inputDocumentItemAux.setInstantiatesSite(purOrderItem.getInstantiatesSite());
                inputDocumentItemAux.setSupplyItem(purOrderItem.getSupplyItem());
                inputDocumentItemAux.setUnitPrice(purOrderItem.getPrice());
                addMovement(inputDocumentItemAux);
                bean.getInputDocumentItemList().add(inputDocumentItemAux);
            }

        }
    }

    public void updateOrderList() {
        if (bean.getFornecedor() != null && bean.getFornecedor().getFornecedorId() != null) {
            orderList = PurOrderDao.findApprovedByFornecedorId(bean.getFornecedor().getFornecedorId());
            bean.setOrderList(orderList);
            updateInputItem();

        }
    }

    @Override
    public void indexBean(Long inro) {
        try {
            super.indexBean(inro);
            supplyItemBean.doBeanList();
            movementBean.doBeanList();
            parameterBean.doBeanList();
            brandBean.doBeanList();
            fornecedorBean.doBeanList();
            tipoDocumentoBean.doBeanList();

            orderList = new ArrayList();
            updateOrderList();
            //fornecedorList = (List<GerFornecedor>) (Object) baseDao.findAll("findAllGerFornecedor");
            //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");
            //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
            //tipoReceitaList = (List<CrcTipoReceita>) (Object) baseDao.findAll("findAllCrcTipoReceita");
            //centroCustoList = (List<CentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
            //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");                       

            inputDocumentItem = new StkInputDocumentItem();
            supplyItemId = 0l;
        } catch (Throwable ex) {
            Logger.getLogger(StkInputDocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        inputDocumentItem = new StkInputDocumentItem();
        supplyItemId = 0l;
        orderList = new ArrayList();
        super.doBeanList();
    }

    @Override
    public void doBeanForm() {
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addItem() {
        String strMsg = "";
        if (inputDocumentItem.getMovementItem().getAmount() == null) {
            strMsg = "Quantidade obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (inputDocumentItem.getMovementItem().getMovement() == null) {
            strMsg = "Item de suprimento obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //inputDocumentItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(inputDocumentItem.getSupplyItem())));
        }
        if (strMsg.isEmpty()) {
            if (inputDocumentItem.getInputDocumentItemId() == null) {
                Long seqnum = 1L;
                if (!bean.getInputDocumentItemList().isEmpty()) {
                    seqnum = (Long) bean.getInputDocumentItemList().get(bean.getInputDocumentItemList().size() - 1).getSeqnum() + 1L;
                }
                inputDocumentItem.setSeqnum(seqnum);
                if (!bean.getOrderList().isEmpty()) {
                    inputDocumentItem.getMovementItem().setMovementHistory(StkMovementItem.MovementHistory.INPUT_ORDER);
                } else {
                    inputDocumentItem.getMovementItem().setMovementHistory(StkMovementItem.MovementHistory.INPUT_SEPARATE);
                }
                //addMovement(inputDocumentItem);
                bean.getInputDocumentItemList().add(inputDocumentItem);
            }
            calcularTotalItem();
            inputDocumentItem = new StkInputDocumentItem();
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

    private void addMovement(StkInputDocumentItem input) {
        //input.getSupplyItem().getMovement().setCurrentBalance(input.getSupplyItem().getMovement().getCurrentBalance().add(input.getAmount()));
        StkMovementItem movementItem = new StkMovementItem();
        movementItem.setAmount(input.getAmount());
        movementItem.setUnitPrice(input.getUnitPrice());
        movementItem.setMovement(input.getSupplyItem().getMovement());
        if (!bean.getOrderList().isEmpty()) {
            movementItem.setMovementHistory(StkMovementItem.MovementHistory.INPUT_ORDER);
        }
        input.setMovementItem(movementItem);
    }

    public void removeItem() {
        bean.getInputDocumentItemList().remove(inputDocumentItem);
        calcularTotalItem();
        inputDocumentItem = new StkInputDocumentItem();
        supplyItemId = 0l;
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelstkInputDocumentBean");
    }

    public void cancelEditItem(String container) {
        inputDocumentItem = new StkInputDocumentItem();
        //bean.
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelstkInputDocumentBean");
    }

    public void updateSupplyItem() {
        if (supplyItemId != null && supplyItemId > 0) {
            StkSupplyItem supplyItemSelected = new StkSupplyItem();
            supplyItemSelected.setSupplyItemId(supplyItemId);
            inputDocumentItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(supplyItemSelected)));
        }
    }

    public StkInputDocumentItem getInputDocumentItem() {
        return inputDocumentItem;
    }

    public void setInputDocumentItem(StkInputDocumentItem inputDocumentItem) {
        this.inputDocumentItem = inputDocumentItem;

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
        //super.doBeanSave();
        List<Object> bList = new ArrayList();
        bList.add(bean);
        bList.addAll(bean.getOrderList());
        super.doBeanSave(bList.toArray());
    }
}
