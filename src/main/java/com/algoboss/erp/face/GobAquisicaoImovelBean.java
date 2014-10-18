/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.dao.GobComplementacaoAquisicaoClientePfDao;
import com.algoboss.erp.entity.GerCliente;
import com.algoboss.erp.entity.GobAquisicaoImovel;
import com.algoboss.erp.entity.GobChecklistDocumentacaoAquisicao;
import com.algoboss.erp.entity.GobComplementacaoAquisicaoClientePf;
import com.algoboss.erp.entity.GobItemPadraoChecklistDocumentacaoAquisicao;
import com.algoboss.erp.entity.GobObra;
import com.algoboss.erp.entity.GobStatusAquisicao;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Agnaldo
 */
@ManagedBean
@SessionScoped
public class GobAquisicaoImovelBean extends GenericBean<GobAquisicaoImovel> {

    @Inject
    private BaseDao baseDao;
    private GobObra obra;
    private long obraId;
    private long clienteId;
    private long statusAquisicaoImovelId;
    private List<Long> checklistIdList = new ArrayList();
    private List<GobStatusAquisicao> statusAquisicaoList = new ArrayList();
    private boolean skip;
    private static Logger logger = Logger.getLogger(GobAquisicaoImovelBean.class.getName());
    private GobComplementacaoAquisicaoClientePf complementacaoAquisicaoClientePf = new GobComplementacaoAquisicaoClientePf();
    @Inject
    private GobObraBean obraBean;
    @Inject
    private GerClienteBean clienteBean;

    /**
     * Creates a new instance of ClienteBean
     */
    public GobAquisicaoImovelBean() {
        super.url = "views/gerenciador-obras/aquisicaoImovelList.xhtml";
        super.urlForm = "aquisicaoImovelForm.xhtml";
        super.namedFindAll = "findAllGobAquisicaoImovel";
        super.type = GobAquisicaoImovel.class;
        super.subtitle = "Operacional | Aquisição Imóvel";
        obra = new GobObra();
        complementacaoAquisicaoClientePf = new GobComplementacaoAquisicaoClientePf();
    }

