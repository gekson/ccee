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
  
    fileName = 'Selecione o arquivo';
    fileInfos?: Observable<any>;
  
    constructor(private uploadService: FileService) { }

    ngOnInit(): void {
      //this.fileInfos = this.uploadService.getFiles();
    }
  
    

    selectFile(event: any): void {
      if (event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        this.currentFile = file;
        this.fileName = this.currentFile.name;
        
        const reader = new FileReader();
        reader.onload = (e: any) => {
          let xml = e.target.result;
          
          let result1 = converter.xml2json(xml, {compact: true, spaces: 4});
          // var options = {compact: true, elementNameFn: function(val: string) {return val.replace('precoMedio:','CASA')}};
          // let result1 = converter.xml2json(xml, options);
          const JSONData = JSON.parse(result1);
          // const jsonAsArray = Array.from(JSONData);
          // console.log(jsonAsArray);
          // console.log(result1);
          // console.log('JSON',JSONData);
          // //delete JSONData[JSONData.agentes.agente.regiao.precoMedio];
          // console.log(this.findPath(JSONData.agentes.agente, 'precoMedio'));
          // console.log(JSONData.agentes.agente);


        //   for(const index in JSONData) {
        //     alert(JSON.stringify(JSONData[index]));
        // }

        //** Apaga precoMedio */
        console.log(JSONData.agentes)
          JSONData.agentes.agente.forEach(function(obj: teste){
            var regiao = obj.regiao;
            regiao.forEach(function(regiao){
              regiao.precoMedio = [{ valor: [] }];
            });
          });
          console.log(JSONData.agentes)
        
          console.log(JSONData)
          // this.jsonToObj(JSONData);



          // const map = new Map(Object.entries(JSON.parse(result1)));
          // console.log(this.findPath(map, 'precoMedio'));

        }
        reader.readAsText(event.target.files[0])
      } else {
        this.fileName = 'Selecione o arquivo';
      }
    }
  
    upload(): void {
      this.progress = 0;
      this.message = "";
  
      if (this.currentFile) {
        this.uploadService.upload(this.currentFile).subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              //this.fileInfos = this.uploadService.getFiles();
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

    findPath(array: Array<string>, val: any): any {
      // console.log(Array.isArray(array))
      //console.log(array.agentes)
      // let myMap = new Map(Object.entries(array));
      // console.log(myMap)
      // const findKey = Object.keys(myMap).find(key => key.includes('agentes'));
      //   console.log('key',findKey);

      // console.log(array.typeof);
      // const clearArray = array.map((obj: teste) => {
      //   console.log('obj', obj);
        
      //   // Find 'consultas' key in the object
      //   const findKey = Object.keys(obj).find(key => key.includes('precoMedio'));
      //   console.log('key',findKey);
      //   if (findKey) {
      //     // Delete key from the object with array
      //     //delete obj[findKey];
      //   }
      //   return obj; // Returning object to array without 'consultas'
      // });
      // return clearArray;

// console.log(array.entries);
//       return array.entries

      // console.log(array);
      // array.agentes.forEach(function(agentes: { subBrands: any[]; }) {
      //   agentes.subBrands = agentes.subBrands.filter(function(subBrand){
      //     console.log(subBrand)
      //     return subBrand.id != 31;
      // })  
    // });
    
    }

    jsonToObj(json: any) {
      json.forEach(function(obj: teste){
        var regiao = obj.regiao;
        regiao.forEach(function(regiao){
          console.log(regiao.precoMedio);
        });
      });
    }
}

  

interface teste {
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