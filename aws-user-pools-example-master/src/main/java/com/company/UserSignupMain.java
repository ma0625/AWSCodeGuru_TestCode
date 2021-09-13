package com.company;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.Arrays;
import java.util.List;

public class UserSignupMain {

    static final String USER_NAME_ID      = "test56";
    static final String CLIENT_ID         = "app client idを設定";
    static final String PASSWORD          = "aaaaaa*-123D";
    static final String EMAIL             = "hoge@hogehoge.local";
    static final String USER_NAME         = "abcdefg";
    static final String PHONE_NUMBER      = "+818012345678";
    static final String USER_POOL_ID      = "ap-northeast-1_XXXXX";
    static final String TOKYO_REGION_NAME = "ap-northeast-1";

    public static void main(String[] args) {

        AwsServiceBase base = new AwsServiceBase();
        AWSCredentials awsCredentials = base.getAwsCredentials();
        //AWSCognitoIdentityProviderClient client = new AWSCognitoIdentityProviderClient(awsCredentials);

        Regions regions = base.getRegions(TOKYO_REGION_NAME);
        client.setRegion(Region.getRegion(regions));

        System.out.println("ユーザーを作成します...");

        SignUpRequest signUpRequest = new SignUpRequest().withClientId(CLIENT_ID)
                                                         .withPassword(PASSWORD)
                                                         .withUsername(USER_NAME_ID);

        List<AttributeType> attributeDataTypes = Arrays.asList(
                new AttributeType().withName("email")
                                   .withValue(EMAIL),
                new AttributeType().withName("name")
                                   .withValue(USER_NAME),
                new AttributeType().withName("phone_number")
                                   .withValue(PHONE_NUMBER)
        );

        signUpRequest.setUserAttributes(attributeDataTypes);

        SignUpResult result = client.signUp(signUpRequest);
        if (result != null) {

            System.out.println("ユーザーが作成されました。");
            System.out.println(result.toString());
        } else {
            System.out.println("ユーザーの作成に失敗しました。");
            return;
        }

        System.out.println("追加したユーザーを取得します");

        AdminGetUserResult adminGetUserResult = client.adminGetUser(
                new AdminGetUserRequest().withUserPoolId(USER_POOL_ID)
                                         .withUsername(USER_NAME_ID));

        System.out.println(adminGetUserResult.toString());
    }

}
