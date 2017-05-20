package com.sherchen.likewechatphotoviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sherchen.likewechatphotoviewer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的adapter
 */
public abstract class ListBaseAdapter<Entity, ViewHolder> extends BaseAdapter {
    protected Context m_Context;
    protected List<Entity> datalist;
    private int layoutResource;
    protected LayoutInflater mInflater;

    public ListBaseAdapter() {
    }

    public ListBaseAdapter(Context context, int paramInt) {
        m_Context = context;
        mInflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        layoutResource = paramInt;
        datalist = new ArrayList<Entity>();
    }

    public ListBaseAdapter(Context context, int layoutResId, List<Entity> list){
        this(context, layoutResId);
        datalist = list;
    }

    @Override
    public int getCount() {
        return datalist == null?0:datalist.size();
    }

    public int getTotalSize(){
        return datalist == null ? 0 : datalist.size();
    }

    public List<Entity> getData() {
        return datalist;
    }

    public void setData(List<Entity> entities) {
        datalist = entities;
    }

    public void add(Entity entity){
        if(entity == null) return;
        datalist.add(entity);
    }

    public void add(Entity entity, int position){
        if(entity == null) return;
        datalist.add(position, entity);
    }

    public void add(List<Entity> entities){
        if(entities == null) return;
        datalist.addAll(entities);
    }

    public void reset(List<Entity> entities){
        if(datalist == null) {
            datalist = new ArrayList<>();
        }else{
            datalist.clear();
        }
        datalist.addAll(entities);
    }

    public void remove(Entity entity){
        if(entity == null) return;
        datalist.remove(entity);
    }

    public void set(int position, Entity entity){
        if(entity == null) return;
        datalist.set(position, entity);
    }

    public void remove(int pos){
        datalist.remove(pos);
    }

    @Override
    public Entity getItem(int paramInt) {
        return datalist.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return paramInt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Entity itemObject = getItem(position);
        ViewHolder holder;
        if ((convertView == null) || (convertView.getTag(R.id.base_tag_glide) == null)) {
            convertView = mInflater.inflate(getLayoutResource(position), null);
            holder = getViewHolder(convertView);
            bindView(holder, convertView);
            convertView.setTag(R.id.base_tag_glide, holder);
        }
        holder = (ViewHolder) convertView.getTag(R.id.base_tag_glide);
        setViewContent(holder, itemObject, convertView, position);
        return convertView;
    }

    private List<Entity> m_BackupList;

    public void onPause(){
        if(datalist == null) return;
        m_BackupList = new ArrayList<>();
        for(Entity entity : datalist){
            m_BackupList.add(entity);
        }
        datalist = null;
        notifyDataSetChanged();
    }

    public void onResume(){
        datalist = m_BackupList;
        notifyDataSetChanged();
    }

    protected int getLayoutResource(int position){
        return layoutResource;
    }

    public void clear(){
        if(datalist != null) {
            datalist.clear();
        }
    }

    /**Bind all the child view to the ViewHolder entity*/
    public abstract void bindView(ViewHolder viewHolder, View convertView);

    /**Get the ViewHolder entity to hold all the child view**/
    public abstract ViewHolder getViewHolder(View content);

    /**As the entity class, inflate the view which is holded by the ViewHolder class*/
    public abstract void setViewContent(ViewHolder viewHolder, Entity entity, View convertView, int position);
}
