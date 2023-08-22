import {Component, Inject, OnInit} from '@angular/core';
import {ConsumerGroupService} from "../../../../services/rest/consumer-group.service";
import {ActivatedRoute} from "@angular/router";
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {TranslationService} from "../../../../services/translation/translation.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ConsumerGroupDto} from "../../../../dto/ConsumerGroupDto";
import {SystemService} from "../../../../services/rest/system.service";

@Component({
  selector: 'app-group-selection',
  templateUrl: './group-selection.component.html',
  styleUrls: ['./group-selection.component.scss']
})
export class GroupSelectionComponent implements OnInit {

  constructor(
    private systemService: SystemService,
    private service: ConsumerGroupService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: UntypedFormBuilder,
    private translationService: TranslationService,
    private dialogRef: MatDialogRef<GroupSelectionComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  public loading = true;
  public groups: ConsumerGroupDto[] = [];
  public formGroup: UntypedFormGroup | undefined;

  ngOnInit(): void {
    this.createForms();
    this.loadData();
  }

  public save(): void {
    this.loading = true;
    const group = this.formGroup!!.controls['group'].value;

    this.systemService.moveToGroup(this.data.id, group.id).subscribe(
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
    this.service.findAll().subscribe(
      response => {
        this.groups = response;
        this.setValues();
      },
      () => {
        this.translationService.showSnackbarOnError();
        this.loading = false;
      }
    );
  }

  private setValues(): void {
    let group = this.groups.find(group => group.id === this.data.group.id);
    this.formGroup!!.setValue({
      group
    });

    this.loading = false;
  }

  private createForms(): void {
    this.formGroup = this.formBuilder.group(
      {
        group: [null, Validators.compose([Validators.required])]
      }
    );
  }
}
