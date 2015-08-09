/*
 * and open the template in the editor.
 */
package com.algoboss.app.face;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.algoboss.app.entity.DevComponentContainer;
import com.algoboss.app.entity.DevEntityClass;
import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.entity.DevEntityPropertyDescriptor;
import com.algoboss.app.entity.DevEntityPropertyValue;
import com.algoboss.app.entity.DevRequirement;
import com.algoboss.app.util.AlgodevUtil;
import com.algoboss.app.util.ComponentFactory;
import com.algoboss.core.dao.UsuarioDao;
import com.algoboss.core.entity.AdmService;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.face.BaseBean;
import com.algoboss.core.face.GenericBean;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.core.face.SecUserBean;
import com.algoboss.core.validations.LoginValidator;
import com.algoboss.erp.util.report.PDFExporter;
import com.algoboss.erp.util.report.PDFExporter2;

/**
 *
 * @author Agnaldo
 */
abstract class AdmAlgoBean<T> extends GenericBean<T> implements Cloneable{

	@Inject
	protected GerLoginBean loginBean;
	@Inject
	protected AdmAlgodevBean algodevBean;
	@Inject
	protected SecUserBean userBean;
	@Inject
	protected BaseBean baseBean;
	SessionUtilBean appBean;
	private transient UIComponent algoContainerForm;
	private transient UIComponent algoContainer;
	private transient UIComponent algoContainerView;
	protected UIComponent elementSelected;
	protected Map<String, Object> elementPropertiesSelected = new TreeMap<String, Object>();
	protected DevEntityClass entity;
	protected DevEntityPropertyDescriptor entityPropertyDescriptor;
	protected List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = new ArrayList<DevEntityPropertyDescriptor>();
	protected List<DevEntityClass> entityClassList = new ArrayList<DevEntityClass>();
	protected DevRequirement requirement;
	protected String containerPage = "form";
	protected Object beanSel;
	protected UIComponent dataTableSelected;
	protected DevEntityObject childBean = new DevEntityObject();
	protected DevEntityClass childEntity;
	protected DevEntityPropertyValue childValue;
	protected DevEntityPropertyValue fileValue;
	protected StreamedContent fileDownload;
	protected Date startDate;
	protected Date endDate;
	protected Set<String> beanMapBrowserCached = new TreeSet<String>(); 
	protected String idxBrowserCached = "";
	transient ScriptEngineManager manager = new ScriptEngineManager();
	transient ScriptEngine jsEngine = manager.getEngineByExtension("js");
	protected String PREFIX_BEAN = "";
	protected Map<String, List<UIComponent>> elementsContainerMap = new HashMap();
	
	public abstract void onConstruct();
	public abstract void onDestroy();
	
	
	public DevRequirement getRequirement() {
		return requirement;
	}

	public void setRequirement(DevRequirement requirement) {
		this.requirement = requirement;
	}
		
	public SessionUtilBean getAppBean() {
		return appBean;
	}
	
