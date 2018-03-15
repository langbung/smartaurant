package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.StaffListDao;

/**
 * Created by LB on 16/3/2561.
 */

public class StaffManager {
    StaffListDao staffDao;

    public StaffManager(StaffListDao staffList) {
        this.staffDao = staffList;
    }

    public StaffManager(){

    }

    public StaffListDao getStaffDao() {
        return staffDao;
    }

    public void setStaffDao(StaffListDao staffDao) {
        this.staffDao = staffDao;
    }
}
