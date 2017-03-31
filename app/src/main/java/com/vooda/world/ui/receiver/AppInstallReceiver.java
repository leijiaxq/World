package com.vooda.world.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;

/**
 * Created by leijiaxq
 * Data       2017/1/16 15:22
 * Describe   用于监听应用的安装
 */


public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBusUtil.postInfoEvent(Contants.FIVE, new EventMessage(packageName));
        }
    }
}
