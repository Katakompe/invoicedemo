import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InvoiceDetailDialogComponent} from './invoice-detail-dialog.component';
import {ApiModule, Invoice} from "../generated/modules/openapi";
import {provideHttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {HarnessLoader} from "@angular/cdk/testing";
import {MatCardModule} from "@angular/material/card";
import {TestbedHarnessEnvironment} from "@angular/cdk/testing/testbed";
import {MatCardHarness} from "@angular/material/card/testing";

let loader: HarnessLoader;
let fixture: ComponentFixture<InvoiceDetailDialogComponent>;

describe('InvoiceDetailDialogComponent', () => {
  let component: InvoiceDetailDialogComponent;
  let fixture: ComponentFixture<InvoiceDetailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoiceDetailDialogComponent, ApiModule, MatCardModule],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        {provide: MatDialogRef, useValue: {}}, {provide: MAT_DIALOG_DATA, useValue: "A1"}]
    })
      .compileComponents();
    fixture = TestBed.createComponent(InvoiceDetailDialogComponent);
    loader = TestbedHarnessEnvironment.loader(fixture);
  });

  it('should create', () => {
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });

  it('should render title', async () => {
    fixture.componentInstance.invoice = {address: "", name: "", billingAmount: 0, dueDate: "", invoiceNumber: ""};
    fixture.detectChanges()
    const matCard = await loader.getHarness(MatCardHarness);
    const title = await matCard.getTitleText()
    expect(title).toBe("Invoice A1");
  });

  it('should call /invoice/A1', () => {
    const fixture = TestBed.createComponent(InvoiceDetailDialogComponent);
    fixture.detectChanges();
    let httpTesting = TestBed.inject(HttpTestingController);
    httpTesting.expectOne('http://localhost:8080/invoice/A1', 'Request to load the invoice with number A1');
  });

  it('should render all invoice details', () => {
    const invoice: Invoice = {
      invoiceNumber: "AB1",
      dueDate: "2023-03-03",
      billingAmount: 22.22,
      name: "Bill",
      address: "mystreet 1"
    };
    fixture.componentInstance.invoice = invoice
    const numKeys = Object.keys(invoice).length

    fixture.detectChanges()

    const nativeElement = fixture.nativeElement as HTMLElement;
    const numRows = nativeElement.querySelectorAll('tr').length
    expect(numRows).toBe(numKeys);
  });

});
