package com.gzeinnumer.mylibnfcreaderexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.mylibnfcreader.helper.EndNFCTagging;
import com.gzeinnumer.mylibnfcreader.helper.NFCCallBack;
import com.gzeinnumer.mylibnfcreader.helper.NFCErrorCallBack;
import com.gzeinnumer.mylibnfcreader.helper.NFCTools;
import com.gzeinnumer.mylibnfcreader.lib.StartNFCTagging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_run_nfc_type_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NFCTools.checkNFCHardware(MainActivity.this)) {
                    Toast.makeText(getApplicationContext(), "NFC Support", Toast.LENGTH_SHORT).show();
                    new StartNFCTagging(MainActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(), "NFC Not Support", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_run_nfc_type_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StartNFCTagging(MainActivity.this, new NFCErrorCallBack() {
                    @Override
                    public void onNotSupport(String msg) {
                        //if Device not Support NFC
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        new EndNFCTagging(intent, getSupportFragmentManager()).observer(new NFCCallBack() {
            @Override
            public void callBack(String idHex, String idReversedHex, String idDec, String idReversedDec) {
                String str = "";
                str = str + "onNewIntent: Hexadecimal Code          " + idHex;
                str = str + "onNewIntent: Reversed Hexadecimal Code " + idReversedHex;
                str = str + "onNewIntent: Decimal Code              " + idDec;
                str = str + "onNewIntent: Reversed Decimal Code     " + idReversedDec;

                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}