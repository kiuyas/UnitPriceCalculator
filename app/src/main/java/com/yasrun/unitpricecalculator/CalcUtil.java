package com.yasrun.unitpricecalculator;

import android.util.Log;

import java.text.DecimalFormat;

public class CalcUtil {

    /** 既定の消費税率 */
    public static final double DEFAULT_TAX = 0.08;

    /** 税込み価格計算係数 */
    public static double withTax = 1 + DEFAULT_TAX;

    /** 端数処理方式 0:切上げ, 1:四捨五入, 2:切捨て, 3:そのまま */
    public static int roundingStyle = 0;

    /**
     * 消費税の設定
     * @param tax 消費税率
     */
    public static void setTax(double tax) {
        withTax = 1 + tax;
    }

    /**
     * 端数処理方式の設定
     * @param _roundingStyle 消費税率
     */
    public static void setRoundingStyle(int _roundingStyle) {
        roundingStyle = _roundingStyle;
    }

    /**
     * 税込み価格の計算
     * @param price 税抜き価格
     * @return 税込み価格
     */
    public static String calcPriceTaxIncluded(String price) {
        try {
            return toString(round(Double.parseDouble(price) * withTax));
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
            return toString(round(Double.parseDouble(price)) / withTax);
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
            result = toString(round( price0 * units0));
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
            result = toString(round(price0 / units0));
        } catch(NumberFormatException e) {
            Log.e("_test_", e.getMessage(), e);
            result = null;
        }

        return result;
    }

    /**
     * 端数の処理を行います。
     * @param value 元の値
     * @return 端数処理された値
     */
    public static double round(double value) {
        switch(roundingStyle) {
            case 0:
                value = Math.ceil(value);
                break;
            case 1:
                value = Math.round(value);
                break;
            case 2:
                value = Math.floor(value);
                break;
        }
        return value;
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
