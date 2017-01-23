package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by david on 08.01.2017.
 */

@Layout(R.layout.activity_matching_card)
public class MatchingActivity_ProfileCard_Apartment {
    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private ApartmentProfile mProfile;
    private MatchingActivity matchingActivity;
    private SwipePlaceHolderView mSwipeView;

    private Bitmap mBitmap;
    private Context mContext;

    private String TAG = "MatchingActivity_ProfileCard_Apartment";

    private TextView priceTextView;

    public MatchingActivity_ProfileCard_Apartment(MatchingActivity matchingActivity, ApartmentProfile profile, SwipePlaceHolderView swipeView, Bitmap bitmap) {
        this.matchingActivity = matchingActivity;
        mProfile = profile;
        mSwipeView = swipeView;
        mBitmap = bitmap;
        mContext  = matchingActivity.getActivity();}

    @Resolve
    private void onResolved(){
        Log.d(TAG, "onResolved: " + mBitmap);
        if (mBitmap == null){
            Glide.with(mContext).load(R.drawable.apartment_default).into(profileImageView);
        } else{
            Glide.with(mContext).load(mBitmap).into(profileImageView);
        }
        nameAgeTxt.setText("Location: " + mProfile.getApartmentLocation().city + ", " + mProfile.getApartmentLocation().district);
        locationNameTxt.setText("Price: " + mProfile.getPrice() + "€");

        matchingActivity.setApartmentPrice(mProfile.getPrice());
        matchingActivity.setApartmentSize(mProfile.getArea());
        matchingActivity.setApartmentZipCode(mProfile.getApartmentLocation().getZipCode());
        matchingActivity.setApartmentCity(mProfile.getApartmentLocation().getCity());
        matchingActivity.setApartmentState(mProfile.getApartmentLocation().getState());
        matchingActivity.setApartmentCountry(mProfile.getApartmentLocation().getCountry());
        matchingActivity.setApartmentImage(mBitmap);
        matchingActivity.setInternet(mProfile.hasInternet());
        matchingActivity.setSmoker(mProfile.isSmokerApartment());
        matchingActivity.setPets(mProfile.hasPets());
        matchingActivity.setWashingMashine(mProfile.hasWashingMachine());
        matchingActivity.setPurpose(mProfile.isPurposeApartment());
    }

    @SwipeOut
    private void onSwipedOut(){
        matchingActivity.tenantSwipedApartment(mProfile, false);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){

    }

    @SwipeIn
    private void onSwipeIn(){
        matchingActivity.tenantSwipedApartment(mProfile, true);
    }

    @SwipeInState
    private void onSwipeInState(){

    }

    @SwipeOutState
    private void onSwipeOutState(){

    }
}
