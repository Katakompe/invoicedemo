import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ApiModule, BASE_PATH, InvoiceListItem, InvoiceService} from "./generated/modules/openapi";
import {SERVER_BASE_URL} from "./env";
import {Observable, of} from "rxjs";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ApiModule, MatTableModule],
  providers: [
    {provide: BASE_PATH, useValue: SERVER_BASE_URL},
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  displayedColumns: string[] = ['invoiceNumber', 'billingAmount', 'dueDate']

  invoices$: Observable<InvoiceListItem[]> = of();

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit() {
    this.loadInvoices();
  }

  private loadInvoices() {
    this.invoices$ = this.invoiceService.invoicesGet();
  }
}
