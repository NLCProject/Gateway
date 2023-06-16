import {MessageType} from './MessageType';
import {SystemStatus} from './SystemStatus';

export class SystemStatusChanged {
  text = '';
  manufacturer = '';
  serialNumber = '';
  status: SystemStatus = SystemStatus.ONLINE;
  type: MessageType = MessageType.VoltageMeasurement;
}
