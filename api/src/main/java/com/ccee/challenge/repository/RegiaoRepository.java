package com.ccee.challenge.repository;

import com.ccee.challenge.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    Regiao findRegiaoBySigla(String sigla);
}
