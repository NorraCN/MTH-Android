package com.norra.cloud.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 */
public abstract class ListAdapter<T> extends BaseAdapter implements RecyclerListener, OnScrollListener {

    protected Context mContext;
    protected ListView mListView;
    protected List<T> mList = new ArrayList<T>();
    private List<OnScrollListener> mListeners = new ArrayList<OnScrollListener>();
    protected LayoutInflater mInflater;

    public ListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mList != null ? mList.size() > position ? mList.get(position) : null : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);

    public void setList(List<T> list) {
        this.mList.clear();
        if (list != null) {
            this.mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    public void addAll(Collection<T> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(int index, T obj) {
        this.mList.add(index, obj);
        notifyDataSetChanged();
    }

    public void add(T obj) {
        this.mList.add(obj);
        notifyDataSetChanged();
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView listView) {
        mListView = listView;
    }

    public Object getItemAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        for (OnScrollListener l : mListeners) {
            l.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        for (OnScrollListener l : mListeners) {
            l.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void addOnScrollStateChangeLinstener(OnScrollListener l) {
        mListeners.add(l);
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        // 必要时回收资源
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }

    public View inflaterView(int resId) {
        return mInflater.inflate(resId, null);
    }
}
