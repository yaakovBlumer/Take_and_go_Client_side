package com.example.yaakovblumer.takego_clientside.model.backend;

import com.example.yaakovblumer.takego_clientside.model.entities.Branch;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.CarModel;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;

import java.util.ArrayList;

public interface DataSource {

    //add:
    long addCustomer(Customer customer);
    long addCar(Car car);
    long addCarModel(CarModel carModel);
    long addBranch(Branch branch);
    long addOrder(Order order);


    //isExists:
    Customer isExistsCustomer(String id);
    Car isExistsCar(String carNumber);
    CarModel isExistsCarModel(String modelCode);
    Branch isExistsBranch(String branchId);
    Order isExistsOrder(String orderNum);


    //allList:
    ArrayList<Customer> allCustomers();
    ArrayList<Car> allCars();
    ArrayList<CarModel> allCarModels();
    ArrayList<Branch> allBranches();
    ArrayList<Order> allOrders();

////////////
}
