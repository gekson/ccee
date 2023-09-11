package com.ccee.challenge.service;

import com.ccee.challenge.dto.AgenteDTO;
import com.ccee.challenge.model.Agente;
import com.ccee.challenge.model.Geracao;
import com.ccee.challenge.model.Regiao;
import com.ccee.challenge.model.ValorGeracao;
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

//                a.getRegiao().stream().forEach(regiao -> {
//                    agente.getRegiao().add(new Regiao());
//                    BeanUtils.copyProperties(regiao, agente.getRegiao().add(new Regiao()));
////                    regiao.setId(
////                        map.get(regiao.getSigla()).getId());
//                });

                for (int i = 0; i < a.getRegiao().size(); i++) {
                    agente.getRegiao().add(new Regiao());
                    BeanUtils.copyProperties(a.getRegiao().get(i), agente.getRegiao().get(i));

                    for (int j = 0; j < a.getRegiao().get(i).getGeracao().size(); j++) {
                        agente.getRegiao().get(i).getGeracao().add(new Geracao());
                        BeanUtils.copyProperties(a.getRegiao().get(i).getGeracao().get(j),
                                agente.getRegiao().get(i).getGeracao().get(j));
                        agente.getRegiao().get(i).getGeracao().get(j).setRegiao(
                                agente.getRegiao().get(i));

                        for (int k = 0; k < a.getRegiao().get(i).getGeracao().get(j).getValor().size(); k++) {
                            agente.getRegiao().get(i).getGeracao().get(j).getValor().add(
                                    new ValorGeracao(a.getRegiao().get(i).getGeracao().get(j).getValor().get(k)));
                            agente.getRegiao().get(i).getGeracao().get(j).getValor().get(k).setGeracao(
                                    agente.getRegiao().get(i).getGeracao().get(j));
                        }
                    }
                }

                agente.getRegiao().stream().forEach(regiao -> {
                    regiao.setId(
                            map.get(regiao.getSigla()).getId());
                    regiaoRepository.save(regiao);
                });
                agenteRepository.save(agente);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar o registro", e);
        }
    }

    public List findDadosPorRegiao(String sigla) {
        return regiaoRepository.findDadosPorRegiao(sigla);
    }
}
