/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import static com.algoboss.erp.face.GenericBean.sendEmail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.algoboss.erp.dao.AdmInstantiatesSiteDao;
import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.dao.UsuarioDao;
import com.algoboss.erp.entity.AdmCompany;
import com.algoboss.erp.entity.AdmContract;
import com.algoboss.erp.entity.AdmInstantiatesSite;
import com.algoboss.erp.entity.AdmService;
import com.algoboss.erp.entity.AdmServiceContract;
import com.algoboss.erp.entity.AdmServiceModuleContract;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.entity.SecAuthorization;
import com.algoboss.erp.entity.SecUser;
import com.algoboss.erp.entity.SecUserAuthorization;
import com.algoboss.integration.face.DataSourceBean;

/**
 *
 * @author Agnaldo
 */
@Named("gerLoginBean")
@SessionScoped
public class GerLoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private BaseDao baseDao;
    private SecUser user;
    private boolean userLogged = false;
    private boolean reloadView = false;
    private boolean inRequest = false;
    private MenuModel model;
    private List<AdmService> serviceList;
    private AdmServiceModuleContract serviceModuleContract;
    private List<SecUserAuthorization> userAuthorizationList = new ArrayList();
    private AdmInstantiatesSite instantiatesSiteContract = new AdmInstantiatesSite();
    private AdmInstantiatesSite instantiatesSiteCompany = new AdmInstantiatesSite();
    private AdmInstantiatesSite instantiatesSiteSubsidiary = new AdmInstantiatesSite();
    private AdmInstantiatesSite instantiatesSiteBusinessUnit = new AdmInstantiatesSite();
    private List<AdmInstantiatesSite> instantiatesSiteList = new ArrayList();
    @Inject
    private ThemeSwitcherBean themeSwitcherBean;
    @Inject
    private BaseBean baseBean;
    @Inject
    private AdmContractBean contractBean;
    @Inject
    AdmAlgoappBean app;
    @Inject
    private DataSourceBean dataSource;
    private boolean activeSession = false;
    private HttpSession session;
    private List<Long> moduleIdList;
    private Map<String, Long> mainActionMap = new HashMap<String, Long>();
    private Map<String, Long> appEntityMap = new HashMap<String, Long>();
    private List<MenuModel> menuModelList = new ArrayList<MenuModel>();
    private String questionsOrSuggestions = "";
    private boolean newUser;
    private boolean newUserForm;
    private Map<String, HashMap<String, Object>> fileDataBaseMap = new HashMap<String, HashMap<String, Object>>();
    private int sessionTimeout;
    private Map<String,String> jndiMap = new HashMap<String, String>();
  
    public GerLoginBean() {
        user = new SecUser();
    }

    public MenuModel getModel() {
        if (model == null) {
        	if(user.getContract()!=null){
        		baseBean.setTitle(user.getContract().getSystemName());
        		themeSwitcherBean.setTheme(user.getContract().getSystemTheme());        		
        	}
			if(this.userLogged){			
				doAuthorization();
			}
        }
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public boolean isNewUserForm() {
        return newUserForm;
    }

    public void setNewUserForm(boolean newUserForm) {
        this.newUserForm = newUserForm;
    }
    
    

    public boolean isReloadView() {
		return reloadView;
	}

	public void setReloadView(boolean reloadView) {
		this.reloadView = reloadView;
	}	
	
	public boolean isInRequest() {
		return inRequest;
	}

	public void setInRequest(boolean inRequest) {
		this.inRequest = inRequest;
	}

	public String getQuestionsOrSuggestions() {
        return questionsOrSuggestions;
    }

    public void setQuestionsOrSuggestions(String questionsOrSuggestions) {
        this.questionsOrSuggestions = questionsOrSuggestions;
    }

    public List<AdmInstantiatesSite> getInstantiatesSiteList() {
        return instantiatesSiteList;
    }

    public void setInstantiatesSiteList(List<AdmInstantiatesSite> instantiatesSiteList) {
        this.instantiatesSiteList = instantiatesSiteList;
    }

    public AdmServiceModuleContract getServiceModuleContract() {
        return serviceModuleContract;
    }

    public List<SecUserAuthorization> getUserAuthorizationList() {
        return userAuthorizationList;
    }

    public SecUser getUser() {
        return user;
    }

    public void setUser(SecUser user) {
        this.user = user;
    }

    public boolean isUserLogged() {
        return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
        this.userLogged = userLogged;
    }

    public AdmInstantiatesSite getInstantiatesSiteBusinessUnit() {
        return instantiatesSiteBusinessUnit;
    }

    public AdmInstantiatesSite getInstantiatesSiteCompany() {
        return instantiatesSiteCompany;
    }

    public AdmInstantiatesSite getInstantiatesSiteContract() {
        return instantiatesSiteContract;
    }

    public AdmInstantiatesSite getInstantiatesSiteSubsidiary() {
        return instantiatesSiteSubsidiary;
    }

    public List<MenuModel> getMenuModelList() {
        return menuModelList;
    }

    public void setMenuModelList(List<MenuModel> menuModelList) {
        this.menuModelList = menuModelList;
    }

    public Map<String, Long> getMainActionMap() {
        return mainActionMap;
    }

    public void setMainActionMap(Map<String, Long> mainActionMap) {
        this.mainActionMap = mainActionMap;
    }

    public Map<String, Long> getAppEntityMap() {
		return appEntityMap;
	}

	public void setAppEntityMap(Map<String, Long> appEntityMap) {
		this.appEntityMap = appEntityMap;
	}

	public BaseBean getBaseBean() {
        return baseBean;
    }

    public Map<String, HashMap<String, Object>> getFileDataBaseMap() {
        return fileDataBaseMap;
    }

    public void setFileDataBaseMap(Map<String, HashMap<String, Object>> fileDataBaseMap) {
        this.fileDataBaseMap = fileDataBaseMap;
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }        

    public Map<String, String> getJndiMap() {
		return jndiMap;
	}

	public void setJndiMap(Map<String, String> jndiMap) {
		this.jndiMap = jndiMap;
	}

	public boolean isActiveSession() {
		return activeSession;
	}

	public void setActiveSession(boolean activeSession) {
		this.activeSession = activeSession;
	}

	public List<Long> getModuleIdList() {
		//moduleIdList = (List<Long>) session.getAttribute("moduleIdList");
		return moduleIdList;
	}

	public void setModuleIdList(List<Long> moduleIdList) {
		//getSession().setAttribute("moduleIdList", new ArrayList<Long>());
		this.moduleIdList = moduleIdList;
	}		

	public void doSendQuestionsOrSuggestions() {
        try {
            List<String[]> destin = new ArrayList<String[]>();
            destin.add(new String[]{"agnaldo_luiz@msn.com", "Agnaldo luiz."});
            String msg = questionsOrSuggestions;
            msg += "\n Nome: " + user.getName();
            msg += "\n E-mail: " + user.getEmail();
            //super.doBeanSaveAndList(false, false, user);
            sendEmail(destin, "webmaster@algoboss.com", "AlgoBoss", "AlgoBoss - Dúvidas ou Sugestões", msg, null);
            FacesMessage msgRet = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua mensagem foi enviada e em breve receberá um retorno.", "");
            FacesContext.getCurrentInstance().addMessage(null, msgRet);
            questionsOrSuggestions = "";
        } catch (Exception ex) {
            Logger.getLogger(AdmContractBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String doLogin() {
    	removeActiveSession();
        try {
            //loginBean = getFacadeWithJNDI(GerLoginBean.class);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) context.getSession(true);
            System.out.println("Timeout:" + session.getMaxInactiveInterval());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        this.model = null;
        user = new UsuarioDao(baseDao).findByEmailAndPassword(user.getEmail().toUpperCase(), user.getPassword());
        if (user != null) {
            if (user.isInactive()) {
                user = new SecUser();
                this.userLogged = false;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Usuário inativo!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            } else {
            	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    
            	Logger.getLogger(GerLoginBean.class.getName()).log(Level.INFO, "LOGIN USER: "+user.getLogin()+"/"+user.getEmail());
            	Logger.getLogger(GerLoginBean.class.getName()).log(Level.INFO, "INFO USER: "+"[IP:"+request.getHeader("x-forwarded-for")+"][BROWSER:"+request.getHeader("user-agent")+"]/"+getHeadersInfo());
                prepareSession();                
                setModuleIdList(new ArrayList<Long>());
                doAuthorization();
                if (user.getContract() != null && user.getContract().getContractId() != null) {
                    baseBean.setTitle(user.getContract().getSystemName());
                    themeSwitcherBean.setTheme(user.getContract().getSystemTheme());
                    doInstatiatesSite();
                    if (user.getContract().getServiceHomePage() != null) {
                        //userAuthorizationList = user.getUserAuthorizationList();
                        for (SecUserAuthorization userAuth : userAuthorizationList) {
                            AdmServiceContract serviceContract = userAuth.getServiceContract();
                            //serviceContract.setHitCounter(serviceContract.getHitCounter() + 1);
                            AdmService service = serviceContract.getService();
                            if (service.equals(user.getContract().getServiceHomePage())) {
                                String methodStr = user.getContract().getServiceHomePage().getMainAddress().replace("(", "(gerLoginBean.mainActionMap.get('" + service.getName()+"')");;
                                FacesContext facesContext = FacesContext.getCurrentInstance();
                                MethodExpression method = facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), String.valueOf(methodStr), null, new Class<?>[]{});
                                method.invoke(facesContext.getELContext(), null);
                                break;
                            }
                            //service.setHitCounter(service.getHitCounter() + 1);                            
                        }
                    }
                } else {
                    contractBean.indexBean(null);
                    List<AdmContract> contractList = contractBean.getBeanList();
                    for (int i = 0; i < contractList.size(); i++) {
                        AdmContract contract = contractList.get(i);
                        instantiatesSiteList.addAll(contract.getInstantiatesSiteList());
                    }
                }

                this.userLogged = true;
                getSession().setAttribute("lastTime", new Date().getTime());
                //TODO REVISAR 
                checkActiveSession();
                //doAuthorization();
                return "/f/index.xhtml?faces-redirect=true";
            }
        } else {
            user = new SecUser();
            this.userLogged = false;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login ou Password incorretos!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    public String doLogout() {
    	Logger.getLogger(GerLoginBean.class.getName()).log(Level.INFO, "LOGOUT USER: "+user.getLogin()+"/"+user.getEmail());
        removeActiveSession();
        try {
			baseDao.clearEntityManager();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.user = new SecUser();
        this.userLogged = false;
        this.model = null;
        if(session!=null){
	        session.invalidate();
	        session = null;
        }else{
        	return "/";
        }
        return "/f/login.xhtml?faces-redirect=true";
    }

    private HttpSession prepareSession() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        session = (HttpSession) context.getSession(false);
        return session;
    }

    public void doInstatiatesSite() {
        instantiatesSiteList = AdmInstantiatesSiteDao.findByContractId(user.getContract().getContractId());
        if (instantiatesSiteList == null || instantiatesSiteList.isEmpty()) {
            try {
                instantiatesSiteContract = new AdmInstantiatesSite();
                instantiatesSiteContract.setContract(user.getContract());
                instantiatesSiteContract = (AdmInstantiatesSite) baseDao.save(instantiatesSiteContract);
                if (instantiatesSiteCompany.getCompany() != null && instantiatesSiteCompany.getCompany().getCompanyId() != null) {
                    //instantiatesSiteCompany = new AdmInstantiatesSite();
                    instantiatesSiteCompany.setContract(user.getContract());
                    instantiatesSiteCompany = (AdmInstantiatesSite) baseDao.save(instantiatesSiteCompany);
                }
                if (instantiatesSiteSubsidiary.getSubsidiary() != null && instantiatesSiteSubsidiary.getSubsidiary().getSubsidiaryId() != null) {
                    //instantiatesSiteSubsidiary = new AdmInstantiatesSite();
                    instantiatesSiteSubsidiary.setContract(user.getContract());
                    instantiatesSiteSubsidiary.setCompany(instantiatesSiteCompany.getCompany());
                    instantiatesSiteSubsidiary = (AdmInstantiatesSite) baseDao.save(instantiatesSiteSubsidiary);
                }
                if (instantiatesSiteBusinessUnit.getBusinessUnit() != null && instantiatesSiteBusinessUnit.getBusinessUnit().getBusinessUnitId() != null) {
                    //instantiatesSiteBusinessUnit = new AdmInstantiatesSite();
                    instantiatesSiteBusinessUnit.setContract(user.getContract());
                    instantiatesSiteBusinessUnit.setCompany(instantiatesSiteSubsidiary.getCompany());
                    instantiatesSiteBusinessUnit.setSubsidiary(instantiatesSiteSubsidiary.getSubsidiary());
                    instantiatesSiteBusinessUnit = (AdmInstantiatesSite) baseDao.save(instantiatesSiteBusinessUnit);
                }

            } catch (Throwable ex) {
                Logger.getLogger(GerLoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (int i = 0; i < instantiatesSiteList.size(); i++) {
                AdmInstantiatesSite instantiatesSite = instantiatesSiteList.get(i);
                if (instantiatesSite.getBusinessUnit() != null) {
                    instantiatesSiteBusinessUnit = instantiatesSite;
                } 
                if (instantiatesSite.getSubsidiary() != null) {
                    instantiatesSiteSubsidiary = instantiatesSite;
                } 
                if (instantiatesSite.getCompany() != null) {
                    instantiatesSiteCompany = instantiatesSite;
                } 
                if (instantiatesSite.getContract() != null) {
                    instantiatesSiteContract = instantiatesSite;
                }
            }
            if(instantiatesSiteContract!=null && instantiatesSiteContract.getContract()!=null){
            	AdmCompany company =  instantiatesSiteContract.getContract().getCompanyList().get(0);
            	instantiatesSiteContract.setCompany(company);
            	instantiatesSiteCompany = instantiatesSiteContract;
            	WebApplicationContext context =  FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());	
            	//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContextSmall.xml");
			    
            	dataSource = context.getBean(DataSourceBean.class);            	
            	dataSource.setUrl(company.getDataSource());
            }
        }
        
    }

    public void doUpdateUser() {
        user = new UsuarioDao(baseDao).findByEmailAndPassword(user.getEmail().toUpperCase(), user.getPassword());
    }

    public void doAuthorization() {
        try {
            userAuthorizationList = new ArrayList();
            if (!user.isInactive()) {
                if (!user.isAdministrator() && user.getContract() != null) {
                    userAuthorizationList = user.getUserAuthorizationList();
                } else if (user.isAdministrator() && user.getContract() != null) {
                    List<AdmServiceModuleContract> moduleList = user.getContract().getServiceModuleContractList();
                    for (AdmServiceModuleContract admServiceModuleContract : moduleList) {
                    	if(admServiceModuleContract.isInactive()){
                    		continue;
                    	}
                        List<AdmServiceContract> serviceContractList = admServiceModuleContract.getServiceContractList();
                        for (AdmServiceContract admServiceContract : serviceContractList) {
                        	if(admServiceContract.isInactive()){
                        		continue;
                        	}
                            SecUserAuthorization userAuthorization = new SecUserAuthorization();
                            SecAuthorization authorization = new SecAuthorization();
                            authorization.setAllowed(true);
                            userAuthorization.setUserAuthorizationId(Double.valueOf(Math.random() * 1000 * Math.random() * 1000).longValue());
                            userAuthorization.setAuthorization(authorization);
                            userAuthorization.setServiceContract(admServiceContract);
                            admServiceContract.setServiceModuleContract(admServiceModuleContract);
                            userAuthorizationList.add(userAuthorization);
                        }
                    }
                } else if (user.isAdministrator() && user.getContract() == null) {
                    loadAllServices();
                }
            }
            Collections.sort(userAuthorizationList, new Comparator<SecUserAuthorization>() {
                @Override
                public int compare(SecUserAuthorization o1, SecUserAuthorization o2) {
                    if (o1 != null && o1.getServiceContract() != null && o1.getServiceContract().getService() != null && o1.getServiceContract().getService().getServiceId() != null
                            && o2 != null && o2.getServiceContract() != null && o2.getServiceContract().getService() != null && o2.getServiceContract().getService().getServiceId() != null) {
                        return o1.getServiceContract().getService().getServiceId().compareTo(o2.getServiceContract().getService().getServiceId());
                    } else {
                        return 0;
                    }
                }
            });
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Application application = facesContext.getApplication();
            ELContext elContext = facesContext.getELContext();
            ExpressionFactory expressionFactory = application.getExpressionFactory();

            //MethodExpression methodExpression = expressionFactory.createMethodExpression(elContext, "#{secUserBean.indexBean()}", String.class, new Class[0]);
            mainActionMap = new HashMap<String, Long>();
            appEntityMap = new HashMap<String, Long>();
            model = new DefaultMenuModel();
            menuModelList = new ArrayList<MenuModel>();
            //MenuItem item = new MenuItem();
            MenuModel containerMenu = model;
            DefaultSubMenu startSubmenu = new DefaultSubMenu();
            startSubmenu.setLabel("Inicial");
            startSubmenu.setIcon("ui-icon-home");
            startSubmenu.setStyle("height: 20px;top:0;");
            startSubmenu.setStyleClass("botaoMenu");
            //model.addElement(startSubmenu);
            DefaultMenuItem openItem = new DefaultMenuItem();
            openItem.setValue("Abrir");
            openItem.setCommand("#{baseBean.main()}");
            openItem.setUpdate(":basePanel");
            //containerMenu.getElements().add(openItem);
            boolean representative = false;
            if (user.getRepresentative() != null && user.getRepresentative().getRepresentativeId() != null) {
                representative = true;
            }

            if (user.isBoss()) {
                DefaultSubMenu internSubmenu = new DefaultSubMenu();
                internSubmenu.setLabel("Área Administrador");
                containerMenu.getElements().add(internSubmenu);
            }
            if (representative || user.isBoss()) {
                DefaultSubMenu representativeSubmenu = new DefaultSubMenu();
                representativeSubmenu.setLabel("Área Representante");
                containerMenu.getElements().add(representativeSubmenu);
                DefaultMenuItem representativeItem = new DefaultMenuItem();
                representativeItem.setValue("Representante");
                representativeItem.setCommand("#{admRepresentativeBean.indexBean(null)}");
                representativeItem.setUpdate(":basePanel");
                representativeItem.setStyleClass("actionMenuItem");
                representativeSubmenu.getElements().add(representativeItem);
                DefaultMenuItem contractItem = new DefaultMenuItem();
                contractItem.setValue("Contrato");
                contractItem.setCommand("#{admContractBean.indexBean(null)}");
                contractItem.setUpdate(":basePanel");
                contractItem.setStyleClass("actionMenuItem");
                representativeSubmenu.getElements().add(contractItem);
                DefaultMenuItem algodevItem = new DefaultMenuItem();
                algodevItem.setValue("Algodev");
                algodevItem.setCommand("#{admAlgodevBean.indexBean(null)}");
                algodevItem.setUpdate(":basePanel");
                algodevItem.setStyleClass("actionMenuItem");
                representativeSubmenu.getElements().add(algodevItem);
            }
            for (int i = 0; i < userAuthorizationList.size(); i++) {
                SecUserAuthorization userAuthorization = userAuthorizationList.get(i);
                AdmServiceContract serviceContract = userAuthorization.getServiceContract();
                if(serviceContract.isInactive()){
                	continue;
                }
                AdmServiceModuleContract admServiceModuleContract = serviceContract.getServiceModuleContract();
                if(admServiceModuleContract.isInactive()){
                	continue;
                }
                AdmService admService = serviceContract.getService();
                if(admService.isInactive()){
                	continue;
                }
                SecAuthorization auth = userAuthorization.getAuthorization();
                if (auth.isAllowed()) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -10);
                    if (admService.getLastAccess() != null && cal.getTime().before(admService.getLastAccess()) && !user.isBoss()) {
                    	/*
                        String methodStr = "#{app.generateElementsContainerMap}";
                        //FacesContext facesContext = FacesContext.getCurrentInstance();
                        MethodExpression method = facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), String.valueOf(methodStr), null, new Class<?>[]{DevRequirement.class});
                        method.invoke(facesContext.getELContext(), new Object[]{admService.getRequirement()});       
                        */
                    	//AdmAlgoappBean app = new AdmAlgoappBean();
                        //app.setBaseBean(baseBean);
                        //app.setBaseDao(baseDao);
                    	//app.generateElementsContainerMap(admService.getRequirement());
                    }
                    String[] nivelArray = admService.getName().split(">");
                    DefaultSubMenu firstSubmenu = new DefaultSubMenu();
                    DefaultMenuItem item = new DefaultMenuItem();
                    Submenu prevMenu = null;
                    String compId = "";
                    if ("AlgoRep AlgoDev Configuração>Autorização Usuário Configuração>Usuário".contains(admService.getName())) {
                    }
                    mainActionMap.put(admService.getName(), userAuthorization.getUserAuthorizationId());
                    if(admService.getRequirement()!=null && admService.getRequirement().getEntityClass()!=null){
                    	appEntityMap.put(admService.getRequirement().getEntityClass().getName(), userAuthorization.getUserAuthorizationId());
                    }
                    for (int j = 0; j < nivelArray.length; j++) {
                        String string = nivelArray[j];
                        compId += string.replaceAll(" ", "-");
                        if (j == nivelArray.length - 1) {
                            String action = admService.getMainAddress().replace("(", "(" + userAuthorization.getUserAuthorizationId());
                            item.setValue(string);
                            item.setCommand(action);
                            item.setUpdate(":basePanel");
                            item.setStyleClass("actionMenuItem");

                            //item.setIncludeViewParams(auth.isReadOnly());
                            //item.
                            if (prevMenu != null) {
                                prevMenu.getElements().add(item);
                            }
                            //firstSubmenu.getChildren().add(item);
                        } else if (j == 0) {

                            firstSubmenu.setLabel(string);
                            firstSubmenu.setId(compId);
                            prevMenu = firstSubmenu;
                        } else {
                            DefaultSubMenu centerSubmenu = new DefaultSubMenu();
                            centerSubmenu.setLabel(string);
                            centerSubmenu.setId(compId);
                            DefaultSubMenu comp = doFindComponent(containerMenu.getElements(), compId);

                            for (int k = 0; k < firstSubmenu.getElements().size(); k++) {
                                DefaultSubMenu compAux = (DefaultSubMenu) firstSubmenu.getElements().get(k);

                                if (compAux instanceof Submenu) {
                                    DefaultSubMenu sub = (DefaultSubMenu) compAux;
                                    if (sub.getLabel().equals(string)) {
                                        comp = sub;
                                        break;
                                    }
                                }
                            }
                            if (comp == null) {
                                firstSubmenu.getElements().add(centerSubmenu);
                                comp = centerSubmenu;
                            }
                            if (comp instanceof DefaultSubMenu) {
                                prevMenu = (DefaultSubMenu) comp;
                            }
                            /*
                             * if (centerSubmenu.getLabel() == null ||
                             * centerSubmenu.getLabel().isEmpty()) {
                             * centerSubmenu.setLabel(string);
                             * firstSubmenu.getChildren().add(centerSubmenu); }
                             */
                        }
                    }
                    if (nivelArray.length > 1) {
                        DefaultSubMenu compAux = null;
                        for (MenuElement comp : containerMenu.getElements()) {
                            if (comp instanceof Submenu) {
                                DefaultSubMenu sub = (DefaultSubMenu) comp;
                                if (sub.getLabel().equals(firstSubmenu.getLabel())) {
                                    sub.getElements().addAll(firstSubmenu.getElements());
                                    compAux = sub;
                                    break;
                                }
                            }
                        }
                        if (compAux == null) {
                            containerMenu.getElements().add(firstSubmenu);
                        }
                    } else {
                        containerMenu.getElements().add(item);
                    }
                }
            }
            DefaultMenuItem exitItem = new DefaultMenuItem();
            exitItem.setValue("Sair");
            exitItem.setCommand("#{gerLoginBean.doLogout()}");
            exitItem.setUpdate(":basePanel");
            exitItem.setAjax(false);
            //startSubmenu.getElements().add(exitItem);
            model.generateUniqueIds();
            for (MenuElement me : containerMenu.getElements()) {
                if (me instanceof DefaultSubMenu) {
                    MenuModel mm = new DefaultMenuModel();
                    mm.addElement(me);
                    menuModelList.add(mm);
                }
            }
        } catch (Throwable ex) {
            Logger.getLogger(GerLoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void loadAllServices() {
        try {
            serviceList = (List<AdmService>) (Object) baseDao.findAll("findAllAdmService");
            for (AdmService admService : serviceList) {
                SecUserAuthorization userAuthorization = new SecUserAuthorization();
                AdmServiceContract serviceContract = new AdmServiceContract();
                AdmServiceModuleContract admServiceModuleContract = new AdmServiceModuleContract();
                serviceContract.setService(admService);
                userAuthorization.setServiceContract(serviceContract);
                SecAuthorization authorization = new SecAuthorization();
                authorization.setAllowed(true);
                userAuthorization.setUserAuthorizationId(Double.valueOf(Math.random() * 1000 * admService.getServiceId()).longValue());
                userAuthorization.setAuthorization(authorization);
                serviceContract.setServiceModuleContract(admServiceModuleContract);
                userAuthorizationList.add(userAuthorization);
            }
        } catch (Throwable ex) {
            Logger.getLogger(GerLoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DefaultSubMenu doFindComponent(List<MenuElement> menuElements, String id) throws Throwable {
        DefaultSubMenu found = null;
        for (Iterator<MenuElement> it = menuElements.iterator(); it.hasNext();) {
            Object compAux = it.next();
            if (compAux instanceof DefaultSubMenu) {
                DefaultSubMenu comp = (DefaultSubMenu) compAux;
                if (comp.getId() != null && comp.getId().equals(id)) {
                    found = comp;
                    break;
                } else if (comp.getElements().iterator().hasNext()) {
                    found = doFindComponent(comp.getElements(), id);
                    if (found != null) {
                        break;
                    }
                }
            }
        }

        return found;
    }

    public int getSessionTimeout() {
        if (session == null || sessionTimeout == 0) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            session = (HttpSession) context.getSession(true);
            sessionTimeout = session.getMaxInactiveInterval();
        }
        return sessionTimeout;
    }

    public HttpSession getSession() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (session == null) {
            session = (HttpSession) context.getSession(true);
        }
        return session;
    }

    public void updateActiveSession(String sessionId) {
    	if(sessionId.equals(session.getId())){    		
	        Long lastTime = (Long) session.getAttribute("lastTime");
	        if (new Date().getTime() - lastTime < updateTime * 60) {
	            session.setAttribute("activeSession", true);
	        }
    	}else{
    		doLogout();
    	}
    }
    private long updateTime = 60 * 1000;// 1 MINUTE

    public long getUpdateTime() {
        return updateTime;
    }

    private void checkActiveSession() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            	String sessionId = session.getId();
                session.setAttribute("activeSession", true);
                if (user.getContract() != null) {
                    while (userLogged) {
                        try {
                            Thread.sleep(updateTime * 2);//TIMEOUT FOR LOGOUT
                            if(session == null || !sessionId.equals(session.getId())){
                            	return;
                            }
                            boolean activeSession = session != null ? Boolean.valueOf(Objects.toString(session.getAttribute("activeSession"),"false")) : false;
                            if (activeSession) {
                                session.setAttribute("activeSession", inRequest);
                            } else {
                                break;
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GerLoginBean.class.getName()).log(Level.SEVERE, null, ex);
                            break;
                        } catch (java.lang.IllegalStateException ex) {
                            System.out.println("Exception in checkActiveSession: " + ex.getMessage());
                            break;
                        }
                    }
                    try {
                    	removeActiveSession();
                        if (session != null) {
                        	try {
                        		session.invalidate();								
							} catch (java.lang.IllegalStateException e) {
								System.out.println("Exception in checkActiveSession2: " + e.getMessage());
							}
                            session = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user = new SecUser();
                    userLogged = false;
                    model = null;
                }
            }
        });
        t.start();
    }

    //@PreDestroy
    public void sessionInative() {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            //Flash flash = fc.getExternalContext().getFlash();

            // Put the exception in the flash scope to be displayed in the error 
            // page if necessary ...
            //flash.put("errorDetails", throwable.getMessage());

            //System.err.println("Error Message: " + throwable.getMessage());

            NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();

            navigationHandler.handleNavigation(fc, null, "error?faces-redirect=true");

            fc.renderResponse();
            FacesContext.getCurrentInstance().getExternalContext().redirect("error?faces-redirect=true");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void removeActiveSession() {
        System.out.println("REMOVE ACTIVE SESSION!");
        try {
            if (!getUser().isBoss() && getUser().getRepresentative() == null && getUser().getContract() != null) {
                List<AdmServiceModuleContract> moduleList = getUser().getContract().getServiceModuleContractList();
                for (int i = 0; i < moduleList.size(); i++) {
                    AdmServiceModuleContract module = moduleList.get(i);
                    moduleIdList = getModuleIdList();
                    if (moduleIdList != null && moduleIdList.contains(module.getServiceModuleContractId())) {
                        try {
                            module = (AdmServiceModuleContract) baseDao.findByObj(module);
                            if(module.getCurrentAmount()>0){
                            	module.setCurrentAmount(module.getCurrentAmount() - 1);
                            	baseDao.save(module);
                            	moduleIdList.remove(module.getServiceModuleContractId());                            	
                            }
                        } catch (Throwable ex) {
                            Logger.getLogger(GenericBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    private Map<String, String> getHeadersInfo() {
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    	 
    	Map<String, String> map = new HashMap<String, String>();
     
    	Enumeration headerNames = request.getHeaderNames();
    	while (headerNames.hasMoreElements()) {
    		String key = (String) headerNames.nextElement();
    		String value = request.getHeader(key);
    		map.put(key, value);
    	}
     
    	return map;
      }    
}
