/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.algoboss.erp.entity.DevEntityPropertyValue;
import com.algoboss.erp.face.BaseBean;

/**
 *
 * @author Agnaldo
 */
public class AlgoUtil {
    public static String normalizerName(String name){
        return Normalizer
                    .normalize(name, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9]+", "").toLowerCase();
    }
 public static Number toNumber(Object propertyValue){    
	 if(propertyValue==null){
		 return null;
	 }
	 Number obj = null;
	 try{
		try {
			obj = Double.valueOf(String.valueOf(propertyValue));
		} catch (Exception e) {
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator(BaseBean.getBundle("decimalSeparator", "msg").charAt(0));
			dfs.setGroupingSeparator(BaseBean.getBundle("thousandsSeparator", "msg").charAt(0));
			df.setDecimalFormatSymbols(dfs);
			obj = df.parse(String.valueOf(propertyValue));
			// TODO: handle exception
		} 
	 } catch (Exception ex) {
	     Logger.getLogger(DevEntityPropertyValue.class.getName()).log(Level.SEVERE, null, ex);
	 } 
	return obj;
}
 public static String extractName(String normalizedName){
	 return AlgoUtil.normalizerName(normalizedName.substring(normalizedName.indexOf("_")+1));
 }
}
