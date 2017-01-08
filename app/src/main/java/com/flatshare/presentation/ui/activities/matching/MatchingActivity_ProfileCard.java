package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.flatshare.R;
import com.flatshare.domain.datatypes.db.profiles.ApartmentUserProfile;
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
 * Created by david on 08.01.2017.
 */

@Layout(R.layout.activity_main_card)
public class MatchingActivity_ProfileCard {
    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private ApartmentUserProfile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public MatchingActivity_ProfileCard(Context context, ApartmentUserProfile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        //Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getApartmentLocation().city + ", " + mProfile.getApartmentLocation().district);
        locationNameTxt.setText(mProfile.getPrice() + "");
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
