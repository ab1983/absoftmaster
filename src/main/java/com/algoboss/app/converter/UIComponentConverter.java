/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.converter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algoboss.core.face.BaseBean;

/**
 *
 * @author Agnaldo
 */
@ConversationScoped
@FacesConverter(value = "ComponentConverter")
public class UIComponentConverter implements javax.faces.convert.Converter, Serializable {

    private static final long serialVersionUID = -406332789399557968L;
    List list = new ArrayList();
    String idAttibute = "id";

    public void setList(List list) {
        this.list = list;
    }

    public static Converter getConverter(final List list) {
        UIComponentConverter converter = new UIComponentConverter();
        converter.setList(list);
        return converter;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object obj = null;
        try {
            for (Iterator it = list.iterator(); it.hasNext();) {
                UIComponent object = (UIComponent)it.next();
                if(object.getId().equals(value)){
                    obj = object;
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            return obj;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Class objClass = null;
        if (value != null && list != null) {
            objClass = value.getClass();
            list.add(value);
        }
        Object obj = value;
        String str = "";
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            if (objClass != null && idAttibute != null && UIComponentBase.class.getDeclaredField(idAttibute) != null && obj != null) {
                Field field = UIComponentBase.class.getDeclaredField(idAttibute);
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
}
