function openModal(order){
    var array = order.split(",");
    var dataPrevistaEntrada = array[2];
    var idOrder = array[0].split("=")[1];
    $("#order").val(idOrder);
     if(dataPrevistaEntrada != null){
        $("#mensagem").css("display","");
        $("#corpo-formulario").css("display","");
        $("#mensagem-alert").html("Essa order teve reserva pelo usuário. Com isso aplicaremos a taxa de reserva do estacionamento.");
    }
    $('#modalFatura').modal('show');
}

