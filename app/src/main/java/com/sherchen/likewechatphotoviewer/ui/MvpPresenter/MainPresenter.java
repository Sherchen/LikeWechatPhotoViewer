package com.sherchen.likewechatphotoviewer.ui.MvpPresenter;

import android.app.Activity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.sherchen.likewechatphotoviewer.R;
import com.sherchen.likewechatphotoviewer.entity.PicInfo;
import com.sherchen.likewechatphotoviewer.ui.MvpView.MainView;
import com.sherchen.likewechatphotoviewer.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by dave on 2017/1/10.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {
    private int imageContentSize;//内容区大小
    private int multiImageSize;
    private int paddingLeft;
    private int paddingRight;
    private int gridSpace;

    public MainPresenter(Activity activity) {
        super(activity);
        paddingLeft = activity.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        paddingRight = activity.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        gridSpace = activity.getResources().getDimensionPixelSize(R.dimen.activity_grid_space);
        imageContentSize = ScreenUtils.getScreenWidth(activity) - paddingLeft - paddingRight - 2 * gridSpace;
        multiImageSize = imageContentSize / 3;
    }

    public void uiShowImages() {
        if(isViewAttached()) {
            getView().showImages(getDummyPics());
        }
    }

    private ArrayList<PicInfo> getDummyPics(){
        ArrayList<PicInfo> list = new ArrayList<>();
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0064.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0064.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0071.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0071.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0074.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0074.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0115.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0115.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0099.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0099.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0149.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0149.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        list.add(new PicInfo("http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0150.JPG", "6000x4000",
                "http://ojk6t1zg8.bkt.clouddn.com/sherchen_DSC_0150.JPG?imageView2/1/w/" + multiImageSize + "/interlace/1"
                ));
        return list;
    }

}
