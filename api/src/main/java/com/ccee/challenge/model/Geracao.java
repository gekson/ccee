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
@Table(name="geracoes")
@Data
@JacksonXmlRootElement(localName = "Geracao")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty(localName = "ValorGeracao")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(mappedBy="geracao", cascade=CascadeType.ALL)
    private List<ValorGeracao> valor= new ArrayList<>();

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="regiao_id")
    private Regiao regiao;
}
