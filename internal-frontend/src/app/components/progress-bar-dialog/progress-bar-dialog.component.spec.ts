import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressBarDialogComponent } from './progress-bar-dialog.component';

describe('ProgressBarDialogComponent', () => {
  let component: ProgressBarDialogComponent;
  let fixture: ComponentFixture<ProgressBarDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProgressBarDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProgressBarDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
