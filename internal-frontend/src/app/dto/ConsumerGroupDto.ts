import {Dto} from './Dto';
import {WiringMode} from "./WiringMode";
import {ConsumerMode} from "./ConsumerMode";
import {BatterySystemDto} from "./BatterySystemDto";

export class ConsumerGroupDto extends Dto {
  name: string = '';
  voltage = 0;
  standard = false;
  mode: ConsumerMode = ConsumerMode.None;
  wiring: WiringMode = WiringMode.Unknown;
  systems: BatterySystemDto[] = [];
}
