/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllGerTipoDocumento",
       query="select t from GerTipoDocumento t")
})
@Entity
@Table(name="ger_tipo_documento")
public class GerTipoDocumento extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GerTipoDocumento",sequenceName="sequence_ger_tipo_documento",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GerTipoDocumento")
    @Column(name="tipo_documento_id")
    private Long tipoDocumentoId;
    private String codigo;
    private String descricao;
    @Column(name="exige_liberacao")
    private boolean exigeLiberacao;
    @Column(name="substituir_documento")
    private boolean substituiDocumento;

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

    public boolean isExigeLiberacao() {
        return exigeLiberacao;
    }

    public void setExigeLiberacao(boolean exigeLiberacao) {
        this.exigeLiberacao = exigeLiberacao;
    }

    public boolean isSubstituiDocumento() {
        return substituiDocumento;
    }

    public void setSubstituiDocumento(boolean substituiDocumento) {
        this.substituiDocumento = substituiDocumento;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerTipoDocumento other = (GerTipoDocumento) obj;
        if (this.tipoDocumentoId != other.tipoDocumentoId && (this.tipoDocumentoId == null || !this.tipoDocumentoId.equals(other.tipoDocumentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.tipoDocumentoId != null ? this.tipoDocumentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CpgTipoDocumento{" + "tipoDocumentoId=" + tipoDocumentoId + ", codigo=" + codigo + ", descricao=" + descricao + ", exigeLiberacao=" + exigeLiberacao + ", substituiDocumento=" + substituiDocumento + '}';
    }
    
    
}
