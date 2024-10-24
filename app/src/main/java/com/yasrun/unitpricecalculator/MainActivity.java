package com.yasrun.unitpricecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText txtPriceWithoutTax = null;
    private EditText txtPriceTaxIncluded = null;
    private EditText txtUnits = null;
    private EditText txtUnitPriceWithoutTax = null;
    private EditText txtUnitPriceTaxIncluded = null;
    private Button btnCalcPrice = null;
    private Button btnCalcUnitPrice = null;
    private TextView txtCalcResult = null;
    private Button btnClear = null;
    private Spinner cmbTax = null;
    private Spinner cmbRounding = null;

    /**
     * 画面作成処理
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControls();
        setEvents();
        clear();
    }

    /**
     * 画面に載っているオブジェクトを取得します。
     */
    private void getControls() {
        txtPriceWithoutTax = findViewById(R.id.txtPriceWithoutTax);
        txtPriceTaxIncluded = findViewById(R.id.txtPriceTaxIncluded);
        txtUnits = findViewById(R.id.txtUnits);
        txtUnitPriceWithoutTax = findViewById(R.id.txtUnitPriceWithoutTax);
        txtUnitPriceTaxIncluded = findViewById(R.id.txtUnitPriceTaxIncluded);
        txtCalcResult = findViewById(R.id.txtCalcResult);
        btnCalcPrice = findViewById(R.id.btnCalcPrice);
        btnCalcUnitPrice = findViewById(R.id.btnCalcUnitPrice);
        btnClear = findViewById(R.id.btnClear);
        cmbTax = findViewById(R.id.cmbTax);
        cmbRounding = findViewById(R.id.cmbRounding);
    }

    /**
     * イベントハンドラを設定します。
     */
    private void setEvents() {
        /**
         * 税抜き価格欄フォーカス変化イベント
         */
        txtPriceWithoutTax.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    calc(v);
                }
            }
        });

        /**
         * 税込み抜き価格欄フォーカス変化イベント
         */
        txtPriceTaxIncluded.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    calc(v);
                }
            }
        });

        /**
         * 税抜き単価欄フォーカス変化イベント
         */
        txtUnitPriceWithoutTax.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    calc(v);
                }
            }
        });

        /**
         * 税込み単価欄フォーカス変化イベント
         */
        txtUnitPriceTaxIncluded.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    calc(v);
                }
            }
        });

        /**
         * 価格計算ボタン押下イベント
         */
        btnCalcPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                txtCalcResult.requestFocus();
                calcPrice();
            }
        });

        /**
         * 単価計算ボタン押下イベント
         */
        btnCalcUnitPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                txtCalcResult.requestFocus();
                calcUnitPrice();
            }
        });

        /**
         * クリアボタン押下イベント
         */
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                clear();
            }
        });

//        cmbTax.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("_test_", String.format("%d %ld", position, id));
//            }
//        });
    }

    /**
     * 税込み・税抜きの価格を計算
     * @param v フォーカスが変化した入力欄
     */
    private void calc(View v) {
        Log.d("_test_", String.format("%d", cmbTax.getSelectedItemId()));
        if (cmbTax.getSelectedItemId() == 0) {
            CalcUtil.setTax(0.08);
        } else {
            CalcUtil.setTax(0.1);
        }
        CalcUtil.setRoundingStyle((int)cmbRounding.getSelectedItemId());

        String value = ((EditText)v).getText().toString();
        String result;

        int id = v.getId();
        if (id == R.id.txtPriceWithoutTax) {
            result = CalcUtil.calcPriceTaxIncluded(value);
            setAnswer(txtPriceTaxIncluded, result);
        } else if (id == R.id.txtPriceTaxIncluded) {
            result = CalcUtil.calcPriceWithoutTax(value);
            setAnswer(txtPriceWithoutTax, result);
        } else if (id == R.id.txtUnitPriceWithoutTax) {
            result = CalcUtil.calcPriceTaxIncluded(value);
            setAnswer(txtUnitPriceTaxIncluded, result);
        } else if (id == R.id.txtUnitPriceTaxIncluded) {
            result = CalcUtil.calcPriceWithoutTax(value);
            setAnswer(txtUnitPriceWithoutTax, result);
        }
    }

    /**
     * クリア
     */
    private void clear() {
        clear(txtPriceWithoutTax, txtPriceTaxIncluded, txtUnits, txtUnitPriceWithoutTax, txtUnitPriceTaxIncluded, txtCalcResult);
    }

    /**
     * クリア
     */
    private void clear(TextView ... texts) {
        for(TextView t : texts) {
            t.setText("");
            t.setHint("");
        }
    }

    /**
     * 価格計算
     */
    private void calcPrice() {
        View view = this.getCurrentFocus();
        if (view != null) {
            calc(view);
        }
        String price1 = txtUnitPriceWithoutTax.getText().toString();
        String price2 = txtUnitPriceTaxIncluded.getText().toString();
        String units = txtUnits.getText().toString();
        String result1 = CalcUtil.calcPrice(price1, units);
        String result2 = CalcUtil.calcPrice(price2, units);
        setAnswer(txtPriceWithoutTax, result1);
        setAnswer(txtPriceTaxIncluded, result2);
        addHistory(price1, price2, "x", units, result1, result2);
    }

    /**
     * 単価計算
     */
    private void calcUnitPrice() {
        View view = this.getCurrentFocus();
        if (view != null) {
            calc(view);
        }
        String price1 = txtPriceWithoutTax.getText().toString();
        String price2 = txtPriceTaxIncluded.getText().toString();
        String units = txtUnits.getText().toString();
        String result1 = CalcUtil.calcUnitPrice(price1, units);
        String result2 = CalcUtil.calcUnitPrice(price2, units);
        setAnswer(txtUnitPriceWithoutTax, result1);
        setAnswer(txtUnitPriceTaxIncluded, result2);
        addHistory(price1, price2, "/", units, result1, result2);
    }

    /**
     * 計算結果を表示します。
     * @param editText 設定対象コントロール
     * @param answer 計算結果
     */
    private void setAnswer(EditText editText, String answer) {
        if (answer == null) {
            editText.setText("");
            editText.setHint(getResources().getString(R.string.msg_error));
        } else {
            editText.setText(answer);
            editText.setHint("");
        }
    }

    /**
     * 計算結果の履歴を表示します。
     * @param v1 値1
     * @param v2 値2
     * @param operator 演算子
     * @param v3 値3
     * @param answer1 計算結果1
     * @param answer2 計算結果2
     */
    private void addHistory(String v1, String v2, String operator, String v3, String answer1, String answer2) {
        if (answer1 != null || answer2 != null) {
            String line = String.format("%s %s %s = %s", v1, operator, v3, answer1)
                     + String.format(" --- %s %s %s = %s", v2, operator, v3, answer2);
            insert(line);
        }
    }

    /**
     * 表示欄の先頭に1行追加する
     * @param line 追加する行データ
     */
    private void insert(String line) {
        txtCalcResult.setText(line + "\n" + txtCalcResult.getText().toString());
    }

    /**
     * 現在フォーカスされているViewのキーボードをOFFにする
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
