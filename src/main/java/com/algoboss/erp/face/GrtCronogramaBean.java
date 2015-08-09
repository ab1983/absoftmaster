/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GenericBean;
import com.algoboss.erp.entity.GrtCronograma;
import com.algoboss.erp.entity.GrtProjeto;

/**
 *
 * @author Agnaldo
 */
@ManagedBean
@SessionScoped
public class GrtCronogramaBean extends GenericBean<GrtCronograma> {
    @Inject
    private BaseDao baseDao;    
    private List<String> projetoIdFiltroList;
    private List<String> tipoEventoFiltroList;
    private List<String[]> tipoEventoList;
    private List<GrtProjeto> projetoList;
    private ScheduleModel eventModel;  
    private boolean cronogramaConcluido;
      
    private ScheduleEvent event = new DefaultScheduleEvent(); 
    /**
     * Creates a new instance of TipoDespesaBean
     */
    public GrtCronogramaBean() {
        super.url = "views/gerenciamento-rotina/cronograma/grtCronogramaList.xhtml";
        super.namedFindAll = "findAllGrtCronograma";
        super.type = GrtCronograma.class;
        super.urlForm = "grtCronogramaForm.xhtml";
        super.subtitle = "Operacional | Gerenciamento da Rotina | Cronograma";         
         
    }

    public List<GrtProjeto> getProjetoList() {
        return projetoList;
    }

    public List<String> getProjetoIdFiltroList() {
        return projetoIdFiltroList;
    }

    public void setProjetoIdFiltroList(List<String> projetoIdFiltroList) {
        this.projetoIdFiltroList = projetoIdFiltroList;
    }

    public List<String> getTipoEventoFiltroList() {
        return tipoEventoFiltroList;
    }

    public void setTipoEventoFiltroList(List<String> tipoEventoFiltroList) {
        this.tipoEventoFiltroList = tipoEventoFiltroList;
    }

    public List<String[]> getTipoEventoList() {
        return tipoEventoList;
    }

    public boolean isCronogramaConcluido() {
        return cronogramaConcluido;
    }

    public void setCronogramaConcluido(boolean cronogramaConcluido) {
        this.cronogramaConcluido = cronogramaConcluido;
    }
    
    public Date getRandomDate(Date base) {  
        Calendar date = Calendar.getInstance();  
        date.setTime(base);  
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month  
          
        return date.getTime();  
    }  
      
    public Date getInitialDate() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);  
          
        return calendar.getTime();  
    }  
      
    public ScheduleModel getEventModel() {  
        return eventModel;  
    }        
            
    public ScheduleEvent getEvent() {  
        return event;  
    }  
  
    public void setEvent(ScheduleEvent event) {  
        this.event = event;  
    }  
      
    public void addEvent(ActionEvent actionEvent) {  
        if(event.getId() == null)  
            eventModel.addEvent(event);  
        else  
            eventModel.updateEvent(event);  
          
        event = new DefaultScheduleEvent();  
    }  
      
    public void onEventSelect(SelectEvent selectEvent) {  
        event = (ScheduleEvent) selectEvent.getObject(); 
        for (GrtCronograma grtCronograma : beanList) {
            if(event.getTitle().equals(grtCronograma.getDescricao()) && event.getEndDate().equals(grtCronograma.getDataHoraTerminoPrevisto())){
                bean = grtCronograma;
                break;
            }
        }
    }  
      
    public void onDateSelect(SelectEvent selectEvent) {  
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());  
    }  
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
          
        addMessage(message);  
    }  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  

    @Override
    public void indexBean(Long nro) {
        try {
            super.indexBean(nro);
            //beanList
            projetoIdFiltroList = new ArrayList();
            projetoList = (List<GrtProjeto>) (Object)baseDao.findAll("findAllGrtProjeto");
            for (GrtProjeto projeto : projetoList) {
                projetoIdFiltroList.add(String.valueOf(projeto.getProjetoId()));
            }        
            tipoEventoFiltroList = new ArrayList();
            tipoEventoList = new ArrayList();
            tipoEventoList.add(new String[]{"Contas a Pagar","Contas a Pagar"});
            tipoEventoList.add(new String[]{"Contas a Receber","Contas a Receber"});
            tipoEventoList.add(new String[]{"Cronograma Obra","Cronograma Obra"});
            /*
            tipoEventoList.add(new String[]{"Alocacao Equipamento","Alocação Equipamento"});
            tipoEventoList.add(new String[]{"Alocacao Mao de Obra","Alocação Mão de Obra"});
            * 
            */
            for (String[] tipoEvento : tipoEventoList) {
                tipoEventoFiltroList.add(tipoEvento[0]);
            }
            cronogramaConcluido = false;
            atualizar();
        } catch (Throwable ex) {
            Logger.getLogger(GrtCronogramaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doBeanList() {
        super.doBeanList();
        //populateBeanList();
    }

    @Override
    public void doBeanSave() {
        try {
            if(cronogramaConcluido){
                bean.setStatus("Concluido");
            }
            baseDao.save(bean);
            doBeanList();
            cronogramaConcluido = false;
            atualizar();
        } catch (Throwable ex) {
            Logger.getLogger(GrtCronogramaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void atualizar(){
        eventModel = new DefaultScheduleModel();  
         for (int i = 0; i < beanList.size(); i++) {
            GrtCronograma grtCronograma = beanList.get(i);
            if(tipoEventoFiltroList.contains(grtCronograma.getTipoEvento()) && grtCronograma.getProjeto()!=null && projetoIdFiltroList.contains(String.valueOf(
                grtCronograma.getProjeto().getProjetoId()))){
                DefaultScheduleEvent evt = new DefaultScheduleEvent(grtCronograma.getDescricao(), grtCronograma.getDataHoraInicioPrevisto(), grtCronograma.getDataHoraTerminoPrevisto(),true);
                evt.setId(String.valueOf(grtCronograma.getCronogramaId()));
                evt.setStyleClass("pendente");
                if(grtCronograma.getDataHoraTerminoPrevisto().before(new Date())){
                    evt.setStyleClass("atrasado");
                }
                if(grtCronograma.getStatus()!=null && grtCronograma.getStatus().equals("Concluido")){
                    evt.setStyleClass("concluido");
                }
                eventModel.addEvent(evt);                              
                
            }
        }
    }
}