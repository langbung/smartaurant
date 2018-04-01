package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.TableListDao;

/**
 * Created by LB on 30/3/2561.
 */

public class TableManager {
    TableListDao tableDao;

    public TableManager() {
    }

    public TableListDao getTableDao() {
        return tableDao;
    }

    public void setTableDao(TableListDao tableDao) {
        this.tableDao = tableDao;
    }
}
