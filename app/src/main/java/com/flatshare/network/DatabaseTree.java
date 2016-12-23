package com.flatshare.network;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Arber on 19/12/2016.
 */

public interface DatabaseTree {

    String USERS_PATH = "users/";

    interface UsersJSON{

        String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid() + "/";

        String CLASSIFICATION_ID = USER_ID + "classification_id/";
        String TENANT_PROFILE_ID = USER_ID + "tenant_profile_id/";
        String APARTMENT_PROFILE_ID = USER_ID + "apartment_profile_id/";

    }

    String TENANT_PROFILES = "tenant_profiles/";

    interface TenantProfilesJSON{

        String FILTER_SETTINGS_PATH = "filter_settings/";
        String APARTMENTS_TO_SHOW_PATH = "apartments_to_show/";
        String MATCHED_APARTMENTS_PATH = "matched_apartments/";

    }


    String APARTMENTS_LOCATION_PATH = "apartments_location/";
    String CITIES_PATH = "cities/";
    String DISTRICTS_PATH = "districts/";

    String ZIP_CODES_PATH = "zip_codes/";


    String APARTMENT_PROFILES_PATH = "apartment_profiles/";
    String MATCHED_TENANTS_PATH = "matched_tenants/";

    String APARTMENT_LOCATION_PATH = "apartment_location/";
    String APARTMENT_ROOMMATES_PATH = "apartment_roommates/";
    String LANGUAGES_PATH = "languages/";

    String POTENTIAL_MATCHES_PATH = "potential_matches/";
}
