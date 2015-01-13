package com.algoboss.integration.small.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the compras database table.
 * 
 */
@Entity
@Table(name="compras")
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "Compra", sequenceName = "g_compras", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Compra")	
	private String registro;

	private double aliquota;

	private Integer anvisa;

	private double baseicm;

	private double basesubsti;

	private String complemento;

	private double desconto;

	private String descricao1;

	private String descricao2;

	private String descricao3;

	private double despesas;

	private double duplicatas;

	@Temporal(TemporalType.DATE)
	private Date emissao;

	private String emitida;

	private String especie;

	private String fornecedor;

	private double frete;

	private String frete12;

	private double icms;

	private double icmssubsti;

	private double ipi;

	private double iss;

	private String marca;

	private String mdestinxml;

	private double mercadoria;

	private String modelo;

	private String nfeid;

	private String nfexml;

	private String numeronf;

	private String nvol;

	private String operacao;

	private double pesobruto;

	private double pesoliqui;

	@Temporal(TemporalType.DATE)
	private Date saidad;

	private String saidah;

	private double seguro;

	private double servicos;

	private double total;

	private String transporta;

	private String vendedor;

	private double volumes;
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="numeronf", name = "numeronf"/*,insertable=false,updatable=false*/)	
	private List<Itens002> produtos = new ArrayList<Itens002>();	

	public Compra() {
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

	public double getBasesubsti() {
		return this.basesubsti;
	}

	public void setBasesubsti(double basesubsti) {
		this.basesubsti = basesubsti;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getDescricao1() {
		return this.descricao1;
	}

	public void setDescricao1(String descricao1) {
		this.descricao1 = descricao1;
	}

	public String getDescricao2() {
		return this.descricao2;
	}

	public void setDescricao2(String descricao2) {
		this.descricao2 = descricao2;
	}

	public String getDescricao3() {
		return this.descricao3;
	}

	public void setDescricao3(String descricao3) {
		this.descricao3 = descricao3;
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

	public String getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
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

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMdestinxml() {
		return this.mdestinxml;
	}

	public void setMdestinxml(String mdestinxml) {
		this.mdestinxml = mdestinxml;
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

	public String getNfexml() {
		return this.nfexml;
	}

	public void setNfexml(String nfexml) {
		this.nfexml = nfexml;
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

	public List<Itens002> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Itens002> produtos) {
		this.produtos = produtos;
	}
	
	

}