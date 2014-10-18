/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllGrtCronograma",
    query = "select t from GrtCronograma t")
})
@Entity
@Table(name = "grt_cronograma")
public class GrtCronograma extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "GrtCronograma",sequenceName="sequence_grt_cronograma", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GrtCronograma")
    @Column(name = "cronograma_id")
    private Long cronogramaId;
    private String tipoEvento;
    private String descricao;
    private String observacao;
    @Column(name = "data_hora_inicio_previsto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInicioPrevisto;
    @Column(name = "data_hora_termino_previsto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraTerminoPrevisto;
    @Column(name = "data_hora_inicio_realizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraInicioRealizado;
    @Column(name = "data_hora_termino_realizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraTerminoRealizado;
    private String status;
    @ManyToOne
    @JoinColumn(name="projeto_id",nullable=false)
    private GrtProjeto projeto;      

    public Long getCronogramaId() {
        return cronogramaId;
    }

    public void setCronogramaId(Long cronogramaId) {
        this.cronogramaId = cronogramaId;
    }

    public Date getDataHoraInicioPrevisto() {
        return dataHoraInicioPrevisto;
    }

    public void setDataHoraInicioPrevisto(Date dataHoraInicioPrevisto) {
        this.dataHoraInicioPrevisto = dataHoraInicioPrevisto;
    }

    public Date getDataHoraInicioRealizado() {
        return dataHoraInicioRealizado;
    }

    public void setDataHoraInicioRealizado(Date dataHoraInicioRealizado) {
        this.dataHoraInicioRealizado = dataHoraInicioRealizado;
    }

    public Date getDataHoraTerminoPrevisto() {
        return dataHoraTerminoPrevisto;
    }

    public void setDataHoraTerminoPrevisto(Date dataHoraTerminoPrevisto) {
        this.dataHoraTerminoPrevisto = dataHoraTerminoPrevisto;
    }

    public Date getDataHoraTerminoRealizado() {
        return dataHoraTerminoRealizado;
    }

    public void setDataHoraTerminoRealizado(Date dataHoraTerminoRealizado) {
        this.dataHoraTerminoRealizado = dataHoraTerminoRealizado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public GrtProjeto getProjeto() {
        return projeto;
    }

    public void setProjeto(GrtProjeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GrtCronograma other = (GrtCronograma) obj;
        if (this.cronogramaId != other.cronogramaId && (this.cronogramaId == null || !this.cronogramaId.equals(other.cronogramaId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.cronogramaId != null ? this.cronogramaId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GrtCronograma{" + "cronogramaId=" + cronogramaId + ", tipoEvento=" + tipoEvento + ", descricao=" + descricao + ", observacao=" + observacao + ", dataHoraInicioPrevisto=" + dataHoraInicioPrevisto + ", dataHoraTerminoPrevisto=" + dataHoraTerminoPrevisto + ", dataHoraInicioRealizado=" + dataHoraInicioRealizado + ", dataHoraTerminoRealizado=" + dataHoraTerminoRealizado + ", status=" + status + '}';
    }        
    
}
