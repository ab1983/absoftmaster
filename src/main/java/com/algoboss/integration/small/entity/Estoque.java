package com.algoboss.integration.small.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the estoque database table.
 * 
 */
//@Entity
@NamedQuery(name="Estoque.findAll", query="SELECT e FROM Estoque e")
public class Estoque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String registro;

	@Column(name="aliq_cofins_entrada")
	private BigDecimal aliqCofinsEntrada;

	@Column(name="aliq_cofins_saida")
	private BigDecimal aliqCofinsSaida;

	@Column(name="aliq_pis_entrada")
	private BigDecimal aliqPisEntrada;

	@Column(name="aliq_pis_saida")
	private BigDecimal aliqPisSaida;

	private Integer alterado;

	private Integer ativo;

	private String cf;

	private String codigo;

	private double comissao;

	private String csosn;

	private String cst;

	@Column(name="cst_ipi")
	private String cstIpi;

	@Column(name="cst_pis_cofins_entrada")
	private String cstPisCofinsEntrada;

	@Column(name="cst_pis_cofins_saida")
	private String cstPisCofinsSaida;

	@Column(name="cus_vend")
	private double cusVend;

	private double custocompr;

	private double customedio;

	@Temporal(TemporalType.DATE)
	@Column(name="dat_inicio")
	private Date datInicio;

	private String descricao;

	private String encrypthash;

	private String fornecedor;

	private byte[] foto;

	private String iat;

	private double iia;

	private double indexador;

	private double ipi;

	private String ippt;

	private String livre1;

	private String livre2;

	private String livre3;

	private String livre4;

	private String local;

	@Column(name="luc_vend")
	private double lucVend;

	private double margemlb;

	private String medida;

	private String nome;

	private String obs;

	private BigDecimal offpromo;

	private BigDecimal onpromo;

	private double peso;

	private double piva;

	private double preco;

	@Temporal(TemporalType.DATE)
	private Date promofim;

	@Temporal(TemporalType.DATE)
	private Date promoini;

	@Column(name="qtd_atual")
	private double qtdAtual;

	@Column(name="qtd_compra")
	private double qtdCompra;

	@Column(name="qtd_inicio")
	private double qtdInicio;

	@Column(name="qtd_minim")
	private double qtdMinim;

	@Column(name="qtd_vend")
	private double qtdVend;

	private String referencia;

	private Integer serie;

	private String st;

	@Column(name="tipo_item")
	private String tipoItem;

	@Temporal(TemporalType.DATE)
	@Column(name="ult_compra")
	private Date ultCompra;

	@Temporal(TemporalType.DATE)
	@Column(name="ult_venda")
	private Date ultVenda;

	@Column(name="val_vend")
	private double valVend;

	public Estoque() {
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public BigDecimal getAliqCofinsEntrada() {
		return this.aliqCofinsEntrada;
	}

	public void setAliqCofinsEntrada(BigDecimal aliqCofinsEntrada) {
		this.aliqCofinsEntrada = aliqCofinsEntrada;
	}

	public BigDecimal getAliqCofinsSaida() {
		return this.aliqCofinsSaida;
	}

	public void setAliqCofinsSaida(BigDecimal aliqCofinsSaida) {
		this.aliqCofinsSaida = aliqCofinsSaida;
	}

	public BigDecimal getAliqPisEntrada() {
		return this.aliqPisEntrada;
	}

	public void setAliqPisEntrada(BigDecimal aliqPisEntrada) {
		this.aliqPisEntrada = aliqPisEntrada;
	}

	public BigDecimal getAliqPisSaida() {
		return this.aliqPisSaida;
	}

	public void setAliqPisSaida(BigDecimal aliqPisSaida) {
		this.aliqPisSaida = aliqPisSaida;
	}

	public Integer getAlterado() {
		return this.alterado;
	}

	public void setAlterado(Integer alterado) {
		this.alterado = alterado;
	}

	public Integer getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getComissao() {
		return this.comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
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

	public String getCstIpi() {
		return this.cstIpi;
	}

	public void setCstIpi(String cstIpi) {
		this.cstIpi = cstIpi;
	}

	public String getCstPisCofinsEntrada() {
		return this.cstPisCofinsEntrada;
	}

	public void setCstPisCofinsEntrada(String cstPisCofinsEntrada) {
		this.cstPisCofinsEntrada = cstPisCofinsEntrada;
	}

	public String getCstPisCofinsSaida() {
		return this.cstPisCofinsSaida;
	}

	public void setCstPisCofinsSaida(String cstPisCofinsSaida) {
		this.cstPisCofinsSaida = cstPisCofinsSaida;
	}

	public double getCusVend() {
		return this.cusVend;
	}

	public void setCusVend(double cusVend) {
		this.cusVend = cusVend;
	}

	public double getCustocompr() {
		return this.custocompr;
	}

	public void setCustocompr(double custocompr) {
		this.custocompr = custocompr;
	}

	public double getCustomedio() {
		return this.customedio;
	}

	public void setCustomedio(double customedio) {
		this.customedio = customedio;
	}

	public Date getDatInicio() {
		return this.datInicio;
	}

	public void setDatInicio(Date datInicio) {
		this.datInicio = datInicio;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEncrypthash() {
		return this.encrypthash;
	}

	public void setEncrypthash(String encrypthash) {
		this.encrypthash = encrypthash;
	}

	public String getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getIat() {
		return this.iat;
	}

	public void setIat(String iat) {
		this.iat = iat;
	}

	public double getIia() {
		return this.iia;
	}

	public void setIia(double iia) {
		this.iia = iia;
	}

	public double getIndexador() {
		return this.indexador;
	}

	public void setIndexador(double indexador) {
		this.indexador = indexador;
	}

	public double getIpi() {
		return this.ipi;
	}

	public void setIpi(double ipi) {
		this.ipi = ipi;
	}

	public String getIppt() {
		return this.ippt;
	}

	public void setIppt(String ippt) {
		this.ippt = ippt;
	}

	public String getLivre1() {
		return this.livre1;
	}

	public void setLivre1(String livre1) {
		this.livre1 = livre1;
	}

	public String getLivre2() {
		return this.livre2;
	}

	public void setLivre2(String livre2) {
		this.livre2 = livre2;
	}

	public String getLivre3() {
		return this.livre3;
	}

	public void setLivre3(String livre3) {
		this.livre3 = livre3;
	}

	public String getLivre4() {
		return this.livre4;
	}

	public void setLivre4(String livre4) {
		this.livre4 = livre4;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public double getLucVend() {
		return this.lucVend;
	}

	public void setLucVend(double lucVend) {
		this.lucVend = lucVend;
	}

	public double getMargemlb() {
		return this.margemlb;
	}

	public void setMargemlb(double margemlb) {
		this.margemlb = margemlb;
	}

	public String getMedida() {
		return this.medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
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

	public BigDecimal getOffpromo() {
		return this.offpromo;
	}

	public void setOffpromo(BigDecimal offpromo) {
		this.offpromo = offpromo;
	}

	public BigDecimal getOnpromo() {
		return this.onpromo;
	}

	public void setOnpromo(BigDecimal onpromo) {
		this.onpromo = onpromo;
	}

	public double getPeso() {
		return this.peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getPiva() {
		return this.piva;
	}

	public void setPiva(double piva) {
		this.piva = piva;
	}

	public double getPreco() {
		return this.preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Date getPromofim() {
		return this.promofim;
	}

	public void setPromofim(Date promofim) {
		this.promofim = promofim;
	}

	public Date getPromoini() {
		return this.promoini;
	}

	public void setPromoini(Date promoini) {
		this.promoini = promoini;
	}

	public double getQtdAtual() {
		return this.qtdAtual;
	}

	public void setQtdAtual(double qtdAtual) {
		this.qtdAtual = qtdAtual;
	}

	public double getQtdCompra() {
		return this.qtdCompra;
	}

	public void setQtdCompra(double qtdCompra) {
		this.qtdCompra = qtdCompra;
	}

	public double getQtdInicio() {
		return this.qtdInicio;
	}

	public void setQtdInicio(double qtdInicio) {
		this.qtdInicio = qtdInicio;
	}

	public double getQtdMinim() {
		return this.qtdMinim;
	}

	public void setQtdMinim(double qtdMinim) {
		this.qtdMinim = qtdMinim;
	}

	public double getQtdVend() {
		return this.qtdVend;
	}

	public void setQtdVend(double qtdVend) {
		this.qtdVend = qtdVend;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getSerie() {
		return this.serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getTipoItem() {
		return this.tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	public Date getUltCompra() {
		return this.ultCompra;
	}

	public void setUltCompra(Date ultCompra) {
		this.ultCompra = ultCompra;
	}

	public Date getUltVenda() {
		return this.ultVenda;
	}

	public void setUltVenda(Date ultVenda) {
		this.ultVenda = ultVenda;
	}

	public double getValVend() {
		return this.valVend;
	}

	public void setValVend(double valVend) {
		this.valVend = valVend;
	}

}