package com.algoboss.integration.small.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the os database table.
 * 
 */
@Entity
@Table(name="os")
@NamedQuery(name="Os.findAll", query="SELECT os FROM Os os")
public class Os implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "Os", sequenceName = "g_os", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Os")	
	private String registro;

	private String cliente;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Temporal(TemporalType.DATE)
	@Column(name="data_ent")
	private Date dataEnt;

	@Temporal(TemporalType.DATE)
	@Column(name="data_pro")
	private Date dataPro;

	@Temporal(TemporalType.DATE)
	@Column(name="data_ter")
	private Date dataTer;

	private double desconto;

	private String descricao;

	@Temporal(TemporalType.DATE)
	private Date garantia;

	private String hora;

	@Column(name="hora_ent")
	private String horaEnt;

	@Column(name="hora_pro")
	private String horaPro;

	@Column(name="hora_ter")
	private String horaTer;

	private String identifi1;

	private String identifi2;

	private String identifi3;

	private String identifi4;

	private String nf;

	private String numero;

	private String observacao;

	private String problema;

	private String situacao;

	private String tecnico;

	private String tempo;

	@Column(name="total_fret")
	private double totalFret;

	@Column(name="total_os")
	private double totalOs;

	@Column(name="total_peca")
	private double totalPeca;

	@Column(name="total_serv")
	private double totalServ;
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="numero", name = "numeroos"/*,insertable=false,updatable=false*/)	
	private List<Itens001> produtos = new ArrayList<Itens001>();	

	public Os() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getCliente() {
		return this.cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataEnt() {
		return this.dataEnt;
	}

	public void setDataEnt(Date dataEnt) {
		this.dataEnt = dataEnt;
	}

	public Date getDataPro() {
		return this.dataPro;
	}

	public void setDataPro(Date dataPro) {
		this.dataPro = dataPro;
	}

	public Date getDataTer() {
		return this.dataTer;
	}

	public void setDataTer(Date dataTer) {
		this.dataTer = dataTer;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getGarantia() {
		return this.garantia;
	}

	public void setGarantia(Date garantia) {
		this.garantia = garantia;
	}

	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getHoraEnt() {
		return this.horaEnt;
	}

	public void setHoraEnt(String horaEnt) {
		this.horaEnt = horaEnt;
	}

	public String getHoraPro() {
		return this.horaPro;
	}

	public void setHoraPro(String horaPro) {
		this.horaPro = horaPro;
	}

	public String getHoraTer() {
		return this.horaTer;
	}

	public void setHoraTer(String horaTer) {
		this.horaTer = horaTer;
	}

	public String getIdentifi1() {
		return this.identifi1;
	}

	public void setIdentifi1(String identifi1) {
		this.identifi1 = identifi1;
	}

	public String getIdentifi2() {
		return this.identifi2;
	}

	public void setIdentifi2(String identifi2) {
		this.identifi2 = identifi2;
	}

	public String getIdentifi3() {
		return this.identifi3;
	}

	public void setIdentifi3(String identifi3) {
		this.identifi3 = identifi3;
	}

	public String getIdentifi4() {
		return this.identifi4;
	}

	public void setIdentifi4(String identifi4) {
		this.identifi4 = identifi4;
	}

	public String getNf() {
		return this.nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getProblema() {
		return this.problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTecnico() {
		return this.tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getTempo() {
		return this.tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public double getTotalFret() {
		return this.totalFret;
	}

	public void setTotalFret(double totalFret) {
		this.totalFret = totalFret;
	}

	public double getTotalOs() {
		return this.totalOs;
	}

	public void setTotalOs(double totalOs) {
		this.totalOs = totalOs;
	}

	public double getTotalPeca() {
		return this.totalPeca;
	}

	public void setTotalPeca(double totalPeca) {
		this.totalPeca = totalPeca;
	}

	public double getTotalServ() {
		return this.totalServ;
	}

	public void setTotalServ(double totalServ) {
		this.totalServ = totalServ;
	}

}