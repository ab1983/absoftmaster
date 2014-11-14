/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import com.algoboss.erp.dao.UsuarioDao;
import com.algoboss.erp.entity.AdmService;
import com.algoboss.erp.entity.DevComponentContainer;
import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyValue;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.entity.SecUser;
import com.algoboss.erp.entity.SecUserAuthorization;

import static com.algoboss.erp.face.GenericBean.sendEmail;

import com.algoboss.erp.util.AlgodevUtil;
import com.algoboss.erp.util.ComponentFactory;
import com.algoboss.erp.util.report.PDFExporter;
import com.algoboss.erp.util.report.PDFExporter2;
import com.algoboss.erp.validations.LoginValidator;
import com.algoboss.integration.small.face.LayoutFieldsFormat;
import com.algoboss.integration.small.face.SmallUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Agnaldo
 */
@Named("app")
@SessionScoped
public class AdmAlgoappBean extends GenericBean<DevEntityObject> implements Cloneable {

	@Inject
	protected GerLoginBean loginBean;
	@Inject
	protected AdmAlgodevBean algodevBean;
	@Inject
	protected SecUserBean userBean;
	@Inject
	protected BaseBean baseBean;
	private UIComponent algoContainerForm;
	private UIComponent algoContainer;
	protected UIComponent algoContainerView;
	protected UIComponent elementSelected;
	protected Map<String, Object> elementPropertiesSelected = new TreeMap<String, Object>();
	private List<UIComponent> elements = new ArrayList<UIComponent>();
	protected DevEntityClass entity;
	protected DevEntityPropertyDescriptor entityPropertyDescriptor;
	protected List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = new ArrayList<DevEntityPropertyDescriptor>();
	protected List<DevEntityClass> entityClassList = new ArrayList<DevEntityClass>();
	protected DevRequirement requirement;
	protected String containerPage = "form";
	private Map<String, List<UIComponent>> elementsContainerMap = new HashMap();
	protected Map<String, List<DevEntityObject>> beanListMap = new HashMap();
	protected Map<String, List<DevEntityObject>> beanListFilteredMap = new HashMap();
	protected Object beanSel;
	protected UIComponent dataTableSelected;
	protected DevEntityObject childBean = new DevEntityObject();
	protected DevEntityClass childEntity;
	protected DevEntityPropertyValue childValue;
	protected DevEntityPropertyValue fileValue;
	protected StreamedContent fileDownload;
	protected Date startDate;
	protected Date endDate;

	public String $date(String attr, String format, DevEntityObject entObj) {
		return entObj.$date(attr, format);
	}

	public String $date(String attr, String format) {
		return $date(attr, format, bean);
	}

	public DevEntityPropertyValue $(String attr) {
		return $(attr, bean);
	}

	public DevEntityPropertyValue $(String attr, DevEntityObject entObj) {
		return entObj.$(attr);
	}
	
	public Object $g(String attr) {
		return $(attr, bean).getStr();
	}

	public Object $g(String attr, DevEntityObject entObj) {
		return entObj.$(attr).getStr();
	}	

	public int count(String attr, DevEntityObject entObj) {
		int c = 0;
		List<DevEntityObject> list = $(attr, entObj).getPropertyChildrenList();
		if (list != null) {
			c = list.size();
		}
		return c;
	}

	public int count(String node) {
		int c = 0;
		List<DevEntityObject> list = null;
		if (node.contains(".")) {
			list = doBeanListChild(node);
		} else {
			list = doBeanList(node);
		}
		if (list != null) {
			c = list.size();
		}
		return c;
	}

