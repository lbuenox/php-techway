<?php

header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");

require_once "conexao.php";

try {

    $sql = "SELECT 
                c.id,
                c.nome,
                c.descricao,
                c.faculdade_id,
                f.nome AS faculdade
            FROM cursos c
            INNER JOIN faculdades f
                ON c.faculdade_id = f.id
            ORDER BY c.nome";

    $stmt = $conn->prepare($sql);
    $stmt->execute();

    $cursos = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode($cursos);

} catch (PDOException $e) {

    echo json_encode([
        "sucesso" => false,
        "mensagem" => "Erro ao buscar cursos"
    ]);
}