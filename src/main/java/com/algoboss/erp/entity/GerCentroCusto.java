/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllGerCentroCusto", query = "select t from GerCentroCusto t")})
@Table(name = "ger_centro_custo")
@javax.persistence.Entity
public class GerCentroCusto extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "GerCentroCusto", sequenceName = "sequence_ger_centro_custo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GerCentroCusto")
    @Column(name = "centro_custo_id")
    private Long centroCustoId;
    private String codigo;
    private String descricao;
    private String classificador;

    public Long getCentroCustoId() {
        return centroCustoId;
    }

    public void setCentroCustoId(Long centroCustoId) {
        this.centroCustoId = centroCustoId;
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
        final GerCentroCusto other = (GerCentroCusto) obj;
        if (this.centroCustoId != other.centroCustoId && (this.centroCustoId == null || !this.centroCustoId.equals(other.centroCustoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.centroCustoId != null ? this.centroCustoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CentroCusto{" + "centroCustoId=" + centroCustoId + ", codigo=" + codigo + ", descricao=" + descricao + ", classificador=" + classificador + '}';
    }
}
