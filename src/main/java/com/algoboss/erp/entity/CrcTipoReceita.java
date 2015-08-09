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
       name="findAllCrcTipoReceita",
       query="select t from CrcTipoReceita t")
})
@Entity
@Table(name="crc_tipo_receita")
public class CrcTipoReceita extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CrcTipoReceita", sequenceName="sequence_crc_tipo_receita", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CrcTipoReceita")
    @Column(name="tipo_receita_id")
    private Long tipoReceitaId;
    private String codigo;
    private String descricao;
    private String classificador;
    @Column(name="aceita_lancamento")
    private boolean aceitaLancamento;

    public boolean isAceitaLancamento() {
        return aceitaLancamento;
    }

    public void setAceitaLancamento(boolean aceitaLancamento) {
        this.aceitaLancamento = aceitaLancamento;
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

    public Long getTipoReceitaId() {
        return tipoReceitaId;
    }

    public void setTipoReceitaId(Long tipoReceitaId) {
        this.tipoReceitaId = tipoReceitaId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CrcTipoReceita other = (CrcTipoReceita) obj;
        if (this.tipoReceitaId != other.tipoReceitaId && (this.tipoReceitaId == null || !this.tipoReceitaId.equals(other.tipoReceitaId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.tipoReceitaId != null ? this.tipoReceitaId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CrcTipoReceita{" + "tipoReceitaId=" + tipoReceitaId + ", codigo=" + codigo + ", descricao=" + descricao + ", classificador=" + classificador + ", aceitaLancamento=" + aceitaLancamento + '}';
    }
    
}
