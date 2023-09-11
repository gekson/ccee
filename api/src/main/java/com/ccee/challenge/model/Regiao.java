package com.ccee.challenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="regioes")
@Data
public class Regiao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String sigla;

    @OneToMany(mappedBy = "regiao",cascade=CascadeType.MERGE)
    private List<AgenteRegiao> agentesRegiao;
}
