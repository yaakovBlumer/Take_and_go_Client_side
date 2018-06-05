package com.example.yaakovblumer.takego_clientside.model.backend;

import com.example.yaakovblumer.takego_clientside.model.datasource.ListsDataSource;
import com.example.yaakovblumer.takego_clientside.model.datasource.MySQL_DB_manager;

public class FactoryMethod
{
    private static final FactoryMethod ourInstance = new FactoryMethod();

    public static FactoryMethod getInstance() {
        return ourInstance;
    }

    private FactoryMethod() {   }

///////////////////////////////

    private static DataSource dataSourceInstance = new ListsDataSource();

    private static DataSource myDataSourceInstance = new MySQL_DB_manager();

////////////////////////////////


    //   public static DataSource getDataSource() {return  myDataSourceInstance; }


    public enum Type {
        MySQL,
        Lists;
    }

    public static  DataSource getDataSource(Type type){
        switch (type)
        {
            case Lists:
                return dataSourceInstance;
            case MySQL:
                return new MySQL_DB_manager();
        }
        return myDataSourceInstance;
    }


}
