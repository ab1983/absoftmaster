/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.small.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
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

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllOrdemServico",
            query = "select t from OrdemServico t")
})
@Entity
@Table(name = "OS")
public class OrdemServico2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "OrdemServico", sequenceName = "G_OS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "OrdemServico")
    @Column(name = "registro")
    private Long registro;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data = new Date();
    @Column(name = "hora")
    private String hora;
    @Column(name = "cliente")
    private String cliente;    
    @Column(name = "data_pro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPro = new Date();  
    @Column(name = "hora_pro")
    private String horaPro;    
    private String tempo;
    @Column(name = "data_ter")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataTer = new Date();  
    @Column(name = "hora_ter")
    private String horaTer;       
    @Column(name = "situacao")
    private String situacao;    
    @Column(name = "data_ent")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEnt = new Date();  
    @Column(name = "hora_ent")
    private String horaEnt;     
    @Column(name = "tecnico")
    private String tecnico;
    @Column(name = "descricao")
    private String descricao;    
    @Column(name = "problema")
    private String problema; 
    @Column(name = "identifi1")
    private String identifi1; 
    @Column(name = "identifi2")
    private String identifi2;     
    @Column(name = "identifi3")
    private String identifi3;     
    @Column(name = "identifi4")
    private String identifi4;     
    @Column(name = "garantia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date garantia;     
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "total_serv",precision=12,scale=4)
    private BigDecimal totalServ;    
    @Column(name = "total_peca",precision=12,scale=4)
    private BigDecimal totalPeca;
    @Column(name = "total_fret",precision=12,scale=4)
    private BigDecimal totalFret;
    @Column(name = "desconto",precision=12,scale=4)
    private BigDecimal desconto;
    @Column(name = "total_os",precision=12,scale=4)
    private BigDecimal totalOs;    

    public Long getRegistro() {
        return registro;
    }

    public void setRegistro(Long registro) {
        this.registro = registro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getDataPro() {
        return dataPro;
    }

    public void setDataPro(Date dataPro) {
        this.dataPro = dataPro;
    }

    public String getHoraPro() {
        return horaPro;
    }

    public void setHoraPro(String horaPro) {
        this.horaPro = horaPro;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Date getDataTer() {
        return dataTer;
    }

    public void setDataTer(Date dataTer) {
        this.dataTer = dataTer;
    }

    public String getHoraTer() {
        return horaTer;
    }

    public void setHoraTer(String horaTer) {
        this.horaTer = horaTer;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataEnt() {
        return dataEnt;
    }

    public void setDataEnt(Date dataEnt) {
        this.dataEnt = dataEnt;
    }

    public String getHoraEnt() {
        return horaEnt;
    }

    public void setHoraEnt(String horaEnt) {
        this.horaEnt = horaEnt;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getIdentifi1() {
        return identifi1;
    }

    public void setIdentifi1(String identifi1) {
        this.identifi1 = identifi1;
    }

    public String getIdentifi2() {
        return identifi2;
    }

    public void setIdentifi2(String identifi2) {
        this.identifi2 = identifi2;
    }

    public String getIdentifi3() {
        return identifi3;
    }

    public void setIdentifi3(String identifi3) {
        this.identifi3 = identifi3;
    }

    public String getIdentifi4() {
        return identifi4;
    }

    public void setIdentifi4(String identifi4) {
        this.identifi4 = identifi4;
    }

    public Date getGarantia() {
        return garantia;
    }

    public void setGarantia(Date garantia) {
        this.garantia = garantia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getTotalServ() {
        return totalServ;
    }

    public void setTotalServ(BigDecimal totalServ) {
        this.totalServ = totalServ;
    }

    public BigDecimal getTotalPeca() {
        return totalPeca;
    }

    public void setTotalPeca(BigDecimal totalPeca) {
        this.totalPeca = totalPeca;
    }

    public BigDecimal getTotalFret() {
        return totalFret;
    }

    public void setTotalFret(BigDecimal totalFret) {
        this.totalFret = totalFret;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getTotalOs() {
        return totalOs;
    }

    public void setTotalOs(BigDecimal totalOs) {
        this.totalOs = totalOs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.registro);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemServico2 other = (OrdemServico2) obj;
        if (!Objects.equals(this.registro, other.registro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrdemServico{" + "registro=" + registro + ", data=" + data + ", hora=" + hora + ", cliente=" + cliente + ", dataPro=" + dataPro + ", horaPro=" + horaPro + ", tempo=" + tempo + ", dataTer=" + dataTer + ", horaTer=" + horaTer + ", situacao=" + situacao + ", dataEnt=" + dataEnt + ", horaEnt=" + horaEnt + ", tecnico=" + tecnico + ", descricao=" + descricao + ", problema=" + problema + ", identifi1=" + identifi1 + ", identifi2=" + identifi2 + ", identifi3=" + identifi3 + ", identifi4=" + identifi4 + ", garantia=" + garantia + ", observacao=" + observacao + ", totalServ=" + totalServ + ", totalPeca=" + totalPeca + ", totalFret=" + totalFret + ", desconto=" + desconto + ", totalOs=" + totalOs + '}';
    }        
    
}
