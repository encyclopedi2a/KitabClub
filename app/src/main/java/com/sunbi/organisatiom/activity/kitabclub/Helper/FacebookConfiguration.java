package com.sunbi.organisatiom.activity.kitabclub.Helper;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

/**
 * Created by gokarna on 8/7/15.
 */
public class FacebookConfiguration {
    public FacebookConfiguration() {

    }

    public SimpleFacebookConfiguration getConfiguration() {
        Permission[] permissions = new Permission[]{
                Permission.USER_PHOTOS,
                Permission.EMAIL,
                Permission.PUBLISH_ACTION,
                Permission.PUBLIC_PROFILE

        };
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId("1450305465278640")
                .setNamespace("kitabclub")
                .setPermissions(permissions)
                .build();
        return configuration;
    }
}
