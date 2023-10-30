package com.bird_farm_shop_android;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bird_farm_shop_android.dao.BirdDAO;
import com.bird_farm_shop_android.dao.FoodDAO;
import com.bird_farm_shop_android.dao.NestDAO;
import com.bird_farm_shop_android.dao.OrderDAO;
import com.bird_farm_shop_android.dao.OrderDetailDAO;
import com.bird_farm_shop_android.dao.UserDAO;
import com.bird_farm_shop_android.enums.OrderStatus;
import com.bird_farm_shop_android.models.Order;
import com.bird_farm_shop_android.models.OrderDetail;
import com.bird_farm_shop_android.models.Product;
import com.bird_farm_shop_android.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// =============================================================================================
        // Create User Done
//        UserDAO userDAO = new UserDAO();
//        User user = new User("LOLTUANCUT", "LOLTUANCUT@gmail.com", "LOLTUANCUT", "LOLTUANCUT",
//                false, "LOLTUANCUT", "2020-02-02", 1);
//        userDAO.createUser(user);

        // Get All User Done
//        UserDAO userDAO = new UserDAO();
//        List<User> userList = userDAO.getAllUser();
//        for(User user : userList)
//        {
//            System.out.println(user.toString());
//        }

        // Get User By Id DONE
//        UserDAO userDAO = new UserDAO();
//        User user = userDAO.getUserByID(5);
//        System.out.println(user.toString());

        //Update User DONE
//        UserDAO userDAO = new UserDAO();
//        User user = new User(6, "LOLTUCMM_UPDATE", "LOLTUCMM_UPDATE@gmail.com", "0902292912", "123456",
//                false, "LOLTUCMM_UPDATE", "2020-02-02", 1);
//        userDAO.updateUser(user);

        //Delete User By ID DONE
//        UserDAO userDAO = new UserDAO();
//        userDAO.deteleUserByID(6);

        //Check LOGIN USER DONE
//        UserDAO userDAO = new UserDAO();
//        userDAO.checkLogin("email@gmail.com", "123456");

        // get User By Email DONE
//        UserDAO userDAO = new UserDAO();
//        User user = userDAO.getUserByEmail("lolTu@gmail.com");
//        System.out.println(user.toString());

// =============================================================================================

        // Create Bird DONE
//        BirdDAO birdDAO = new BirdDAO();
//        Product product = new Product("Bird 3", Float.parseFloat("97667"), "This is Product 3", 31);
//        birdDAO.createBird(product);

        // Get All Bird DONE
//        BirdDAO birdDAO = new BirdDAO();
//        List<Product> allBird = birdDAO.getAllBird();
//        for (Product bird : allBird)
//        {
//            System.out.println(bird.toString());
//        }

        //Get Bird By Id Done
//        BirdDAO birdDAO = new BirdDAO();
//        Product bird = birdDAO.getBirdByID(1);
//        System.out.println("Bird DAO: " + bird.toString());

        //Delete Bird by ID DONE
//        BirdDAO birdDAO = new BirdDAO();
//        birdDAO.deleteBirdByID(1);

        //DELETE BIRD BY BIRD CLASS DONE
//        BirdDAO birdDAO = new BirdDAO();
//        Product product = new Product(3, "Bird 3", Float.parseFloat("97667"), "This is Product 3", 31);
//        birdDAO.deleteBird(product);

        //UPDATE BIRD DONE
//        BirdDAO birdDAO = new BirdDAO();
//        Product product = new Product(2, "LOL TU", Float.parseFloat("97667"), "This is LOL TU AN CUT", 11);
//        birdDAO.updateBird(product);

// =============================================================================================

        // Create FOOD DONE
//        FoodDAO foodDAO = new FoodDAO();
//        Product product = new Product("Food 3", Float.parseFloat("233232"), "This is Food 3", 23);
//        foodDAO.createFood(product);

        // Get All Bird DONE
//        FoodDAO foodDAO = new FoodDAO();
//        List<Product> allFood = foodDAO.getAllFood();
//        for (Product food : allFood)
//        {
//            System.out.println(food.toString());
//        }

        //Get Bird By Id Done
//        FoodDAO foodDAO = new FoodDAO();
//        Product food = foodDAO.getFoodByID(4);
//        System.out.println("FOOD DAO: " + food.toString());

        //Delete Food by ID DONE
//        FoodDAO foodDAO = new FoodDAO();
//        foodDAO.deleteFoodByID(5);

        //DELETE FOOD BY PRODUCT CLASS DONE
//        FoodDAO foodDAO = new FoodDAO();
//        Product product = new Product(7, "Food 3", Float.parseFloat("233232"), "This is Food 3", 23);
//        foodDAO.deleteFood(product);

        //UPDATE BIRD DONE
