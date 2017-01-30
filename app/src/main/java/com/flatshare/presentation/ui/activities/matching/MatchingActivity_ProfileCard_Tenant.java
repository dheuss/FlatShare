package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentProfile;
import com.flatshare.domain.datatypes.db.profiles.TenantProfile;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by david on 12.01.2017.
 */

@Layout(R.layout.activity_matching_card)
public class MatchingActivity_ProfileCard_Tenant {
    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private TenantProfile mProfile;
    private MatchingActivity matchingActivity;
    private SwipePlaceHolderView mSwipeView;

    private Bitmap mBitmap;
    private Context mContext;

    public MatchingActivity_ProfileCard_Tenant(MatchingActivity matchingActivity, TenantProfile profile, SwipePlaceHolderView swipeView, Bitmap bitmap) {
        this.matchingActivity = matchingActivity;
        mProfile = profile;
        mSwipeView = swipeView;
        mBitmap = bitmap;
        mContext = matchingActivity.getActivity();
    }

    @Resolve
    private void onResolved(){
        //ToDo
        if (mBitmap == null) {
            Glide.with(mContext).load(R.drawable.tenant_default).into(profileImageView);
        } else {
            Glide.with(mContext).load(mBitmap).into(profileImageView);
        }
        nameAgeTxt.setText("Name: " + mProfile.getFirstName() + ", " + mProfile.getAge());
        locationNameTxt.setText("Job: " + mProfile.getOccupation());
        matchingActivity.setTenantName(mProfile.getFirstName());
        matchingActivity.setTenantAge(mProfile.getAge());
        matchingActivity.setTenantGender(mProfile.getGender());
        matchingActivity.setTenantSmoker(mProfile.isSmoker());
        matchingActivity.setTenantPets(mProfile.hasPets());
        matchingActivity.setTenantOccupation(mProfile.getOccupation());
        matchingActivity.setTenantInfo(mProfile.getShortBio());
    }

    @SwipeOut
    private void onSwipedOut(){
        matchingActivity.roommateSwipedTenant(mProfile, false);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){

    }

    @SwipeIn
    private void onSwipeIn(){
        matchingActivity.roommateSwipedTenant(mProfile, true);
    }

    @SwipeInState
    private void onSwipeInState(){

    }

    @SwipeOutState
    private void onSwipeOutState(){

    }
}
