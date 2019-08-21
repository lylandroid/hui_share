package com.yuepointbusiness.ui.main.contract;

import com.yuepointbusiness.bean.NewsChannelTable;
import com.yuepointbusiness.common.base.BaseModel;
import com.yuepointbusiness.common.base.BasePresenter;
import com.yuepointbusiness.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * des:
 * Created by xsf
 * on 2016.09.11:53
 */
public interface NewsMainContract {

    interface Model extends BaseModel {
        Observable< List<NewsChannelTable> > lodeMineNewsChannels();
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeMineChannelsRequest();
    }
}
