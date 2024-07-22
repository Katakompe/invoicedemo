import {TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {ApiModule} from './generated/modules/openapi';
import {provideHttpClient} from '@angular/common/http';
import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, ApiModule],
      providers: [provideHttpClient(), provideHttpClientTesting()]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
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

  it('should have a table with 2 entries', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    let httpTesting = TestBed.inject(HttpTestingController);
    const req = httpTesting.expectOne('http://localhost:8080/invoices', 'Request to load the invoice table');

    req.flush('[' +
      '{"invoiceNumber":"AB1","dueDate":[2024,1,23],"billingAmount":22.22},' +
      '{"invoiceNumber":"AB2","dueDate":[2024,12,17],"billingAmount":10000.99}' +
      ']');

    const compiled = fixture.nativeElement as HTMLElement;
    //compiled.querySelectorAll('.mat-row').forEach(v => console.log(v))
    expect(compiled.querySelectorAll('tr.mat-mdc-row').length).toBe(2)
  });
});
