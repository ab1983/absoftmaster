package com.algoboss.integration.small.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.NoResultException;

import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.face.AdmAlgoappBean;


public class OrdemServicoBo {
	public static void preRender(AdmAlgoappBean app) {

		// totalCalc(obj);
	}

	public static void numeroOsGen(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_ordemservico")) {
				Object numero = obj.getPropObj("numero").getPropertyValue();
				if (Objects.toString(numero, "").isEmpty()) {
					
					Object nextnumeroos = 0;
					try {
						nextnumeroos = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT GEN_ID(G_NUMEROOS, 0 ) FROM RDB$DATABASE where not exists(select NUMERO from OS WHERE NUMERO LIKE '%'||(GEN_ID(G_NUMEROOS, 0 ))) and GEN_ID(G_NUMEROOS, 0 ) != 0;").getSingleResult();
					} catch (NoResultException e) {
						nextnumeroos = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT NEXT VALUE FOR G_NUMEROOS FROM RDB$DATABASE;").getSingleResult().toString();
					}
					obj.getPropObj("numero").setPropertyValue(String.format ("%010d", Long.valueOf(nextnumeroos.toString())));
					//obj.getPropObj("especie").setPropertyValue("Caixa");
					//obj.getPropObj("marca").setPropertyValue("Volumes");
					//obj.getPropObj("frete12").setPropertyValue("0");
					
					//obj.getPropObj("tipopreco").setPropertyValue("PREÇO1");
					Date now = new Date();
					obj.getPropObj("data").setPropertyValue(now);
					obj.getPropObj("hora").setPropertyValue(new SimpleDateFormat("HH:mm").format(now));
					obj.getPropObj("datapro").setPropertyValue(now);
					obj.getPropObj("horapro").setPropertyValue(new SimpleDateFormat("HH:mm").format(now));
					obj.getPropObj("situacao").setPropertyValue("AGENDADA");
					obj.getPropObj("identifi1").setPropertyValue("Interno");				
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
