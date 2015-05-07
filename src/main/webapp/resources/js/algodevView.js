/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var loaded = false;
var page = null; 
var formOld = "";
var formOld2 = "";
var beanMapCache = new Array();
var timeoutVar = null;

function oncomplete(xhr, status, args){
	var call = args.callback;
	if(call!==null && call !== undefined && call !== ""){
        timeoutVar = window.setTimeout(function(call) {
        	//if(isFormChanged()){
            		$(document).ready(function() {
            			//alert(1);
            			eval(call);           		            			
            		});  	            	
        	//}                
        }, 500,call);                		
		
	}
	//log(call);            	
}

function isFormChanged(){
	//log('formChanged');
	var formCloned = $('.ui-algo-container-form').clone(true);
	var els = formCloned.children().remove("input[name='javax.faces.ViewState']");;            	              	
    var formNew = $(formCloned).serialize();  
	
    if(formNew !== formOld && formNew!== formOld2){
    	formOld2 = formOld;
        formOld = formNew;  
        return true;
    }else{
    	return false;
    }        	
}

function clearFormChanged(){
    formOld = "";
    formOld2 = "";            	
}

function restartFormChanged(){
	clearFormChanged();
	isFormChanged();
} 

function eventPage(call) {
    //if (page !== '#{app.containerPage}') {
        //page = '#{app.containerPage}';
    //}
    timeoutVar = window.setTimeout(function(call) {
    	if(isFormChanged()){
        		$(document).ready(function() {
        			//alert(1);
	           		eventPageRc([{name: 'call', value: call}]);            		            			
	           		log(call);
        		});  	            	
    	}                
    }, 500,call);       
}     

function eventBean(call) {
	if(timeoutVar!==null){
		clearTimeout(timeoutVar);
		//alert('1');
	}            	
	if(isFormChanged()){
		$(document).ready(function() {
       		eventBeanRc([{name: 'call', value: call}]);     
       		log(call);       		
		});
	}
}

function eventBeanWait(call,timeout) {
	if(timeoutVar!==null){
		clearTimeout(timeoutVar);
	}
	
	if(timeout===undefined){
		timeout = 2000;
	}
		//alert('1');
		$(document).ready(function() {
			timeoutVar = window.setTimeout(function(call) {
        	if(isFormChanged()){
       			eventBeanRc([{name: 'call', value: call}]);   
           		log(call);
       			timeoutVar = null;
       			//alert('2');
        	}
            }, timeout,call);             			
		});
}   

function eventCall(call){
	eventCallRc([{name: 'call', value: call}]);   
	log(call);
}   

function focusBean(element) {
    window.setTimeout(function() {
        $(element).focus();
    }, 1000);            
}

