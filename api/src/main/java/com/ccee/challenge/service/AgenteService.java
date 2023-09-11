package com.ccee.challenge.service;

import com.ccee.challenge.dto.AgenteDTO;
import com.ccee.challenge.model.*;
import com.ccee.challenge.repository.AgenteRepository;
import com.ccee.challenge.repository.RegiaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgenteService {

    private final AgenteRepository agenteRepository;
    private final RegiaoRepository regiaoRepository;

    @Transactional
    public Boolean salvarAgentes(List<AgenteDTO> agentes) {
        try {
            List<Regiao> regioes = regiaoRepository.findAll();
            Map<String, Regiao> map = regioes.stream()
                  .collect(Collectors.toMap(Regiao::getSigla, Function.identity()));

            for (AgenteDTO a : agentes) {
                Agente agente = new Agente();
                BeanUtils.copyProperties(a, agente);

                populaRegiao(map, a, agente);

                agenteRepository.save(agente);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar o registro", e);
        }
    }

    private static void populaRegiao(Map<String, Regiao> map, AgenteDTO a, Agente agente) {
        for (int i = 0; i < a.getRegiao().size(); i++) {
            agente.getAgentesRegiao().add(new AgenteRegiao());
            agente.getAgentesRegiao().get(i).setRegiao(map.get(a.getRegiao().get(i).getSigla()));
            agente.getAgentesRegiao().get(i).setAgente(agente);

            populaValorGeracao(a, agente, i);
            populaValorCompra(a, agente, i);
        }
    }

    private static void populaValorGeracao(AgenteDTO a, Agente agente, int i) {
        for (int j = 0; j < a.getRegiao().get(i).getGeracao().size(); j++) {
            for (int k = 0; k < a.getRegiao().get(i).getGeracao().get(j).getValor().size(); k++) {
                agente.getAgentesRegiao().get(i).getValor().add(
                        new ValorGeracao(a.getRegiao().get(i).getGeracao().get(j).getValor().get(j)));
                agente.getAgentesRegiao().get(i).getValor().get(k).setAgenteRegiao(agente.getAgentesRegiao().get(i));
            }
        }
    }

    private static void populaValorCompra(AgenteDTO a, Agente agente, int i) {
        for (int j = 0; j < a.getRegiao().get(i).getCompra().size(); j++) {
            for (int k = 0; k < a.getRegiao().get(i).getCompra().get(j).getValor().size(); k++) {
                agente.getAgentesRegiao().get(i).getValorCompra().add(
                        new ValorCompra(a.getRegiao().get(i).getCompra().get(j).getValor().get(j)));
                agente.getAgentesRegiao().get(i).getValorCompra().get(k).setAgenteRegiao(agente.getAgentesRegiao().get(i));
            }
        }
    }

    public List<Map<String,Object>> findDadosPorRegiao(String sigla) {
        return regiaoRepository.findDadosPorRegiao(sigla);
    }

    public List<Map<String,Object>> findConsolidadoValorGeracao() {
        return regiaoRepository.findConsolidadoValorGeracao();
    }

    public List<Map<String,Object>> findConsolidadoValorCompra() {
        return regiaoRepository.findConsolidadoValorCompra();
    }

    public List<Map<String,Object>> findConsolidadoValorTotal() {
        return regiaoRepository.findConsolidadoValorTotal();
    }
}
