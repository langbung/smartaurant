package com.smartaurant_kmutt.smartaurant.util;

import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Voucher {
    String[] tableEncrypts = {"0123456789/:", "AsVEojuqPzWK"};
    private SimpleDateFormat stringDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private SimpleDateFormat stringTime = new SimpleDateFormat("ss:mm:HH:SSS", Locale.ENGLISH);
    private SimpleDateFormat stringDateTimeEnd = new SimpleDateFormat("ss:mm:HH:SSS dd/MM/yyyy", Locale.ENGLISH);

    public Voucher() {
    }


    public String getVoucher(int sale, int endDateAmount) {
        Calendar calendar = Calendar.getInstance();
//        Date dateNow = calendar.getTime();
        calendar.add(Calendar.DATE, endDateAmount);
        Date dateEnd = calendar.getTime();
        String saleEncrypted = encrypt(String.valueOf(sale));
//        String dateNowEncrypted = encrypt(stringDate.format(dateNow));
        String dateEndEncrypted = encrypt(stringDate.format(dateEnd));
        String timeEndEncrypted = encrypt(stringTime.format(dateEnd));
        return timeEndEncrypted + "-" + dateEndEncrypted + "-" + saleEncrypted;
    }

    public int decryptVoucher(String voucherCode) {
        String[] word = voucherCode.split("-");
        Date dateEnd;
        Date dateNow = Calendar.getInstance().getTime();
        int sale = -1;
        String saleText;
        try {
            saleText = decrypt(word[2]);
        } catch (Throwable e) {
            Log.e("Voucher","error on decrypt saleText");
            return -2;
        }


        try {
            dateEnd = stringDateTimeEnd.parse(decrypt(word[0]) + " " + decrypt(word[1]));
        } catch (ParseException e) {
            MyUtil.print("date end error");
            return -2;
        }

        if (dateEnd.after(dateNow)) {
            sale = Integer.parseInt(saleText);
        }
//        MyUtil.print(stringDateTimeEnd.format(dateEnd));
        return sale;
    }


    private String encrypt(String string) {
        char[] decrypted = tableEncrypts[0].toCharArray();
        char[] encrypted = tableEncrypts[1].toCharArray();
        char[] stringChar = string.toCharArray();

        for (int i = 0; i <= string.length() - 1; i++) {
            for (int j = 0; j <= decrypted.length; j++) {
                if (stringChar[i] == decrypted[j]) {
                    stringChar[i] = encrypted[j];
                    break;
                }
            }
        }
        string = String.valueOf(stringChar);
        return string;
    }

    private String decrypt(String string) {

        char[] decrypted = tableEncrypts[0].toCharArray();
        char[] encrypted = tableEncrypts[1].toCharArray();
        char[] stringChar = string.toCharArray();

        for (int i = 0; i <= string.length() - 1; i++) {
            for (int j = 0; j <= encrypted.length - 1; j++) {
                if (stringChar[i] == encrypted[j]) {
                    stringChar[i] = decrypted[j];
                    break;
                }
            }
        }
        string = String.valueOf(stringChar);
        return string;
    }


}
