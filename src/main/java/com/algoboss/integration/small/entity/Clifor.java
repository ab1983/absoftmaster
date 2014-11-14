package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the clifor database table.
 * 
 */
@Entity
@NamedQuery(name="Clifor.findAll", query="SELECT c FROM Clifor c")
public class Clifor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	private Integer ativo;

	@Temporal(TemporalType.DATE)
	private Date cadastro;

	private String celular;

	private String cep;

	private String cgc;

	private String cidade;

	private String clifor;

	private String comple;

	private double compra;

	private String contato;

	private String contatos;

	private String convenio;

	private double credito;

	private double custo;

	@Temporal(TemporalType.DATE)
	private Date datanas;

	private String email;

	private String endere;

	private String estado;

	private String fax;

	private String fone;

	private String foto;

	private String identificador1;

	private String identificador2;

	private String identificador3;

	private String identificador4;

	private String identificador5;

	private String ie;

	private String mostrar;

	private String nome;

	private String obs;

	@Temporal(TemporalType.DATE)
	private Date proxdata;

	@Temporal(TemporalType.DATE)
	private Date ultimaco;

	public Clifor() {
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

	public Date getCadastro() {
		return this.cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCgc() {
		return this.cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getClifor() {
		return this.clifor;
	}

	public void setClifor(String clifor) {
		this.clifor = clifor;
	}

	public String getComple() {
		return this.comple;
	}

	public void setComple(String comple) {
		this.comple = comple;
	}

	public double getCompra() {
		return this.compra;
	}

	public void setCompra(double compra) {
		this.compra = compra;
	}

	public String getContato() {
		return this.contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getContatos() {
		return this.contatos;
	}

	public void setContatos(String contatos) {
		this.contatos = contatos;
	}

	public String getConvenio() {
		return this.convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public double getCredito() {
		return this.credito;
	}

	public void setCredito(double credito) {
		this.credito = credito;
	}

	public double getCusto() {
		return this.custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public Date getDatanas() {
		return this.datanas;
	}

	public void setDatanas(Date datanas) {
		this.datanas = datanas;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndere() {
		return this.endere;
	}

	public void setEndere(String endere) {
		this.endere = endere;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIdentificador1() {
		return this.identificador1;
	}

	public void setIdentificador1(String identificador1) {
		this.identificador1 = identificador1;
	}

	public String getIdentificador2() {
		return this.identificador2;
	}

	public void setIdentificador2(String identificador2) {
		this.identificador2 = identificador2;
	}

	public String getIdentificador3() {
		return this.identificador3;
	}

	public void setIdentificador3(String identificador3) {
		this.identificador3 = identificador3;
	}

	public String getIdentificador4() {
		return this.identificador4;
	}

	public void setIdentificador4(String identificador4) {
		this.identificador4 = identificador4;
	}

	public String getIdentificador5() {
		return this.identificador5;
	}

	public void setIdentificador5(String identificador5) {
		this.identificador5 = identificador5;
	}

	public String getIe() {
		return this.ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getMostrar() {
		return this.mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Date getProxdata() {
		return this.proxdata;
	}

	public void setProxdata(Date proxdata) {
		this.proxdata = proxdata;
	}

	public Date getUltimaco() {
		return this.ultimaco;
	}

	public void setUltimaco(Date ultimaco) {
		this.ultimaco = ultimaco;
	}

}