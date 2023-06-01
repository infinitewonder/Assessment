import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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

  constructor() {}

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)];
  }

  onSubmit() {
    console.log(this.orderForm.value);
    // Call the order API endpoint/service here with this.orderForm.value as the payload
  }
}
