package com.sherchen.likewechatphotoviewer.entity;

import android.text.TextUtils;

import com.sherchen.likewechatphotoviewer.utils.ObjectUtils;

/**
 * Created by dave on 2017/1/10.
 */

public class PicInfo {

    /**
     * pic_url_wh : 600x800
     * pic_url_thum : poster/20161020/thumbnail/1.jpg
     * pic_url_thum_wh : 218x290
     * pic_url : poster/20161020/original/1.jpg
     */

    private String pic_url_wh;//原图宽高
    private String pic_url_thum;//缩略图地址
    private String pic_url_thum_wh;//缩略图宽高
    private String pic_url;//原图地址

    public PicInfo(String pic_url, String pic_url_wh, String pic_url_thum) {
        this.pic_url_wh = pic_url_wh;
        this.pic_url = pic_url;
        this.pic_url_thum = pic_url_thum;
    }

    private int itemSingleDW;//单张图片的显示宽度
    private int itemSingleDH;//单张图片的显示高度

    private int detailDW;//详情图片宽度
    private int detailDH;//详情图片高度

    private int viewerDW;//预览图片宽度
    private int viewerDH;//预览图片高度

    private double picW = -1;//原图的高
    private double picH = -1;//原图的宽

    private void getPicW(){
        if(picW == -1){
            if(TextUtils.isEmpty(pic_url_wh) || !pic_url_wh.contains("x")) {
                picW = 0;
                picH = 0;
            }
            String[] array = pic_url_wh.split("x");
            if(ObjectUtils.getSize(array) != 2) {
                picW = 0;
                picH = 0;
            }
            try {
                picW = Double.parseDouble(array[0]);
            } catch (NumberFormatException e) {
                picW = 0;
            }
            try {
                picH = Double.parseDouble(array[1]);
            } catch (NumberFormatException e) {
                picH = 0;
            }
        }
    }

    public int getViewerDW(int contentSize){
        getPicW();
        if(picW == 0 || picW == -1) return viewerDW;
        if(viewerDW == 0){
            viewerDW = contentSize;
        }
        if(viewerDW > picW) {//wsq 限制显示图片大小不能超过原图
            viewerDW = (int) picW;
        }
        return viewerDW;
    }

    public int getViewerDH(int contentSize){
        getViewerDW(contentSize);
        if(picH == 0 || picH == -1) return viewerDH;
        if(viewerDW == 0) return viewerDH;
        if(viewerDH == 0) {
            viewerDH = (int) (viewerDW * picH / picW);
        }
        return viewerDH;
    }


    public int getDetailDW(int contentSize){
        getPicW();
        if(picW == 0 || picW == -1) return detailDW;
        if(detailDW == 0){
            detailDW = contentSize;
        }
        if(detailDW > picW) {//wsq 限制显示图片大小不能超过原图
            detailDW = (int) picW;
        }
        return detailDW;
    }

    public int getDetailDH(int contentSize){
        getDetailDW(contentSize);
        if(picH == 0 || picH == -1) return detailDH;
        if(detailDW == 0) return detailDH;
        if(detailDH == 0) {
            detailDH = (int) (detailDW * picH / picW);
        }
        return detailDH;
    }

    public String getPic_url_thum() {
        return pic_url_thum;
    }

    public boolean isValideItemSingleDWH(){
        return itemSingleDW != 0 && itemSingleDH != 0;
    }

    public int getItemSingleDW(int contentSize){
        getPicW();
        if(picW == 0 || picW == -1) return itemSingleDW;
        if(itemSingleDW == 0){
            itemSingleDW = contentSize * 5 / 9;
        }
        if(itemSingleDW > picW) {//wsq 限制显示图片大小不能超过原图
            itemSingleDW = (int) picW;
        }
        return itemSingleDW;
    }

    public int getItemSingleDW(){
        return itemSingleDW;
    }

    public int getItemSingleDH(){
        return itemSingleDH;
    }

    public int getItemSingleDH(int contentSize){
        getItemSingleDW(contentSize);
        if(picH == 0 || picH == -1) return itemSingleDH;
        if(itemSingleDW == 0) return itemSingleDH;
        if(itemSingleDH == 0) {
            itemSingleDH = (int) (itemSingleDW * picH / picW);
        }
        return itemSingleDH;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

}
