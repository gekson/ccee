package com.ccee.challenge.service;

import com.ccee.challenge.model.Agente;
import com.ccee.challenge.model.Regiao;
import com.ccee.challenge.repository.AgenteRepository;
import com.ccee.challenge.repository.RegiaoRepository;
import lombok.RequiredArgsConstructor;
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
    public Boolean salvarAgentes(List<Agente> agentes) {
        try {
            List<Regiao> regioes = regiaoRepository.findAll();
            Map<String, Regiao> map = regioes.stream()
                  .collect(Collectors.toMap(Regiao::getSigla, Function.identity()));

            for (Agente a : agentes) {
                a.getRegiao().stream().forEach(regiao -> regiao.setId(
                        map.get(regiao.getSigla()).getId()));
                agenteRepository.save(a);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao salvar o registro", e);
        }
    }
}
