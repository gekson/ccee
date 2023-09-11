package com.ccee.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Compra")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompraDTO {

    @JacksonXmlProperty(localName = "valor")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Double> valor= new ArrayList<>();
}
