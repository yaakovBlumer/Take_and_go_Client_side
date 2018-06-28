package com.example.yaakovblumer.takego_clientside.model.datasource;

import android.content.ContentValues;
import android.util.Log;

import com.example.yaakovblumer.takego_clientside.model.backend.DataSource;
import com.example.yaakovblumer.takego_clientside.model.backend.PHP_Tools;
import com.example.yaakovblumer.takego_clientside.model.entities.Branch;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.CarModel;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MySQL_DB_manager implements DataSource
{

    private static final String WEB_URL = "http://elhanani.vlab.jct.ac.il/TakeAndGo/carsRent";

    private boolean updateFlag = false;

    public void printLog(String message)
    {
        Log.d(this.getClass().getName(), "\n" + message);
    }

    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(), "Exception-->\n" + message);
    }

    private void SetUpdate()
    {
        updateFlag = true;
    }



    @Override
    public long addCustomer(Customer customer)
    {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8Fadd_customer.php";

            String result =PHP_Tools.POST(url, ConstantsAndEnums.CustomerToContentValues(customer));
            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;
            if (id > 0)
                SetUpdate();
            printLog("add customer:\n" + result);
            return id;
        }
        catch (IOException e)
        {
            printLog("addCustomer Exception:\n" + e);
            return -1;
        }
    }


    @Override
    public long addCar(Car car)
    {
        try {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8Fadd_car.php";

            String result = PHP_Tools.POST(url, ConstantsAndEnums.CarToContentValues(car));

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("add car:\n" + result);

            return id;

        }
        catch (Exception e)
        {
            printLog("addCar Exception:\n" + e);
            return -1;
        }
    }


    @Override
    public long addCarModel(CarModel carModel)
    {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8Fadd_car_model.php";

            String result =  PHP_Tools.POST(url, ConstantsAndEnums.CarModelToContentValues(carModel));

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("add carModel:\n" + result);

            return id;
        }
        catch (Exception e)
        {
            printLog("addCarModel Exception:\n" + e);
            return -1;
        }
    }


    @Override
    public long addBranch(Branch branch)
    {
        try
        {
            String url = WEB_URL + "/add_branch.php";

            String result =  PHP_Tools.POST(url, ConstantsAndEnums.BranchToContentValues(branch));

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("add Branch:\n" + result);

            return id;
        }
        catch (Exception e)
        {
            printLog("addBranch Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addOrder(Order order) {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8Fadd_order.php";

            String result =  PHP_Tools.POST(url, ConstantsAndEnums.OrderToContentValues(order));

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("add order:\n" + result);

            return id;
        }
        catch (Exception e)
        {
            printLog("addOrder Exception:\n" + e);
            return -1;
        }
        }

        //////////////////////////////////////////////////////


        @Override
        public Customer isExistsCustomer(String id) {

            try
            {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8FisCustomerExists.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CustomerConst.ID, id);
            String result =PHP_Tools.POST(url, contentValues);
                if(result.equals("0 results"))
                    return null;
                //JSONObject jsonObject=new JSONObject(result);
                //ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
                //Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues2);
            Customer customer=ConstantsAndEnums.ContentValuesToCustomer(PHP_Tools.JsonToContentValues(new JSONObject(result)));
            return customer;
            }
            catch (Exception e)
            {
                printLog("addOrder Exception:\n" + e);

            }
        return null;
    }

    @Override
    public Car isExistsCar(String carNumber) {
        try
        {
            String url = WEB_URL + "/isCarExists.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarConst.LICENSE_NUMBER, carNumber);
            String result =PHP_Tools.POST(url, contentValues);
            if(result.equals("0 results"))
                return null;
            //JSONObject jsonObject=new JSONObject(result);
            //ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
            //Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues2);
            Car car=ConstantsAndEnums.ContentValuesToCar(PHP_Tools.JsonToContentValues(new JSONObject(result)));
            return car;
        }
        catch (Exception e)
        {
            printLog("addOrder Exception:\n" + e);

        }
        return null;
    }

    @Override
    public CarModel isExistsCarModel(String modelCode) {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8FisCarModelExists.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarModelConst.MODEL_CODE, modelCode);
            String result =PHP_Tools.POST(url, contentValues);
            if(result.equals("0 results"))
                return null;
            //JSONObject jsonObject=new JSONObject(result);
            //ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
            //Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues2);
            CarModel carModel=ConstantsAndEnums.ContentValuesToCarModel(PHP_Tools.JsonToContentValues(new JSONObject(result)));
            return carModel;
        }
        catch (Exception e)
        {
            printLog("addOrder Exception:\n" + e);

        }
        return null;
    }

    @Override
    public Branch isExistsBranch(String branchId) {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8FisBranchExists.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.BranchConst.BRANCH_NUM, branchId);
            String result =PHP_Tools.POST(url, contentValues);
            if(result.equals("0 results"))
                return null;
            //JSONObject jsonObject=new JSONObject(result);
            //ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
            //Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues2);
            Branch branch=ConstantsAndEnums.ContentValuesToBranch(PHP_Tools.JsonToContentValues(new JSONObject(result)));
            return branch;
        }
        catch (Exception e)
        {
            printLog("addOrder Exception:\n" + e);

        }
        return null;
    }

    @Override
    public Order isExistsOrder(String orderNum) {
        try
        {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8FisOrderExists.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.OrderConst.ORDER_NUM, orderNum);
            String result =PHP_Tools.POST(url, contentValues);
            if(result.equals("0 results"))
                return null;
            //JSONObject jsonObject=new JSONObject(result);
            //ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
            //Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues2);
            Order order=ConstantsAndEnums.ContentValuesToOrder(PHP_Tools.JsonToContentValues(new JSONObject(result)));
            return order;
        }
        catch (Exception e)
        {
            printLog("addOrder Exception:\n" + e);

        }
        return null;
    }




    @Override
    public void updateCarMileage(String licenseNumber, int mileage) {
        try
        {
        String url = WEB_URL + "/update_car.php";
        ContentValues contentValues=new ContentValues();
        contentValues.put(ConstantsAndEnums.CarConst.LICENSE_NUMBER, licenseNumber);
        contentValues.put(ConstantsAndEnums.CarConst.MILEAGE, mileage);
        String result =PHP_Tools.POST(url, contentValues);

            String temp="UPDATED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("Updated car:\n" + result);

        }
        catch (Exception e)
        {
            printLog("Update Mileage Exception:\n" + e);

        }
    }

    @Override
    public ArrayList<Car> allCarAvailable() {

        ArrayList<Car> cars = new ArrayList<>();

        try {

            String str = PHP_Tools.GET(WEB_URL + "/getCarsAvailable.php");
            JSONArray array = new JSONObject(str).getJSONArray("Cars");
            if(array.equals("0 results"))
                return null;

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Car car = ConstantsAndEnums.ContentValuesToCar(contentValues);

                cars.add(car);
            }
            return cars;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Car> allCarAvailableInBranch(String id) {
        try {
            ArrayList<Car> cars = new ArrayList<>();

            String url = WEB_URL + "/getCarAvailableInBranch.php";

            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarConst.HOME_BRANCH, id);

            String result = PHP_Tools.POST(url, contentValues);
            if(result.equals("0 results"))
                return null;

            JSONArray array = new JSONObject(result).getJSONArray("Cars");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
                Car car = ConstantsAndEnums.ContentValuesToCar(contentValues2);

                cars.add(car);
            }
            return cars;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Car> allCarAvailableInRadius(int radius) {
        return null;
    }


    @Override
    public ArrayList<Branch> allBranchesExistsModel(String model) {
        ArrayList<Branch> branches = new ArrayList<>();

        try
        {
            String url = WEB_URL + "/AllBranchExistsModel.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarModelConst.MODEL_CODE, model);

            String result =  PHP_Tools.POST(url, contentValues);
            JSONArray array = new JSONObject(result).getJSONArray("Branches");
            if(array.equals("0 results"))
                return null;

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues2 = PHP_Tools.JsonToContentValues(jsonObject);
                Branch branch= ConstantsAndEnums.ContentValuesToBranch(contentValues2);

                branches.add(branch);
            }
            return branches;
        }
        catch (Exception e)
        {
            printLog("Exception:\n" + e);
        }

        return null;
    }


    @Override
    public ArrayList<Order> allOrdersOpen() {
        ArrayList<Order> orders = new ArrayList<>();

        try
        {
            String str = PHP_Tools.GET(WEB_URL + "/%E2%80%8F%E2%80%8FgetOrders.php");
            JSONArray array = new JSONObject(str).getJSONArray("Orders");
            if(array.equals("0 results"))
                return null;

            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Order order=ConstantsAndEnums.ContentValuesToOrder(contentValues);

                orders.add(order);
            }
            return orders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeOrder(String orderNum) {

        try
        {
            String url = WEB_URL + "/close_order.php";

            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.OrderConst.ORDER_NUM, orderNum);

            String result =  PHP_Tools.POST(url, contentValues);

            String temp="UPDATED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("closed order number:\n" + result);

        }
        catch (Exception e)
        {
            printLog("update order Exception:\n" + e);
        }
    }

    @Override
    public int isClosedOrderInLast10Seconds() {
        return 0;
    }

    @Override
    public long updateMileageCar(int mileage, String licenseNumber) {


        try {
            String url = WEB_URL + "/update_mileage_car.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarConst.MILEAGE, mileage);
            contentValues.put(ConstantsAndEnums.CarConst.LICENSE_NUMBER, licenseNumber);

            String result = PHP_Tools.POST(url, contentValues);

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("update mileage car:\n" + result);

            return id;

        }
        catch (Exception e)
        {
            printLog("updateCar Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long updateBusyCar(boolean busy, String licenseNumber) {


        try {
            String url = WEB_URL + "/%E2%80%8F%E2%80%8Fupdate_busy_car.php";
            ContentValues contentValues=new ContentValues();
            contentValues.put(ConstantsAndEnums.CarConst.BUSY, busy);
            contentValues.put(ConstantsAndEnums.CarConst.LICENSE_NUMBER, licenseNumber);

            String result = PHP_Tools.POST(url, contentValues);

            String temp="INSERTED OK";
            long id=-1;
            if(result.equals(temp))
                id=1;

            if (id > 0)
                SetUpdate();
            printLog("update busy car:\n" + result);

            return id;

        }
        catch (Exception e)
        {
            printLog("updateCar Exception:\n" + e);
            return -1;
        }
    }


    @Override
    public ArrayList<Customer> allCustomers()
    {
        ArrayList<Customer> customers = new ArrayList<>();

        try
        {
            String str = PHP_Tools.GET(WEB_URL + "/%E2%80%8F%E2%80%8FgetCustomers.php");
            JSONArray array = new JSONObject(str).getJSONArray("Customers");

            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Customer customer=ConstantsAndEnums.ContentValuesToCustomer(contentValues);

                customers.add(customer);
            }
            return customers;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public ArrayList<Car> allCars()
    {
        ArrayList<Car> cars = new ArrayList<>();

        try {

            String str = PHP_Tools.GET(WEB_URL + "/%E2%80%8F%E2%80%8FgetCars.php");
            JSONArray array = new JSONObject(str).getJSONArray("Cars");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Car car = ConstantsAndEnums.ContentValuesToCar(contentValues);

                cars.add(car);
            }
            return cars;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public ArrayList<CarModel> allCarModels()
    {
        ArrayList<CarModel> carModels = new ArrayList<>();

        try
        {

            String str = PHP_Tools.GET(WEB_URL + "/getCarModels.php");
            JSONArray array = new JSONObject(str).getJSONArray("CarModels");


            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                CarModel carModel=ConstantsAndEnums.ContentValuesToCarModel(contentValues);

                carModels.add(carModel);
            }
            return carModels;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public ArrayList<Branch> allBranches()
    {
        ArrayList<Branch> branches = new ArrayList<>();

        try
        {

            String str = PHP_Tools.GET(WEB_URL + "/%E2%80%8F%E2%80%8FgetBranches.php");
            JSONArray array = new JSONObject(str).getJSONArray("Branches");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Branch branch= ConstantsAndEnums.ContentValuesToBranch(contentValues);

                branches.add(branch);
            }
            return branches;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<Order> allOrders()
    {
        ArrayList<Order> orders = new ArrayList<>();

        try
        {
            String str = PHP_Tools.GET(WEB_URL + "/%E2%80%8F%E2%80%8FgetOrders.php");
            JSONArray array = new JSONObject(str).getJSONArray("Orders");

            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHP_Tools.JsonToContentValues(jsonObject);
                Order order=ConstantsAndEnums.ContentValuesToOrder(contentValues);

                orders.add(order);
            }
            return orders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
