/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
       name="findAllGobComplementacaoAquisicaoClientePf",
       query="select t from GobComplementacaoAquisicaoClientePf t")
})
@Entity
@Table(name="gob_complementacao_aquisicao_cliente_pf")
public class GobComplementacaoAquisicaoClientePf extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GobComplementacaoAquisicaoClientePf",sequenceName="sequence_gob_complementacao_aquisicao_cliente_pf",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GobComplementacaoAquisicaoClientePf")
    @Column(name="complementacao_aquisicao_cliente_pf_id")
    private Long complementacaoAquisicaoClientePfId;
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
    @Column(name="estado_civil")
    private String estadoCivil;
    @Column(name="uniao_estavel")
    private String uniaoEstavel;
    private String escolaridade;
    private String conclusao;
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
    private String profissao2;    
    private String empresa2;
    private String cnpj2;
    @Column(name="data_inicio2")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicio2;
    private BigDecimal renda2;
    @Column(name="data_ref2")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRef2;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="conjugue_aquisicao_id")
    private GobConjugueAquisicao conjugueAquisicao = new GobConjugueAquisicao();    
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="cliente_id",unique=true,nullable=false)
    private GerCliente cliente = new GerCliente();

    public GerCliente getCliente() {
        return cliente;
    }

    public void setCliente(GerCliente cliente) {
        this.cliente = cliente;
    }

    public String getCnpj1() {
        return cnpj1;
    }

    public void setCnpj1(String cnpj1) {
        this.cnpj1 = cnpj1;
    }

    public String getCnpj2() {
        return cnpj2;
    }

    public void setCnpj2(String cnpj2) {
        this.cnpj2 = cnpj2;
    }

    public Long getComplementacaoAquisicaoClientePfId() {
        return complementacaoAquisicaoClientePfId;
    }

    public void setComplementacaoAquisicaoClientePfId(Long complementacaoAquisicaoClientePfId) {
        this.complementacaoAquisicaoClientePfId = complementacaoAquisicaoClientePfId;
    }

    public String getConclusao() {
        return conclusao;
    }

    public void setConclusao(String conclusao) {
        this.conclusao = conclusao;
    }

    public GobConjugueAquisicao getConjugueAquisicao() {
        return conjugueAquisicao;
    }

    public void setConjugueAquisicao(GobConjugueAquisicao conjugueAquisicao) {
        this.conjugueAquisicao = conjugueAquisicao;
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

    public Date getDataInicio2() {
        return dataInicio2;
    }

    public void setDataInicio2(Date dataInicio2) {
        this.dataInicio2 = dataInicio2;
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

    public Date getDataRef2() {
        return dataRef2;
    }

    public void setDataRef2(Date dataRef2) {
        this.dataRef2 = dataRef2;
    }

    public String getEmpresa1() {
        return empresa1;
    }

    public void setEmpresa1(String empresa1) {
        this.empresa1 = empresa1;
    }

    public String getEmpresa2() {
        return empresa2;
    }

    public void setEmpresa2(String empresa2) {
        this.empresa2 = empresa2;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public BigDecimal getRenda2() {
        return renda2;
    }

    public void setRenda2(BigDecimal renda2) {
        this.renda2 = renda2;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getUniaoEstavel() {
        return uniaoEstavel;
    }

    public void setUniaoEstavel(String uniaoEstavel) {
        this.uniaoEstavel = uniaoEstavel;
    }

    public String getProfissao1() {
        return profissao1;
    }

    public void setProfissao1(String profissao1) {
        this.profissao1 = profissao1;
    }

    public String getProfissao2() {
        return profissao2;
    }

    public void setProfissao2(String profissao2) {
        this.profissao2 = profissao2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GobComplementacaoAquisicaoClientePf other = (GobComplementacaoAquisicaoClientePf) obj;
        if (this.complementacaoAquisicaoClientePfId != other.complementacaoAquisicaoClientePfId && (this.complementacaoAquisicaoClientePfId == null || !this.complementacaoAquisicaoClientePfId.equals(other.complementacaoAquisicaoClientePfId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.complementacaoAquisicaoClientePfId != null ? this.complementacaoAquisicaoClientePfId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GobComplementacaoAquisicaoClientePf{" + "complementacaoAquisicaoClientePfId=" + complementacaoAquisicaoClientePfId + ", dataNascimento=" + dataNascimento + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", orgaoEmissor=" + orgaoEmissor + ", dataEmissao=" + dataEmissao + ", naturalidade=" + naturalidade + ", pai=" + pai + ", mae=" + mae + ", estadoCivil=" + estadoCivil + ", uniaoEstavel=" + uniaoEstavel + ", escolaridade=" + escolaridade + ", conclusao=" + conclusao + ", inicioResidencia=" + inicioResidencia + ", profissao1=" + profissao1 + ", empresa1=" + empresa1 + ", cnpj1=" + cnpj1 + ", dataInicio1=" + dataInicio1 + ", renda1=" + renda1 + ", dataRef1=" + dataRef1 + ", profissao2=" + profissao2 + ", empresa2=" + empresa2 + ", cnpj2=" + cnpj2 + ", dataInicio2=" + dataInicio2 + ", renda2=" + renda2 + ", dataRef2=" + dataRef2 + ", conjugueAquisicao=" + conjugueAquisicao + ", cliente=" + cliente + '}';
    }    
    
}
