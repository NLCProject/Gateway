import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslationService} from '../../../services/translation/translation.service';
import {ConsumerGroupService} from '../../../services/rest/consumer-group.service';
import { ConsumerMode } from 'src/app/dto/ConsumerMode';
import {ConsumerGroupDto} from '../../../dto/ConsumerGroupDto';
import {ConsumerGroupComponent} from './consumer-group/consumer-group.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-consumer-overview',
  templateUrl: './consumer-overview.component.html',
  styleUrls: ['./consumer-overview.component.scss']
})
export class ConsumerOverviewComponent implements OnInit, OnDestroy {

  constructor(
    private dialog: MatDialog,
    private consumerService: ConsumerGroupService,
    private translationService: TranslationService
  ) { }

  private interval: any = null;
  public loading = true;
  public ConsumerMode = ConsumerMode;
  public groups: ConsumerGroupDto[] = [];

  ngOnInit(): void {
    this.loadData();

    this.interval = setInterval(() => {
      this.loadData();
    }, 2000);
  }

  public ngOnDestroy(): void {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  public openDialog(group: ConsumerGroupDto): void {
    const promptDialog = this.dialog.open(ConsumerGroupComponent, {
      data: { group }
    });

    promptDialog.afterClosed().subscribe(() => {
      this.loadData();
    });
  }

  public openDialogOnNew(): void {
    const promptDialog = this.dialog.open(ConsumerGroupComponent);

    promptDialog.afterClosed().subscribe(() => {
      this.loadData();
    });
  }

  private loadData(): void {
    this.consumerService.findAll().subscribe(
      response => {
        this.groups = response;
        this.loading = false;
      },
      () => {
        this.translationService.showSnackbarOnError();
        this.loading = false;
      }
    );
  }
}
