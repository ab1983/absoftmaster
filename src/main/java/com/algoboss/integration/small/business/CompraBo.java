package com.algoboss.integration.small.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.face.AdmAlgoappBean;


public class CompraBo {

	public static void produtoCalc(AdmAlgoappBean app) {
		produtoTotalCalcImpl(app, false);
	}

	public static void produtoTotalCalc(AdmAlgoappBean app) {
		produtoTotalCalcImpl(app, false);
	}

	public static void produtoTotalCalcImpl(AdmAlgoappBean app, boolean precoRecalc) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
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
				Double totalIcms = 0d;
				Double totalIpi = 0d;
				Double totalBaseIcms = 0d;
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
					Object ipiObj = devEntityObject.getPropObj("produtos.vipi").getPropertyValue();
					if (ipiObj != null) {
						totalIpi += (Double) ipiObj;
					}					
					Object icmsObj = devEntityObject.getPropObj("produtos.vicms").getPropertyValue();
					if (icmsObj != null) {
						totalIcms += (Double) icmsObj;
						if((Double) icmsObj>0){
							totalBaseIcms += (Double) totalObj;
						}
					}					
				}
				obj.getPropObj("volumes").setPropertyValue(volumes);
				obj.getPropObj("total").setPropertyValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("mercadoria").setPropertyValue(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("pesoliqui").setPropertyValue(BigDecimal.valueOf(pesoLiquido).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("icms").setPropertyValue(BigDecimal.valueOf(totalIcms).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("baseicm").setPropertyValue(BigDecimal.valueOf(totalBaseIcms).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("ipi").setPropertyValue(BigDecimal.valueOf(totalIpi).setScale(2, RoundingMode.HALF_UP).doubleValue());
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
		Double icms = (Double) objChild.getProp("produtos.icm",0d);
		Double ipi = (Double) objChild.getProp("produtos.ipi",0d);
		Double totalProduto = BigDecimal.valueOf(quantidade * unitario).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Double pesoProduto = BigDecimal.valueOf(quantidade * peso).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Double totalIcms = BigDecimal.valueOf(totalProduto * icms/100d).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Double totalIpi = BigDecimal.valueOf(totalProduto * ipi/100d).setScale(2, RoundingMode.HALF_UP).doubleValue();
		objChild.getPropObj("produtos.base").setPropertyValue(100d);
		objChild.getPropObj("produtos.total").setPropertyValue(totalProduto);
		objChild.getPropObj("produtos.peso").setPropertyValue(pesoProduto);
		objChild.getPropObj("produtos.vicms").setPropertyValue(totalIcms);
		objChild.getPropObj("produtos.vipi").setPropertyValue(totalIpi);
		objChild.getPropObj("produtos.fornecedor").setPropertyValue(app.getBean().getProp("fornecedor"));
		return true;
	}

	public static void onChangeTransportadoraPlaca(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				// DevEntityObject objChild = app.getChildBean();
				Object placa = obj.getProp("descricao3");
				DevEntityObject veiculo = SmallUtil.getVeiculo(placa, app);
				if (veiculo != null) {
					obj.getPropObj("transporta").setVal(veiculo.getProp("nome"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void onNew(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				Date now = new Date();
				obj.getPropObj("emissao").setPropertyValue(now);
				obj.getPropObj("saidad").setPropertyValue(now);
				obj.getPropObj("saidah").setPropertyValue(new SimpleDateFormat("HH:mm:ss").format(now));
				obj.getPropObj("volumes").setPropertyValue(0d);
				obj.getPropObj("total").setPropertyValue(0d);
				obj.getPropObj("pesoliqui").setPropertyValue(0d);
				obj.getPropObj("especie").setPropertyValue("Caixa");
				obj.getPropObj("marca").setPropertyValue("Volumes");		
				obj.getPropObj("modelo").setPropertyValue("55");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void onBlurNumeroNf(AdmAlgoappBean app) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				Long numeronf;
				try {
					numeronf = Long.valueOf(obj.getProp("numeronf", "0").toString());					
				} catch (NumberFormatException e) {
					numeronf = 0L;
				}
				obj.getPropObj("numeronf").setPropertyValue(String.format("%012d",numeronf));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void onChangeProduto(AdmAlgoappBean app) {
		try { 
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				DevEntityObject objChild = app.getChildBean();
				Object nomeFornecedor = obj.getProp("fornecedor");
				DevEntityObject fornecedor = SmallUtil.getCliente(nomeFornecedor, app);
				if (fornecedor != null) {
					Object nomeOperacao = obj.getProp("operacao");
					DevEntityObject operacao = SmallUtil.getOperacao(nomeOperacao, app);
					if (operacao != null) {
						Object uf = fornecedor.getProp("estado");
						objChild.getPropObj("icm").setVal(operacao.getProp(Objects.toString(uf,"").toLowerCase() ));
					}
				}

				precoProdutoGen(app);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void precoProdutoGen(AdmAlgoappBean app) {
		precoProdutoGenImpl(app, app.getChildBean(), true);
	}

	public static void precoProdutoGenImpl(AdmAlgoappBean app, DevEntityObject objChild, boolean totalCalc) {
		try {
			DevEntityObject obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				// DevEntityObject objChild = app.getChildBean();
				Object codigo = objChild.getProp("produtos.codigo");
				if (!Objects.toString(codigo, "").isEmpty()) {
					DevEntityObject devEntityObject = SmallUtil.getProduto(codigo, app);
					// Object tipoPreco =
					// obj.getPropObj("identificador1").getVal();

					Double preco = (Double) devEntityObject.getProp("preco");

					if (preco == null) {
						preco = 0d;
					}
					// DevEntityObject clienteObj =
					// SmallUtil.getCliente(obj.getProp("cliente"), app);
					// Number desconto = AlgoUtil.toNumber(clienteObj != null ?
					// clienteObj.getProp("identificador2") : null);
					// Double valorDesconto = total *
					// (desconto!=null?desconto.doubleValue()/100d:0d);
					objChild.getPropObj("unitario").setVal(BigDecimal.valueOf(preco).setScale(2, RoundingMode.HALF_UP).doubleValue());
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
