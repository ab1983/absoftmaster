/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.converter;

/*
* JBoss, Home of Professional Open Source
* Copyright 2011, Red Hat, Inc., and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

//import org.jboss.solder.logging.Logger;

/**
* A basic converter that works with any kind of object. NOTE: It should only be used in long
* running conversations!
*
* @author <a href="http://community.jboss.org/people/LightGuard">Jason Porter</a>a>
*/
@ConversationScoped
@FacesConverter("ObjectConverter")
public class ObjectConverter implements javax.faces.convert.Converter, Serializable {

    private static final long serialVersionUID = -406332789399557968L;
    final private Map<String, Object> converterMap = new HashMap<String, Object>();
    final private Map<Object, String> reverseConverterMap = new HashMap<Object, String>();

    @Inject
    private transient Conversation conversation;
    //private transient Session session;

    private final transient Logger log = Logger.getLogger(ObjectConverter.class.getName());

    private int incrementor = 1;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
        if (this.conversation!=null && this.conversation.isTransient()) {
            log.warning("Conversion attempted without a long running conversation");
        }

        return this.converterMap.get(value);
            
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try{
        if (this.conversation!=null && this.conversation.isTransient()) {
            log.warning("Conversion attempted without a long running conversation");
        }

        if (this.reverseConverterMap.containsKey(value)) {
            return this.reverseConverterMap.get(value);
        } else {
            final String incrementorStringValue = String.valueOf(this.incrementor++);
            this.converterMap.put(incrementorStringValue, value);
            this.reverseConverterMap.put(value, incrementorStringValue);
            return incrementorStringValue;
        }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}    

