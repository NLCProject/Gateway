import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {RestHeaderService} from './rest-header.service';
import {ConsumerGroupDto} from '../../dto/ConsumerGroupDto';
import {WiringMode} from '../../dto/WiringMode';

@Injectable({
  providedIn: 'root'
})
export class ConsumerGroupService extends RestHeaderService {
  path = 'consumer';

  public findAll(): Observable<ConsumerGroupDto[]> {
    const url = `${this.getBaseUrl(this.path)}/findAll`;
    return this.http.get<ConsumerGroupDto[]>(url, this.getHeaders());
  }

  public save(groupId: string, name: string, mode: WiringMode): Observable<void> {
    const url = `${this.getBaseUrl(this.path)}/save?groupId=${groupId}&mode=${mode}&name=${name}`;
    return this.http.post<void>(url, null, this.getHeaders());
  }
}
