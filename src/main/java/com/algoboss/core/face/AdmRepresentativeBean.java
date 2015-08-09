/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algoboss.core.dao.RepresentativeDao;
import com.algoboss.core.entity.AdmRepresentative;
import com.algoboss.core.entity.GerBankAccount.AccountType;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class AdmRepresentativeBean extends GenericBean<AdmRepresentative> {

    @Inject
    private GerLoginBean loginBean;
    @Inject
    private RepresentativeDao representativeDao;
    @Inject
    private GerBankBean bankBean;
    private List<AdmRepresentative> supervisorList = new ArrayList();
    private List<AccountType> accountTypeList = new ArrayList();

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public AdmRepresentativeBean() {
        super.url = "views/core/administration/representative/representativeList.xhtml";
        super.namedFindAll = "findAllAdmRepresentative";
        super.type = AdmRepresentative.class;
        super.urlForm = "representativeForm.xhtml";
        super.subtitle = "Administração | Representante";
    }

    public List<AdmRepresentative> getSupervisorList() {
        return supervisorList;
    }

    public List<AccountType> getAccountTypeList() {
        return accountTypeList;
    }

    @Override
    public void indexBean(Long nro) {
        bankBean.doBeanList();
        accountTypeList.add(AccountType.CURRENT_ACCOUNT);
        accountTypeList.add(AccountType.SAVINGS_ACCOUNT);
        try {
            super.indexBean(nro);

        } catch (Throwable ex) {
            Logger.getLogger(AdmRepresentativeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initBean() {
        bean.setSupervisor(loginBean.getUser().getRepresentative());
        super.initBean();
    }

    @Override
    public void doBeanList() {
        supervisorList.clear();
        if (loginBean.getUser().getRepresentative() != null) {
            beanList = representativeDao.findBySupervisorId(loginBean.getUser().getRepresentative().getRepresentativeId());
            supervisorList.add(loginBean.getUser().getRepresentative());
            bean = new AdmRepresentative();
            formRendered = false;
        } else if (loginBean.getUser().isBoss()) {
            super.doBeanList();
            supervisorList.addAll(beanList);
        }
    }

    @Override
    public void doBeanSave() {
        try {
            bean.getUser().setAdministrator(true);
            super.doBeanSave();
        } catch (Throwable ex) {
            Logger.getLogger(AdmRepresentativeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
