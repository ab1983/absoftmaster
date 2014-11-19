package com.algoboss.erp.util;

import java.util.ArrayList;
import java.util.List;

import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyDescriptorConfig;
import com.algoboss.erp.entity.DevEntityPropertyValue;
import com.algoboss.erp.entity.DevReportFieldContainer;
import com.algoboss.erp.entity.DevReportFieldOptions;
import com.algoboss.erp.entity.DevReportFieldOptionsMap;
import com.algoboss.erp.entity.DevRequirement;

public class LayoutFieldsFormat {
	private DevRequirement requirement;
	private LayoutFieldsFormat(DevRequirement requirement) {
		this.requirement = requirement;
		populateContainerField();
	}
	public static LayoutFieldsFormat create(DevRequirement requirement){
		return new LayoutFieldsFormat(requirement);
	}
	public DevRequirement populateContainerField(){
		List<String> fieldNames = new ArrayList<String>();
		if(requirement.getRequirementId()==null){
			return requirement;
		}
		if(requirement.getRequirementId() == 232){//PRÉ-VENDAS
			putTypeConfig(requirement.getEntityClass(),"cliente","objectList","1236_clifor;nome;nome;cgc;comple");
			putTypeConfig(requirement.getEntityClass(),"vendedor","objectList","1236_vendedor;nome;nome");
			putTypeConfig(requirement.getEntityClass(),"operacao","objectList","1236_icm;nome;cfop,nome");
			putTypeConfig(requirement.getEntityClass(),"placa","objectList","1236_transpor;placa;placa,nome");
			fieldNames.clear();
			fieldNames.add("numeronf");
			fieldNames.add("vendedor");
			fieldNames.add("operacao");
			fieldNames.add("cliente");
			fieldNames.add("identificador1");
			fieldNames.add("desconto");
			fieldNames.add("emissao");
			fieldNames.add("saidad");
			fieldNames.add("saidah");
			fieldNames.add("placa");
			fieldNames.add("total");
			fieldNames.add("volumes");
			fieldNames.add("produtos");
			clear(requirement);
			List<String> fieldNamesList = new ArrayList<String>(fieldNames);
			fieldNamesList.remove("identificador1");
			fieldNamesList.remove("desconto");
			fieldNamesList.remove("operacao");
			fieldNamesList.remove("placa");
			fieldNamesList.remove("produtos");
			fieldNamesList.remove("saidad");
			fieldNamesList.remove("saidah");
			show("list", fieldNamesList.toArray(new String[]{}));	
			show("form", fieldNames.toArray(new String[]{}));
			readOnly("form", new String[]{"numeronf","total","volumes"});
			//putTypeConfig(requirement.getEntityClass(),"volumes","value","#{app.count('produtos')}");
			putTypeConfig(requirement.getEntityClass(),"produtos.codigo","objectList","1236_estoque;codigo;codigo;descricao");
			//putTypeConfig(requirement.getEntityClass(),"produtos.total","value","#{(app.$('produtos.quantidade')).val*app.$('produtos.unitario').val)}");
			fieldNames.clear();
			//fieldNames.add("produtos.registro");
			fieldNames.add("produtos.codigo");
			//fieldNames.add("produtos.descricao");
			fieldNames.add("produtos.quantidade");
			fieldNames.add("produtos.unitario");
			fieldNames.add("produtos.total");
			//clear(requirement);
			show("form-form-1236_itens001", fieldNames.toArray(new String[]{}));
			show("form-list-1236_itens001", fieldNames.toArray(new String[]{}));
			
			style("list", "cliente", "width:350px;");
			
			readOnly("form-form-1236_itens001", new String[]{"produtos.total"});
			
			label("numeronf", "Nº PRÉ-VENDA","form","list");
			label("identificador1", "TIPO PREÇO","form","list");
			//label("transporta", "TRANSPORTADORA","form","list");
			label("placa", "PLACA","form","list");
			label("operacao", "OPERAÇÃO","form","list");
			label("emissao","EMISSÃO","form","list");
			label("saidad","DATA SAÍDA","form","list");
			label("saidah","HORA SAÍDA","form","list");			
			label("volumes", "QUANTIDADE","form","list");
			
			label("produtos.codigo", "CÓDIGO/DESCRIÇÃO","form-form-1236_itens001");
			label("produtos.unitario", "UNITÁRIO","form-form-1236_itens001","form-list-1236_itens001");
			
			eventField("form-form-1236_itens001", new String[]{"produtos.codigo"}, "eventBean('com.algoboss.integration.small.business.PreVendaBo.precoProdutoGen');focusBean('.c_quantidade.c_input');", "onchange");
			eventField("form-form-1236_itens001", new String[]{"produtos.quantidade","produtos.unitario"}, "eventBean('com.algoboss.integration.small.business.PreVendaBo.produtoCalc')", "onblur");
			eventField("form", new String[]{"identificador1"}, "eventBean('com.algoboss.integration.small.business.PreVendaBo.produtoTotalCalc')", "onchange");
			eventField("form", new String[]{"cliente"}, "eventBean('com.algoboss.integration.small.business.PreVendaBo.descontoCliente')", "onchange");
			eventField("form", new String[]{"placa"}, "eventBean('com.algoboss.integration.small.business.PreVendaBo.setTransportadoraPlaca')", "onchange");
			propertyStyleClass("form","data-form" ,"onload","if($('.c_numeronf.c_input').val()===''){eventPage('com.algoboss.integration.small.business.PreVendaBo.numeroNfGen');clearFormChanged();};eventPage('com.algoboss.integration.small.business.PreVendaBo.produtoTotalCalc');");
			propertyStyleClass("form","c_save_button" ,"value","Gravar Pré-Venda");
			propertyStyleClass("form","c_save_and_back_button" ,"value","Finalizar Pré-Venda e Voltar");
			propertyStyleClass("form-form-1236_itens001","c_save_button" ,"value","Confirmar Produto");
			propertyStyleClass("form-form-1236_itens001","c_save_and_back_button" ,"value","Confirmar Produto e Voltar");
			//propertyStyleClass("form","c_save_and_back" ,"value","Finalizar Pré-Venda e Voltar");
			//Nome botões
			//evento botões
			//label pagina
			//ui-commandButton 
						
			
			DevRequirement reqCloned = DevRequirement.clone(requirement);
			
			
			//returnReq.setFieldContainerList(fieldContainerList);
			return reqCloned;
		}if(requirement.getRequirementId() == 238){//ORDEM SERVIÇO
			clear(requirement);
			putTypeConfig(requirement.getEntityClass(),"identifi1","list","Interno,Externo");
			putTypeConfig(requirement.getEntityClass(),"cliente","objectList","1236_clifor;nome;nome;cgc");
			putTypeConfig(requirement.getEntityClass(),"tecnico","objectList","1236_vendedor[funcao|equals|TECNICO];nome;nome");
			fieldNames.clear();
			fieldNames.add("numero");
			fieldNames.add("identifi1");
			fieldNames.add("tecnico");
			fieldNames.add("data");
			fieldNames.add("hora");
			fieldNames.add("situacao");
			fieldNames.add("cliente");
			fieldNames.add("problema");
			fieldNames.add("totalos");
			fieldNames.add("produtos");
			List<String> fieldNamesList = new ArrayList<String>(fieldNames);
			fieldNamesList.remove("identifi1");
			fieldNamesList.remove("produtos");			
			show("list", fieldNamesList.toArray(new String[]{}));	
			show("form", fieldNames.toArray(new String[]{}));
			readOnly("form", new String[]{"numero","totalos"});		
			
			label("numero", "NÚMERO OS","form","list");
			label("identifi1", "ORIGEM","form","list");
			label("tecnico", "ATENDENTE","form","list");
			label("situacao", "SITUAÇÃO","form","list");
			label("totalos", "TOTAL OS","form","list");
			
			style("list", "cliente", "width:200px;");
			style("list", "hora", "width:50px;");
			style("list", "data", "width:80px;");
			style("list", "identifi1", "width:60px;");
			style("list", "problema", "width:120px;");			
			
			propertyStyleClass("form","data-form" ,"onload","if($('.c_numero.c_input').val()===''){eventPage('com.algoboss.integration.small.business.OrdemServicoBo.numeroOsGen');};");			
			
			putTypeConfig(requirement.getEntityClass(),"produtos.codigo","objectList","1236_estoque;codigo;codigo;descricao");
			//putTypeConfig(requirement.getEntityClass(),"produtos.total","value","#{(app.$('produtos.quantidade')).val*app.$('produtos.unitario').val)}");
			fieldNames.clear();
			//fieldNames.add("produtos.registro");
			fieldNames.add("produtos.codigo");
			//fieldNames.add("produtos.descricao");
			fieldNames.add("produtos.quantidade");
			fieldNames.add("produtos.unitario");
			fieldNames.add("produtos.total");
			//clear(requirement);
			show("form-form-1236_itens001", fieldNames.toArray(new String[]{}));
			show("form-list-1236_itens001", fieldNames.toArray(new String[]{}));			
			
		}else if(requirement.getRequirementName().equals("PRODUTOS")){
			fieldNames.clear();
			fieldNames.add("codigo");
			fieldNames.add("descricao");
			fieldNames.add("quantidade");
			fieldNames.add("unitario");
			fieldNames.add("total");
			clear(requirement);
			show("form",  fieldNames.toArray(new String[]{}));
			show("list",  fieldNames.toArray(new String[]{}));			
			return requirement;
		}else{
			clear(requirement);
			/*
			List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = requirement.getEntityClass().getEntityPropertyDescriptorList();
			for (DevEntityPropertyDescriptor devEntityPropertyDescriptor : entityPropertyDescriptorList) {
				fieldNames.add(devEntityPropertyDescriptor.getPropertyName());
			}
			show("form", fieldNames.toArray(new String[]{}));
			show("list", fieldNames.toArray(new String[]{}));	
			*/
		}
		return requirement;
		
	}
	private void style(String container, String fieldName, String style) {
		writeOptArray(new String[]{"style"}, "String", style, new String[]{container}, requirement, new String[]{fieldName});
	}	
	private void propertyStyleClass(String container, String clazz, String property, String value) {
		writeOptArray(new String[]{property}, "String", value, new String[]{container}, requirement, clazz);
	}
	private void eventField(String container, String[] fieldNames,String function, String... events) {
		writeOptArray(events, "String", function, new String[]{container}, requirement, fieldNames);
	}		
	private void label(String fieldName, String label, String... containers) {
		writeOptArray(new String[]{"label"}, "String", label, containers, requirement, new String[]{fieldName});
	}	
	private void readOnly(String container, String[] fieldNames) {
		writeOptArray(new String[]{"readonly"}, "Boolean", "true", new String[]{container}, requirement, fieldNames);
		writeOptArray(new String[]{"disabled"}, "Boolean", "true", new String[]{container}, requirement, fieldNames);
	}