    @Override
    public void indexBean(Long nro) {
        try {
            //clienteList = (List<GerCliente>) (Object) baseDao.findAll("findAllGerCliente");
            clienteBean.doBeanList();
            obraBean.doBeanList();
            //obraList = (List<GobObra>) (Object) baseDao.findAll("findAllGobObra");
            statusAquisicaoList = (List<GobStatusAquisicao>) (Object) baseDao.findAll("findAllGobStatusAquisicao");
            obra = new GobObra();
            super.indexBean(nro);
            popularCheckList();
        } catch (Throwable ex) {
            Logger.getLogger(GobAquisicaoImovelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanSave() {
        try {
            GobStatusAquisicao status = new GobStatusAquisicao();
            status.setStatusAquisicaoImovelId(statusAquisicaoImovelId);
            bean.setStatusAquisicao(statusAquisicaoList.get(statusAquisicaoList.indexOf(status)));
            if (bean.getCliente().getTipoPessoa().equalsIgnoreCase("FISICA")) {
                try {
                    complementacaoAquisicaoClientePf.setCliente(bean.getCliente());
                    complementacaoAquisicaoClientePf = (GobComplementacaoAquisicaoClientePf) baseDao.save(complementacaoAquisicaoClientePf);
                    complementacaoAquisicaoClientePf = new GobComplementacaoAquisicaoClientePf();
                } catch (Throwable ex) {
                    Logger.getLogger(GobAquisicaoImovelBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(GobAquisicaoImovelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        complementacaoAquisicaoClientePf = new GobComplementacaoAquisicaoClientePf();
        popularCheckList();
    }

    @Override
    public void doBeanForm() {
        super.doBeanForm();
        obraId = 0;
        clienteId = 0;
        try {
            if (bean.getUnidadeObra() != null) {
                obra = bean.getUnidadeObra().getObra();
            }
            //obraId = bean.getUnidadeObra().getObra().getObraId();
        } catch (Exception e) {
        }
        try {
            clienteId = bean.getCliente().getClienteId();
            if (bean.getCliente().getTipoPessoa().equalsIgnoreCase("FISICA")) {
                complementacaoAquisicaoClientePf = GobComplementacaoAquisicaoClientePfDao.findByClienteId(clienteId);
            }
        } catch (Exception e) {
        }
    }

    private void popularCheckList() {
        if (bean.getChecklistDocumentacaoAquisicaoList().isEmpty()) {
            try {
                List<GobItemPadraoChecklistDocumentacaoAquisicao> itemPadraoChecklistDocumentacaoAquisicaoList = (List<GobItemPadraoChecklistDocumentacaoAquisicao>) (Object) baseDao.findAll("findAllGobItemPadraoChecklistDocumentacaoAquisicao");
                for (GobItemPadraoChecklistDocumentacaoAquisicao gobItemPadraoChecklistDocumentacaoAquisicao : itemPadraoChecklistDocumentacaoAquisicaoList) {
                    GobChecklistDocumentacaoAquisicao obj = new GobChecklistDocumentacaoAquisicao();
                    obj.setItemPadraoChecklistDocumentacaoAquisicao(gobItemPadraoChecklistDocumentacaoAquisicao);
                    bean.getChecklistDocumentacaoAquisicaoList().add(obj);
                }
            } catch (Throwable ex) {
                Logger.getLogger(GobAquisicaoImovelBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public GobObra getObra() {
        return obra;
    }

    public void setObra(GobObra obra) {
        this.obra = obra;
    }

    public long getObraId() {
        return obraId;
    }

    public void setObraId(long obraId) {
        this.obraId = obraId;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public long getStatusAquisicaoImovelId() {
        return statusAquisicaoImovelId;
    }

    public void setStatusAquisicaoImovelId(long statusAquisicaoImovelId) {
        this.statusAquisicaoImovelId = statusAquisicaoImovelId;
    }

    public List<Long> getChecklistIdList() {
        return checklistIdList;
    }

    public void setChecklistIdList(List<Long> checklistIdList) {
        this.checklistIdList = checklistIdList;
    }

    public GobComplementacaoAquisicaoClientePf getComplementacaoAquisicaoClientePf() {
        return complementacaoAquisicaoClientePf;
    }

    public void setComplementacaoAquisicaoClientePf(GobComplementacaoAquisicaoClientePf complementacaoAquisicaoClientePf) {
        this.complementacaoAquisicaoClientePf = complementacaoAquisicaoClientePf;
    }

    public List<GobStatusAquisicao> getStatusAquisicaoList() {
        return statusAquisicaoList;
    }

    public void setStatusAquisicaoList(List<GobStatusAquisicao> statusAquisicaoList) {
        this.statusAquisicaoList = statusAquisicaoList;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public GerClienteBean getClienteBean() {
        return clienteBean;
    }

    public void setClienteBean(GerClienteBean clienteBean) {
        this.clienteBean = clienteBean;
    }

    public void obraUpdate() {
        if (obraId > 0) {
            GobObra obraSelected = new GobObra();
            obraSelected.setObraId(obraId);
            //bean.getUnidadeObra().setObra(obraList.get(obraList.indexOf(obraSelected)));
        }
    }

    public void clienteUpdate() {
        if (clienteId > 0) {
            GerCliente clienteSelected = new GerCliente();
            clienteSelected.setClienteId(clienteId);
            bean.setCliente(clienteBean.getBeanList().get(clienteBean.getBeanList().indexOf(clienteSelected)));
            if (bean.getCliente().getTipoPessoa().equalsIgnoreCase("FISICA")) {
                complementacaoAquisicaoClientePf = GobComplementacaoAquisicaoClientePfDao.findByClienteId(clienteId);
            }
            if (complementacaoAquisicaoClientePf == null) {
                complementacaoAquisicaoClientePf = new GobComplementacaoAquisicaoClientePf();
            }
        }
    }

    public void checklistUpdate() {
        for (GobChecklistDocumentacaoAquisicao checklistDocumentacaoAquisicao : bean.getChecklistDocumentacaoAquisicaoList()) {
            checklistDocumentacaoAquisicao.setEntregue(checklistIdList.contains(checklistDocumentacaoAquisicao.getChecklistDocumentacaoAquisicaoId()));
        }
    }

    public String onFlowProcess(FlowEvent event) {
        logger.info("Current wizard step:" + event.getOldStep());
        logger.info("Next step:" + event.getNewStep());
        skip = (bean.getStatusAquisicao() == null || bean.getStatusAquisicao().getStatusAquisicaoImovelId() == null || bean.getStatusAquisicao().getStatusAquisicaoImovelId() == 1);
        String next = event.getNewStep();
        if (skip && event.getOldStep().equals("checklistWiz")
                && event.getNewStep().equals("complementoWiz")) {
            next = "statusWiz";
        }
        if (skip && event.getOldStep().equals("statusWiz")
                && event.getNewStep().equals("complementoWiz")) {
            next = "checklistWiz";
        }
        return next;
    }
}
