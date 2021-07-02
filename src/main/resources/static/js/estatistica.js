 $(document).ready(function() {
        $.ajax({
             url : "/admin/estatisticas/saldo",
             type : 'POST',
             data : {
                       data : "30_DIAS"
              },
             beforeSend : function(){

             }
        })
        .done(function(msg){

            alert(msg);
        })
        .fail(function(jqXHR, textStatus, msg){
             console.log(msg);
             alert(msg);

        });
});