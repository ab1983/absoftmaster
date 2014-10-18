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
       name="findAllGerFornecedor",
       query="select t from GerFornecedor t")
})
@Entity
@Table(name="ger_fornecedor")
public class GerFornecedor extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GerFornecedor",sequenceName="sequence_ger_fornecedor",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GerFornecedor")
    @Column(name="fornecedor_id")
    private Long fornecedorId;
    @Column(name="company_registration_type")
    private String companyRegistrationType;
    @Column(name="cpf_cnpj")
    private String cpfCnpj;
    @Column(name="razao_social")
    private String razaoSocial;
    @Column(name="nome_fantasia")
    private String nomeFantasia;
    private String endereco;
    private String cidade;
    private String uf;
    private String telefone;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCompanyRegistrationType() {
        return companyRegistrationType;
    }

    public void setCompanyRegistrationType(String companyRegistrationType) {
        this.companyRegistrationType = companyRegistrationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerFornecedor other = (GerFornecedor) obj;
        if (this.fornecedorId != other.fornecedorId && (this.fornecedorId == null || !this.fornecedorId.equals(other.fornecedorId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.fornecedorId != null ? this.fornecedorId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Fornecedor{" + "fornecedorId=" + fornecedorId + ", cpfCnpj=" + cpfCnpj + ", razaoSocial=" + razaoSocial + ", nomeFantasia=" + nomeFantasia + ", endereco=" + endereco + ", cidade=" + cidade + ", uf=" + uf + ", telefone=" + telefone + '}';
    }            
}
