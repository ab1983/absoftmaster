/**
 * SMALL VENDAS
 */
var SmallUtil = com.algoboss.integration.small.business.SmallUtil;
var AlgoUtil = com.algoboss.erp.util.AlgoUtil;

function Vendas() {
	// var nfexml;

	this.registro = null;
	this.aliquota = null;
	this.anvisa = null;
	this.baseicm = null;
	this.baseiss = null;
	this.basesubsti = null;
	this.ccexml = null;
	this.cliente = null;
	this.codSit = null;
	this.complemento = null;
	this.dataCancel = null;
	this.desconto = null;
	this.despesas = null;
	this.duplicatas = null;
	this.emissao = null;
	this.emitida = null;
	this.especie = null;
	this.frete = null;
	this.frete12 = null;
	this.horaCancel = null;
	this.icce = null;
	this.icms = null;
	this.icmssubsti = null;
	this.identificador1 = null;
	this.ipi = null;
	this.iss = null;
	this.loked = null;
	this.marca = null;
	this.mercadoria = null;
	this.modelo = null;
	this.nfeid = null;
	this.nfeprotocolo = null;
	this.nferecibo = null;
	this.nfexml = null;
	this.nsu = null;
	this.nsud = null;
	this.nsuh = null;
	this.numeronf = null;
	this.nvol = null;
	this.operacao = null;
	this.pesobruto = null;
	this.pesoliqui = null;
	this.placa = null;
	this.reciboxml = null;
	this.saidad = null;
	this.saidah = null;
	this.seguro = null;
	this.servicos = null;
	this.status = null;
	this.total = null;
	this.transporta = null;
	this.vendedor = null;
	this.volumes = null;
	this.produtos = new ArrayList();

}

function Produto() {
	this.codigo = null;
	this.quantidade = null;
	this.unitario = null;
	this.total = null;
	this.cfop = null;
	this.base = null;
	this.icm = null;
	this.vicms = null;
	this.ipi = null;
	this.descricao = null;

}

