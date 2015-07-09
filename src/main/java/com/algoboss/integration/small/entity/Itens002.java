package com.algoboss.integration.small.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the itens002 database table.
 * 
 */
@Cacheable(true)
@Entity
@NamedQuery(name = "Itens002.findAll", query = "SELECT i FROM Itens002 i")
public class Itens002 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Itens002", sequenceName = "g_Itens002", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "Itens002")
	private String registro;

	@Column(name = "aliq_cofins")
	private double aliqCofins;

	@Column(name = "aliq_pis")
	private double aliqPis;

	private double base;

	private String cfop;

	private String codigo;

	@Column(name = "cst_icms")
	private String cstIcms;

	@Column(name = "cst_ipi")
	private String cstIpi;

	@Column(name = "cst_pis_cofins")
	private String cstPisCofins;

	private double custo;

	private String descricao;

	private String fornecedor;

	private double icm;

	private double ipi;

	private double lista;

	private String medida;

	private String numeronf;

	private double peso;

	private double quantidade;

	private double sincronia;

	private String st;

	private double total;

	private double unitario;

	private double vbc;

	private double vbcst;

	private double vicms;

	private double vicmsst;

	private double vipi;

	private double vpreco;

	public Itens002() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public double getAliqCofins() {
		return this.aliqCofins;
	}

	public void setAliqCofins(double aliqCofins) {
		this.aliqCofins = aliqCofins;
	}

	public double getAliqPis() {
		return this.aliqPis;
	}

	public void setAliqPis(double aliqPis) {
		this.aliqPis = aliqPis;
	}

	public double getBase() {
		return this.base;
	}

	public void setBase(double base) {
		this.base = base;
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

	public String getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
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

	public String getNumeronf() {
		return this.numeronf;
	}

	public void setNumeronf(String numeronf) {
		this.numeronf = numeronf;
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

	public double getVbc() {
		return this.vbc;
	}

	public void setVbc(double vbc) {
		this.vbc = vbc;
	}

	public double getVbcst() {
		return this.vbcst;
	}

	public void setVbcst(double vbcst) {
		this.vbcst = vbcst;
	}

	public double getVicms() {
		return this.vicms;
	}

	public void setVicms(double vicms) {
		this.vicms = vicms;
	}

	public double getVicmsst() {
		return this.vicmsst;
	}

	public void setVicmsst(double vicmsst) {
		this.vicmsst = vicmsst;
	}

	public double getVipi() {
		return this.vipi;
	}

	public void setVipi(double vipi) {
		this.vipi = vipi;
	}

	public double getVpreco() {
		return this.vpreco;
	}

	public void setVpreco(double vpreco) {
		this.vpreco = vpreco;
	}

}