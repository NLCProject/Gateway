import {Component, OnInit} from '@angular/core';
import {SystemService} from '../../../services/rest/system.service';
import {BatterySystemDto} from '../../../dto/BatterySystemDto';
import {TranslationService} from '../../../services/translation/translation.service';
import {SystemStatus} from 'src/app/dto/SystemStatus';
import {WebsocketService} from '../../../services/websocket/websocket.service';
import {MessageType} from "../../../dto/MessageType";
import {SystemValue} from "../../../dto/SystemValue";

@Component({
  selector: 'app-system-overview',
  templateUrl: './system-overview.component.html',
  styleUrls: ['./system-overview.component.scss']
})
export class SystemOverviewComponent implements OnInit {

  constructor(
    private systemService: SystemService,
    private translationService: TranslationService,
    private websocketService: WebsocketService
  ) {
    this.handleWebsocketMessage();
  }

  public values: SystemValue[] = [];
  public systems: BatterySystemDto[] = [];
  public SystemStatus = SystemStatus;
  public loading = true;

  ngOnInit(): void {
    this.loadData();
  }

  public getDataBySystem(system: BatterySystemDto): string {
    const value = this.values.find(value => value.serialNumber === system.serialNumber && value.manufacturer === system.manufacturer)?.data;
    if (value) {
      return value;
    }

    return '0.0';
  }

  private loadData(): void {
    this.systemService.findAll().subscribe(
      response => {
        this.systems = response;
        this.loading = false;
      },
      () => {
        this.translationService.showSnackbarOnError();
        this.loading = false;
      }
    );
  }

  private handleWebsocketMessage(): void {
    this.websocketService.messages.subscribe(message => {
      if (message.type.toString() === MessageType[MessageType.SystemDetected]) {
        this.translationService.showSnackbar('System erkannt');
      }

      if (message.type.toString() === MessageType[MessageType.SystemRegistered]) {
        this.loadData();
      }

      if (message.type.toString() === MessageType[MessageType.SystemStatusChanged]) {
        this.systems.forEach(system => {
          if (system.serialNumber === message.serialNumber && system.manufacturer === message.manufacturer) {
            // @ts-ignore
            system.status = SystemStatus[message.data]
          }
        });
      }

      if (message.type.toString() === MessageType[MessageType.VoltageMeasurement]) {
        let valueFound = false;
        this.values.forEach(value => {
          if (value.serialNumber === message.serialNumber && value.manufacturer === message.manufacturer) {
            value.data = message.data;
            valueFound = true;
          }
        });

        if (!valueFound) {
          const value = new SystemValue();
          value.data = message.data;
          value.manufacturer = message.manufacturer;
          value.serialNumber = message.serialNumber;
          this.values.push(value);
        }
      }
    });
  }
}
