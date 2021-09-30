package com.src.Internship.Project_Version1.Internship_Project_Version1.Logic;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

import java.util.Arrays;
import java.util.List;

public class AWSCognitoUserSignupMain {
	
	final static String USER_NAME_ID = "Test";
	final static String CLIENT_ID = "3m80nvquv890pm3v7vub0poev0";
	final static String PASSWORD = "Test@1234";
	final static String EMAIL = "m.fukaya@solpac.co.jp";
	final static String USER_NAME = "Test";
	final static String PHONE_NUMBER = "08094209625";
	final static String USER_POOL_ID = "us-east-2_vhSX4qzi5";
	final static String TOKYO_REGION_NAME = "us-east-2";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

        AWSCognitoTestCode base = new AWSCognitoTestCode();
        AWSCredentials awsCredentials = base.getAwsCredentials();

		//AWSCognitoIdentityProviderClient client = new AWSCognitoIdentityProviderClient(awsCredentials);
        //AmazonSNS sns = new AmazonSNSClient();
        Regions regions = base.getRegions(TOKYO_REGION_NAME);
        AmazonSNS sns = AmazonSNSClient.builder().build();
        sns.setRegion(Region.getRegion(regions));

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
