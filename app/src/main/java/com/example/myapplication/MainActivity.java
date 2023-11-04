package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txt_sayimi_goster;
    private Button btn_sayimi_baslat, btn_sayimi_durdur, btn_sayimi_duraklat;
    private EditText editTextDakika; // Yeni ekledik
    private CountDownTimer geri_sayim_sayaci;
    private long kalanSure = 100000; // Varsayılan süre 100 saniye

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_sayimi_goster = findViewById(R.id.sure);
        btn_sayimi_baslat = findViewById(R.id.baslat);
        btn_sayimi_durdur = findViewById(R.id.duraklat);
        btn_sayimi_duraklat = findViewById(R.id.durdurSifirla);
        editTextDakika = findViewById(R.id.sureGir); // EditText'i tanımladık

        txt_sayimi_goster.setText(formatSure(kalanSure));

        btn_sayimi_baslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText'ten girilen dakika miktarını alıyoruz
                String dakikaStr = editTextDakika.getText().toString();
                if (!dakikaStr.isEmpty()) {
                    int dakika = Integer.parseInt(dakikaStr);
                    kalanSure = dakika * 60 * 1000; // Dakikayı milisaniyeye çeviriyoruz
                    baslatGeriSayim();
                }
            }
        });

        btn_sayimi_durdur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duraklatGeriSayim();
            }
        });

        btn_sayimi_duraklat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sifirlaGeriSayim();
            }
        });
    }

    private void baslatGeriSayim() {
        geri_sayim_sayaci = new CountDownTimer(kalanSure, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                kalanSure = millisUntilFinished;
                txt_sayimi_goster.setText(formatSure(kalanSure));
            }

            @Override
            public void onFinish() {
                txt_sayimi_goster.setText("Süre Bitti!");
            }
        }.start();
    }

    private void duraklatGeriSayim() {
        if (geri_sayim_sayaci != null) {
            geri_sayim_sayaci.cancel();
        }
    }

    private void sifirlaGeriSayim() {
        if (geri_sayim_sayaci != null) {
            geri_sayim_sayaci.cancel();
        }
        kalanSure = 0; // Geri sayım süresini sıfırlıyoruz
        txt_sayimi_goster.setText(formatSure(kalanSure));
    }

    private String formatSure(long millis) {
        int saniye = (int) (millis / 1000);
        int dakika = saniye / 60;
        saniye = saniye % 60;
        return String.format("%02d:%02d", dakika, saniye);
    }
}
