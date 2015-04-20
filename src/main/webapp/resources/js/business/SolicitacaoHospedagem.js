/**
 * 
 */
function onConstruction(){
			var fieldNames = new ArrayList();
			fmt.clear(requirement);
			//putTypeConfig(requirement.getEntityClass(), "fornecedor", "objectList", "1236_clifor[clifor|equals|Fornecedor];nome;nome;cgc;comple");
			//putTypeConfig(requirement.getEntityClass(), "operacao", "objectList", "1236_icm[cfop|lessThan|5000];nome;cfop,nome");
			//putTypeConfig(requirement.getEntityClass(), "transporta", "objectList", "1236_transpor;placa;placa,nome");
			fmt.putTypeConfig(requirement.getEntityClass(), "acomodacao", "list", "Single,Duplo Casal,Duplo Twin,Triplo,Quadruplo");
			fmt.putTypeConfig(requirement.getEntityClass(), "status", "list", "Pendente,Respondido,Disponível");
			fieldNames.clear();
			fieldNames.add("seqnum");
			// fieldNames.add("vendedor");
			fieldNames.add("datasolicitacao");
			fieldNames.add("local");
			// fieldNames.add("identificador1");
			// fieldNames.add("desconto");
			fieldNames.add("dataentrada");
			fieldNames.add("datasaida");
			fieldNames.add("qtdediarias");
			fieldNames.add("qtdehospedes");
			fieldNames.add("acomodacao");
			fieldNames.add("status");
			fieldNames.add("concluido");
			fieldNames.add("observacoes");
			fieldNames.add("opcoesdehospedagem");
			fmt.clear(requirement);
			var fieldNamesList = new ArrayList(fieldNames);
			fieldNamesList.remove("dataentrada");
			fieldNamesList.remove("datasaida");
			fieldNamesList.remove("qtdediarias");
			fieldNamesList.remove("produtos");
			fieldNamesList.remove("qtdehospedes");
			fieldNamesList.remove("observacoes");
			fmt.show("list", fmt.array(fieldNamesList));
			fmt.show("form", fmt.array(fieldNames));
			fmt.readOnly("form", fmt.array( "seqnum","status"));
			// putTypeConfig(requirement.getEntityClass(),"volumes","value","#{app.count('produtos')}");
			//putTypeConfig(requirement.getEntityClass(), "produtos.codigo", "objectList", "1236_estoque;codigo;codigo;descricao");
			// putTypeConfig(requirement.getEntityClass(),"produtos.total","value","#{(app.$('produtos.quantidade')).val*app.$('produtos.unitario').val)}");
			fieldNames.clear();
			fieldNames.add("opcoesdehospedagem.hotel");
			fieldNames.add("opcoesdehospedagem.tarifa");
			fieldNames.add("opcoesdehospedagem.taxa");
			fieldNames.add("opcoesdehospedagem.observacao");
			fieldNamesList = new ArrayList(fieldNames);
			//fmt.style("form-list-1146_opcoesdehospedagem", "opcoesdehospedagem.tarifa", "#{(app.$('opcoesdehospedagem.tarifa')).val=='Pendente')?'display:none;':''}");
			//fmt.style("form-list-1146_opcoesdehospedagem", "opcoesdehospedagem.taxa", "#{(app.$('opcoesdehospedagem.tarifa')).val=='Pendente')?'display:none;':''}");
			//fmt.style("form-list-1146_opcoesdehospedagem", "opcoesdehospedagem.observacao", "#{(app.$('opcoesdehospedagem.tarifa')).val=='Pendente')?'display:none;':''}");
			
			fmt.show("form-form-1146_opcoesdehospedagem", fmt.array(fieldNames));
			fmt.show("form-list-1146_opcoesdehospedagem", fmt.array(fieldNamesList));
			
			fmt.label("acomodacao", "ACOMODAÇÃO", "form", "list");
			fmt.readOnly("form-form-1146_opcoesdehospedagem", fmt.array( "opcoesdehospedagem.tarifa","opcoesdehospedagem.taxa"));

			fmt.propertyStyleClass("form", "data-form", "onload", "if($('.c_seqnum.c_input').val()===''){eventPage('onNew()');clearFormChanged();}else{eventPage('onOpen()');}");
}
function onNew(){

			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1146_solicitacao")) {
				var numero = obj.getPropObj("seqnum").getPropertyValue();
				if (Objects.toString(numero, "").isEmpty()) {
					obj.getPropObj("seqnum").setPropertyValue("+");
				}
				
				var now = new java.util.Date();
				obj.getPropObj("datasolicitacao").setPropertyValue(now);
				obj.getPropObj("qtdediarias").setPropertyValue(1);
				obj.getPropObj("qtdehospedes").setPropertyValue(1);
				obj.getPropObj("status").setPropertyValue("Pendente");
				obj.getPropObj("acomodacao").setPropertyValue("Single");
				
			}


}
function onOpen(){
			var obj = app.getBean();
			if (obj != null && obj.getEntityClass().getName().equals("1146_solicitacao")) {
				if(obj.getSeqnum()!=null){
					//obj.getPropObj("seqnum").setPropertyValue(obj.getSeqnum());
				}
			}
}