function reloadSelectsByJS(){
    var selects = $('.ui-algo-container').find('select');
    var selectsUI = $('.ui-selectonemenu-items-wrapper').find('ul');
    var idxSel = 0;
    var page1 = page;
    //alert(selectsUI.length);
	if(typeof beanMapCache[page1]==='undefined'){
    	beanMapCache[page1] = new Array();	            		
	}            
    selects.each(function() {
    	//alert(beanMapCache[page]);   
    	//alert(typeof beanMapCache[page]!=='undefined');
    	//alert(idxSel);
    	//alert(idxCached.indexOf('false')===-1);
    	if(idxCached.indexOf('false')===-1){
            var idEl = $(this).attr('id').replace('_input','');
            var idWdEl = 'widget_'+idEl.replace(':','_');
            try {
	            var elWd = eval(idWdEl);
            	
	            if(typeof beanMapCache[page1][idxSel]!=='undefined'){
	            	//alert(beanMapCache[page][idxSel]);
	            	//$(this).empty();
		            //$(this).append(beanMapCache[page1][idxSel]);
		            ///$(selectsUI[selectsUI.length - selects.length + idxSel]).empty();
		            //$(selectsUI[selectsUI.length - selects.length + idxSel]).html(beanMapCache[page1][idxSel+'-ui']);
		            //var elWdOld = beanMapCache[page1][idxSel+'-wd'];			            
		            //elWd.preShowValue = elWd.options.filter(":selected");
		            
		            //var panelId = elWd.jqId + '_panel';
		            var input = $(elWd.jqId + '_input');
		            //var focusInput = $(elWd.jqId + '_focus');
		            //var label = elWd.jq.find('.ui-selectonemenu-label');
		            //var menuIcon = elWd.jq.children('.ui-selectonemenu-trigger');
		            //var panel = elWd.jq.children(this.panelId);
		            //var disabled = elWd.jq.hasClass('ui-state-disabledc');
		            //var itemsWrapper = elWd.panel.children('.ui-selectonemenu-items-wrapper');
		            var itemsContainer = elWd.itemsWrapper.children('.ui-selectonemenu-items');
		            //itemsContainer.empty();
		            var selected = input.val();
		            //if(input.val()===null || input.val()===''){
	            	
		            itemsContainer.html(beanMapCache[page1][idxSel+'-ui'].clone());
		            //elWd.items = beanMapCache[page1][idxSel+'-ui'];
		           // var items = elWd.itemsContainer.find('.ui-selectonemenu-item');
		            //var options = elWd.input.children('option');
		            //input.empty();
		            input.html(beanMapCache[page1][idxSel].clone());
		            
		            
			        input.val(selected);
		            restartSelect(elWd,elWd.cfg);
		            //elWd.options = elWd.options.clone().empty();//beanMapCache[page1][idxSel];
		            elWd.bindEvents();
	                   
		            elWd.bindConstantEvents();
	                   
		            elWd.setupDialogSupport();
		            //elWd.input.data(PrimeFaces.CLIENT_ID_DATA, elWd.id);
		            //}else{
		            	//alert(input.val());
		            //}
		            //$(input).find('option[value=\"'+input.val()+'\"]').attr('selected',true);
		            //var cfg.effect = elWd.cfg.effect||'fade';
		            //var cfg.effectSpeed = elWd.cfg.effectSpeed||'normal';
		            //var optGroupsSize = elWd.itemsContainer.children('li.ui-selectonemenu-item-group').length;
		            
		            
		            
		            //elWd.options.empty();
		            //elWd.options.append(beanMapCache[page1][idxSel]);
		            //var preShow = elWd.preShowValue;
	            	
		            /*
		            //$(function(){
		            	PrimeFaces.cw('SelectOneMenu',idWdEl,{id:idEl,filter:true,filterMatchMode:'contains',			            	
		            	behaviors:{change:function(event,ext){
		            		PrimeFaces.bc(elWd,event,ext,['PrimeFaces.ab({source:'+idEl+',event:\'valueChange\',process:'+idEl+'}, arguments[1]);']);
		            		}
		            }			            
		            });
		            	//});
		            */
		           
	            }else{
	            	//alert(page+2);
	            	beanMapCache[page1][idxSel] = elWd.options.clone();//$(this).children();
	            	beanMapCache[page1][idxSel+'-ui'] = elWd.items.clone();//$(selectsUI[selectsUI.length - selects.length + idxSel]).children();
	            	//beanMapCache[page1][idxSel+'-wd'] = elWd.jq.clone();
	            	//alert(page+3);
	            }						
            idxSel++;
            //alert(idxSel+"/"+selects.length);
			} catch (e) {
				//log("##Stack do Erro:"+e.stack);
				error("reloadSelectsByJS:"+e);
			}
    	}
    });
	
}   
function log(msg) {
    setTimeout(function() {
    	console.log(msg);
        //throw new Error(msg);
    }, 0);
}  
function error(msg) {
    setTimeout(function() {
        throw new Error(msg);
    }, 0);
}      
function restartSelect(_this,cfg) {
    //_this._super(cfg);
   	_this.unbindEvents();
    _this.panelId = _this.jqId + '_panel';
    _this.input = $(_this.jqId + '_input');
    _this.focusInput = $(_this.jqId + 
    		'_focus');
    _this.label = _this.jq.find('.ui-selectonemenu-label');
    _this.menuIcon = _this.jq.children('.ui-selectonemenu-trigger');
    _this.panel = $(_this.panelId);//_this.panel = _this.jq.children(_this.panelId);
    //alert(_this.panelId+_this.jq.children(_this.panelId))

    _this.disabled = _this.jq.hasClass('ui-state-disabled');
    _this.itemsWrapper = _this.panel.children('.ui-selectonemenu-items-wrapper');
    _this.itemsContainer = _this.itemsWrapper.children('.ui-selectonemenu-items');
    _this.items = _this.itemsContainer.find('.ui-selectonemenu-item');
    //alert(_this.items.length);
    _this.options = _this.input.find('option');
    _this.preShowValue = _this.input.find('option')[0];
    _this.cfg.effect = _this.cfg.effect||'fade';
    _this.cfg.effectSpeed = _this.cfg.effectSpeed||'normal';
    _this.optGroupsSize = _this.itemsContainer.children('li.ui-selectonemenu-item-group').length;

    var $_this = _this,
    selectedOption = _this.options.filter(':selected');
    //disable options
    _this.options.filter(':disabled').each(function() {
        $_this.items.eq($(this).index()).addClass('ui-state-disabled');
    });
   
    //triggers
    _this.triggers = _this.cfg.editable ? _this.jq.find('.ui-selectonemenu-trigger') : _this.jq.find('.ui-selectonemenu-trigger, .ui-selectonemenu-label');
   
    //activate selected
    if(_this.cfg.editable) {
        var customInputVal = _this.label.val();
       
        //predefined input
        if(customInputVal === selectedOption.text()) {
            _this.highlightItem(_this.items.eq(selectedOption.index()));
        }
        //custom input
        else {
            _this.items.eq(0).addClass('ui-state-highlight');
            _this.customInput = true;
            _this.customInputVal = customInputVal;
        }
    }
    else {
        _this.highlightItem(_this.items.eq(selectedOption.index()));
    }
/*
       */    
    //mark trigger and descandants of trigger as a trigger for a primefaces overlay
    _this.triggers.data('primefaces-overlay-target', true).find('*').data('primefaces-overlay-target', true);


    //Append panel to body
    $(document.body).children(_this.panelId).remove();
    _this.panel.appendTo(document.body);
   
    //pfs metadata
    _this.input.data(PrimeFaces.CLIENT_ID_DATA, _this.id);
   
    _this.renderDeferred();
    
    //if(!_this.disabled) {            
       //_this.bindEvents();
       
       //_this.bindConstantEvents();
       
       //_this.setupDialogSupport();
    //}
}

