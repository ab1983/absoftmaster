package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the itens001 database table.
 * 
 */
@Entity
@NamedQuery(name="Itens001.findAll", query="SELECT i FROM Itens001 i")
public class Itens001 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	@Column(name="aliq_cofins")
	private BigDecimal aliqCofins;

	@Column(name="aliq_pis")
	private BigDecimal aliqPis;

	private double aliquota;

	private double base;

	private double baseiss;

	private String cfop;

	private String codigo;

	@Column(name="cst_icms")
	private String cstIcms;

	@Column(name="cst_ipi")
	private String cstIpi;

	@Column(name="cst_pis_cofins")
	private String cstPisCofins;

	private double custo;

	private String descricao;

	private double icm;

	private double ipi;

	private double iss;

	private double lista;

	private String medida;

	private String nitemped;

	private String numeronf;

	private String numeroos;

	private double peso;

	private double quantidade;

	private double sincronia;

	private String st;

	private double total;

	private double unitario;

	private BigDecimal vbc;

	private BigDecimal vbcst;

	private BigDecimal vicms;

	private BigDecimal vicmsst;

	private BigDecimal vipi;

	private String xped;

	public Itens001() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public BigDecimal getAliqCofins() {
		return this.aliqCofins;
	}

	public void setAliqCofins(BigDecimal aliqCofins) {
		this.aliqCofins = aliqCofins;
	}

	public BigDecimal getAliqPis() {
		return this.aliqPis;
	}

	public void setAliqPis(BigDecimal aliqPis) {
		this.aliqPis = aliqPis;
	}

	public double getAliquota() {
		return this.aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public double getBase() {
		return this.base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getBaseiss() {
		return this.baseiss;
	}

	public void setBaseiss(double baseiss) {
		this.baseiss = baseiss;
	}

	public String getCfop() {
		return this.cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCstIcms() {
		return this.cstIcms;
	}

	public void setCstIcms(String cstIcms) {
		this.cstIcms = cstIcms;
	}

	public String getCstIpi() {
		return this.cstIpi;
	}

	public void setCstIpi(String cstIpi) {
		this.cstIpi = cstIpi;
	}

	public String getCstPisCofins() {
		return this.cstPisCofins;
	}

	public void setCstPisCofins(String cstPisCofins) {
		this.cstPisCofins = cstPisCofins;
	}

	public double getCusto() {
		return this.custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getIcm() {
		return this.icm;
	}

	public void setIcm(double icm) {
		this.icm = icm;
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

	public double getLista() {
		return this.lista;
	}

	public void setLista(double lista) {
		this.lista = lista;
	}

	public String getMedida() {
		return this.medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getNitemped() {
		return this.nitemped;
	}

	public void setNitemped(String nitemped) {
		this.nitemped = nitemped;
	}

	public String getNumeronf() {
		return this.numeronf;
	}

	public void setNumeronf(String numeronf) {
		this.numeronf = numeronf;
	}

	public String getNumeroos() {
		return this.numeroos;
	}

	public void setNumeroos(String numeroos) {
		this.numeroos = numeroos;
	}

	public double getPeso() {
		return this.peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getSincronia() {
		return this.sincronia;
	}

	public void setSincronia(double sincronia) {
		this.sincronia = sincronia;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getUnitario() {
		return this.unitario;
	}

	public void setUnitario(double unitario) {
		this.unitario = unitario;
	}

	public BigDecimal getVbc() {
		return this.vbc;
	}

	public void setVbc(BigDecimal vbc) {
		this.vbc = vbc;
	}

	public BigDecimal getVbcst() {
		return this.vbcst;
	}

	public void setVbcst(BigDecimal vbcst) {
		this.vbcst = vbcst;
	}

	public BigDecimal getVicms() {
		return this.vicms;
	}

	public void setVicms(BigDecimal vicms) {
		this.vicms = vicms;
	}

	public BigDecimal getVicmsst() {
		return this.vicmsst;
	}

	public void setVicmsst(BigDecimal vicmsst) {
		this.vicmsst = vicmsst;
	}

	public BigDecimal getVipi() {
		return this.vipi;
	}

	public void setVipi(BigDecimal vipi) {
		this.vipi = vipi;
	}

	public String getXped() {
		return this.xped;
	}

	public void setXped(String xped) {
		this.xped = xped;
	}

}