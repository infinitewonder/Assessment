import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  'Personal - 6 inches',
  'Regular - 9 inches',
  'Large - 12 inches',
  'Extra Large - 15 inches',
];

const PIZZA_TOPPINGS: string[] = [
  'chicken',
  'seafood',
  'beef',
  'vegetables',
  'cheese',
  'arugula',
  'pineapple',
];

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent {
  pizzaSize = SIZES[0];

  orderForm = new FormGroup({
    name: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    pizzaSize: new FormControl('', Validators.required),
    base: new FormControl('', Validators.required),
    sauce: new FormControl('', Validators.required),
    toppings: new FormControl('', Validators.required),
    comments: new FormControl(''),
  });

  constructor(private pizzaService: PizzaService, private router: Router) {}

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)];
  }

  private createPizzaOrder(orderData: any): any {
    const pizzaOrder: any = {
      name: orderData.name,
      email: orderData.email,
      size: parseInt(orderData.pizzaSize),
      sauce: orderData.sauce,
      crust: orderData.base,
      toppings: orderData.toppings,
      comments: orderData.comments === '' ? null : orderData.comments,
    };
    return pizzaOrder;
  }

  onSubmit() {
    const pizzaOrder = this.createPizzaOrder(this.orderForm.value);
    this.pizzaService.placeOrder(pizzaOrder).subscribe(
      (response: any) => {
        this.router.navigate(['/orders', response.email]);
      },
      (error: any) => {
        alert(error.error.error);
      }
    );
  }
}
