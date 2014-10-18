/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.xml.registry.infomodel.User;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityClassConfig;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyDescriptorConfig;
import com.algoboss.erp.entity.DevPrototypeComponentBehaviors;
import com.algoboss.erp.entity.DevPrototypeComponentChildren;
import com.algoboss.erp.entity.DevPrototypeComponentFacets;
import com.algoboss.erp.entity.DevPrototypeComponentProperty;
import com.algoboss.erp.face.AdmAlgodevBean;
import com.algoboss.erp.face.BaseBean;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.faces.facelets.compiler.XMLInstruction;
import com.sun.faces.facelets.el.ELText;

/**
 *
 * @author Agnaldo
 */
public class ComponentFactory {

	private UIComponent componentCreated;

	public UIComponent getComponentCreated() {
		return componentCreated;
	}

	public ComponentFactory(UIComponent comp, UIComponent elementPanel, String parentContainer, String parentEntity, Map<String, List<UIComponent>> elementsContainerMap, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		try {
			if (entitySelected != null) {
				UIComponent compClone = comp;// ComponentFactory.componentClone(comp,
												// false);
				Object styleClass = getProperty(compClone, "styleClass");
				// if (styleClass != null &&
				// String.valueOf(styleClass).contains("ui-integer")) {
				UIComponent compFinded = null;
				if ((compFinded = findComponentByClassName("javax.faces.component.html.HtmlSelectOneMenu", compClone)) != null) {
					if ((compFinded = findComponentByClassName("javax.faces.component.UISelectItems", compFinded)) != null) {
						this.doSelectItemsFactory(compFinded, parentContainer, entitySelected, propertySelectedList, appType);
					}
				}
				if (styleClass != null && String.valueOf(styleClass).contains("data-list")) {
					if ((compFinded = findComponentByClassName("javax.faces.component.UIColumn", compClone)) != null) {
						this.doUIColumnFactory(compFinded, elementPanel, parentContainer, parentEntity, entitySelected, propertySelectedList, appType);
						elementsContainerMap.put("list", new ArrayList<UIComponent>(compClone.getParent().getChildren()));
					}
				}
				if (styleClass != null && String.valueOf(styleClass).contains("data-form")) {
					this.doUIFieldSetFactory(compClone, elementPanel, parentContainer, parentEntity, elementsContainerMap, entitySelected, propertySelectedList, appType);
					elementsContainerMap.put("form", new ArrayList<UIComponent>(compClone.getParent().getChildren()));
				}
				if (styleClass != null && String.valueOf(styleClass).contains("data-grid")) {
					this.doUIDataGridFactory(compClone, elementPanel, parentContainer, parentEntity, elementsContainerMap, entitySelected, propertySelectedList, appType);
				}
				if (styleClass != null && String.valueOf(styleClass).contains("data-carousel")) {
					this.doUICarouselFactory(compClone, elementPanel, parentContainer, parentEntity, elementsContainerMap, entitySelected, propertySelectedList, appType);
				}
				/*
				 * if
				 * (Class.forName("javax.faces.component.html.HtmlSelectOneMenu"
				 * ).isAssignableFrom(compClone.getClass())) { for (UIComponent
				 * child : compClone.getChildren()) { if
				 * (Class.forName("javax.faces.component.UISelectItems"
				 * ).isAssignableFrom(child.getClass())) {
				 * this.doSelectItemsFactory(child, parentContainer,
				 * entitySelected, propertySelectedList, appType); break; } } }
				 * if (styleClass != null &&
				 * String.valueOf(styleClass).contains("data-list")) { for
				 * (UIComponent child : compClone.getChildren()) { if
				 * (Class.forName
				 * ("javax.faces.component.UIColumn").isAssignableFrom
				 * (child.getClass())) { this.doUIColumnFactory(child,
				 * elementPanel, parentContainer, parentEntity, entitySelected,
				 * propertySelectedList, appType); break; } } } if (styleClass
				 * != null && String.valueOf(styleClass).contains("data-form"))
				 * { this.doUIFieldSetFactory(compClone, elementPanel,
				 * parentContainer, parentEntity, elementsContainerMap,
				 * entitySelected, propertySelectedList, appType); } if
				 * (styleClass != null &&
				 * String.valueOf(styleClass).contains("data-grid")) { for
				 * (UIComponent child : compClone.getChildren()) { if
				 * (Class.forName
				 * ("javax.faces.component.UIColumn").isAssignableFrom
				 * (child.getClass())) { //this.doUIColumnFactory(child,
				 * elementPanel, parentContainer, parentEntity, entitySelected,
				 * propertySelectedList, appType); break; } }
				 * this.doUIDataGridFactory(compClone, elementPanel,
				 * parentContainer, parentEntity, elementsContainerMap,
				 * entitySelected, propertySelectedList, appType); } if
				 * (styleClass != null &&
				 * String.valueOf(styleClass).contains("data-carousel")) {
				 * this.doUICarouselFactory(compClone, elementPanel,
				 * parentContainer, parentEntity, elementsContainerMap,
				 * entitySelected, propertySelectedList, appType); }
				 */
				componentCreated = compClone;
			}
		} catch (Exception ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void setValue(UIComponent comp, String field, String strExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ValueExpression o = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), strExpression, String.class);
		comp.setValueExpression(field, (ValueExpression) o);
	}

	private void doSelectItemsFactory(UIComponent comp, String field, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		String propertyArrayStr = "";
		for (int i = 0; false && i < propertySelectedList.size(); i++) {
			DevEntityPropertyDescriptor devEntityPropertyDescriptor = propertySelectedList.get(i);
			if (devEntityPropertyDescriptor.isPropertyIdentifier()) {
				if (!propertyArrayStr.isEmpty()) {
					propertyArrayStr += ";";
				}
				propertyArrayStr += devEntityPropertyDescriptor.getPropertyName();
			}
		}
		// String str = "#{app.beanMap('" + entitySelected.getName() + "','" +
		// propertyArrayStr + "')}";
		String str = "#{app.beanMap('" + entitySelected.getName() + "')}";
		setValue(comp, field, str);
	}

