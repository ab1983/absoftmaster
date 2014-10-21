package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the itens003 database table.
 * 
 */
//@Entity 
@NamedQuery(name="Itens003.findAll", query="SELECT i FROM Itens003 i")
public class Itens003 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	private double baseiss;

	private String cfop;

	private String codigo;

	private String descricao;

	private double iss;

	private String medida;

	private String numeronf;

	private String numeroos;

	private double quantidade;

	private String tecnico;

	private double total;

	private double unitario;

	public Itens003() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
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

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getIss() {
		return this.iss;
	}

	public void setIss(double iss) {
		this.iss = iss;
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

	public String getNumeroos() {
		return this.numeroos;
	}

	public void setNumeroos(String numeroos) {
		this.numeroos = numeroos;
	}

	public double getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public String getTecnico() {
		return this.tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
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

}