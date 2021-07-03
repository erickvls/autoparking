 $(document).ready(function() {
    var url = '/admin/estatisticas/saldo';
    requisitarAjax(url,'POST','30_DIAS');

    $('.mudarValor').click(function() {
       var dias = this.id;
      requisitarAjax(url,'POST',dias);
    });
});

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