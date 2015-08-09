/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.algoboss.app.util.ComponentFactory;
import com.algoboss.core.dao.AdmContractDao;
import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.dao.UsuarioDao;
import com.algoboss.core.entity.AdmCompany;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmInstantiatesSite;
import com.algoboss.core.entity.AdmRepresentative;
import com.algoboss.core.entity.AdmService;
import com.algoboss.core.entity.AdmServiceContract;
import com.algoboss.core.entity.AdmServiceModuleContract;
import com.algoboss.core.entity.SecUser;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class AdmContractBean extends GenericBean<AdmContract> {

    @Inject
    private BaseDao baseDao;
    @Inject
    private AdmRepresentativeBean representativeBean;
    @Inject
    private ThemeSwitcherBean themeSwitcherBean;
    @Inject
    private GerLoginBean loginBeany;
    @Inject
    private SecUserBean userBean;
    private SecUser user;
    private List<SecUser> userList;
    private BigDecimal totalItem;
    private AdmServiceModuleContract serviceModuleContract;
    private List<AdmService> serviceList;
    private Converter serviceConverter = null;
    private DualListModel<AdmService> serviceFilter;
    private List<AdmCompany> companyList;
    private AdmCompany company;
    private int contractTabIndex;

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public AdmContractBean() {
        super.url = "views/core/administration/contract/contractList.xhtml";
        super.namedFindAll = "findAllAdmContract";
        super.type = AdmContract.class;
        super.urlForm = "contractForm.xhtml";
        super.subtitle = "Administração | Contrato";
    }

    public int getContractTabIndex() {
        return contractTabIndex;
    }

    public void setContractTabIndex(int contractTabIndex) {
        if (this.contractTabIndex == 0 && contractTabIndex == 1) {
            if (false && !user.getEmail().equals(user.getEmailAgain())) {
                contractTabIndex = this.contractTabIndex;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail não confere! E-mail 1 deve ser igual ao E-mail 2.", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                try {
                    Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    String keywordParam = String.valueOf(map.get("keywordParam"));
                    List<String[]> destin = new ArrayList<String[]>();
                    destin.add(new String[]{"agnaldo_luiz@msn.com", "Agnaldo luiz."});
                    String msg = "Foi cadastrado um novo usuário:";
                    msg += "\n Nome: " + user.getName();
                    msg += "\n E-mail: " + user.getEmail();
                    msg += "\n Senha: " + user.getPassword();
                    msg += "\n Keyword: " + keywordParam;
                    //super.doBeanSaveAndList(false, false, user);
                    sendEmail(destin, "webmaster@algoboss.com", "AlgoBoss", "AlgoBoss - Novo Usuário", msg, null);
                } catch (Exception ex) {
                    Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.contractTabIndex = contractTabIndex;
    }

    public void indexHomeBean() {
        try {
            contractTabIndex = 0;
            loginBeany.setUser(new SecUser());
            generateRepresentativeSystem();
            serviceList = (List<AdmService>) (Object) baseDao.findAll("findAllAdmServiceApproved");
            generateIndex();
            company = new AdmCompany();
            user = new SecUser();
            bean = new AdmContract();
            //doBeanList();
            prepareModuleList();
            notReadOnly = true;
        } catch (Throwable ex) {
            Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateRepresentativeSystem() {
        AdmRepresentative representative = new AdmRepresentative();
        representative.setRepresentativeId(146l);
        representative = (AdmRepresentative) doBeanRefresh(representative);
        loginBeany.getUser().setRepresentative(representative);
    }

    private void generateIndex() {
        try {
            representativeBean.doBeanList();            
            serviceConverter = new Converter() {
                @Override
                public Object getAsObject(FacesContext context, UIComponent component, String value) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    AdmService item = new AdmService();
                    item.setServiceId(Long.valueOf(value));
                    item = serviceList.get(serviceList.indexOf(item));
                    return item;
                }

                @Override
                public String getAsString(FacesContext context, UIComponent component, Object value) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    AdmService item = (AdmService) value;
                    return item.getServiceId().toString();
                }
            };
        } catch (Throwable ex) {
            Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SecUser getUser() {
        return user;
    }

    public void setUser(SecUser user) {
        this.user = user;
    }

    public AdmServiceModuleContract getServiceModuleContract() {
        return serviceModuleContract;
    }

    public void setServiceModuleContract(AdmServiceModuleContract serviceModuleContract) {
        this.serviceModuleContract = serviceModuleContract;
    }

    public Converter getServiceConverter() {
        return serviceConverter;
    }

    public void setServiceConverter(Converter serviceConverter) {
        this.serviceConverter = serviceConverter;
    }

    public DualListModel<AdmService> getServiceFilter() {
        return serviceFilter;
    }

    public void setServiceFilter(DualListModel<AdmService> serviceFilter) {
        this.serviceFilter = serviceFilter;
    }

    public AdmCompany getCompany() {
        return company;
    }

    public void setCompany(AdmCompany company) {
        this.company = company;
    }

    public AdmInstantiatesSite getInstatiatesSite() {
        AdmInstantiatesSite siteContract = null;
        if (loginBeany != null) {
            siteContract = loginBeany.getInstantiatesSiteContract();
        }
        return siteContract;
    }

    public void onTransfer(TransferEvent event) {
        for (int i = 0; i < event.getItems().size(); i++) {
            AdmService item = (AdmService) event.getItems().get(i);
            Integer idx = null;
            for (int j = 0; j < serviceModuleContract.getServiceContractList().size(); j++) {
                AdmServiceContract serviceContract = serviceModuleContract.getServiceContractList().get(j);
                if (serviceContract.getService().getServiceId().equals(item.getServiceId())) {
                    idx = j;
                    break;
                }
            }
            if (event.isAdd() && idx == null) {
                AdmServiceContract serviceContract = new AdmServiceContract();
                serviceContract.setService(item);
                serviceModuleContract.getServiceContractList().add(serviceContract);
            } else if (event.isAdd() && idx != null) {
                serviceModuleContract.getServiceContractList().get(idx.intValue()).setInactive(false);
            }
            if (event.isRemove() && idx != null) {
                serviceModuleContract.getServiceContractList().get(idx.intValue()).setInactive(true);
            }
        }
    }

    public void uptadeServiceList() {
        List<AdmService> source = new ArrayList<AdmService>();
        List<AdmService> target = new ArrayList<AdmService>();
        for (int i = 0; i < serviceList.size(); i++) {
            AdmService item = (AdmService) serviceList.get(i);
            for (int j = 0; j < serviceModuleContract.getServiceContractList().size(); j++) {
                AdmServiceContract serviceContract = serviceModuleContract.getServiceContractList().get(j);
                if (serviceContract.getService().getServiceId().equals(item.getServiceId()) && !serviceContract.isInactive()) {
                    target.add(item);
                    break;
                }
            }
        }
        source = new ArrayList(serviceList);
        source.removeAll(target);
        serviceFilter = new DualListModel<AdmService>(source, target);
    }

    @Override
    public void indexBean(Long nro) {
        try{ 
            serviceList = (List<AdmService>) (Object) baseDao.findAll("findAllAdmService");
            generateIndex();
            super.indexBean(nro);
        } catch (Throwable ex) {
            Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String doBeanSaveHome() {
        loginBeany.getUser().setEmail(user.getEmail());
        loginBeany.getUser().setPassword(user.getPassword());
        loginBeany.setNewUser(true);
        bean.setRepresentative(loginBeany.getUser().getRepresentative());
        bean.getCompanyList().add(company);
        user.setContract(bean);
        user.setAdministrator(true);
        bean.getUserList().add(user);
        doBeanSaveAndList(true, false, false, bean);
        //bean = (AdmContract)doBeanRefresh(bean);
        beanList = new ArrayList<AdmContract>();
        beanList.add(bean);
        return loginBeany.doLogin();
    }

    @Override
    public void doBeanSave() {
        try {
        	if(bean.getRepresentative()==null){
        		bean.setRepresentative(loginBeany.getUser().getRepresentative());        		
        	}
            bean.getCompanyList().add(company);
            user.setContract(bean);
            user.setAdministrator(true);
            bean.getUserList().add(user);
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void prepareModuleList() {
        if (loginBeany.getBaseBean() != null) {
            baseBean = loginBeany.getBaseBean();
        } else {
            baseBean = new BaseBean();
        }
        bean.setSystemName(baseBean.getTitle());
        bean.setSystemTheme(themeSwitcherBean.getTheme());
        for (int i = 0; i < serviceList.size(); i++) {
            AdmService admService = serviceList.get(i);
            AdmServiceModuleContract module = null;
            for (int j = 0; j < bean.getServiceModuleContractList().size(); j++) {
                if (bean.getServiceModuleContractList().get(j).getName().equals(admService.getModule())) {
                    module = bean.getServiceModuleContractList().get(j);
                    break;
                }
            }
            if (module == null) {
                module = new AdmServiceModuleContract();
                module.setName(admService.getModule());
                module.setExpectedAmount(1);
                module.setCurrentAmount(0);
                bean.getServiceModuleContractList().add(module);
            }
            AdmServiceContract serviceContract = new AdmServiceContract();
            serviceContract.setService(admService);
            module.getServiceContractList().add(serviceContract);
        }
    }

    @Override
    public void doBeanList() {
        contractTabIndex = 0;
        serviceModuleContract = new AdmServiceModuleContract();
        company = new AdmCompany();
        user = new SecUser();
        List<AdmService> source = new ArrayList<AdmService>();
        List<AdmService> target = new ArrayList<AdmService>();
        serviceFilter = new DualListModel<AdmService>(source, target);
        if (!loginBeany.getUser().isBoss()) {
            String msgStr = "";
            Long siteId = null;
            try {
                if (loginBeany.getUser().getRepresentative() != null) {
                    beanList = new AdmContractDao(baseDao).findByRepresentativeId(loginBeany.getUser().getRepresentative().getRepresentativeId());
                }
                bean = new AdmContract();
                formRendered = false;
                //return "/f/cadastro/usuario/usuarioList.xhtml";
            } catch (Throwable ex) {
                msgStr = "Falha no processamento. Motivo: " + ex.getClass();
                ex.printStackTrace();
            } finally {
                if (!msgStr.isEmpty()) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            generateRepresentativeSystem();
            super.doBeanList();
        }
        prepareModuleList();
    }

    @Override
    public void doBeanForm() {
        if (!bean.getCompanyList().isEmpty()) {
            company = bean.getCompanyList().get(0);
        }
        userList = UsuarioDao.findAdministratorByContractId(bean.getContractId());
        if (userList != null && !userList.isEmpty()) {
            user = userList.get(0);
        }
        calcularTotalItem();
        super.doBeanForm();
    }

    public void addItem() {
        String strMsg = "";
        if (serviceModuleContract.getName() == null) {
            strMsg = "Nome obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (strMsg.isEmpty()) {
            if (serviceModuleContract.getServiceModuleContractId() == null) {
                bean.getServiceModuleContractList().add(serviceModuleContract);
            }
            calcularTotalItem();
            serviceModuleContract = new AdmServiceModuleContract();
        }
    }

    public void removeItem() {
        bean.getServiceModuleContractList().remove(serviceModuleContract);
        calcularTotalItem();
        serviceModuleContract = new AdmServiceModuleContract();
    }

    public void startEditItem(UIComponent container) {
        //RequestContext context = RequestContext.getCurrentInstance();//FacesContext.getCurrentInstance().getViewRoot().findComponent("tabView:"+container + ":itemPaneladmContractBeanserviceModuleContract")getPartialViewContext().getRenderIds()
        //context.reset(container.getClientId());
    	ComponentFactory.resetComponent(container);
    }

    public void cancelEditItem() {
        serviceModuleContract = new AdmServiceModuleContract();
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    private void calcularTotalItem() {
        totalItem = new BigDecimal(0);
        //for (CpgItemDocumento item : bean.getCpgItemDocumentoList()) {
        //  totalItem = totalItem.add(new BigDecimal(item.getValor()));
        //}
    }
}
