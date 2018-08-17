package jm.study.dagger2demo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import jm.study.dagger2demo.adapter.MovieTopAdapter;
import jm.study.dagger2demo.base.BaseSchedulerTransformer;
import jm.study.dagger2demo.base.BaseSubscriber;
import jm.study.dagger2demo.bean.HotMovieBean;
import jm.study.dagger2demo.http.callback.RequestCallBack;
import jm.study.dagger2demo.http.service.DouBanService;
import jm.study.dagger2demo.injector.component.activity.DaggerDoubanMovieTopComponent;
import jm.study.dagger2demo.injector.module.activity.DouBanMovieTopModule;
import jm.study.dagger2demo.injector.module.http.DouBanHttpModule;
import jm.study.dagger2demo.view.EasyLoadMoreView;

public class MainActivity extends AppCompatActivity implements MovieTopAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    DouBanService mService;

    @Inject
    MovieTopAdapter mAdapter;

    private SwipeRefreshLayout mSwipeLayout;

    private int mPageSize = 20;
    private int mTotalSize = 250;
    private int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeLayout = findViewById(R.id.swipeLayout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        DaggerDoubanMovieTopComponent.builder()
                .douBanHttpModule(new DouBanHttpModule())
                .douBanMovieTopModule(new DouBanMovieTopModule())
                .build().injectDoubanMovieTop(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setLoadMoreView(new EasyLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, recyclerView);

        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setOnRefreshListener(this);

        loadData(true);
    }

    private void loadData(boolean refresh) {
        if (refresh) {
            mCounter = 0;
        }
        fetchMovieTop250(mCounter, mPageSize);
    }

    public void fetchMovieTop250(final int start, int count) {

        mService.fetchMovieTop250(start, count)
                .compose(new BaseSchedulerTransformer<HotMovieBean>())
                .subscribe(new BaseSubscriber<>(new RequestCallBack<HotMovieBean>() {
                    @Override
                    public void onBefore() {
                    }

                    @Override
                    public void onComplete() {
                        mSwipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        mSwipeLayout.setRefreshing(false);
                        mAdapter.loadMoreFail();
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(HotMovieBean data) {
                        List<HotMovieBean.SubjectsBean> list = data.getSubjects();
                        mCounter += list.size();
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                    }
                }));
    }

    @Override
    public void onLoadMoreRequested() {
        if (mCounter >= mTotalSize) {
            mAdapter.loadMoreEnd();
        } else {
            loadData(false);
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onItemClickListener(HotMovieBean.SubjectsBean positionData, View view) {
        Toast.makeText(this, "电影名称："+positionData.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
