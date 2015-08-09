/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.algoboss.core.entity.GenericEntity;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllGobUnidadeObra",
       query="select t from GobUnidadeObra t")
})
@Entity
@Table(name="gob_unidade_obra")
public class GobUnidadeObra extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobUnidadeObra",sequenceName="sequence_gob_unidade_obra",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobUnidadeObra")
    @Column(name="unidade_obra_id")
    private Long unidadeObraId;
    private Integer sequencia;
    @Column(name = "data_inicio_previsto")
    @Temporal(TemporalType.DATE)
    private Date dataInicioPrevisto;
    @Column(name = "data_termino_previsto")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoPrevisto;
    @Column(name = "data_inicio_realizado")
    @Temporal(TemporalType.DATE)
    private Date dataInicioRealizado;
    @Column(name = "data_termino_realizado")
    @Temporal(TemporalType.DATE)
    private Date dataTerminoRealizado;
    @ManyToOne 
    @JoinColumn(name="obra_id")
    private GobObra obra;
    @Column(name="status_imovel")
    private String statusImovel;    
    private String status;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="cronograma_id")
    private GrtCronograma cronograma = new GrtCronograma();     

    public Date getDataInicioPrevisto() {
        return dataInicioPrevisto;
    }

    public void setDataInicioPrevisto(Date dataInicioPrevisto) {
        this.dataInicioPrevisto = dataInicioPrevisto;
    }

    public Date getDataInicioRealizado() {
        return dataInicioRealizado;
    }

    public void setDataInicioRealizado(Date dataInicioRealizado) {
        this.dataInicioRealizado = dataInicioRealizado;
    }

    public Date getDataTerminoPrevisto() {
        return dataTerminoPrevisto;
    }

    public void setDataTerminoPrevisto(Date dataTerminoPrevisto) {
        this.dataTerminoPrevisto = dataTerminoPrevisto;
    }

    public Date getDataTerminoRealizado() {
        return dataTerminoRealizado;
    }

    public void setDataTerminoRealizado(Date dataTerminoRealizado) {
        this.dataTerminoRealizado = dataTerminoRealizado;
    }

    public GobObra getObra() {
        return obra;
    }

    public void setObra(GobObra obra) {
        this.obra = obra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUnidadeObraId() {
        return unidadeObraId;
    }

    public void setUnidadeObraId(Long unidadeObraId) {
        this.unidadeObraId = unidadeObraId;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public GrtCronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(GrtCronograma cronograma) {
        this.cronograma = cronograma;
    }
    public String getStatusImovel() {
        return statusImovel;
    }

    public void setStatusImovel(String statusImovel) {
        this.statusImovel = statusImovel;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobUnidadeObra other = (GobUnidadeObra) obj;
        if (this.unidadeObraId != other.unidadeObraId && (this.unidadeObraId == null || !this.unidadeObraId.equals(other.unidadeObraId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.unidadeObraId != null ? this.unidadeObraId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GobUnidadeObra{" + "unidadeObraId=" + unidadeObraId + ", dataInicioPrevisto=" + dataInicioPrevisto + ", dataTerminoPrevisto=" + dataTerminoPrevisto + ", dataInicioRealizado=" + dataInicioRealizado + ", dataTerminoRealizado=" + dataTerminoRealizado + ", obra=" + obra + ", status=" + status + '}';
    }
    
}
