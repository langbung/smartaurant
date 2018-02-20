package com.smartaurant_kmutt.smartaurant.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AccountManager {


    private Context mContext;

    public AccountManager() {
        mContext = Contextor.getInstance().getContext();
    }

}
