/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Agnaldo
 */
@ManagedBean
@SessionScoped
public class GerHomeBean  implements Serializable {

    private static final long serialVersionUID = 1L;
          
    private List<String[]> images;

    public List<String[]> getImages() {
        images = new ArrayList();
        //images.add(new String[]{"web-based-software.jpg","Sistema web","Acesso seguro de qualquer lugar do mundo a qualquer momento."});        
        //images.add(new String[]{"browsers.jpg","Não requer instalação","Basta estar conectado a internet e utilizar o navegador de sua preferência."});
        images.add(new String[]{"100ponline-device.png","Liberte o seu negócio","Ao subscrever o serviço recebe os dados de login, estando de imediato criadas as condições para começar a trabalhar a partir de qualquer lugar e de qualquer máquina; sem necessitar de configurações iniciais morosas."});
        images.add(new String[]{"organize-process.png","Um sistema do seu jeito","A AlgoBoss acredita que processos devem servir pessoas. Modelos de negócio inovadores e diferenciados demandam sistemas que organizem informações e processos a sua maneira."});
        return images;
    }
  
    @PostConstruct  
    public void init() {          
        //theme = gp.getTheme();  
        images = new ArrayList();
        images.add(new String[]{"browsers.jpg","Navegação"});
        images.add(new String[]{"mobile-applications-asg.jpg","Navegação"});
        images.add(new String[]{"web-based-software.jpg","Navegação"});
              
        
    }  
      
}  
