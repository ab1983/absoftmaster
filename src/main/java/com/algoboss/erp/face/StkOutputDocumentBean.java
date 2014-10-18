/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.entity.GerFornecedor;
import com.algoboss.erp.entity.GerTipoDocumento;
import com.algoboss.erp.entity.PurSolicitation;
import com.algoboss.erp.entity.StkMovementItem;
import com.algoboss.erp.entity.StkOutputDocument;
import com.algoboss.erp.entity.StkOutputDocumentItem;
import com.algoboss.erp.entity.StkSupplyItem;
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

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class StkOutputDocumentBean extends GenericBean<StkOutputDocument> {

    private StkOutputDocumentItem outputDocumentItem;
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
    private List<GerFornecedor> fornecedorList;
    private List<GerTipoDocumento> gerTipoDocumentoList; 
    private List<PurSolicitation> solicitationList;   

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public StkOutputDocumentBean() {
        super.url = "views/stock/output-document/outputDocumentList.xhtml";
        super.namedFindAll = "findAllStkOutputDocument";
        super.type = StkOutputDocument.class;
        super.urlForm = "outputDocumentForm.xhtml";
        super.subtitle = "Suprimentos | Estoque | Saída";
    }

    public List<GerFornecedor> getFornecedorList() {
        return fornecedorList;
    }

    public List<GerTipoDocumento> getGerTipoDocumentoList() {
        return gerTipoDocumentoList;
    }

    public List<PurSolicitation> getSolicitationList() {
        return solicitationList;
    }
    public void updateSolicitationList(){
        //if(bean.getFornecedor()!=null && bean.getFornecedor().getFornecedorId()!=null){
         //   solicitationList = PurSolicitationDao.findApprovedByFornecedorId(bean.getFornecedor().getFornecedorId());                    
        //}
    }
    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
            supplyItemBean.doBeanList();
            movementBean.doBeanList();            
            parameterBean.doBeanList();
            brandBean.doBeanList();
            solicitationList = new ArrayList();
            updateSolicitationList();
            fornecedorList = (List<GerFornecedor>) (Object) baseDao.findAll("findAllGerFornecedor");
            gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");            
            //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
            //tipoReceitaList = (List<CrcTipoReceita>) (Object) baseDao.findAll("findAllCrcTipoReceita");
            //centroCustoList = (List<CentroCusto>) (Object) baseDao.findAll("findAllCentroCusto");
            //gerTipoDocumentoList = (List<GerTipoDocumento>) (Object) baseDao.findAll("findAllGerTipoDocumento");                       
            
            outputDocumentItem = new StkOutputDocumentItem();
            supplyItemId = 0l;
        } catch (Throwable ex) {
            Logger.getLogger(StkOutputDocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        outputDocumentItem = new StkOutputDocumentItem();
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
        if (outputDocumentItem.getMovementItem().getAmount() == null) {
            strMsg = "Quantidade obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (outputDocumentItem.getMovementItem().getMovement()==null) {
            strMsg = "Item de suprimento obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            //outputDocumentItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(outputDocumentItem.getSupplyItem())));
        }
        if (strMsg.isEmpty()) {
            if (outputDocumentItem.getOutputDocumentItemId() == null) {
                Long seqnum = 1L;
                if(!bean.getOutputDocumentItemList().isEmpty()){
                    seqnum = (Long)bean.getOutputDocumentItemList().get(bean.getOutputDocumentItemList().size() - 1).getSeqnum() + 1L;
                }
                outputDocumentItem.getMovementItem().setMovementHistory(StkMovementItem.MovementHistory.OUTPUT_SOLICITATION); 
                outputDocumentItem.setSeqnum(seqnum);      
                //addMovement(outputDocumentItem);
                bean.getOutputDocumentItemList().add(outputDocumentItem);
            }
            calcularTotalItem();
            outputDocumentItem = new StkOutputDocumentItem();
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
    private void addMovement(StkOutputDocumentItem output) {
        //output.getSupplyItem().getMovement().setCurrentBalance(output.getSupplyItem().getMovement().getCurrentBalance().add(output.getAmount()));
        StkMovementItem movementItem = new StkMovementItem();
        movementItem.setAmount(output.getAmount());
        movementItem.setUnitPrice(output.getUnitPrice());
        movementItem.setMovement(output.getSupplyItem().getMovement());
        movementItem.setMovementHistory(StkMovementItem.MovementHistory.OUTPUT_SOLICITATION);        
        output.setMovementItem(movementItem);        
    }
    public void removeItem() {
        bean.getOutputDocumentItemList().remove(outputDocumentItem);
        calcularTotalItem();
        outputDocumentItem = new StkOutputDocumentItem();
        supplyItemId = 0l;
    }

    public void startEditItem(String container) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelstkOutputDocumentBean");
    }

    public void cancelEditItem(String container) {
        outputDocumentItem = new StkOutputDocumentItem();
        supplyItemId = 0l;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":itemPanelstkOutputDocumentBean");
    }

    public void updateSupplyItem() {
        if (supplyItemId != null && supplyItemId > 0) {
            StkSupplyItem supplyItemSelected = new StkSupplyItem();
            supplyItemSelected.setSupplyItemId(supplyItemId);
            outputDocumentItem.setSupplyItem(supplyItemBean.getBeanList().get(supplyItemBean.getBeanList().indexOf(supplyItemSelected)));
        }
    }

    public StkOutputDocumentItem getOutputDocumentItem() {
        return outputDocumentItem;
    }

    public void setOutputDocumentItem(StkOutputDocumentItem outputDocumentItem) {
        this.outputDocumentItem = outputDocumentItem;

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
            try {
                /*
                String title = (bean.getSolicitationId() != null ? "Alterado" : "Criado")+" Pedido de Compra: " + bean.getNumber();
                String msg = title;
                msg += "\nData: " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDateRegistration());
                msg += "\nObservação: " + bean.getObservation();
                msg += "\nQuantidade de Itens: " + bean.getOutputDocumentItemList().size();
                msg += "\nDetalhes Item/Quantidade: ";
                for (int i = 0; i < bean.getOutputDocumentItemList().size(); i++) {
                    StkOutputDocumentItem item = bean.getOutputDocumentItemList().get(i);
                    msg += "\n" + (i + 1) + ") " + item.getSupplyItem().getDescription() + "/" + item.getAmount();
                }
                * 
                */
                super.doBeanSave();
                /*
                if (parameterBean.getBean().getEmailForSolicitation() != null) {
                    List<String[]> destinatariosAdmin = new ArrayList<String[]>();
                    destinatariosAdmin.add(new String[]{parameterBean.getBean().getEmailForSolicitation(), "Agnaldo luiz"});
                    //if (!erroEnvio.equals("")) {
                    GenericBean.sendEmail(destinatariosAdmin, "agnaldo_luiz@msn.com", "Agnaldo Luíz", title, msg, null);
                }*/
                // }
            } catch (Throwable ex) {
                Logger.getLogger(StkOutputDocumentBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(StkOutputDocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
