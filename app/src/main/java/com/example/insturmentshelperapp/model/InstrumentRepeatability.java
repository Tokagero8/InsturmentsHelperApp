package com.example.insturmentshelperapp.model;

public class InstrumentRepeatability {
    private static int [] repeatability = {-1, 1, 2, 3, 4, 5, 6, 7};

    public static int getRepeatability(int position){
        return repeatability[position];
    }

    public static int[] getRepeatabilityList(){
        return repeatability;
    }
}
