package com.smartaurant_kmutt.smartaurant.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.dao.VoucherItem;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Voucher {
    private String[] tableEncrypts = {"0123456789/:", "AsVEojuqPzWK"};
    //    private SimpleDateFormat stringDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//    private SimpleDateFormat stringTime = new SimpleDateFormat("SSS:ss:mm:HH", Locale.ENGLISH);
//    private SimpleDateFormat stringDateTimeEnd = new SimpleDateFormat("SSS:ss:mm:HH dd/MM/yyyy", Locale.ENGLISH);
    private SimpleDateFormat stringDateTimeEnd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ", Locale.ENGLISH);
    private SimpleDateFormat id = new SimpleDateFormat("SssmmSSS", Locale.ENGLISH);

    public Voucher() {
    }


    public VoucherItem getVoucher(int sale, int endDateAmount) {
        Calendar calendar = Calendar.getInstance();
//        Date dateNow = calendar.getTime();
        calendar.add(Calendar.DATE, endDateAmount);
        Date dateEnd = calendar.getTime();
//        String saleEncrypted = encrypt(String.valueOf(sale));
//        String dateNowEncrypted = encrypt(stringDate.format(dateNow));
        VoucherItem voucherItem = new VoucherItem();
        voucherItem.setAlreadyUsed(false);
        voucherItem.setDateEnd(stringDateTimeEnd.format(dateEnd));
        voucherItem.setDiscount(sale);
        Random random = new Random();
        int ran = random.nextInt(99999999) + 1;
        DecimalFormat df = new DecimalFormat("00000000");
        voucherItem.setId(df.format(ran));
        return voucherItem;
    }

    public void updateVoucher(VoucherItem voucherItem) {
        DatabaseReference databaseReference = UtilDatabase.getDatabase().child("voucher/"+voucherItem.getId());
        databaseReference.setValue(voucherItem);
    }

    public void checkVoucherCode(String voucherId, final VoucherListener voucherListener) {
        final DatabaseReference databaseReference = UtilDatabase.getDatabase().child("voucher/" + voucherId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    VoucherItem voucherItem = dataSnapshot.getValue(VoucherItem.class);
                    voucherListener.onGetVoucher(voucherItem);
                }
                else{
                    voucherListener.onNoVoucher();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //    public int decryptVoucher(String voucherCode) {
//        String[] word = voucherCode.split("-");
//        Date dateEnd;
//        Date dateNow = Calendar.getInstance().getTime();
//        int sale = -1;
//        String saleText;
//        try {
//            saleText = decrypt(word[1]);
//        } catch (Throwable e) {
//            Log.e("Voucher", "error on decrypt saleText");
//            return -2;
//        }
//
//
//        try {
//            dateEnd = stringDateTimeEnd.parse(decrypt(word[0]));
//        } catch (ParseException e) {
//            MyUtil.print("date end error");
//            return -2;
//        }
//
//        if (dateEnd.after(dateNow)) {
//            sale = Integer.parseInt(saleText);
//        }
////        MyUtil.print(stringDateTimeEnd.format(dateEnd));
//        return sale;
//    }
//
//
    private String encrypt(String string) {
        char[] decrypted = tableEncrypts[0].toCharArray();
        char[] encrypted = tableEncrypts[1].toCharArray();
        char[] stringChar = string.toCharArray();

        for (int i = 0; i <= string.length() - 1; i++) {
            for (int j = 0; j <= decrypted.length - 1; j++) {
                if (stringChar[i] == decrypted[j]) {
                    stringChar[i] = encrypted[j];
                    break;
                }
            }
        }
        string = String.valueOf(stringChar);
        return string;
    }

    //
//    private String decrypt(String string) {
//
//        char[] decrypted = tableEncrypts[0].toCharArray();
//        char[] encrypted = tableEncrypts[1].toCharArray();
//        char[] stringChar = string.toCharArray();
//
//        for (int i = 0; i <= string.length() - 1; i++) {
//            for (int j = 0; j <= encrypted.length - 1; j++) {
//                if (stringChar[i] == encrypted[j]) {
//                    stringChar[i] = decrypted[j];
//                    break;
//                }
//            }
//        }
//        string = String.valueOf(stringChar);
//        return string;
//    }
    public interface VoucherListener {
        void onGetVoucher(VoucherItem voucherItem);
        void onNoVoucher();
    }

}
