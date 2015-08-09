/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
       name="findAllCpgDocumento",
       query="select t from CpgDocumento t")
})
@Entity
@Table(name="cpg_documento")
public class CpgDocumento extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CpgDocumento", sequenceName="sequence_cpg_documento", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CpgDocumento")
    @Column(name="documento_id")
    private Long documentoId;
    @ManyToOne()
    @JoinColumn(name="fornecedor_id")
    private GerFornecedor gerFornecedor;
    private String serie;
    private String parcela = "1";
    @Column(name="numero_docto") 
    private String numeroDocto;
    @ManyToOne()
    @JoinColumn(name="tipo_documento_id")
    private GerTipoDocumento tipoDocumento;
    @Column(name="data_emissao")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dataEmissao;
    @Column(name="data_entrada")
    @Temporal(javax.persistence.TemporalType.DATE) 
    private Date dataEntrada = new Date();
    @Column(name="data_vencimento")
    @Temporal(javax.persistence.TemporalType.DATE) 
    private Date dataVencimento;
    //@OneToMany(cascade= CascadeType.ALL,mappedBy="documentoCp",orphanRemoval=true,fetch=FetchType.EAGER)
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="documento_id")
    private List<CpgItemDocumento> cpgItemDocumentoList = new ArrayList<CpgItemDocumento>();      

    public List<CpgItemDocumento> getCpgItemDocumentoList() {
        return cpgItemDocumentoList;
    }

    public void setCpgItemDocumentoList(List<CpgItemDocumento> cpgItemDocumentoList) {
        this.cpgItemDocumentoList = cpgItemDocumentoList;
    }    
    
    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public GerFornecedor getGerFornecedor() {
        return gerFornecedor;
    }

    public void setGerFornecedor(GerFornecedor gerFornecedor) {
        this.gerFornecedor = gerFornecedor;
    }

    public String getNumeroDocto() {
        return numeroDocto;
    }

    public void setNumeroDocto(String numeroDocto) {
        this.numeroDocto = numeroDocto;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public GerTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(GerTipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CpgDocumento other = (CpgDocumento) obj;
        if (this.documentoId != other.documentoId && (this.documentoId == null || !this.documentoId.equals(other.documentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.documentoId != null ? this.documentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CpgDocumento{" + "documentoId=" + documentoId + ", gerFornecedor=" + gerFornecedor + ", serie=" + serie + ", parcela=" + parcela + ", numeroDocto=" + numeroDocto + ", cpgTipoDocumento=" + tipoDocumento + ", dataEmissao=" + dataEmissao + ", dataEntrada=" + dataEntrada + ", dataVencimento=" + dataVencimento + ", cpgItemDocumentoList=" + cpgItemDocumentoList + '}';
    }
    
}
