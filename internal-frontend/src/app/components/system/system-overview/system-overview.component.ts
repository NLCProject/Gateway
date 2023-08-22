import {Component, OnInit} from '@angular/core';
import {SystemService} from '../../../services/rest/system.service';
import {BatterySystemDto} from '../../../dto/BatterySystemDto';
import {TranslationService} from '../../../services/translation/translation.service';
import {SystemStatus} from 'src/app/dto/SystemStatus';
import {WebsocketService} from '../../../services/websocket/websocket.service';
import {MessageType} from '../../../dto/MessageType';
import {SystemValue} from '../../../dto/SystemValue';
import {VoltageMeasurement} from '../../../dto/VoltageMeasurement';
import {SystemStatusChanged} from "../../../dto/SystemStatusChanged";
import { ConsumerMode } from 'src/app/dto/ConsumerMode';

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

  public loading = true;
  public values: SystemValue[] = [];
  public SystemStatus = SystemStatus;
  public ConsumerMode = ConsumerMode;
  public systems: BatterySystemDto[] = [];

  ngOnInit(): void {
    this.loadData();
  }

  public getDataBySystem(system: BatterySystemDto): string {
    const value = this.values.find(value => {
      return value.serialNumber === system.serialNumber && value.manufacturer === system.manufacturer
    })?.data;

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
        const dto = message as unknown as SystemStatusChanged;
        this.systems.forEach(system => {
          if (system.serialNumber === dto?.serialNumber && system.manufacturer === dto?.manufacturer) {
            // @ts-ignore
            system.status = SystemStatus[dto.status]
          }
        });
      }

      if (message.type.toString() === MessageType[MessageType.VoltageMeasurement]) {
        const dto = message as unknown as VoltageMeasurement;
        let valueFound = false;
        this.values.forEach(value => {
          if (value.serialNumber === dto?.serialNumber && value.manufacturer === dto?.manufacturer) {
            value.data = dto?.value?.toString();
            valueFound = true;
          }
        });

        if (!valueFound && dto) {
          const value = new SystemValue();
          value.data = dto?.value?.toString();
          value.manufacturer = dto?.manufacturer;
          value.serialNumber = dto?.serialNumber;
          this.values.push(value);
        }
      }
    });
  }
}