//        FoodDAO foodDAO = new FoodDAO();
//        Product product = new Product(4, "CON CHO TU", Float.parseFloat("97667"), "This is CON CHO TU AN CUT", 11);
//        foodDAO.updateFood(product);

// =============================================================================================
        // Create NEST DONE
//        NestDAO nestDAO = new NestDAO();
//        Product product = new Product("Nest 4", Float.parseFloat("2293"), "This is Nest 2", 93);
//        nestDAO.createNest(product);

        // Get All NEST DONE
//        NestDAO nestDAO = new NestDAO();
//        List<Product> allNest = nestDAO.getAllNest();
//        for (Product nest : allNest)
//        {
//            System.out.println(nest.toString());
//        }

        //Get NEST By Id Done
//        NestDAO nestDAO = new NestDAO();
//        Product nest = nestDAO.getNestByID(9);
//        System.out.println("NEST DAO: " + nest.toString());

        //Delete NEST by ID DONE
//        NestDAO nestDAO = new NestDAO();
//        nestDAO.deleteNestByID(8);

        //DELETE NEST BY PRODUCT CLASS DONE
//        NestDAO nestDAO = new NestDAO();
//        Product product = new Product(9, "CON CHO TU 9", Float.parseFloat("233232"),"CON CHO TU 9 ", 23);
//        nestDAO.deleteNest(product);

        //UPDATE NEST DONE
//        NestDAO nestDAO = new NestDAO();
//        Product product = new Product(9, "CON CHO TU 9 ", Float.parseFloat("97667"), "This is CON CHO TU AN CUT 9 ", 11);
//        nestDAO.updateNest(product);

// =============================================================================================
        // Create Order DONE
//        OrderDAO orderDAO = new OrderDAO();
//        Order order = new Order(3, "LOLMMM", "LOLMMM", "LOLMMM",
//                Float.parseFloat("12333313"), "2023-11-29", OrderStatus.PENDING, "22222 LOLMMM LOLMMM LOLMMM");
//        orderDAO.createOrder(order);

        // Get All Order DONE
//        OrderDAO orderDAO = new OrderDAO();
//        List<Order> orderList = orderDAO.getAllOrder();
//        for(Order order : orderList)
//        {
//            System.out.println(order.toString());
//        }

        //Get Order By ID Done
//        OrderDAO orderDAO = new OrderDAO();
//        Order order = orderDAO.getOrderByID(6);
//        System.out.println("This is get Order By ID: " + order.toString());

        //Delete Order By ID DONE
//        OrderDAO orderDAO = new OrderDAO();
//        orderDAO.deleteOrderByID(5);

        //Delete Order  by Order
//        OrderDAO orderDAO = new OrderDAO();
//        Order order = new Order(6, 1, "con dog Tam Version 2", "556644332211", "ThaiLand",
//                Float.parseFloat("009922211"), "2023-10-29", OrderStatus.PENDING, "22222 This Note");
//        orderDAO.deleteOrder(order);

        //Get List Order by Customer ID DONE
//        OrderDAO orderDAO = new OrderDAO();
//        List<Order> orderList = orderDAO.getOrderByCustomerID(1);
//        for(Order order : orderList)
//        {
//            System.out.println(order.toString());
//        }

        // Update Order Done
//        OrderDAO orderDAO = new OrderDAO();
//        Order order = new Order(7, 1, "Xin cam on xin cam on", "9922010122", "ThaiLand",
//                Float.parseFloat("009922211"), "2023-10-29", OrderStatus.PENDING, "Xin cam on xin cam on");
//        orderDAO.updateOrder(order);

// =============================================================================================
        //Create Order Detail DONE
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        OrderDetail orderDetail = new OrderDetail(3, 3);
//        orderDetailDAO.createOrderDetail(orderDetail);

        //Get OrderDetail by OrderID DONE
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailByOrderID(3);
//        for (OrderDetail orderDetail : orderDetails)
//        {
//            System.out.println(orderDetail.toString());
//        }

        //Get OrderDetail by OrderDetailID DONE
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        OrderDetail orderDetail = orderDetailDAO.getOrderDetailByID(3);
//        System.out.println(orderDetail.toString());

        //Get All Order Detail
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        List<OrderDetail> orderDetails = orderDetailDAO.getAllOrderDetail();
//        for (OrderDetail orderDetail : orderDetails)
//        {
//            System.out.println("Order Detail: " + orderDetail.toString());
//        }

        //update Order Detail
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        OrderDetail orderDetail = new OrderDetail(2, 3, 3);
//        orderDetailDAO.updateOrderDetail(orderDetail);

        //Delete OrderDetail By OrderDetailId
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        orderDetailDAO.deleteOrderDetailByID(3);

        //Delete OrderDetail by OrderDetail
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        OrderDetail orderDetail = new OrderDetail(4, 3, 3);
//        orderDetailDAO.deleteOrderDetail(orderDetail);
    }
}