/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.entity.SalOrder;
import com.algoboss.erp.entity.SalOrderItem;
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
public class SalOrderBean extends GenericBean<SalOrder> {
    @Inject
    private BaseDao baseDao;
    private SalOrderItem orderItem;
    private BigDecimal totalItem;
    private Long supplyItemId;
    @Inject
    private StkSupplyItemBean supplyItemBean;
    @Inject
    private PurParameterBean parameterBean;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public SalOrderBean() {
        super.url = "views/sales/order/orderList.xhtml";
        super.namedFindAll = "findAllSalOrder";
        super.type = SalOrder.class;
        super.urlForm = "orderForm.xhtml";
        super.subtitle = "Vendas | Pedido";
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
        } catch (Throwable ex) {
            Logger.getLogger(SalOrderBean.class.getName()).log(Level.SEVERE, null, ex);
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
        orderItem = new SalOrderItem();
        supplyItemId = 0l;

    }

    @Override
    public void doBeanList() {
        orderItem = new SalOrderItem();
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
        if (orderItem.getAmount() == null) {
            strMsg = "Quantidade obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (orderItem.getSupplyItem().getSupplyItemId() == null) {
            strMsg = "Item de suprimento obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            orderItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(orderItem.getSupplyItem())));
        }
        if (strMsg.isEmpty()) {
            if (orderItem.getOrderItemId() == null) {
                Long seqnum = 1L;
                if (!bean.getOrderItemList().isEmpty()) {
                    seqnum = (Long) bean.getOrderItemList().get(bean.getOrderItemList().size() - 1).getSeqnum() + 1L;
                }
                orderItem.setSeqnum(seqnum);
                bean.getOrderItemList().add(orderItem);
            }
            calcularTotalItem();
            orderItem = new SalOrderItem();
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
        bean.getOrderItemList().remove(orderItem);
        calcularTotalItem();
        orderItem = new SalOrderItem();
        supplyItemId = 0l;
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelpurOrderBean");
    }

    public void cancelEditItem(String container) {
        orderItem = new SalOrderItem();
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelpurOrderBean");
    }

    public void updateSupplyItem() {
        if (supplyItemId != null && supplyItemId > 0) {
            StkSupplyItem supplyItemSelected = new StkSupplyItem();
            supplyItemSelected.setSupplyItemId(supplyItemId);
            orderItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(supplyItemSelected)));
        }
    }

    public SalOrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(SalOrderItem orderItem) {
        this.orderItem = orderItem;
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
            String title = (bean.getOrderId() != null ? "Alterado" : "Criado") + " Pedido de Compra: " + bean.getNumber();
            String msg = title;
            msg += "\nData: " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDateRegistration());
            msg += "\nObservação: " + bean.getObservation();
            msg += "\nQuantidade de Itens: " + bean.getOrderItemList().size();
            msg += "\nDetalhes Item/Quantidade: ";
            for (int i = 0; i < bean.getOrderItemList().size(); i++) {
                SalOrderItem item = bean.getOrderItemList().get(i);
                msg += "\n" + (i + 1) + ") " + item.getSupplyItem().getDescription() + "/" + item.getAmount();
            }
            super.doBeanSave();
            if (parameterBean.getBean().getEmailForPurchaseSolicitation() != null) {
                List<String[]> destinatariosAdmin = new ArrayList<String[]>();
                destinatariosAdmin.add(new String[]{parameterBean.getBean().getEmailForPurchaseSolicitation(), "Compras"});
                //if (!erroEnvio.equals("")) {
                GenericBean.sendEmail(destinatariosAdmin, "atendimento@algoboss.com", baseBean.getTitle(), title, msg, null);
            }
            // }
        } catch (Throwable ex) {
            Logger.getLogger(SalOrderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
