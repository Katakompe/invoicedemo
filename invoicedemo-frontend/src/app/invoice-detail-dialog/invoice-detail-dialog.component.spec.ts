import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InvoiceDetailDialogComponent} from './invoice-detail-dialog.component';

describe('InvoiceDetailDialogComponent', () => {
  let component: InvoiceDetailDialogComponent;
  let fixture: ComponentFixture<InvoiceDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoiceDetailDialogComponent]
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
