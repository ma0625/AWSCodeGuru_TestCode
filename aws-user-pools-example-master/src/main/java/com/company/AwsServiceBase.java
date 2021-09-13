package com.company;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;

public class AwsServiceBase {

    /**
     * リージョン名からリージョンを取得します。
     *
     * @param regionString リージョン文字列
     * @return 取得したリージョン
     */
    public Regions getRegions(String regionString) {
        return Regions.fromName(regionString);
    }


    public AWSCredentials getAwsCredentials() {
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        return credentials;
    }
}