	public void putTypeConfig(DevEntityClass devEntityClass, String property, String key, String value){
		DevEntityObject entObj = new DevEntityObject(devEntityClass);
		DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfigAux = null;
		List<DevEntityPropertyDescriptorConfig> DevEntityPropertyDescriptorConfigList = entObj.getPropObj(property).getEntityPropertyDescriptor().getEntityPropertyDescriptorConfigList();
		for (DevEntityPropertyDescriptorConfig devEntityPropertyDescriptorConfig : DevEntityPropertyDescriptorConfigList) {
			if(devEntityPropertyDescriptorConfig.getConfigName().equals(key)){
				devEntityPropertyDescriptorConfigAux = devEntityPropertyDescriptorConfig;
				break;
			}
			//devEntityPropertyDescriptorConfig.
		}
		if(devEntityPropertyDescriptorConfigAux == null){
			devEntityPropertyDescriptorConfigAux = new DevEntityPropertyDescriptorConfig();
			devEntityPropertyDescriptorConfigAux.setConfigName(key);
			DevEntityPropertyDescriptorConfigList.add(devEntityPropertyDescriptorConfigAux);
		}
		devEntityPropertyDescriptorConfigAux.setConfigValue(value);
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
			if(fieldName.endsWith(fieldOptionsTmp.getName())){
				fieldOptions = fieldOptionsTmp;
				break;
			}				
		}
		if(fieldOptions == null){
			fieldOptions = new DevReportFieldOptions();
			DevEntityPropertyValue propField = entObj.getPropObj(fieldName);
			if(propField!=null){
				fieldOptions.setEntityPropertyDescriptor(propField.getEntityPropertyDescriptor());
				fieldOptions.setName(fieldOptions.getEntityPropertyDescriptor().getPropertyName());				
			}else{
				fieldOptions.setName(fieldName);
			}
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
		return loadOpt(fieldOptions, optionName, null);
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
			if(optionType!=null){
				fieldOptionsMap.setOptionsName(optionName);
				fieldOptionsMap.setOptionsType(optionType);	
				fieldOptions.getFieldOptionsMapList().add(fieldOptionsMap);				
			}		
		}
		return fieldOptionsMap;
	}	
	public void show(String container, String... fieldNames){
		writeOptArray(new String[]{"visible"}, "Boolean", "true", new String[]{container}, requirement, fieldNames);
	}
	public static void writeOptArray(String[] optionNames, String optionType,String optionValue, String[] containers,DevRequirement requirement, String... fieldNames){
		for (int i = 0; i < containers.length; i++) {
			String container = containers[i];
			DevReportFieldContainer fieldContainer = getFieldContainer(requirement, container);
			DevEntityObject entObj = new DevEntityObject(requirement.getEntityClass());
			for (int j = 0; j < fieldNames.length; j++) {
				String fieldName = fieldNames[j];
				for (int k = 0; k < optionNames.length; k++) {
					String optionName = optionNames[k];
					loadOpt(entObj, fieldContainer, fieldName, optionName, optionType).setOptionsValue(optionValue);					
				}
			}		
		}
	}
	public static void hidden(String container,DevRequirement requirement, String... fieldNames){
		
	}	
	
	public static List<DevReportFieldOptions> getVisibleFields(DevRequirement requirement, String parent, DevEntityClass entitySelected){
		DevReportFieldContainer fieldContainer = LayoutFieldsFormat.getFieldContainer(requirement, parent);
		List<DevReportFieldOptions> fieldOptionsListTmp = fieldContainer.getFieldOptionsList();
		List<DevReportFieldOptions> fieldOptionsList = new ArrayList<DevReportFieldOptions>();
		if(fieldOptionsListTmp.isEmpty()){
			fieldOptionsList = generateOptionsForAll(entitySelected);
		}
		for (DevReportFieldOptions devReportFieldOptions : fieldOptionsListTmp) {	
			Object obj = LayoutFieldsFormat.loadOpt(devReportFieldOptions, "visible").getOptionsValue();
			if(obj!=null && Boolean.valueOf(obj.toString())){
				fieldOptionsList.add(devReportFieldOptions);
			}				
		}
		return fieldOptionsList;
	}
	private static List<DevReportFieldOptions> generateOptionsForAll(DevEntityClass entitySelected){
		List<DevReportFieldOptions> fieldOptionsList = new ArrayList<DevReportFieldOptions>();
		List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = entitySelected.getEntityPropertyDescriptorList();
		for (DevEntityPropertyDescriptor devEntityPropertyDescriptor : entityPropertyDescriptorList) {
			DevReportFieldOptions devReportFieldOptions = new DevReportFieldOptions();
			devReportFieldOptions.setEntityPropertyDescriptor(devEntityPropertyDescriptor);
			devReportFieldOptions.setName(devEntityPropertyDescriptor.getPropertyName());
			fieldOptionsList.add(devReportFieldOptions);
		}
		return fieldOptionsList;
	}
	public static List<DevReportFieldOptions> getContainerOptions(DevRequirement requirement, String parent, DevEntityClass entitySelected){
		DevReportFieldContainer fieldContainer = LayoutFieldsFormat.getFieldContainer(requirement, parent);
		List<DevReportFieldOptions> fieldOptionsListTmp = fieldContainer.getFieldOptionsList();
		List<DevReportFieldOptions> fieldOptionsList = new ArrayList<DevReportFieldOptions>();
		for (DevReportFieldOptions devReportFieldOptions : fieldOptionsListTmp) {	
			if(devReportFieldOptions.getEntityPropertyDescriptor()==null){
				fieldOptionsList.add(devReportFieldOptions);
			}				
		}
		return fieldOptionsListTmp;
	}
	public static List<String> getVisibleFieldsNames(DevRequirement requirement, String containerPage, DevEntityClass entity) {
		List<DevReportFieldOptions> listOptions = getVisibleFields(requirement, containerPage, entity);
		List<String> names = new ArrayList<String>();
		for (DevReportFieldOptions devReportFieldOptions : listOptions) {
			names.add(devReportFieldOptions.getName());
		}
		return names;
	}	
}
