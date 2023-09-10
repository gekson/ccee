package com.ccee.challenge.repository;

import com.ccee.challenge.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    Regiao findRegiaoBySigla(String sigla);

    @Query(
            value = "SELECT a.codigo as codigo, a.data as data, r.sigla as sigla, vg.valor as valor FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join agentes a on a.id = ar.agente_id\n" +
                    "inner join GERACOES g on g.regiao_id = ar.regiao_id\n" +
                    "inner join VALORES_GERACAO vg on vg.geracao_id = g.id\n" +
                    "Where r.sigla = ?1 ",
            nativeQuery = true)
    List<Object> findDadosPorRegiao(String sigla);
}
