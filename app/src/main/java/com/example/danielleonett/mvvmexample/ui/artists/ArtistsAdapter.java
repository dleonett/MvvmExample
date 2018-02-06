package com.example.danielleonett.mvvmexample.ui.artists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danielleonett.mvvmexample.R;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.ui.base.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class ArtistsAdapter extends BaseRecyclerAdapter<Artist, BaseRecyclerAdapter.BaseViewHolder<Artist>> {

    public static final String TAG = ArtistsAdapter.class.getSimpleName();

    private OnArtistClickListener listener;

    public ArtistsAdapter(OnArtistClickListener listener, OnEmptyStateListener emptyStateListener) {
        super(emptyStateListener);
        this.listener = listener;
    }

    @Override
    public BaseViewHolder<Artist> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.item_list_artist, parent, false);
        return new ViewHolderItem(v);
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public interface OnArtistClickListener {
        void onArtistClick(Artist artist);
    }

    class ViewHolderItem extends BaseViewHolder<Artist> {

        // Views
        @BindView(R.id.imageCover)
        ImageView imageCover;
        @BindView(R.id.labelName)
        TextView labelName;

        public ViewHolderItem(View view) {
            super(view);
        }

        @Override
        public void bindViews(final Artist artist) {
            labelName.setText(artist.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onArtistClick(artist);
                    }
                }
            });

            itemView.post(new Runnable() {
                @Override
                public void run() {
                    itemView.getLayoutParams().height = itemView.getWidth();

                    Picasso.with(itemView.getContext())
                            .load(artist.getCoverImage())
                            .placeholder(R.color.grey_700)
                            .into(imageCover);
                }
            });
        }

    }
}