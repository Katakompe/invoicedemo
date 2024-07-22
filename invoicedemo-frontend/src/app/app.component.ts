import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ApiModule, BASE_PATH, InvoiceListItem, InvoiceService} from "./generated/modules/openapi";
import {SERVER_BASE_URL} from "./env";
import {delay, Observable, of} from "rxjs";
import {MatTableModule} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {InvoiceDetailDialogComponent} from "./invoice-detail-dialog/invoice-detail-dialog.component";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {AsyncPipe} from "@angular/common";
import {NoDataRowOutlet} from "@angular/cdk/table";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ApiModule, MatTableModule, MatProgressSpinner, AsyncPipe, NoDataRowOutlet],
  providers: [
    {provide: BASE_PATH, useValue: SERVER_BASE_URL},
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  readonly displayedColumns: string[] = ['invoiceNumber', 'billingAmount', 'dueDate']

  invoices$: Observable<InvoiceListItem[]> = of([]);

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit() {
    this.loadInvoices();
  }

  private loadInvoices() {
    this.invoices$ = this.invoiceService.invoicesGet()      .pipe(
      delay(1000)
    );
  }

  openInvoiceDialog(row: InvoiceListItem) {
    this.dialog.open(InvoiceDetailDialogComponent, {
      data: row.invoiceNumber
    });
  }

}
