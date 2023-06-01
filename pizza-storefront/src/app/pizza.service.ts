import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PizzaService {
  private orderUrl = '/api/order';

  constructor(private http: HttpClient) {}

  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder(orderDetails: any): Observable<any> {
    return this.http.post<any>(this.orderUrl, orderDetails);
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(email: string): Observable<any> {
    return this.http.get<any>(`${this.orderUrl}/${email}`);
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered(orderId: string): Observable<any> {
    return this.http.delete<any>(`${this.orderUrl}/${orderId}`);
  }
}
