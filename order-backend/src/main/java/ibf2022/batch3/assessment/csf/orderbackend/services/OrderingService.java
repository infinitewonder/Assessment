package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;

public class OrderingService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private PendingOrdersRepository pendingOrdersRepo;

    private RestTemplate restTemplate = new RestTemplate();

    // TODO: Task 5
    // WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
    public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {
        String apiUrl = "https://pizza-pricing-production.up.railway.app/order";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", order.getName());
        map.add("email", order.getEmail());
        map.add("sauce", order.getSauce());
        map.add("size", String.valueOf(order.getSize()));
        map.add("thickCrust", String.valueOf(order.isThickCrust()));
        map.add("toppings", String.join(",", order.getTopplings()));
        map.add("comments", order.getComments() == null ? "" : order.getComments());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String[] responseData = response.getBody().split(",");

            // Update order with received data
            order.setOrderId(responseData[0]);
            order.setDate(new Date(Long.parseLong(responseData[1])));
            order.setTotal(Float.parseFloat(responseData[2]));

            // Save order to Mongo and Redis DB
            ordersRepo.add(order);
            pendingOrdersRepo.add(order);

            return order;
        } else {
            throw new OrderException("Error while processing the order. Please try again later.");
        }
    }

    // For Task 6
    // WARNING: Do not change the method's signature or its implemenation
    public List<PizzaOrder> getPendingOrdersByEmail(String email) {
        return ordersRepo.getPendingOrdersByEmail(email);
    }

    // For Task 7
    // WARNING: Do not change the method's signature or its implemenation
    public boolean markOrderDelivered(String orderId) {
        return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
    }

}
