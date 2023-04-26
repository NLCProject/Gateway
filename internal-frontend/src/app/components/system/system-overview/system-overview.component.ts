import { Component, OnInit } from '@angular/core';
import {SystemService} from '../../../services/rest/system.service';
import {BatterySystemDto} from '../../../dto/BatterySystemDto';
import {TranslationService} from '../../../services/translation/translation.service';
import { SystemStatus } from 'src/app/dto/SystemStatus';

@Component({
  selector: 'app-system-overview',
  templateUrl: './system-overview.component.html',
  styleUrls: ['./system-overview.component.scss']
})
export class SystemOverviewComponent implements OnInit {

  constructor(
    private systemService: SystemService,
    private translationService: TranslationService
  ) { }

  public systems: BatterySystemDto[] = [];
  public SystemStatus = SystemStatus;
  public loading = true;

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.systemService.findAll().subscribe(
      response => {
        this.systems = response;
        this.loading = false;
      },
      error => {
        this.translationService.showSnackbarOnError();
        this.loading = false;
      }
    );
  }
}
