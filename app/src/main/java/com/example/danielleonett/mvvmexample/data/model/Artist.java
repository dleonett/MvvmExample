package com.example.danielleonett.mvvmexample.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class Artist implements Parcelable {

    private String id;
    private String name;
    private int popularity;
    private List<String> genres;
    private List<Image> images;

    public Artist() {
    }

    protected Artist(Parcel in) {
        id = in.readString();
        name = in.readString();
        popularity = in.readInt();
        genres = in.createStringArrayList();
        images = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getGenresAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < genres.size(); i++) {
            stringBuilder.append(genres.get(i));

            if (i < genres.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    public String getCoverImage() {
        String nonEmpyString = "nonEmptyString";

        if (images == null) {
            return nonEmpyString;
        }

        return (images.size() > 0)
                ? images.get(0).getUrl()
                : nonEmpyString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(popularity);
        parcel.writeStringList(genres);
        parcel.writeTypedList(images);
    }
}
