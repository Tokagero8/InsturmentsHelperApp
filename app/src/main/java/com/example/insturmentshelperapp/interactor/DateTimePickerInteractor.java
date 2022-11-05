package com.example.insturmentshelperapp.interactor;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateTimePickerInteractor extends DialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    String nextLessonName;
    Calendar mainCalendar;
    Context context;

    public DateTimePickerInteractor(String nextLessonName, Context context){
        super();
        this.nextLessonName = nextLessonName;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        mainCalendar = Calendar.getInstance();
        int year = mainCalendar.get(Calendar.YEAR);
        int month = mainCalendar.get(Calendar.MONTH);
        int dayOfMonth = mainCalendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mainCalendar.set(Calendar.YEAR, year);
        mainCalendar.set(Calendar.MONTH, month);
        mainCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        int hour = mainCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mainCalendar.get(Calendar.MINUTE);

        new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity())).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mainCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mainCalendar.set(Calendar.MINUTE, minute);
        mainCalendar.set(Calendar.SECOND, 0);

        startNotification(mainCalendar, context);
    }

    private void startNotification(Calendar calendar, Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Title", "Next Lesson");
        intent.putExtra("Text", "Your next lesson has just started: " + nextLessonName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
