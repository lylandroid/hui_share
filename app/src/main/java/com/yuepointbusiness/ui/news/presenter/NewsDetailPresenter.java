package com.yuepointbusiness.ui.news.presenter;

import com.yuepointbusiness.R;
import com.yuepointbusiness.bean.NewsDetail;
import com.yuepointbusiness.ui.news.contract.NewsDetailContract;
import com.yuepointbusiness.common.baserx.RxSubscriber;
import com.yuepointbusiness.common.commonutils.ToastUitl;

/**
 * des:新闻详情
 * Created by xsf
 * on 2016.09.17:08
 */
public class NewsDetailPresenter extends NewsDetailContract.Presenter{
    @Override
    public void getOneNewsDataRequest(String postId) {
        mRxManage.add(mModel.getOneNewsData(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
            mView.returnOneNewsData(newsDetail);
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(message, R.drawable.ic_wrong);
            }
        }));
    }
}
