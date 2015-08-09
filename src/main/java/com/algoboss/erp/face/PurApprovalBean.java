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
import com.algoboss.erp.entity.PurOrder;
import com.algoboss.erp.entity.PurOrderItem;
import com.algoboss.erp.entity.PurSolicitation;
import com.algoboss.erp.entity.PurSolicitationItem;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class PurApprovalBean extends GenericBean<Object> {

    @Inject
    private PurParameterBean parameterBean;
    @Inject
    private BaseDao baseDao;
    private List<PurSolicitation> solicitationList;
    private List<PurSolicitation> solicitationSelectedList;
    private List<PurOrder> orderList;
    private List<PurOrder> orderSelectedList;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public PurApprovalBean() {
        super.url = "views/purchase/approval/approvalList.xhtml";
        super.namedFindAll = "findAllPurSolicitation";
        super.type = Object.class;
        super.urlForm = "solicitationForm.xhtml";
        super.subtitle = "Suprimentos | Compra | Aprovação";
        super.formRendered = false;
    }

    public List<PurOrder> getOrderList() {
        return orderList;
    }

    public List<PurSolicitation> getSolicitationList() {
        return solicitationList;
    }

    public List<PurOrder> getOrderSelectedList() {
        return orderSelectedList;
    }

    public void setOrderSelectedList(List<PurOrder> orderSelectedList) {
        this.orderSelectedList = orderSelectedList;
    }

    public List<PurSolicitation> getSolicitationSelectedList() {
        return solicitationSelectedList;
    }

    public void setSolicitationSelectedList(List<PurSolicitation> solicitationSelectedList) {
        this.solicitationSelectedList = solicitationSelectedList;
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
            parameterBean.doBeanList();
            solicitationList = (List<PurSolicitation>) (Object) baseDao.findAll("findPurSolicitationOpen");
            orderList = (List<PurOrder>) (Object) baseDao.findAll("findAllPurOrderOpen");
            updateSelection();

        } catch (Throwable ex) {
            Logger.getLogger(PurApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSelection() {
        solicitationSelectedList = new ArrayList();
        for (int i = 0; i < solicitationList.size(); i++) {
            PurSolicitation solicitation = solicitationList.get(i);
            if (solicitation.getStatus().equals("approved")) {
                solicitationSelectedList.add(solicitation);
            }
        }
        orderSelectedList = new ArrayList();
        for (int i = 0; i < orderList.size(); i++) {
            PurOrder order = orderList.get(i);
            if (order.getStatus().equals("approved")) {
                orderSelectedList.add(order);
            }
        }
    }

    public void doApproval() {

        for (int i = 0; i < solicitationList.size(); i++) {
            try {
                PurSolicitation solicitation = solicitationList.get(i);
                if (solicitationSelectedList.contains(solicitation)) {
                    solicitation.setStatus("approved");
                    for (int j = 0; j < solicitation.getSolicitationItemList().size(); j++) {
                        PurSolicitationItem object = solicitation.getSolicitationItemList().get(j);
                        object.setStatus("approved");
                    }
                } else {
                    solicitation.setStatus("opened");
                    for (int j = 0; j < solicitation.getSolicitationItemList().size(); j++) {
                        PurSolicitationItem object = solicitation.getSolicitationItemList().get(j);
                        object.setStatus("opened");
                    }                    
                }
                baseDao.save(solicitation);
            } catch (Throwable ex) {
                Logger.getLogger(PurApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < orderList.size(); i++) {
            try {
                PurOrder order = orderList.get(i);
                if (orderSelectedList.contains(order)) {
                    order.setStatus("approved");
                    for (int j = 0; j < order.getOrderItemList().size(); j++) {
                        PurOrderItem object = order.getOrderItemList().get(j);
                        object.setStatus("approved");
                    }                    
                } else {
                    order.setStatus("opened");
                    for (int j = 0; j < order.getOrderItemList().size(); j++) {
                        PurOrderItem object = order.getOrderItemList().get(j);
                        object.setStatus("opened");
                    }                    
                }
                baseDao.save(order);
            } catch (Throwable ex) {
                Logger.getLogger(PurApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void doBeanSave() {
        try {
            /*
             * String title = (bean.getSolicitationId() != null ? "Alterado" :
             * "Criado")+" Solicitação de Compra: " + bean.getNumber(); String
             * msg = title; msg += "\nData: " + new
             * SimpleDateFormat("dd/MM/yyyy").format(bean.getDateRegistration());
             * msg += "\nObservação: " + bean.getObservation(); msg +=
             * "\nQuantidade de Itens: " +
             * bean.getSolicitationItemList().size(); msg += "\nDetalhes
             * Item/Quantidade: "; for (int i = 0; i <
             * bean.getSolicitationItemList().size(); i++) { PurSolicitationItem
             * item = bean.getSolicitationItemList().get(i); msg += "\n" + (i +
             * 1) + ") " + item.getSupplyItem().getDescription() + "/" +
             * item.getAmount(); } super.doBeanSave(); if
             * (parameterBean.getBean().getEmailForSolicitation() != null) {
             * List<String[]> destinatariosAdmin = new ArrayList<String[]>();
             * destinatariosAdmin.add(new
             * String[]{parameterBean.getBean().getEmailForSolicitation(),
             * "Agnaldo luiz"}); //if (!erroEnvio.equals("")) {
             * GenericBean.sendEmail(destinatariosAdmin, "agnaldo_luiz@msn.com",
             * "Agnaldo Luíz", title, msg, null); } // }
             *
             */
        } catch (Exception ex) {
            Logger.getLogger(PurApprovalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
