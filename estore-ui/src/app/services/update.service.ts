import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {
  private cartUpdateSource = new BehaviorSubject<boolean>(false);
  cartUpdate$ = this.cartUpdateSource.asObservable();

  constructor(private messageService: MessageService) { }

  /** Log an UpdateService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`UpdateService: ${message}`);
  }

  updateCart() {
    this.cartUpdateSource.next(true);
    this.log('cart updated');
  }
}
