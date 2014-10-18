/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.AdmContractDao;
import com.algoboss.erp.dao.UsuarioDao;
import com.algoboss.erp.entity.AdmContract;
import com.algoboss.erp.entity.AdmService;
import com.algoboss.erp.entity.AdmServiceContract;
import com.algoboss.erp.entity.AdmServiceModuleContract;
import com.algoboss.erp.entity.SecAuthorizationItem;
import com.algoboss.erp.entity.SecUser;
import com.algoboss.erp.entity.SecUserAuthorization;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class SecUserAuthorizationBean extends GenericBean<SecUser> {

    @Inject
    private SecUserBean userBean;   
    private List<AdmServiceContract> serviceContractList = new ArrayList();
    private List<SecUserAuthorization> userAuthorizationList = new ArrayList();    
    private SecUserAuthorization userAuthorization = new SecUserAuthorization();
    /**
     * Creates a new instance of TipoDespesaBean
     */
    public SecUserAuthorizationBean() {
        super.url = "views/security/user-authorization/userAuthorizationList.xhtml";
        super.namedFindAll = "findAllSecUserAuthorization";
        super.type = SecUser.class;
        super.urlForm = "userAuthorizationForm.xhtml";
        super.subtitle = "Segurança | Autorização do Usuário";
    }

    public List<SecUserAuthorization> getUserAuthorizationList() {
        return userAuthorizationList;
    }

    public void setUserAuthorizationList(List<SecUserAuthorization> userAuthorizationList) {
        this.userAuthorizationList = userAuthorizationList;
    }

    public List<AdmServiceContract> getServiceContractList() {
        return serviceContractList;
    }
    public SecAuthorizationItem idxAuthorizationItem(int idx) {
            SecAuthorizationItem authorizationItem = new SecAuthorizationItem();
        if(userAuthorization.getAuthorization().getAuthorizationItemList().size()<=idx){
            userAuthorization.getAuthorization().getAuthorizationItemList().add(authorizationItem);
        }else{
            authorizationItem = userAuthorization.getAuthorization().getAuthorizationItemList().get(idx);
        }
        return authorizationItem;
    }
    public SecUserAuthorization getUserAuthorization() {
        return userAuthorization;
    }

    public void setUserAuthorization(SecUserAuthorization userAuthorization) {
        this.userAuthorization = userAuthorization;
    }

    @Override
    public void indexBean(Long nro) {
        try {
            List<AdmService> serviceList = new ArrayList();
            //serviceList.add(new AdmService("Usuário", "Cadastro de Usuário", "views/cadastro/sec/user/userList.xhtml", "", "1", new Date()));
            //serviceList.add(new AdmService("Autorização Usuário", "Libera acesso ao usuário", "views/security/user-authorization/userAuthorizationList.xhtml", "", "1", new Date()));
            userBean.doBeanList();
            super.indexBean(nro);
           
        } catch (Throwable ex) {
            Logger.getLogger(SecUserAuthorizationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateAuthorization(){
    }
    public void changeAllAllowed(boolean event){        
        for (SecUserAuthorization userAuth : userAuthorizationList) {
            userAuth.getAuthorization().setAllowed(event);
        }
    }
    public void changeAllReadOnly(boolean event){        
        for (SecUserAuthorization userAuth : userAuthorizationList) {
            userAuth.getAuthorization().setReadOnly(event);
        }
    }    
    public void updateUser(){
    serviceContractList = new ArrayList();
    userAuthorizationList = new ArrayList();    
        //SecUser user = new SecUser();
        //user.setUserId(bean.getUserId());
        if(bean.getUserId()!=null){
            //bean = userBean.getBeanList().get(userBean.getBeanList().indexOf(user));
            bean = UsuarioDao.findById(bean.getUserId());
            
            AdmContract contract = AdmContractDao.findById(bean.getContract().getContractId());
            for (AdmServiceModuleContract module : contract.getServiceModuleContractList()) {
                for (AdmServiceContract serviceContract : module.getServiceContractList()) {
                    serviceContract.setServiceModuleContract(module);
                }
                serviceContractList.addAll(module.getServiceContractList());
            }
            
            for (AdmServiceContract serviceContract : serviceContractList) {
                SecUserAuthorization userAuth = new SecUserAuthorization();
                userAuth.setServiceContract(serviceContract);
                for (SecUserAuthorization userAuthorization : bean.getUserAuthorizationList()) {
                    if(userAuthorization.getServiceContract().getServiceContractId().equals(serviceContract.getServiceContractId())){
                       userAuth =  userAuthorization;
                        break;
                    }
                }
                userAuthorizationList.add(userAuth);
            }
            bean.setUserAuthorizationList(userAuthorizationList);
        }        
    }
    
    @Override
    public void doBeanSave() {
        if(bean.getUserId()!=null){
            try {
                super.doBeanSave();
            } catch (Throwable ex) {
                Logger.getLogger(SecUserAuthorizationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Selecione um usuário!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        }
    }
    
}
