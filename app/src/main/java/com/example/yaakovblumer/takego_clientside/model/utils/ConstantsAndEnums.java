package com.example.yaakovblumer.takego_clientside.model.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.ContentValues;

import com.example.yaakovblumer.takego_clientside.model.entities.Branch;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.CarModel;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;

public class ConstantsAndEnums {

    public class Log {
        public final static String TAG = "Take&GO";

        public final static String APP_LOG = "Rent";
    }

    public class SharedPreferences{
        public final static String File = "Local.Preferences";
    }

    public class BroadCastMessages{
        public final static String UPDATE = "com.example.ourex.takengo.UPDATE";
    }

    public class Messages{

    }

    public enum gearboxMode{MANUAL, AUTOMATIC}

    public enum carKind{PRIVATE, FAMILY, TRANSIT, JEEP}

    public enum orderMode{OPEN, CLOSE}


    /////////////////////////////////////////

    public static class CustomerConst {
        public static final String ID = "Id";
        public static final String FIRST_NAME = "FirstName";
        public static final String LAST_NAME = "LastName";
        public static final String PHONE_NUM = "PhoneNum";
        public static final String EMAIL = "Email";
        public static final String CREDIT_CARD_NUM = "CreditCardNum";    }


    public static class CarConst {
        public static final String MODEL_CODE = "Model";
        public static final String PRODUCTION_DATE = "ProductionDate";
        public static final String MILEAGE = "Mileage";
        public static final String LICENSE_NUMBER = "LicenseNumber";
        public static final String HOME_BRANCH = "HomeBranch";
        public static final String AVARAGE_COST_PER_DAY = "AverageCostPerDay";
        public static final String BUSY = "Busy"; }



    public static class BranchConst {
        public static final String BRANCH_ADDRESS = "BranchAddress";
        public static final String CAPACITY_OF_CAR = "CapacityOfCar";
        public static final String BRANCH_NUM = "BranchNum";
        public static final String ADMINISTRATOR_NAME = "AdministratorName"; }


    public static class CarModelConst {
        public static final String COMPANY_NAME = "CompanyName";
        public static final String MODEL_NAME = "ModelName";
        public static final String MODEL_CODE = "ModelCode";
        public static final String ENGINE_VOLUME = "EngineVolume";
        public static final String GEARBOX = "Gearbox";
        public static final String NUM_OF_SEATS = "NumOfSeats";
        public static final String CAR_KIND = "CarKind"; }



    public static class OrderConst {
        public static final String CUSTOMER_NUM = "CustomerNum";
        public static final String MODE_OF_ORDER = "ModeOfOrder";
        public static final String CAR_NUMBER = "CarNumber";
        public static final String RENT_START_DATE = "RentStartDate";
        public static final String RENT_END_DATE = "RentEndDate";
        public static final String KILOMETERES_AT_START = "KilometresAtStart";
        public static final String KILOMETERES_AT_END = "KilometresAtEnd";
        public static final String IS_INSERT_DELEK = "IsInsertDelek";
        public static final String HOW_MUCH_DELEK_INSERT = "HowMuchDelekInsert";
        public static final String HOW_MUCH_NEED_PAY = "HowMuchNeedPay";
        public static final String ORDER_NUM = "OrderNum"; }




