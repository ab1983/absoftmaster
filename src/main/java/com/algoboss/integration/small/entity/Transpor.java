package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the transpor database table.
 * 
 */
@Entity
@NamedQuery(name="Transpor.findAll", query="SELECT t FROM Transpor t")
public class Transpor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	private String antt;

	private String cgc;

	private String email;

	private String endereco;

	private String estado;

	private String fone;

	private String ie;

	private String municipio;

	private String nome;

	private String placa;

	private String uf;

	public Transpor() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getAntt() {
		return this.antt;
	}

	public void setAntt(String antt) {
		this.antt = antt;
	}

	public String getCgc() {
		return this.cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getIe() {
		return this.ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}