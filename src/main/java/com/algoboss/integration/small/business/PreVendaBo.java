package com.algoboss.integration.small.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.face.AdmAlgoappBean;
import com.algoboss.core.util.AlgoUtil;

public class PreVendaBo {
	public static void preRender(AdmAlgoappBean app) {

		// totalCalc(obj);
	}

	public static void produtoCalc(AdmAlgoappBean app) {
		produtoTotalCalcImpl(app, false);
	}

	public static void produtoTotalCalc(AdmAlgoappBean app) {
		produtoTotalCalcImpl(app, false);
	}

	public static void produtoTotalCalcImpl(AdmAlgoappBean app, boolean precoRecalc) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
				// volumesCalc(obj);
				DevEntityObject objChild = app.getChildBean();
				if (objChild != null && !objChild.getEntityPropertyValueList().isEmpty()) {
					if (!putProdutoTotal(app, objChild)) {
						return;
					}
				} else {
					objChild = null;
				}
				List<DevEntityObject> objChildList = (List<DevEntityObject>) obj.getProp("produtos");
				if (objChild != null && !objChildList.contains(objChild)) {
					objChildList.add(objChild);
				}
				Integer volumes = 0;
				Double total = 0d;
				Double pesoLiquido = 0d;
				for (DevEntityObject devEntityObject : objChildList) {
					if (precoRecalc) {
						precoProdutoGenImpl(app, devEntityObject, false);
					}
					putProdutoTotal(app, devEntityObject);
					volumes++;
					Object totalObj = devEntityObject.getPropObj("produtos.total").getPropertyValue();
					if (totalObj != null) {
						total += (Double) totalObj;
					}
					Object pesoLiquidoObj = devEntityObject.getPropObj("produtos.peso").getPropertyValue();
					if (pesoLiquidoObj != null) {
						pesoLiquido += (Double) pesoLiquidoObj;
					}
				}
				obj.getPropObj("volumes").setPropertyValue(volumes);
				obj.getPropObj("total").setPropertyValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("pesoliqui").setPropertyValue(BigDecimal.valueOf(pesoLiquido).setScale(2, RoundingMode.HALF_UP).doubleValue());
				// obj.getPropObj("desconto").setPropertyValue(BigDecimal.valueOf(valorDesconto).setScale(2,
				// RoundingMode.HALF_UP).doubleValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean putProdutoTotal(AdmAlgoappBean app, DevEntityObject objChild) {
		// DevEntityObject obj = app.getBean();
		Double quantidade = (Double) objChild.getProp("produtos.quantidade");
		Double unitario = (Double) objChild.getProp("produtos.unitario");
		if (quantidade == null || unitario == null) {
			return false;
		}
		Object codigo = objChild.getProp("produtos.codigo");
		DevEntityObject produto = SmallUtil.getProduto(codigo, app);
		Double peso = (Double) produto.getProp("peso");
		if (peso == null) {
			peso = 0d;
		}
		Double totalProduto = BigDecimal.valueOf(quantidade * unitario).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Double pesoProduto = BigDecimal.valueOf(quantidade * peso).setScale(2, RoundingMode.HALF_UP).doubleValue();
		objChild.getPropObj("produtos.total").setPropertyValue(totalProduto);
		objChild.getPropObj("produtos.peso").setPropertyValue(pesoProduto);
		return true;
	}

	public static void descontoCliente(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
				// DevEntityObject objChild = app.getChildBean();
				Object cliente = obj.getProp("cliente");
				DevEntityObject clienteObj = SmallUtil.getCliente(cliente, app);
				if (clienteObj != null) {
					obj.getPropObj("duplicatas").setVal(Objects.toString(clienteObj.getProp("identificador1"), "").split(Pattern.quote("/")).length);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setTransportadoraPlaca(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
				// DevEntityObject objChild = app.getChildBean();
				Object placa = obj.getProp("placa");
				DevEntityObject veiculo = SmallUtil.getVeiculo(placa, app);
				if (veiculo != null) {
					obj.getPropObj("transporta").setVal(veiculo.getProp("nome"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void numeroNfGen(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
				Object numeronf = obj.getPropObj("numeronf").getPropertyValue();
				if (Objects.toString(numeronf, "").isEmpty()) {

					Object nextnumeronf = 0;
					try {
						try {
							nextnumeronf = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT GEN_ID(G_SERIE1, 0 )||'001' FROM RDB$DATABASE where not exists(select NUMERONF from VENDAS WHERE NUMERONF LIKE '%'||(GEN_ID(G_SERIE1, 0 ))||'001') and GEN_ID(G_SERIE1, 0 ) != 0;").getSingleResult();
						} catch (NoResultException e) {
							nextnumeronf = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT NEXT VALUE FOR G_SERIE1 FROM RDB$DATABASE;").getSingleResult().toString().concat("001");
						}						
					} catch (Exception e) {
						String msgTmp = "PreVendaBo: error to generate numeronf. "+e.getMessage();
						System.err.println(msgTmp);
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgTmp, "");
						FacesContext.getCurrentInstance().addMessage(null, msg);						
					}
					obj.getPropObj("numeronf").setPropertyValue(String.format("%012d", Long.valueOf(nextnumeronf.toString())));
					obj.getPropObj("especie").setPropertyValue("Caixa");
					obj.getPropObj("marca").setPropertyValue("Volumes");
					obj.getPropObj("frete12").setPropertyValue("0");

					obj.getPropObj("identificador1").setPropertyValue("PREÇO1");
					Date now = new Date();
					obj.getPropObj("emissao").setPropertyValue(now);
					obj.getPropObj("saidad").setPropertyValue(now);
					obj.getPropObj("saidah").setPropertyValue(new SimpleDateFormat("HH:mm:ss").format(now));
					obj.getPropObj("volumes").setPropertyValue(0d);
					obj.getPropObj("total").setPropertyValue(0d);
					obj.getPropObj("pesoliqui").setPropertyValue(0d);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void precoProdutoGen(AdmAlgoappBean app) {
		precoProdutoGenImpl(app, app.getChildBean(), true);
	}

	public static void precoProdutoGenImpl(AdmAlgoappBean app, DevEntityObject objChild, boolean totalCalc) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
				// DevEntityObject objChild = app.getChildBean();
				Object codigo = objChild.getProp("produtos.codigo");
				if (!Objects.toString(codigo, "").isEmpty()) {
					DevEntityObject devEntityObject = SmallUtil.getProduto(codigo, app);
					Object tipoPreco = obj.getPropObj("identificador1").getVal();

					Double preco;
					if (tipoPreco == null || tipoPreco.equals("PREÇO1")) {
						preco = (Double) devEntityObject.getProp("preco");
					} else {
						preco = (Double) AlgoUtil.toNumber(devEntityObject.getProp("livre2"));
					}
					if (preco == null) {
						preco = 0d;
					}
					DevEntityObject clienteObj = SmallUtil.getCliente(obj.getProp("cliente"), app);
					Number desconto = AlgoUtil.toNumber(clienteObj != null ? clienteObj.getProp("identificador2") : null);
					// Double valorDesconto = total *
					// (desconto!=null?desconto.doubleValue()/100d:0d);
					objChild.getPropObj("unitario").setVal(BigDecimal.valueOf(preco * (desconto != null ? 1 - desconto.doubleValue() / 100d : 1d)).setScale(2, RoundingMode.HALF_UP).doubleValue());
					objChild.getPropObj("descricao").setVal(devEntityObject.getProp("descricao"));

					// objChild.getPropObj("peso").setVal(devEntityObject.getProp("peso"));
					// objChild.getPropObj("cfop").setVal(devEntityObject.getProp("cfop"));
					if (totalCalc) {
						produtoTotalCalc(app);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
