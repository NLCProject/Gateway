import {MessageType} from './MessageType';

export class VoltageMeasurement {
  text = '';
  value: number = 0;
  manufacturer = '';
  serialNumber = '';
  type: MessageType = MessageType.VoltageMeasurement;
}
