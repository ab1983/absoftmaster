package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the icm database table.
 * 
 */
@Entity
@NamedQuery(name="Icm.findAll", query="SELECT i FROM Icm i")
public class Icm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	@Column(name="ac_")
	private double ac;

	@Column(name="al_")
	private double al;

	@Column(name="am_")
	private double am;

	@Column(name="ap_")
	private double ap;

	@Column(name="ba_")
	private double ba;

	private double base;

	private double baseiss;

	private BigDecimal bccofins;

	private BigDecimal bcpis;

	@Column(name="ce_")
	private double ce;

	private String cfop;

	private String conta;

	private String csosn;

	private String cst;

	private String cstpiscofins;

	@Column(name="df_")
	private double df;

	@Column(name="es_")
	private double es;

	@Column(name="ex_")
	private double ex;

	@Column(name="go_")
	private double go;

	private String integracao;

	private double iss;

	@Column(name="ma_")
	private double ma;

	@Column(name="mg_")
	private double mg;

	@Column(name="ms_")
	private double ms;

	@Column(name="mt_")
	private double mt;

	private String nome;

	private String obs;

	@Column(name="pa_")
	private double pa;

	@Column(name="pb_")
	private double pb;

	private BigDecimal pcofins;

	@Column(name="pe_")
	private double pe;

	@Column(name="pi_")
	private double pi;

	private BigDecimal ppis;

	@Column(name="pr_")
	private double pr;

	@Column(name="rj_")
	private double rj;

	@Column(name="rn_")
	private double rn;

	@Column(name="ro_")
	private double ro;

	@Column(name="rr_")
	private double rr;

	@Column(name="rs_")
	private double rs;

	@Column(name="sc_")
	private double sc;

	@Column(name="se_")
	private double se;

	private String sobrefrete;

	private String sobreipi;

	private String sobreoutras;

	private String sobreseguro;

	@Column(name="sp_")
	private double sp;

	private String st;

	@Column(name="to_")
	private double to;

	public Icm() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public double getAc() {
		return this.ac;
	}

	public void setAc(double ac) {
		this.ac = ac;
	}

	public double getAl() {
		return this.al;
	}

	public void setAl(double al) {
		this.al = al;
	}

	public double getAm() {
		return this.am;
	}

	public void setAm(double am) {
		this.am = am;
	}

	public double getAp() {
		return this.ap;
	}

	public void setAp(double ap) {
		this.ap = ap;
	}

	public double getBa() {
		return this.ba;
	}

	public void setBa(double ba) {
		this.ba = ba;
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

	public BigDecimal getBccofins() {
		return this.bccofins;
	}

	public void setBccofins(BigDecimal bccofins) {
		this.bccofins = bccofins;
	}

	public BigDecimal getBcpis() {
		return this.bcpis;
	}

	public void setBcpis(BigDecimal bcpis) {
		this.bcpis = bcpis;
	}

	public double getCe() {
		return this.ce;
	}

	public void setCe(double ce) {
		this.ce = ce;
	}

	public String getCfop() {
		return this.cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCsosn() {
		return this.csosn;
	}

	public void setCsosn(String csosn) {
		this.csosn = csosn;
	}

	public String getCst() {
		return this.cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getCstpiscofins() {
		return this.cstpiscofins;
	}

	public void setCstpiscofins(String cstpiscofins) {
		this.cstpiscofins = cstpiscofins;
	}

	public double getDf() {
		return this.df;
	}

	public void setDf(double df) {
		this.df = df;
	}

	public double getEs() {
		return this.es;
	}

	public void setEs(double es) {
		this.es = es;
	}

	public double getEx() {
		return this.ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}

	public double getGo() {
		return this.go;
	}

	public void setGo(double go) {
		this.go = go;
	}

	public String getIntegracao() {
		return this.integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	public double getIss() {
		return this.iss;
	}

	public void setIss(double iss) {
		this.iss = iss;
	}

	public double getMa() {
		return this.ma;
	}

	public void setMa(double ma) {
		this.ma = ma;
	}

	public double getMg() {
		return this.mg;
	}

	public void setMg(double mg) {
		this.mg = mg;
	}

	public double getMs() {
		return this.ms;
	}

	public void setMs(double ms) {
		this.ms = ms;
	}

	public double getMt() {
		return this.mt;
	}

	public void setMt(double mt) {
		this.mt = mt;
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

	public double getPa() {
		return this.pa;
	}

	public void setPa(double pa) {
		this.pa = pa;
	}

	public double getPb() {
		return this.pb;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public BigDecimal getPcofins() {
		return this.pcofins;
	}

	public void setPcofins(BigDecimal pcofins) {
		this.pcofins = pcofins;
	}

	public double getPe() {
		return this.pe;
	}

	public void setPe(double pe) {
		this.pe = pe;
	}

	public double getPi() {
		return this.pi;
	}

	public void setPi(double pi) {
		this.pi = pi;
	}

	public BigDecimal getPpis() {
		return this.ppis;
	}

	public void setPpis(BigDecimal ppis) {
		this.ppis = ppis;
	}

	public double getPr() {
		return this.pr;
	}

	public void setPr(double pr) {
		this.pr = pr;
	}

	public double getRj() {
		return this.rj;
	}

	public void setRj(double rj) {
		this.rj = rj;
	}

	public double getRn() {
		return this.rn;
	}

	public void setRn(double rn) {
		this.rn = rn;
	}

	public double getRo() {
		return this.ro;
	}

	public void setRo(double ro) {
		this.ro = ro;
	}

	public double getRr() {
		return this.rr;
	}

	public void setRr(double rr) {
		this.rr = rr;
	}

	public double getRs() {
		return this.rs;
	}

	public void setRs(double rs) {
		this.rs = rs;
	}

	public double getSc() {
		return this.sc;
	}

	public void setSc(double sc) {
		this.sc = sc;
	}

	public double getSe() {
		return this.se;
	}

	public void setSe(double se) {
		this.se = se;
	}

	public String getSobrefrete() {
		return this.sobrefrete;
	}

	public void setSobrefrete(String sobrefrete) {
		this.sobrefrete = sobrefrete;
	}

	public String getSobreipi() {
		return this.sobreipi;
	}

	public void setSobreipi(String sobreipi) {
		this.sobreipi = sobreipi;
	}

	public String getSobreoutras() {
		return this.sobreoutras;
	}

	public void setSobreoutras(String sobreoutras) {
		this.sobreoutras = sobreoutras;
	}

	public String getSobreseguro() {
		return this.sobreseguro;
	}

	public void setSobreseguro(String sobreseguro) {
		this.sobreseguro = sobreseguro;
	}

	public double getSp() {
		return this.sp;
	}

	public void setSp(double sp) {
		this.sp = sp;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public double getTo() {
		return this.to;
	}

	public void setTo(double to) {
		this.to = to;
	}

}