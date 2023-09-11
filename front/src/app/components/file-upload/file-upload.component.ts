import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileService } from 'src/app/services/file.service';
import * as converter from 'xml-js';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {
    currentFile?: File;
    progress = 0;
    message = '';
    currentJSON? = {};
  
    fileName = 'Selecione o arquivo';
    fileInfos?: Observable<any>;
  
    constructor(private uploadService: FileService) { }

    ngOnInit(): void {
      
    }
  

    /**
     * Pega a Extensão do aqrquivo
     * @param filename 
     * @returns 
     */
    getFileExtension(filename: string) {
      return (filename != '' && filename != undefined && filename != null) ? filename.split('.').pop() : '';
    }

    /**
     * Seleciona um arquivo
     * @param event 
     */
    selectFile(event: any): void {
      if (event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (this.getFileExtension(file.name) != 'xml') {
          alert("Escolha um arquivo XML");
          return;
        }
        this.currentFile = file;
        this.fileName = this.currentFile.name;
        
        const reader = new FileReader();
        reader.onload = (e: any) => {
          let xml = e.target.result;
          
          let result1 = converter.xml2json(xml, {compact: true, spaces: 4});
          const JSONData = this.limpaPrecoMedio(JSON.parse(result1));
          this.currentJSON = JSONData;
   
          let options = {compact: true, ignoreComment: true, spaces: 4};
          let result = converter.js2xml(JSONData, options);
   
          const newFile = new File([result], "result.xml", {
            type: "text/plain",
          });
          this.currentFile = newFile;

        }
        reader.readAsText(event.target.files[0])
      } else {
        this.fileName = 'Selecione o arquivo';
      }
    }
  
    /**
     * Upload de um arquivo
     */
    upload(): void {
      this.progress = 0;
      this.message = "";
  
      if (this.currentFile) {
        this.uploadService.upload(this.currentFile).subscribe(
        // this.uploadService.sendJson(this.currentJSON).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = 'Agentes cadastrados com sucesso.';
            }
          },
          (err: any) => {
            console.log(err);
            this.progress = 0;
  
            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Erro ao fazer o upload!';
            }
  
            this.currentFile = undefined;
          });
      }
  
    }

    /**
     * Limpa os  preços Medio, para não enviar para a API.
     * @param jsonData 
     * @returns Json
     */
    limpaPrecoMedio(jsonData: any): any {
      jsonData.agentes.agente.forEach(function(obj: IAgente){
        var regiao = obj.regiao;
        regiao.forEach(function(regiao){
          regiao.precoMedio = [{ valor: [] }];
        });
      });
      return jsonData;
    }
}

interface IAgente {
  codigo: string,
  data: string,
  regiao: [{
    _attributes: string,
    compra: [{
      valor: []
    }],
    geracao: [{
      valor: []
    }],
    precoMedio: [{
      valor: []
    }],
  }],
};