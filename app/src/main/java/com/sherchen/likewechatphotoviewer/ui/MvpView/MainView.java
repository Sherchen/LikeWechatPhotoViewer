package com.sherchen.likewechatphotoviewer.ui.MvpView;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.sherchen.likewechatphotoviewer.entity.PicInfo;

import java.util.ArrayList;

/**
 * Created by dave on 2017/1/10.
 */

public interface MainView extends MvpView{
    void showImages(ArrayList<PicInfo> picInfos);
}
