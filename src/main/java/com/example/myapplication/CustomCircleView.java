package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircleView extends View {
    private int circleColor = Color.BLUE;  // Cor padrão é azul
    private Paint paint = new Paint();

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Recupera cor das preferências
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        circleColor = prefs.getInt("circleColor", Color.BLUE);

        // Se não houver cor nas preferências, tenta pegar do XML
        if (attrs != null) {
            int[] attrsArray = new int[] {
                    android.R.attr.color // ID do atributo de cor
            };
            circleColor = context.obtainStyledAttributes(attrs, attrsArray).getColor(0, circleColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Configura a cor e estilo do pincel
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.FILL);

        // Desenha o círculo principal
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) / 2;
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Desenha círculos concêntricos
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 1; i <= 3; i++) {
            canvas.drawCircle(centerX, centerY, radius * i / 3, paint);
        }

        // Desenha as retas ortogonais
        canvas.drawLine(centerX, 0, centerX, getHeight(), paint);
        canvas.drawLine(0, centerY, getWidth(), centerY, paint);
    }

    // Método para atualizar a cor
    public void setCircleColor(int color) {
        this.circleColor = color;
        invalidate(); // Redesenha a view com a nova cor
    }

    // Método para obter a cor atual do círculo
    public int getCircleColor() {
        return circleColor;
    }
}
