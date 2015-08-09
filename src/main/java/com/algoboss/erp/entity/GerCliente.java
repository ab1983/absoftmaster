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
       name="findAllGerCliente",
       query="select t from GerCliente t")
})
@Entity
@Table(name="ger_cliente")
public class GerCliente extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GerCliente",sequenceName="sequence_ger_cliente",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GerCliente")
    @Column(name="cliente_id")
    private Long clienteId;
    @Column(name="tipo_pessoa")
    private String tipoPessoa;
    private String nome;
    @Column(name="cpf_cnpj")
    private String cpfCnpj;
    private String endereco;
    private String cep;
    private String cidade;
    private String uf;
    private String email;
    private String telefone1;
    private String telefone2;
    private String telefone3;
    private String status;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerCliente other = (GerCliente) obj;
        if (this.clienteId != other.clienteId && (this.clienteId == null || !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.clienteId != null ? this.clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clienteId=" + clienteId + ", tipoPessoa=" + tipoPessoa + ", nome=" + nome + ", cpfCnpj=" + cpfCnpj + ", endereco=" + endereco + ", cep=" + cep + ", cidade=" + cidade + ", uf=" + uf + ", email=" + email + ", telefone1=" + telefone1 + ", telefone2=" + telefone2 + ", telefone3=" + telefone3 + ", status=" + status + '}';
    }
    
}