	public void updateEntityObject() {
		try {
			// beanList = (List<DevEntityObject>) (Object)
			// baseDao.findAll(DevEntityObject.class, getSiteIdList(),
			// startDate, endDate);
			// beanList = (List<DevEntityObject>) (Object)
			// baseDao.findAll(DevEntityObject.class, getSiteIdList(), null,
			// null);
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoreportBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public DevEntityObject getChildBean() {
		if (childBean == null) {
			childBean = new DevEntityObject();
		}
		return childBean;
	}

	public void setChildBean(DevEntityObject childBean) {
		this.childBean = childBean;
	}

	public Map<String, List<DevEntityObject>> getBeanListMap() {
		return beanListMap;
	}

	public void setBeanListMap(Map<String, List<DevEntityObject>> beanListMap) {
		this.beanListMap = beanListMap;
	}

	public Map<String, List<DevEntityObject>> getBeanListFilteredMap() {
		return beanListFilteredMap;
	}

	public void setBeanListFilteredMap(Map<String, List<DevEntityObject>> beanListFilteredMap) {
		this.beanListFilteredMap = beanListFilteredMap;
	}

	public DevRequirement getRequirement() {
		return requirement;
	}

	public void setRequirement(DevRequirement requirement) {
		this.requirement = requirement;
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	/**
	 * Creates a new instance of TipoDespesaBean
	 */
	public AdmAlgoappBean() {
		super.url = "views/administration/algodev/algodevView.xhtml";
		super.namedFindAll = "findAllAdmRepresentative";
		super.type = DevEntityObject.class;
		super.urlForm = "algodevForm.xhtml";
		super.subtitle = "Administração | Algoapp | Aplicação";
		initBean();
	}

	@Override
	public void indexBean(Long nro) {
		try {

			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.getActualMinimum(Calendar.DATE);

			Calendar c3 = Calendar.getInstance();
			// c3.add(Calendar.DATE, -1);
			// c3.add(Calendar.MONTH, -11);
			c3.set(c3.get(Calendar.YEAR), c3.get(Calendar.MONTH), 1);

			Calendar c2 = Calendar.getInstance();
			// c2.add(Calendar.DATE, -1);
			c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE));

			startDate = c3.getTime();
			endDate = c2.getTime();
			long startTime1 = Calendar.getInstance().getTimeInMillis();
			super.indexBeanNoList(nro);
			long endTime1 = Calendar.getInstance().getTimeInMillis();
			if(requirement.getInterfaceType().startsWith("list")){
				doList();			
			}else{
				doForm();
			}
			long endTime2 = Calendar.getInstance().getTimeInMillis();
			System.out.println("Time1 Bean " + this.subtitle + ": " + (endTime1 - startTime1));
			System.out.println("Time2 Bean " + this.subtitle + ": " + (endTime2 - startTime1));

		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
		}
	}

	@Override
	public void indexBeanNewWin(Long nro) throws Throwable {
		super.indexBeanNewWin(nro); // To change body of generated methods,
		if(requirement.getInterfaceType().startsWith("list")){
			doList();			
		}else{
			doForm();
		}
	}

	@Override
	public void initBean() {
		long startTime1 = Calendar.getInstance().getTimeInMillis();
		try {
			fileValue = null;
			fileDownload = null;
			elementPropertiesSelected = new TreeMap<String, Object>();
			elements = new ArrayList();
			entity = new DevEntityClass();
			entityPropertyDescriptor = new DevEntityPropertyDescriptor();
			elementsContainerMap = new HashMap();
			if (algoContainer != null && algoContainerView != null) {
				// algoContainer = algoContainer.getClass().newInstance();
				// algoContainerView =
				// algoContainerView.getClass().newInstance();
				// algoContainer =
				// ComponentFactory.componentClone(algoContainer, false);
				// algoContainerView =
				// ComponentFactory.componentClone(algoContainerView, false);
			}
			refreshElementsContainer();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		super.initBean();
		bean = new DevEntityObject();
		bean.setEntityClass(entity);		
		long endTime2 = Calendar.getInstance().getTimeInMillis();
		// System.out.println("Time initBean " + this.subtitle + ": " +
		// (endTime2 - startTime1));
	}

	public void refreshElementsContainer() {
		if (userAuth != null && userAuth.getServiceContract() != null) {
			AdmService service = (AdmService) userAuth.getServiceContract().getService();
			generateElementsContainerMap(service);
			if (containerPage != null) {
				updateContainerPage();
			}
		}
	}

	public void generateElementsContainerMap(AdmService service) {
		try {
			if (service != null && service.getRequirement() == null) {
				service = (AdmService) doBeanRefresh(service);
			}
			if (service != null && service.getRequirement() != null) {
				requirement = (DevRequirement) doBeanRefresh(service.getRequirement());
				if (requirement != null) {
					entity = requirement.getEntityClass();
					List<DevComponentContainer> componentContainerList = requirement.getComponentContainerList();
					for (DevComponentContainer devComponentContainer : componentContainerList) {
						if(devComponentContainer.getPrototypeComponentChildrenList()!=null && !devComponentContainer.getPrototypeComponentChildrenList().isEmpty()){
							elements = AlgodevUtil.generateComponentList(devComponentContainer);
							elementsContainerMap.put(devComponentContainer.getName(), elements);							
						}
					}
				}
			}

		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public UIComponent getAlgoContainer() {
		/*
		 * if(algoContainer!=null && algoContainer.getChildren().isEmpty() &&
		 * elements != null){ algoContainer.getChildren().addAll(elements); }
		 */
		return algoContainer;
	}

	public void setAlgoContainer(UIComponent algoContainer) {

		// draggedClientId = String.valueOf(map.get("elementModelId"));
		if (!isInvalidUpdateContainer && isToUpdate(algoContainer)) {
			if (elements.isEmpty()) {
				refreshElementsContainer();
			}
			algoContainer.getChildren().clear();
			algoContainer.getChildren().addAll(elements);
			this.algoContainer = algoContainer;
		}
	}

	public void setAlgoContainer(ComponentSystemEvent algoContainerEvt) {
		UIComponent algoContainer = algoContainerEvt.getComponent();
		setAlgoContainer(algoContainer);
	}

	public UIComponent getAlgoContainerView() {
		isToUpdate(algoContainerView);
		return algoContainerView;
	}

	public void setAlgoContainerView(UIComponent algoContainerView) {
		if (isToUpdate(algoContainerView)) {
			this.algoContainerView = algoContainerView;
		}
	}

	private boolean isToUpdate(UIComponent algoContainerView) {
		boolean toUpdate = false;
		if (algoContainerView != null) {
			if (algoContainerView.getChildren().isEmpty()) {
				toUpdate = true;
			}/* else if (ComponentFactory.findComponentByStyleClass("c_" + requirement.getRequirementId(), algoContainerView) != null) {
				toUpdate = true;
			} else if (ComponentFactory.findComponentByStyleClass("c_requirement_tab", algoContainerView) == null) {
				// toUpdate = true;
				UIComponent tab = baseBean.getTabView().getChildren().get(baseBean.getContainerIndex());
				UIComponent container = ComponentFactory.findComponentByStyleClass("ui-algo-container", tab);
				if (container != null && container.getId().equals(algoContainerView.getId())) {
					Map<String, Object> mapParam = new HashMap<String, Object>();
					mapParam.put("styleClass", "ui-algo-container c_requirement_tab c_" + requirement.getRequirementId());
					// mapParam.put("styleClass", "");
					ComponentFactory.updateElementProperties(algoContainerView, mapParam);
					toUpdate = true;
				}
			}*/else{
				UIComponent tab = baseBean.getTabView().getChildren().get(baseBean.getContainerIndex());
				UIComponent container = ComponentFactory.findComponentByStyleClass("ui-algo-container", tab);
				if (container != null && container.getId().equals(algoContainerView.getId())) {
					Map<String, Object> mapParam = new HashMap<String, Object>();
					mapParam.put("styleClass", "ui-algo-container c_requirement_tab c_" + requirement.getRequirementId());
					// mapParam.put("styleClass", "");
					ComponentFactory.updateElementProperties(algoContainerView, mapParam);
					toUpdate = true;
				}				
			}
		}
		return toUpdate;
	}

	public void updateContainerPage() {
		fileValue = null;
		fileDownload = null;
		elements = elementsContainerMap.get(containerPage);
		setAlgoContainer(algoContainerView);
	}

	public String getContainerPage() {
		return containerPage;
	}

	public UIComponent getDataTableSelected() {
		return dataTableSelected;
	}

	public UIComponent getAlgoContainerForm() {
		return algoContainerForm;
	}

	public void setAlgoContainerForm(UIComponent algoContainerForm) {
		/*
		 * if(algoContainerForm!=null && algoContainerView!=null){ UIComponent
		 * compTab = algoContainerForm.getParent(); UIComponent comp1 = null;
		 * List<UIComponent> compList1 = compTab.getChildren(); while ((comp1 =
		 * ComponentFactory.findComponentByStyleClass("ui-algo-container-form",
		 * compTab))!=null) { compList1.remove(comp1); }
		 * compList1.add(algoContainerForm); UIComponent comp = null;
		 * List<UIComponent> compList = algoContainerForm.getChildren(); while
		 * ((comp =
		 * ComponentFactory.findComponentByStyleClass("ui-algo-container",
		 * algoContainerForm))!=null) { compList.remove(comp); }
		 * compList.add(algoContainerView); }
		 */
		this.algoContainerForm = algoContainerForm;
	}

	public void setDataTableSelectedId(UIComponent dataTableSelected) {
		this.dataTableSelected = dataTableSelected;
	}

	@Override
	public void doBeanList() {
		beanList = findListByClass(entity);
		bean = new DevEntityObject();
		bean.setEntityClass(entity);	

		// super.doBeanList(); //To change body of generated methods, choose
		// Tools | Templates.
	}

	public void doList() {
		doList("list", "");
	}

	public void doList(String container, String node) {
		containerPage = container;
		updateContainerPage();
		if (!container.equals("list")) {
			doBeanListChild(node);
		} else {
			doBeanList();
		}
	}

	@Override
	public void doBeanForm() {
		super.doBeanForm(); // To change body of generated methods, choose Tools
							// | Templates.
	}

	public void doForm() {	
		doForm("form");
	}

	public void doForm(String container) {
		containerPage = container;
		updateContainerPage();
		if (!container.equals("form")) {
			// String node =
			// container.split("-")[container.split("-").length-1];
			// childBean = new DevEntityObject();
		} else {
			doBeanForm();
		}
	}

	public void doRemove(DevEntityObject obj) {
		bean = obj;
		doBeanRemove();		
	}

	public void doRemoveChild(DevEntityObject obj) {
		childBean = obj;
		if (childBean != null) {
			try {
				// super.doBeanSave(childBean);
				if (childValue.getPropertyChildrenList().contains(childBean)) {
					childValue.getPropertyChildrenList().remove(childBean);
				}
				childBean = new DevEntityObject();
				childBean.setEntityClass(childEntity);
			} catch (Exception ex) {
				Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
			}
		}
		// bean = obj;
		// doBeanRemove();
	}

	public void doEdit(DevEntityObject obj) {
		bean = (DevEntityObject)doBeanRefresh(obj);
		doForm();
	}

	public void doCopy(DevEntityObject obj) {
		bean = new DevEntityObject(obj);
		doForm();
	}

	public void doCopy() {
		if (bean != null) {
			bean = new DevEntityObject(bean);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Copiado com Sucesso! Faça as alterações se necessário e salve.", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void doCopyChild() {
		if (childBean != null) {
			childBean = new DevEntityObject(childBean);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Copiado com Sucesso! Faça as alterações se necessário e salve.", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void doEditChild(DevEntityObject obj, String container) {
		childBean = obj;
		doForm(container);
	}

	public void setBeanSel(Object obj) {
		doEdit((DevEntityObject) obj);
		super.setBean((DevEntityObject) obj); // To change body of generated
												// methods, choose Tools |
												// Templates.
	}

	public Object getBeanSel() {
		return beanSel;
	}

	public void doSave() throws Throwable {
		doSave(false);
	}

	public void doSaveChild() {
		if (childBean != null) {
			try {
				// super.doBeanSave(childBean);
				if (!childValue.getPropertyChildrenList().contains(childBean)) {
					childValue.getPropertyChildrenList().add(childBean);
				}
				childBean.prePersist();
				childBean = new DevEntityObject();
				childBean.setEntityClass(childEntity);
			} catch (Exception ex) {
				Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
			}
		}
	}

	public void doSaveAndListChild(String container, String node) {
		doSaveChild();
		doList(container, node);
	}

	public void doSaveAndList() {
		doSave(true);
	}

	public void doSave(boolean hasList) {
		try {
			if (bean != null) {
				super.doBeanSaveAndList(true, true, hasList, bean);					
				bean.setEntityClass(entity);
			}
			if (hasList) {
				doList();
			}
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void doSaveItem(DevEntityObject obj) {
		doBeanSaveAndList(true, false, false, obj);
	}

	public Map beanMap(String key) {
		return beanMap(key, "");
	}
	public Map beanMap(String className, String keysStr) {
		return beanMap(className, keysStr, "");
	}
	public Map beanMap(String className, String keysStr, String valsStr) {
		String[] keys = keysStr.split(";");
		String[] vals = valsStr.split(";");
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<DevEntityObject> list = doBeanList(className);
		try {
			if (list != null) {
				for (DevEntityObject devEntityObject : list) {
					Object keyMap = devEntityObject;
					Object valMap = devEntityObject;
					String valStr = "";
					for (int i = 0; i < vals.length && !valsStr.isEmpty(); i++) {
						String val = vals[i];
						DevEntityPropertyValue value = $(className + "." + val, devEntityObject);
						if (i > 0) {
							valStr += " - ";
						}
						valStr += String.valueOf(value.getPropertyValue());
					}
					String keyStr = "";
					for (int i = 0; i < keys.length && !keysStr.isEmpty(); i++) {
						String key = keys[i];
						DevEntityPropertyValue value = $(className + "." + key, devEntityObject);
						if (i > 0) {
							keyStr += " - ";
						}
						keyStr += String.valueOf(value.getPropertyValue());
					}					
					if (!keyStr.isEmpty()) {
						keyMap = keyStr;
					}
					if (!valStr.isEmpty()) {
						valMap = valStr;
					}					
					map.put(keyMap, valMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public List<DevEntityObject> doBeanList(String className) {
		List<DevEntityObject> entityObjectList = null;
		if (className != null && !className.isEmpty()) {
			if (beanListMap.containsKey(className) && !className.contains(".")) {
				entityObjectList = beanListMap.get(className);
			} else {
				entityObjectList = findListByClass((entity.getName()!=null && entity.getName().equals(className))?entity:baseDao.findEntityClass(className));
				beanListMap.put(className, entityObjectList);
				beanListFilteredMap.put(className, entityObjectList);
				formRendered = false;
			}
		}
		return entityObjectList; // To change body of generated methods, choose
									// Tools | Templates.
	}

	public List<DevEntityObject> doBeanListChild(String node) {
		List<DevEntityObject> childrenList = null;
		if (node != null && !node.isEmpty() && node.contains(".")) {
			childValue = $(node.toLowerCase());
			if (childValue != null && childValue.getEntityPropertyDescriptor() != null) {
				//baseDao.entityObjectSyncList(className, siteIdList, objectList, objectListSmallRest, Class.forName(childEntity.getCanonicalClassName()));
				childrenList = childValue.getPropertyChildrenList();
				if(childrenList == null){
					baseDao.entityObjectSyncPopulate(bean);	
					childValue = $(node.toLowerCase());
					childrenList = childValue.getPropertyChildrenList();
					if(childrenList == null){
						childrenList = new ArrayList();						
						childValue.setPropertyChildrenList(childrenList);
					}
				}
				childEntity = childValue.getEntityPropertyDescriptor().getPropertyClass();
				beanListMap.put(node.toLowerCase(), childrenList);
				beanListFilteredMap.put(node.toLowerCase(), childrenList);
				childBean = new DevEntityObject();
				childBean.setEntityClass(childEntity);
				for (DevEntityPropertyDescriptor propDesc : childEntity.getEntityPropertyDescriptorList()) {
					if (propDesc.getPropertyClass() != null && propDesc.getPropertyClass().equals(entity)) {
						try {
							$(childEntity.getName() + "." + propDesc.getPropertyName(), childBean).setVal(bean);
						} catch (Exception ex) {
							Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				}
			}
		}
		return childrenList;
	}

	public List<DevEntityObject> findListByClass(DevEntityClass className) {
		List<DevEntityObject> entityObjectList = null;
		if (className != null) {
			String msgStr = "";
			try {
				if (className.equals("sec_user")) {
					userBean.doBeanList();
					List<SecUser> userList = userBean.getBeanList();
					DevEntityClass entClass = new DevEntityClass();
					entClass.setEntityClassId(1L);

					entityObjectList = new ArrayList<DevEntityObject>();
					for (SecUser secUser : userList) {
						DevEntityObject entObj = new DevEntityObject();
						entObj.setEntityClass(entClass);
						entObj.setEntityObjectId(Double.valueOf(Math.pow(secUser.getUserId(), 5)).longValue());
						DevEntityPropertyValue propVal = new DevEntityPropertyValue();
						propVal.setUser(secUser);
						DevEntityPropertyDescriptor propDesc = new DevEntityPropertyDescriptor();
						propDesc.setPropertyIdentifier(true);
						propVal.setEntityPropertyDescriptor(propDesc);
						entObj.getEntityPropertyValueList().add(propVal);
						entityObjectList.add(entObj);
					}
				} else {
					if (className.getName().contains(".")) {
						DevEntityPropertyValue childValueProp = $(className.getName());
						if (childValueProp != null && childValueProp.getEntityPropertyDescriptor() != null) {
							entityObjectList = childValueProp.getPropertyChildrenList();
						}
					} else {
						List<String> cols = new ArrayList<String>();
						if(entity.getName()!=null && entity.getName().equals(className.getName())){
							cols = LayoutFieldsFormat.getVisibleFieldsNames(requirement, containerPage, entity);							
						}
						entityObjectList = baseDao.findEntityObjectByClass(className,cols, getSiteIdList(), null, null);													
					}
				}
				// entityObjectList = baseDao.findEntityObjectByClass(className,
				// getSiteIdList(), startDate, endDate);
				// bean = new DevEntityObject();
				beanListMap.put(className.getName(), entityObjectList);
				beanListFilteredMap.put(className.getName(), entityObjectList);
				formRendered = false;
				// return "/f/cadastro/usuario/usuarioList.xhtml";
			} catch (Throwable ex) {
				msgStr = "Falha no processamento. Motivo: " + ex.getClass();
				ex.printStackTrace();
			} finally {
				if (!msgStr.isEmpty()) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgStr, "");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		}
		return entityObjectList; // To change body of generated methods, choose
									// Tools | Templates.
	}

	public void prepareUpload(Object obj) {
		try {
			if (obj != null) {
				fileValue = (DevEntityPropertyValue) obj;
				setFilePreview(null);
				RequestContext requestContext = RequestContext.getCurrentInstance();
				requestContext.addCallbackParam("isValid", true);
			}
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void handleFileUpload() throws Exception {

	}

	public void handleFileUpload(FileUploadEvent event) throws Exception {
		UploadedFile fileAux = event.getFile();
		if(fileAux!=null && fileValue!=null){
			fileValue.setVal(fileAux.getFileName());
			fileValue.setPropertyFile(fileAux.getContents());
		}
	}

	public void exportDataTable() {
		try {
			Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String source = String.valueOf(map.get("javax.faces.source"));
			String draggedId = source.split(":")[source.split(":").length - 1];
			UIComponent comp3 = null;
			if (algoContainer != null && !draggedId.isEmpty()) {
				comp3 = ComponentFactory.doFindComponent(algoContainer.getParent().getParent(), draggedId);
			}
			if (comp3 != null) {
				UIComponent parent = comp3.getParent();
				dataTableSelected = parent;
			}
			String x = "";
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void exportHtmlToPdf() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().setRequestCharacterEncoding("utf-8");
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestCharacterEncoding());
			Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String html = String.valueOf(map.get("html"));
			String css = String.valueOf(map.get("css"));
			System.out.println(html);
			PDFExporter2 exp = new PDFExporter2();
			// exp.export(FacesContext.getCurrentInstance(), ((DataTable)
			// dataTableSelected), requirement.getRequirementName()+"_Report");
			exp.export(FacesContext.getCurrentInstance(), css, html, requirement.getRequirementName() + "_Report");
		} catch (IOException ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void exportPdf() throws IOException {
		try {
			PDFExporter exp = new PDFExporter();
			// exp.export(FacesContext.getCurrentInstance(), ((DataTable)
			// dataTableSelected), requirement.getRequirementName()+"_Report");
			exp.export(FacesContext.getCurrentInstance(), ((DataTable) dataTableSelected), requirement.getRequirementName() + "_Report", "iso-8859-1");
			// ContentCaptureServletResponse capContent = new
			// ContentCaptureServletResponse(dataTableSelected);

		} catch (Exception e) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	protected UIComponent filePreview;
	protected int imgRandon;
	private Map<String, Object> methodMap;

	public int getImgRandon() {
		imgRandon = Double.valueOf(Math.random() * 10000).intValue();
		return imgRandon;
	}

	public void setImgRandon(int imgRandon) {
		this.imgRandon = imgRandon;
	}

	public StreamedContent getFileDownload(int rand) {
		return fileDownload;
	}

	public UIComponent getFilePreview() {
		return filePreview;
	}
	public void setFilePreviewEvt(ComponentSystemEvent filePreviewEvent) {
		if (filePreviewEvent != null) {
			filePreview = filePreviewEvent.getComponent();
			setFilePreview(filePreview);
		}
	}
	public void setFilePreview(UIComponent filePreview) {
		if (filePreview != null) {
			String action = "#{app.getFileDownload(" + getImgRandon() + ")}";
			ValueExpression value = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(), action, Object.class);
			filePreview.setValueExpression("value", value);
			this.filePreview = filePreview;
		}
		if (fileValue != null && fileValue.getPropertyValue() != null) {
			try {
				setFileDownload(prepareStreamedContent(fileValue));
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Carregar Arquivo!", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public StreamedContent prepareStreamedContent(DevEntityPropertyValue fileValue) {
		if (fileValue != null && fileValue.getPropertyFile() != null) {
			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(fileValue.getPropertyFile());
				String extension = String.valueOf(fileValue.getPropertyValue()).substring(String.valueOf(fileValue.getPropertyValue()).lastIndexOf("."));
				String contentType = "";
				if (extension.contains(".jpg")) {
					contentType = "image/jpg";
				} else if (extension.contains(".gif")) {
					contentType = "image/gif";
				} else if (extension.contains(".jpeg")) {
					contentType = "image/jpeg";
				} else if (extension.contains(".png")) {
					contentType = "image/png";
				} else if (extension.contains(".bmp")) {
					contentType = "image/bmp";
				} else if (extension.contains(".pdf")) {
					contentType = "application/pdf";
				} else {
					contentType = "image/jpg";
				}
				StreamedContent sc = new DefaultStreamedContent(bis, contentType, String.valueOf(fileValue.getPropertyValue()));
				return sc;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public String getFile(DevEntityPropertyValue fileValue) {
		String urlFileMap = "";
		try {
			if (fileValue != null) {
				StreamedContent sc = prepareStreamedContent(fileValue);
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				// HttpSession session = (HttpSession)
				// context.getSession(false);
				Map<String, HashMap<String, Object>> fileDataBaseMap = loginBean.getFileDataBaseMap();
				// if (session.getAttribute("fileDataBaseMap") != null) {
				// fileDataBaseMap = (HashMap<String, HashMap<String, Object>>)
				// session.getAttribute("fileDataBaseMap");
				// } else {
				// fileDataBaseMap = new HashMap<String, HashMap<String,
				// Object>>();
				// session.setAttribute("fileDataBaseMap", fileDataBaseMap);
				// }
				HashMap<String, Object> fileDataBasePropMap = new HashMap<String, Object>();
				fileDataBasePropMap.put("fileName", sc.getName());
				fileDataBasePropMap.put("content", sc.getStream());
				UUID uuid = UUID.randomUUID();
				String strUuid = uuid.toString();
				fileDataBaseMap.put(strUuid, fileDataBasePropMap);
				urlFileMap = context.getRequestServletPath() + "/file?uuid=" + strUuid;
				// getFile(strUuid);
			}
		} catch (Exception ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		return urlFileMap;
	}

	protected void getFileX(String id) {
		FacesContext context = loginBean.getFacesContext();
		ExternalContext externalContext = context.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		Map<String, HashMap<String, Object>> fileDataBaseMap = null;
		if (session.getAttribute("fileDataBaseMap") != null) {

			try {
				fileDataBaseMap = (HashMap<String, HashMap<String, Object>>) session.getAttribute("fileDataBaseMap");
				HashMap<String, Object> propMap = fileDataBaseMap.get(id);
				InputStream inputStream = (InputStream) propMap.get("content");
				String fileName = String.valueOf(propMap.get("fileName"));
				// externalContext.responseReset();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				baos.write(inputStream.toString().getBytes());
				// ByteArrayOutputStream baos2 = new
				// ByteArrayOutputStream(1000000);
				OutputStreamWriter writer = new OutputStreamWriter(baos, "iso-8859-1");
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, null, "iso-8859-1"));
				context.setResponseStream(context.getRenderKit().createResponseStream(baos));
				// response.reset();
				response.setStatus(200);
				response.setContentType(externalContext.getMimeType(fileName));
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "Mon, 8 Aug 1980 10:00:00 GMT");
				response.setContentLength(baos.size());
				response.getOutputStream().write(baos.toByteArray(), 0, baos.size());
				FacesContext.getCurrentInstance().responseComplete();
				// PrintWriter writer = response.getWriter();
				// JspFactory.getDefaultFactory().getPageContext(null, null,
				// response, "", true, 1, false);
				// OutputStream out = externalContext.getResponseOutputStream();
				// externalContext.setResponseStatus(200);
				// externalContext.setResponseContentType(externalContext.getMimeType(fileName));

				// externalContext.setResponseHeader("Cache-Control",
				// "no-cache, no-store, must-revalidate");
				// externalContext.setResponseHeader("Pragma", "no-cache");
				// externalContext.setResponseHeader("Expires",
				// "Mon, 8 Aug 1980 10:00:00 GMT");
				// baos.writeTo(out);
				// externalContext.getResponseOutputStream().write(baos.toByteArray(),
				// 0, baos.size());
				byte[] buffer = new byte[8192];

				int length;
				// externalContext.getResponse()ResponseOutputStream().close();
				while ((length = (inputStream.read(buffer))) >= 0) {
					// externalContext.getResponseOutputStream().write(buffer,
					// 0, length);
				}
				externalContext.responseFlushBuffer();
				context.responseComplete();
			} catch (Exception ex) {
				ex.printStackTrace();
				// Logger.getLogger(GerAuthFilter.class.getName()).log(Level.SEVERE,
				// null, ex);
			}
		} else {
			System.out.println("Não encontrou fileDataBaseMap na sessão.");
		}
	}

	public class ContentCaptureServletResponse extends HttpServletResponseWrapper {

		protected ByteArrayOutputStream contentBuffer;
		protected PrintWriter writer;

		public ContentCaptureServletResponse(HttpServletResponse resp) {
			super(resp);
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if (writer == null) {
				contentBuffer = new ByteArrayOutputStream();
				writer = new PrintWriter(contentBuffer);
			}
			return writer;
		}

		public String getContent() {
			writer.flush();
			String xhtmlContent = new String(contentBuffer.toByteArray());
			xhtmlContent = xhtmlContent.replaceAll("<thead>|</thead>|" + "<tbody>|</tbody>", "");
			return xhtmlContent;
		}
	}

	public LoginValidator loginValidator() {
		return new LoginValidator();
	}

	public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		SecUser user = new UsuarioDao(baseDao).findByEmailAndPassword(loginBean.getUser().getEmail().toUpperCase(), String.valueOf(value));
		boolean isValidate = false;
		if (user != null) {
			if (!user.isInactive()) {
				isValidate = true;
			}
		}
		if (!isValidate) {

			FacesMessage msg = new FacesMessage(BaseBean.getBundle("incorrectPassword", "msg"), "");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}

	public void sendEmail() {
		try {
			Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String dest = String.valueOf(map.get("email_destin"));
			String name = String.valueOf(map.get("name_destin"));
			String msg = String.valueOf(map.get("email_msg"));
			String title = String.valueOf(map.get("email_title"));
			List<String[]> destin = new ArrayList<String[]>();
			destin.add(new String[] { dest, name });
			sendEmail(destin, "atendimento@algoboss.com", "AlgoBoss", title, msg, null);
			FacesMessage msgRet = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua mensagem foi enviada com sucesso.", "");
			FacesContext.getCurrentInstance().addMessage(null, msgRet);
		} catch (Exception ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void randomSort() {
		try {
			Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String amountField = String.valueOf(map.get("amount_field"));
			String listField = String.valueOf(map.get("list_field"));
			String targetField = String.valueOf(map.get("target_field"));

			List<DevEntityObject> childrenList = null;
			if (listField != null && !listField.isEmpty() && listField.contains(".")) {
				DevEntityPropertyValue childValue2 = $(listField.toLowerCase());
				if (childValue2 != null && childValue2.getEntityPropertyDescriptor() != null) {
					childrenList = childValue2.getPropertyChildrenList();
				}
			}
			List<DevEntityObject> entToSort = new ArrayList<DevEntityObject>();
			for (int i = 0; i < childrenList.size(); i++) {
				DevEntityObject devEntityObject = childrenList.get(i);
				DevEntityPropertyValue childValue2 = $(amountField.toLowerCase(), devEntityObject);
				Integer amountSort = Integer.valueOf(String.valueOf(childValue2.getPropertyValue()));
				for (int j = 0; j < amountSort; j++) {
					entToSort.add(devEntityObject);
				}
			}
			Random rand = new Random();
			Integer sortInt = rand.nextInt(entToSort.size());
			DevEntityObject devEntityObjectSort = entToSort.get(sortInt);
			DevEntityPropertyValue childValueTarget = $(targetField.toLowerCase());
			if (childValueTarget != null) {
				childValueTarget.setPropertyObject(devEntityObjectSort);
				// String title = String.valueOf(map.get("email_title"));
				// List<String[]> destin = new ArrayList<String[]>();
				// destin.add(new String[]{dest, name});
				// sendEmail(destin, "atendimento@algoboss.com", "AlgoBoss",
				// title, msg, null);
				FacesMessage msgRet = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorteio realizado com sucesso.", "");
				FacesContext.getCurrentInstance().addMessage(null, msgRet);
			}
		} catch (Exception ex) {
			Logger.getLogger(AdmAlgoappBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void resetForm(String id) {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			context.reset(id);
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void eventBean(){
		Map callMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();	
		AlgodevUtil.event(callMap, this);	
		//String listField = String.valueOf(map.get("list_field"));
		//String targetField = String.valueOf(map.get("target_field"));
	}
	protected boolean isInvalidUpdateContainer = false;

	public PhaseListener getPhaseListenerImpl() {
		return new PhaseListener() {
			@Override
			public void beforePhase(PhaseEvent event) {
				if (event.getPhaseId().equals(PhaseId.PROCESS_VALIDATIONS)) {
					isInvalidUpdateContainer = true;
				}
				// System.out.println("Before Executing " + event.getPhaseId() +
				// isInvalidUpdateContainer);
			}

			@Override
			public void afterPhase(PhaseEvent event) {
				if (event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES) || event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
					isInvalidUpdateContainer = false;
				}
				// System.out.println("After Executing " + event.getPhaseId() +
				// isInvalidUpdateContainer);
			}

			@Override
			public PhaseId getPhaseId() {
				return PhaseId.ANY_PHASE;
			}
		};

	}
}
