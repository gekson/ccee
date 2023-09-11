import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {
  count = 0;
  visibility: BehaviorSubject<boolean>;

  constructor() {
    this.visibility = new BehaviorSubject<boolean>(false);
  }

  show() {
    this.visibility.next(true);
    this.count++;
  }

  hide() {
    this.count--;
    this.visibility.next(false);
  }
}