function onConstruction() {
	try {
		var fieldNames = new ArrayList();
		fmt.clear(requirement);
		fmt.putTypeConfig(requirement.getEntityClass(), "cliente", "objectList", "1236_clifor[clifor|equals|Cliente];nome;nome;cgc;comple");
		fmt.putTypeConfig(requirement.getEntityClass(), "vendedor", "objectList", "1236_vendedor[funcao|equals|VENDEDOR];nome;nome");
		fmt.putTypeConfig(requirement.getEntityClass(), "operacao", "objectList", "1236_icm;nome;cfop,nome");
		fmt.putTypeConfig(requirement.getEntityClass(), "placa", "objectList", "1236_transpor;placa;placa,nome");
		fieldNames.clear();
		fieldNames.add("numeronf");
		fieldNames.add("vendedor");
		fieldNames.add("operacao");
		fieldNames.add("cliente");
		fieldNames.add("identificador1");
		fieldNames.add("desconto");
		fieldNames.add("emissao");
		fieldNames.add("saidad");
		fieldNames.add("saidah");
		fieldNames.add("placa");
		fieldNames.add("total");
		fieldNames.add("volumes");
		fieldNames.add("produtos");
		fmt.clear(requirement);
		var fieldNamesList = new ArrayList(fieldNames);
		fieldNamesList.remove("identificador1");
		fieldNamesList.remove("desconto");
		fieldNamesList.remove("operacao");
		fieldNamesList.remove("placa");
		fieldNamesList.remove("produtos");
		fieldNamesList.remove("saidad");
		fieldNamesList.remove("saidah");
		fmt.show("list", fmt.array(fieldNamesList));
		fmt.show("form", fmt.array(fieldNames));
		fmt.readOnly("form", fmt.array("numeronf", "total", "volumes"));
		// putTypeConfig(requirement.getEntityClass(),"volumes","value","#{app.count('produtos')}");
		fmt.putTypeConfig(requirement.getEntityClass(), "produtos.codigo", "objectList", "1236_estoque;codigo;codigo;descricao");
		// putTypeConfig(requirement.getEntityClass(),"produtos.total","value","#{(app.$('produtos.quantidade')).val*app.$('produtos.unitario').val)}");
		fieldNames.clear();
		// fieldNames.add("produtos.registro");
		fieldNames.add("produtos.codigo");
		// fieldNames.add("produtos.descricao");
		fieldNames.add("produtos.quantidade");
		fieldNames.add("produtos.unitario");
		fieldNames.add("produtos.total");
		fieldNamesList = new ArrayList(fieldNames);
		fieldNamesList.add(1, "produtos.descricao");
		// clear(requirement);
		fmt.show("form-form-1236_itens001", fmt.array(fieldNames));
		fmt.show("form-list-1236_itens001", fmt.array(fieldNamesList));

		fmt.style("list", "cliente", "width:350px;");
		
		fmt.style("form", "cliente", "width:350px;");
		fmt.style("form", "operacao", "width:350px;");		

		fmt.readOnly("form-form-1236_itens001", fmt.array("produtos.total"));

		fmt.label("numeronf", "Nº PRÉ-VENDA", "form", "list");
		fmt.label("identificador1", "TIPO PREÇO", "form", "list");
		// label("transporta", "TRANSPORTADORA","form","list");
		fmt.label("placa", "TRANSPORTADORA", "form", "list");
		fmt.label("operacao", "OPERAÇÃO", "form", "list");
		fmt.label("emissao", "EMISSÃO", "form", "list");
		fmt.label("saidad", "DATA SAÍDA", "form", "list");
		fmt.label("saidah", "HORA SAÍDA", "form", "list");
		fmt.label("volumes", "QUANTIDADE", "form", "list");

		fmt.label("produtos.codigo", "CÓDIGO/DESCRIÇÃO", "form-form-1236_itens001");
		fmt.label("produtos.unitario", "UNITÁRIO", "form-form-1236_itens001", "form-list-1236_itens001");
		fmt.label("produtos.codigo", "CÓDIGO", "form-list-1236_itens002");
		fmt.label("produtos.descricao", "DESCRIÇÃO", "form-list-1236_itens002");

		fmt.eventField("form-form-1236_itens001", fmt.array("produtos.codigo"),
				"eventBean('precoProdutoGen()');focusBean('.c_quantidade.c_input');", "onchange");
		fmt.eventField("form-form-1236_itens001", fmt.array("produtos.quantidade", "produtos.unitario"),
				"eventBean('produtoCalc()')", "onblur");
		fmt.eventField("form", fmt.array("identificador1"), "eventBean('produtoTotalCalc()')", "onchange");
		fmt.eventField("form", fmt.array("cliente"), "eventBean('descontoCliente()')", "onchange");
		fmt.eventField("form", fmt.array("placa"), "eventBean('setTransportadoraPlaca()')", "onchange");
		fmt
				.propertyStyleClass(
						"form",
						"data-form",
						"onload",
						"if($('.c_numeronf.c_input').val()===''){eventPage('numeroNfGen()');clearFormChanged();}else{/*eventPage('com.algoboss.integration.small.business.PreVendaBo.produtoTotalCalc');*/}");
		fmt.propertyStyleClass("form", "c_save_button", "value", "Gravar Pré-Venda");
		fmt.propertyStyleClass("form", "c_save_and_back_button", "value", "Finalizar Pré-Venda e Voltar");
		fmt.propertyStyleClass("form-form-1236_itens001", "c_save_button", "value", "Confirmar Produto");
		fmt.propertyStyleClass("form-form-1236_itens001", "c_save_and_back_button", "value", "Confirmar Produto e Voltar");
		
		fmt.create("form", "c_button_panel", "ui-commandButton", "c_enviar_button")
		.attr("value", "Enviar NFe")
		.attr("onclick","eventCall('enviarNFe()')")
		.attr("immediate", "true")
		.attr("icon", "ui-icon-transferthick-e-w");				
		
		fmt.create("form", "c_button_panel", "ui-commandButton", "c_emitir_button")
		.attr("value", "Emitir DANFE")
		.attr("onclick","eventCall('emitirDanfe()')")
		.attr("immediate", "true")
		.attr("icon", "ui-icon-document");			

	} catch (e) {
		stack(e);
	}
}

function produtoCalc() {
	produtoTotalCalcImpl(app, false);
}

