package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO: Task 7 - DELETE /api/order/<orderId>

}