function formIsEmpty(){
	var isEmpty = true;
	var elements = $('.ui-algo-container').find('[tabindex]');
	elements.each(function() {
        if ($(this).children().length > 0) {
            //findIndex($(this).children());
        }
        if (this.value !== '') {
        	isEmpty = false;
        	return;
        }
	});
	return isEmpty;
}

function generateCSS() {
    var cssStr = "";
    $("link[type='text/css']").each(function() {
        //cssStr += $(this)[0].outerHTML;
        cssStr += $(this).attr('href') + ";";
    });
    return cssStr;
}
function prepareExportHtmlToPdf(contentClass) {
    $(contentClass + ' input').remove();
    $(contentClass + ' table').css("font-size", "10px");
    $(esc('html')).val($(contentClass).html());
    //$('.ui-algo-container input').remove();
    //$(esc('html')).val($('.data-list')[0].outerHTML);
    $(esc('css')).val(generateCSS());
    //'.ui-algo-container .ui-datatable-tablewrapper'
    //alert($(esc('tabView:css')).val());
}

function handleFileUploadDlg(xhr, status, args) {
    var isValid = args.isValid;
    if (isValid) {
        //$('.handleFileDlgImg').attr('src', '');
        uploadFileWv.show();
        updateHandleFileDlg();
    }
}
function sendEmail(email_destin, name_destin, email_msg, email_title) {
    sendEmailRc([{name: 'email_destin', value: email_destin}, {name: 'name_destin', value: name_destin}, {name: 'email_msg', value: email_msg}, {name: 'email_title', value: email_title}]);
}
function randomSort(list_field, amount_field, target_field) {
    randomSortRc([{name: 'list_field', value: list_field}, {name: 'amount_field', value: amount_field}, {name: 'target_field', value: target_field}]);
}   

var tabindex = null;
var firstindex = null;            
function findIndex(elements) {
    elements.each(function() {
        if ($(this).children().length > 0) {
            findIndex($(this).children());
        }
        if (this.type !== "hidden" && this.value === '') {
            if (firstindex === null) {
                firstindex = this;
            }
            if ((tabindex === null && $(this).attr("tabindex") !== undefined) || parseFloat($(this).attr("tabindex")) < parseFloat($(tabindex).attr("tabindex"))) {
                tabindex = this;
                
                window.setTimeout(function(tabindex) {
                	//alert(tabindex);
                    $(tabindex).focus();
                }, 100,tabindex);
            }
        }
        //alert($(tabindex).attr("tabindex"));
    });
}
function init2() {
    var elements = $('.ui-algo-container').find('[tabindex]');
    findIndex(elements);
    if (tabindex === null && firstindex !== null) {
    	window.setTimeout(function(firstindex) {
            $(firstindex).focus();
        }, 100,firstindex);
    }
    //alert($(tabindex).attr("tabindex"));
    if (!loaded) {
        //updateCurrentForm();
        //updateCurrentConteiner();
        //updateBasePanel();
        loaded = true;
    }
}   

function init1() {

    //alert($('.ui-growl').html());
    if (page !== '#{app.containerPage}') {
        page = '#{app.containerPage}';
        window.setTimeout(function() {
            if ($('.ui-growl').html() !== '') {
                //alert($('.ui-growl').html() + '1');
                window.setTimeout(function() {
                    //updateCurrentPage();
                }, 1000);
            } else {
                //updateCurrentPage();
            }
        }, 100);
    }
}

function onStart(pageArg){
	tabindex = null;
	firstindex = null; 	
	if(formIsEmpty()){
	    init2();            	
	}	
	if (page !== pageArg) {            	
	    page = pageArg;
	    clearFormChanged();
	    init2();   
	}
	//alert(page);
	$(document).ready(function() {
		reloadSelectsByJS();         		            			
	});  
	
	window.setTimeout(function() {
	   //reloadSelectsByJS();
	   //alert('oi2');
	}, 100);	
	
}