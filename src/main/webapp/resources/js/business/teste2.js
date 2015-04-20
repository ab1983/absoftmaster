/**
 * SMALL COMPRAS
 */
	var SmallUtil = com.algoboss.integration.small.business.SmallUtil;
	function onConstruction(){
		try{
			var fieldNames = new ArrayList();
			fmt.clear(requirement);
			fmt.putTypeConfig(requirement.getEntityClass(), "fornecedor", "objectList", "1236_clifor[clifor|equals|Fornecedor];nome;nome;cgc;comple");
			fmt.putTypeConfig(requirement.getEntityClass(), "operacao", "objectList", "1236_icm[cfop|lessThan|5000];nome;cfop,nome");
			fmt.putTypeConfig(requirement.getEntityClass(), "descricao3", "objectList", "1236_transpor;placa;placa,nome");
			fieldNames.clear();
			fieldNames.add("numeronf");
			// fieldNames.add("vendedor");
			fieldNames.add("operacao");
			fieldNames.add("fornecedor");
			// fieldNames.add("identificador1");
			// fieldNames.add("desconto");
			fieldNames.add("emissao");
			fieldNames.add("saidad");
			fieldNames.add("saidah");
			fieldNames.add("descricao3");
			fieldNames.add("total");
			fieldNames.add("volumes");
			fieldNames.add("modelo");
			fieldNames.add("produtos");
			fmt.clear(requirement);
			var fieldNamesList = new ArrayList(fieldNames);
			fieldNamesList.remove("operacao");
			fieldNamesList.remove("descricao3");
			fieldNamesList.remove("produtos");
			fieldNamesList.remove("saidad");
			fieldNamesList.remove("saidah");
			fieldNamesList.remove("modelo");
			fmt.show("list", fmt.array(fieldNamesList));
			fmt.show("form", fmt.array(fieldNames));
			fmt.readOnly("form", fmt.array( "total", "volumes" ));
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
			fieldNames.add("produtos.icm");
			fieldNames.add("produtos.vicms");
			fieldNames.add("produtos.ipi");
			fieldNames.add("produtos.vipi");
			fieldNamesList = new ArrayList(fieldNames);
			fieldNamesList.add(1,"produtos.descricao");
			// clear(requirement);
			fmt.show("form-form-1236_itens002", fmt.array(fieldNames));
			fmt.show("form-list-1236_itens002", fmt.array(fieldNamesList));

			fmt.style("list", "fornecedor", "width:350px;");

			fmt.readOnly("form-form-1236_itens002", fmt.array( "produtos.total","produtos.vicms","produtos.vipi" ));

			fmt.label("numeronf", "Nº NOTA FISCAL", "form", "list");
			// label("identificador1", "TIPO PREÇO","form","list");
			// label("transporta", "TRANSPORTADORA","form","list");
			fmt.label("descricao3", "TRANSPORTADORA", "form", "list");
			fmt.label("operacao", "OPERAÇÃO", "form", "list");
			fmt.label("emissao", "EMISSÃO", "form", "list");
			fmt.label("saidad", "DATA ENTRADA", "form", "list");
			fmt.label("saidah", "HORA ENTRADA", "form", "list");
			fmt.label("volumes", "QUANTIDADE", "form", "list");

			fmt.label("produtos.codigo", "CÓDIGO/DESCRIÇÃO", "form-form-1236_itens002");
			fmt.label("produtos.codigo", "CÓDIGO", "form-list-1236_itens002");
			fmt.label("produtos.descricao", "DESCRIÇÃO", "form-list-1236_itens002");
			fmt.label("produtos.unitario", "UNITÁRIO", "form-form-1236_itens002", "form-list-1236_itens002");
			fmt.label("produtos.icm", "% ICMS", "form-form-1236_itens002", "form-list-1236_itens002");
			fmt.label("produtos.vicms", "ICMS", "form-form-1236_itens002", "form-list-1236_itens002");
			fmt.label("produtos.ipi", "% IPI", "form-form-1236_itens002", "form-list-1236_itens002");
			fmt.label("produtos.vipi", "IPI", "form-form-1236_itens002", "form-list-1236_itens002");

			fmt.eventField("form-form-1236_itens002", fmt.array( "produtos.codigo" ), "eventBean('onChangeProduto()');focusBean('.c_quantidade.c_input');", "onchange");
			fmt.eventField("form-form-1236_itens002", fmt.array("produtos.quantidade", "produtos.unitario","produtos.icm","produtos.ipi" ), "eventBeanWait('produtoCalc()')", "onkeyup");
			
			fmt.eventField("form", fmt.array("numeronf"), "eventBean('onBlurNumeroNf()')","onblur");
			// eventField("form", new String[]{"cliente"},
			// "eventBean('descontoCliente()')",
			// "onchange");
			fmt.eventField("form", fmt.array( "descricao3" ), "eventBean('onChangeTransportadoraPlaca()')", "onchange");
			fmt.propertyStyleClass("form", "data-form", "onload", "if($('.c_saidah.c_input').val()===''){eventPage('onNew()');clearFormChanged();};eventPage('produtoTotalCalc()');");
			fmt.propertyStyleClass("form", "c_save_button", "value", "Gravar NF Compra");
			fmt.propertyStyleClass("form", "c_save_and_back_button", "value", "Finalizar NF Compra e Voltar");
			fmt.propertyStyleClass("form-form-1236_itens002", "c_save_button", "value", "Confirmar Produto");
			fmt.propertyStyleClass("form-form-1236_itens002", "c_save_and_back_button", "value", "Confirmar Produto e Voltar");
		}catch(e){
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
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
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				// volumesCalc(obj);
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
				var totalIcms = parseFloat(0);
				var totalIpi = parseFloat(0);
				var totalBaseIcms = parseFloat(0);
				for (var x = 0; x < objChildList.size(); x++) {
					var devEntityObject = objChildList.get(x);
					if (precoRecalc) {
						precoProdutoGenImpl(app, devEntityObject, false);
					}
					putProdutoTotal(app, devEntityObject);
					volumes++;
					var totalObj = devEntityObject.getPropObj("produtos.total").getPropertyValue();
					if (totalObj != null) {
						total +=  parseFloat(totalObj);
					}
					var pesoLiquidoObj = devEntityObject.getPropObj("produtos.peso").getPropertyValue();
					if (pesoLiquidoObj != null) {
						pesoLiquido += parseFloat(pesoLiquidoObj);
					}
					var ipiObj = devEntityObject.getPropObj("produtos.vipi").getPropertyValue();
					if (ipiObj != null) {
						totalIpi += parseFloat(ipiObj);
					}					
					var icmsObj = devEntityObject.getPropObj("produtos.vicms").getPropertyValue();
					if (icmsObj != null) {
						totalIcms += parseFloat(icmsObj);
						if(icmsObj>0){
							totalBaseIcms += parseFloat(totalObj);
						}
					}					
				}
				obj.getPropObj("volumes").setPropertyValue(volumes);
				obj.getPropObj("total").setPropertyValue(BigDecimal.valueOf(parseFloat(total)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("mercadoria").setPropertyValue(BigDecimal.valueOf(parseFloat(total)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("pesoliqui").setPropertyValue(BigDecimal.valueOf(parseFloat(pesoLiquido)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("icms").setPropertyValue(BigDecimal.valueOf(parseFloat(totalIcms)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("baseicm").setPropertyValue(BigDecimal.valueOf(parseFloat(totalBaseIcms)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				obj.getPropObj("ipi").setPropertyValue(BigDecimal.valueOf(parseFloat(totalIpi)).setScale(2, RoundingMode.HALF_UP).doubleValue());
				// obj.getPropObj("desconto").setPropertyValue(BigDecimal.valueOf(valorDesconto).setScale(2,
				// RoundingMode.HALF_UP).doubleValue());
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}

	function putProdutoTotal(app, objChild) {
		try {
			
		// DevEntityObject obj = app.getBean();
		var quantidade = objChild.getProp("produtos.quantidade");
		var unitario = objChild.getProp("produtos.unitario");
		if (quantidade == null || unitario == null) {
			return false;
		}
		var codigo = objChild.getProp("produtos.codigo");
		var produto = SmallUtil.getProduto(codigo, app);
		if(produto==null){
			return false;
		}
		var peso = produto.getProp("peso");
		if (peso == null) {
			peso = 0;
		}
		var icms = objChild.getProp("produtos.icm",0);
		var ipi = objChild.getProp("produtos.ipi",0);
		var totalProduto = BigDecimal.valueOf(quantidade * unitario).setScale(2, RoundingMode.HALF_UP).doubleValue();
		var pesoProduto = BigDecimal.valueOf(quantidade * peso).setScale(2, RoundingMode.HALF_UP).doubleValue();
		var totalIcms = BigDecimal.valueOf(totalProduto * icms/100).setScale(2, RoundingMode.HALF_UP).doubleValue();
		var totalIpi = BigDecimal.valueOf(totalProduto * ipi/100).setScale(2, RoundingMode.HALF_UP).doubleValue();
		if(!objChild.getProp("produtos.numeronf","").toString().isEmpty() && !objChild.getProp("produtos.numeronf","").toString().equals(obj.getProp("numeronf",""))){
			objChild.getPropObj("produtos.numeronf").setPropertyValue(obj.getProp("numeronf"));			
		}
		objChild.getPropObj("produtos.base").setPropertyValue(100);
		objChild.getPropObj("produtos.total").setPropertyValue(totalProduto);
		objChild.getPropObj("produtos.peso").setPropertyValue(pesoProduto);
		objChild.getPropObj("produtos.vicms").setPropertyValue(totalIcms);
		objChild.getPropObj("produtos.vipi").setPropertyValue(totalIpi);
		objChild.getPropObj("produtos.fornecedor").setPropertyValue(app.getBean().getProp("fornecedor"));
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
		return true;
	}

	function onChangeTransportadoraPlaca() {
		try {
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				// DevEntityObject objChild = app.getChildBean();
				var placa = obj.getProp("descricao3");
				var veiculo = SmallUtil.getVeiculo(placa, app);
				if (veiculo != null) {
					obj.getPropObj("transporta").setVal(veiculo.getProp("nome"));
				}
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}

	function onNew() {
		try {
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				var now = new java.util.Date();
				obj.getPropObj("emissao").setPropertyValue(now);
				obj.getPropObj("saidad").setPropertyValue(now);
				obj.getPropObj("saidah").setPropertyValue(new java.text.SimpleDateFormat("HH:mm:ss").format(now));
				obj.getPropObj("volumes").setPropertyValue(0);
				obj.getPropObj("total").setPropertyValue(0);
				obj.getPropObj("pesoliqui").setPropertyValue(0);
				obj.getPropObj("especie").setPropertyValue("Caixa");
				obj.getPropObj("marca").setPropertyValue("Volumes");		
				obj.getPropObj("modelo").setPropertyValue("55");	
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}
	
	function onBlurNumeroNf() {
		try {
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				var numeronf;
				try {
					numeronf = Long.valueOf(obj.getProp("numeronf", "0").toString());					
				} catch (e) {
					println(e);
					numeronf = 0;
				}
				obj.getPropObj("numeronf").setPropertyValue(java.lang.String.format("%012d",numeronf));
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}	
	
	function onChangeProduto() {
		try { 
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				var objChild = app.getChildBean();
				var nomeFornecedor = obj.getProp("fornecedor");
				var fornecedor = SmallUtil.getCliente(nomeFornecedor, app);
				if (fornecedor != null) {
					var nomeOperacao = obj.getProp("operacao");
					var operacao = SmallUtil.getOperacao(nomeOperacao, app);
					if (operacao != null) {
						var uf = fornecedor.getProp("estado");
						objChild.getPropObj("icm").setVal(operacao.getProp(Objects.toString(uf,"").toLowerCase() ));
					}
				}

				precoProdutoGen(app);
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}

	function precoProdutoGen() {
		precoProdutoGenImpl(app, app.getChildBean(), true);
	}

	function precoProdutoGenImpl(app, objChild, totalCalc) {
		try {
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1236_compra")) {
				// DevEntityObject objChild = app.getChildBean();
				var codigo = objChild.getProp("produtos.codigo");
				if (!Objects.toString(codigo, "").isEmpty()) {
					var devEntityObject = SmallUtil.getProduto(codigo, app);
					// Object tipoPreco =
					// obj.getPropObj("identificador1").getVal();

					var preco = devEntityObject.getProp("preco");

					if (preco == null) {
						preco = 0;
					}
					// DevEntityObject clienteObj =
					// SmallUtil.getCliente(obj.getProp("cliente"), app);
					// Number desconto = AlgoUtil.toNumber(clienteObj != null ?
					// clienteObj.getProp("identificador2") : null);
					// Double valorDesconto = total *
					// (desconto!=null?desconto.doubleValue()/100:0);
					objChild.getPropObj("unitario").setVal(BigDecimal.valueOf(preco).setScale(2, RoundingMode.HALF_UP).doubleValue());
					objChild.getPropObj("descricao").setVal(devEntityObject.getProp("descricao"));

					// objChild.getPropObj("peso").setVal(devEntityObject.getProp("peso"));
					// objChild.getPropObj("cfop").setVal(devEntityObject.getProp("cfop"));
					if (totalCalc) {
						produtoTotalCalc();
					}
				}
			}
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}
