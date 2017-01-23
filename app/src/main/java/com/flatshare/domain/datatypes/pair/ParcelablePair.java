package com.flatshare.domain.datatypes.pair;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arber on 23/01/2017.
 */

public class ParcelablePair implements Parcelable {

    private String id;
    private String value;

    public ParcelablePair(Pair<String, String> idNicknamePair) {
        id = idNicknamePair.getLeft();
        value = idNicknamePair.getRight();
    }

    public ParcelablePair(Parcel in) {
        this.id = in.readString();
        this.value = in.readString();

//        String[] data = new String[2];
//        in.readStringArray(data);
//        this.id = data[0];
//        this.value = data[1];
    }

    public static final Creator<ParcelablePair> CREATOR = new Creator<ParcelablePair>() {
        @Override
        public ParcelablePair createFromParcel(Parcel in) {
            return new ParcelablePair(in);
        }

        @Override
        public ParcelablePair[] newArray(int size) {
            return new ParcelablePair[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(value);
//        dest.writeStringArray(new String[] {this.id,
//                this.value});
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
