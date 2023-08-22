import {SystemStatus} from './SystemStatus';
import {Dto} from './Dto';
import {ConsumerGroupDto} from "./ConsumerGroupDto";

export class BatterySystemDto extends Dto {
  value = '';
  manufacturer = '';
  serialNumber = '';
  group: ConsumerGroupDto | null = null;
  status: SystemStatus = SystemStatus.ONLINE;
}
