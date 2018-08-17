package jm.study.dagger2demo.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import jm.study.dagger2demo.BuildConfig;

/**
 * @author jamin
 *         Created on 2018/6/22.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        // init Logger
        Logger.addLogAdapter(new AndroidLogAdapter() {

            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                PrettyFormatStrategy
                        .newBuilder()
                        .tag("Logger")
                        .showThreadInfo(false)
                        .build()
                        .log(priority, tag, message);
            }
        });
    }

    public static Context getAppContext() {
        return mContext;
    }

}
