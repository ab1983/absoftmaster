/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
       name="findAllCbcPlanejamentoOrcamentario",
       query="select t from CbcPlanejamentoOrcamentario t")
})
@Entity
@Table(name="cbc_planejamento_orcamentario")
public class CbcPlanejamentoOrcamentario extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CbcPlanejamentoOrcamentario",sequenceName="sequence_cbc_planejamento_orcamentario", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CbcPlanejamentoOrcamentario")
    @Column(name="planejamento_orcamentario_id")
    private Long planejamentoOrcamentarioId;
    @ManyToOne()
    @JoinColumn(name="tipo_despesa_id")
    private CpgTipoDespesa tipoDespesa = new CpgTipoDespesa();
    @ManyToOne()
    @JoinColumn(name="centro_custo_id")
    private GerCentroCusto centroCusto = new GerCentroCusto();
    @Column(precision=12,scale=4)
    private BigDecimal valor;
    @Column(name="periodo_vigencia") 
    @Temporal(TemporalType.DATE)
    private Date periodoVigencia;

    public GerCentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(GerCentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Date getPeriodoVigencia() {
        return periodoVigencia;
    }

    public void setPeriodoVigencia(Date periodoVigencia) {
        this.periodoVigencia = periodoVigencia;
    }

    public Long getPlanejamentoOrcamentarioId() {
        return planejamentoOrcamentarioId;
    }

    public void setPlanejamentoOrcamentarioId(Long planejamentoOrcamentarioId) {
        this.planejamentoOrcamentarioId = planejamentoOrcamentarioId;
    }

    public CpgTipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(CpgTipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CbcPlanejamentoOrcamentario other = (CbcPlanejamentoOrcamentario) obj;
        if (this.planejamentoOrcamentarioId != other.planejamentoOrcamentarioId && (this.planejamentoOrcamentarioId == null || !this.planejamentoOrcamentarioId.equals(other.planejamentoOrcamentarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.planejamentoOrcamentarioId != null ? this.planejamentoOrcamentarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CbcPlanejamentoOrcamentario{" + "planejamentoOrcamentarioId=" + planejamentoOrcamentarioId + ", tipoDespesa=" + tipoDespesa + ", centroCusto=" + centroCusto + ", valor=" + valor + ", periodoVigencia=" + periodoVigencia + '}';
    }        
    
}
