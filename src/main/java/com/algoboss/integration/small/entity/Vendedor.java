package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vendedor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendedor.findAll", query="SELECT v FROM Vendedor v")
public class Vendedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	private Integer ativo;

	private double comissa1;

	private double comissa2;

	private String funcao;

	private String horastrab;

	private String nome;

	public Vendedor() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public Integer getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public double getComissa1() {
		return this.comissa1;
	}

	public void setComissa1(double comissa1) {
		this.comissa1 = comissa1;
	}

	public double getComissa2() {
		return this.comissa2;
	}

	public void setComissa2(double comissa2) {
		this.comissa2 = comissa2;
	}

	public String getFuncao() {
		return this.funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getHorastrab() {
		return this.horastrab;
	}

	public void setHorastrab(String horastrab) {
		this.horastrab = horastrab;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}