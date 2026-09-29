<?php

header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");

require_once "conexao.php";

try {

    $sql = "SELECT id, nome, cidade, descricao
            FROM faculdades
            ORDER BY nome";

    $stmt = $conn->prepare($sql);
    $stmt->execute();

    $faculdades = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode($faculdades);

} catch (PDOException $e) {

    echo json_encode([
        "sucesso" => false,
        "mensagem" => "Erro ao buscar faculdades"
    ]);
}