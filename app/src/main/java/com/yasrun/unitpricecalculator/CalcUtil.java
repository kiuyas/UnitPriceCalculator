package com.yasrun.unitpricecalculator;

import android.content.res.Resources;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;

public class CalcUtil {

    /** 税込み価格計算係数 */
    public static final double WITH_TAX = 1.08;

    /**
     * 税込み価格の計算
     * @param price 税抜き価格
     * @return 税込み価格
     */
    public static String calcPriceTaxIncluded(String price) {
        try {
            return toString(Double.parseDouble(price) * WITH_TAX);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    /**
     * 税抜き価格の計算
     * @param price 税込み価格
     * @return 税抜き価格
     */
    public static String calcPriceWithoutTax(String price) {
        try {
            return toString(Double.parseDouble(price)/ WITH_TAX);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    /**
     * 価格計算
     * @param unitPrice 単価
     * @param units 数量
     * @return 価格
     */
    public static String calcPrice(String unitPrice, String units) {
        String result = null;

        try {
            double price0 = Double.parseDouble(unitPrice);
            double units0 = Double.parseDouble(units);
            result = toString( price0 * units0);
        } catch(NumberFormatException e) {
            Log.e("_test_", e.getMessage(), e);
            result = null;
        }

        return result;
    }

    /**
     * 単価計算
     * @param price 価格
     * @param units 数量
     * @return 単価
     */
    public static String calcUnitPrice(String price, String units) {
        String result = null;

        try {
            double price0 = Double.parseDouble(price);
            double units0 = Double.parseDouble(units);
            result = toString(price0 / units0);
        } catch(NumberFormatException e) {
            Log.e("_test_", e.getMessage(), e);
            result = null;
        }

        return result;
    }

    /**
     * 数値を文字列化します。
     * ※小数点以下が .0 の場合は小数点以下がない状態にします。
     * @param value 数値
     * @return 文字列化された数値
     */
    public static String toString(double value) {
        String result = new DecimalFormat("#.0").format(value);
        if (result.endsWith(".0")) {
            result = Integer.toString((int)value);
        }
        return result;
    }
}
