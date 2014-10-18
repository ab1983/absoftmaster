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
   @NamedQuery(
       name="findAllGobAquisicaoImovel",
       query="select t from GobAquisicaoImovel t")
})
@Entity
@Table(name="gob_aquisicao_imovel")
public class GobAquisicaoImovel extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobAquisicaoImovel",sequenceName="sequence_gob_aquisicao_imovel",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobAquisicaoImovel")
    @Column(name="aquisicao_imovel_id")
    private Long aquisicaoImovelId;
    @Column(name="data_cadastro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="aquisicao_imovel_id")
    private List<GobChecklistDocumentacaoAquisicao> checklistDocumentacaoAquisicaoList = new ArrayList<GobChecklistDocumentacaoAquisicao>();       
    @OneToOne   
    @JoinColumn(name="cliente_id")
    private GerCliente cliente;
    @ManyToOne(cascade= {CascadeType.REFRESH},fetch=FetchType.EAGER)
    @JoinColumn(name="unidade_obra_id")
    private GobUnidadeObra unidadeObra;    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="status_aquisicao_imovel_id")
    private GobStatusAquisicao statusAquisicao;

    public Long getAquisicaoImovelId() {
        return aquisicaoImovelId;
    }

    public List<GobChecklistDocumentacaoAquisicao> getChecklistDocumentacaoAquisicaoList() {
        return checklistDocumentacaoAquisicaoList;
    }

    public GerCliente getCliente() {
        return cliente;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public GobStatusAquisicao getStatusAquisicao() {
        return statusAquisicao;
    }

    public GobUnidadeObra getUnidadeObra() {
        return unidadeObra;
    }

    public void setAquisicaoImovelId(Long aquisicaoImovelId) {
        this.aquisicaoImovelId = aquisicaoImovelId;
    }

    public void setChecklistDocumentacaoAquisicaoList(List<GobChecklistDocumentacaoAquisicao> checklistDocumentacaoAquisicaoList) {
        this.checklistDocumentacaoAquisicaoList = checklistDocumentacaoAquisicaoList;
    }

    public void setCliente(GerCliente cliente) {
        this.cliente = cliente;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setStatusAquisicao(GobStatusAquisicao statusAquisicao) {
        this.statusAquisicao = statusAquisicao;
    }

    public void setUnidadeObra(GobUnidadeObra unidadeObra) {
        this.unidadeObra = unidadeObra;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobAquisicaoImovel other = (GobAquisicaoImovel) obj;
        if (this.aquisicaoImovelId != other.aquisicaoImovelId && (this.aquisicaoImovelId == null || !this.aquisicaoImovelId.equals(other.aquisicaoImovelId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.aquisicaoImovelId != null ? this.aquisicaoImovelId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GobAquisicaoImovel{" + "aquisicaoImovelId=" + aquisicaoImovelId + ", dataCadastro=" + dataCadastro + ", checklistDocumentacaoAquisicaoList=" + checklistDocumentacaoAquisicaoList + ", cliente=" + cliente + ", unidadeObra=" + unidadeObra + ", statusAquisicao=" + statusAquisicao + '}';
    }
    
}
