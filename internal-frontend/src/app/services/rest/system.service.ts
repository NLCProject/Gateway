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

  public moveToGroup(systemId: string, groupId: string): Observable<void> {
    const url = `${this.getBaseUrl(this.path)}/moveToGroup?systemId=${systemId}&groupId=${groupId}`;
    return this.http.post<void>(url, null, this.getHeaders());
  }
}
