package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

@Repository
public class OrdersRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // TODO: Task 3
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for add()
    public void add(PizzaOrder order) {
        mongoTemplate.insert(order, "orders");
    }

    // TODO: Task 6
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for getPendingOrdersByEmail()
    public List<PizzaOrder> getPendingOrdersByEmail(String email) {
        MongoCollection<Document> collection = mongoTemplate.getCollection("orders");

        // Native MongoDB query:
        // db.orders.find({email: <email>, delivered: {$ne: true}}, {_id: 0, orderId: 1,
        // total: 1, date: 1}).sort({date: -1})

        List<Document> results = collection.find(Filters.and(Filters.eq("email", email), Filters.ne("delivered", true)))
                .projection(new Document("_id", 0).append("orderId", 1).append("total", 1).append("date", 1))
                .sort(new Document("date", -1))
                .into(new ArrayList<>());

        List<PizzaOrder> orders = new ArrayList<>();
        for (Document doc : results) {
            PizzaOrder order = new PizzaOrder();
            order.setOrderId(doc.getString("orderId"));
            order.setDate(doc.getDate("date"));
            order.setTotal(Float.valueOf(doc.getDouble("total").toString()));
            orders.add(order);
        }
        return orders;
    }

    // TODO: Task 7
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for markOrderDelivered()
    // db.orders.updateOne({_id: <orderId>}, { $set: { delivered: true } })
    public boolean markOrderDelivered(String orderId) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(orderId)));
        Update update = new Update().set("delivered", true);
        UpdateResult result = mongoTemplate.updateFirst(query, update, PizzaOrder.class);
        return result.getModifiedCount() > 0;
    }

}
