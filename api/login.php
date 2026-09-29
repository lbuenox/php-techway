<?php

header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type");

$dados = json_decode(
    file_get_contents("php://input"),
    true
);

$email = $dados["email"] ?? "";
$senha = $dados["senha"] ?? "";

if ($email == "" || $senha == "") {

    echo json_encode([
        "sucesso" => false,
        "mensagem" => "Preencha todos os campos"
    ]);

    exit;
}

// TESTE TEMPORÁRIO — ainda sem banco
if ($email == "teste@email.com" && $senha == "123456") {

    echo json_encode([
        "sucesso" => true,
        "mensagem" => "Login realizado com sucesso",
        "token" => "token_teste_123",
        "nome" => "Usuário Teste",
        "id" => 1
    ]);

} else {

    echo json_encode([
        "sucesso" => false,
        "mensagem" => "E-mail ou senha incorretos"
    ]);
}