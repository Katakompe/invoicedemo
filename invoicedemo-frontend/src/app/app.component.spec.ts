import {ComponentFixture, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {ApiModule} from './generated/modules/openapi';
import {provideHttpClient} from '@angular/common/http';
import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';
import {of} from "rxjs";
import {MatTableModule} from "@angular/material/table";
import {HarnessLoader} from "@angular/cdk/testing";
import {TestbedHarnessEnvironment} from "@angular/cdk/testing/testbed";
import {MatTableHarness} from "@angular/material/table/testing";

let loader: HarnessLoader;
let fixture: ComponentFixture<AppComponent>;

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, ApiModule, MatTableModule],
      providers: [provideHttpClient(), provideHttpClientTesting()]
    }).compileComponents();
    fixture = TestBed.createComponent(AppComponent);
    loader = TestbedHarnessEnvironment.loader(fixture);
  });

  it('should create the app', () => {
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });


  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Your invoices');
  });


  it('should call /invoices', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    let httpTesting = TestBed.inject(HttpTestingController);
    httpTesting.expectOne('http://localhost:8080/invoices', 'Request to load the invoice table');
  });


  it('should have a table with 2 entries', async () => {
    const table = await loader.getHarness(MatTableHarness);
    fixture.componentInstance.invoices$ = of([
        {invoiceNumber: "AB1", dueDate: "2023-03-03", billingAmount: 22.22},
        {invoiceNumber: "AB2", dueDate: "2023-03-03", billingAmount: 10000.99}
      ]
    );
    fixture.detectChanges();
    const rows = await table.getRows();
    expect(rows.length).toBe(2);
  });

  it('should open MatDialog on click', async () => {
    const table = await loader.getHarness(MatTableHarness);
    fixture.componentInstance.invoices$ = of([
        {invoiceNumber: "AB1", dueDate: "2023-03-03", billingAmount: 22.22},
        {invoiceNumber: "AB2", dueDate: "2023-03-03", billingAmount: 10000.99}
      ]
    );

    fixture.detectChanges();
    const rows = await table.getRows();
    const testelement = await rows[0].host();
    await testelement.click();

    expect(fixture.componentInstance.dialog.openDialogs.length).toBe(1);

  });
});
