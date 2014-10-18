/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllGobStatusAquisicao",
       query="select t from GobStatusAquisicao t order by t.statusAquisicaoImovelId")
})
@Entity
@Table(name="gob_status_aquisicao")
public class GobStatusAquisicao extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobStatusAquisicao",sequenceName="sequence_gob_status_aquisicao",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobStatusAquisicao")
    @Column(name="status_aquisicao_imovel_id")
    private Long statusAquisicaoImovelId;
    private String codigo;
    private String descricao;
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public Long getStatusAquisicaoImovelId() {
        return statusAquisicaoImovelId;
    }

    public void setStatusAquisicaoImovelId(Long statusAquisicaoImovelId) {
        this.statusAquisicaoImovelId = statusAquisicaoImovelId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobStatusAquisicao other = (GobStatusAquisicao) obj;
        if (this.statusAquisicaoImovelId != other.statusAquisicaoImovelId && (this.statusAquisicaoImovelId == null || !this.statusAquisicaoImovelId.equals(other.statusAquisicaoImovelId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.statusAquisicaoImovelId != null ? this.statusAquisicaoImovelId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GobStatusAquisicao{" + "statusAquisicaoImovelId=" + statusAquisicaoImovelId + ", codigo=" + codigo + ", descricao=" + descricao + ", ativo=" + ativo + '}';
    }
    
    

    
}
