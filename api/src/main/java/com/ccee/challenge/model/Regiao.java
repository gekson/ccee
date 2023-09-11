package com.ccee.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="regioes")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Regiao")
public class Regiao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty
    @Column(unique=true)
    private String sigla;

    @JacksonXmlProperty(localName = "geracao")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(mappedBy="regiao", cascade = CascadeType.ALL)
    private List<Geracao> geracao = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.MERGE)
    private List<Agente> agentes;
}
