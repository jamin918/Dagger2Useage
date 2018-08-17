package jm.study.dagger2demo.injector.component.activity;


import javax.inject.Singleton;

import dagger.Component;
import jm.study.dagger2demo.MainActivity;
import jm.study.dagger2demo.injector.module.activity.DouBanMovieTopModule;
import jm.study.dagger2demo.injector.module.http.DouBanHttpModule;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { DouBanHttpModule.class, DouBanMovieTopModule.class})
public interface DoubanMovieTopComponent {
    void injectDoubanMovieTop(MainActivity activity);
}
