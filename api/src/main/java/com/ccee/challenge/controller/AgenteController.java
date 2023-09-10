package com.ccee.challenge.controller;

import com.ccee.challenge.model.Agente;
import com.ccee.challenge.dto.AgentesDTO;
import com.ccee.challenge.service.AgenteService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService agenteService;
    private final String TEMP_STORAGE = System.getProperty("java.io.tmpdir");

    @RequestMapping(value = "/uploadJson", method = RequestMethod.POST)
    public String uploadAgentes(@RequestBody Agente agente) {
        System.out.println(agente);
        return "Agente salvo " + agente;
    }

    @PostMapping(path = "/fileUpload")
    public void fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            ResourceUtils.getURL("classpath:application.properties");
            File fileToImport = new File(TEMP_STORAGE + originalFileName);
            multipartFile.transferTo(fileToImport);

            XmlMapper xmlMapper = new XmlMapper();
            AgentesDTO agentes = xmlMapper.readValue(fileToImport, AgentesDTO.class);
            agenteService.salvarAgentes(agentes.getAgentes());
            System.out.println(agentes);

            ResponseEntity.ok();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
