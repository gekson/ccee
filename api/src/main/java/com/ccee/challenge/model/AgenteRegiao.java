package com.ccee.challenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="agentes_regiao")
@Data
public class AgenteRegiao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agente_id")
    Agente agente;

    @ManyToOne
    @JoinColumn(name = "regiao_id")
    Regiao regiao;

    @OneToMany(mappedBy="agenteRegiao", cascade=CascadeType.ALL)
    private List<ValorGeracao> valor= new ArrayList<>();
}
