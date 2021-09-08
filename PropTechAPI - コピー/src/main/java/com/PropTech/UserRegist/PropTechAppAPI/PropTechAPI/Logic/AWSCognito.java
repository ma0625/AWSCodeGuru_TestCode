package com.example.AWSCodeGuru.TestCode;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.ayth.BasicAWSCredentials;

static String myKeyId = "AKIAX742FUDUQXXXXXXX";
static String mySecretKey = "MySecretKey";

public static void main(String[] args){
	AWSCredentials creds = getCreds(myKeyId, mySecretKey);
}

static AWSCredentials getCreds(String id, String key){
	return new BasicAWSCredentials(id, key);
}