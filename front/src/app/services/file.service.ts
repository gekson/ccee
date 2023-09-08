import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// import * as converter from 'xml-js';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.api}/importData`, formData, {
      reportProgress: true,
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
