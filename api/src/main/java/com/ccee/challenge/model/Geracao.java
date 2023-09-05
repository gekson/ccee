package com.ccee.challenge.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Geracao")
public class Geracao {
    @JacksonXmlProperty(localName = "valor")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Double> valor= new ArrayList<>();
}
