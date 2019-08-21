package com.yuepointbusiness.ui.news.model;

import com.yuepointbusiness.api.Api;
import com.yuepointbusiness.api.ApiConstants;
import com.yuepointbusiness.api.HostType;
import com.yuepointbusiness.bean.NewsSummary;
import com.yuepointbusiness.ui.news.contract.NewsListContract;
import com.yuepointbusiness.common.baserx.RxSchedulers;
import com.yuepointbusiness.common.commonutils.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 */
public class NewsListModel implements NewsListContract.Model {
    /**
     * 获取新闻列表
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    @Override
    public Observable<List<NewsSummary>> getNewsListData(final String type, final String id, final int startPage) {
       return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(),type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(map.get("北京"));
                        }
                        return Observable.from(map.get(id));
                    }
                })
                //转化时间
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = TimeUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<List<NewsSummary>>io_main());
    }
}
