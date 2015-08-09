/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.algoboss.core.entity.GenericEntity;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllGobConjugueAquisicao",
       query="select t from GobConjugueAquisicao t")
})
@Entity
@Table(name="gob_conjugue_aquisicao")
public class GobConjugueAquisicao extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobConjugueAquisicao",sequenceName="sequence_gob_conjugue_aquisicao",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobConjugueAquisicao")
    @Column(name="conjugue_aquisicao_id")
    private Long conjugueAquisicaoId;    
    private String nome;
    private String cpf;
    @Column(name="data_nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    @Column(name="tipo_documento")
    private String tipoDocumento;
    @Column(name="numero_documento")
    private String numeroDocumento;
    @Column(name="orgao_emissor")
    private String orgaoEmissor;
    @Column(name="data_emissao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEmissao;    
    private String naturalidade;
    private String pai;
    private String mae;
    private String escolaridade;
    private String conclusao;
    private String endereco;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String email;
    @Column(name="inicio_residencia")    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicioResidencia;
    private String profissao1;
    private String empresa1;
    private String cnpj1;
    @Column(name="data_inicio1")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicio1;
    private BigDecimal renda1;
    @Column(name="data_ref1")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRef1;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

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

    public String getCnpj1() {
        return cnpj1;
    }

    public void setCnpj1(String cnpj1) {
        this.cnpj1 = cnpj1;
    }

    public String getConclusao() {
        return conclusao;
    }

    public void setConclusao(String conclusao) {
        this.conclusao = conclusao;
    }

    public Long getConjugueAquisicaoId() {
        return conjugueAquisicaoId;
    }

    public void setConjugueAquisicaoId(Long conjugueAquisicaoId) {
        this.conjugueAquisicaoId = conjugueAquisicaoId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataInicio1() {
        return dataInicio1;
    }

    public void setDataInicio1(Date dataInicio1) {
        this.dataInicio1 = dataInicio1;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataRef1() {
        return dataRef1;
    }

    public void setDataRef1(Date dataRef1) {
        this.dataRef1 = dataRef1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa1() {
        return empresa1;
    }

    public void setEmpresa1(String empresa1) {
        this.empresa1 = empresa1;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Date getInicioResidencia() {
        return inicioResidencia;
    }

    public void setInicioResidencia(Date inicioResidencia) {
        this.inicioResidencia = inicioResidencia;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public BigDecimal getRenda1() {
        return renda1;
    }

    public void setRenda1(BigDecimal renda1) {
        this.renda1 = renda1;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getProfissao1() {
        return profissao1;
    }

    public void setProfissao1(String profissao1) {
        this.profissao1 = profissao1;
    }

    @Override
    public String toString() {
        return "GobConjugueAquisicao{" + "conjugueAquisicaoId=" + conjugueAquisicaoId + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", orgaoEmissor=" + orgaoEmissor + ", dataEmissao=" + dataEmissao + ", naturalidade=" + naturalidade + ", pai=" + pai + ", mae=" + mae + ", escolaridade=" + escolaridade + ", conclusao=" + conclusao + ", endereco=" + endereco + ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", email=" + email + ", inicioResidencia=" + inicioResidencia + ", empresa1=" + empresa1 + ", cnpj1=" + cnpj1 + ", dataInicio1=" + dataInicio1 + ", renda1=" + renda1 + ", dataRef1=" + dataRef1 + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobConjugueAquisicao other = (GobConjugueAquisicao) obj;
        if (this.conjugueAquisicaoId != other.conjugueAquisicaoId && (this.conjugueAquisicaoId == null || !this.conjugueAquisicaoId.equals(other.conjugueAquisicaoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.conjugueAquisicaoId != null ? this.conjugueAquisicaoId.hashCode() : 0);
        return hash;
    }

}
