/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function inputUpperCase() {
    $(".ui-inputfield:not(.ui-password, .ui-inputmask, .no-upper)").blur(function() {
        this.value = this.value.toUpperCase();
    });
    $(".ui-inputfield:not(.ui-password, .ui-inputmask, .no-upper)").keyup(function(e) {
        if (e.which >= 97 && e.which <= 122) {
            var newKey = e.which - 32;
            // I have tried setting those
            e.keyCode = newKey;
            e.charCode = newKey;
        }
        //this.value = this.value.toUpperCase()
    });
}
function applyMaskMoney() {
    $(".maskMoney").maskMoney({
        thousands: '.',
        decimal: ',',
        precision: 2
    });
}
function esc(myid) {
    return PrimeFaces.escapeClientId(myid);
}
function exibirAlert(msg,title){
    $(function() {  
        $("#div_msg").html(msg);
        $("#div_msg").dialog({
            resizable: false,
            autoOpen: false,
            height: 150,
            width: 350,
            modal: true,
            title: title,
            zIndex: 10000,
            dialogClass: "no-close",
            buttons: {
                "OK": function() {
                    $( this ).dialog( "close" );
                }
            }
        }); 
        $("#div_msg").dialog('open');
    });        
}
    
function showConfirm(msg,title,callback,callbackFalse){       
    $(function() {  
        $("#div_msg").html(msg);
        $("#div_msg").dialog({
            resizable: false,
            autoOpen: false,
            height: 150,
            width: 350,
            modal: true,
            title: title,
            zIndex: 10000,
            dialogClass: "no-close",
            buttons: {
                "Confirmar": function() {
                    $( this ).dialog( "close" );
                    callback(true);
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                    if(callbackFalse !== undefined && callbackFalse == true){
                        callback(false);                        
                    }
                }
            }
        }); 
        $("#div_msg").dialog('open');
    });                
}

