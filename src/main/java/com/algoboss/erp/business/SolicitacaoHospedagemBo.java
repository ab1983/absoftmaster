package com.algoboss.erp.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyDescriptorConfig;
import com.algoboss.erp.entity.DevReportFieldContainer;
import com.algoboss.erp.entity.DevReportFieldOptions;
import com.algoboss.erp.entity.DevReportFieldOptionsMap;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.face.AdmAlgoappBean;
import com.algoboss.erp.util.AlgoUtil;

public class SolicitacaoHospedagemBo {

	public static void onNew(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1146_solicitacao")) {
				Object numero = obj.getPropObj("seqnum").getPropertyValue();
				if (Objects.toString(numero, "").isEmpty()) {
					obj.getPropObj("seqnum").setPropertyValue("+");
				}
				
				Date now = new Date();
				obj.getPropObj("datasolicitacao").setPropertyValue(now);
				obj.getPropObj("qtdediarias").setPropertyValue(1);
				obj.getPropObj("qtdehospedes").setPropertyValue(1);
				//obj.getPropObj("status").setPropertyValue("Pendente");
				//obj.getPropObj("acomodacao").setPropertyValue("Single");
				   ScriptEngineManager manager = new ScriptEngineManager();
				    ScriptEngine jsEngine;  
				    jsEngine = manager.getEngineByExtension("js");
				    //jsEngine.eval("importPackage(javax.swing);var optionPane =JOptionPane.showMessageDialog(null, 'Hello!);");
				    jsEngine.put("obj", obj);
				    jsEngine.eval("obj.getPropObj('status').setPropertyValue('Pendente')");
				    jsEngine.eval("obj.getPropObj('acomodacao').setPropertyValue('Single')");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void onOpen(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1146_solicitacao")) {
				if(obj.getSeqnum()!=null){
					obj.getPropObj("seqnum").setPropertyValue(obj.getSeqnum());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
