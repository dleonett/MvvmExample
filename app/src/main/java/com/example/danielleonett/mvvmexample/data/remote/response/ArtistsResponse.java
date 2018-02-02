package com.example.danielleonett.mvvmexample.data.remote.response;

import com.example.danielleonett.mvvmexample.data.model.Artist;

import java.util.List;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class ArtistsResponse {

    private ArtistsWrapper artists;

    public ArtistsWrapper getArtists() {
        return artists;
    }

    public class ArtistsWrapper {

        private List<Artist> items;
        private int total;

        public List<Artist> getItems() {
            return items;
        }

        public int getTotal() {
            return total;
        }
    }
}
