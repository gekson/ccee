import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// import * as converter from 'xml-js';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  /**
   * Envia um arquivo XML para a API
   * @param file 
   * @returns 
   */
  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.api}/fileUpload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
    
  }

  sendJson(jsonData: any): Observable<HttpEvent<any>> {
    console.log('SERVICO', jsonData);
    const headers = { 'Content-Type': 'application/json' };

    const req = new HttpRequest('POST', `${this.api}/uploadJson`, jsonData, {
      reportProgress: true,
      headers: new HttpHeaders(headers),
      responseType: 'json'
    });

    return this.http.request(req);
    
  }

  // proccessFile(file: File) {
  //   let xml = file;
  //   let result1 = converter.xml2json(xml, {compact: true, spaces: 4});

  //   const JSONData = JSON.parse(result1);
    
  //   return file;
  // }
}
