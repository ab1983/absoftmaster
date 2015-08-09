/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.algoboss.core.entity.GenericEntity;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllCpgTipoDespesa",
       query="select t from CpgTipoDespesa t")
})
@Entity
@Table(name="cpg_tipo_despesa")
public class CpgTipoDespesa extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CpgTipoDespesa",sequenceName="sequence_cpg_tipo_despesa",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CpgTipoDespesa")
    @Column(name="tipo_despesa_id")
    private Long tipoDespesaId;
    private String codigo;
    private String descricao;
    private String classificador;

    public Long getTipoDespesaId() {
        return tipoDespesaId;
    }

    public void setTipoDespesaId(Long tipoDespesaId) {
        this.tipoDespesaId = tipoDespesaId;
    }

    public String getClassificador() {
        return classificador;
    }

    public void setClassificador(String classificador) {
        this.classificador = classificador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CpgTipoDespesa other = (CpgTipoDespesa) obj;
        if (this.tipoDespesaId != other.tipoDespesaId && (this.tipoDespesaId == null || !this.tipoDespesaId.equals(other.tipoDespesaId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.tipoDespesaId != null ? this.tipoDespesaId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TipoDespesa{" + "tipoDespesaId=" + tipoDespesaId + ", codigo=" + codigo + ", descricao=" + descricao + ", classificador=" + classificador + '}';
    }
    
}
