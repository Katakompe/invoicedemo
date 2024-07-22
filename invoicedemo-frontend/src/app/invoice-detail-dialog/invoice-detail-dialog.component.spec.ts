import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InvoiceDetailDialogComponent} from './invoice-detail-dialog.component';
import {ApiModule} from "../generated/modules/openapi";
import {provideHttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

describe('InvoiceDetailDialogComponent', () => {
  let component: InvoiceDetailDialogComponent;
  let fixture: ComponentFixture<InvoiceDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoiceDetailDialogComponent, ApiModule],
      providers: [provideHttpClient(),
        { provide: MatDialogRef, useValue: {} }, { provide: MAT_DIALOG_DATA, useValue: "A1" }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvoiceDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