    public static ContentValues CustomerToContentValues(Customer customer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerConst.ID, customer.getId());
        contentValues.put(CustomerConst.FIRST_NAME, customer.getFirstName());
        contentValues.put(CustomerConst.LAST_NAME, customer.getLastName());
        contentValues.put(CustomerConst.PHONE_NUM, customer.getPhoneNum());
        contentValues.put(CustomerConst.EMAIL, customer.getEmail());
        contentValues.put(CustomerConst.CREDIT_CARD_NUM, customer.getCreditCardNum());
        return contentValues; }



    public static Customer ContentValuesToCustomer(ContentValues contentValues) {
        Customer customer = new Customer();
        customer.setId(contentValues.getAsString(CustomerConst.ID));
        customer.setFirstName(contentValues.getAsString(CustomerConst.FIRST_NAME));
        customer.setLastName(contentValues.getAsString(CustomerConst.LAST_NAME));
        customer.setPhoneNum(contentValues.getAsString(CustomerConst.PHONE_NUM));
        customer.setEmail(contentValues.getAsString(CustomerConst.EMAIL));
        customer.setCreditCardNum(contentValues.getAsString(CustomerConst.CREDIT_CARD_NUM));
        return customer; }


    public static ContentValues CarToContentValues(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConst.MODEL_CODE, car.getModelCode());
        contentValues.put(CarConst.PRODUCTION_DATE, car.getProductionDate().toString());
        contentValues.put(CarConst.MILEAGE, car.getMileage());
        contentValues.put(CarConst.LICENSE_NUMBER, car.getLicenseNumber() );
        contentValues.put(CarConst.HOME_BRANCH, car.getHomeBranch());
        contentValues.put(CarConst.AVARAGE_COST_PER_DAY, car.getAverageCostPerDay());
        contentValues.put(CarConst.BUSY, car.getBusy());
        return contentValues; }



    public static Car ContentValuesToCar(ContentValues contentValues) {
        Car car = new Car();
        car.setModelCode(contentValues.getAsString(CarConst.MODEL_CODE));
        car.setProductionDate(contentValues.getAsString(CarConst.PRODUCTION_DATE));
        car.setMileage(contentValues.getAsInteger(CarConst.MILEAGE));
        car.setLicenseNumber(contentValues.getAsString(CarConst.LICENSE_NUMBER));
        car.setHomeBranch(contentValues.getAsString(CarConst.HOME_BRANCH));
        car.setAverageCostPerDay(contentValues.getAsInteger(CarConst.AVARAGE_COST_PER_DAY));
        car.setBusy(contentValues.getAsBoolean(CarConst.BUSY));
        return car; }



    public static ContentValues BranchToContentValues(Branch branch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BranchConst.BRANCH_ADDRESS, branch.getBranchAddress());
        contentValues.put(BranchConst.CAPACITY_OF_CAR, branch.getCapacityOfCar());
        contentValues.put(BranchConst.BRANCH_NUM, branch.getBranchNum());
        contentValues.put(BranchConst.ADMINISTRATOR_NAME, branch.getAdministratorName());
        return contentValues; }



    public static Branch ContentValuesToBranch(ContentValues contentValues) {
        Branch branch = new Branch();
        branch.setBranchAddress(contentValues.getAsString(BranchConst.BRANCH_ADDRESS));
        branch.setCapacityOfCar(contentValues.getAsInteger(BranchConst.CAPACITY_OF_CAR));
        branch.setBranchNum(contentValues.getAsString(BranchConst.BRANCH_NUM));
        branch.setAdministratorName(contentValues.getAsString(BranchConst.ADMINISTRATOR_NAME));
        return branch; }



    public static ContentValues CarModelToContentValues(CarModel carModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarModelConst.COMPANY_NAME, carModel.getCompanyName());
        contentValues.put(CarModelConst.MODEL_NAME, carModel.getModelName());
        contentValues.put(CarModelConst.MODEL_CODE, carModel.getModelCode());
        contentValues.put(CarModelConst.ENGINE_VOLUME, carModel.getEngineVolume());
        contentValues.put(CarModelConst.GEARBOX, carModel.getGearbox().toString());
        contentValues.put(CarModelConst.NUM_OF_SEATS, carModel.getNumOfSeats());
        contentValues.put(CarModelConst.CAR_KIND, carModel.getCarKind().toString());
        return contentValues; }



    public static CarModel ContentValuesToCarModel(ContentValues contentValues) {
        CarModel carModel = new CarModel();
        carModel.setCompanyName(contentValues.getAsString(CarModelConst.COMPANY_NAME));
        carModel.setModelName(contentValues.getAsString(CarModelConst.MODEL_NAME));
        carModel.setModelCode(contentValues.getAsString(CarModelConst.MODEL_CODE));
        carModel.setEngineVolume(contentValues.getAsInteger(CarModelConst.ENGINE_VOLUME));
        carModel.setGearbox(gearboxMode.valueOf(contentValues.getAsString(CarModelConst.GEARBOX)));
        carModel.setNumOfSeats(contentValues.getAsInteger(CarModelConst.NUM_OF_SEATS));
        carModel.setCarKind(carKind.valueOf(contentValues.getAsString(CarModelConst.CAR_KIND)));
        return carModel; }





    public static ContentValues OrderToContentValues(Order order) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderConst.CUSTOMER_NUM, order.getCustomerNum());
        contentValues.put(OrderConst.MODE_OF_ORDER, order.getModeOfOrder().toString());
        contentValues.put(OrderConst.CAR_NUMBER, order.getCarNumber());
        contentValues.put(OrderConst.RENT_START_DATE, order.getRentStartDate().toString());
        contentValues.put(OrderConst.RENT_END_DATE, order.getRentEndDate().toString());
        contentValues.put(OrderConst.KILOMETERES_AT_START, order.getKilometresAtStart());
        contentValues.put(OrderConst.KILOMETERES_AT_END, order.getKilometresAtEnd());
        contentValues.put(OrderConst.IS_INSERT_DELEK, order.getIsInsertDelek());
        contentValues.put(OrderConst.HOW_MUCH_DELEK_INSERT, order.getHowMuchDelekInsert());
        contentValues.put(OrderConst.HOW_MUCH_NEED_PAY, order.getHowMuchNeedPay());
        contentValues.put(OrderConst.ORDER_NUM, order.getOrderNum());
        return contentValues; }



    public static Order ContentValuesToOrder(ContentValues contentValues) {
        Order order = new Order();
        order.setCustomerNum(contentValues.getAsString(OrderConst.CUSTOMER_NUM));
        order.setModeOfOrder(orderMode.valueOf(contentValues.getAsString(OrderConst.MODE_OF_ORDER)));
        order.setCarNumber(contentValues.getAsString(OrderConst.CAR_NUMBER));
        order.setRentStartDate(contentValues.getAsString(OrderConst.RENT_START_DATE));
        order.setRentEndDate(contentValues.getAsString(OrderConst.RENT_END_DATE));
        order.setKilometresAtStart(contentValues.getAsInteger(OrderConst.KILOMETERES_AT_START));
        order.setKilometresAtEnd(contentValues.getAsInteger(OrderConst.KILOMETERES_AT_END));
        order.setIsInsertDelek(contentValues.getAsBoolean(OrderConst.IS_INSERT_DELEK));
        order.setHowMuchDelekInsert(contentValues.getAsInteger(OrderConst.HOW_MUCH_DELEK_INSERT));
        order.setHowMuchNeedPay(contentValues.getAsInteger(OrderConst.HOW_MUCH_NEED_PAY));
        order.setOrderNum(contentValues.getAsString(OrderConst.ORDER_NUM));
        return order; }





}
