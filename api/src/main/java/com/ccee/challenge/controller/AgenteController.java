package com.ccee.challenge.controller;

import com.ccee.challenge.model.Agente;
import com.ccee.challenge.dto.AgentesDTO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class AgenteController {

    private final String TEMP_STORAGE = System.getProperty("java.io.tmpdir");

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadAgente(@RequestBody Agente agente) {
        System.out.println(agente);
        return "Agente salvo " + agente;
    }

    @PostMapping(path = "/importData")
    public void startBatch(@RequestParam("file") MultipartFile multipartFile) {


        // file  -> path we don't know
        //copy the file to some storage in your VM : get the file path
        //copy the file to DB : get the file path

        try {
            String originalFileName = multipartFile.getOriginalFilename();
            ResourceUtils.getURL("classpath:application.properties");
            File fileToImport = new File(TEMP_STORAGE + originalFileName);
            multipartFile.transferTo(fileToImport);

            XmlMapper xmlMapper = new XmlMapper();
            AgentesDTO agentes = xmlMapper.readValue(fileToImport, AgentesDTO.class);
//            Agente agentes = xmlMapper.readValue(fileToImport, Agente.class);
            System.out.println(agentes);

//            Agente a = new Agente("2","3");
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("fullPathFileName", TEMP_STORAGE + originalFileName)
//                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
//
//            JobExecution execution = jobLauncher.run(job, jobParameters);

//            if(execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)){
//                //delete the file from the TEMP_STORAGE
//                Files.deleteIfExists(Paths.get(TEMP_STORAGE + originalFileName));
//            }

//        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | IOException e) {
//
//            e.printStackTrace();
//        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
