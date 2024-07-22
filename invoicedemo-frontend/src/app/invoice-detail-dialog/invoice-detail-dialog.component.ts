import {Component, inject, OnInit} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {Invoice, InvoiceService} from "../generated/modules/openapi";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-invoice-detail-dialog',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatProgressSpinnerModule,
    MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    MatCardActions,
    MatButton
  ],
  templateUrl: './invoice-detail-dialog.component.html',
  styleUrl: './invoice-detail-dialog.component.css'
})
export class InvoiceDetailDialogComponent implements OnInit{
  readonly dialogRef = inject(MatDialogRef<InvoiceDetailDialogComponent>);
  readonly invoiceNumber = inject<string>(MAT_DIALOG_DATA);

  invoice: (Invoice|null) = null;

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit() {
    this.invoiceService.invoiceInvoiceNumberGet(this.invoiceNumber)
      .subscribe(i => this.invoice = i);
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
