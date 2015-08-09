/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.algoboss.app.face.SessionUtilBean;
import com.algoboss.app.util.ComponentFactory;
import com.algoboss.core.util.AlgoUtil;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String url;
	private List<String[]> urlList;
	private String title;
	private String subtitle;
	private Integer containerIndex;
	private String addressBean;
	private String module;
	private List<Long> moduleIdList;
	private Map<String, GenericBean<Object>> beanLoadMap = new HashMap<String, GenericBean<Object>>();
	private GenericBean actualBean;
	private UIComponent tabView;
	private boolean activeTabRemoved;
	private boolean expandedWindow = false;
	public static transient UIComponent algoPalette;
	private transient Map<String,SessionUtilBean> appBeanMap = new HashMap<String,SessionUtilBean>();
	private int tabId;

	/**
	 * Creates a new instance of CentroCustoBean
	 */
	public BaseBean() {
		title = "Plataforma de Gerenciamento";
		this.main();
	}
	
    public static void setAlgoPalette(UIComponent algoPalette2) {
        algoPalette = algoPalette2;
    }
    
	public static UIComponent getAlgoPalette() {
		return algoPalette;
	}

	public Map<String, SessionUtilBean> getAppBeanMap() {		
		return this.appBeanMap;
	}

	public void setAppBeanMap(Map<String, SessionUtilBean> appBeanMap) {
		this.appBeanMap = appBeanMap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public boolean isBeanSubtitle() {
		return !"Principal Nova Janela".contains(this.subtitle);
	}

	public List<String[]> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String[]> urlList) {
		this.urlList = urlList;
	}

	public Integer getContainerIndex() {
		return containerIndex;
	}

	public void setContainerIndex(Integer containerIndex) {

		try {
			String beansubtitle = "";
			boolean clonable = false;
			if (actualBean != null) {
				clonable = Arrays.asList(actualBean.getClass().getGenericInterfaces()).contains(Cloneable.class);
				beansubtitle = actualBean.subtitle;
				if (this.containerIndex != containerIndex && clonable) {
					GenericBean actualBeanClone = actualBean.getClass().newInstance();
					copyObject(actualBean, actualBeanClone);
					getBeanLoadMap().put(beansubtitle, actualBeanClone);
				}
			}
			if (!urlList.isEmpty() && urlList.get(containerIndex)[0].equals(subtitle)) {
				GenericBean<Object> bean = beanLoadMap.get(urlList.get(containerIndex)[0]);
				if (bean != null) {
					if (bean instanceof GenericBean && !bean.subtitle.equals(beansubtitle) && clonable) {
						resetObject(actualBean);
						copyObject(bean, actualBean);
						actualBean.onReload();
					}
				}
			}
			this.containerIndex = containerIndex;
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(String addressBean) {
		this.addressBean = addressBean;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<Long> getModuleIdList() {
		return moduleIdList;
	}

	public void setModuleIdList(List<Long> moduleIdList) {
		this.moduleIdList = moduleIdList;
	}

	public Map<String, GenericBean<Object>> getBeanLoadMap() {
		return beanLoadMap;
	}

	public Object getActualBean() {
		return actualBean;
	}

	public void setActualBean(GenericBean actualBean) {
		this.actualBean = actualBean;
	}
	
	public UIComponent getTabView() {
		return tabView;
	}

	public void setTabView(UIComponent tabView) {
		if(tabView!=null){
			tabView.getChildren().clear();
		}
		this.tabView = tabView;	
	}			
	
	public void refresh(){
		UIComponent tabViewAlgoBoss = ComponentFactory.findComponentByStyleClass("tabViewAlgoBoss", FacesContext.getCurrentInstance().getViewRoot());
		if(tabViewAlgoBoss != null){
			tabViewAlgoBoss.getChildren().clear();
		}		
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);					
	}
	
	@PreDestroy
	public void onDestroy(){
		SessionUtilBean.clear();
	}

	public int getTabId() {
		return tabId++;
	}

	public void setTabId(Integer tabId) {
		//this.tabId = tabId;
	}

	public boolean isExpandedWindow() {
		return expandedWindow;
	}

	public void setExpandedWindow(boolean expandedWindow) {
		this.expandedWindow = expandedWindow;
	}

	public void main() {
		urlList = new ArrayList();
		moduleIdList = new ArrayList();
		subtitle = "Principal";
		url = "main.xhtml";
		urlList.add(new String[] { subtitle, url });
		setContainerIndex(getUrlList().isEmpty() ? 0 : getUrlList().size() - 1);
	}

	public void onChangeOld(TabChangeEvent event) {
		Tab activeTab = event.getTab();
		String title = activeTab != null ? activeTab.getTitle() : "";
		if (title.equals("+")) {
			setUrl("main.xhtml");
			setSubtitle("Nova Janela");
			getUrlList().add(new String[] { getSubtitle(), getUrl() });
			setContainerIndex(getUrlList().isEmpty() ? 0 : getUrlList().size() - 1);
		}
	}

	public void onChange(TabChangeEvent event) {
		Tab activeTab = event.getTab();
		try {
			if (activeTab != null && !activeTabRemoved) {
				String title = activeTab.getTitle();
				String[] stringsRemove = contains(title);
				if (stringsRemove != null && !title.equals("")) {
					setSubtitle(stringsRemove[0]);
					setUrl(stringsRemove[1]);
					setContainerIndex(getUrlList().indexOf(stringsRemove));
				} else {
					setContainerIndex(getUrlList().isEmpty() ? 0 : getUrlList().size() - 1);
					// RequestContext context =
					// RequestContext.getCurrentInstance();
					// context.update("basePanel");
				}
			}
			activeTabRemoved = false;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void newWindow() {
		try {
			String subt = "Nova Janela";
			String[] stringsRemove = contains(subt);
			if (stringsRemove == null) {
				setUrl("new.xhtml");
				setSubtitle(subt);
				getUrlList().add(new String[] { getSubtitle(), getUrl() });
				setContainerIndex(getUrlList().isEmpty() ? 0 : getUrlList().size() - 1);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void newIndex() {
		url = "new.xhtml";
		subtitle = "Nova Janela";
		setUrl(url);
		setSubtitle(subtitle);
		try {

			String[] str = contains(subtitle);
			if (str == null) {
				if (getUrlList().isEmpty()) {
					getUrlList().add(new String[] { getSubtitle(), getUrl() });
					setContainerIndex(0);
				} else {
					getUrlList().set(getContainerIndex(), new String[] { getSubtitle(), getUrl() });
					// baseBean.setContainerIndex(baseBean.getContainerIndex());
				}
			} else {
				setContainerIndex(getUrlList().indexOf(str));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void onClose(TabCloseEvent event) {
		try {
			Tab activeTab = event.getTab();
			if (activeTab != null) {
				String title = activeTab.getTitle();
				String[] stringsRemove = contains(title);
				if (stringsRemove != null) {
					int idx = urlList.indexOf(stringsRemove);
					//event.setTab(null);
					//event.setPhaseId(PhaseId.RESTORE_VIEW);
					//tabView.getChildren().remove(idx);
					urlList.remove(idx);
					if (!getUrlList().isEmpty() && subtitle.equals(title)) {
						idx = Math.min(getUrlList().size() - 1, Math.max(idx, 0));							
						String[] stringSelect = urlList.get(idx);
						setSubtitle(stringSelect[0]);
						setUrl(stringSelect[1]);
						setContainerIndex(idx);
						activeTabRemoved = true;
						//this.containerIndex = idx;
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String[] contains(String title) throws Throwable {
		String[] stringsRemove = null;
		for (String[] strings : urlList) {
			if (title != null && title.equals(strings[0])) {
				stringsRemove = strings;
				break;
			}
		}
		return stringsRemove;
	}

	public String dateFormat(String d, String s) {

		return new SimpleDateFormat(s).format(new Date(Long.parseLong(d)));
	}

	public static Converter getConverter(final List list, final Class type, final String idAttibute) {

		Converter converter = new Converter() {
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				Class objClass = type;
				if (type == null && list != null && !list.isEmpty()) {
					objClass = list.get(0).getClass();
				}
				Object obj = null;
				try {
					obj = objClass.newInstance();
					try {
						if (objClass != null && objClass.getDeclaredField(idAttibute) != null) {
							Field field = obj.getClass().getDeclaredField(idAttibute);
							field.setAccessible(true);
							field.set(obj, Long.valueOf(value));
							field.setAccessible(false);
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
					// forn.setFornecedorId(Long.valueOf(value));
					obj = list.get(list.indexOf(obj));
				} catch (InstantiationException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} finally {
					return obj;

				}
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				Class objClass = type;
				if (value != null && !list.isEmpty()) {
					objClass = list.get(0).getClass();
				}
				Object obj = value;
				String str = "";
				try {
					// throw new
					// UnsupportedOperationException("Not supported yet.");
					if (objClass != null && objClass.getDeclaredField(idAttibute) != null && obj != null) {
						Field field = obj.getClass().getDeclaredField(idAttibute);
						field.setAccessible(true);
						str = String.valueOf(field.get(obj));
						field.setAccessible(false);
					}
				} catch (NoSuchFieldException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SecurityException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (Throwable e) {
					e.printStackTrace();
				} finally {
					return str;
				}
			}
		};
		return converter;
	}

	public Converter getInstanceConverter(final List list, final Class type, final String idAttibute) {
		Converter converter = new Converter() {
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				Object obj = null;
				try {
					obj = type.newInstance();
					try {
						if (type.getDeclaredField(idAttibute) != null) {
							Field field = obj.getClass().getDeclaredField(idAttibute);
							field.setAccessible(true);
							field.set(obj, Long.valueOf(value));
							field.setAccessible(false);
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
					// forn.setFornecedorId(Long.valueOf(value));
					obj = list.get(list.indexOf(obj));
				} catch (InstantiationException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} finally {
					return obj;

				}
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				Object obj = value;
				String str = "";
				try {
					// throw new
					// UnsupportedOperationException("Not supported yet.");
					if (type.getDeclaredField(idAttibute) != null && obj != null) {
						Field field = obj.getClass().getDeclaredField(idAttibute);
						field.setAccessible(true);
						str = field.get(obj).toString();
						field.setAccessible(false);
					}
				} catch (NoSuchFieldException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SecurityException ex) {
					Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
				} catch (Throwable e) {
					e.printStackTrace();
				} finally {
					return str;
				}
			}
		};
		return converter;
	}

	public static String getBundle(String key, String bundleName) {
		return AlgoUtil.getBundle(key, bundleName);
	}

	public String encodeHtml(String html) {
		return String.valueOf(html).replaceAll("'", "\\'").replaceAll("\"", "\\\"").replaceAll("\n", " ");
	}

	public ComponentSystemEvent getComponentEvent(UIComponent comp) {
		return new ComponentSystemEvent(comp) {
			private static final long serialVersionUID = 1L;

		};
	}

	public static <T> void resetObject(T to) {
		Object from = null;
		try {
			from = to.getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (from != null) {
			for (Class obj = from.getClass(); !obj.equals(Object.class); obj = obj.getSuperclass()) {
				// Percorrer campo por campo da classe...
				for (Field field : obj.getDeclaredFields()) {
					field.setAccessible(true);
					try {
						if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
							Object val = null;
							val = field.get(from);

							field.set(to, val);
						}
						// Copiar campo atual
					} catch (Throwable t) {
						new IllegalStateException("Fail to set field: " + field.getName(), t);
					}
				}

			}
		}
	}
	public static <T> T copyObject(T from) {
		T to = null;;
		try {
			to = (T) from.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		copyObject(from,to);
		return to;
	}
	public static <T> void copyObject(T from, T to) {
		for (Class obj = from.getClass(); !obj.equals(Object.class); obj = obj.getSuperclass()) {
			// Percorrer campo por campo da classe...
			for (Field field : obj.getDeclaredFields()) {
				field.setAccessible(true);
				try {
					if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isPrivate(field.getModifiers())) {
						Object val = null;
						if (field.getType().getSimpleName().equals("UIComponent")) {
							UIComponent comp = (UIComponent) field.get(from);
							if (comp != null) {

								/*
								 * UIComponent parent = comp.getParent();
								 * if(parent.getChildren().remove(comp)){ comp =
								 * ComponentFactory.componentClone(comp, false);
								 * parent.getChildren().add(comp); val = comp; }
								 */
								if(FacesContext.getCurrentInstance()!=null && !comp.isTransient()){
									comp = ComponentFactory.componentClone(comp, false);
								}
								val = comp;
							}
						} else {
							val = field.get(from);
						}
						field.set(to, val);
					}
					// Copiar campo atual
				} catch (Throwable t) {
					new IllegalStateException("Fail to set field: " + field.getName(), t);
				}
			}

		}
	}

}
