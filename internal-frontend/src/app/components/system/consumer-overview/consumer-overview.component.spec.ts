import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsumerOverviewComponent } from './consumer-overview.component';

describe('ConsumerOverviewComponent', () => {
  let component: ConsumerOverviewComponent;
  let fixture: ComponentFixture<ConsumerOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsumerOverviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsumerOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
