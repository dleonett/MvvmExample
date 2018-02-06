package com.example.danielleonett.mvvmexample.ui.artists.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.danielleonett.mvvmexample.R;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class ArtistDetailActivity extends BaseActivity implements
        ArtistDetailFragment.OnInteractionListener {

    // Constants
    public static final String TAG = ArtistDetailActivity.class.getSimpleName();
    public static final String ARG_ARTIST = "ARG_ARTIST";

    // Fields
    private ArtistDetailFragment artistDetailFragment;

    public static Intent newIntent(Context context, Bundle args) {
        Intent intent = new Intent(context, ArtistDetailActivity.class);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void initVars() {
        Artist artist = (Artist) getArgument(ARG_ARTIST);
        artistDetailFragment = ArtistDetailFragment.newInstance(artist);
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void useViews() {
        addArtistDetailFragment();
    }

    private void addArtistDetailFragment() {
        addFragment(R.id.container, artistDetailFragment);
    }

    @Override
    public void onFragmentInteraction() {

    }
}
