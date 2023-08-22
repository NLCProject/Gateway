import { Component, OnInit } from '@angular/core';
import {TranslationService} from "../../../services/translation/translation.service";
import {SystemValue} from "../../../dto/SystemValue";
import {ConsumerService} from "../../../services/rest/consumer.service";
import { ConsumerMode } from 'src/app/dto/ConsumerMode';
import {ConsumerGroupDto} from "../../../dto/ConsumerGroupDto";

@Component({
  selector: 'app-consumer-overview',
  templateUrl: './consumer-overview.component.html',
  styleUrls: ['./consumer-overview.component.scss']
})
export class ConsumerOverviewComponent implements OnInit {

  constructor(
    private consumerService: ConsumerService,
    private translationService: TranslationService
  ) { }

  private interval: any = null;
  public loading = true;
  public values: SystemValue[] = [];
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

  private loadData(): void {
    this.loading = true;
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
