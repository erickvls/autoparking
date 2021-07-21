 $(document).ready(function() {


    function setSelectionRange(input, selectionStart, selectionEnd) {
      if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
      } else if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        console.log(collapse);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
      }
    }

    function setCaretToPos(input, pos) {
      setSelectionRange(input, pos, pos);
    }



    $(".mascara").maskMoney({
     decimal: ".",
     thousands: "."
    });

     $(document).on('show.bs.modal', '#confirmarExclusaoModal', function (event){
      var button = $(event.relatedTarget); // Button that triggered the modal
      var codigoServico = button.data('servico'); // Extract info from data-* attributes
      var descricao  = button.data('descricao');
      var modal = $(this);
      var form = modal.find('form');
      var action = form.data('url-base');
      if(!action.endsWith('/')){
    	  action +='/';
      }
      form.attr('action', action);
      $("#servico").attr("value",codigoServico);
      modal.find('.modal-body p').html('Tem certeza que deseja excluir <strong>'+ descricao + '</strong>');
    });

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
                    if(msg.veiculoSelecionado == null && msg.veiculos <1){
                        $("#resultado-error").css("display","");
                        $("#alert-msg-error").html("Usuário não possui veículos associados a conta.");
                        return;
                    }
                    if(msg.userExiste == null){
                        $("#resultado-error").css("display","");
                        $("#alert-msg-error").html("Usuário não encontrado.");
                        return;
                    }
                    if(msg.dataPrevistaEntrada == null){
                    $("#resultado-error").css("display","none");
                    $("#alert-resultado").html("O Usuário não possui reserva. Uma ordem será criada");

                    $.each(msg.veiculos , function(index, org_types) {
                              var content='<option value="' + org_types.id + '">' + org_types.modelo + '</option>';
                              $("#veiculo").append(content);
                    });
                    $("#resultado").css("display","");
                                       $("#corpo-formulario").css("display","");
                                       $("#btn-pesquisar-usuario").css("display","none");
                                       $("#botoes-enviar").css("display","");

                    }else{
                        $("#resultado-error").css("display","none");
                       $("#alert-resultado").html("O Usuário já possui uma ordem em aberto devido a reserva. "+
                       "Utilizaremos a order já criada.");
                       $('#dataPrevistaSaida').removeAttr('required');
                       $("#data").css("display","none");
                       $("#dataPrevistaSaida").val(msg.dataPrevistaSaida);
                       $("#veiculo")
                           .append(
                               $('<option />')
                                   .text(msg.veiculoSelecionado.modelo + " - " +msg.veiculoSelecionado.placa)
                                   .val(msg.veiculoSelecionado.id)
                           );
                           $("#order").val(msg.id);
                    $("#resultado").css("display","");
                   $("#corpo-formulario").css("display","");
                   $("#btn-pesquisar-usuario").css("display","none");
                   $("#botoes-enviar").css("display","");
                   }

            })
            .fail(function(jqXHR, textStatus, msg){
                 alert(msg);
            });
        }else{
            $("#mensagem").css("display","");
        }
    });
});