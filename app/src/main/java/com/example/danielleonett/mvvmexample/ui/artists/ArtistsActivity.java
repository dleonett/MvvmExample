package com.example.danielleonett.mvvmexample.ui.artists;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.danielleonett.mvvmexample.R;
import com.example.danielleonett.mvvmexample.data.model.Artist;
import com.example.danielleonett.mvvmexample.data.model.UiStateModel;
import com.example.danielleonett.mvvmexample.ui.artists.detail.ArtistDetailActivity;
import com.example.danielleonett.mvvmexample.ui.base.BaseActivity;
import com.example.danielleonett.mvvmexample.ui.base.BaseRecyclerAdapter;
import com.example.danielleonett.mvvmexample.ui.common.GridSpacingItemDecoration;
import com.example.danielleonett.mvvmexample.util.KeyboardUtils;
import com.example.danielleonett.mvvmexample.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArtistsActivity extends BaseActivity implements
        BaseRecyclerAdapter.OnEmptyStateListener,
        ArtistsAdapter.OnArtistClickListener {

    // Constants
    public static final String TAG = ArtistsActivity.class.getSimpleName();

    // Views
    @BindView(R.id.listArtists)
    RecyclerView listArtists;
    @BindView(R.id.inputSearch)
    AppCompatEditText inputSearch;
    @BindView(R.id.btnClearSearch)
    View btnClearSearch;
    @BindView(R.id.emptyView)
    View emptyView;
    @BindView(R.id.progress)
    View progress;

    // Fields
    private ArtistsAdapter artistsAdapter;
    private ArtistsViewModel artistsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_artists;
    }

    @Override
    protected void initVars() {
        artistsAdapter = new ArtistsAdapter(this, this);
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void useViews() {
        initArtistsList();
        initInputSearchListener();
        initViewModels();
    }

    private void initArtistsList() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        listArtists.setLayoutManager(layoutManager);
        listArtists.setHasFixedSize(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_items_spacing);
        listArtists.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

        listArtists.setAdapter(artistsAdapter);
    }

    private void initInputSearchListener() {
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    btnClearSearch.setVisibility(View.VISIBLE);
                } else {
                    btnClearSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViewModels() {
        artistsViewModel =
                ViewModelProviders.of(this).get(ArtistsViewModel.class);

        subscribeToArtistsViewModel();
    }

    private void subscribeToArtistsViewModel() {
        artistsViewModel.getArtists()
                .observe(this, new Observer<UiStateModel<List<Artist>>>() {
                    @Override
                    public void onChanged(@Nullable UiStateModel<List<Artist>> artistsUiStateModel) {
                        onArtistsViewModelChanged(artistsUiStateModel);
                    }
                });
    }

    private void onArtistsViewModelChanged(UiStateModel<List<Artist>> artistsUiStateModel) {
        Log.d(TAG, "onArtistsViewModelChanged()");

        if (artistsUiStateModel == null) return;

        if (artistsUiStateModel.isInProgress()) {
            showLoading();
        }

        if (artistsUiStateModel.isSuccess()) {
            hideLoading();
            artistsAdapter.setItems(artistsUiStateModel.getData());
        }

        if (artistsUiStateModel.isError()) {
            hideLoading();
            showToast(artistsUiStateModel.getErrorMessage());
        }
    }

    @OnClick(R.id.btnSearch)
    public void onSearchClick() {
        KeyboardUtils.closeKeyboard(this);
        artistsViewModel.searchArtists(inputSearch.getText().toString().trim());
    }

    @OnClick(R.id.btnClearSearch)
    public void onClearSearchClick() {
        inputSearch.setText("");
        ViewUtils.requestFocus(this, inputSearch);
    }

    private void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onAdapterEmpty() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAdapterNotEmpty() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onArtistClick(Artist artist) {
        navigateToArtistDetail(artist);
    }

    private void navigateToArtistDetail(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ArtistDetailActivity.ARG_ARTIST, artist);

        startActivity(ArtistDetailActivity.newIntent(this, args));
    }
}