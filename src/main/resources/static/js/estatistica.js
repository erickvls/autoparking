

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
             alert("Falha na requisição Saldo");
        });
}



function requisitarAjaxUser(url,metodo,valor){
        $.ajax({
             url : url,
             type : metodo,
             data : {
                       ano : valor[0],
                       mes: valor[1]

              },
             beforeSend : function(){

             }
        })
        .done(function(msg){
            $("#qtd-usuario").html(msg);
        })
        .fail(function(jqXHR, textStatus, msg){
             alert("Falha na requisição UsuarioMEs");
        });
}

 $(document).ready(function() {
    var url = '/admin/estatisticas/saldo';
    requisitarAjax(url,'POST','30_DIAS');

    $('.mudarValor').click(function() {
       var dias = this.id;
      requisitarAjax(url,'POST',dias);
    });

    var urlUsuarios = '/admin/estatisticas/usuarios'
    let mes = document.getElementById("mes-usuario").value
    mes = mes.split('-');
    requisitarAjaxUser(urlUsuarios,'POST',mes);


    $( "#mes-usuario").change(function() {
      var mes = document.getElementById("mes-usuario").value
      mes = mes.split('-');
      requisitarAjaxUser(urlUsuarios,'POST',mes);
    });
});