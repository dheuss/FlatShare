package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public MatchingActivity_ProfileCard_Tenant(Context context, TenantProfile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        //Glide.with(mContext).load(mProfile.getImageIds()).into(profileImageView);
        nameAgeTxt.setText("Name: " + mProfile.getFirstName() + ", " + mProfile.getAge());
        locationNameTxt.setText("Job: " + mProfile.getOccupation() + ", Duration: " + mProfile.getDurationOfStay());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){

    }

    @SwipeIn
    private void onSwipeIn(){

    }

    @SwipeInState
    private void onSwipeInState(){

    }

    @SwipeOutState
    private void onSwipeOutState(){

    }
}
