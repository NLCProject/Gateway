import {MessageType} from "./MessageType";

export class WebsocketMessage {
  type: MessageType = MessageType.VoltageMeasurement;
  data = '';
  manufacturer = '';
  serialNumber = '';
}
