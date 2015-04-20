/**
 * SMALL ESTOQUE
 */
	var SmallUtil = com.algoboss.integration.small.business.SmallUtil;
	function onConstruction(){
		try{
			var fieldNames = new ArrayList();
			fmt.clear(requirement);
			fieldNames.clear();
			

			fieldNames.clear();
			fieldNames.add("codigo");
			fieldNames.add("descricao");
			fieldNames.add("preco");
			fieldNames.add("custocompr");
			fieldNames.add("livre1");
			fieldNames.add("peso");
			fieldNames.add("comissao");
			fieldNames.add("ipi");
			fieldNames.add("cf");
			fmt.clear(requirement);
			var fieldNamesList = new ArrayList(fieldNames);
			fmt.show("list", fmt.array(fieldNamesList));
			fmt.show("form", fmt.array(fieldNames));
			
			fmt.readOnly("form", fmt.array( "codigo"));
			
			fmt.label("codigo", "CÓDIGO", "form", "list");	
			fmt.label("descricao", "DESCRIÇÃO", "form", "list");	
			fmt.label("preco", "PREÇO", "form", "list");	
			fmt.label("custocompr", "CUSTO", "form", "list");	
			fmt.label("livre1", "% ICMS", "form", "list");	
			fmt.label("comissao", "COMISSÃO", "form", "list");	
			fmt.label("cf", "NCM", "form", "list");	
			
			fmt.propertyStyleClass("form", "data-form", "onload", "if($('.c_codigo.c_input').val()===''){eventPage('onNew()');clearFormChanged();};");			
		}catch(e){
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}

	function onNew(){
		var obj = app.getBean();
		var codigo = obj.getPropObj("codigo").getPropertyValue();
		if (Objects.toString(codigo, "").isEmpty()) {		
			obj.getPropObj("codigo").setPropertyValue(java.lang.String.format("%05d", Long.valueOf(codigoEstoqueGen())));
		}
		var now = new java.util.Date();
		obj.getPropObj("dat_inicio").setPropertyValue(now);		
	}
	
	function codigoEstoqueGen() {
		try {
			var nextcodigo = 0;
			try {
				try {
					nextcodigo = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT GEN_ID(G_CODIGO, 0 ) FROM RDB$DATABASE where not exists(select CODIGO from ESTOQUE WHERE CODIGO LIKE '%'(GEN_ID(G_CODIGO, 0 ))) and GEN_ID(G_CODIGO, 0 ) != 0;").getSingleResult();
				} catch (e) {
					nextcodigo = app.getBaseDao().getEntityManagerSmall().createNativeQuery("SELECT NEXT VALUE FOR G_CODIGO FROM RDB$DATABASE;").getSingleResult().toString();
				}						
			} catch (e) {	
				var message = "EstoqueBo: error to generate codigo. "+e.stack();
				map.put("message",message);
				//map.put("callback","restartFormChanged();focusBean('.c_numeronf .c_input');");
				println(message);						
			}
			return nextcodigo;
		} catch (e) {
			println(e);
			println(e.stack);
			println('Line Number:'+e.lineNumber);
			println('Column Number:'+e.columnNumber);
			println('File Name:'+e.fileName);
		}
	}
	