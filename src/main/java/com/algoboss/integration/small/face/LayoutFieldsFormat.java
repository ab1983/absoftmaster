package com.algoboss.integration.small.face;

import java.util.ArrayList;
import java.util.List;

import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevReportFieldContainer;
import com.algoboss.erp.entity.DevReportFieldOptions;
import com.algoboss.erp.entity.DevReportFieldOptionsMap;
import com.algoboss.erp.entity.DevRequirement;

public class LayoutFieldsFormat {
	public static void populateContainerField(DevRequirement requirement){
		List<String> fieldNames = new ArrayList<String>();
		if(requirement.getRequirementName().equals("VENDAS")){
			fieldNames.clear();
			fieldNames.add("registro");
			fieldNames.add("vendedor");
			fieldNames.add("desconto");
			fieldNames.add("cliente");
			fieldNames.add("emissao");
			fieldNames.add("saidad");
			fieldNames.add("saidah");
			fieldNames.add("transporta");
			fieldNames.add("total");
			fieldNames.add("volumes");
			clear(requirement);
			show("list", requirement, fieldNames.toArray(new String[]{}));	
			fieldNames.add("produtos");
			show("form", requirement, fieldNames.toArray(new String[]{}));
			fieldNames.clear();
			fieldNames.add("produtos.registro");
			fieldNames.add("produtos.codigo");
			fieldNames.add("produtos.descricao");
			fieldNames.add("produtos.quantidade");
			fieldNames.add("produtos.unitario");
			fieldNames.add("produtos.total");
			//clear(requirement);
			show("form-form-1236_itens001", requirement, fieldNames.toArray(new String[]{}));
			show("form-list-1236_itens001", requirement, fieldNames.toArray(new String[]{}));	
		}else if(requirement.getRequirementName().equals("PRODUTOS")){
			fieldNames.clear();
			fieldNames.add("codigo");
			fieldNames.add("descricao");
			fieldNames.add("quantidade");
			fieldNames.add("unitario");
			fieldNames.add("total");
			clear(requirement);
			show("form", requirement, fieldNames.toArray(new String[]{}));
			show("list", requirement, fieldNames.toArray(new String[]{}));			

		}else{
			List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = requirement.getEntityClass().getEntityPropertyDescriptorList();
			for (DevEntityPropertyDescriptor devEntityPropertyDescriptor : entityPropertyDescriptorList) {
				fieldNames.add(devEntityPropertyDescriptor.getPropertyName());
			}
			show("form", requirement, fieldNames.toArray(new String[]{}));
			show("list", requirement, fieldNames.toArray(new String[]{}));			
		}
		
	}
	public static List<DevReportFieldOptions> filter(DevRequirement requirement, String parentContainer, String optionName, String optionValue){
		
		DevReportFieldContainer fieldContainer = LayoutFieldsFormat.getFieldContainer(requirement, parentContainer);
		
		List<DevReportFieldOptions> fieldOptionsList = new ArrayList<DevReportFieldOptions>();
		List<DevReportFieldOptions> fieldOptionsListTmp = fieldContainer.getFieldOptionsList();
		for (DevReportFieldOptions devReportFieldOptions : fieldOptionsListTmp) {
			if(String.valueOf(LayoutFieldsFormat.loadOpt(devReportFieldOptions, optionName).getOptionsValue()).equals(optionValue)){
				fieldOptionsList.add(devReportFieldOptions);
			}	
		}		
		return fieldOptionsList;
	}
	public static DevReportFieldContainer getFieldContainer(DevRequirement requirement, String container){
		DevReportFieldContainer fieldContainer = null;
		List<DevReportFieldContainer> fieldContainerList = requirement.getFieldContainerList();
		for (DevReportFieldContainer devReportFieldContainer : fieldContainerList) {
			if(devReportFieldContainer.getName().equals(container)){
				fieldContainer = devReportFieldContainer;
				break;
			}
		}
		if(fieldContainer == null){
			fieldContainer = new DevReportFieldContainer();
			fieldContainer.setName(container);
			fieldContainerList.add(fieldContainer);
		}
		return fieldContainer;
	}
	public static void clear(DevRequirement requirement){
		requirement.getFieldContainerList().clear();
	}
	public static DevReportFieldOptions load(DevEntityClass entCls, DevReportFieldContainer fieldContainer, String fieldName){
		DevEntityObject entObj = new DevEntityObject(entCls);
		return load(entObj, fieldContainer, fieldName);
		
	}
	public static DevReportFieldOptions load(DevEntityObject entObj, DevReportFieldContainer fieldContainer, String fieldName){
		DevReportFieldOptions fieldOptions = null;
		List<DevReportFieldOptions> fieldOptionsList = fieldContainer.getFieldOptionsList();
		for (DevReportFieldOptions fieldOptionsTmp : fieldOptionsList) {
			if(fieldOptionsTmp.getName().equals(fieldName)){
				fieldOptions = fieldOptionsTmp;
				break;
			}				
		}
		if(fieldOptions == null){
			fieldOptions = new DevReportFieldOptions();
			fieldOptions.setEntityPropertyDescriptor(entObj.getPropObj(fieldName).getEntityPropertyDescriptor());
			fieldOptions.setName(fieldOptions.getEntityPropertyDescriptor().getPropertyName());
			fieldContainer.getFieldOptionsList().add(fieldOptions);		
		}
		return fieldOptions;
	}
	public static DevReportFieldOptionsMap loadOpt(DevEntityClass entCls, DevReportFieldContainer fieldContainer, String fieldName, String optionName){
		DevEntityObject entObj = new DevEntityObject(entCls);
		return loadOpt(entObj, fieldContainer, fieldName, optionName,"String");
	}	
	public static DevReportFieldOptionsMap loadOpt(DevEntityObject entObj, DevReportFieldContainer fieldContainer, String fieldName, String optionName){
		return loadOpt(entObj, fieldContainer, fieldName, optionName,"String");
	}
	
