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
       name="findAllCrcDocumento",
       query="select t from CrcDocumento t")
})
@Entity
@Table(name="crc_documento")
public class CrcDocumento extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CrcDocumento", sequenceName="sequence_crc_documento", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CrcDocumento")
    @Column(name="documento_id")
    private Long documentoId;
    @ManyToOne()
    @JoinColumn(name="cliente_id")
    private GerCliente cliente;
    private String serie;
    private String parcela = "1";
    @Column(name="numero_docto") 
    private String numeroDocto;
    @ManyToOne()
    @JoinColumn(name="tipo_documento_id")
    private GerTipoDocumento gerTipoDocumento;
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
    private List<CrcItemDocumento> crcItemDocumentoList = new ArrayList<CrcItemDocumento>();   

    public List<CrcItemDocumento> getCrcItemDocumentoList() {
        return crcItemDocumentoList;
    }

    public void setCrcItemDocumentoList(List<CrcItemDocumento> crcItemDocumentoList) {
        this.crcItemDocumentoList = crcItemDocumentoList;
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

    public GerCliente getCliente() {
        return cliente;
    }

    public void setCliente(GerCliente cliente) {
        this.cliente = cliente;
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

    public GerTipoDocumento getGerTipoDocumento() {
        return gerTipoDocumento;
    }

    public void setGerTipoDocumento(GerTipoDocumento gerTipoDocumento) {
        this.gerTipoDocumento = gerTipoDocumento;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CrcDocumento other = (CrcDocumento) obj;
        if (this.documentoId != other.documentoId && (this.documentoId == null || !this.documentoId.equals(other.documentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.documentoId != null ? this.documentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CrcDocumento{" + "documentoId=" + documentoId + ", cliente=" + cliente + ", serie=" + serie + ", parcela=" + parcela + ", numeroDocto=" + numeroDocto + ", gerTipoDocumento=" + gerTipoDocumento + ", dataEmissao=" + dataEmissao + ", dataEntrada=" + dataEntrada + ", dataVencimento=" + dataVencimento + ", crcItemDocumentoList=" + crcItemDocumentoList + '}';
    }   
}
