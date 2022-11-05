package com.example.insturmentshelperapp.interactor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordDrawerInteractor {

    public static Bitmap getChordFromTabs(String tabs, Bitmap clearChord, float scale) {
        List<Integer> valueList = getValueListFromTabs(tabs);
        final int firstFret = minOverOneFromList(valueList);

        Bitmap mutableClearChord = Bitmap.createScaledBitmap(clearChord, (int)(clearChord.getWidth()*scale), (int)(clearChord.getHeight()*scale), true);

        Paint circlePaint = setCirclePaint(scale);
        Paint xSymbolPaint = setXSymbolPaint(scale);
        Paint textPaint = setTextPaint(scale);

        Canvas canvas = new Canvas(mutableClearChord);

        canvas.drawText(String.valueOf(firstFret), 12.5f * scale, 378 * scale, textPaint);
        canvas.drawText(String.valueOf(firstFret + 1), 12.5f * scale, (378 + 371) * scale, textPaint);
        canvas.drawText(String.valueOf(firstFret + 2), 12.5f * scale, (378 + 371 + 371) * scale, textPaint);
        canvas.drawText(String.valueOf(firstFret + 3), 12.5f * scale, (378 + 371 + 371 + 371) * scale, textPaint);

        for (int i = 0; i < valueList.size(); i++) {
            if (valueList.get(i) == -1) {
                canvas.drawLine((120 + i * 265) * scale, 42.5f * scale, (185 + i * 265) * scale, 107.5f * scale, xSymbolPaint);
                canvas.drawLine((185 + i * 265) * scale, 42.5f * scale, (120 + i * 265) * scale, 107.5f * scale, xSymbolPaint);
            } else if (valueList.get(i) != 0) {
                canvas.drawCircle((152.5f + i * 265) * scale, (343 + (valueList.get(i) - firstFret) * 371) * scale, 30 * scale, circlePaint);
            }
        }

        return mutableClearChord;
    }

    private static List<Integer> getValueListFromTabs(String tabs) {
        List<Integer> valueList = new ArrayList<>();
        int firstChar = -1;
        int secondChar = 0;

        while (secondChar >= 0) {
            secondChar = tabs.indexOf(",", firstChar + 1);
            if (secondChar >= 0) {
                valueList.add(Integer.parseInt(tabs.substring(firstChar + 1, secondChar)));
            } else {
                valueList.add(Integer.parseInt(tabs.substring(firstChar + 1)));
            }
            firstChar = secondChar;
        }
        return valueList;
    }

    private static int minOverOneFromList(List<Integer> valueList) {
        if (valueList == null || valueList.size() == 0) {
            return 1;
        }
        List<Integer> findingList = new ArrayList<>(valueList);
        List<Integer> clearFindingList = new ArrayList<>();
        for (Integer listElement : findingList) {
            if (listElement != 0 && listElement != -1) clearFindingList.add(listElement);
        }
        Collections.sort(clearFindingList);
        if(clearFindingList.size() == 0){
            return 1;
        }
        if (clearFindingList.get(0) < 5 && clearFindingList.get(clearFindingList.size() - 1) < 5)
            return 1;
        return clearFindingList.get(0);
    }

    private static Paint setCirclePaint(float scale) {
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        circlePaint.setStrokeWidth(60f * scale);
        return circlePaint;
    }

    private static Paint setXSymbolPaint(float scale) {
        Paint xSymbolPaint = new Paint();
        xSymbolPaint.setAntiAlias(true);
        xSymbolPaint.setStyle(Paint.Style.STROKE);
        xSymbolPaint.setStrokeJoin(Paint.Join.ROUND);
        xSymbolPaint.setStrokeWidth(20f * scale);
        return xSymbolPaint;
    }

    private static Paint setTextPaint(float scale) {
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(125f * scale);
        return textPaint;
    }
}
