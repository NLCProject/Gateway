import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-progress-bar-dialog',
  templateUrl: './progress-bar-dialog.component.html',
  styleUrls: ['./progress-bar-dialog.component.scss']
})
export class ProgressBarDialogComponent {

  @Input()
  public loading = false;
}