	public void setAppBean(String id){
		if(id.startsWith(PREFIX_BEAN)){
			if(!baseBean.getAppBeanMap().containsKey(id)){			
				baseBean.getAppBeanMap().put(id, new SessionUtilBean());
			}
			appBean = baseBean.getAppBeanMap().get(id);
		}
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	public String getIdxBrowserCached() {
		return idxBrowserCached;
	}

	public void setIdxBrowserCached(String idxBrowserCached) {
		this.idxBrowserCached = idxBrowserCached;
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
				generateElementsContainerMap(requirement);
				getAppBean().setRequirement(requirement);
			}
		} catch (Throwable ex) {
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void generateElementsContainerMap(DevRequirement requirement) {
		if (requirement != null) {			
			getAppBean().getElementsContainerMap(requirement).clear();
			entity = requirement.getEntityClass();
			getAppBean().setEntity(entity);
			List<DevComponentContainer> componentContainerList = requirement.getComponentContainerList();
			for (DevComponentContainer devComponentContainer : componentContainerList) {
				if(devComponentContainer.getPrototypeComponentChildrenList()!=null && !devComponentContainer.getPrototypeComponentChildrenList().isEmpty()){
					//Application app = FacesContext.getCurrentInstance().getApplication();

					//UIComponent panel = app.createComponent(HtmlPanelGroup.COMPONENT_TYPE);
					List<UIComponent> elements;
					elements = AlgodevUtil.generateComponentList(devComponentContainer);
					
					UIComponent panel = new HtmlPanelGroup();
					FacesContext facesContext = FacesContext.getCurrentInstance();
					ValueExpression val = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf("#{'"+devComponentContainer.getName()+"'== app.containerPage}"), Boolean.class);					
					panel.setValueExpression("rendered",val);
					//panel.setTransient(true);
					panel.getChildren().addAll(elements);
					elements = Collections.singletonList(panel);
					
					getAppBean().getElementsContainerMap(requirement).put(devComponentContainer.getName(), elements);	
				}
			}
		}		
	}
	public void initBrowserCache(){
		beanMapBrowserCached = new TreeSet<String>();
	}
	public UIComponent getAlgoContainer() {
		/*
		 * if(algoContainer!=null && algoContainer.getChildren().isEmpty() &&
		 * elements != null){ algoContainer.getChildren().addAll(elements); }
		 */
		UIComponent algoContainerX = ComponentFactory.findComponentByStyleClass("ui-algo-container", FacesContext.getCurrentInstance().getViewRoot());
		return algoContainerX;
	}
	public void setAlgoContainer() {		
		UIComponent algoContainer = ComponentFactory.findComponentByStyleClass("ui-algo-container", FacesContext.getCurrentInstance().getViewRoot());
		if(algoContainer!=null){
			this.algoContainer = algoContainer;
			this.algoContainerView = algoContainer;					
			if (getAppBean().getElementsContainerMap(requirement).isEmpty()) {
				refreshElementsContainer();
			}else{
				updateContainerPage();				
			}
			//refresh();
		}		
	}
	public void setAlgoContainer(UIComponent algoContainer) {
		String x = "";
	}
	public void setAlgoContainerx(UIComponent algoContainer) {

		// draggedClientId = String.valueOf(map.get("elementModelId"));
	try {
/*

		if (!isInvalidUpdateContainer && isToUpdate(algoContainer)) {
			if (getAppBean().getElements().isEmpty()) {
				refreshElementsContainer();
			}
			if(algoContainer!=null && algoContainer.getChildren().isEmpty()){
				this.algoContainer = algoContainer;
				this.algoContainerView = algoContainer;					
				updateContainerPage();
			}
				if(!algoContainer.getChildren().containsAll(getAppBean().getElements())){
				
				if(this.algoContainer != null && !algoContainer.equals(this.algoContainer)){
					//algoContainer.setInView(false);
					//this.algoContainer.markInitialState();
				}
				
				
				
				
						
				
				//algoContainer.getChildren().clear();
				ArrayList<ComponentStruct> actions = (ArrayList<ComponentStruct>)StateContext.getStateContext(FacesContext.getCurrentInstance()).getDynamicActions();
				//componentMap.clear();
				HashMap<String, UIComponent> componentMap = StateContext.getStateContext(FacesContext.getCurrentInstance()).getDynamicComponents();
				//StateContext.release(FacesContext.getCurrentInstance());
				if (actions != null && false) {
					            List<Object> toRemoveActions = new ArrayList<Object>();
					            for (ComponentStruct action : actions) {
					            	UIComponent elFound = null;
					                for (UIComponent el : getAppBean().getElements()) {
					                	try {
					                		elFound = ComponentFactory.doFindComponent(el, action.id);
					                		if (el.getId().equals(action.id) ||  elFound != null) {
					                			elFound = el;
					                			break;
					                		}
			
										} catch (Throwable e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
					                }
					           		if (elFound != null) {
			                			toRemoveActions.add(action);
			                		}
					            }
					            actions.removeAll(toRemoveActions);
				}
				
				
				//elements = algoContainer.getChildren();

				//algoContainer.setInView(true);
			}
		}*/
	} catch (Exception e) {
		e.printStackTrace();
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
	
	public void setAlgoContainerViewWidget(UIComponent algoContainerView) {
		this.algoContainerView = null; 
		this.algoContainer = algoContainerView;
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
				UIComponent tab = baseBean.getTabView()!=null?baseBean.getTabView().getChildren().get(baseBean.getContainerIndex()):null;
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

	public String getBeanKeyName(){
		return PREFIX_BEAN+getRequirement().getRequirementId();
	}
	
	public Map<String, List<UIComponent>> getElementsContainerMap(){
		return getAppBean().getElementsContainerMap(getRequirement());
	}
	
	public void updateContainerPage() {
		//idxBrowserCached = "";
		fileValue = null;
		fileDownload = null;
		//getAppBean().setElements(getAppBean().getElementsContainerMap().get(containerPage));
		boolean start = false;
		UIComponent algoContainerView = getAlgoContainer();
		if(algoContainerView!=null && getRequirement()!=null && getElementsContainerMap()!=null){
			if(!algoContainerView.getId().equals(PREFIX_BEAN+getRequirement().getRequirementId())){
				algoContainerView.getChildren().clear();
			}
			if(algoContainerView.getChildren().isEmpty()){
				algoContainerView.setId(PREFIX_BEAN+getRequirement().getRequirementId());
				setAppBean(getBeanKeyName());
				start = true;
				//includePhaseListener();
			}
				
			
			//algoContainerView.getChildren().clear();
			if(start){
			for(Map.Entry<String, List<UIComponent>> object : getElementsContainerMap().entrySet()){
				//for (UIComponent comp : object.getValue()) {
					////comp.setRendered(false);
				//}
					algoContainerView.getChildren().addAll(object.getValue());
				}
			//FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(algoContainerView.getId());
			FacesContext.getCurrentInstance().getPartialViewContext().getExecuteIds().add(algoContainerView.getId());
			}				
			
			//for (UIComponent comp : sessionUtilBean.getElements()) {
				/////comp.setRendered(true);
				//FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(comp.getId());
			//}
			
			
			//FaceletFactory ff = (FaceletFactory) RequestStateManager.get(FacesContext.getCurrentInstance(), RequestStateManager.FACELET_FACTORY);
			// construct component sub-tree with given parent
			//try {
				//Facelet ft = ff.getFacelet("/index.xhtml");
				
				////ft.apply(FacesContext.getCurrentInstance(), algoContainerView);
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			//FacesContext.getCurrentInstance().getPartialViewContext().getExecuteIds().add(algoContainerView.getId());
		}
		
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
	public void doBeanForm() {
		super.doBeanForm(); // To change body of generated methods, choose Tools
							// | Templates.
	}

	public void doForm() {	
		doForm("form");
	}

	public void doForm(String container) {
		containerPage = container;
		getAppBean().setContainerPage(containerPage);	
		updateContainerPage();
		if (!container.equals("form")) {
			// String node =
			// container.split("-")[container.split("-").length-1];
			// childBean = new DevEntityObject();
		} else {
			doBeanForm();
		}
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
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, e);
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
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(AdmAlgoBean.class.getName()).log(Level.SEVERE, null, ex);
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
	protected boolean isInvalidUpdateContainer = false;
	
	class AppPhaseListener implements PhaseListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 540211169676512128L;

		@Override
		public void beforePhase(PhaseEvent event) {
			try {
				
			if (event.getPhaseId().equals(PhaseId.PROCESS_VALIDATIONS)) {
				isInvalidUpdateContainer = true;
			}
			UIComponent algoContainerX = ComponentFactory.findComponentByStyleClass("ui-algo-container", FacesContext.getCurrentInstance().getViewRoot());
			if(algoContainerX!=null){
				algoContainer = algoContainerX;
				algoContainerView = algoContainerX;	
			}
			if(algoContainerView!=null){
				setAlgoContainer();					
				if(event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES)){
					ComponentFactory.resetComponent();
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Before Executing " + event.getPhaseId() );
		}

		@Override
		public void afterPhase(PhaseEvent event) {
			try {
				
			if (event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES) || event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
				isInvalidUpdateContainer = false;
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("After Executing " + event.getPhaseId());
		}

		@Override
		public PhaseId getPhaseId() {
			return PhaseId.ANY_PHASE;
		}
		
	}
	public PhaseListener getPhaseListener(){
		return new AppPhaseListener();
	}
	public void includePhaseListener() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx!=null){
			boolean exist = false;
			List<PhaseListener> phaseListeners = ctx.getViewRoot().getPhaseListeners();
			for (int i = 0; i < phaseListeners.size(); i++) {
				PhaseListener element = phaseListeners.get(i);
				if(element instanceof AdmAlgoBean.AppPhaseListener){
					exist = true;
					break;
				}
			}
			if(!exist){
				ctx.getViewRoot().addPhaseListener(new AppPhaseListener());					
			}
		}	
	}
	
	public void refresh() {
		  FacesContext context = FacesContext.getCurrentInstance();
		  Application application = context.getApplication();
		  ViewHandler viewHandler = application.getViewHandler();
		  UIViewRoot viewRoot = viewHandler.createView(context, context
		   .getViewRoot().getViewId());
		  context.setViewRoot(viewRoot);
		  //context.renderResponse(); //Optional
		}	
}
