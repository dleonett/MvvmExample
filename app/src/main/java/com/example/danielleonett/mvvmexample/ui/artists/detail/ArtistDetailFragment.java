package com.example.danielleonett.mvvmexample.ui.artists.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danielleonett.mvvmexample.R;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.ui.artists.ArtistsViewModel;
import com.example.danielleonett.mvvmexample.ui.base.BaseFragment;
import com.example.danielleonett.mvvmexample.util.StringUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daniel.leonett on 6/02/2018.
 */

public class ArtistDetailFragment extends BaseFragment {

    // Constants
    public static final String TAG = ArtistDetailFragment.class.getSimpleName();
    public static final String ARG_ARTIST = "ARG_ARTIST";

    // Views
    @BindView(R.id.imageCover)
    ImageView imageCover;
    @BindView(R.id.labelName)
    TextView labelName;
    @BindView(R.id.labelPopularity)
    TextView labelPopularity;
    @BindView(R.id.labelGenres)
    TextView labelGenres;

    // Fields
    private OnInteractionListener listener;
    private ArtistDetailViewModel artistViewModel;

    public static ArtistDetailFragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTIST, artist);

        ArtistDetailFragment fragment = new ArtistDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_artist_detail;
    }

    @Override
    protected void initVars() {
        artistViewModel = ViewModelProviders
                .of(this)
                .get(ArtistDetailViewModel.class);

        artistViewModel.setArtist((Artist) getArgument(ARG_ARTIST));
    }

    @Override
    protected void bindViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void useViews() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        subscribeToArtistDetailViewModel();
    }

    private void subscribeToArtistDetailViewModel() {
        artistViewModel.getArtist().observe(this, new Observer<Artist>() {
            @Override
            public void onChanged(@Nullable Artist artist) {
                onArtistViewModelChanged(artist);
            }
        });
    }

    private void onArtistViewModelChanged(Artist artist) {
        if (artist != null) {
            bindArtist(artist);
        }
    }

    private void bindArtist(Artist artist) {
        labelName.setText(artist.getName());
        labelPopularity.setText(String.valueOf(artist.getPopularity()));
        labelGenres.setText(artist.getGenresAsString());
        Picasso.with(getContext()).load(artist.getCoverImage())
                .placeholder(R.color.grey_700)
                .into(imageCover);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            listener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement " + OnInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface OnInteractionListener {
        void onFragmentInteraction();
    }
}
