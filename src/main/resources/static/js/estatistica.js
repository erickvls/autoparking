

function requisitarAjax(url,metodo,data){
        $.ajax({
             url : url,
             type : metodo,
             data : {
                       data : data
              },
             beforeSend : function(){

             }
        })
        .done(function(msg){
            $("#valor").html(msg);

        })
        .fail(function(jqXHR, textStatus, msg){
             alert("Falha na requisição");
        });
}

