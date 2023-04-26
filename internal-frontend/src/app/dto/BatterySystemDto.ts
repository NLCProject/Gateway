import {SystemStatus} from './SystemStatus';
import {Dto} from './Dto';

export class BatterySystemDto extends Dto {
  status: SystemStatus = SystemStatus.ONLINE;
  manufacturer = '';
  serialNumber = '';
}
