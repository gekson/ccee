package com.ccee.challenge.repository;

import com.ccee.challenge.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    Regiao findRegiaoBySigla(String sigla);

    @Query(
            value = "SELECT a.codigo, a.data, r.sigla, vg.valor  FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join AGENTES  a on a.id = ar.agente_id\n" +
                    "inner join VALORES_GERACAO vg on vg.agente_regiao_id = ar.id\n" +
                    "where r.sigla = ?1",
            nativeQuery = true)
    List<Map<String,Object>> findDadosPorRegiao(String sigla);

    @Query(
            value = "SELECT  r.sigla, sum(vg.valor) as ValorConsolidado  FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join AGENTES  a on a.id = ar.agente_id\n" +
                    "inner join VALORES_GERACAO vg on vg.agente_regiao_id = ar.id\n" +
                    "GROUP BY r.sigla",
            nativeQuery = true)
    List<Map<String,Object>> findConsolidadoValorGeracao();

    @Query(
            value = "SELECT  r.sigla, sum(vc.valor) as ValorConsolidado  FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join AGENTES  a on a.id = ar.agente_id\n" +
                    "inner join VALORES_COMPRA vc on vc.agente_regiao_id = ar.id\n" +
                    "GROUP BY r.sigla",
            nativeQuery = true)
    List<Map<String,Object>> findConsolidadoValorCompra();

    @Query(
            value = "SELECT  sigla, sum (ConsolidadoValores) as Total  from (\n" +
                    "SELECT   sigla, sum(vg.valor) as ConsolidadoValores FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join AGENTES  a on a.id = ar.agente_id\n" +
                    "inner join VALORES_GERACAO vg on vg.agente_regiao_id = ar.id\n" +
                    "GROUP BY r.sigla\n" +
                    "UNION\n" +
                    "SELECT sigla, sum(vc.valor) as ConsolidadoValores   FROM REGIOES r\n" +
                    "inner join AGENTES_REGIAO ar on r.id = ar.regiao_id\n" +
                    "inner join AGENTES  a on a.id = ar.agente_id\n" +
                    "inner join VALORES_COMPRA vc on vc.agente_regiao_id =ar.id\n" +
                    "GROUP BY r.sigla) virtual\n" +
                    "GROUP BY sigla",
            nativeQuery = true)
    List<Map<String,Object>> findConsolidadoValorTotal();
}
