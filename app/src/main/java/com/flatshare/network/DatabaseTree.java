package com.flatshare.network;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Arber on 19/12/2016.
 */

public interface DatabaseTree {

    String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String USERS_PATH = "users/";

    String APARTMENTS_LOCATION_PATH = "apartments_location/";
    String CITIES_PATH = "cities/";
    String DISTRICTS_PATH = "districts/";
    String ZIP_CODES_PATH = "zip_codes/";

    String TENANT_PROFILES_PATH = "tenant_profiles/";
    String TENANT_FILTER_PATH = "tenant_filter_settings/";
    String APARTMENTS_TO_SHOW_PATH = "apartments_to_show/";
    String MATCHED_APARTMENTS_PATH = "matched_apartments/";


    String APARTMENT_PROFILES_PATH = "apartment_profiles/";
    String APARTMENT_FILTER_PATH = "apartment_filter_settings/";
    String MATCHED_TENANTS_PATH = "matched_tenants/";

    String APARTMENT_LOCATION_PATH = "apartment_location/";
    String APARTMENT_ROOMMATES_PATH = "apartment_roommates/";
    String LANGUAGES_PATH = "languages/";

    String POTENTIAL_MATCHES_PATH = "potential_matches/";
}
