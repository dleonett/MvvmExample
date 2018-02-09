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

    public ArtistsResponse() {
    }

    public ArtistsResponse(List<Artist> artistList) {
        artists = new ArtistsWrapper(artistList);
    }

    public class ArtistsWrapper {

        private List<Artist> items;
        private int total;

        public ArtistsWrapper() {
        }

        public ArtistsWrapper(List<Artist> items, int total) {
            this.items = items;
            this.total = total;
        }

        public ArtistsWrapper(List<Artist> artistList) {
            items = artistList;
            total = artistList.size();
        }

        public List<Artist> getItems() {
            return items;
        }

        public int getTotal() {
            return total;
        }
    }
}
