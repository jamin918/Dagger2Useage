package jm.study.dagger2demo.injector.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jm.study.dagger2demo.http.service.DouBanService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author PRDEV
 *         Created on 2018-8-16.
 */

@Module
public class DouBanHttpModule extends BaseHttpModule {

    @Singleton
    @Provides
    Retrofit provideDouBanRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, DouBanService.API_DOUBAN);
    }

    @Singleton
    @Provides
    DouBanService provideDouBanService(Retrofit retrofit) {
        return retrofit.create(DouBanService.class);
    }
}