function produtoTotalCalc() {
	produtoTotalCalcImpl(app, false);
}

function produtoTotalCalcImpl(app, precoRecalc) {
	try {
		var obj = app.getBean();
		if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {

			var objChild = app.getChildBean();
			if (objChild != null && !objChild.getEntityPropertyValueList().isEmpty()) {
				if (!putProdutoTotal(app, objChild)) {
					return;
				}
			} else {
				objChild = null;
			}
			var objChildList = obj.getProp("produtos");
			if (objChild != null && !objChildList.contains(objChild)) {
				objChildList.add(objChild);
			}
			var volumes = parseFloat(0);
			var total = parseFloat(0);
			var pesoLiquido = parseFloat(0);
			for (var x = 0; x < objChildList.size(); x++) {
				var devEntityObject = objChildList.get(x);
				if (precoRecalc) {
					precoProdutoGenImpl(app, devEntityObject, false);
				}
				putProdutoTotal(app, devEntityObject);
				volumes++;
				var totalObj = devEntityObject.getPropObj("produtos.total").getPropertyValue();
				if (totalObj != null) {
					total += parseFloat(totalObj);
				}
				var pesoLiquidoObj = devEntityObject.getPropObj("produtos.peso").getPropertyValue();
				if (pesoLiquidoObj != null) {
					pesoLiquido += parseFloat(pesoLiquidoObj);
				}
			}
			obj.getPropObj("volumes").setPropertyValue(volumes);
			try{
			obj.getPropObj("total").setPropertyValue(BigDecimal.valueOf(new Double(total)).setScale(2, RoundingMode.HALF_UP).doubleValue());
			}catch(e){
				println(total);
			}
			obj.getPropObj("pesoliqui").setPropertyValue(BigDecimal.valueOf(new Double(pesoLiquido)).setScale(2, RoundingMode.HALF_UP).doubleValue());
			// obj.getPropObj("desconto").setPropertyValue(BigDecimal.valueOf(valorDesconto).setScale(2,
			// RoundingMode.HALF_UP).doubleValue());
		}
	} catch (e) {
		stack(e);
	}
}

function putProdutoTotal(app, objChild) {
	// DevEntityObject obj = app.getBean();
	var quantidade = objChild.getProp("produtos.quantidade");
	var unitario = objChild.getProp("produtos.unitario");
	if (quantidade == null || unitario == null) {
		return false;
	}
	var codigo = objChild.getProp("produtos.codigo");
	var produto = SmallUtil.getProduto(codigo, app);
	var peso = produto.getProp("peso");
	if (peso == null) {
		peso = 0;
	}
	var totalProduto = BigDecimal.valueOf(quantidade * unitario).setScale(2, RoundingMode.HALF_UP).doubleValue();
	var pesoProduto = BigDecimal.valueOf(quantidade * peso).setScale(2, RoundingMode.HALF_UP).doubleValue();
	objChild.getPropObj("produtos.total").setPropertyValue(totalProduto);
	objChild.getPropObj("produtos.peso").setPropertyValue(pesoProduto);
	return true;
}

function descontoCliente() {
	try {
		var obj = app.getBean();
		if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
			// DevEntityObject objChild = app.getChildBean();
			var cliente = obj.getProp("cliente");
			var clienteObj = SmallUtil.getCliente(cliente, app);
			if (clienteObj != null) {
				obj.getPropObj("duplicatas").setVal(Objects.toString(clienteObj.getProp("identificador1"), "").split(java.util.regex.Pattern.quote("/")).length);
			}
		}
	} catch (e) {
		stack(e);
	}
}

function setTransportadoraPlaca() {
	try {
		var obj = app.getBean();
		if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
			// DevEntityObject objChild = app.getChildBean();
			var placa = obj.getProp("placa");
			var veiculo = SmallUtil.getVeiculo(placa, app);
			if (veiculo != null) {
				obj.getPropObj("transporta").setVal(veiculo.getProp("nome"));
			}
		}
	} catch (e) {
		stack(e);
	}
}

