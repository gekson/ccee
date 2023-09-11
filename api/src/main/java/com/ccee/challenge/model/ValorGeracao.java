package com.ccee.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="valores_geracao")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ValorGeracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double valor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="agente_regiao_id")
    private AgenteRegiao agenteRegiao;
}
