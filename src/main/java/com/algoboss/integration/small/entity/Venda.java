package com.algoboss.integration.small.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the vendas database table.
 * 
 */
@Entity
@Table(name="vendas")
@NamedQuery(name="Venda.findAll", query="SELECT v FROM Venda v")
public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	private double aliquota;

	private Integer anvisa;

	private double baseicm;

	private double baseiss;

	private double basesubsti;

	private String ccexml;

	private String cliente;

	@Column(name="cod_sit")
	private String codSit;

	private String complemento;

	@Temporal(TemporalType.DATE)
	@Column(name="data_cancel")
	private Date dataCancel;

	private double desconto;

	private double despesas;

	private double duplicatas;

	@Temporal(TemporalType.DATE)
	private Date emissao;

	private String emitida;

	private String especie;

	private double frete;

	private String frete12;

	@Column(name="hora_cancel")
	private String horaCancel;

	private Integer icce;

	private double icms;

	private double icmssubsti;

	private String identificador1;

	private double ipi;

	private double iss;

	private String loked;

	private String marca;

	private double mercadoria;

	private String modelo;

	private String nfeid;

	private String nfeprotocolo;

	private String nferecibo;

	private String nfexml;

	private String nsu;

	@Temporal(TemporalType.DATE)
	private Date nsud;

	private String nsuh;

	private String numeronf;

	private String nvol;

	private String operacao;

	private double pesobruto;

	private double pesoliqui;

	private String placa;

	private String reciboxml;

	@Temporal(TemporalType.DATE)
	private Date saidad;

	private String saidah;

	private double seguro;

	private double servicos;

	private String status;

	private double total;

	private String transporta;

	private String vendedor;

	private double volumes;

	public Venda() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public double getAliquota() {
		return this.aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public Integer getAnvisa() {
		return this.anvisa;
	}

	public void setAnvisa(Integer anvisa) {
		this.anvisa = anvisa;
	}

	public double getBaseicm() {
		return this.baseicm;
	}

	public void setBaseicm(double baseicm) {
		this.baseicm = baseicm;
	}

	public double getBaseiss() {
		return this.baseiss;
	}

	public void setBaseiss(double baseiss) {
		this.baseiss = baseiss;
	}

	public double getBasesubsti() {
		return this.basesubsti;
	}

	public void setBasesubsti(double basesubsti) {
		this.basesubsti = basesubsti;
	}

	public String getCcexml() {
		return this.ccexml;
	}

	public void setCcexml(String ccexml) {
		this.ccexml = ccexml;
	}

	public String getCliente() {
		return this.cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCodSit() {
		return this.codSit;
	}

	public void setCodSit(String codSit) {
		this.codSit = codSit;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Date getDataCancel() {
		return this.dataCancel;
	}

	public void setDataCancel(Date dataCancel) {
		this.dataCancel = dataCancel;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getDespesas() {
		return this.despesas;
	}

	public void setDespesas(double despesas) {
		this.despesas = despesas;
	}

	public double getDuplicatas() {
		return this.duplicatas;
	}

	public void setDuplicatas(double duplicatas) {
		this.duplicatas = duplicatas;
	}

	public Date getEmissao() {
		return this.emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getEmitida() {
		return this.emitida;
	}

	public void setEmitida(String emitida) {
		this.emitida = emitida;
	}

	public String getEspecie() {
		return this.especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public double getFrete() {
		return this.frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}

	public String getFrete12() {
		return this.frete12;
	}

	public void setFrete12(String frete12) {
		this.frete12 = frete12;
	}

	public String getHoraCancel() {
		return this.horaCancel;
	}

	public void setHoraCancel(String horaCancel) {
		this.horaCancel = horaCancel;
	}

	public Integer getIcce() {
		return this.icce;
	}

	public void setIcce(Integer icce) {
		this.icce = icce;
	}

	public double getIcms() {
		return this.icms;
	}

	public void setIcms(double icms) {
		this.icms = icms;
	}

	public double getIcmssubsti() {
		return this.icmssubsti;
	}

	public void setIcmssubsti(double icmssubsti) {
		this.icmssubsti = icmssubsti;
	}

	public String getIdentificador1() {
		return this.identificador1;
	}

	public void setIdentificador1(String identificador1) {
		this.identificador1 = identificador1;
	}

	public double getIpi() {
		return this.ipi;
	}

	public void setIpi(double ipi) {
		this.ipi = ipi;
	}

	public double getIss() {
		return this.iss;
	}

	public void setIss(double iss) {
		this.iss = iss;
	}

	public String getLoked() {
		return this.loked;
	}

	public void setLoked(String loked) {
		this.loked = loked;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getMercadoria() {
		return this.mercadoria;
	}

	public void setMercadoria(double mercadoria) {
		this.mercadoria = mercadoria;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNfeid() {
		return this.nfeid;
	}

	public void setNfeid(String nfeid) {
		this.nfeid = nfeid;
	}

	public String getNfeprotocolo() {
		return this.nfeprotocolo;
	}

	public void setNfeprotocolo(String nfeprotocolo) {
		this.nfeprotocolo = nfeprotocolo;
	}

	public String getNferecibo() {
		return this.nferecibo;
	}

	public void setNferecibo(String nferecibo) {
		this.nferecibo = nferecibo;
	}

	public String getNfexml() {
		return this.nfexml;
	}

	public void setNfexml(String nfexml) {
		this.nfexml = nfexml;
	}

	public String getNsu() {
		return this.nsu;
	}

	public void setNsu(String nsu) {
		this.nsu = nsu;
	}

	public Date getNsud() {
		return this.nsud;
	}

	public void setNsud(Date nsud) {
		this.nsud = nsud;
	}

	public String getNsuh() {
		return this.nsuh;
	}

	public void setNsuh(String nsuh) {
		this.nsuh = nsuh;
	}

	public String getNumeronf() {
		return this.numeronf;
	}

	public void setNumeronf(String numeronf) {
		this.numeronf = numeronf;
	}

	public String getNvol() {
		return this.nvol;
	}

	public void setNvol(String nvol) {
		this.nvol = nvol;
	}

	public String getOperacao() {
		return this.operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public double getPesobruto() {
		return this.pesobruto;
	}

	public void setPesobruto(double pesobruto) {
		this.pesobruto = pesobruto;
	}

	public double getPesoliqui() {
		return this.pesoliqui;
	}

	public void setPesoliqui(double pesoliqui) {
		this.pesoliqui = pesoliqui;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getReciboxml() {
		return this.reciboxml;
	}

	public void setReciboxml(String reciboxml) {
		this.reciboxml = reciboxml;
	}

	public Date getSaidad() {
		return this.saidad;
	}

	public void setSaidad(Date saidad) {
		this.saidad = saidad;
	}

	public String getSaidah() {
		return this.saidah;
	}

	public void setSaidah(String saidah) {
		this.saidah = saidah;
	}

	public double getSeguro() {
		return this.seguro;
	}

	public void setSeguro(double seguro) {
		this.seguro = seguro;
	}

	public double getServicos() {
		return this.servicos;
	}

	public void setServicos(double servicos) {
		this.servicos = servicos;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getTransporta() {
		return this.transporta;
	}

	public void setTransporta(String transporta) {
		this.transporta = transporta;
	}

	public String getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public double getVolumes() {
		return this.volumes;
	}

	public void setVolumes(double volumes) {
		this.volumes = volumes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (registro == null) {
			if (other.registro != null)
				return false;
		} else if (!registro.equals(other.registro))
			return false;
		return true;
	}

}