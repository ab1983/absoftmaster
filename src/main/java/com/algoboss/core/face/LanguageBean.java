/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        //countries.put("English", Locale.ENGLISH); //label, value
        countries.put("English", new Locale("en", "US")); //label, value
        countries.put("Português", new Locale("pt", "BR")); //label, value
        countries.put("Español", new Locale("es")); //label, value
        //countries.put("Chinese", Locale.SIMPLIFIED_CHINESE);
        //FacesContext.getCurrentInstance().getViewRoot().getLocale();
        localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
    }

    public LanguageBean() {
        //FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.getDefault());
        localeCode = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().toString();
    }

    public Map<String, Object> getCountriesInMap() {
        return countries;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            String newLocaleValue = e.getNewValue().toString();
            System.out.println("Locale: "+newLocaleValue);
            //loop country map to compare the locale code
            updateLocale(newLocaleValue);
        }
    }

    public void updateLocale(String newLocaleValue) {
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
                TimeZone timeZone = TimeZone.getTimeZone("GMT");
                timeZone.setRawOffset(-3*60*60*1000);
                break;
            }
        }
    }
}
