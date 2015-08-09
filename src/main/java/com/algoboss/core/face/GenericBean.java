/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.algoboss.app.entity.DevReportRequirement;
import com.algoboss.app.entity.DevRequirement;
import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmInstantiatesSite;
import com.algoboss.core.entity.AdmService;
import com.algoboss.core.entity.AdmServiceContract;
import com.algoboss.core.entity.AdmServiceModuleContract;
import com.algoboss.core.entity.SecUserAuthorization;
import com.algoboss.core.util.AlgoUtil;

/**
 *
 * @author Agnaldo
 */
public abstract class GenericBean<T> implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;
    protected boolean notReadOnly;
    protected T bean;
    protected List<T> beanList;
    protected String url;
    protected String subtitle;
    protected String urlForm;
    protected String containerId;
    protected boolean formRendered = false;
    protected String namedFindAll;
    protected boolean limitExceeded = false;
    protected AdmInstantiatesSite site;
    protected Class<T> type;
    @Inject
    protected BaseBean baseBean;
    @Inject
    protected BaseDao baseDao;
    @Inject
    protected GerLoginBean loginBean;
    protected SecUserAuthorization userAuth;

    public GenericBean() {
    	bean = newInstance();
    }

    /**
     * Creates a new instance of UsuarioBean
     */
    private T newInstance() {
        T t = null;
        try {
            t = type.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return t;
        }
    }

    public boolean isNotReadOnly() {
        return notReadOnly;
    }

    public void setNotReadOnly(boolean notReadOnly) {
        this.notReadOnly = notReadOnly;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T obj) {
        this.bean = obj;
    }

    public List<T> getBeanList() {

        return beanList;
    }

    public boolean isFormRendered() {
        return formRendered;
    }

    public void setFormRendered(boolean formRendered) {
        this.formRendered = formRendered;
    }    
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getUrlForm() {
        return urlForm;
    }

    public GerLoginBean getLoginBean() {
        return loginBean;
    }
    
    public void setLoginBean(GerLoginBean loginBean) {
        this.loginBean = loginBean;
    }    

    public List<T> findBeanList() {
        if (beanList == null || beanList.isEmpty()) {
            doBeanList();
        }
        return beanList;
    }

    protected Boolean checkContract() {
        Boolean check = null;
        try {
        	if (type.getSuperclass().getDeclaredField("instantiatesSite") != null) {
                AdmInstantiatesSite currentSite = null;
                if (site == null) {
                    site = loginBean.getInstantiatesSiteContract();                    
                } 
                currentSite = site;
                
                if (currentSite != null && currentSite.getInstantiatesSiteId() != null) {
                    check = true;
                } else {
                    check = false;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return check;
    }
    private SecUserAuthorization getUserAuthorization(Long id) {  
    	Map<String,Boolean> param = new HashMap<String,Boolean>();
    	SecUserAuthorization userAuth = getUserAuthorization(id,param);
    	limitExceeded = param.get("limitExceeded");
    	notReadOnly = param.get("notReadOnly");   
        
    	return userAuth;
    }
    private SecUserAuthorization getUserAuthorization(Long id, Map<String,Boolean> param) {
    	boolean limitExceeded = false, notReadOnly = false;
        SecUserAuthorization userAuth = null;
        if (id != null && id > 0) {
        	userAuth = new SecUserAuthorization();
            userAuth.setUserAuthorizationId(id);
            List<SecUserAuthorization> userAuthorizationList = loginBean.getUserAuthorizationList();      
            int useAuthIdx = userAuthorizationList.indexOf(userAuth);
            if(useAuthIdx>-1){
            	userAuth = userAuthorizationList.get(useAuthIdx);            
            }
        }
        if (loginBean.getUser().isBoss() || loginBean.getUser().getRepresentative() != null) {
            notReadOnly = true;
        } else if (userAuth != null && !loginBean.getUserAuthorizationList().isEmpty()) {
            userAuth.setHitCounter(userAuth.getHitCounter() + 1);
            AdmContract contract = loginBean.getUser().getContract();
            AdmServiceContract serviceContract = userAuth.getServiceContract();
            serviceContract.setHitCounter(serviceContract.getHitCounter() + 1);
            AdmService service = serviceContract.getService();
            service.setHitCounter(service.getHitCounter() + 1);
            notReadOnly = !userAuth.getAuthorization().isReadOnly();
            AdmServiceModuleContract module = serviceContract.getServiceModuleContract();
            if (module == null) {
                for (AdmServiceModuleContract moduleAux : contract.getServiceModuleContractList()) {
                    if (moduleAux.getServiceContractList().contains(serviceContract)) {
                        module = moduleAux;
                        break;
                    }
                }
            }
            baseBean.setModule(String.valueOf(module.getServiceModuleContractId()));
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) context.getSession(false);
            List<Long> moduleIdList = loginBean.getModuleIdList();//(List<Long>) session.getAttribute("moduleIdList");// baseBean.getModuleIdList();
            if (module.getServiceModuleContractId() != null && !moduleIdList.contains(module.getServiceModuleContractId())) {
                try {
                    module = (AdmServiceModuleContract) baseDao.findByObj(module);
                    if (module.getCurrentAmount().longValue() >= module.getExpectedAmount().longValue()) {
                        limitExceeded = true;
                    } else {
                        module.setCurrentAmount(module.getCurrentAmount() + 1);
                        baseDao.save(module);
                        moduleIdList.add(module.getServiceModuleContractId());
                    }
                } catch (Throwable ex) {
                    Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        param.put("limitExceeded", limitExceeded);
        param.put("notReadOnly", notReadOnly);
        return userAuth;
    }

    public void initBean() {
        if (bean == null) {
            bean = newInstance();
        }
    }

    private void prepareIndexBean(Long nro, boolean isList, boolean isAdd) throws Throwable {
    	Map<String,Boolean> param = new HashMap<String,Boolean>();
    	SecUserAuthorization userAuth = getUserAuthorization(nro,param);    	
        if (! param.get("limitExceeded")) {
            String subtitleTemp = subtitle;
            if (userAuth != null && userAuth.getServiceContract() != null) {
            	AdmService service = userAuth.getServiceContract().getService();//(AdmService) userAuth.getServiceContract().getService();
                if(service.getRequirement()==null && service.getReportRequirement()==null){
                    service = (AdmService) doBeanRefresh(service);
                }
                if (service != null && service.getRequirement() != null) {
                    DevRequirement requirement = (DevRequirement) doBeanRefresh(service.getRequirement());
                    subtitleTemp = "AlgoApp | " + requirement.getRequirementName();
                    service.setLastAccess(new Date());
                    if(service.getServiceId()!=null){
                        baseDao.save(service);
                    }
                }
                if (service != null && service.getReportRequirement() != null) {
                    DevReportRequirement requirement = (DevReportRequirement) doBeanRefresh(service.getReportRequirement());
                    subtitleTemp = "AlgoRep | " + requirement.getReportRequirementName();
                    service.setLastAccess(new Date());
                    if(service.getServiceId()!=null){
                        baseDao.save(service);
                    }
                }                
            }
            //url = "main.xhtml";
                    
            String[] str = baseBean.contains(subtitleTemp);
            if (str == null) {
                if (!isAdd) {
                    if (baseBean.getUrlList().isEmpty()) {
                        baseBean.getUrlList().add(new String[]{subtitleTemp, url, baseBean.getModule()});
                        baseBean.setContainerIndex(0);
                    } else {
                    	int idx = baseBean.getContainerIndex() > 0 && baseBean.getContainerIndex() < baseBean.getUrlList().size() ? baseBean.getContainerIndex() : 0;
                        baseBean.getUrlList().set(idx, new String[]{subtitleTemp, url});
                        baseBean.setContainerIndex(idx);
                    }
                } else {
                	int idx = baseBean.getUrlList().isEmpty() ? 0 : baseBean.getUrlList().size();
                    baseBean.getUrlList().add(idx,new String[]{subtitleTemp, url});
                    baseBean.setContainerIndex(idx);
                }
            } else {
                baseBean.setContainerIndex(baseBean.getUrlList().indexOf(str));
            }
            if(!subtitleTemp.isEmpty()){
            	subtitle = subtitleTemp;
            }
        	this.limitExceeded = param.get("limitExceeded");
        	this.notReadOnly = param.get("notReadOnly");             
        	this.userAuth = userAuth;
        	this.site = loginBean.getInstantiatesSiteContract();
            if (!type.isAssignableFrom(Object.class) && isList) {
                doBeanList();
            }        	
            baseBean.setActualBean(this);    
            baseBean.setUrl(url);
            baseBean.setSubtitle(subtitle);
            
            initBean();
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Limite de Licenças Excedido!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void indexBean(Long nro) throws Throwable {
        //notReadOnly = !nro;
        //getUserAuthorization(nro);
        prepareIndexBean(nro, true, false);
    }
    public void indexBeanNoList(Long nro) throws Throwable {
        //notReadOnly = !nro;
        //getUserAuthorization(nro);
        prepareIndexBean(nro, false, false);
    }
    public void indexBeanNewWinForm(Long nro) throws Throwable {
        //getUserAuthorization(nro);
        prepareIndexBean(nro, false, true);
        /*
         getUserAuthorization(nro);
         //notReadOnly = !nro;
         //doBeanList();
         baseBean.setUrl(url);
         baseBean.setSubtitle(subtitle);
         String[] stringsRemove = baseBean.contains(subtitle);
         if (stringsRemove == null) {
         baseBean.getUrlList().add(new String[]{baseBean.getSubtitle(), baseBean.getUrl()});
         baseBean.setContainerIndex(baseBean.getUrlList().isEmpty() ? 0 : baseBean.getUrlList().size() - 1);
         }
         initBean();
         * */
    }

    public void indexBeanNewWin(Long nro) throws Throwable {
        //getUserAuthorization(nro);
        //notReadOnly = !nro;
        prepareIndexBean(nro, false, true);
    }

    public void indexBeanNewWin() throws Throwable {
    	throw new UnsupportedOperationException("Method not implement yet.");
        //prepareIndexBean(true, true);
        //getUserAuthorization(nro);
        //notReadOnly = !nro;
        /*
         doBeanList();
         baseBean.setUrl(url);
         baseBean.setSubtitle(subtitle);
         String[] stringsRemove = baseBean.contains(subtitle);
         if (stringsRemove == null) {
         baseBean.getUrlList().add(new String[]{baseBean.getSubtitle(), baseBean.getUrl()});
         baseBean.setContainerIndex(baseBean.getUrlList().isEmpty() ? 0 : baseBean.getUrlList().size() - 1);
         }
         initBean();
         */
    }

    public void doBeanList() {
        doBeanList(true, null, null);
    }

    public List<Long> getSiteIdList() {
        Long siteId = null;
        List<Long> siteIdList = null;
        if (Boolean.valueOf(true).equals(checkContract())) {
            siteIdList = new ArrayList();
            if (site == null) {
                siteId = loginBean.getInstantiatesSiteContract().getInstantiatesSiteId();
            } else {
                siteId = site.getInstantiatesSiteId();
            }
            siteIdList.add(siteId);
        } else if (checkContract() != null) {
            siteIdList = new ArrayList();
            List<AdmInstantiatesSite> instantiatesSiteList = loginBean.getInstantiatesSiteList();
            for (int j = 0; instantiatesSiteList != null && j < instantiatesSiteList.size(); j++) {
                AdmInstantiatesSite admInstantiatesSite = instantiatesSiteList.get(j);
                siteIdList.add(admInstantiatesSite.getInstantiatesSiteId());
            }
        }
        return siteIdList;
    }

    public void doBeanList(boolean newBean, Date startDate, Date endDate) {
        String msgStr = "";

        try {
            beanList = (List<T>) baseDao.findAll(type, getSiteIdList(), startDate, endDate);
            if (newBean) {
                bean = newInstance();
                try {
                    if (bean.getClass().getDeclaredField("number") != null) {
                        Long number = 1L;
                        if (!beanList.isEmpty()) {

                            number = (Long) beanList.get(beanList.size() - 1).getClass().getDeclaredMethod("getNumber").invoke(beanList.get(beanList.size() - 1)) + 1L;
                        }
                        //System.out.println("AQUI!!!!!!!!!!!!!!!!!!"+number);
                        bean.getClass().getDeclaredMethod("setNumber", Long.class).invoke(bean, number);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                try {
                    if (bean.getClass().getSuperclass().getDeclaredField("seqnum") != null) {
                        Long number = 1L;
                        if (!beanList.isEmpty()) {

                            number = (Long) beanList.get(beanList.size() - 1).getClass().getSuperclass().getDeclaredMethod("getSeqnum").invoke(beanList.get(beanList.size() - 1)) + 1L;
                        }
                        //System.out.println("AQUI!!!!!!!!!!!!!!!!!!"+number);
                        bean.getClass().getSuperclass().getDeclaredMethod("setSeqnum", Long.class).invoke(bean, number);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            formRendered = false;
            //return "/f/cadastro/usuario/usuarioList.xhtml";
        } catch (Throwable ex) {
            msgStr = "Falha no processamento. Motivo: " + ex.getClass()+"-"+ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (!msgStr.isEmpty()) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void doBeanSave() throws Throwable {
        FacesMessage msg = null;
        try {
            if (notReadOnly) {
                if (!Boolean.valueOf(false).equals(checkContract())) {
                    bean = (T) baseDao.save(bean);
                    //bean.save();
                    //usuario = new Usuario();
                    doBeanList();
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro gravado com sucesso!", "");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Contrato Indefinido! Selecione antes um contrato padrão.", "");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso somente para leitura! Não foi possível gravar registro.", "");
            }
        } catch (Throwable ex) {
            if (ex.getCause() instanceof Error) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getMessage(), "");
                throw ex;
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na gravação. Motivo: " + ex.getClass()+"-"+ex.getMessage(), "");
            }
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void doBeanSave(Object... values) {
        doBeanSaveAndList(true, true, true, values);
    }

    public void doBeanSaveAndList(boolean refresh, boolean newBean, boolean list, Object... values) {
        FacesMessage msg = null;
        try {
            if (notReadOnly) {
                if (!Boolean.valueOf(false).equals(checkContract())) {
                    baseDao.saveRefresh(refresh, values);
                    //bean.save();
                    //usuario = new Usuario();
                    if (list) {
                        doBeanList(newBean, null, null);
                    }else{
                        if (newBean) {
                            bean = newInstance();
                        }
                    }
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro gravado com sucesso!", "");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Contrato Indefinido! Selecione antes um contrato padrão.", "");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso somente para leitura! Não foi possível gravar registro.", "");
            }
        } catch (Throwable ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na gravação. Motivo: " + ex.getClass()+"-"+ex.getMessage(), "");
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void doBeanRemove() {
        doBeanRemove(bean);
    }

    public void doBeanRemove(Object beanObj) {
        FacesMessage msg = null;
        try {
            if (notReadOnly) {
                baseDao.remove(beanObj);
                doBeanList();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", "");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso somente para leitura! Não foi possível excluir registro.", "");
            }
        } catch (javax.persistence.RollbackException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na exclusão. Verifique se o registro não está sendo utilizado e repita a operação.", "");
        } catch (javax.transaction.RollbackException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na exclusão. Verifique se o registro não está sendo utilizado e repita a operação.", "");
        } catch (Throwable ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na exclusão. Motivo: " + ex.getClass()+"-"+ex.getMessage(), "");
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Object doBeanRefresh(Object obj) {
        FacesMessage msg = null;
        try {
        	obj = baseDao.findByObj(obj);
        	return obj;
        } catch (javax.persistence.RollbackException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização. Verifique se o registro está disponível e repita a operação.", "");
        } catch (javax.transaction.RollbackException ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização. Verifique se o registro está disponível e repita a operação.", "");
        } catch (Throwable ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização. Motivo: " + ex.getClass()+"-"+ex.getMessage(), "");
        }    	
        FacesContext.getCurrentInstance().addMessage(null, msg);        		
        return null;
    }

    public void doBeanForm() {
        formRendered = true;
    }

    public static String sendEmail(final List<String[]> destinatarios,
            final String emailRemet, final String nomeRemet, final String assunto, final String corpo, final List<File> files)
            throws Exception {
        String erro = "";
        try {
            Thread t = new Thread(new Runnable() {
                long timeCount = System.currentTimeMillis();

                public void run() {
                	try {
						AlgoUtil.sendEmail(destinatarios, emailRemet, nomeRemet, assunto, corpo, files);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
            erro = e.getMessage();
        } finally {
            return erro;
        }
    }    
    
	public BaseDao getBaseDao() {
		return baseDao;
	}	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public BaseBean getBaseBean() {
		return baseBean;
	}

	public void setBaseBean(BaseBean baseBean) {
		this.baseBean = baseBean;
	}		

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void onReload() {
		// TODO Auto-generated method stub
		
	}
    
}
