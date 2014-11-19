/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.ServiceDao;
import com.algoboss.erp.entity.AdmContract;
import com.algoboss.erp.entity.AdmService;
import com.algoboss.erp.entity.AdmServiceContract;
import com.algoboss.erp.entity.AdmServiceModuleContract;
import com.algoboss.erp.entity.DevComponentContainer;
import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityClassConfig;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyDescriptorConfig;
import com.algoboss.erp.entity.DevPrototypeComponentChildren;
import com.algoboss.erp.entity.DevPrototypeComponentProperty;
import com.algoboss.erp.entity.DevReportRequirement;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.entity.SecAuthorization;
import com.algoboss.erp.entity.SecUserAuthorization;
import com.algoboss.erp.util.AlgoUtil;
import com.algoboss.erp.util.AlgodevUtil;
import com.algoboss.erp.util.ComponentFactory;
import com.algoboss.erp.util.LayoutFieldsFormat;
import com.algoboss.erp.util.ComponentFactory.AppType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import static com.algoboss.erp.util.ComponentFactory.getProperty;

import java.beans.PropertyDescriptor;
import java.lang.Object;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Agnaldo
 */
@Named()
@SessionScoped
public class AdmAlgodevBean extends GenericBean<DevRequirement> {

    private UIComponent algoContainer;
    private UIComponent algoPalette;
    private UIComponent elementSelected;
    private List<UIComponent> childrenElementSelected = new ArrayList<UIComponent>();
    private List<UIComponent> facetsElementSelected = new ArrayList<UIComponent>();
    private Map<String, String[]> elementPropertiesSelected = new TreeMap<String, String[]>();
    private List<UIComponent> elements = new ArrayList<UIComponent>();
    private DevEntityClass entity;
    private DevEntityClass entityNodeSelected;
    private DevEntityPropertyDescriptor entityPropertyDescriptor;
    private List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = new ArrayList<DevEntityPropertyDescriptor>();
    private List<DevEntityClass> entityClassNodeList = new ArrayList<DevEntityClass>();
    private Map<Integer, DevEntityPropertyDescriptor> propertyTypeNodeMap = new HashMap<Integer, DevEntityPropertyDescriptor>();    
    private Long autorizationUserIdTemp = null;
    private String containerPage = "form";
    private Map<String, List<UIComponent>> elementsContainerMap = new HashMap();
    private DevEntityClass entitySelected;
    private List<DevEntityPropertyDescriptor> propertySelectedListCollection;
    private List<DevEntityPropertyDescriptor> propertySelectedFormCollection;
    private String strUIComponentId;
    private List<DevEntityClassConfig> entityClassConfigList;
    private List<DevEntityPropertyDescriptorConfig> entityPropertyDescriptorConfigList;
    private Date data = new Date();
    private String actualServiceDescription;
    private List<DevEntityClass> propEntList;
    
    @Inject
    AdmAlgoreportBean algoRep;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Creates a new instance of TipoDespesaBean
     */
    public AdmAlgodevBean() {
        super.url = "views/administration/algodev/algodevList.xhtml";
        super.namedFindAll = "findAllAdmRepresentative";
        super.type = DevRequirement.class;
        super.urlForm = "algodevForm.xhtml";
        super.subtitle = "Administração | Algodev | Projeto";
        AlgodevUtil.translateCmd();
        initBean();
    }

    public String getStrUIComponentId() {
        return strUIComponentId;
    }

    public void setStrUIComponentId(String strUIComponentId) {
        this.strUIComponentId = strUIComponentId;
    }

    public String getContainerPage() {
        return containerPage;
    }

    public void setContainerPage(String containerPage) {
        this.containerPage = containerPage;
        updateContainerPage();
    }

    public void updateContainerPage() {
        elements = elementsContainerMap.get(containerPage);
    }

    public void clearContainerPage() {
        elements.clear();
    }

    public Object[] doElementsConatainerList() {
        return elementsContainerMap.keySet().toArray();
    }

    public DevEntityClass getEntitySelected() {
        return entitySelected;
    }

    public void setEntitySelected(DevEntityClass entitySelected) {
        if (entitySelected != null && actualConstructorTabIndex == 1) {
            this.entitySelected = entitySelected;
            setPropertySelectedListCollection(entitySelected.getEntityPropertyDescriptorList());
            setPropertySelectedFormCollection(entitySelected.getEntityPropertyDescriptorList());
            algoRep.setEntity(entitySelected);
        }
    }

    public List<DevEntityPropertyDescriptor> getPropertySelectedListCollection() {
        return propertySelectedListCollection;
    }

    public void setPropertySelectedListCollection(List<DevEntityPropertyDescriptor> propertySelectedListCollection) {
        this.propertySelectedListCollection = propertySelectedListCollection;
    }

    public List<DevEntityPropertyDescriptor> getPropertySelectedFormCollection() {
        return propertySelectedFormCollection;
    }

    public void setPropertySelectedFormCollection(List<DevEntityPropertyDescriptor> propertySelectedFormCollection) {
        this.propertySelectedFormCollection = propertySelectedFormCollection;
    }

