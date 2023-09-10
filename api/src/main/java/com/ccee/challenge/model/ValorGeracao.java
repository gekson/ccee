package com.ccee.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="valores_geracao")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ValorGeracao")
public class ValorGeracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty
    private Double valor;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="geracao_id")
    private Geracao geracao;
}
