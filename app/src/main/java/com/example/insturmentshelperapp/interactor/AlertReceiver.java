package com.example.insturmentshelperapp.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("Title");
        String text = bundle.getString("Text");
        NotificationInteractor notificationInteractor = new NotificationInteractor(context);
        NotificationCompat.Builder builder = notificationInteractor.getChannelNotification(title, text);
        notificationInteractor.getManager().notify(1, builder.build());
    }
}
