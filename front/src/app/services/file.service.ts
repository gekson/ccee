import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpinnerService } from './spinner.service';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private api = 'http://localhost:8080';

  constructor(public spinnerService: SpinnerService, private http: HttpClient) { }

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
      responseType: 'json',
    });

    return this.http.request(req);
    
  }

  sendJson(jsonData: any): Observable<HttpEvent<any>> {
    // console.log('SERVICO', jsonData);
    const headers = { 'Content-Type': 'application/json' };

    const req = new HttpRequest('POST', `${this.api}/uploadJson`, jsonData, {
      reportProgress: true,
      headers: new HttpHeaders(headers),
      responseType: 'json'
    });

    return this.http.request(req);
    
  }
}
