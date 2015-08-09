package com.algoboss.erp.business;

import java.util.Date;
import java.util.Objects;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.face.AdmAlgoappBean;

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
