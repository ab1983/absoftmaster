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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
       name="findAllGobChecklistDocumentacaoAquisicao",
       query="select t from GobChecklistDocumentacaoAquisicao t")
})
@Entity
@Table(name="gob_checklist_documentacao_aquisicao")
public class GobChecklistDocumentacaoAquisicao extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobChecklistDocumentacaoAquisicao",sequenceName="sequence_gob_checklist_documentacao_aquisicao",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobChecklistDocumentacaoAquisicao")
    @Column(name="checklist_documentacao_aquisicao_id")
    private Long checklistDocumentacaoAquisicaoId;
    private boolean entregue;
    @ManyToOne()
    @JoinColumn(name="item_padrao_checklist_documentacao_aquisicao_id")
    private GobItemPadraoChecklistDocumentacaoAquisicao itemPadraoChecklistDocumentacaoAquisicao = new GobItemPadraoChecklistDocumentacaoAquisicao();

    public Long getChecklistDocumentacaoAquisicaoId() {
        return checklistDocumentacaoAquisicaoId;
    }

    public void setChecklistDocumentacaoAquisicaoId(Long checklistDocumentacaoAquisicaoId) {
        this.checklistDocumentacaoAquisicaoId = checklistDocumentacaoAquisicaoId;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

    public GobItemPadraoChecklistDocumentacaoAquisicao getItemPadraoChecklistDocumentacaoAquisicao() {
        return itemPadraoChecklistDocumentacaoAquisicao;
    }

    public void setItemPadraoChecklistDocumentacaoAquisicao(GobItemPadraoChecklistDocumentacaoAquisicao itemPadraoChecklistDocumentacaoAquisicao) {
        this.itemPadraoChecklistDocumentacaoAquisicao = itemPadraoChecklistDocumentacaoAquisicao;
    }

    @Override
    public String toString() {
        return "GobChecklistDocumentacaoAquisicao{" + "checklistDocumentacaoAquisicaoId=" + checklistDocumentacaoAquisicaoId + ", entregue=" + entregue + ", itemPadraoChecklistDocumentacaoAquisicao=" + itemPadraoChecklistDocumentacaoAquisicao + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobChecklistDocumentacaoAquisicao other = (GobChecklistDocumentacaoAquisicao) obj;
        if (this.checklistDocumentacaoAquisicaoId != other.checklistDocumentacaoAquisicaoId && (this.checklistDocumentacaoAquisicaoId == null || !this.checklistDocumentacaoAquisicaoId.equals(other.checklistDocumentacaoAquisicaoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.checklistDocumentacaoAquisicaoId != null ? this.checklistDocumentacaoAquisicaoId.hashCode() : 0);
        return hash;
    }
    
}