	private void doUIColumnFactory(UIComponent compClone, UIComponent elementPanel, String parentContainer, String parentEntity, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			boolean hasEdit = appType == AppType.LIST_EDIT_FORM;
			UIComponent cloned = null;
			UIComponent parent = compClone.getParent();
			UIComponent rowEditor = compClone.getChildren().remove(1);
			for (int i = 0; i < propertySelectedList.size(); i++) {
				DevEntityPropertyDescriptor devEntityPropertyDescriptor = propertySelectedList.get(i);
				if (i == 0) {
					parent.getChildren().clear();
				}
				cloned = ComponentFactory.componentClone(compClone, false);
				parent.getChildren().add(cloned);

				boolean center = false;
				Map<String, Object> mapParam = new HashMap<String, Object>();
				// mapParam.put("filterMatchMode", "");
				// mapParam.put("filterBy", "");
				// mapParam.put("filterStyle", "");
				Map<String, String> configMap = new HashMap<String, String>();
				if (devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null) {
					for (DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfig : devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList()) {
						if (!String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).isEmpty() && !String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).equals("null")) {
							configMap.put(devEntityPropertyDescriptorConfig.getConfigName(), devEntityPropertyDescriptorConfig.getConfigValue());
						}
					}
				}
				parentContainer = (parentContainer != null && !parentContainer.isEmpty()) ? parentContainer : "list";
				try {
					UIComponent childAux = cloned.getChildren().get(0).getFacet("output");
					cloned.getChildren().get(0).getFacets().put("input", createInputComponent(elementPanel, entitySelected, devEntityPropertyDescriptor, configMap, parentEntity, appType, parentContainer));
					for (UIComponent child : childAux.getChildren()) {
						if (Class.forName("javax.faces.component.html.HtmlOutputText").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")) {
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								// mapParam.put("filterBy", "#{app.$('" +
								// entitySelected.getName() + "." +
								// devEntityPropertyDescriptor.getPropertyName()
								// + "',item).val}");
								// mapParam.put("filterMatchMode", "contains");
								// mapParam.put("filterStyle", "display:none;");
								setValue(child, "value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")) {
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								// mapParam.put("filterBy", "#{app.$('" +
								// entitySelected.getName() + "." +
								// devEntityPropertyDescriptor.getPropertyName()
								// + "',item).val}");
								// mapParam.put("filterMatchMode", "contains");
								// mapParam.put("filterStyle", "display:none;");
								setValue(child, "value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING")) {
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("filterBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("filterMatchMode", "contains");
								mapParam.put("filterStyle", "display:none;");
								setValue(child, "value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
								String pattern = BaseBean.getBundle("calendar.pattern", "msg");
								if (configMap.containsKey("pattern")) {
									pattern = configMap.get("pattern");
								}
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("filterBy", "$date('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "','" + pattern + "')");
								mapParam.put("filterMatchMode", "contains");
								mapParam.put("filterStyle", "display:none;");
								setValue(child, "value", "#{app.$date('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "','" + pattern + "',item)}");
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
								mapParam.put("filterBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("sortBy", devEntityPropertyDescriptor.getPropertyName());
								mapParam.put("filterMatchMode", "contains");
								mapParam.put("filterStyle", "display:none;");
								Map<String, Object> mapParam2 = new HashMap<String, Object>();
								mapParam2.put("value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
								mapParam2.put("styleClass", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item).val?' ui-icon ui-icon-check ':' ui-icon ui-icon-cancel ')}");
								mapParam2.put("style", "display:inline-block;");
								ComponentFactory.updateElementProperties(f2, child, mapParam2, null);
								center = true;
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS")) {
								mapParam.put("filterMatchMode", "contains");
								mapParam.put("filterStyle", "display:none;");
								mapParam.put("filterBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								setValue(child, "value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
							} else if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
								mapParam.put("filterMatchMode", "contains");
								mapParam.put("filterStyle", "display:none;");
								mapParam.put("filterBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								mapParam.put("sortBy", "getProp('" + devEntityPropertyDescriptor.getPropertyName() + "')");
								setValue(child, "value", "#{(app.count('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item))}");
								center = true;
							} else {
								setValue(child, "value", "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item)).val}");
							}
						}
					}
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
				}

				mapParam.put("header", devEntityPropertyDescriptor.getPropertyLabel());
				mapParam.put("headerText", devEntityPropertyDescriptor.getPropertyLabel());
				generateComponentStyleClassName(mapParam, "list", entitySelected, devEntityPropertyDescriptor, "");
				if (center) {
					mapParam.put("style", "text-align:center;");
				}
				mapParam.put("style", Objects.toString(mapParam.get("style"), "").concat("overflow: hidden;white-space:nowrap;"));
				ComponentFactory.updateElementProperties(f2, cloned, mapParam, null);
				// propertyArrayStr +=
				// devEntityPropertyDescriptor.getPropertyName();
			}
			try {
				cloned = ComponentFactory.componentClone(compClone, false);
				parent.getChildren().add(cloned);
				Map<String, Object> mapParam = new HashMap<String, Object>();
				mapParam.put("headerText", "#{msg['action']}");
				mapParam.put("exportable", "false");
				// mapParam.put("filterMatchMode", "");
				// mapParam.put("filterBy", "");
				// mapParam.put("sortBy", "");
				mapParam.put("style", "text-align:center;width:" + (hasEdit ? "150px;" : "100px;"));
				ComponentFactory.updateElementProperties(f2, cloned, mapParam, null);
				UIComponent link1 = null;
				UIComponent link2 = null;
				UIComponent label = null;
				UIComponent childAux = cloned.getChildren().get(0).getFacet("output");
				for (UIComponent child : childAux.getChildren()) {
					Map<String, Object> mapParam2 = new HashMap<String, Object>();
					if (Class.forName("javax.faces.component.html.HtmlCommandLink").isAssignableFrom(child.getClass())) {
						String editAction = "#{app.doEdit(item)}";
						String removeAction = "#{app.doRemove(item)}";
						if (parentContainer.contains("-")) {
							editAction = "#{app.doEditChild(item,'" + parentContainerName(parentContainer) + "-form-" + entitySelected.getName() + "')}";
							removeAction = "#{app.doRemoveChild(item)}";
						}
						link1 = ComponentFactory.componentClone(child, false);
						mapParam2.put("actionListener", editAction);
						mapParam2.put("value", hasEdit ? "#{msg['open']}" : "#{msg['edit']}");
						ComponentFactory.updateElementProperties(f2, link1, mapParam2, null);

						link2 = ComponentFactory.componentClone(child, false);

						mapParam2.clear();
						mapParam2.put("actionListener", removeAction);
						mapParam2.put("value", "#{msg['exclude']}");
						ComponentFactory.updateElementProperties(f2, link2, mapParam2, null);
					}
					if (Class.forName("javax.faces.component.html.HtmlOutputText").isAssignableFrom(child.getClass())) {
						label = child;
						mapParam2.clear();
						mapParam2.put("value", " | ");
						ComponentFactory.updateElementProperties(f2, label, mapParam2, null);
					}
				}
				cloned.getChildren().clear();
				if (hasEdit) {
					cloned.getChildren().add(rowEditor);
					// mapParam.put("style", "float:left;");
					// ComponentFactory.updateElementProperties(f2, link1,
					// mapParam2, null);
				}
				cloned.getChildren().add(link1);
				cloned.getChildren().add(label);
				cloned.getChildren().add(link2);

			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
			}
			String node = "";
			if (!parentEntity.isEmpty()) {
				node = parentEntity + ".";
			}
			String str = "#{app.beanListMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str3 = "#{app.beanListFilteredMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str2 = "#{app.beanSel}";
			Map<String, Object> mapParam = new HashMap<String, Object>();
			mapParam.put("value", str);
			mapParam.put("rowKey", "#{item.hashCode()}");
			mapParam.put("editable", "true");
			mapParam.put("filteredValue", str3);
			// mapParam.put("selection", str2);
			// mapParam.put("selectionMode", "single");
			mapParam.put("paginator", "true");
			mapParam.put("rows", "10");
			mapParam.put("sortMode", "multiple");
			mapParam.put("tableStyle", "width:990px;table-layout: fixed; overflow: hidden;");
			mapParam.put("style", "width:990px;table-layout: fixed; overflow: hidden;");
			mapParam.put("scrollable", "false");
			mapParam.put("scrollWidth", "1010");
			mapParam.put("scrollHeight", "210");
			mapParam.put("widgetVar", "c_" + entitySelected.getName() + "DataTable");

			componentCreated = cloned.getParent();
			ComponentFactory.updateElementProperties(f2, componentCreated, mapParam, null);
			if (componentCreated.getFacets().containsKey("footer")) {
				UIComponent export = componentCreated.getFacets().get("footer");
				if (export instanceof UICommand) {
					mapParam.clear();
					mapParam.put("value", "#{msg['export']}");
					mapParam.put("action", "#{app.exportDataTable()}");
					ComponentFactory.updateElementProperties(f2, export, mapParam, null);
				}
			}
			if (componentCreated.getFacets().containsKey("header")) {
				UIComponent labelCloned = null;
				UIComponent label2Cloned = null;
				UIComponent inputCloned = null;
				UIComponent panelCloned = null;
				UIComponent panelCloned2 = null;

				panelCloned = ComponentFactory.componentClone(findComponentByStyleClass("ui-panel", elementPanel), false);
				mapParam.clear();
				mapParam.put("header", "");
				ComponentFactory.updateElementProperties(f2, panelCloned, mapParam, null);
				for (UIComponent child : elementPanel.getChildren()) {

					if (panelCloned2 == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						// panelCloned2 = ComponentFactory.componentClone(
						// child, context);
						mapParam.clear();
						// mapParam.put("header", "");
						// ComponentFactory.updateElementProperties(f2,
						// panelCloned2, mapParam, null);
					}
					if (labelCloned == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						labelCloned = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("value", "#{msg['listing']}: " + entitySelected.getLabel());
						ComponentFactory.updateElementProperties(f2, labelCloned, mapParam, null);
					}
					if (label2Cloned == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						label2Cloned = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("value", "#{msg['search']}: ");
						mapParam.put("style", "float:right;top:-5px;position: relative;");
						ComponentFactory.updateElementProperties(f2, label2Cloned, mapParam, null);
					}
					if (inputCloned == null && child instanceof javax.faces.component.html.HtmlInputText) {
						inputCloned = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("onkeyup", "c_" + entitySelected.getName() + "DataTable" + ".filter()");
						// mapParam.put("style",
						// "float:right;position:relative;top:-20% !important;");
						ComponentFactory.updateElementProperties(f2, inputCloned, mapParam, null);
						inputCloned.setId("globalFilter");
					}
				}

				panelCloned.getChildren().add(labelCloned);
				panelCloned.getChildren().add(label2Cloned);
				label2Cloned.getChildren().add(inputCloned);

				componentCreated.getFacets().put("header", panelCloned);
			}
			doUICommandButtonListFactory("c_" + entitySelected.getName() + "DataTable", componentCreated.getParent(), elementPanel, entitySelected, parentContainer, appType);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void doUICommandButtonListFactory(String widgetList, UIComponent container, UIComponent elementPanel, DevEntityClass entitySelected, String parentContainer, AppType appType) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			UIComponent button = null;
			UIComponent buttonCloned = null;
			UIComponent buttonCloned2 = null;

			button = findComponentByClassName("javax.faces.component.html.HtmlCommandButton", elementPanel);
			buttonCloned = ComponentFactory.componentClone(button, false);
			String action = "#{app.doForm()}";
			if (parentContainer.contains("-") && entitySelected != null) {
				action = "#{app.doForm('" + parentContainerName(parentContainer) + "-form-" + entitySelected.getName() + "')}";
				buttonCloned2 = ComponentFactory.componentClone(button, false);
			}

			Map<String, Object> mapParam = new HashMap<String, Object>();
			if (buttonCloned != null) {
				mapParam.put("value", "#{msg['include']}");
				mapParam.put("action", action);
				mapParam.put("update", "@form");
				mapParam.put("immediate", "true");
				ComponentFactory.updateElementProperties(f2, buttonCloned, mapParam, null);
				container.getChildren().add(0, buttonCloned);
			}
			if (buttonCloned2 != null) {
				mapParam.clear();
				mapParam.put("value", "#{msg['back']}");
				mapParam.put("action", "#{app.doForm('" + parentContainerName(parentContainer) + "')}");
				mapParam.put("update", "@form");
				mapParam.put("immediate", "true");
				ComponentFactory.updateElementProperties(f2, buttonCloned2, mapParam, null);
				container.getChildren().add(1, buttonCloned2);
			}

		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void doUICommandButtonFormFactory(UIComponent container, UIComponent elementPanel, DevEntityClass entitySelected, String parentContainer, String parentEntity, AppType appType) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			UIComponent button = null;
			UIComponent buttonCloned = null;
			UIComponent panelCloned = null;
			boolean hasList = appType.hasList();
			String node = "";
			if (!parentEntity.isEmpty()) {
				node = parentEntity + ".";
			}
			Map<String, Object> mapParam = new HashMap<String, Object>();
			Map<String, Object> mapParamBasic = new HashMap<String, Object>();

			panelCloned = ComponentFactory.componentClone(findComponentByStyleClass("ui-panel", elementPanel), false);
			mapParam.put("header", "");
			ComponentFactory.updateElementProperties(f2, panelCloned, mapParam, null);

			button = findComponentByClassName("javax.faces.component.html.HtmlCommandButton", elementPanel);

			String save = "#{app.doSave()}";
			String saveAndBack = "#{app.doSaveAndList()}";
			String back = "#{app.doList()}";
			String copy = "#{app.doCopy()}";
			if (parentContainer.contains("-")) {
				save = "#{app.doSaveChild()}";
				if (hasList) {
					saveAndBack = "#{app.doSaveAndListChild('" + parentContainerName(parentContainer) + "-list-" + entitySelected.getName() + "','" + node + entitySelected.getName() + "')}";
					back = "#{app.doList('" + parentContainerName(parentContainer) + "-list-" + entitySelected.getName() + "','" + node + entitySelected.getName() + "')}";
				} else {
					saveAndBack = "#{app.doSaveAndListChild('" + parentContainerName(parentContainer) + "','" + node + "')}";
					back = "#{app.doList('" + parentContainerName(parentContainer) + "','" + node + "')}";
				}
				copy = "#{app.doCopyChild()}";
			}
			mapParamBasic.put("update", "@form");
			mapParamBasic.put("immediate", "false");
			if (hasList) {
				buttonCloned = ComponentFactory.componentClone(button, false);
				mapParam.putAll(mapParamBasic);
				mapParam.put("value", "#{msg['save']}");
				mapParam.put("action", save);
				ComponentFactory.updateElementProperties(f2, buttonCloned, mapParam, null);
				panelCloned.getChildren().add(buttonCloned);
			}
			buttonCloned = ComponentFactory.componentClone(button, false);
			mapParam.putAll(mapParamBasic);
			mapParam.put("value", "#{msg['saveAndBack']}");
			mapParam.put("action", saveAndBack);
			ComponentFactory.updateElementProperties(f2, buttonCloned, mapParam, null);
			panelCloned.getChildren().add(buttonCloned);

			if (hasList) {
				buttonCloned = ComponentFactory.componentClone(button, false);
				mapParam.putAll(mapParamBasic);
				mapParam.put("value", "#{msg['copy']}");
				mapParam.put("action", copy);
				ComponentFactory.updateElementProperties(f2, buttonCloned, mapParam, null);
				panelCloned.getChildren().add(buttonCloned);
			}

			buttonCloned = ComponentFactory.componentClone(button, false);
			mapParam.putAll(mapParamBasic);
			mapParam.put("value", "#{msg['back']}");
			mapParam.put("action", back);
			mapParam.put("immediate", "true");
			ComponentFactory.updateElementProperties(f2, buttonCloned, mapParam, null);
			panelCloned.getChildren().add(buttonCloned);
			container.getChildren().add(0, panelCloned);

		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private UIComponent createInputComponent(UIComponent elementPanel, DevEntityClass entitySelected, DevEntityPropertyDescriptor devEntityPropertyDescriptor, Map<String, String> configMap, String parentEntity, AppType appType, String parentContainer) {
		UIComponent input = null;
		UIComponent inputCloned = null;
		UIComponent inputWrapper = null;
		String item = "";
		boolean editableList = appType == AppType.LIST_EDIT_FORM && parentContainer.contains("list");
		if (editableList) {
			item = ",item";
		} else {
			item = !parentEntity.isEmpty() ? ",app.childBean" : "";
		}
		Object styleClass = null;
		Map<String, Object> mapParam = new HashMap<String, Object>();
		if (devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null) {
			for (DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfig : devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList()) {
				if (!String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).isEmpty() && !String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).equals("null")) {
					configMap.put(devEntityPropertyDescriptorConfig.getConfigName(), devEntityPropertyDescriptorConfig.getConfigValue());
				}
			}
		}
		try {
			for (UIComponent child : elementPanel.getChildren()) {
				styleClass = getProperty(child, "styleClass");
				if (styleClass != null && String.valueOf(styleClass).contains("ui-integer")) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")) {
						input = child;
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")) {
						input = child;
						if (!configMap.containsKey("precision") || configMap.get("precision") == null) {
							configMap.put("precision", "2");
						}
						break;
					}
				}
				boolean isList = false;
				boolean isMask = false;
				boolean isShort = false;
				boolean isLong = false;
				if (configMap.containsKey("list") && !configMap.get("list").isEmpty()) {
					isList = true;
				} else if (configMap.containsKey("mask") && configMap.get("mask") != null && !configMap.get("mask").isEmpty()) {
					isMask = true;
				} else if ((!configMap.containsKey("length") || configMap.get("length") == null || configMap.get("length").equals("short"))) {
					isShort = true;
				} else if (configMap.containsKey("length") && configMap.get("length").equals("long")) {
					isLong = true;
				}
				if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && isList) {
						input = child;
						break;
					}
				}
				if (styleClass != null && String.valueOf(styleClass).contains("ui-mask")) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && isMask) {
						input = child;
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && isShort) {
						input = child;
						break;
					}
				}
				if (styleClass != null && String.valueOf(styleClass).contains("ui-textArea")) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && isLong) {
						input = child;
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlSelectBooleanCheckbox").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
						input = child;
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FILE")) {
						input = child;
						// mapParam.put("update", "@form");
						mapParam.put("immediate", "true");
						String propValObj = "app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "'" + item + ")";
						String str = "#{(" + propValObj + ".val==null?msg['select']:" + propValObj + ".val)}";
						mapParam.put("value", str);
						mapParam.put("action", "#{app.prepareUpload(" + propValObj + ")}");
						mapParam.put("oncomplete", "(handleFileUploadDlg(xhr, status, args))");
						// mapParam.put("onclick",
						// "function () {uploadFileWv.show();return true;}");
						// mapParam.put("onclick",
						// "javascript:(function(){uploadFileWv.show();return false;})");
						ComponentFactory.updateElementProperties(input, mapParam);
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(child.getClass())) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS") && devEntityPropertyDescriptor.getPropertyClass() != null) {
						input = child;
						break;
					}
				}
				if (Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
					if ((devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") || devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) && devEntityPropertyDescriptor.getPropertyClass() != null) {
						input = ComponentFactory.componentClone(child, false);
						break;
					}
				}
				/*
				 * if (false &&
				 * Class.forName("javax.faces.component.UIData").isAssignableFrom
				 * (child.getClass())) { if
				 * (devEntityPropertyDescriptor.getPropertyType
				 * ().equalsIgnoreCase("ENTITYCHILDREN") &&
				 * devEntityPropertyDescriptor.getPropertyClass() != null) { if
				 * (
				 * Class.forName("javax.faces.component.UIPanel").isAssignableFrom
				 * (child.getChildren().get(0).getClass())) { if (containerChild
				 * == null) { containerChild =
				 * ComponentFactory.componentClone(child, false); tab =
				 * containerChild.getChildren().get(0);
				 * comp.getParent().getParent
				 * ().getChildren().add(containerChild); } else { tab =
				 * ComponentFactory
				 * .componentClone(containerChild.getChildren().get(0), false);
				 * containerChild.getChildren().add(tab); } break; } } }
				 */
				if (styleClass != null && String.valueOf(styleClass).contains("ui-calendar")) {
					if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
						input = child;
						break;
					}
				}
			}
			inputCloned = ComponentFactory.componentClone(input, false);
			mapParam.clear();
			if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.containsKey("list") && !configMap.get("list").isEmpty()) {
				UIComponent childAux = null;
				for (UIComponent child : inputCloned.getChildren()) {
					if (Class.forName("javax.faces.component.UISelectItem").isAssignableFrom(child.getClass())) {
						childAux = child;
						break;
					}
				}
				String[] strList = configMap.get("list").split("\n");
				for (int j = 0; j < strList.length; j++) {
					String string = strList[j];
					UIComponent childCloned = ComponentFactory.componentClone(childAux, false);
					mapParam.put("itemValue", string);
					mapParam.put("itemLabel", string);
					ComponentFactory.updateElementProperties(childCloned, mapParam);
					inputCloned.getChildren().add(childCloned);
				}
				mapParam.clear();
			} else {
				if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(input.getClass())) {
					DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
					// new ComponentFactory(inputCloned, elementPanel, "value",
					// entitySelected.getName(), elementsContainerMap, ent,
					// ent.getEntityPropertyDescriptorList(), appType);
					UIComponent compFinded = null;
					if ((compFinded = findComponentByClassName("javax.faces.component.UISelectItems", inputCloned)) != null) {
						this.doSelectItemsFactory(compFinded, "value", ent, ent.getEntityPropertyDescriptorList(), appType);
					}
					if (!editableList) {
						UIComponent labelSelect = ComponentFactory.componentClone(findComponentByStyleClass("ui-textOutput", elementPanel), false);
						// mapParam.put("style", "vertical-align:middle;");
						// ComponentFactory.updateElementProperties(labelSelect,
						// mapParam);
						UIComponent openCrud = ComponentFactory.componentClone(findComponentByStyleClass("ui-commandButton", elementPanel), false);
						mapParam.put("icon", "ui-icon-newwin");
						mapParam.put("style", "position:absolute;#{!gerLoginBean.appEntityMap.containsKey('" + ent.getName() + "')?'display:none':''};");
						mapParam.put("value", "");
						mapParam.put("update", ":basePanel");
						mapParam.put("immediate", "true");
						mapParam.put("action", "#{app.indexBeanNewWin(gerLoginBean.appEntityMap.get('" + ent.getName() + "'))}");
						mapParam.put("disabled", "#{!gerLoginBean.appEntityMap.containsKey('" + ent.getName() + "')}");
						mapParam.put("readonly", "#{!gerLoginBean.appEntityMap.containsKey('" + ent.getName() + "')}");
						ComponentFactory.updateElementProperties(openCrud, mapParam);
						labelSelect.getChildren().add(inputCloned);
						labelSelect.getChildren().add(openCrud);
						inputWrapper = labelSelect;
					} else {
						Object o = getProperty(inputCloned, "clientBehaviors");
						if (o != null) {
							for (Map.Entry<String, List<ClientBehavior>> objectFacets : ((Map<String, List<ClientBehavior>>) o).entrySet()) {
								String objectMap1 = objectFacets.getKey();
								List<ClientBehavior> l = objectFacets.getValue();
								l.clear();
								// ((UIComponentBase)inputCloned).addClientBehavior(objectMap1,
								// null);
								// List<ClientBehavior> objectMap2List =
								// objectFacets.getValue();
								// ClientBehavior objectMap2 =
								// objectMap2List.get(0);
								// behaviorsArrayList.add(new
								// DevPrototypeComponentBehaviors(objectMap1,
								// objectMap2.getClass().getName(),
								// componentSerializerBehavior(objectMap2)));
							}
							// ((Map<String,List<ClientBehavior>>)o).clear();
						}
					}
					// mapParam.put("converter", "EntityObjectConverter");
				}
			}
			// node += entitySelected.getName() + "." +
			// devEntityPropertyDescriptor.getPropertyName();
			String str = "";
			if (inputCloned.getValueExpression("value") == null) {
				str = "#{(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "'" + item + ")).val}";
				mapParam.put("value", str);
			}
			// mapParam.put("styleClass", "");
			mapParam.put("required", devEntityPropertyDescriptor.isPropertyRequired());
			mapParam.put("requiredMessage", "#{msg.required}: " + devEntityPropertyDescriptor.getPropertyLabel());
			// mapParam.put("tabindex", String.valueOf(tabindex));
			mapParam.put("tabindex", String.valueOf("1"));

			if (configMap.containsKey("mask")) {
				mapParam.put("mask", configMap.get("mask"));
			}
			if (configMap.containsKey("precision")) {
				mapParam.put("styleClass", Objects.toString(styleClass, "").concat(" decimalFormat".concat(configMap.get("precision"))));
			}
			if (configMap.containsKey("xhtml") && configMap.get("xhtml").equals("true")) {
				mapParam.put("styleClass", Objects.toString(styleClass, "").concat(" no-upper"));
			}
			if (configMap.containsKey("length")) {
				// mapParam.put("mask", configMap.get("mask"));
			}
			if (configMap.containsKey("pattern")) {
				mapParam.put("pattern", configMap.get("pattern"));
				mapParam.put("styleClass", Objects.toString(styleClass, "").replaceAll(" dateFormat", "").concat(" timeFormat"));
				if (configMap.get("pattern").equals("HH:mm")) {
					mapParam.put("timeOnly", true);
				}
			}
			generateComponentStyleClassName(mapParam, "form", entitySelected, devEntityPropertyDescriptor, "input");
			// setValue(inputCloned, "value", str);
			ComponentFactory.updateElementProperties(inputCloned, mapParam);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inputWrapper != null) {
			inputCloned = inputWrapper;
		}
		return inputCloned;
	}

	private void generateComponentStyleClassName(Map<String, Object> mapParam, String parent, DevEntityClass entitySelected, DevEntityPropertyDescriptor devEntityPropertyDescriptor, String suffix) {
		String preffix = " c_";
		mapParam.put("styleClass", Objects.toString(mapParam.get("styleClass"), "").concat(preffix + parent + preffix + entitySelected.getName() + preffix + devEntityPropertyDescriptor.getPropertyName() + (suffix.isEmpty() ? "" : preffix + suffix)));
	}

	private void doUIFieldSetFactory(UIComponent comp, UIComponent elementPanel, String parentContainer, String parentEntity, Map<String, List<UIComponent>> elementsContainerMap, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		try {
			String propertyArrayStr = "";
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			UIComponent labelCloned = null;
			UIComponent inputCloned = null;
			UIComponent label = null;
			UIComponent input = null;
			UIComponent tab = null;
			UIComponent containerChild = ComponentFactory.componentClone(comp.getParent(), false);
			containerChild.getChildren().clear();
			UIComponent algoContainer = ComponentFactory.componentClone(comp.getParent().getParent(), false);
			algoContainer.getChildren().clear();
			UIComponent algoContainerCloned = null;
			UIComponent panelInternalObject = null;
			UIComponent panelAuth = null;
			// Map<String, Object> mapParam1 = new HashMap<String, Object>();
			// mapParam1.put("legend", "");
			// ComponentFactory.updateElementProperties(f2, containerChild,
			// mapParam1, null);
			int tabindex = 1;
			UIComponent buttom = null;
			UIComponent panel = null;
			try {
				Object styleClass = null;
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("data-panel")) {
						panel = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					if (Class.forName("javax.faces.component.html.HtmlOutputLabel").isAssignableFrom(child.getClass())) {
						label = child;
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
			}
			Map<String, Object> mapParam = new HashMap<String, Object>();
			UIComponent panelTableParent = null;
			panelTableParent = ComponentFactory.componentClone(panel, false);
			mapParam.put("style", "display:table");
			mapParam.put("layout", "block");
			ComponentFactory.updateElementProperties(panelTableParent, mapParam);
			UIComponent panelScroll = ComponentFactory.componentClone(findComponentByStyleClass("ui-algo-element ui-algo-element-container ui-scrollPanel", elementPanel), false);
			mapParam.put("style", "width:965px;height:335px");
			mapParam.put("mode", "native");
			ComponentFactory.updateElementProperties(panelScroll, mapParam);
			if (propertySelectedList.size() > 14) {
				comp.getChildren().add(panelScroll);
				panelScroll.getChildren().add(panelTableParent);
			} else {
				comp.getChildren().add(panelTableParent);
			}
			UIComponent panelTableChild = null;
			for (int i = 0; i < propertySelectedList.size(); i++) {
				if (i % 7 == 0) {
					UIComponent panelTableColumn = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table-cell;vertical-align:top;");
					// mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(panelTableColumn, mapParam);
					panelTableParent.getChildren().add(panelTableColumn);

					panelTableChild = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table");
					mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(panelTableChild, mapParam);
					panelTableColumn.getChildren().add(panelTableChild);
				}
				DevEntityPropertyDescriptor devEntityPropertyDescriptor = propertySelectedList.get(i);
				UIComponent panelTableRow = ComponentFactory.componentClone(panel, false);
				mapParam.clear();
				mapParam.put("style", "display:table-row");
				generateComponentStyleClassName(mapParam, "form", entitySelected, devEntityPropertyDescriptor, "");
				ComponentFactory.updateElementProperties(panelTableRow, mapParam);
				panelTableChild.getChildren().add(panelTableRow);
				Map<String, String> configMap = new HashMap<String, String>();

				mapParam.clear();
				// Map<String, Object> mapParam = new HashMap<String, Object>();
				try {
					input = null;
					buttom = null;
					String parent = (parentContainer != null && !parentContainer.isEmpty()) ? parentContainer : "form";
					// Object styleClass;
					input = createInputComponent(elementPanel, entitySelected, devEntityPropertyDescriptor, configMap, parentEntity, appType, parent);
					// styleClass = getProperty(input, "styleClass");

					String node = "";
					if (!parentEntity.isEmpty()) {
						node = parentEntity + ".";
					}
					boolean isButtonInputChildren = input instanceof javax.faces.component.html.HtmlCommandButton && devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN");
					if (input != null && !isButtonInputChildren) {
						UIComponent panelTableCellIcon = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(panelTableCellIcon, mapParam);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						mapParam.put("value", "");
						mapParam.put("styleClass", "ui-icon-sortable");
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(labelCloned, mapParam);
						panelTableCellIcon.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellIcon);

						UIComponent panelTableCellLabel = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(panelTableCellLabel, mapParam);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(labelCloned, mapParam);
						panelTableCellLabel.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellLabel);

						UIComponent panelTableCellInput = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;min-width:200px");
						ComponentFactory.updateElementProperties(panelTableCellInput, mapParam);

						panelTableCellInput.getChildren().add(input);
						panelTableRow.getChildren().add(panelTableCellInput);
						tabindex++;
					} else if (input != null && isButtonInputChildren) {
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-form") && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								fieldSet = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-list") && Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								table = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						// String parent = (parentContainer != null &&
						// !parentContainer.isEmpty()) ? parentContainer :
						// "form";
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
						mapParam.clear();
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel() + " (#{app.count('" + String.valueOf(node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName()).toLowerCase() + "')})");
							mapParam.put("action", "#{app.doList('" + parent + "-list-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						} else {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
							mapParam.put("action", "#{app.doList('" + parent + "-form-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						}
						mapParam.put("update", "@form");
						mapParam.put("immediate", "false");
						ComponentFactory.updateElementProperties(input, mapParam);
						if (panelInternalObject == null) {
							panelInternalObject = ComponentFactory.componentClone(fieldSet, false);
							mapParam.clear();
							mapParam.put("legend", "");
							ComponentFactory.updateElementProperties(panelInternalObject, mapParam);
						}
						panelInternalObject.getChildren().add(input);

						algoContainerCloned = ComponentFactory.componentClone(algoContainer, false);
						UIComponent containerChildClone;
						// String prefix = (parentContainer != null &&
						// !parentContainer.isEmpty()) ? parentContainer + "-" :
						// "form-";
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							containerChildClone = ComponentFactory.componentClone(containerChild, false);
							containerChildClone.getChildren().clear();
							containerChildClone.getChildren().add(table);
							algoContainerCloned.getChildren().clear();
							algoContainerCloned.getChildren().add(containerChildClone);
							String parentList = parent + "-list-" + ent.getName();
							new ComponentFactory(table, elementPanel, parentList, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
							elementsContainerMap.put(parentList, new ArrayList<UIComponent>(containerChildClone.getChildren()));
						}
						containerChildClone = ComponentFactory.componentClone(containerChild, false);
						containerChildClone.getChildren().clear();
						containerChildClone.getChildren().add(fieldSet);
						algoContainerCloned.getChildren().clear();
						algoContainerCloned.getChildren().add(containerChildClone);
						String parentForm = parent + "-form-" + ent.getName();
						new ComponentFactory(fieldSet, elementPanel, parentForm, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") ? AppType.FORM : appType)).getComponentCreated();
						elementsContainerMap.put(parentForm, new ArrayList<UIComponent>(containerChildClone.getChildren()));
						mapParam.clear();
						mapParam.put("legend", "");
						// mapParam.put("styleClass", "");
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet, mapParam, null);
						mapParam.clear();
						int colSise = BigDecimal.valueOf(ent.getEntityPropertyDescriptorList().size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
						mapParam.put("columns", String.valueOf(colSise));
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet.getC hildren().get(0), mapParam, null);
					} else if (tab != null) {
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();

						mapParam.put("title", devEntityPropertyDescriptor.getPropertyLabel());
						ComponentFactory.updateElementProperties(tab, mapParam);
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getChildren().get(0).getClass())) {
									fieldSet = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIColumn").isAssignableFrom(child.getChildren().get(0).getClass())) {
									table = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						tab.getChildren().add(table);
						new ComponentFactory(table, elementPanel, "", parentEntity, elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
						/*
						 * tab.getChildren().add(fieldSet); new
						 * ComponentFactory(fieldSet, elementPanel, "",
						 * elementsContainerMap, ent,
						 * ent.getEntityPropertyDescriptorList
						 * ()).getComponentCreated(); List<UIComponent> comList
						 * = new ArrayList<UIComponent>();
						 * comList.add(fieldSet);
						 * elementsContainerMap.put("form-"+ent.getName(),
						 * comList); mapParam.clear(); mapParam.put("legend",
						 * ""); ComponentFactory.updateElementProperties(f2,
						 * fieldSet, mapParam, null); mapParam.clear();
						 * mapParam.put("columns", "4");
						 * ComponentFactory.updateElementProperties(f2,
						 * fieldSet.getChildren().get(0), mapParam, null);
						 */
					}
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
				}
				// propertyArrayStr +=
				// devEntityPropertyDescriptor.getPropertyName();
			}
			mapParam.clear();
			int colSise = BigDecimal.valueOf(propertySelectedList.size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
			mapParam.put("columns", String.valueOf(colSise));
			// ComponentFactory.updateElementProperties(f2, comp, mapParam,
			// null);
			mapParam.clear();
			mapParam.put("legend", "#{msg['registration']}: " + entitySelected.getLabel());
			// mapParam.put("styleClass", "");
			ComponentFactory.updateElementProperties(f2, comp, mapParam, null);
			if (panelInternalObject != null && !panelInternalObject.getChildren().isEmpty()) {
				comp.getParent().getChildren().add(panelInternalObject);
			}
			if (entitySelected.getEntityClassConfigList() != null && !entitySelected.getEntityClassConfigList().isEmpty()) {
				Map<String, String> entConfigMap = new HashMap<String, String>();
				for (DevEntityClassConfig entConfig : entitySelected.getEntityClassConfigList()) {
					entConfigMap.put(entConfig.getConfigName(), String.valueOf(entConfig.getConfigValue()));
				}
				if (entConfigMap.containsKey("loginAuth") && Boolean.valueOf(entConfigMap.get("loginAuth"))) {
					UIComponent fieldSet = null;
					UIComponent password = null;
					try {
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-form") && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								fieldSet = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						UIComponent pwlabel = ComponentFactory.componentClone(label, false);
						mapParam.clear();
						mapParam.put("value", String.valueOf(BaseBean.getBundle("password", "msg")).toUpperCase() + ": ");
						ComponentFactory.updateElementProperties(pwlabel, mapParam);
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("ui-password")) {
								password = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						mapParam.clear();
						mapParam.put("validator", "#{app.validateLogin}");
						mapParam.put("value", "");
						mapParam.put("redisplay", "false");
						mapParam.put("autocomplete", "off");
						mapParam.put("required", true);
						mapParam.put("requiredMessage", "#{msg.required}: " + String.valueOf(BaseBean.getBundle("password", "msg")).toUpperCase());
						mapParam.put("tabindex", String.valueOf("1"));
						ComponentFactory.updateElementProperties(f2, password, mapParam, null);
						if (panelAuth == null) {
							panelAuth = ComponentFactory.componentClone(fieldSet, false);
							mapParam.clear();
							mapParam.put("legend", "");
							ComponentFactory.updateElementProperties(panelAuth, mapParam);
						}
						panelAuth.getChildren().add(pwlabel);
						panelAuth.getChildren().add(password);
						comp.getParent().getChildren().add(panelAuth);
					} catch (ClassNotFoundException ex) {
						Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			doUICommandButtonFormFactory(comp.getParent(), elementPanel, entitySelected, parentContainer, parentEntity, appType);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void doUIDataGridFactory(UIComponent comp, UIComponent elementPanel, String parentContainer, String parentEntity, Map<String, List<UIComponent>> elementsContainerMap, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		try {
			UIComponent column = null;
			for (UIComponent child : comp.getChildren()) {
				if (Class.forName("javax.faces.component.UIColumn").isAssignableFrom(child.getClass())) {
					column = child;
					break;
				}
			}
			String propertyArrayStr = "";
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			UIComponent labelCloned = null;
			UIComponent inputCloned = null;
			UIComponent label = null;
			UIComponent input = null;
			UIComponent image = null;
			UIComponent tab = null;
			UIComponent containerChild = ComponentFactory.componentClone(comp.getParent(), false);
			containerChild.getChildren().clear();
			UIComponent algoContainer = ComponentFactory.componentClone(comp.getParent().getParent(), false);
			algoContainer.getChildren().clear();
			UIComponent algoContainerCloned = null;
			UIComponent panelInternalObject = null;
			UIComponent panelAuth = null;
			// Map<String, Object> mapParam1 = new HashMap<String, Object>();
			// mapParam1.put("legend", "");
			// ComponentFactory.updateElementProperties(f2, containerChild,
			// mapParam1, null);
			int tabindex = 1;
			UIComponent buttom = null;
			UIComponent panel = null;
			UIComponent uipanel = null;
			UIComponent output = null;

			try {
				Object styleClass = null;
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("data-panel")) {
						panel = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					if (Class.forName("javax.faces.component.html.HtmlOutputLabel").isAssignableFrom(child.getClass())) {
						label = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("ui-panel")) {
						uipanel = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("data-output")) {
						output = child;
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
			}
			Map<String, Object> mapParam = new HashMap<String, Object>();
			mapParam.clear();
			mapParam.put("headerText", "");
			mapParam.put("sortBy", "toString()");
			mapParam.put("filterBy", "toString()");
			mapParam.put("filterMatchMode", "contains");
			mapParam.put("filterStyle", "display:none;");
			ComponentFactory.updateElementProperties(f2, column, mapParam, null);
			UIComponent panelTableParent = null;
			panelTableParent = ComponentFactory.componentClone(panel, false);
			mapParam.clear();
			mapParam.put("style", "display:table");
			mapParam.put("layout", "block");
			ComponentFactory.updateElementProperties(f2, panelTableParent, mapParam, null);
			// uipanel.getChildren().add(panelTableParent);
			column.getChildren().clear();
			column.getChildren().add(panelTableParent);
			// comp.getChildren().add(panelTableParent);
			UIComponent panelTableChild = null;
			for (int i = 0; i < propertySelectedList.size(); i++) {
				if (i % 7 == 0) {
					UIComponent panelTableColumn = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table-cell;");
					// mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(f2, panelTableColumn, mapParam, null);
					panelTableParent.getChildren().add(panelTableColumn);

					panelTableChild = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table");
					mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(f2, panelTableChild, mapParam, null);
					panelTableColumn.getChildren().add(panelTableChild);
				}
				UIComponent panelTableRow = ComponentFactory.componentClone(panel, false);
				mapParam.clear();
				mapParam.put("style", "display:table-row");
				ComponentFactory.updateElementProperties(f2, panelTableRow, mapParam, null);
				panelTableChild.getChildren().add(panelTableRow);
				DevEntityPropertyDescriptor devEntityPropertyDescriptor = propertySelectedList.get(i);
				Map<String, String> configMap = new HashMap<String, String>();
				if (devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null) {
					for (DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfig : devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList()) {
						if (!String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).isEmpty() && !String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).equals("null")) {
							configMap.put(devEntityPropertyDescriptorConfig.getConfigName(), devEntityPropertyDescriptorConfig.getConfigValue());
						}
					}
				}
				mapParam.clear();
				// Map<String, Object> mapParam = new HashMap<String, Object>();
				try {
					input = null;
					buttom = null;
					Object styleClass = null;
					for (UIComponent child : elementPanel.getChildren()) {
						styleClass = getProperty(child, "styleClass");
						if (styleClass != null && String.valueOf(styleClass).contains("ui-integer")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")) {
								input = child;
								if (!configMap.containsKey("precision")) {
									configMap.put("precision", "2");
								}
								break;
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-mask")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.containsKey("mask")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.isEmpty()) {
								input = child;
								break;
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-textArea")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.containsKey("length")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlSelectBooleanCheckbox").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
								input = child;
								break;
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("data-image")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FILE")) {
								UIComponent imageCloned = ComponentFactory.componentClone(child, false);
								String propValObj = "app.prepareStreamedContent(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item))";
								// String str = "#{" + propValObj +
								// ".val==null?msg['select']:" + propValObj +
								// ".val}";
								// String str = "#{" + propValObj + "}";
								String str = "#{app.getFile(app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item))}";
								mapParam.put("value", str);
								mapParam.put("height", "100");
								mapParam.put("width", "150");
								ComponentFactory.updateElementProperties(f2, imageCloned, mapParam, null);
								String action = str;// "#{app.getFileDownload("
													// + getImgRandon() + ")}";
								ValueExpression value = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(), action, Object.class);
								// imageCloned.setValueExpression("value",
								// value);
								Object obj = imageCloned.getValueExpression("value");
								panelTableRow.getChildren().add(imageCloned);
								break;
							}
						}
						if (false && Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FILE")) {
								input = child;
								// mapParam.put("update", "@form");
								mapParam.put("immediate", "true");
								String propValObj = "app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')";
								String str = "#{" + propValObj + ".val==null?msg['select']:" + propValObj + ".val}";
								mapParam.put("value", str);
								mapParam.put("action", "#{app.prepareUpload(" + propValObj + ")}");
								mapParam.put("oncomplete", "(handleFileUploadDlg(xhr, status, args))");
								// mapParam.put("onclick",
								// "function () {uploadFileWv.show();return true;}");
								// mapParam.put("onclick",
								// "javascript:(function(){uploadFileWv.show();return false;})");
								ComponentFactory.updateElementProperties(f2, input, mapParam, null);
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS") && devEntityPropertyDescriptor.getPropertyClass() != null) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
							if ((devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") || devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) && devEntityPropertyDescriptor.getPropertyClass() != null) {
								buttom = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						if (false && Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN") && devEntityPropertyDescriptor.getPropertyClass() != null) {
								if (Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getChildren().get(0).getClass())) {
									if (containerChild == null) {
										containerChild = ComponentFactory.componentClone(child, false);
										tab = containerChild.getChildren().get(0);
										comp.getParent().getParent().getChildren().add(containerChild);
									} else {
										tab = ComponentFactory.componentClone(containerChild.getChildren().get(0), false);
										containerChild.getChildren().add(tab);
									}
									break;
								}
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-calendar")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
								input = child;
								break;
							}
						}
					}
					String node = "";
					if (!parentEntity.isEmpty()) {
						node = parentEntity + ".";
					}
					if (input != null) {
						UIComponent panelTableCellIcon = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(f2, panelTableCellIcon, mapParam, null);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						mapParam.put("value", "");
						mapParam.put("styleClass", "ui-icon-sortable");
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(f2, labelCloned, mapParam, null);
						panelTableCellIcon.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellIcon);

						UIComponent panelTableCellLabel = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(f2, panelTableCellLabel, mapParam, null);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						if (!configMap.containsKey("xhtml") || configMap.get("xhtml").equals("false")) {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
						} else {
							mapParam.put("value", "");
						}
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(f2, labelCloned, mapParam, null);
						panelTableCellLabel.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellLabel);

						UIComponent panelTableCellInput = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;");
						ComponentFactory.updateElementProperties(f2, panelTableCellInput, mapParam, null);
						UIComponent outputCloned = ComponentFactory.componentClone(output, false);
						// inputCloned = ComponentFactory.componentClone(input,
						// false);
						mapParam.clear();
						if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(input.getClass())) {
							DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
							new ComponentFactory(inputCloned, elementPanel, "value", entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType);
							mapParam.put("converter", "EntityObjectConverter");
						}
						// node += entitySelected.getName() + "." +
						// devEntityPropertyDescriptor.getPropertyName();
						String str = "";
						if (outputCloned.getValueExpression("value") == null) {
							if (!parentEntity.isEmpty()) {
								str = "#{app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',app.childBean).val}";
							} else {
								// str = "#{app.$('" + entitySelected.getName()
								// + "." +
								// devEntityPropertyDescriptor.getPropertyName()
								// + "').val}";
								str = "#{app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item).val}";
							}
							mapParam.put("value", str);
						}
						// mapParam.put("styleClass", "");
						mapParam.put("required", devEntityPropertyDescriptor.isPropertyRequired());
						mapParam.put("requiredMessage", "#{msg.required}: " + devEntityPropertyDescriptor.getPropertyLabel());
						// mapParam.put("tabindex", String.valueOf(tabindex));
						mapParam.put("tabindex", String.valueOf("1"));
						if (configMap.containsKey("mask")) {
							mapParam.put("mask", configMap.get("mask"));
						}
						if (configMap.containsKey("precision")) {
							mapParam.put("styleClass", String.valueOf(styleClass).concat(" decimalFormat".concat(configMap.get("precision"))));
						}
						if (configMap.containsKey("length")) {
							// mapParam.put("mask", configMap.get("mask"));
						}
						if (configMap.containsKey("pattern")) {
							mapParam.put("pattern", configMap.get("pattern"));
							mapParam.put("styleClass", String.valueOf(styleClass).replaceAll(" dateFormat", "").concat(" timeFormat"));
							if (configMap.get("pattern").equals("HH:mm")) {
								mapParam.put("timeOnly", true);
							}
						}
						mapParam.put("escape", false);
						// setValue(inputCloned, "value", str);
						ComponentFactory.updateElementProperties(f2, outputCloned, mapParam, null);
						panelTableCellInput.getChildren().add(outputCloned);
						panelTableRow.getChildren().add(panelTableCellInput);
						tabindex++;
					} else if (buttom != null) {
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-form") && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								fieldSet = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-list") && Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								table = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						String parent = (parentContainer != null && !parentContainer.isEmpty()) ? parentContainer : "form";
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
						mapParam.clear();
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel() + " (#{app.count('" + String.valueOf(node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName()).toLowerCase() + "')})");
							mapParam.put("action", "#{app.doList('" + parent + "-list-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						} else {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
							mapParam.put("action", "#{app.doList('" + parent + "-form-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						}
						mapParam.put("update", "@form");
						mapParam.put("immediate", "false");
						ComponentFactory.updateElementProperties(f2, buttom, mapParam, null);
						if (panelInternalObject == null) {
							panelInternalObject = ComponentFactory.componentClone(fieldSet, false);
							mapParam.clear();
							mapParam.put("legend", "");
							ComponentFactory.updateElementProperties(f2, panelInternalObject, mapParam, null);
						}
						panelInternalObject.getChildren().add(buttom);

						algoContainerCloned = ComponentFactory.componentClone(algoContainer, false);
						UIComponent containerChildClone;
						// String prefix = (parentContainer != null &&
						// !parentContainer.isEmpty()) ? parentContainer + "-" :
						// "form-";
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							containerChildClone = ComponentFactory.componentClone(containerChild, false);
							containerChildClone.getChildren().clear();
							containerChildClone.getChildren().add(table);
							algoContainerCloned.getChildren().clear();
							algoContainerCloned.getChildren().add(containerChildClone);
							new ComponentFactory(table, elementPanel, parent, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
							elementsContainerMap.put(parent + "-list-" + ent.getName(), containerChildClone.getChildren());
						}
						containerChildClone = ComponentFactory.componentClone(containerChild, false);
						containerChildClone.getChildren().clear();
						containerChildClone.getChildren().add(fieldSet);
						algoContainerCloned.getChildren().clear();
						algoContainerCloned.getChildren().add(containerChildClone);
						new ComponentFactory(fieldSet, elementPanel, parent, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") ? AppType.FORM : appType)).getComponentCreated();
						elementsContainerMap.put(parent + "-form-" + ent.getName(), containerChildClone.getChildren());
						mapParam.clear();
						mapParam.put("legend", "");
						// mapParam.put("styleClass", "");
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet, mapParam, null);
						mapParam.clear();
						int colSise = BigDecimal.valueOf(ent.getEntityPropertyDescriptorList().size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
						mapParam.put("columns", String.valueOf(colSise));
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet.getC hildren().get(0), mapParam, null);

					} else if (tab != null) {
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
						mapParam.put("title", devEntityPropertyDescriptor.getPropertyLabel());
						ComponentFactory.updateElementProperties(f2, tab, mapParam, null);
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getChildren().get(0).getClass())) {
									fieldSet = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIColumn").isAssignableFrom(child.getChildren().get(0).getClass())) {
									table = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						tab.getChildren().add(table);
						new ComponentFactory(table, elementPanel, "", parentEntity, elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
						/*
						 * tab.getChildren().add(fieldSet); new
						 * ComponentFactory(fieldSet, elementPanel, "",
						 * elementsContainerMap, ent,
						 * ent.getEntityPropertyDescriptorList
						 * ()).getComponentCreated(); List<UIComponent> comList
						 * = new ArrayList<UIComponent>();
						 * comList.add(fieldSet);
						 * elementsContainerMap.put("form-"+ent.getName(),
						 * comList); mapParam.clear(); mapParam.put("legend",
						 * ""); ComponentFactory.updateElementProperties(f2,
						 * fieldSet, mapParam, null); mapParam.clear();
						 * mapParam.put("columns", "4");
						 * ComponentFactory.updateElementProperties(f2,
						 * fieldSet.getChildren().get(0), mapParam, null);
						 */
					}
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
				}
				// propertyArrayStr +=
				// devEntityPropertyDescriptor.getPropertyName();
			}
			mapParam.clear();
			int colSise = BigDecimal.valueOf(propertySelectedList.size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
			mapParam.put("columns", String.valueOf(colSise));
			// ComponentFactory.updateElementProperties(f2, comp, mapParam,
			// null);
			// mapParam.clear();
			// mapParam.put("header", "#{msg['registration']}: " +
			// entitySelected.getLabel());
			// mapParam.put("styleClass", "");
			// ComponentFactory.updateElementProperties(f2, comp, mapParam,
			// null);

			String node = "";
			if (!parentEntity.isEmpty()) {
				node = parentEntity + ".";
			}
			String str = "#{app.beanListMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str3 = "#{app.beanListFilteredMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str2 = "#{app.beanSel}";
			mapParam.clear();
			mapParam.put("value", str);
			mapParam.put("rowKey", "#{item.hashCode()}");
			mapParam.put("filteredValue", str3);
			// mapParam.put("selection", str2);
			// mapParam.put("selectionMode", "single");
			mapParam.put("paginator", "true");
			mapParam.put("rows", "2");
			mapParam.put("sortMode", "multiple");
			mapParam.put("tableStyle", "width:990px;");
			mapParam.put("style", "width:990px;");
			mapParam.put("scrollable", "true");
			mapParam.put("scrollWidth", "1010");
			mapParam.put("scrollHeight", "280");
			mapParam.put("widgetVar", entitySelected.getName() + "DataTable");

			componentCreated = comp;
			ComponentFactory.updateElementProperties(f2, componentCreated, mapParam, null);

			if (panelInternalObject != null && !panelInternalObject.getChildren().isEmpty()) {
				comp.getParent().getChildren().add(panelInternalObject);
			}
			if (componentCreated.getFacets().containsKey("footer")) {
				UIComponent export = componentCreated.getFacets().get("footer");
				if (export instanceof UICommand) {
					mapParam.clear();
					mapParam.put("value", "");
					mapParam.put("action", "");
					ComponentFactory.updateElementProperties(f2, export, mapParam, null);
				}
			}
			if (componentCreated.getFacets().containsKey("header")) {
				UIComponent labelCloned2 = null;
				UIComponent label2Cloned = null;
				UIComponent inputCloned2 = null;
				UIComponent panelCloned = null;
				UIComponent panelCloned2 = null;

				panelCloned = ComponentFactory.componentClone(findComponentByStyleClass("ui-panel", elementPanel), false);
				mapParam.clear();
				mapParam.put("header", "");
				mapParam.put("legend", "");
				ComponentFactory.updateElementProperties(f2, panelCloned, mapParam, null);
				for (UIComponent child : elementPanel.getChildren()) {

					if (panelCloned2 == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						// panelCloned2 = ComponentFactory.componentClone(
						// child, context);
						mapParam.clear();
						// mapParam.put("header", "");
						// ComponentFactory.updateElementProperties(f2,
						// panelCloned2, mapParam, null);
					}
					if (labelCloned2 == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						labelCloned2 = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("value", "#{msg['listing']}: " + entitySelected.getLabel());
						ComponentFactory.updateElementProperties(f2, labelCloned2, mapParam, null);
					}
					if (label2Cloned == null && child instanceof javax.faces.component.html.HtmlOutputLabel) {
						label2Cloned = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("value", "#{msg['search']}: ");
						mapParam.put("style", "float:right;top:-5px;position: relative;");
						ComponentFactory.updateElementProperties(f2, label2Cloned, mapParam, null);
					}
					if (inputCloned2 == null && child instanceof javax.faces.component.html.HtmlInputText) {
						inputCloned2 = ComponentFactory.componentClone(child, false);
						mapParam.clear();
						mapParam.put("onkeyup", entitySelected.getName() + "DataTable" + ".filter()");
						// mapParam.put("style",
						// "float:right;position:relative;top:-20% !important;");
						ComponentFactory.updateElementProperties(f2, inputCloned2, mapParam, null);
						inputCloned2.setId("globalFilter");
					}
				}

				panelCloned.getChildren().add(labelCloned2);
				panelCloned.getChildren().add(label2Cloned);
				label2Cloned.getChildren().add(inputCloned2);

				componentCreated.getFacets().put("header", panelCloned);

			}
			// doUICommandButtonFormFactory(comp.getParent(), elementPanel,
			// entitySelected, parentContainer, parentEntity, appType);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void doUICarouselFactory(UIComponent comp, UIComponent elementPanel, String parentContainer, String parentEntity, Map<String, List<UIComponent>> elementsContainerMap, DevEntityClass entitySelected, List<DevEntityPropertyDescriptor> propertySelectedList, AppType appType) {
		try {
			UIComponent column = null;
			for (UIComponent child : comp.getChildren()) {
				if (Class.forName("javax.faces.component.UIColumn").isAssignableFrom(child.getClass())) {
					column = child;
					break;
				}
			}
			String propertyArrayStr = "";
			FacesContext context = FacesContext.getCurrentInstance();
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			UIComponent labelCloned = null;
			UIComponent inputCloned = null;
			UIComponent label = null;
			UIComponent input = null;
			UIComponent tab = null;
			UIComponent containerChild = ComponentFactory.componentClone(comp.getParent(), false);
			containerChild.getChildren().clear();
			UIComponent algoContainer = ComponentFactory.componentClone(comp.getParent().getParent(), false);
			algoContainer.getChildren().clear();
			UIComponent algoContainerCloned = null;
			UIComponent panelInternalObject = null;
			UIComponent panelAuth = null;
			// Map<String, Object> mapParam1 = new HashMap<String, Object>();
			// mapParam1.put("legend", "");
			// ComponentFactory.updateElementProperties(f2, containerChild,
			// mapParam1, null);
			int tabindex = 1;
			UIComponent buttom = null;
			UIComponent panel = null;
			UIComponent uipanel = null;
			try {
				Object styleClass = null;
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("data-panel")) {
						panel = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					if (Class.forName("javax.faces.component.html.HtmlOutputLabel").isAssignableFrom(child.getClass())) {
						label = child;
						break;
					}
				}
				for (UIComponent child : elementPanel.getChildren()) {
					styleClass = getProperty(child, "styleClass");
					if (styleClass != null && String.valueOf(styleClass).contains("ui-panel")) {
						uipanel = child;
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
			}
			Map<String, Object> mapParam = new HashMap<String, Object>();
			UIComponent panelTableParent = null;
			panelTableParent = ComponentFactory.componentClone(panel, false);
			mapParam.clear();
			mapParam.put("style", "display:table");
			mapParam.put("layout", "block");
			ComponentFactory.updateElementProperties(f2, panelTableParent, mapParam, null);
			// uipanel.getChildren().add(panelTableParent);
			// column.getChildren().add(uipanel);
			comp.getChildren().add(panelTableParent);
			UIComponent panelTableChild = null;
			for (int i = 0; i < propertySelectedList.size(); i++) {
				if (i % 7 == 0) {
					UIComponent panelTableColumn = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table-cell;");
					// mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(f2, panelTableColumn, mapParam, null);
					panelTableParent.getChildren().add(panelTableColumn);

					panelTableChild = ComponentFactory.componentClone(panel, false);
					mapParam.clear();
					mapParam.put("style", "display:table");
					mapParam.put("styleClass", "sortable connectedSortable");
					// mapParam.put("layout", "block");
					ComponentFactory.updateElementProperties(f2, panelTableChild, mapParam, null);
					panelTableColumn.getChildren().add(panelTableChild);
				}
				UIComponent panelTableRow = ComponentFactory.componentClone(panel, false);
				mapParam.clear();
				mapParam.put("style", "display:table-row");
				ComponentFactory.updateElementProperties(f2, panelTableRow, mapParam, null);
				panelTableChild.getChildren().add(panelTableRow);
				DevEntityPropertyDescriptor devEntityPropertyDescriptor = propertySelectedList.get(i);
				Map<String, String> configMap = new HashMap<String, String>();
				if (devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList() != null) {
					for (DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfig : devEntityPropertyDescriptor.getEntityPropertyDescriptorConfigList()) {
						if (!String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).isEmpty() && !String.valueOf(devEntityPropertyDescriptorConfig.getConfigName()).equals("null")) {
							configMap.put(devEntityPropertyDescriptorConfig.getConfigName(), devEntityPropertyDescriptorConfig.getConfigValue());
						}
					}
				}
				mapParam.clear();
				// Map<String, Object> mapParam = new HashMap<String, Object>();
				try {
					input = null;
					buttom = null;
					Object styleClass = null;
					for (UIComponent child : elementPanel.getChildren()) {
						styleClass = getProperty(child, "styleClass");
						if (styleClass != null && String.valueOf(styleClass).contains("ui-integer")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")) {
								input = child;
								if (!configMap.containsKey("precision")) {
									configMap.put("precision", "2");
								}
								break;
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-mask")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.containsKey("mask")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlInputText").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.isEmpty()) {
								input = child;
								break;
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-textArea")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING") && configMap.containsKey("length")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlSelectBooleanCheckbox").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FILE")) {
								input = child;
								// mapParam.put("update", "@form");
								mapParam.put("immediate", "true");
								String propValObj = "app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')";
								String str = "#{" + propValObj + ".val==null?msg['select']:" + propValObj + ".val}";
								mapParam.put("value", str);
								mapParam.put("action", "#{app.prepareUpload(" + propValObj + ")}");
								mapParam.put("oncomplete", "(handleFileUploadDlg(xhr, status, args))");
								// mapParam.put("onclick",
								// "function () {uploadFileWv.show();return true;}");
								// mapParam.put("onclick",
								// "javascript:(function(){uploadFileWv.show();return false;})");
								ComponentFactory.updateElementProperties(f2, input, mapParam, null);
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS") && devEntityPropertyDescriptor.getPropertyClass() != null) {
								input = child;
								break;
							}
						}
						if (Class.forName("javax.faces.component.html.HtmlCommandButton").isAssignableFrom(child.getClass())) {
							if ((devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") || devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) && devEntityPropertyDescriptor.getPropertyClass() != null) {
								buttom = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						if (false && Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN") && devEntityPropertyDescriptor.getPropertyClass() != null) {
								if (Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getChildren().get(0).getClass())) {
									if (containerChild == null) {
										containerChild = ComponentFactory.componentClone(child, false);
										tab = containerChild.getChildren().get(0);
										comp.getParent().getParent().getChildren().add(containerChild);
									} else {
										tab = ComponentFactory.componentClone(containerChild.getChildren().get(0), false);
										containerChild.getChildren().add(tab);
									}
									break;
								}
							}
						}
						if (styleClass != null && String.valueOf(styleClass).contains("ui-calendar")) {
							if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
								input = child;
								break;
							}
						}
					}
					String node = "";
					if (!parentEntity.isEmpty()) {
						node = parentEntity + ".";
					}
					if (input != null) {
						UIComponent panelTableCellIcon = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(f2, panelTableCellIcon, mapParam, null);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						mapParam.put("value", "");
						mapParam.put("styleClass", "ui-icon-sortable");
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(f2, labelCloned, mapParam, null);
						panelTableCellIcon.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellIcon);

						UIComponent panelTableCellLabel = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;vertical-align:middle;");
						ComponentFactory.updateElementProperties(f2, panelTableCellLabel, mapParam, null);
						mapParam.clear();
						labelCloned = ComponentFactory.componentClone(label, false);
						mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
						// mapParam.put("styleClass", "");
						ComponentFactory.updateElementProperties(f2, labelCloned, mapParam, null);
						panelTableCellLabel.getChildren().add(labelCloned);
						panelTableRow.getChildren().add(panelTableCellLabel);

						UIComponent panelTableCellInput = ComponentFactory.componentClone(panel, false);
						mapParam.clear();
						mapParam.put("style", "display:table-cell;padding:5px;");
						ComponentFactory.updateElementProperties(f2, panelTableCellInput, mapParam, null);
						inputCloned = ComponentFactory.componentClone(input, false);
						mapParam.clear();
						if (Class.forName("javax.faces.component.html.HtmlSelectOneMenu").isAssignableFrom(input.getClass())) {
							DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
							new ComponentFactory(inputCloned, elementPanel, "value", entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType);
							mapParam.put("converter", "EntityObjectConverter");
						}
						// node += entitySelected.getName() + "." +
						// devEntityPropertyDescriptor.getPropertyName();
						String str = "";
						if (inputCloned.getValueExpression("value") == null) {
							if (!parentEntity.isEmpty()) {
								str = "#{app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',app.childBean).val}";
							} else {
								// str = "#{app.$('" + entitySelected.getName()
								// + "." +
								// devEntityPropertyDescriptor.getPropertyName()
								// + "').val}";
								str = "#{app.$('" + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "',item).val}";
							}
							mapParam.put("value", str);
						}
						// mapParam.put("styleClass", "");
						mapParam.put("required", devEntityPropertyDescriptor.isPropertyRequired());
						mapParam.put("requiredMessage", "#{msg.required}: " + devEntityPropertyDescriptor.getPropertyLabel());
						// mapParam.put("tabindex", String.valueOf(tabindex));
						mapParam.put("tabindex", String.valueOf("1"));
						if (configMap.containsKey("mask")) {
							mapParam.put("mask", configMap.get("mask"));
						}
						if (configMap.containsKey("precision")) {
							mapParam.put("styleClass", String.valueOf(styleClass).concat(" decimalFormat".concat(configMap.get("precision"))));
						}
						if (configMap.containsKey("length")) {
							// mapParam.put("mask", configMap.get("mask"));
						}
						if (configMap.containsKey("pattern")) {
							mapParam.put("pattern", configMap.get("pattern"));
							mapParam.put("styleClass", String.valueOf(styleClass).replaceAll(" dateFormat", "").concat(" timeFormat"));
							if (configMap.get("pattern").equals("HH:mm")) {
								mapParam.put("timeOnly", true);
							}
						}
						mapParam.put("disabled", true);
						// setValue(inputCloned, "value", str);
						ComponentFactory.updateElementProperties(f2, inputCloned, mapParam, null);
						panelTableCellInput.getChildren().add(inputCloned);
						panelTableRow.getChildren().add(panelTableCellInput);
						tabindex++;
					} else if (buttom != null) {
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-form") && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								fieldSet = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-list") && Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								table = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						String parent = (parentContainer != null && !parentContainer.isEmpty()) ? parentContainer : "form";
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
						mapParam.clear();
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel() + " (#{app.count('" + String.valueOf(node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName()).toLowerCase() + "')})");
							mapParam.put("action", "#{app.doList('" + parent + "-list-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						} else {
							mapParam.put("value", devEntityPropertyDescriptor.getPropertyLabel());
							mapParam.put("action", "#{app.doList('" + parent + "-form-" + ent.getName() + "','" + node + entitySelected.getName() + "." + devEntityPropertyDescriptor.getPropertyName() + "')}");
						}
						mapParam.put("update", "@form");
						mapParam.put("immediate", "false");
						ComponentFactory.updateElementProperties(f2, buttom, mapParam, null);
						if (panelInternalObject == null) {
							panelInternalObject = ComponentFactory.componentClone(fieldSet, false);
							mapParam.clear();
							mapParam.put("legend", "");
							ComponentFactory.updateElementProperties(f2, panelInternalObject, mapParam, null);
						}
						panelInternalObject.getChildren().add(buttom);

						algoContainerCloned = ComponentFactory.componentClone(algoContainer, false);
						UIComponent containerChildClone;
						// String prefix = (parentContainer != null &&
						// !parentContainer.isEmpty()) ? parentContainer + "-" :
						// "form-";
						if (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
							containerChildClone = ComponentFactory.componentClone(containerChild, false);
							containerChildClone.getChildren().clear();
							containerChildClone.getChildren().add(table);
							algoContainerCloned.getChildren().clear();
							algoContainerCloned.getChildren().add(containerChildClone);
							new ComponentFactory(table, elementPanel, parent, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
							elementsContainerMap.put(parent + "-list-" + ent.getName(), containerChildClone.getChildren());
						}
						containerChildClone = ComponentFactory.componentClone(containerChild, false);
						containerChildClone.getChildren().clear();
						containerChildClone.getChildren().add(fieldSet);
						algoContainerCloned.getChildren().clear();
						algoContainerCloned.getChildren().add(containerChildClone);
						new ComponentFactory(fieldSet, elementPanel, parent, entitySelected.getName(), elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), (devEntityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ONEINTERNALOBJECT") ? AppType.FORM : appType)).getComponentCreated();
						elementsContainerMap.put(parent + "-form-" + ent.getName(), containerChildClone.getChildren());
						mapParam.clear();
						mapParam.put("legend", "");
						// mapParam.put("styleClass", "");
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet, mapParam, null);
						mapParam.clear();
						int colSise = BigDecimal.valueOf(ent.getEntityPropertyDescriptorList().size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
						mapParam.put("columns", String.valueOf(colSise));
						// ComponentFactory.updateElementProperties(f2,
						// fieldSet.getC hildren().get(0), mapParam, null);

					} else if (tab != null) {
						DevEntityClass ent = devEntityPropertyDescriptor.getPropertyClass();
						mapParam.put("title", devEntityPropertyDescriptor.getPropertyLabel());
						ComponentFactory.updateElementProperties(f2, tab, mapParam, null);
						UIComponent fieldSet = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getChildren().get(0).getClass())) {
									fieldSet = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						UIComponent table = null;
						for (UIComponent child : elementPanel.getChildren()) {
							if (Class.forName("javax.faces.component.UIData").isAssignableFrom(child.getClass())) {
								if (!child.getChildren().isEmpty() && Class.forName("javax.faces.component.UIColumn").isAssignableFrom(child.getChildren().get(0).getClass())) {
									table = ComponentFactory.componentClone(child, false);
									break;
								}
							}
						}
						tab.getChildren().add(table);
						new ComponentFactory(table, elementPanel, "", parentEntity, elementsContainerMap, ent, ent.getEntityPropertyDescriptorList(), appType).getComponentCreated();
						/*
						 * tab.getChildren().add(fieldSet); new
						 * ComponentFactory(fieldSet, elementPanel, "",
						 * elementsContainerMap, ent,
						 * ent.getEntityPropertyDescriptorList
						 * ()).getComponentCreated(); List<UIComponent> comList
						 * = new ArrayList<UIComponent>();
						 * comList.add(fieldSet);
						 * elementsContainerMap.put("form-"+ent.getName(),
						 * comList); mapParam.clear(); mapParam.put("legend",
						 * ""); ComponentFactory.updateElementProperties(f2,
						 * fieldSet, mapParam, null); mapParam.clear();
						 * mapParam.put("columns", "4");
						 * ComponentFactory.updateElementProperties(f2,
						 * fieldSet.getChildren().get(0), mapParam, null);
						 */
					}
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
				}
				// propertyArrayStr +=
				// devEntityPropertyDescriptor.getPropertyName();
			}
			mapParam.clear();
			int colSise = BigDecimal.valueOf(propertySelectedList.size() / 8f).setScale(0, RoundingMode.UP).intValue() * 2;
			mapParam.put("columns", String.valueOf(colSise));
			// ComponentFactory.updateElementProperties(f2, comp, mapParam,
			// null);
			mapParam.clear();
			mapParam.put("header", "#{msg['registration']}: " + entitySelected.getLabel());
			// mapParam.put("styleClass", "");
			ComponentFactory.updateElementProperties(f2, comp, mapParam, null);

			String node = "";
			if (!parentEntity.isEmpty()) {
				node = parentEntity + ".";
			}
			String str = "#{app.beanListMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str3 = "#{app.beanListFilteredMap.get('" + String.valueOf(node + entitySelected.getName()).toLowerCase() + "')}";
			String str2 = "#{app.beanSel}";
			mapParam.clear();
			mapParam.put("value", str);
			mapParam.put("rowKey", "#{item.hashCode()}");
			mapParam.put("filteredValue", str3);
			// mapParam.put("selection", str2);
			// mapParam.put("selectionMode", "single");
			mapParam.put("paginator", "true");
			mapParam.put("rows", "10");
			mapParam.put("sortMode", "multiple");
			mapParam.put("tableStyle", "width:990px;");
			mapParam.put("style", "width:990px;");
			mapParam.put("scrollable", "true");
			mapParam.put("scrollWidth", "1010");
			mapParam.put("scrollHeight", "210");
			mapParam.put("widgetVar", entitySelected.getName() + "DataTable");

			componentCreated = comp;
			ComponentFactory.updateElementProperties(f2, componentCreated, mapParam, null);

			if (panelInternalObject != null && !panelInternalObject.getChildren().isEmpty()) {
				comp.getParent().getChildren().add(panelInternalObject);
			}
			if (entitySelected.getEntityClassConfigList() != null && !entitySelected.getEntityClassConfigList().isEmpty()) {
				Map<String, String> entConfigMap = new HashMap<String, String>();
				for (DevEntityClassConfig entConfig : entitySelected.getEntityClassConfigList()) {
					entConfigMap.put(entConfig.getConfigName(), String.valueOf(entConfig.getConfigValue()));
				}
				if (entConfigMap.containsKey("loginAuth") && Boolean.valueOf(entConfigMap.get("loginAuth"))) {
					UIComponent fieldSet = null;
					UIComponent password = null;
					try {
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("data-form") && Class.forName("javax.faces.component.UIPanel").isAssignableFrom(child.getClass())) {
								fieldSet = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						UIComponent pwlabel = ComponentFactory.componentClone(label, false);
						mapParam.clear();
						mapParam.put("value", String.valueOf(BaseBean.getBundle("password", "msg")).toUpperCase() + ": ");
						ComponentFactory.updateElementProperties(f2, pwlabel, mapParam, null);
						for (UIComponent child : elementPanel.getChildren()) {
							Object styleClassEntField = getProperty(child, "styleClass");
							if (styleClassEntField != null && String.valueOf(styleClassEntField).contains("ui-password")) {
								password = ComponentFactory.componentClone(child, false);
								break;
							}
						}
						mapParam.clear();
						mapParam.put("validator", "#{app.validateLogin}");
						mapParam.put("value", "");
						mapParam.put("redisplay", "false");
						mapParam.put("autocomplete", "off");
						mapParam.put("required", true);
						mapParam.put("requiredMessage", "#{msg.required}: " + String.valueOf(BaseBean.getBundle("password", "msg")).toUpperCase());
						mapParam.put("tabindex", String.valueOf("1"));
						ComponentFactory.updateElementProperties(f2, password, mapParam, null);
						if (panelAuth == null) {
							panelAuth = ComponentFactory.componentClone(fieldSet, false);
							mapParam.clear();
							mapParam.put("legend", "");
							ComponentFactory.updateElementProperties(f2, panelAuth, mapParam, null);
						}
						panelAuth.getChildren().add(pwlabel);
						panelAuth.getChildren().add(password);
						comp.getParent().getChildren().add(panelAuth);
					} catch (ClassNotFoundException ex) {
						Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			// doUICommandButtonFormFactory(comp.getParent(), elementPanel,
			// entitySelected, parentContainer, parentEntity, appType);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static UIComponent cloneComp(Field f2, UIComponent comp3, FacesContext context) {
		UIComponent comp2 = null;
		try {
			if (comp3 instanceof com.sun.faces.facelets.compiler.UIInstructions) {
				comp2 = new HtmlOutputText();
				((HtmlOutputText) comp2).setValue(comp3);
			} else {
				comp2 = comp3.getClass().newInstance();
			}

			HashMap<String, PropertyDescriptor> hm = (HashMap) f2.get(comp3);
			for (Map.Entry<String, PropertyDescriptor> object : hm.entrySet()) {
				String object1 = object.getKey();
				PropertyDescriptor object2 = object.getValue();
				if (object2.getReadMethod() != null) {
					try {
						object2.getReadMethod().setAccessible(true);
						Object o = object2.getReadMethod().invoke(comp3);
						if (object1.equals("children")) {
							List<UIComponent> childrenList = (List<UIComponent>) o;
							for (int i = 0; i < childrenList.size(); i++) {
								UIComponent comp3a = (UIComponent) childrenList.get(i);
								if (comp3a instanceof com.sun.faces.facelets.compiler.UIInstructions) {
									HtmlOutputText out = new HtmlOutputText();
									out.setValue(comp3a);
									comp3a = out;
								}
								comp2.getChildren().add(cloneComp(f2, comp3a, context));
							}
						} else if (object1.equals("facets")) {
							String x = "";
							for (Map.Entry<String, UIComponent> objectFacets : ((HashMap<String, UIComponent>) o).entrySet()) {
								String objectMap1 = objectFacets.getKey();
								UIComponent objectMap2 = objectFacets.getValue();
								if (objectMap2 instanceof com.sun.faces.facelets.compiler.UIInstructions) {
									HtmlOutputText out = new HtmlOutputText();
									out.setValue(objectMap2);
									objectMap2 = out;
								}
								comp2.getFacets().put(objectMap1, cloneComp(f2, objectMap2, context));
							}
						} else {
							Class c = object2.getReadMethod().getDeclaringClass();
							if (object2.getWriteMethod() != null) {
								if (o != null && !c.getName().equals("javax.faces.component.UIComponentBase") && !c.getName().equals("javax.faces.component.UIComponent") && !c.getName().equals("com.sun.faces.facelets.compiler.UILeaf")) {
									object2.getWriteMethod().invoke(comp2, o);
								}
							}
						}
					} catch (java.lang.reflect.InvocationTargetException e) {
						System.out.println("Exception:" + e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (comp2.getId() == null) {
				comp2.setId("ad_" + context.getViewRoot().createUniqueId());
			}
			comp2.setParent(null);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			return comp2;
		}
	}

	public static void updateElementProperties(UIComponent comp3, Map mapParam) {
		try {
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			updateElementProperties(f2, comp3, mapParam, null);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void updateElementProperties(Field f2, UIComponent comp3, Map mapParam, Map<String, String[]> elementPropertiesSelected) {
		try {
			if (elementPropertiesSelected == null) {
				elementPropertiesSelected = new TreeMap<String, String[]>();
			}
			elementPropertiesSelected.clear();
			HashMap<String, PropertyDescriptor> hm = (HashMap) f2.get(comp3);
			for (Map.Entry<String, PropertyDescriptor> object : hm.entrySet()) {
				String object1 = object.getKey();
				PropertyDescriptor object2 = object.getValue();
				if (object2.getReadMethod() != null) {
					try {
						object2.getReadMethod().setAccessible(true);
						Object o;
						ValueExpression valExp = comp3.getValueExpression(object1);
						if (valExp != null) {
							o = valExp;
						} else {
							try {
								o = object2.getReadMethod().invoke(comp3);
							} catch (java.lang.reflect.InvocationTargetException e) {
								continue;
							}
						}
						String str = "";
						if (object1.equals("children")) {
							List<UIComponent> childrenList = (List<UIComponent>) o;
							// for (int i = 0; i < childrenList.size(); i++) {
							// UIComponent comp3a = (UIComponent)
							// childrenList.get(i);
							// comp2.getChildren().add(cloneComp(f2, comp3a,
							// context, comp2));
							// /}
						} else {
							Class c = object2.getReadMethod().getDeclaringClass();
							if (object2.getWriteMethod() != null) {
								String className = object2.getWriteMethod().getParameterTypes()[0].getName();
								if (!c.getName().equals("javax.faces.component.UIComponentBase") && !c.getName().equals("javax.faces.component.UIComponent")) {
									Object param = mapParam.get(object1);
									if (object1.equals("tabindex") && !(Class.forName("javax.faces.component.UICommand").isAssignableFrom(comp3.getClass()) || Class.forName("javax.faces.component.UIInput").isAssignableFrom(comp3.getClass()))) {
										continue;
									}
									if (mapParam.containsKey(object1)) {
										String nameClass = object2.getWriteMethod().getParameterTypes()[0].getSimpleName();
										if (String.valueOf(param).isEmpty()) {
											o = null;
										} else {
											o = param;
										}
										try {
											comp3.setValueExpression(object1, (ValueExpression) null);
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}

										if (className.equals("javax.faces.component.UIComponent")) {
											if (o != null) {
												if (o instanceof UIComponent) {
												}
												HtmlOutputText out = new HtmlOutputText();
												out.setValue(o);
												o = out;
											} else {
												comp3.getFacets().remove(object1);
											}
											// elementPropertiesSelected.put(object1,
											// o);
											// continue;
										}
										if (o != null) {
											if (className.equals("javax.faces.convert.Converter")) {
												FacesContext facesContext = FacesContext.getCurrentInstance();
												if (String.valueOf(o).contains("#")) {
													o = (javax.faces.convert.Converter) facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(o), Converter.class).getValue(facesContext.getELContext());
													o = facesContext.getApplication().createConverter(o.getClass());
												} else {
													o = facesContext.getApplication().createConverter(String.valueOf(o));
												}
											} else if (className.equals("javax.faces.validator.Validator")) {
												FacesContext facesContext = FacesContext.getCurrentInstance();
												if (String.valueOf(o).contains("#")) {
													o = (javax.faces.validator.Validator) facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(o), Validator.class).getValue(facesContext.getELContext());
													// o =
													// facesContext.getApplication().createValidator(o.getClass());
												} else {
													o = facesContext.getApplication().createValidator(String.valueOf(o));
												}
											} else if (className.equals("javax.faces.el.MethodBinding")) {
												FacesContext facesContext = FacesContext.getCurrentInstance();
												if (object1.equals("validator")) {
													o = Application.class.getMethod("createMethodBinding", String.class, new Class<?>[] {}.getClass()).invoke(facesContext.getApplication(), String.valueOf(o), new Class<?>[] { FacesContext.class, UIComponent.class, Object.class });
												} else {
													o = Application.class.getMethod("createMethodBinding", String.class, new Class<?>[] {}.getClass()).invoke(facesContext.getApplication(), String.valueOf(o), new Class<?>[] {});
												}
												// value = new
												// MethodExpression();
											} else if (className.equals("javax.el.MethodExpression")) {
												FacesContext facesContext = FacesContext.getCurrentInstance();
												o = facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), String.valueOf(o), null, new Class<?>[] {});
											} else if (className.equals("java.util.List")) {
											} else if (String.valueOf(o).contains("#")) {
												FacesContext facesContext = FacesContext.getCurrentInstance();
												o = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(o), String.class);
												comp3.setValueExpression(object1, (ValueExpression) o);
											} else if (nameClass.equals("boolean")) {
												o = Boolean.valueOf(param.toString());
											} else if (nameClass.equals("int")) {
												o = Integer.valueOf(param.toString());
											} else if (nameClass.equals("double")) {
												o = Double.valueOf(param.toString());
											}
										}
										if (!(o instanceof ValueExpression)) {
											try {
												object2.getWriteMethod().invoke(comp3, o);
											} catch (java.lang.reflect.InvocationTargetException e) {
												String msg = e.getMessage();
											} catch (java.lang.IllegalArgumentException e) {
												String msg = e.getMessage();
											} catch (Throwable e) {
												e.printStackTrace();
											}
										}
									}
									str = getPropertyString(o, className);
									elementPropertiesSelected.put(object1, new String[] { str, className });
								}
							}
						}
					} catch (java.lang.reflect.InvocationTargetException e) {
						System.out.println("Exception:" + e.getMessage());
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
			if (mapParam instanceof java.util.AbstractMap) {
				mapParam.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UIComponent findComponentByStyleClass(String styleClass, UIComponent elementContainer) {
		Object styleClassObj = null;
		UIComponent returnComp = null;
		styleClassObj = ComponentFactory.getProperty(elementContainer, "styleClass");
		if (styleClassObj instanceof javax.el.ValueExpression) {
			styleClassObj = ((javax.el.ValueExpression) styleClassObj).getValue(FacesContext.getCurrentInstance().getELContext());
		}
		String[] styleArray = String.valueOf(styleClassObj).split(" ");
		String[] styleArrayArg = String.valueOf(styleClass).split(" ");
		boolean hasClass = true;
		if (styleClassObj != null) {
			for (int i = 0; i < styleArrayArg.length; i++) {
				String itemClass = styleArrayArg[i];
				if (!Arrays.asList(styleArray).contains(itemClass)) {
					hasClass = false;
					break;
				}

			}
			if (hasClass) {
				return elementContainer;
			}
		}
		for (UIComponent child : elementContainer.getChildren()) {
			returnComp = findComponentByStyleClass(styleClass, child);
			if (returnComp != null) {
				return returnComp;
			}
		}
		return null;
	}

	public static UIComponent findComponentByClassName(String className, UIComponent elementContainer) {
		UIComponent returnComp = null;
		try {
			if (Class.forName(className).isAssignableFrom(elementContainer.getClass())) {
				return elementContainer;
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (UIComponent child : elementContainer.getChildren()) {
			// styleClassObj = ComponentFactory.getProperty(child,
			// "styleClass");
			returnComp = findComponentByClassName(className, child);
			if (returnComp != null) {
				return returnComp;
			}
		}
		return null;
	}

	public static Object getProperty(UIComponent comp, String key) {
		Object o = null;
		try {
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			HashMap<String, PropertyDescriptor> hm = (HashMap) f2.get(comp);
			for (Map.Entry<String, PropertyDescriptor> object : hm.entrySet()) {
				String object1 = object.getKey();
				PropertyDescriptor object2 = object.getValue();
				if (object1.equals(key) && object2.getReadMethod() != null) {
					try {
						object2.getReadMethod().setAccessible(true);

						ValueExpression valExp = comp.getValueExpression(object1);
						if (valExp != null) {
							o = valExp;
						} else {
							o = object2.getReadMethod().invoke(comp);
						}
					} catch (Exception e) {
					}
					break;
				}
			}
		} catch (NoSuchFieldException ex) {
			// Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE,
			// null, ex);
		} catch (SecurityException ex) {
			// Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE,
			// null, ex);
		} catch (IllegalArgumentException ex) {
			// Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE,
			// null, ex);
		} catch (IllegalAccessException ex) {
			// Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE,
			// null, ex);
		}
		return o;
	}

	public static String getPropertyString(Object o, String className) {
		if (o != null) {
			try {
				if (className.equals("javax.faces.component.UIComponent")) {
					if (o.getClass().isAssignableFrom(HtmlOutputText.class)) {
						o = ((HtmlOutputText) o).getValue();
					}
				}
				if (className.equals("javax.faces.convert.Converter")) {
					o = ((javax.faces.convert.Converter) o).getClass().getAnnotation(FacesConverter.class).value();
				}
				if (className.equals("javax.faces.validator.Validator")) {
					o = ((javax.faces.validator.Validator) o).getClass().getAnnotation(FacesValidator.class).value();
				}
				if (className.equals("javax.faces.el.MethodBinding")) {
					// o = ((javax.faces.el.MethodBinding)
					// o).getExpressionString();
					o = Class.forName("javax.faces.el.MethodBinding").getMethod("getExpressionString", new Class<?>[] {}).invoke(o, new Object[] {});
					// value = new MethodExpression();
				}
				if ((o instanceof javax.el.MethodExpression || className.equals("javax.el.MethodExpression")) && !String.valueOf(o).contains("#")) {
					o = ((javax.el.MethodExpression) o).getExpressionString();
				}
				if (o instanceof javax.el.ValueExpression) {
					o = ((javax.el.ValueExpression) o).getExpressionString();
				}
				if (o instanceof Collection) {
					o = "";
				}
			} catch (Exception ex) {
				Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return String.valueOf(o);
	}

	public static UIComponent doFindComponent(UIComponent container, String id) throws Throwable {
		UIComponent found = null;
		for (Iterator<UIComponent> it = container.getFacetsAndChildren(); it.hasNext();) {
			UIComponent comp = it.next();
			if (comp.getId().equals(id)) {
				found = comp;
				break;
			} else if (comp.getFacetsAndChildren().hasNext()) {
				found = doFindComponent(comp, id);
				if (found != null) {
					break;
				}
			}
		}

		return found;
	}

	public static List<DevPrototypeComponentProperty> componentSerializer(Field f2, UIComponent comp3, Map mapParam, boolean repeatId) {
		List<DevPrototypeComponentProperty> prototypeComponentPropertyList = new ArrayList();
		// DevPrototypeComponentChildren prototypeComponentPropertyList = new
		// DevPrototypeComponentChildren();

		try {
			HashMap<String, PropertyDescriptor> hm = (HashMap) f2.get(comp3);
			for (Map.Entry<String, PropertyDescriptor> object : hm.entrySet()) {
				String object1 = object.getKey();
				PropertyDescriptor object2 = object.getValue();
				if (object2.getReadMethod() != null) {
					try {
						Object o;
						Class c = object2.getReadMethod().getDeclaringClass();
						ValueExpression valEx = comp3.getValueExpression(object1);
						if (valEx != null) {
							o = valEx;
						} else {
							try {
								o = object2.getReadMethod().invoke(comp3);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// System.out.println("Exception:" +
								// e.getMessage());
								continue;
							}
						}

						if (object1.equals("children")) {
							List<UIComponent> childrenList = (List<UIComponent>) o;
							List<DevPrototypeComponentChildren> childrenArrayList = new ArrayList<DevPrototypeComponentChildren>();
							for (int i = 0; i < childrenList.size(); i++) {
								UIComponent comp3a = (UIComponent) childrenList.get(i);
								// comp2.getChildren().add(cloneComp(f2, comp3a,
								// context, comp2));
								childrenArrayList.add(new DevPrototypeComponentChildren(comp3a.getClass().getName(), componentSerializer(f2, comp3a, mapParam, repeatId)));
							}
							if (!childrenArrayList.isEmpty()) {
								prototypeComponentPropertyList.add(new DevPrototypeComponentProperty(object1, childrenArrayList.getClass().getName(), null, childrenArrayList, null, null));
							}
						} else if (object1.equals("facets")) {
							List<DevPrototypeComponentFacets> facetsArrayList = new ArrayList<DevPrototypeComponentFacets>();
							for (Map.Entry<String, UIComponent> objectFacets : ((HashMap<String, UIComponent>) o).entrySet()) {
								String objectMap1 = objectFacets.getKey();
								UIComponent objectMap2 = objectFacets.getValue();
								if (objectMap2 instanceof com.sun.faces.facelets.compiler.UIInstructions) {
									HtmlOutputText out = new HtmlOutputText();
									out.setValue(objectMap2);
									objectMap2 = out;
								}
								facetsArrayList.add(new DevPrototypeComponentFacets(objectMap1, objectMap2.getClass().getName(), componentSerializer(f2, objectMap2, mapParam, repeatId)));
							}
							if (!facetsArrayList.isEmpty()) {
								prototypeComponentPropertyList.add(new DevPrototypeComponentProperty(object1, facetsArrayList.getClass().getName(), null, null, facetsArrayList, null));
							}
						} else if (object1.equals("clientBehaviors")) {
							List<DevPrototypeComponentBehaviors> behaviorsArrayList = new ArrayList<DevPrototypeComponentBehaviors>();

							for (Map.Entry<String, List<ClientBehavior>> objectFacets : ((Map<String, List<ClientBehavior>>) o).entrySet()) {
								String objectMap1 = objectFacets.getKey();
								List<ClientBehavior> objectMap2List = objectFacets.getValue();
								if (!objectMap2List.isEmpty()) {
									ClientBehavior objectMap2 = objectMap2List.get(0);
									behaviorsArrayList.add(new DevPrototypeComponentBehaviors(objectMap1, objectMap2.getClass().getName(), componentSerializerBehavior(objectMap2)));
								}
							}
							if (!behaviorsArrayList.isEmpty()) {
								prototypeComponentPropertyList.add(new DevPrototypeComponentProperty(object1, behaviorsArrayList.getClass().getName(), null, null, null, behaviorsArrayList));
							}
						} else {

							if (object2.getWriteMethod() != null) {
								String className = object2.getWriteMethod().getParameterTypes()[0].getName();
								if (!c.getName().equals("javax.faces.component.UIComponentBase") && !c.getName().equals("javax.faces.component.UIComponent")) {
									Object param = mapParam != null ? mapParam.get(object1) : null;
									String nameClass = object2.getWriteMethod().getParameterTypes()[0].getSimpleName();
									// if (param != null) {
									if (nameClass.equals("boolean")) {
										o = Boolean.valueOf(Objects.toString(param, Objects.toString(o, null)));
										if (Objects.deepEquals(o, false)) {
											o = null;
										}
									} else if (nameClass.equals("int")) {
										o = Integer.valueOf(Objects.toString(param, Objects.toString(o, null)));
										if (Objects.deepEquals(o, 0)) {
											o = null;
										}
									} else {
										o = param != null ? param : o;
									}
									// object2.getWriteMethod().invoke(comp3,
									// o);
									// }
									if (o != null) {
										o = ComponentFactory.getPropertyString(o, className);
										prototypeComponentPropertyList.add(new DevPrototypeComponentProperty(object1, object2.getWriteMethod().getParameterTypes()[0].getName(), String.valueOf(o), null, null, null));
									}
									// elementPropertiesSelected.put(object1,
									// new String[]{String.valueOf(o),
									// className});
								} else if (object1.equals("id") && (String.valueOf(o).isEmpty() || !String.valueOf(o).contains("j_id") || repeatId)) {
									prototypeComponentPropertyList.add(new DevPrototypeComponentProperty(object1, object2.getWriteMethod().getParameterTypes()[0].getName(), String.valueOf(o), null, null, null));
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IllegalArgumentException ex) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(AdmAlgodevBean.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			return prototypeComponentPropertyList;
		}
	}

	public static UIComponent componentDeserializer(DevPrototypeComponentChildren component) {
		UIComponent comp2 = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			if (String.valueOf(component.getPrototypeComponentType()).equals("com.sun.faces.facelets.compiler.UIInstructions")) {
				comp2 = new com.sun.faces.facelets.compiler.UIInstructions(ELText.parse(""), new XMLInstruction[] { new XMLInstruction(ELText.parse("")) });
			} else {
				comp2 = (UIComponent) Class.forName(String.valueOf(component.getPrototypeComponentType())).newInstance();
			}
			// HashMap<String, Object[]> property = (HashMap<String, Object[]>)
			// component[1];
			List<DevPrototypeComponentProperty> property = component.getPrototypeComponentPropertyList();

			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			Map<String, DevPrototypeComponentProperty> objectAuxMap = new HashMap();
			for (DevPrototypeComponentProperty objectAux : property) {
				objectAuxMap.put(objectAux.getPropertyName(), objectAux);
			}

			HashMap<String, PropertyDescriptor> hm = (HashMap) f2.get(comp2);
			for (Map.Entry<String, PropertyDescriptor> objectComp : hm.entrySet()) {
				Object value = null;
				String object1 = objectComp.getKey();
				DevPrototypeComponentProperty object = objectAuxMap.get(object1);
				PropertyDescriptor object2 = objectComp.getValue();
				if (object != null) {
					value = object.getPropertyValue();
				} else {
					object = new DevPrototypeComponentProperty();
				}
				if (object2.getReadMethod() != null) {
					try {
						if (object1.equals("selected")) {
							String d = "";
							// continue;
						}
						object2.getReadMethod().setAccessible(true);
						// Object value = object2.getReadMethod().invoke(comp3);
						if (object1.equals("children")) {
							List<DevPrototypeComponentChildren> children = object.getChildren();
							// Collection<Object[]> childrenArrayCol =
							// (HashSet<Object[]>) value;
							for (Iterator<DevPrototypeComponentChildren> it = children.iterator(); it.hasNext();) {
								DevPrototypeComponentChildren objects = it.next();
								comp2.getChildren().add(componentDeserializer(objects));
							}
						} else if (object1.equals("facets")) {
							List<DevPrototypeComponentFacets> facets = object.getFacets();
							// Collection<Object[]> childrenArrayCol =
							// (HashSet<Object[]>) value;
							for (Iterator<DevPrototypeComponentFacets> it = facets.iterator(); it.hasNext();) {
								DevPrototypeComponentFacets objects = it.next();
								comp2.getFacets().put(objects.getPrototypeComponentName(), componentDeserializer(new DevPrototypeComponentChildren(objects.getPrototypeComponentType(), objects.getPrototypeComponentPropertyList())));
							}
						} else if (object1.equals("clientBehaviors")) {
							List<DevPrototypeComponentBehaviors> facets = object.getBehaviors();
							// Collection<Object[]> childrenArrayCol =
							// (HashSet<Object[]>) value;
							for (Iterator<DevPrototypeComponentBehaviors> it = facets.iterator(); it.hasNext();) {
								DevPrototypeComponentBehaviors objects = it.next();
								((UIComponentBase) comp2).addClientBehavior(objects.getPrototypeComponentName(), componentDeserializerBehavior(new DevPrototypeComponentBehaviors(objects.getPrototypeComponentName(), objects.getPrototypeComponentType(), objects.getPrototypeComponentPropertyList())));
							}
						} else {
							Class c = object2.getReadMethod().getDeclaringClass();
							if (facesContext != null && object2.getWriteMethod() != null) {
								String className = object2.getWriteMethod().getParameterTypes()[0].getName();
								if (!c.getName().equals("javax.faces.component.UIComponentBase") && !c.getName().equals("javax.faces.component.UIComponent")) {
									if (className.equals("boolean")) {
										value = Boolean.valueOf(Objects.toString(value, "false"));
									} else if (className.equals("int")) {
										value = Integer.parseInt(Objects.toString(value, "0"));
									} else if (value == null || String.valueOf(value).equals("null")) {
										continue;
									} else if (className.equals("double")) {
										value = Double.parseDouble(String.valueOf(value));
									} else if (className.equals("java.lang.Long")) {
										value = Long.parseLong(String.valueOf(value));
									} else if (className.equals("java.util.Map")) {
										continue;
									} else if (className.equals("java.util.List")) {
										continue;
									} else if (className.equals("javax.faces.convert.Converter")) {
										if (String.valueOf(value).contains("#")) {
											value = (javax.faces.convert.Converter) facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(value), Converter.class).getValue(facesContext.getELContext());
											value = facesContext.getApplication().createConverter(value.getClass());
										} else {
											value = facesContext.getApplication().createConverter(String.valueOf(value));
										}
									} else if (className.equals("javax.faces.validator.Validator")) {
										if (String.valueOf(value).contains("#")) {
											value = (javax.faces.validator.Validator) facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(value), Validator.class).getValue(facesContext.getELContext());
											value = facesContext.getApplication().createConverter(value.getClass());
										} else {
											value = facesContext.getApplication().createValidator(String.valueOf(value));
										}
									} else if (className.equals("javax.faces.component.UIComponent")) {
										/*
										 * if (value != null) { HtmlOutputText
										 * out = new HtmlOutputText();
										 * out.setValue(value); value = out; }
										 * else {
										 * comp2.getFacets().remove(object1); }
										 */
										continue;
									} else if (className.equals("javax.faces.el.MethodBinding")) {
										if (object1.equals("validator")) {
											value = Application.class.getMethod("createMethodBinding", String.class, new Class<?>[] {}.getClass()).invoke(facesContext.getApplication(), String.valueOf(value), new Class<?>[] { FacesContext.class, UIComponent.class, Object.class });
										} else {
											value = Application.class.getMethod("createMethodBinding", String.class, new Class<?>[] {}.getClass()).invoke(facesContext.getApplication(), String.valueOf(value), new Class<?>[] {});
										}
										// value = new MethodExpression();
									} else if (className.equals("javax.el.MethodExpression")) {
										value = facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), String.valueOf(value), null, new Class<?>[] {});
									} else if (String.valueOf(value).contains("#")) {
										value = facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), String.valueOf(value), Object.class);
										comp2.setValueExpression(object1, (ValueExpression) value);
										continue;
									}

									try {
										object2.getWriteMethod().invoke(comp2, value);
									} catch (java.lang.IllegalArgumentException e) {
										System.out.println("EXCEPTION IN WRITE METHOD: " + e.getMessage());
									}
								} else if (object1.equals("id") && value != null) {
									comp2.setId(String.valueOf(value));
								}
							}
						}
					} catch (java.lang.reflect.InvocationTargetException e) {
						System.out.println("Exception:" + e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (facesContext != null && comp2.getId() == null) {
				comp2.setId("ad_" + facesContext.getViewRoot().createUniqueId());
			}
			comp2.setParent(null);
		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			return comp2;
		}
	}

	private static List<DevPrototypeComponentProperty> componentSerializerBehavior(ClientBehavior behavior) {
		// DevPrototypeComponentBehaviors devPrototypeComponentBehaviors = new
		// DevPrototypeComponentBehaviors();
		List<DevPrototypeComponentProperty> devPrototypeComponentPropertyList = new ArrayList<DevPrototypeComponentProperty>();
		DevPrototypeComponentProperty property = new DevPrototypeComponentProperty();
		// Object[] objs =
		// (Object[])behavior.saveState(FacesContext.getCurrentInstance());
		// List<BehaviorListener> listeners =
		// (List<BehaviorListener>)ClientBehaviorBase.class.getDeclaredField("listeners").get(behavior);
		// BehaviorListener bl = listeners.get(0);

		// HashMap<String, PropertyDescriptor> props =
		// generatePropertyDescriptorMap(behavior);
		HashMap<String, HashMap<String, Object>> hm = generatePropertyDescriptorMap(behavior);
		;
		for (Map.Entry<String, HashMap<String, Object>> object : hm.entrySet()) {
			String object1 = object.getKey();
			HashMap<String, Object> object2 = object.getValue();
			Method readMethod = (Method) object2.get("readMethod");
			Method writeMethod = (Method) object2.get("writeMethod");
			if (readMethod != null) {
				property = new DevPrototypeComponentProperty();
				Object o = null;
				try {
					o = readMethod.invoke(behavior);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (o != null) {
					String className = readMethod.getReturnType().getName();
					property.setPropertyName(object1);
					property.setPropertyType(className);
					property.setPropertyValue(ComponentFactory.getPropertyString(o, className));
					devPrototypeComponentPropertyList.add(property);
				}
			}
		}

		try {
			Field f = behavior.getClass().getSuperclass().getSuperclass().getDeclaredField("listeners");
			f.setAccessible(true);
			ArrayList obj = (ArrayList) f.get(behavior);
			if (obj != null) {
				Object o = obj.get(0);
				property = new DevPrototypeComponentProperty();
				property.setPropertyName("listeners");
				property.setPropertyType(obj.getClass().getName());
				property.setPropertyValue(objectToJson(o).toString());
				devPrototypeComponentPropertyList.add(property);
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new IllegalArgumentException("Object null in 201407061837.", e);
		}
		return devPrototypeComponentPropertyList;
	}

	private static ClientBehavior componentDeserializerBehavior(DevPrototypeComponentBehaviors devPrototypeComponentBehaviors) {
		ClientBehavior comp2 = null;
		try {
			comp2 = (ClientBehavior) Class.forName(String.valueOf(devPrototypeComponentBehaviors.getPrototypeComponentType())).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// behavior = new AjaxBehavior();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// ((AjaxBehavior)behavior).getClass().getMethods()[0].
		List<DevPrototypeComponentProperty> devPrototypeComponentPropertyList = devPrototypeComponentBehaviors.getPrototypeComponentPropertyList();
		for (int i = 0; i < devPrototypeComponentPropertyList.size(); i++) {
			DevPrototypeComponentProperty object = devPrototypeComponentPropertyList.get(i);
			HashMap<String, HashMap<String, Object>> hm = generatePropertyDescriptorMap(comp2);
			// if(property.getPropertyName().equals("listener")){
			// ValueExpression value =
			// facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(),
			// String.valueOf(property.getPropertyValue()), Object.class);
			// ((AjaxBehavior)behavior).setValueExpression("listener", value);
			// }
			String object1 = object.getPropertyName();
			HashMap<String, Object> object2 = hm.get(object1);
			Method readMethod = (Method) object2.get("readMethod");
			Method writeMethod = (Method) object2.get("writeMethod");
			String className = String.valueOf(object.getPropertyType());
			Object value = object.getPropertyValue();
			if (object1.equals("listeners")) {
				Object obj = jsonToObject(value);
				List l = new ArrayList();
				l.add(obj);
				Field f = null;
				try {
					f = comp2.getClass().getSuperclass().getSuperclass().getDeclaredField("listeners");
					f.setAccessible(true);
					f.set(comp2, l);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			if (readMethod != null) {
				Class c = readMethod.getDeclaringClass();
				if (facesContext != null && writeMethod != null) {
					if (value != null && !String.valueOf(value).equals("null") && !c.getName().equals("javax.faces.component.UIComponentBase") && !c.getName().equals("javax.faces.component.UIComponent")) {
						if (className.equals("boolean")) {
							value = Boolean.valueOf(String.valueOf(value));
						} else if (className.equals("int")) {
							value = Integer.parseInt(String.valueOf(value));
						} else if (className.equals("double")) {
							value = Double.parseDouble(String.valueOf(value));
						} else if (className.equals("java.lang.Long")) {
							value = Long.parseLong(String.valueOf(value));
						}
						try {
							writeMethod.invoke(comp2, value);
						} catch (java.lang.IllegalArgumentException e) {
							System.out.println("EXCEPTION IN WRITE METHOD: " + e.getMessage());
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		}
		return comp2;
	}

	private static Object jsonToObject(Object json) {
		JSONObject rootNode = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (json instanceof String) {
				rootNode = new JSONObject(String.valueOf(json));
			}
			if (json instanceof JSONObject) {
				rootNode = (JSONObject) json;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// rootNode.keys();
		// for (Iterator iterator = rootNode.keys(); iterator.hasNext();) {
		// String type = (String) iterator.next();
		try {
			// Object ob = rootNode.get("type");
			String tpe = (String) rootNode.get("type");
			Class clazz = Class.forName(tpe);
			Object val = rootNode.get("value");

			if (val instanceof JSONObject) {
				Object o = null;
				if (MethodExpression.class.isAssignableFrom(clazz)) {
					String valStr = val.toString();
					int start = valStr.indexOf("#{");
					int end = valStr.indexOf("}", start) + 1;
					String expr = valStr.substring(start, end);
					o = fc.getApplication().getExpressionFactory().createMethodExpression(fc.getELContext(), expr, null, new Class<?>[] {});
					return o;
				} else {
					o = clazz.newInstance();
				}
				JSONObject child = (JSONObject) val;
				for (Iterator iterator = child.keys(); iterator.hasNext();) {
					String fieldName = (String) iterator.next();
					Field f = clazz.getDeclaredField(fieldName);
					f.setAccessible(true);
					try {
						f.set(o, jsonToObject(child.get(fieldName)));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return o;
			} else {
				return clazz.getConstructor(String.class).newInstance(val);
			}

			// if (ob instanceof JSONObject) {
			// JSONObject new_Json = (JSONObject) ob;
			// String tpe = (String) new_Json.get("type");
			// Object val = new_Json.get("value");
			// }else{

			// }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		// }
		return null;
	}

	private static Object objectToJson(Object o) {
		JSONObject rootNode = new JSONObject();
		JSONObject childNode = new JSONObject();
		try {
			Field[] fs = null;
			if (o == null) {
				return null;
			}
			rootNode.put("type", o.getClass().getName());
			if ((fs = o.getClass().getDeclaredFields()).length == 0 || o instanceof Number || o instanceof String) {
				rootNode.put("value", o);
				return rootNode;
			}
			rootNode.put("value", childNode);
			for (int i = 0; i < fs.length; i++) {
				Field field = fs[i];
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				Object of = null;
				try {
					of = field.get(o);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					// JSONObject object = new JSONObject();
					// object.put("type", field.getType().getName());
					// object.put("value", objectToJson(of));
					childNode.putOpt(field.getName(), objectToJson(of));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return rootNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static HashMap<String, HashMap<String, Object>> generatePropertyDescriptorMap(ClientBehavior obj) {
		Field[] fields = null;
		Method[] methods = null;
		Object value = null;
		HashMap<String, Object> prop = new HashMap<String, Object>();
		HashMap<String, HashMap<String, Object>> propMap = new HashMap<String, HashMap<String, Object>>();
		Class clazz = obj.getClass();
		while (true) {
			fields = clazz.getDeclaredFields();
			methods = clazz.getDeclaredMethods();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				try {
					field.get(obj);
					String fieldName = field.getName();
					Method setMethod = null;
					Method getMethod = null;
					// prop = new PropertyDescriptor(field.getName(), clazz);
					prop = new HashMap<String, Object>();
					prop.put("propertyName", field.getName());
					new PropertyDescriptor(field.getName(), clazz, null, null);
					for (int j = 0; j < methods.length; j++) {
						Method method = methods[j];
						String methodLc = method.getName().toLowerCase();
						if (method.getName().toLowerCase().endsWith(fieldName)) {
							if (("get" + fieldName).equals(methodLc) || ("is" + fieldName).equals(methodLc)) {
								getMethod = method;
								prop.put("readMethod", method);
							} else if (("set" + fieldName).equals(methodLc)) {
								prop.put("writeMethod ", method);
							}
						}
						if (prop.containsKey("readMethod") && prop.containsKey("writeMethod")) {
							break;
						}
					}
					// prop = new PropertyDescriptor(field.getName(), clazz,
					// getMethod, setMethod);
					propMap.put(field.getName(), prop);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (clazz.getSuperclass() != null) {
				clazz = clazz.getSuperclass();
			} else {
				break;
			}
		}
		return propMap;
	}

	public static UIComponent componentClone(UIComponent elementSelected, boolean repeatId) {
		UIComponent cloned = null;
		try {
			Field f2 = UIComponentBase.class.getDeclaredField("pdMap");
			f2.setAccessible(true);
			List<DevPrototypeComponentProperty> property = ComponentFactory.componentSerializer(f2, elementSelected, null, repeatId);
			cloned = ComponentFactory.componentDeserializer(new DevPrototypeComponentChildren(elementSelected.getClass().getName(), property));
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		return cloned;
	}

	public static String parentContainerName(String currentContainer) {
		String parentContainer = "";
		String[] array = currentContainer.split("-");
		for (int i = 0; i < array.length - 2; i++) {
			parentContainer = (parentContainer.isEmpty() ? "" : parentContainer.concat("-")).concat(array[i]);
		}
		return parentContainer;
	}

	public enum AppType {

		LIST_FORM("listForm"), FORM_LIST("formList"), FORM("form"), SUMM("summ"), LIST_EDIT_FORM("listEditForm");
		private String appTypeName;

		AppType(String appTypeName) {
			this.appTypeName = appTypeName;
		}

		public String getRegistrationTypeName() {
			return appTypeName;
		}

		public void setRegistrationTypeName(String appTypeName) {
			this.appTypeName = appTypeName;
		}

		private static final Map<String, AppType> lookup = new HashMap();

		// Populate the lookup table on loading time
		static {
			for (AppType s : EnumSet.allOf(AppType.class))
				lookup.put(s.getRegistrationTypeName(), s);
		}

		// This method can be used for reverse lookup purpose
		public static AppType get(String type) {
			return lookup.get(type);
		}

		public boolean hasList() {
			return appTypeName.toLowerCase().contains("list");
		}
	}
}
