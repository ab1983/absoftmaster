/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.algoboss.core.entity.GenericEntity;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllCpgItemDocumento",
    query = "select t from CpgItemDocumento t")
})
@Entity
@Table(name = "cpg_item_documento")
public class CpgItemDocumento extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "CpgItemDocumento",sequenceName="sequence_cpg_item_documento", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CpgItemDocumento")
    @Column(name = "item_documento_id")
    private Long itemDocumentoId;
    @ManyToOne()
    @JoinColumn(name = "tipo_despesa_id")
    private CpgTipoDespesa tipoDespesa;
    @Column(precision=12,scale=4)
    private Float valor;
    @ManyToOne()
    @JoinColumn(name = "centro_custo_id")
    private GerCentroCusto centroCusto;
    private String observacao;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="cronograma_id")
    private GrtCronograma cronograma = new GrtCronograma();       
    /*
    @ManyToOne()
    @JoinColumn(name = "documento_cp_id")
    private DocumentoCP documentoCp;

    public DocumentoCP getDocumentoCp() {
        return documentoCp;
    }

    public void setDocumentoCp(DocumentoCP documentoCp) {
        this.documentoCp = documentoCp;
    }*/

    public Long getItemDocumentoId() {
        return itemDocumentoId;
    }

    public void setItemDocumentoId(Long itemDocumentoId) {
        this.itemDocumentoId = itemDocumentoId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
    
    public void setValor(String valor){
        try{
            this.valor = new DecimalFormat("0,00").parse(valor).floatValue();                        
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public GerCentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(GerCentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public CpgTipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(CpgTipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public GrtCronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(GrtCronograma cronograma) {
        this.cronograma = cronograma;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CpgItemDocumento other = (CpgItemDocumento) obj;
        if (this.itemDocumentoId != other.itemDocumentoId && (this.itemDocumentoId == null || !this.itemDocumentoId.equals(other.itemDocumentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.itemDocumentoId != null ? this.itemDocumentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CpgItemDocumento{" + "itemDocumentoId=" + itemDocumentoId + ", tipoDespesa=" + tipoDespesa + ", valor=" + valor + ", centroCusto=" + centroCusto + ", observacao=" + observacao + '}';
    }
}
