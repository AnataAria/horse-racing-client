package com.theanimegroup.horse_racing_client.utils;

import com.theanimegroup.horse_racing_client.model.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataUtils {
    private static DataUtils instance;
    private Account currentUser;
    private List<Account> databaseAccount;
    public DataUtils () {
        if (databaseAccount == null) {
            databaseAccount = new ArrayList<>();
        }
    }
    public static DataUtils getInstance () {
        if (instance == null) {
            instance = new DataUtils();
        }
        return instance;
    }

    public void setCurrentUser(Account user) {
        this.currentUser = user;
    }

    public Account getCurrentUser () {
        return this.currentUser;
    }

    public  Collection<Account> getDatabaseAccount () {
        return databaseAccount;
    }
}