	public static DevReportFieldOptionsMap loadOpt(DevEntityObject entObj, DevReportFieldContainer fieldContainer, String fieldName, String optionName, String optionType){
		DevReportFieldOptions fieldOptions = load(entObj, fieldContainer, fieldName);
		return loadOpt(fieldOptions, optionName, optionType);
	}
	public static DevReportFieldOptionsMap loadOpt(DevReportFieldOptions fieldOptions, String optionName){
		return loadOpt(fieldOptions, optionName, "String");
	}
	public static DevReportFieldOptionsMap loadOpt(DevReportFieldOptions fieldOptions, String optionName, String optionType){
		DevReportFieldOptionsMap fieldOptionsMap = null;
		for (DevReportFieldOptionsMap fieldOptionsMapTmp : fieldOptions.getFieldOptionsMapList()) {
			if(fieldOptionsMapTmp.getOptionsName().equals(optionName)){
				fieldOptionsMap = fieldOptionsMapTmp;
				break;
			}
		}
		if(fieldOptionsMap == null){
			fieldOptionsMap = new DevReportFieldOptionsMap();
			fieldOptionsMap.setOptionsName(optionName);
			fieldOptionsMap.setOptionsType(optionType);	
			fieldOptions.getFieldOptionsMapList().add(fieldOptionsMap);
			
		}
		return fieldOptionsMap;
	}	
	public static void show(String container,DevRequirement requirement, String... fieldNames){
		
		DevReportFieldContainer fieldContainer = getFieldContainer(requirement, container);
		
		DevEntityObject entObj = new DevEntityObject(requirement.getEntityClass());
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			loadOpt(entObj, fieldContainer, fieldName, "visible","Boolean").setOptionsValue("true");
			
			
		}
	}
	
	public static void hidden(String container,DevRequirement requirement, String... fieldNames){
		
	}	
}
