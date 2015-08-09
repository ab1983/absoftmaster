/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.converter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.core.face.BaseBean;

/**
 *
 * @author Agnaldo
 */
@ConversationScoped
@FacesConverter(value = "EntityObjectConverter")
public class DevEntityObjectConverter implements javax.faces.convert.Converter, Serializable {

    private static final long serialVersionUID = -406332789399557968L;
    List list = new ArrayList();
    String idAttibute = "entityObjectId";

    public void setList(List list) {
        this.list = list;
    }

    public static Converter getConverter(final List list) {
        DevEntityObjectConverter converter = new DevEntityObjectConverter();
        converter.setList(list);
        return converter;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Class objClass = null;

        Object obj = null;
        if (list != null && !list.isEmpty()) {
            objClass = list.get(0).getClass();
        }
        try {
            if (objClass != null && objClass.getDeclaredField(idAttibute) != null && value != null && !String.valueOf(value).isEmpty()) {
                obj = objClass.newInstance();
                Field field = obj.getClass().getDeclaredField(idAttibute);
                field.setAccessible(true);
                field.set(obj, Long.valueOf(value));
                field.setAccessible(false);
                obj = list.get(list.indexOf(obj));
            }
            //forn.setFornecedorId(Long.valueOf(value));
        } catch (InstantiationException ex) {
            Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            return obj;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Class objClass = null;
        if (value instanceof DevEntityObject && value != null && !value.toString().isEmpty()) {
            objClass = value.getClass();
            if(list != null && !list.contains(value)){
                list.add(value);                
            }
        }
        Object obj = value;
        String str = "";
        //Object id = objClass.getAnnotation(Id.class);
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
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
}