function numeroNfGen() {
	try {
		var obj = app.getBean();
		if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
			var numeronf = obj.getPropObj("numeronf").getPropertyValue();
			if (Objects.toString(numeronf, "").isEmpty()) {

				var nextnumeronf = 0;
				try {
					try {
						nextnumeronf = app
								.getBaseDao()
								.getEntityManagerSmall()
								.createNativeQuery(
										"SELECT GEN_ID(G_SERIE1, 0 )||'001' FROM RDB$DATABASE where not exists(select NUMERONF from VENDAS WHERE NUMERONF LIKE '%'||(GEN_ID(G_SERIE1, 0 ))||'001') and GEN_ID(G_SERIE1, 0 ) != 0;")
								.getSingleResult();
					} catch (e) {
						nextnumeronf = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT NEXT VALUE FOR G_SERIE1 FROM RDB$DATABASE;")
								.getSingleResult().toString().concat("001");
					}
				} catch (e) {
					var msgTmp = "PreVendaBo: error to generate numeronf. " + e;
					println(msgTmp);
					// FacesMessage msg = new
					// FacesMessage(FacesMessage.SEVERITY_ERROR, msgTmp, "");
					// FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				obj.getPropObj("numeronf").setPropertyValue(java.lang.String.format("%012d", Long.valueOf(nextnumeronf.toString())));
				obj.getPropObj("especie").setPropertyValue("Caixa");
				obj.getPropObj("marca").setPropertyValue("Volumes");
				obj.getPropObj("frete12").setPropertyValue("0");

				obj.getPropObj("identificador1").setPropertyValue("PREÇO1");
				var now = new java.util.Date();
				obj.getPropObj("emissao").setPropertyValue(now);
				obj.getPropObj("saidad").setPropertyValue(now);
				obj.getPropObj("saidah").setPropertyValue(new java.text.SimpleDateFormat("HH:mm:ss").format(now));
				obj.getPropObj("volumes").setPropertyValue(0);
				obj.getPropObj("total").setPropertyValue(0);
				obj.getPropObj("pesoliqui").setPropertyValue(0);
			}
		}
	} catch (e) {
		stack(e);
	}
}

function precoProdutoGen() {
	precoProdutoGenImpl(app, app.getChildBean(), true);
}

function precoProdutoGenImpl(app, objChild, totalCalc) {
	try {
		var obj = app.getBean();
		if (obj != null && obj.getEntityClass().getName().equals("1236_venda")) {
			// DevEntityObject objChild = app.getChildBean();
			var codigo = objChild.getProp("produtos.codigo");
			if (!Objects.toString(codigo, "").isEmpty()) {
				var devEntityObject = SmallUtil.getProduto(codigo, app);
				var tipoPreco = obj.getPropObj("identificador1").getVal();

				var preco;
				if (tipoPreco == null || tipoPreco.equals("PREÇO1")) {
					preco = devEntityObject.getProp("preco");
				} else {
					preco = AlgoUtil.toNumber(devEntityObject.getProp("livre2"));
				}
				if (preco == null) {
					preco = 0;
				}
				var clienteObj = SmallUtil.getCliente(obj.getProp("cliente"), app);
				var desconto = AlgoUtil.toNumber(clienteObj != null ? clienteObj.getProp("identificador2") : null);
				// Double valorDesconto = total *
				// (desconto!=null?desconto.doubleValue()/100d:0d);
				objChild.getPropObj("unitario")
						.setVal(
								BigDecimal.valueOf(preco * (desconto !== null ? 1 - desconto.doubleValue() / 100 : 1)).setScale(2, RoundingMode.HALF_UP)
										.doubleValue());
				objChild.getPropObj("descricao").setVal(devEntityObject.getProp("descricao"));

				// objChild.getPropObj("peso").setVal(devEntityObject.getProp("peso"));
				// objChild.getPropObj("cfop").setVal(devEntityObject.getProp("cfop"));
				if (totalCalc) {
					produtoTotalCalc(app);
				}
			}
		}
	} catch (e) {
		stack(e);
	}
}
function enviarNFe(){
	map.put("callback", "alert('NFe enviada com sucesso!');");
}
function emitirDanfe(){
	map.put("callback", "alert('Danfe emitido com sucesso!');");
}

function stack(e) {
	println(e);
	println(e.stack);
	println('Line Number:' + e.lineNumber);
	println('Column Number:' + e.columnNumber);
	println('File Name:' + e.fileName);
}
