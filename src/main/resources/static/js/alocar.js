 $(document).ready(function() {

    $("#btn-pesquisar-usuario").click(function(e){
        var emailUsuario = $("#email").val();
        var estacionamento = $("#estacionamento").val();
        if(emailUsuario != ''){
            $.ajax({
                 url : "/admin/pesquisar/usuario",
                 type : 'POST',
                 data : {
                           email : emailUsuario,
                           estacionamento : estacionamento
                      },
                 beforeSend : function(){

                 }
            })
            .done(function(msg){
                    if(msg.dataPrevistaEntrada == null){
                    $("#alert-resultado").html("O Usuário não possui reserva. Uma ordem será criada");

                    $.each(msg.veiculos , function(index, org_types) {
                              var content='<option value="' + org_types.id + '">' + org_types.modelo + '</option>';
                              $("#veiculo").append(content);
                    });


                    }else{
                       $("#alert-resultado").html("O Usuário já possui uma ordem em aberto devido a reserva. "+
                       "Utilizaremos a order já criada.");
                       $("#veiculo")
                           .append(
                               $('<option />')
                                   .text(msg.veiculoSelecionado.modelo + " - " +msg.veiculoSelecionado.placa)
                                   .val(msg.veiculoSelecionado.id)
                           );
                           $("#order").val(msg.id);

                   }
                   $("#resultado").css("display","");
                   $("#corpo-formulario").css("display","");
                   $("#btn-pesquisar-usuario").css("display","none");
                   $("#botoes-enviar").css("display","");
            })
            .fail(function(jqXHR, textStatus, msg){
                 alert(msg);
            });
        }else{
            $("#mensagem").css("display","");
        }
    });
});