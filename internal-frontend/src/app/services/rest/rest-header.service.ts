import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export abstract class RestHeaderService {

  protected constructor(
    protected http: HttpClient
  ) { }

  protected getBaseUrl(path: string): string {
    return `http://localhost:8082/api/internal/` + path;
  }

  protected getHeaders(): { headers: HttpHeaders } {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }
}
