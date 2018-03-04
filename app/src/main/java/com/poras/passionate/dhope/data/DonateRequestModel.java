package com.poras.passionate.dhope.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by purus on 3/4/2018.
 */

public class DonateRequestModel implements Parcelable {

    public String mCategory;
    public String mQuantity;

    public DonateRequestModel(String category, String quantity) {
        this.mCategory = category;
        this.mQuantity = quantity;
    }

    protected DonateRequestModel(Parcel in) {
        mCategory = in.readString();
        mQuantity = in.readString();
    }

    public static final Creator<DonateRequestModel> CREATOR = new Creator<DonateRequestModel>() {
        @Override
        public DonateRequestModel createFromParcel(Parcel in) {
            return new DonateRequestModel(in);
        }

        @Override
        public DonateRequestModel[] newArray(int size) {
            return new DonateRequestModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategory);
        dest.writeString(mQuantity);
    }
}
