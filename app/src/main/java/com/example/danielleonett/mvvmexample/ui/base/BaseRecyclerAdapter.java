package com.example.danielleonett.mvvmexample.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerAdapter<Item, Holder extends BaseRecyclerAdapter.BaseViewHolder<Item>>
        extends RecyclerView.Adapter<Holder> {

    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    protected OnEmptyStateListener onEmptyStateListener;
    protected List<Item> mItems = new ArrayList<>();

    public BaseRecyclerAdapter(OnEmptyStateListener listener) {
        onEmptyStateListener = listener;
    }

    public void clearItems() {
        mItems.clear();

        if (onEmptyStateListener != null) {
            onEmptyStateListener.onAdapterEmpty();
        }

        notifyDataSetChanged();
    }

    protected List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);

        if (onEmptyStateListener != null) {
            if (items.size() > 0) {
                onEmptyStateListener.onAdapterNotEmpty();
            } else {
                onEmptyStateListener.onAdapterEmpty();
            }
        }

        notifyDataSetChanged();
    }

    protected void removeItemInPosition(int position) {
        mItems.remove(position);

        if (mItems.size() == 0 && onEmptyStateListener != null) {
            onEmptyStateListener.onAdapterEmpty();
        }

        notifyItemRemoved(position);
    }

    public void updateItem(Item item) {
        int itemPos = getItems().indexOf(item);

        if (itemPos > -1) {
            getItems().set(itemPos, item);

            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindViews(mItems.get(position));
    }

    public interface OnEmptyStateListener {
        void onAdapterEmpty();

        void onAdapterNotEmpty();
    }

    public static abstract class BaseViewHolder<Item> extends RecyclerView.ViewHolder {

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public abstract void bindViews(Item item);

    }

}