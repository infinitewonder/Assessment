package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class PendingOrdersRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // TODO: Task 3
    // WARNING: Do not change the method's signature.
    public void add(PizzaOrder order) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(order.getOrderId(), order);
    }

    // TODO: Task 7
    // WARNING: Do not change the method's signature.
    public boolean delete(String orderId) {
        return redisTemplate.delete(orderId);
    }

}
