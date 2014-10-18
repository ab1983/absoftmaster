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
   @NamedQuery(
       name="findAllGobObra",
       query="select t from GobObra t")
})
@Entity
@Table(name="gob_obra")
public class GobObra extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobObra",sequenceName="sequence_gob_obra",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobObra")
    @Column(name="obra_id")
    private Long obraId;
    @Column(name="nome_obra")
    private String nomeObra;
    @Column(name="cliente_id")
    private Integer clienteId;
    private String endereco;
    private String cidade;
    private String cep;
    private String uf;
    private String status;
    @Column(name="tipo_obra")
    private String tipoObra;
    private Integer unidades;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="projeto_id")
    private GrtProjeto projeto = new GrtProjeto();
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="obra_id")
    private List<GobUnidadeObra> unidadeObraList = new ArrayList<GobUnidadeObra>();    
    
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public GrtProjeto getProjeto() {
        return projeto;
    }

    public void setProjeto(GrtProjeto projeto) {
        this.projeto = projeto;
    }
    public void setProjeto(GobObra obra) {
        this.projeto.getCentroCusto().setDescricao(obra.getNomeObra());
        this.projeto.setNome(obra.getNomeObra());
        this.projeto.setDescricao(obra.getEndereco()+"-"+obra.getCidade()+"/"+obra.getUf());
    }    
    public List<GobUnidadeObra> getUnidadeObraList() {
        return unidadeObraList;
    }

    public void setUnidadeObraList(List<GobUnidadeObra> unidadeObraList) {
        this.unidadeObraList = unidadeObraList;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }        

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobObra other = (GobObra) obj;
        if (this.obraId != other.obraId && (this.obraId == null || !this.obraId.equals(other.obraId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.obraId != null ? this.obraId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Obra{" + "obraId=" + obraId + ", nomeObra=" + nomeObra + ", clienteId=" + clienteId + ", endereco=" + endereco + ", cidade=" + cidade + ", cep=" + cep + ", uf=" + uf + ", status=" + status + '}';
    }
    
}
