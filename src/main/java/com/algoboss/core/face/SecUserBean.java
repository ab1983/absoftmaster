/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algoboss.core.dao.UsuarioDao;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmServiceContract;
import com.algoboss.core.entity.AdmServiceModuleContract;
import com.algoboss.core.entity.SecAuthorization;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.entity.SecUserAuthorization;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class SecUserBean extends GenericBean<SecUser> {

    @Inject
    private GerLoginBean loginBeanx;

    /**
     * Creates a new instance of SecUserBean
     */
    public SecUserBean() {
        super.url = "views/core/cadastro/sec/user/userList.xhtml";
        super.namedFindAll = "findAllSecUser";
        super.type = SecUser.class;
        super.subtitle = "Cadastro | Usuário";
    }

    @Override
    public void doBeanList() {
        String msgStr = "";
        try {
            bean = new SecUser();
            AdmContract contract = loginBeanx.getInstantiatesSiteContract().getContract();
            if (contract != null && contract.getContractId() != null) {
                bean.setContract(contract);
                beanList = UsuarioDao.findByContractId(contract.getContractId());
            } else if (loginBeanx.getUser().isBoss()) {
                beanList = UsuarioDao.findAll();
            }

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

        //super.doBeanList();
    }

    public String doBeanSaveNewUser() {
        try {
            notReadOnly = true;
            loginBeanx.getUser().setEmail(bean.getEmail());
            loginBeanx.getUser().setPassword(bean.getPassword());
            loginBeanx.setNewUser(true);
            AdmContract contract = loginBeanx.getUser().getContract();
            bean.setContract(loginBeanx.getUser().getContract());
            if (contract.getServiceHomePage() != null) {
                List<SecUserAuthorization> userAuthorizationList = bean.getUserAuthorizationList();
                List<AdmServiceModuleContract> moduleList = bean.getContract().getServiceModuleContractList();
                for (AdmServiceModuleContract admServiceModuleContract : moduleList) {
                    List<AdmServiceContract> serviceContractList = admServiceModuleContract.getServiceContractList();
                    for (AdmServiceContract admServiceContract : serviceContractList) {
                        if (contract.getServiceHomePage().equals(admServiceContract.getService())) {
                            SecUserAuthorization userAuthorization = new SecUserAuthorization();
                            SecAuthorization authorization = new SecAuthorization();
                            authorization.setAllowed(true);
                            //userAuthorization.setUserAuthorizationId(Double.valueOf(Math.random() * 1000 * Math.random() * 1000).longValue());
                            userAuthorization.setAuthorization(authorization);
                            userAuthorization.setServiceContract(admServiceContract);
                            admServiceContract.setServiceModuleContract(admServiceModuleContract);
                            userAuthorizationList.add(userAuthorization);
                            break;
                        }
                    }
                    if (userAuthorizationList != null && !userAuthorizationList.isEmpty()) {
                        break;
                    }
                }
            }

            doBeanSaveAndList(true, false, false, bean);
            //bean = (AdmContract)doBeanRefresh(bean);
            beanList = new ArrayList<SecUser>();
            beanList.add(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginBeanx.doLogin();
    }
}
