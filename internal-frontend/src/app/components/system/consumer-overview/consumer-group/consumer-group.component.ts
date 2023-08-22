import { Component, Inject, OnInit } from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {ConsumerGroupService} from '../../../../services/rest/consumer-group.service';
import {TranslationService} from '../../../../services/translation/translation.service';
import {ActivatedRoute} from '@angular/router';
import {WiringMode} from '../../../../dto/WiringMode';

@Component({
  selector: 'app-consumer-group',
  templateUrl: './consumer-group.component.html',
  styleUrls: ['./consumer-group.component.scss']
})
export class ConsumerGroupComponent implements OnInit {

  constructor(
    private service: ConsumerGroupService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: UntypedFormBuilder,
    private translationService: TranslationService,
    private dialogRef: MatDialogRef<ConsumerGroupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  public loading = true;
  public formGroup: UntypedFormGroup | undefined;

  ngOnInit(): void {
    this.createForms();
    this.loadData();
  }

  public getMode(): string[] {
    return Object.keys(WiringMode).filter(key => typeof WiringMode[key as any] === 'number');
  }

  public save(): void {
    this.loading = true;
    const controls = this.formGroup!!.controls;
    const name = controls['name'].value;
    const mode = controls['wiring'].value;
    let id = this.data?.group?.id;
    if (!id || id?.length === 0) {
      id = '';
    }

    this.service.save(id, name, mode).subscribe(
      () => {
        this.translationService.showSnackbar('Updated');
        this.dialogRef.close();
      },
      () => {
        this.translationService.showSnackbarOnError();
        this.loading = false;
      }
    );
  }

  private loadData(): void {
    if (this.data?.group) {
      this.formGroup!!.setValue({
        name: this.data.group.name,
        wiring: this.data.group.wiring
      });
    }

    this.loading = false;
  }

  private createForms(): void {
    this.formGroup = this.formBuilder.group(
      {
        name: [null, Validators.compose([Validators.required])],
        wiring: [null, Validators.compose([Validators.required])]
      }
    );
  }
}