    public void addPropertyDescritor() throws Throwable {
        String strMsg = "";
        if (entityPropertyDescriptor.getPropertyName() == null) {
            strMsg = "Nome obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (entityPropertyDescriptor.getPropertyType() == null) {
            strMsg = "Tipo obrigatório;\n";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    strMsg, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if (strMsg.isEmpty()) {
            if (entityPropertyDescriptor.getEntityPropertyDescriptorId() == null) {
                if (!getEntity().getEntityPropertyDescriptorList().contains(entityPropertyDescriptor)) {
                    getEntity().getEntityPropertyDescriptorList().add(entityPropertyDescriptor);
                }
            }
            if (entityPropertyDescriptorConfigList != null && !entityPropertyDescriptorConfigList.isEmpty()) {
                entityPropertyDescriptor.setEntityPropertyDescriptorConfigList(entityPropertyDescriptorConfigList);
            }
            entityPropertyDescriptor = new DevEntityPropertyDescriptor();
            entityPropertyDescriptorConfigList = new ArrayList<DevEntityPropertyDescriptorConfig>();
        }
    }

    public void removePropertyDescritor() throws Throwable {
        getEntity().getEntityPropertyDescriptorList().remove(entityPropertyDescriptor);
        entityPropertyDescriptor = new DevEntityPropertyDescriptor();
    }

    public void startEditItem(String container) throws Throwable {
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("tabView:" + container + ":tabViewConfig:itemPanelAlgodev");
    }

    public void resetForm(String id) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            context.reset(id);
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelEditItem() {
        entityPropertyDescriptor = new DevEntityPropertyDescriptor();
    }

    public void configItem() {
        //entityPropertyDescriptor = new DevEntityPropertyDescriptor();
        if (entityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null && !entityPropertyDescriptor.getEntityPropertyDescriptorConfigList().isEmpty()) {
            entityPropertyDescriptorConfigList = entityPropertyDescriptor.getEntityPropertyDescriptorConfigList();
        } else {
            entityPropertyDescriptorConfigList = new ArrayList<DevEntityPropertyDescriptorConfig>();
        }
    }

    public void addEntityNode() throws Throwable {
        entity = new DevEntityClass();
        entityClassNodeList.add(entity);
        if (entityClassNodeList.size() > 1 && entityPropertyDescriptor.getPropertyType() != null) {
            propertyTypeNodeMap.put(entityClassNodeList.size(), entityPropertyDescriptor);
            if (entityPropertyDescriptor.getPropertyLabel() != null && !entityPropertyDescriptor.getPropertyLabel().isEmpty()) {
                entity.setLabel(entityPropertyDescriptor.getPropertyLabel());
                entity.getEntityPropertyDescriptorParentList().add(entityPropertyDescriptor);
            }
        }
        entityPropertyDescriptor = new DevEntityPropertyDescriptor();
        entitySelected = entity;
    }

    public void editEntityNode() throws Throwable {
        if (entityPropertyDescriptor.getPropertyClass() != null) {
            entityClassNodeList.add(entityPropertyDescriptor.getPropertyClass());
            propertyTypeNodeMap.put(entityClassNodeList.size(), entityPropertyDescriptor);
            entityPropertyDescriptor = new DevEntityPropertyDescriptor();
        }
    }

    public void removeEntityNode() throws Throwable {
        if (!entityClassNodeList.isEmpty()) {
            propertyTypeNodeMap.remove(entityClassNodeList.size());
            entityClassNodeList.remove(entityClassNodeList.size() - 1);
        }
        entityPropertyDescriptor = new DevEntityPropertyDescriptor();
    }

    public void applyEntityNode() throws Throwable {
        if (entityClassNodeList.size() > 1) {
            DevEntityClass prevNode = entityClassNodeList.get(entityClassNodeList.size() - 2);
            DevEntityClass actualNode = getEntity();
            entityPropertyDescriptor = propertyTypeNodeMap.get(entityClassNodeList.size());
            entityPropertyDescriptor.setPropertyClass(actualNode);
            if (entityPropertyDescriptor.getPropertyLabel() == null || entityPropertyDescriptor.getPropertyLabel().isEmpty()) {
                entityPropertyDescriptor.setPropertyLabel(actualNode.getLabel());
                actualNode.getEntityPropertyDescriptorParentList().add(entityPropertyDescriptor);
                prevNode.getEntityPropertyDescriptorList().add(entityPropertyDescriptor);
            }
        } else {
            bean.setEntityClass(getEntity());
            updateTreeExpression();
        }
        removeEntityNode();
    }

    public DevEntityClassConfig idxEntityClassConfig(int idx, boolean reset) {
        DevEntityClassConfig config = new DevEntityClassConfig();
        if (reset) {
            if (entity.getEntityClassConfigList() != null && !entity.getEntityClassConfigList().isEmpty()) {
                entityClassConfigList = entity.getEntityClassConfigList();
            } else {
                //entityClassConfigList = new ArrayList<DevEntityClassConfig>();
                //entitySelected.setEntityClassConfigList(entityClassConfigList);
            }
            if (entityClassConfigList == null) {
                entityClassConfigList = new ArrayList<DevEntityClassConfig>();
            }
        }
        if(entityClassConfigList!=null){
	        if (entityClassConfigList.size() <= idx) {
	            entityClassConfigList.add(config);
	        } else {
	            config = entityClassConfigList.get(idx);
	        }
        }
        return config;
    }

    public void addEntityClassConfig() {
        entity.setEntityClassConfigList(entityClassConfigList);
        entityClassConfigList = null;
    }

    public DevEntityPropertyDescriptorConfig idxEntityPropertyDescriptorConfig(String name, Object objDefault) {
        DevEntityPropertyDescriptorConfig config = null;
        if (entityPropertyDescriptorConfigList != null) {
            for (DevEntityPropertyDescriptorConfig configAux : entityPropertyDescriptorConfigList) {
                if (configAux.getConfigName().equals(name)) {
                    config = configAux;
                    break;
                }
            }
            if (config == null) {
                config = new DevEntityPropertyDescriptorConfig();
                config.setConfigName(name);
                config.setConfigValue(String.valueOf(objDefault));
                entityPropertyDescriptorConfigList.add(config);
            }
        } else {
            config = new DevEntityPropertyDescriptorConfig();
        }
        return config;
    }
    /*
     public DevEntityPropertyDescriptorConfig idxEntityPropertyDescriptorConfig(int idx, boolean reset) {
     DevEntityPropertyDescriptorConfig config = new DevEntityPropertyDescriptorConfig();
     if (reset) {
     if (entityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null && !entityPropertyDescriptor.getEntityPropertyDescriptorConfigList().isEmpty()) {
     entityPropertyDescriptorConfigList = entityPropertyDescriptor.getEntityPropertyDescriptorConfigList();
     } else {
     entityPropertyDescriptorConfigList = new ArrayList<DevEntityPropertyDescriptorConfig>();
     }
     }
     if (entityPropertyDescriptorConfigList.size() <= idx) {
     entityPropertyDescriptorConfigList.add(config);
     } else {
     config = entityPropertyDescriptorConfigList.get(idx);
     }
     return config;
     }
     */

    public DevEntityClass getEntity() throws Throwable {

        if (entityClassNodeList.isEmpty()) {
            //entityClassNodeList.add(entity);
        } else {
            setEntity(entityClassNodeList.get(entityClassNodeList.size() - 1));
        }

        return entity;
    }

    public void setEntity(DevEntityClass entity) {
        if (entity != null && actualConstructorTabIndex == 1) {
            entitySelected = entity;
            setPropertySelectedListCollection(entity.getEntityPropertyDescriptorList());
            setPropertySelectedFormCollection(entity.getEntityPropertyDescriptorList());
            //algoRep.setEntity(entity);
        }
        this.entity = entity;
    }

    public DevEntityClass getEntityNodeSelected() {
        return entityNodeSelected;
    }

    public void setEntityNodeSelected(DevEntityClass entityNodeSelected) {
        this.entityNodeSelected = entityNodeSelected;
    }

    public List<DevEntityClass> getEntityClassList() {
        return AlgodevUtil.entityClassList;
    }

    public List<DevEntityClass> getEntityClassNodeList() {
        return entityClassNodeList;
    }

    public DevEntityPropertyDescriptor getEntityPropertyDescriptor() {
        return entityPropertyDescriptor;
    }

    public void setEntityPropertyDescriptor(DevEntityPropertyDescriptor entityPropertyDescriptor) {
        this.entityPropertyDescriptor = entityPropertyDescriptor;
        entityPropertyDescriptorConfigList = new ArrayList<DevEntityPropertyDescriptorConfig>();
    }

    public UIComponent getAlgoContainer() {
        return algoContainer;
    }

    public void setAlgoContainer(UIComponent algoContainer) {
        if (elements != null && algoContainer!=null) {
            algoContainer.getChildren().clear();
            algoContainer.getChildren().addAll(elements);
        }
        this.algoContainer = algoContainer;
    }    
    
    public void setAlgoContainerEvt(ComponentSystemEvent algoContainerEvt) throws Throwable {
    	UIComponent algoContainer = algoContainerEvt.getComponent();
        if (elements != null && algoContainer!=null) {
            algoContainer.getChildren().clear();
            algoContainer.getChildren().addAll(elements);
        }
        this.algoContainer = algoContainer;
    }

    public UIComponent getAlgoPalette() {
        return algoPalette;
    }


    public void setAlgoPalette(UIComponent algoPalette) {
        this.algoPalette = algoPalette;
    }    
    
    public void setAlgoPaletteEvt(ComponentSystemEvent algoPaletteEvt) {
    	UIComponent algoPalette = algoPaletteEvt.getComponent();
        this.algoPalette = algoPalette;
    }

    public UIComponent getElementSelected() {
        return elementSelected;
    }

    public void setElementSelected(UIComponent elementSelected) {
        this.elementSelected = elementSelected;
    }

    public List<UIComponent> getChildrenElementSelected() throws Throwable {
        if (elementSelected != null) {
            //childrenElementSelected = elementSelected.getChildren();
        }
        return childrenElementSelected;
    }
    public void setFacetsElementSelected(Map<String, UIComponent> facetsElementSelected) throws Throwable {
        this.childrenElementSelected = new ArrayList(facetsElementSelected.values());
    }
    public void setChildrenElementSelected(List<UIComponent> childrenElementSelected) throws Throwable {
        this.childrenElementSelected = childrenElementSelected;
        if (elementSelected != null && elementSelected.getChildren() != null) {
            //List<UIComponent> childrenElementSelectedOld = new ArrayList<UIComponent>();
            List<UIComponent> childrenElementSelectedNew = new ArrayList<UIComponent>();
            childrenElementSelectedNew.addAll(childrenElementSelected.subList(childrenElementSelected.size() - elementSelected.getChildren().size(), childrenElementSelected.size()));
            /*
             for (UIComponent uIComponent : childrenElementSelected) {
             if (elementSelected.getChildren().contains(uIComponent) && !childrenElementSelectedOld.contains(uIComponent)) {
             childrenElementSelectedOld.add(uIComponent);
             }else{
             childrenElementSelectedNew.add(uIComponent);
             }
             }*/
            if (!childrenElementSelectedNew.isEmpty()) {
                UIComponent compTarget = algoContainer.getParent().findComponent("algoContainer");
                elementSelected.getChildren().clear();
                elementSelected.getChildren().addAll(childrenElementSelectedNew);
                UIComponent cloned = ComponentFactory.componentClone(elementSelected, true);
                //UIComponent cloned = ComponentFactory.cloneComp(f2, elementSelected, context);
                UIComponent container = elementSelected.getParent();
                if (container.getId().equals(compTarget.getId())) {
                    removeElement(elementSelected);
                    updateChildren(elements, cloned);
                } else {
                    container.getChildren().remove(elementSelected);
                    updateChildren(container.getChildren(), cloned);
                }
                //cloned.setId(elementSelected.getId());
                elementSelected = cloned;
                //generateElementsContainerMap();
                //updateContainerPage();
                compTarget.getChildren().clear();
                compTarget.getChildren().addAll(elements);
            }
        }
    }

    public Map<String, String[]> getElementPropertiesSelected() {
        return elementPropertiesSelected;
    }

    public void setElementPropertiesSelected(Map<String, String[]> elementPropertiesSelected) {
        this.elementPropertiesSelected = elementPropertiesSelected;
    }

    public void cleanUpApp() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) context.getSession(false);
        session.removeAttribute("org.jboss.weld.context.http.HttpSessionContext#org.jboss.weld.bean-web-ManagedBean-class com.erp.face.AdmAlgoappBean");
    }

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
            //doBeanList();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    prepareIndexBean();
                }
            });
            t.start();
            algoRep.initBean();
            //Collection<Object[]> componentList = (HashSet<Object[]>) objIn.readObject();
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }	
    }

    private List<DevEntityClass> findEntityClass() {
        try {
        	List<DevEntityClass> findEntity = (List<DevEntityClass>) (Object) baseDao.findAll("findAllDevEntityClass");
        	List<DevEntityClass> mineEntityList = new ArrayList();
        	List<DevEntityClass> theirsEntityList = new ArrayList();
        	List<DevEntityClass> allEntityList = new ArrayList();
        	
        	for (Iterator iterator = findEntity.iterator(); iterator
					.hasNext();) {
				DevEntityClass devEntityClass = (DevEntityClass) iterator
						.next();
				if(devEntityClass.getInstantiatesSite()!=null && devEntityClass.getInstantiatesSite().getInstantiatesSiteId().equals(site.getInstantiatesSiteId())){
					mineEntityList.add(devEntityClass);
				}else{
					theirsEntityList.add(devEntityClass);
				}
			}
        	allEntityList.addAll(mineEntityList);
        	allEntityList.addAll(theirsEntityList);
            return allEntityList;
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void prepareIndexBean() {
        try {
            elements.clear();
            AlgodevUtil.entityClassList = findEntityClass();
            entitySelected = new DevEntityClass();
            propertySelectedListCollection = new ArrayList<DevEntityPropertyDescriptor>();
            propertySelectedFormCollection = new ArrayList<DevEntityPropertyDescriptor>();
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void indexBeanForm(Long nro) throws Throwable {
        try {
            DevRequirement beanAux = bean;
            super.indexBean(nro);
            doBeanList();
            prepareIndexBean();
            bean = beanAux;
            doBeanForm();
            //Collection<Object[]> componentList = (HashSet<Object[]>) objIn.readObject();
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    public Long getAutorizationUserIdTemp() throws Throwable {
        setAutorizationUserIdTemp();
        return autorizationUserIdTemp;
    }

    public void setAutorizationUserIdTemp() throws Throwable {
        SecUserAuthorization userAuth = new SecUserAuthorization();
        userAuth.setUserAuthorizationId(autorizationUserIdTemp);

        if (autorizationUserIdTemp == null || !loginBean.getUserAuthorizationList().contains(userAuth)) {
            AdmService service = new AdmService("Aplicativo>App>Teste", "Aplicativo Teste", "Aplicativo", "#{admAlgoappBean.indexBean()}", null, "1", new Date());
            service.setRequirement(bean);
            SecUserAuthorization userAuthorization = new SecUserAuthorization();
            AdmServiceContract serviceContract = new AdmServiceContract();
            serviceContract.setService(service);
            userAuthorization.setServiceContract(serviceContract);
            SecAuthorization authorization = new SecAuthorization();
            authorization.setAllowed(true);
            AdmServiceModuleContract module = new AdmServiceModuleContract();
            module.setName("Aplicativo");
            module.setExpectedAmount(2);
            serviceContract.setServiceModuleContract(module);
            autorizationUserIdTemp = Double.valueOf((Math.random() * 1000d) * (Math.random() * 1000d)).longValue();
            userAuthorization.setUserAuthorizationId(autorizationUserIdTemp);
            userAuthorization.setAuthorization(authorization);
            loginBean.getUserAuthorizationList().add(userAuthorization);
        }

    }

    @Override
    public void initBean() {
        elementPropertiesSelected = new TreeMap<String, String[]>();
        elements = new ArrayList();
        entity = new DevEntityClass();
        entitySelected = entity;
        entityPropertyDescriptor = new DevEntityPropertyDescriptor();
        entityPropertyDescriptorConfigList = new ArrayList<DevEntityPropertyDescriptorConfig>();
        entityClassNodeList.clear();
        propertyTypeNodeMap.clear();
        entityNodeSelected = entity;
        elementsContainerMap.clear();
        elementsContainerMap.put("form", elements);
        elementsContainerMap.put("list", new ArrayList<UIComponent>());
        childrenElementSelected = new ArrayList();
        elementSelected = null;
        autorizationUserIdTemp = null;
        actualConstructorTabIndex = 0;
        super.initBean();
    }

    @Override
    public void doBeanList() {
        try {
            super.doBeanList();
            List<DevRequirement> beanListAux = new ArrayList<DevRequirement>();
            for (DevRequirement devRequirement : beanList) {
                List<SecUserAuthorization> userAuthList = loginBean.getUserAuthorizationList();
                for (SecUserAuthorization secUserAuthorization : userAuthList) {
                    if (devRequirement.getService() != null && secUserAuthorization.getServiceContract() != null && secUserAuthorization.getServiceContract().getService() != null
                            && devRequirement.getService().getServiceId().equals(secUserAuthorization.getServiceContract().getService().getServiceId())) {
                        beanListAux.add(devRequirement);
                        break;
                    }
                }
            }
            beanList = beanListAux;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doBeanForm() {
        autorizationUserIdTemp = null;
        actualConstructorTabIndex = 0;
        elementsContainerMap.clear();
        if (bean != null) {
        	try {
	            entity = bean.getEntityClass();
	            entitySelected = entity;
	            entityNodeSelected = entity;
	            entityPropertyDescriptorConfigList.clear();
	            entityClassNodeList.clear();
	            propertyTypeNodeMap.clear();
	            actualServiceDescription = bean.getService().getDescription();
	            algoRep.setFieldContainerList(bean.getFieldContainerList());
                entityClassNodeList.add(entity);
                List<DevComponentContainer> componentContainerList = bean.getComponentContainerList();
                for (DevComponentContainer devComponentContainer : componentContainerList) {
                    elements = AlgodevUtil.generateComponentList(devComponentContainer);
                    elementsContainerMap.put(devComponentContainer.getName(), elements);
                    containerPage = devComponentContainer.getName();
                }
                //setAutorizationUserIdTemp();
                updateTreeExpression();
            } catch (Throwable ex) {
                Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o Projeto.", ""));
        }
        //super.doBeanForm(); //To change body of generated methods, choose Tools | Templates.
    }

    public void doBeanNew() {
        initBean();
        bean = new DevRequirement();
        bean.setContract(loginBean.getUser().getContract());
        bean.setEntityClass(entity);
    }

    public void doEntityRemove(DevEntityClass obj) {
        try {
            super.doBeanRemove(obj);
            entity = new DevEntityClass();
            AlgodevUtil.entityClassList = findEntityClass();
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanRemove() {
        super.doBeanRemove();
        AdmContract contract = loginBean.getUser().getContract();
        loginBean.doUpdateUser();
        initBean();
        bean.setEntityClass(entity);
        if (contract != null && loginBean.getUser().isBoss()) {
            contract = (AdmContract) doBeanRefresh(contract);
            loginBean.getUser().setContract(contract);
        }
    }
    public List<DevEntityClass> getExtPropEntList(DevEntityClass entClas) {
    	return getPropEntList(entClas, "EXT");
    }
    public List<DevEntityClass> getIntPropEntList(DevEntityClass entClas) {
    	return getPropEntList(entClas, "INT");
    }
    public List<DevEntityClass> getPropEntList(DevEntityClass entClas, String type) {
        List<DevEntityClass> propEntList = new ArrayList<DevEntityClass>();
        if (entClas != null) {
        	if(entClas.getInstantiatesSite()==null){
        		entClas.setInstantiatesSite(loginBean.getInstantiatesSiteContract());
        	}
            for (DevEntityPropertyDescriptor entProp : entClas.getEntityPropertyDescriptorList()) {
                if (entProp.getPropertyClass() != null && ((type.equals("EXT") && AlgodevUtil.isExternalPropertyClass(entProp.getPropertyType()))||(type.equals("INT") && AlgodevUtil.isInternalPropertyClass(entProp.getPropertyType()))) ) {
                    propEntList.addAll(getPropEntList(entProp.getPropertyClass(),type));
                }
            }
            if (!entClas.getName().equals(entity.getName())) {
                propEntList.add(entClas);
            }
        }
        return propEntList;
    }
    
    private boolean autorizationCheck() {
        boolean check = true;
        FacesContext ctx = FacesContext.getCurrentInstance();
        if ((loginBean.getUser().getContract() != null && bean.getContract() != null && !loginBean.getUser().getContract().getContractId().equals(bean.getContract().getContractId())) || (loginBean.getUser().getContract() == null && bean.getContract() != null) || (loginBean.getUser().getContract() == null && bean.getContract() == null && !loginBean.getUser().isBoss())) {
            String msgStr = "Projeto não pertence ao contrato atual. Alteração não permitida.";
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, ""));
            check = false;
        }
        return check;
    }
    @Override
    public void doBeanSave() {
    	doBeanSaveImpl(bean, entity, elementsContainerMap);
    }
    public void doBeanSaveImpl(DevRequirement bean,DevEntityClass entity,Map<String,List<UIComponent>> elementsContainerMap) {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            //Collection<Object[]> componentList = new HashSet<Object[]>();
            String msgStr = "";
            if (bean.getRequirementName() == null || bean.getRequirementName().isEmpty()) {
                msgStr = "Informe um nome em propriedade do projeto.";
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, ""));
            }
            if (bean.getService().getName() == null || bean.getService().getName().isEmpty()) {
                msgStr = "Informe um serviço em propriedade do projeto.";
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, ""));
            }

            if (autorizationCheck() && msgStr.isEmpty()) {
                generateElementsContainerMap(elementsContainerMap, bean);
                //this.bean.setFieldContainerList(algoRep.getFieldContainerList());
                bean.setEntityClass(entity);
                bean.getService().setMainAddress("#{app.indexBean()}");
                bean.getService().setModule("APP");
                AdmService serviceAux = new ServiceDao(baseDao).findByServiceName(bean.getService().getName());
                if (serviceAux != null && !serviceAux.equals(bean.getService())) {
                    bean.getService().setName(bean.getService().getName().concat(" [S").concat(Integer.toString(new Random().nextInt(100)).concat("]")));
                }
                List<Object> toSaveList = new ArrayList();
                if(entity.getInstantiatesSite()==null){
                	entity.updateProperty();
                }
                toSaveList.add(loginBean.getInstantiatesSiteContract());
                toSaveList.add(bean.getService());
                propEntList = getIntPropEntList(entity);
                if (!propEntList.isEmpty()) {
                    toSaveList.addAll(propEntList);
                }
                toSaveList.add(entity);
                toSaveList.add(bean);
                AdmContract contract = loginBean.getUser().getContract();
                AdmService service = null;
                AdmServiceContract admServiceContractAux = null;
                AdmServiceModuleContract admServiceModuleContractAux = null;
                if (contract != null) {
                    bean.setContract(contract);
                    List<AdmServiceModuleContract> serviceModuleContractList = contract.getServiceModuleContractList();
                    for (AdmServiceModuleContract admServiceModuleContract : serviceModuleContractList) {
                        if (admServiceModuleContract.getName().equals(bean.getService().getModule())) {
                            admServiceModuleContractAux = admServiceModuleContract;
                            break;
                        }
                    }
                    if (admServiceModuleContractAux != null) {
                        //.add(serviceContract);
                        List<AdmServiceContract> admServiceContractList = admServiceModuleContractAux.getServiceContractList();
                        for (AdmServiceContract admServiceContract : admServiceContractList) {
                            if (admServiceContract.getService().getServiceId().equals(bean.getService().getServiceId())) {
                                admServiceContractAux = admServiceContract;
                                break;
                            }
                        }
                    } else {
                        admServiceModuleContractAux = new AdmServiceModuleContract();
                        admServiceModuleContractAux.setName(bean.getService().getModule());
                        admServiceModuleContractAux.setExpectedAmount(1);
                        admServiceModuleContractAux.setCurrentAmount(0);
                        serviceModuleContractList.add(admServiceModuleContractAux);
                    }
                    if (admServiceContractAux == null) {
                        admServiceContractAux = new AdmServiceContract();
                        admServiceContractAux.setService(bean.getService());
                        admServiceModuleContractAux.getServiceContractList().add(admServiceContractAux);
                        service = bean.getService();
                        toSaveList.add(contract);
                    }
                }
                super.doBeanSaveAndList(false, false, true, toSaveList.toArray());
                if (service != null) {
                    service.setRequirement(bean);
                } else {
                    service = bean.getService();
                    if (admServiceContractAux != null) {
                        admServiceContractAux.setService(service);
                    }
                    service.setRequirement(bean);
                }
                if (service.getServiceId() != null) {
                    if (admServiceContractAux != null) {
                        admServiceContractAux.setServiceModuleContract(admServiceModuleContractAux);
                        admServiceContractAux.setService((AdmService) super.doBeanRefresh(service));
                    }
                }
                loginBean.doAuthorization();
                AlgodevUtil.entityClassList = findEntityClass();
                entitySelected = entity;
                //setAutorizationUserIdTemp();
            }
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    private void generateElementsContainerMap(Map<String, List<UIComponent>> elementsContainerMap, DevRequirement bean) {
        try {
        	List<DevComponentContainer> componentContainerList = bean.getComponentContainerList();
        	List<DevComponentContainer> componentContainerListAux = new ArrayList<DevComponentContainer>();
            for (Map.Entry<String, List<UIComponent>> object : elementsContainerMap.entrySet()) {
                String object1 = object.getKey();
                List<UIComponent> elementList = object.getValue();
                List<DevPrototypeComponentChildren> children = new ArrayList<DevPrototypeComponentChildren>();
                for (int i = 0; i < elementList.size(); i++) {
                    UIComponent comp = elementList.get(i);
                    Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
                    f2.setAccessible(true);
                    List<DevPrototypeComponentProperty> property = ComponentFactory.componentSerializer(f2, comp, null, false);
                    children.add(new DevPrototypeComponentChildren(comp.getClass().getName(), property));
                }
                DevComponentContainer componentContainer = null;
                for (DevComponentContainer devComponentContainer : componentContainerList) {
                    if (devComponentContainer.getName().equals(object1)) {
                        //devComponentContainer.getPrototypeComponentChildrenList().clear();
                        devComponentContainer.setPrototypeComponentChildrenList(children);
                        componentContainer = devComponentContainer;
                        break;
                    }
                }
                if (componentContainer == null) {
                    componentContainer = new DevComponentContainer();
                    componentContainer.setName(object1);
                    componentContainer.setPrototypeComponentChildrenList(children);
                    //bean.getComponentContainerList().add(componentContainer);
                }
                componentContainerListAux.add(componentContainer);
                elementsContainerMap.put(componentContainer.getName(), AlgodevUtil.generateComponentList(componentContainer));
            }
            bean.setComponentContainerList(componentContainerListAux);
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    public void onSort() {
        try {

            String draggedClientId;// = ddEvent.getDragId(); //Client id of dragged component
            String droppedClientId;// = ddEvent.getDropId(); //Client id of dropped component
            //Object data = ddEvent.getData(); //Model object of a datasource
            FacesContext context = FacesContext.getCurrentInstance();
            Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            draggedClientId = String.valueOf(map.get("elementModelId"));
            String elementContainerClientId = String.valueOf(map.get("elementContainerId"));
            String action = String.valueOf(map.get("param_action"));
            String oldIndex = String.valueOf(map.get("oldIndexParam"));
            String newIndex = String.valueOf(map.get("newIndexParam"));
            String targetId = String.valueOf(map.get("targetId"));
            Object parameter2 = map.get("style");
            String draggedId = draggedClientId.split(":")[draggedClientId.split(":").length - 1];
            //UIComponent compTarget = ddEvent.getComponent().getParent().findComponent("algoContainer");
            //UIComponent comp = ddEvent.getComponent().getParent().findComponent("elementPanel");
            UIComponent compSource = ComponentFactory.doFindComponent(algoContainer.getParent().getParent(), draggedId);
            //UIComponent elementPanel = algoPalette.getParent().findComponent("elementPanel");
            //UIComponent compOldIndex = compSource.getChildren().get(Integer.parseInt(newIndex));
            UIComponent compNewIndex = compSource.getChildren().get(Integer.parseInt(oldIndex));
            if (targetId != null && !targetId.isEmpty()) {
                String droppedId = targetId.split(":")[targetId.split(":").length - 1];
                UIComponent compTarget = ComponentFactory.doFindComponent(algoContainer.getParent().getParent(), droppedId);
                compSource.getChildren().remove(Integer.parseInt(oldIndex));
                compSource = compTarget;
            } else {
            }
            compSource.getChildren().add(Integer.parseInt(newIndex), compNewIndex);

        } catch (Throwable e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }

    public void onDrop() {
        try {
            String draggedClientId;// = ddEvent.getDragId(); //Client id of dragged component
            String droppedClientId;// = ddEvent.getDropId(); //Client id of dropped component
            //Object data = ddEvent.getData(); //Model object of a datasource
            FacesContext context = FacesContext.getCurrentInstance();
            Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            draggedClientId = String.valueOf(map.get("elementModelId"));
            String elementContainerClientId = String.valueOf(map.get("elementContainerId"));
            String action = String.valueOf(map.get("param_action"));
            Object parameter2 = map.get("style");
            String draggedId = draggedClientId.split(":")[draggedClientId.split(":").length - 1];
            //UIComponent compTarget = ddEvent.getComponent().getParent().findComponent("algoContainer");
            //UIComponent comp = ddEvent.getComponent().getParent().findComponent("elementPanel");

            UIComponent elementPanel = algoPalette.getParent().findComponent("elementPanel");
            UIComponent comp3 = ComponentFactory.doFindComponent(algoContainer.getParent().getParent(), draggedId);
            UIComponent compTarget = algoContainer.getParent().findComponent("algoContainer");
            Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
            f2.setAccessible(true);
            UIComponent cloned = null;
            if (comp3 != null && (!comp3.getParent().getClientId().equals(elementContainerClientId) || action.equals("add"))) {
                String elementContainerId = elementContainerClientId.split(":")[elementContainerClientId.split(":").length - 1];
                UIComponent compTargetElementContainer = null;
                if (!action.equals("add")) {
                    compTargetElementContainer = ComponentFactory.doFindComponent(algoContainer.getParent().getParent(), elementContainerId);
                    if (!comp3.getParent().getClientId().equals(elementPanel.getClientId())) {
                        comp3.getParent().getChildren().remove(comp3);
                    }
                } else {
                    compTargetElementContainer = comp3.getParent();
                }
                if (compTargetElementContainer != null) {
                    cloned = ComponentFactory.componentClone(comp3, false);
                    updateChildren(compTargetElementContainer.getChildren(), cloned);

                    if (cloned != null) {
                        ComponentFactory.updateElementProperties(f2, cloned, map, elementPropertiesSelected);
                    }
                    if (elementContainerClientId.contains("algoContainer")) {
                        updateChildren(elements, cloned);
                    } else {
                        UIComponent parentContainer = compTargetElementContainer.getParent();
                        compTargetElementContainer = ComponentFactory.componentClone(compTargetElementContainer, true);
                        if (elementSelected != null && compTargetElementContainer.getId().equals(elementSelected.getId())) {
                            elementSelected = compTargetElementContainer;
                        }
                        parentContainer.getChildren().add(compTargetElementContainer);
                        removeElement(comp3);
                        updateChildren(elements, compTargetElementContainer);
                    }
                    createByConstructor(map, cloned, compTarget);

                    compTarget.getChildren().clear();
                    compTarget.getChildren().addAll(elements);
                }
            }
            String s = null;
        } catch (Throwable ex) {
        	Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void prepareCreateByConstructor() { 
    	try {
    		if (bean != null && bean.getService() != null){
    			String[] strPath = bean.getService().getName().split(">");
    			bean.setRequirementName(strPath.length > 0 ? strPath[strPath.length - 1] : "");
    			if(!bean.getService().getDescription().isEmpty() && !bean.getService().getDescription().equals(actualServiceDescription)) {
    				propertySelectedListCollection = new ArrayList<DevEntityPropertyDescriptor>();
    				propertySelectedFormCollection = new ArrayList<DevEntityPropertyDescriptor>();
    				Gson json = new Gson();
    				Object[] je= json.fromJson("["+AlgodevUtil.formatDescription(bean.getService().getDescription()).replace(' ', Character.toChars(0)[0])+"]",Object[].class);
    				//String[] strArray = stringToArray(je.getAsJsonArray().get(0).getAsString());
    				entity = AlgodevUtil.arrayToEntity(je,entity);
    				entity.setLabel(bean.getRequirementName());
    				entityClassNodeList.add(entity);
    				//entity = entitySelected;
    				
    				propertySelectedListCollection.addAll(entity.getEntityPropertyDescriptorList());
    				propertySelectedFormCollection.addAll(entity.getEntityPropertyDescriptorList());
    			
    			}
    		}
		} catch (Exception e) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
    }

    public void createByConstructor() {
        autorizationUserIdTemp = null;
        setNotReadOnly(true);
        //Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Map<String, Object> map = new HashMap<String, Object>();
        if (bean != null && bean.getInterfaceType() == null) {
            bean.setInterfaceType("listForm");
        }
        if (bean.getInterfaceType().equals("listForm") || bean.getInterfaceType().equals("listEditForm") || bean.getInterfaceType().equals("formList") || bean.getInterfaceType().equals("formlistEdit")) {
        	map.put("dataform", "true");
            map.put("datalist", "true");
        }    
        if (bean.getInterfaceType().equals("summary")) {
            map.put("datagrid", "true");
        }
        prepareCreateByConstructor();
        if ((propertySelectedListCollection != null && !propertySelectedListCollection.isEmpty()) || (propertySelectedFormCollection != null && !propertySelectedFormCollection.isEmpty())) {
            bean.getService().setDescription(AlgodevUtil.entityToString(entity));
            algoContainer.getChildren().clear();
            createByConstructor(map, null, algoContainer);
            doBeanSave();
        }
        List<DevEntityClass> propEntList = getExtPropEntList(entity);  
        for (Iterator iterator = propEntList.iterator(); iterator.hasNext();) {
        	DevEntityClass propEnt = (DevEntityClass) iterator.next();
        	if(!loginBean.getAppEntityMap().containsKey(propEnt.getName())){
        		DevRequirement beanProp = new DevRequirement();
        		beanProp.getService().setName(bean.getService().getName().substring(0, bean.getService().getName().lastIndexOf(">"))+">"+propEnt.getLabel());
        		beanProp.getService().setDescription(AlgodevUtil.entityToString(propEnt));
        		beanProp.setContract(loginBean.getUser().getContract());
        		beanProp.setInterfaceType(bean.getInterfaceType());
        		beanProp.setRequirementName(propEnt.getLabel());
        		Map<String,List<UIComponent>> elementsContainerMap = new HashMap<String, List<UIComponent>>();
        		//UIComponent comp = ComponentFactory.componentClone(algoContainer,false);
        		algoContainer.getChildren().clear();
        		createByConstructor(map, null, algoContainer, elementsContainerMap, beanProp, propEnt, propEnt.getEntityPropertyDescriptorList(),  propEnt.getEntityPropertyDescriptorList());
        		doBeanSaveImpl(beanProp, propEnt, elementsContainerMap);			
        	}         			
        }
        doBeanForm();
    }
    /**
     * Geração de JSon passando a nome da classe.
     */
    public void generateClassDescriptionByClassName(){
    	try {
    		entity.getEntityPropertyDescriptorList().clear();
			Class clazz = Class.forName(entity.getCanonicalClassName());			
			Field[] fields = clazz.getDeclaredFields();
			entity.setLabel(clazz.getSimpleName());
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
					DevEntityPropertyDescriptor prop = new DevEntityPropertyDescriptor();
					prop.setPropertyLabel(field.getName());
					prop.setPropertyType(field.getType().getSimpleName().toUpperCase());
					List<DevEntityPropertyDescriptorConfig> configList = new ArrayList();
					prop.setEntityPropertyDescriptorConfigList(configList);
					entity.getEntityPropertyDescriptorList().add(prop);
				}
			}
			bean.getService().setDescription(AlgodevUtil.entityToString(entity));
		} catch (ClassNotFoundException e) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    }
    public void createByConstructorFull() {
    }
    private void createByConstructor(Map map, UIComponent cloned, UIComponent compTarget) {
    	createByConstructor(map, cloned, compTarget, elementsContainerMap, bean, entity, propertySelectedFormCollection,  propertySelectedListCollection);
    }
    private void createByConstructor(Map map, UIComponent cloned, UIComponent compTarget, Map<String,List<UIComponent>> elementsContainerMap, DevRequirement bean, DevEntityClass entity, List<DevEntityPropertyDescriptor> propertySelectedFormCollection, List<DevEntityPropertyDescriptor> propertySelectedListCollection) {
		LayoutFieldsFormat.create(bean);
		elementsContainerMap.clear();
        UIComponent elementPanel = algoPalette;
        try {
            if (map.containsKey("dataform") && !String.valueOf(map.get("dataform")).isEmpty()) {
                compTarget.getChildren().clear();
                if (cloned == null) {
                    cloned = ComponentFactory.findComponentByStyleClass("ui-algo-element-container data-form", elementPanel);
                    cloned = ComponentFactory.componentClone(cloned, false);
                    updateChildren(compTarget.getChildren(), cloned);
                    setContainerPage("form");
                }
                cloned = new ComponentFactory(cloned, elementPanel, "", "", elementsContainerMap, entity, bean, AppType.get(bean.getInterfaceType())).getComponentCreated();
                //elements.clear();
                //elements.addAll(compTarget.getChildren());
                generateElementsContainerMap(elementsContainerMap, bean);
                updateContainerPage();
                cloned = null;
            }          
            if (map.containsKey("datalist") && !String.valueOf(map.get("datalist")).isEmpty()) {
                compTarget.getChildren().clear();
                if (cloned == null) {
                    cloned = ComponentFactory.findComponentByStyleClass("ui-algo-element-container data-list", elementPanel);
                    cloned = ComponentFactory.componentClone(cloned, false);
                    updateChildren(compTarget.getChildren(), cloned);
                    setContainerPage("list");
                }
                cloned = new ComponentFactory(cloned, elementPanel, "", "", elementsContainerMap, entity, bean, AppType.get(bean.getInterfaceType())).getComponentCreated();
                //elements.clear();
                //elements.addAll(compTarget.getChildren());
                generateElementsContainerMap(elementsContainerMap, bean);
                updateContainerPage();
                cloned = null;
            }
            if (map.containsKey("datagrid") && !String.valueOf(map.get("datagrid")).isEmpty()) {
                if (cloned == null) {
                    cloned = ComponentFactory.findComponentByStyleClass("ui-algo-element-container data-list", elementPanel);
                    cloned = ComponentFactory.componentClone(cloned, false);
                    updateChildren(compTarget.getChildren(), cloned);
                    setContainerPage("list");
                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    mapParam.put("styleClass", String.valueOf(getProperty(cloned, "styleClass")).replaceAll("data-list", "data-grid"));
                    ComponentFactory.updateElementProperties(cloned, mapParam);
                }
                cloned = new ComponentFactory(cloned, elementPanel, "", "", elementsContainerMap, entity, bean, AppType.SUMM).getComponentCreated();
                //elements.clear();
                //elements.addAll(compTarget.getChildren());
                generateElementsContainerMap(elementsContainerMap, bean);
                updateContainerPage();
                cloned = null;
                //compTarget.getChildren().clear();
            }
        } catch (Throwable ex) {
            Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private long actualConstructorTabIndex;

    public long getActualConstructorTabIndex() {
        return actualConstructorTabIndex;
    }

    public void setActualConstructorTabIndex(long actualConstructorTabIndex) {
        try {
            if (this.actualConstructorTabIndex == 0 && actualConstructorTabIndex == 1) {
                if (entity != null) {
                    prepareCreateByConstructor();
                }
            }
            this.actualConstructorTabIndex = actualConstructorTabIndex;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateChildren(List<UIComponent> children, UIComponent comp) throws Throwable {
        boolean notFound = true;
        for (int i = 0; i < children.size(); i++) {
            UIComponent compAux = children.get(i);
            if (compAux.getId().equals(comp.getId())) {
                children.set(i, comp);
                notFound = false;
                break;
            }
        }
        if (notFound) {
            children.add(comp);
        }
    }

    public void updateElement() {
        try {

            Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String parameter = map.get("param_id").toString();
            Object parameter2 = map.get("style");
            //FacesContext context = FacesContext.getCurrentInstance();
            String draggedId = parameter.split(":")[parameter.split(":").length - 1];
            elementSelected = ComponentFactory.doFindComponent(getAlgoContainer(), draggedId);
            Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
            f2.setAccessible(true);
            if (elementSelected != null) {
                ComponentFactory.updateElementProperties(f2, elementSelected, map, elementPropertiesSelected);
            } else {
                String t = "";
            }

            String s = "";
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void removeComponent() throws Throwable {
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        UIComponent toRemove = null;
        if (!map.containsKey("param_id")) {
            if (elementSelected != null) {
                toRemove = elementSelected;
                elementPropertiesSelected.clear();
            }
        } else {
            String parameter = map.get("param_id").toString();
            String draggedId = parameter.split(":")[parameter.split(":").length - 1];
            toRemove = ComponentFactory.doFindComponent(getAlgoContainer(), draggedId);
        }
        if (toRemove != null) {
            if (!removeElement(toRemove)) {
                UIComponent container = toRemove.getParent();
                toRemove.getParent().getChildren().remove(toRemove);
                UIComponent compTarget = algoContainer.getParent().findComponent("algoContainer");
                if (container.getId().equals(compTarget.getId())) {
                    //removeElement(elementSelected);
                    //updateChildren(elements, cloned);
                } else {
                    UIComponent parentContainer = container.getParent();
                    UIComponent cloned = ComponentFactory.componentClone(container, true);
                    if (map.containsKey("param_id")) {
                        elementSelected = cloned;
                    }
                    //container.getChildren().remove(elementSelected);
                    if (parentContainer.getId().equals(compTarget.getId())) {
                        updateChildren(elements, cloned);
                    } else {
                        updateChildren(parentContainer.getChildren(), cloned);
                    }
                    compTarget.getChildren().clear();
                    compTarget.getChildren().addAll(elements);
                }
            }
        }
        //UIComponent cloned = ComponentFactory.cloneComp(f2, elementSelected, context);
    }

    private boolean removeElement(UIComponent toRemove) {
        Integer idxToRemove = null;
        for (int i = 0; i < elements.size(); i++) {
            UIComponent comp = elements.get(i);
            if (comp.getId().equals(toRemove.getId())) {
                idxToRemove = i;
                break;
            }
        }
        if (idxToRemove != null) {
            elements.remove(idxToRemove.intValue());
            return true;
        } else {
            return false;
        }
    }
    private TreeNode rootProp;
    private TreeNode rootFunc;
    private TreeNode selectedNode;

    public TreeNode getRootProp() {
        return rootProp;
    }

    public void setRootProp(TreeNode root) {
        this.rootProp = root;
    }

    public TreeNode getRootFunc() throws Throwable {
        rootFunc = new DefaultTreeNode("RootFunc", null);
        TreeNode node1 = new DefaultTreeNode(new String[]{"save", "#{app.doSave()}"}, rootFunc);
        TreeNode node2 = new DefaultTreeNode(new String[]{"list", "#{app.doList()}"}, rootFunc);
        TreeNode node3 = new DefaultTreeNode(new String[]{"saveAndList", "#{app.doSaveAndList()}"}, rootFunc);
        TreeNode node4 = new DefaultTreeNode(new String[]{"form", "#{app.doForm()}"}, rootFunc);
        TreeNode node5 = new DefaultTreeNode(new String[]{"edit", "#{app.doEdit(item)}"}, rootFunc);
        return rootFunc;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void createPropertyNode(DevEntityClass ent, TreeNode parentNode) throws Throwable {
        DevEntityPropertyDescriptor propertyRet = new DevEntityPropertyDescriptor();
        TreeNode node01 = null;
        for (DevEntityPropertyDescriptor property : ent.getEntityPropertyDescriptorList()) {
            if (property.getPropertyClass() != null && !getEntity().equals(property.getPropertyClass()) && rootProp.getChildCount() < 10) {
                createPropertyNode(property.getPropertyClass(), new DefaultTreeNode(new String[]{property.getPropertyName(), ((String[]) parentNode.getData())[1] + "." + property.getPropertyName(), ent.getName()}, parentNode));
            } else {
                node01 = new DefaultTreeNode(new String[]{property.getPropertyName(), ((String[]) parentNode.getData())[1] + "." + property.getPropertyName(), ent.getName()}, parentNode);
            }
        }
        //return node01;
    }

    public void updateTreeExpression() throws Throwable {
        DevEntityClass ent = getEntity();
        if (ent != null && ent.getName() != null) {
            rootProp = new DefaultTreeNode("RootProp", null);
            //#{app.$('').val}
            TreeNode node0 = new DefaultTreeNode(new String[]{ent.getName(), ent.getName(), ent.getName()}, rootProp);
            createPropertyNode(ent, node0);
        }
    }
}
