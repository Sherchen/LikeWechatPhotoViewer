package com.sherchen.likewechatphotoviewer.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.sherchen.likewechatphotoviewer.R;
import com.sherchen.likewechatphotoviewer.adapter.ListBaseAdapter;
import com.sherchen.likewechatphotoviewer.entity.PicInfo;
import com.sherchen.likewechatphotoviewer.ui.MvpPresenter.MainPresenter;
import com.sherchen.likewechatphotoviewer.ui.MvpView.MainView;
import com.sherchen.likewechatphotoviewer.utils.GlideUtils;
import com.sherchen.likewechatphotoviewer.utils.ObjectUtils;
import com.sherchen.likewechatphotoviewer.utils.SceneHelp;
import com.sherchen.likewechatphotoviewer.views.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.pager_picviewer)
    HackyViewPager pagerPicviewer;
    @BindView(R.id.iv_expanded)
    ImageView ivExpanded;
    @BindView(R.id.v_container)
    View vContainer;

    private ZoomInOutHelper zoomInOutHelper;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        zoomInOutHelper = new ZoomInOutHelper(this);
        presenter.uiShowImages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    private List<PicInfo> picInfos;

    @Override
    public void showImages(final ArrayList<PicInfo> picInfos) {
        this.picInfos = picInfos;
        gridview.setAdapter(new ImagePreviewItemAdapter(this, picInfos));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zoomInOutHelper.playZoomInAnim(view, picInfos, position, null);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBackPressed() {
        if(pagerPicviewer.getVisibility() == View.VISIBLE) {
            zoomInOutHelper.playZoomOutAnim();
            return;
        }
        super.onBackPressed();
    }

    private class ImagePreviewItemAdapter extends ListBaseAdapter<PicInfo, ImagePreviewItemHolder> {

        public ImagePreviewItemAdapter(Context context, List<PicInfo> list) {
            super(context, R.layout.pic_preview_item, list);
        }

        @Override
        public void bindView(ImagePreviewItemHolder holder, View convertView) {

        }

        @Override
        public ImagePreviewItemHolder getViewHolder(View content) {
            return new ImagePreviewItemHolder(content);
        }

        @Override
        public void setViewContent(ImagePreviewItemHolder holder, PicInfo picInfo, View convertView, int position) {
            GlideUtils.displayImageCenterCrop(m_Context, holder.rivPreview, picInfo.getPic_url_thum(), R.drawable.ic_placeholder_rect_big);
        }
    }

    class ImagePreviewItemHolder {
        @BindView(R.id.riv_post_preview)
        ImageView rivPreview;

        ImagePreviewItemHolder(View content){
            ButterKnife.bind(this, content);
        }
    }

    private class ZoomInOutHelper {
        private SceneHelp sceneHelp;
        public ZoomInOutHelper(Activity activity) {
            sceneHelp = new SceneHelp(activity);
        }

        /**
         * 从小图到大图
         * @param thumbView 缩略图
         * @param clickPicInfos 所有图片信息
         * @param position 图片位置索引
         * @param l 动画结束监听。
         */
        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        private void playZoomInAnim(View thumbView, ArrayList<PicInfo> clickPicInfos, int position, final SceneHelp.AnimFinishListener l){
            pagerPicviewer.setVisibility(View.GONE);
            pagerPicviewer.setVisibility(View.VISIBLE);
            pagerPicviewer.setAdapter(new PicInfoPagerAdapter(MainActivity.this, clickPicInfos));
            pagerPicviewer.setCurrentItem(position, true);
            ivExpanded.setVisibility(View.VISIBLE);
            ivExpanded.setBackgroundColor(Color.BLACK);
            sceneHelp.showExpandedView(ivExpanded, clickPicInfos.get(position));
            sceneHelp.zoomThumb2Expaned(vContainer, thumbView, ivExpanded, new SceneHelp.AnimFinishListener() {
                @Override
                public void onAnimFinished() {
                    pagerPicviewer.setVisibility(View.VISIBLE);
                    pagerPicviewer.setBackgroundColor(Color.BLACK);
                    ivExpanded.setVisibility(View.GONE);
                }
            });
        }

        /**
         * 从大图到小图
         */
        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        private void playZoomOutAnim(){
            int position = pagerPicviewer.getCurrentItem();
            View thumbView = gridview.getChildAt(position);
            sceneHelp.showExpandedView(ivExpanded, picInfos.get(position));
            ivExpanded.setVisibility(View.VISIBLE);
            pagerPicviewer.setVisibility(View.GONE);
            pagerPicviewer.removeAllViews();
            sceneHelp.zoomExpand2Thumb(vContainer, thumbView, ivExpanded, new SceneHelp.AnimFinishListener() {
                @Override
                public void onAnimFinished() {
                    ivExpanded.setVisibility(View.GONE);
                }
            });
        }

        private class PicInfoPagerAdapter extends PagerAdapter {

            private ArrayList<PicInfo> picUrls;

            private SceneHelp sceneHelp;

            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            public PicInfoPagerAdapter(Activity activity, ArrayList<PicInfo> picUrls
            ) {
                this.picUrls = picUrls;
                sceneHelp = new SceneHelp(activity);
            }

            @Override
            public int getCount() {
                return ObjectUtils.getSize(picUrls);
            }

            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public View instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(container.getContext());
                photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        playZoomOutAnim();
                    }
                });
                PicInfo picInfo = picUrls.get(position);
                sceneHelp.showExpandedView(photoView, picInfo);
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

        }
    }
}
