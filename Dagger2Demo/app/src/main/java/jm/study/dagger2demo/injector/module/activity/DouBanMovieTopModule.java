package jm.study.dagger2demo.injector.module.activity;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jm.study.dagger2demo.adapter.MovieTopAdapter;
import jm.study.dagger2demo.bean.HotMovieBean;

/**
 * @author PRDEV
 *         Created on 2018-8-16.
 */

@Module
public class DouBanMovieTopModule {

    @Provides
    @Singleton
    public MovieTopAdapter provideAdapter() {
        return new MovieTopAdapter(new ArrayList<HotMovieBean.SubjectsBean>());
    }
}
