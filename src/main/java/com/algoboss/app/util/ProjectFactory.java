/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.util;

import java.util.List;

import com.algoboss.app.entity.DevEntityPropertyDescriptor;

/**
 *
 * @author Agnaldo
 */
public class ProjectFactory {
    private boolean edit;
    private boolean exclude;
    private boolean include;
    private boolean saveAndBack;
    private boolean save;
    private boolean back;
    private boolean listVisible;
    private List<DevEntityPropertyDescriptor> propertySelectedItemsList;    
    private List<DevEntityPropertyDescriptor> propertySelectedItemsForm;    

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public boolean isSaveAndBack() {
        return saveAndBack;
    }

    public void setSaveAndBack(boolean saveAndBack) {
        this.saveAndBack = saveAndBack;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public List<DevEntityPropertyDescriptor> getPropertySelectedItemsList() {
        return propertySelectedItemsList;
    }

    public void setPropertySelectedItemsList(List<DevEntityPropertyDescriptor> propertySelectedItemsList) {
        this.propertySelectedItemsList = propertySelectedItemsList;
    }

    public List<DevEntityPropertyDescriptor> getPropertySelectedItemsForm() {
        return propertySelectedItemsForm;
    }

    public void setPropertySelectedItemsForm(List<DevEntityPropertyDescriptor> propertySelectedItemsForm) {
        this.propertySelectedItemsForm = propertySelectedItemsForm;
    }
    
    
}
