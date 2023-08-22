import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {RestHeaderService} from './rest-header.service';
import {BatterySystemDto} from '../../dto/BatterySystemDto';

@Injectable({
  providedIn: 'root'
})
export class SystemService extends RestHeaderService {
  path = 'system';

  public findAll(): Observable<BatterySystemDto[]> {
    const url = `${this.getBaseUrl(this.path)}/findAll`;
    return this.http.get<BatterySystemDto[]>(url, this.getHeaders());
  }
}
