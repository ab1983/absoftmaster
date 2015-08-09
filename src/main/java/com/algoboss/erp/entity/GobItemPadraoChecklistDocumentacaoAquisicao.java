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
       name="findAllGobItemPadraoChecklistDocumentacaoAquisicao",
       query="select t from GobItemPadraoChecklistDocumentacaoAquisicao t")
})
@Entity
@Table(name="gob_item_padrao_checklist_documentacao_aquisicao")
public class GobItemPadraoChecklistDocumentacaoAquisicao extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobItemPadraoChecklistDocumentacaoAquisicao",sequenceName="sequence_gob_item_padrao_checklist_documentacao_aquisicao",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobItemPadraoChecklistDocumentacaoAquisicao")
    @Column(name="item_padrao_checklist_documentacao_aquisicao_id")
    private Long itemPadraoChecklistDocumentacaoAquisicaoId;
    private String descricao;
    private String categoria;
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getItemPadraoChecklistDocumentacaoAquisicaoId() {
        return itemPadraoChecklistDocumentacaoAquisicaoId;
    }

    public void setItemPadraoChecklistDocumentacaoAquisicaoId(Long itemPadraoChecklistDocumentacaoAquisicaoId) {
        this.itemPadraoChecklistDocumentacaoAquisicaoId = itemPadraoChecklistDocumentacaoAquisicaoId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobItemPadraoChecklistDocumentacaoAquisicao other = (GobItemPadraoChecklistDocumentacaoAquisicao) obj;
        if (this.itemPadraoChecklistDocumentacaoAquisicaoId != other.itemPadraoChecklistDocumentacaoAquisicaoId && (this.itemPadraoChecklistDocumentacaoAquisicaoId == null || !this.itemPadraoChecklistDocumentacaoAquisicaoId.equals(other.itemPadraoChecklistDocumentacaoAquisicaoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.itemPadraoChecklistDocumentacaoAquisicaoId != null ? this.itemPadraoChecklistDocumentacaoAquisicaoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GobItemPadraoChecklistDocumentacaoAquisicao{" + "itemPadraoChecklistDocumentacaoAquisicaoId=" + itemPadraoChecklistDocumentacaoAquisicaoId + ", descricao=" + descricao + ", categoria=" + categoria + ", ativo=" + ativo + '}';
    }    
}
