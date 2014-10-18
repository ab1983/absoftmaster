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
       name="findAllFuncionario",
       query="select t from Funcionario t")
})
@Entity
public class Funcionario extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Funcionario",sequenceName="sequence_funcionario", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="Funcionario")
    @Column(name="funcionario_id")
    private Long funcionarioId;
    private String codigo;
    private String cargo;
    @Column(name="departamento_id")
    private Integer departamentoId;
    @Column(name="data_admissao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAdmissao;
    @Column(name="data_prox_ferias")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataProxFerias;
    @Column(name="data_anter_ferias")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAnterFerias;
    private String email;
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    @Column(name="nome_pai")
    private String nomePai;
    @Column(name="nome_mae")
    private String nomeMae;
    private String nacionalidade;
    private String status;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataAnterFerias() {
        return dataAnterFerias;
    }

    public void setDataAnterFerias(Date dataAnterFerias) {
        this.dataAnterFerias = dataAnterFerias;
    }

    public Date getDataProxFerias() {
        return dataProxFerias;
    }

    public void setDataProxFerias(Date dataProxFerias) {
        this.dataProxFerias = dataProxFerias;
    }

    public Integer getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Integer departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Funcionario other = (Funcionario) obj;
        if (this.funcionarioId != other.funcionarioId && (this.funcionarioId == null || !this.funcionarioId.equals(other.funcionarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.funcionarioId != null ? this.funcionarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "funcionarioId=" + funcionarioId + ", codigo=" + codigo + ", cargo=" + cargo + ", departamentoId=" + departamentoId + ", dataAdmissao=" + dataAdmissao + ", dataProxFerias=" + dataProxFerias + ", dataAnterFerias=" + dataAnterFerias + ", email=" + email + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", nomePai=" + nomePai + ", nomeMae=" + nomeMae + ", nacionalidade=" + nacionalidade + ", status=" + status + '}';
    }
        
}
