/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllGrtProjeto",
    query = "select t from GrtProjeto t")
})
@Entity
@Table(name = "grt_projeto")
public class GrtProjeto extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "GrtProjeto",sequenceName="sequence_grt_projeto", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GrtProjeto")
    @Column(name = "projeto_id")
    private Long projetoId;
    private String nome;
    private String descricao;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name = "centro_custo_id")
    private GerCentroCusto centroCusto = new GerCentroCusto();    
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="projeto_id")
    private List<GrtCronograma> grtCronogramaList = new ArrayList<GrtCronograma>();    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public GerCentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(GerCentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public List<GrtCronograma> getGrtCronogramaList() {
        return grtCronogramaList;
    }

    public void setGrtCronogramaList(List<GrtCronograma> grtCronogramaList) {
        this.grtCronogramaList = grtCronogramaList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GrtProjeto other = (GrtProjeto) obj;
        if (this.projetoId != other.projetoId && (this.projetoId == null || !this.projetoId.equals(other.projetoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.projetoId != null ? this.projetoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GrtProjeto{" + "projetoId=" + projetoId + ", nome=" + nome + ", descricao=" + descricao + ", grtCronogramaList=" + grtCronogramaList + '}';
    } 
    
}
