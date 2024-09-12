package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CustomCircleView customView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        customView = findViewById(R.id.customCircleView);

        // Clique no componente customizado abre o diálogo de cores
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    private void showColorDialog() {
        // Infla o layout do diálogo
        final View dialogView = getLayoutInflater().inflate(R.layout.color_dialog, null);
        final RadioGroup radioGroupColors = dialogView.findViewById(R.id.radioGroupColors);

        // Cria e exibe o diálogo
        new AlertDialog.Builder(this)
                .setTitle("Selecione a cor")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedColor = Color.BLUE; // Azul por padrão

                        int checkedRadioButtonId = radioGroupColors.getCheckedRadioButtonId();
                        if (checkedRadioButtonId == R.id.radioButtonRed) {
                            selectedColor = Color.RED;
                        } else if (checkedRadioButtonId == R.id.radioButtonGreen) {
                            selectedColor = Color.GREEN;
                        } else if (checkedRadioButtonId == R.id.radioButtonBlue) {
                            selectedColor = Color.BLUE;
                        }

                        // Define a nova cor do círculo
                        customView.setCircleColor(selectedColor);

                        // Salva a cor nas preferências
                        prefs.edit().putInt("circleColor", selectedColor).apply();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
