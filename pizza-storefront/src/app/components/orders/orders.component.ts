import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PizzaService } from 'src/app/pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  email: string;
  pendingOrders: any[] | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pizzaService: PizzaService
  ) {
    this.email = this.route.snapshot.paramMap.get('email') || '';
  }

  ngOnInit(): void {
    this.fetchPendingOrders();
  }

  fetchPendingOrders(): void {
    this.pizzaService.getOrders(this.email).subscribe((orders: any[]) => {
      this.pendingOrders = orders;
    });
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}
