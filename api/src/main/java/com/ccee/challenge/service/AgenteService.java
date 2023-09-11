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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

                for (int i = 0; i < a.getRegiao().size(); i++) {
                    agente.getAgentesRegiao().add(new AgenteRegiao());
                    agente.getAgentesRegiao().get(i).setRegiao(map.get(a.getRegiao().get(i).getSigla()));
                    agente.getAgentesRegiao().get(i).setAgente(agente);

                    for (int j = 0; j < a.getRegiao().get(i).getGeracao().size(); j++) {
                        for (int k = 0; k < a.getRegiao().get(i).getGeracao().get(j).getValor().size(); k++) {
                            agente.getAgentesRegiao().get(i).getValor().add(
                                    new ValorGeracao(a.getRegiao().get(i).getGeracao().get(j).getValor().get(j)));
                            agente.getAgentesRegiao().get(i).getValor().get(k).setAgenteRegiao(agente.getAgentesRegiao().get(i));
                        }
                    }
                }

//                agente.getRegiao().stream().forEach(regiao -> {
//                    regiao.setId(
//                            map.get(regiao.getSigla()).getId());
//                    regiaoRepository.save(regiao);
//                });
                agenteRepository.save(agente);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar o registro", e);
        }
    }

    public List<Map<String,Object>> findDadosPorRegiao(String sigla) {
        return regiaoRepository.findDadosPorRegiao(sigla);
    }

    public List<Map<String,Object>> findConsolidadoRegiao() {
        return regiaoRepository.findConsolidadoRegiao();
    }
}
