package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;

@RestController
@RequestMapping("/api")
public class OrderController {

    // TODO: Task 3 - POST /api/order
    @Autowired
    private OrderingService orderingService;

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody PizzaOrder orderDetails) {
        try {
            PizzaOrder response = orderingService.placeOrder(orderDetails);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: Task 6 - GET /api/orders/<email>
    @GetMapping("/orders/{email}")
    public ResponseEntity<?> getPendingOrders(@PathVariable String email) {
        List<PizzaOrder> response = orderingService.getPendingOrdersByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TODO: Task 7 - DELETE /api/order/<orderId>
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId) {
        boolean result = orderingService.markOrderDelivered(orderId);
        if (result) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Order not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
