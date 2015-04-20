function onConstruction() {
	var fieldNames = new ArrayList();
	fmt.clear(requirement);

	fieldNames.clear();
	fieldNames.add("seqnum");

	fieldNames.add("datasolicitacao");
	fieldNames.add("local");

	fieldNames.add("dataentrada");
	fieldNames.add("datasaida");
	fieldNames.add("qtdediarias");
	fieldNames.add("qtdehospedes");
	fieldNames.add("acomodacao");
	fieldNames.add("status");
	fieldNames.add("concluido");
	fieldNames.add("observacoes");
	fieldNames.add("opcoesdehospedagem");
	fieldNames.add("hospedagemescolhida");
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
	fmt.readOnly("form", fmt.array("seqnum", "status","hospedagemescolhida"));

	fieldNames.clear();
	fieldNames.add("opcoesdehospedagem.hotel");
	fieldNames.add("opcoesdehospedagem.categoria");
	fieldNames.add("opcoesdehospedagem.tarifa");
	fieldNames.add("opcoesdehospedagem.taxa");
	fieldNames.add("opcoesdehospedagem.observacao");
	fieldNamesList = new ArrayList(fieldNames);

	fmt.show("form-form-1146_opcoesdehospedagem", fmt.array(fieldNames));
	fmt.show("form-list-1146_opcoesdehospedagem", fmt.array(fieldNamesList));

	fmt.label("acomodacao", "ACOMODAÇÃO", "form", "list");

	fmt.propertyStyleClass("form", "data-form", "onload", "eventPage('onOpen()')");
	
	fmt.create("form", "c_button_panel", "ui-commandButton", "c_disponibilizar_button");
	fmt.propertyStyleClass("form", "c_disponibilizar_button", "value", "Disponibilizar");
	//fmt.propertyStyleClass("form-list-1146_opcoesdehospedagem", "c_disponibilizar_button", "actionListener", "#{app.doSelectChild(item)}");
	fmt.propertyStyleClass("form", "c_disponibilizar_button", "oncomplete", "eventPage('onDisponibilizarHospedagem()');");
	fmt.propertyStyleClass("form", "c_disponibilizar_button", "style", "#{(app.$g('1146_solicitacao.status')=='Pendente' and app.$g('1146_solicitacao.hospedagemescolhida')!='')?'':'display:none'}");	
	
}
function onOpen() {
	try {
		var obj = app.getBean();
		if (obj != null	&& obj.getEntityClass().getName().equals("1146_solicitacao")) {
			var objChildList = obj.getProp("opcoesdehospedagem");
			var respondido = false;
			//println(objChildList.get(0).getPropObj("opcoesdehospedagem.tarifa"));
			for (var x = 0; x < objChildList.size(); x++) {
				var devEntityObject = objChildList.get(x);
				var tarifa = devEntityObject.getPropObj("opcoesdehospedagem.tarifa").getPropertyValue();
				//println(tarifa);
				if (tarifa != null && tarifa > 0) {
					respondido = true;
					break;
				}
			}
			if (respondido && obj.getPropObj("hospedagemescolhida").getVal()==null) {
				obj.getPropObj("status").setPropertyValue("Respondido");
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
function onDisponibilizarHospedagem() {
	try {
		var obj = app.getBean();
		if (obj != null	&& obj.getEntityClass().getName().equals("1146_solicitacao")) {
			obj.getPropObj("status").setPropertyValue("Disponível");
		}
	} catch (e) {
		println(e);
		//println(e.stack);
		println('Line Number:'+e.lineNumber);
		println('Column Number:'+e.columnNumber);
		println('File Name:'+e.fileName);
		
	}
}