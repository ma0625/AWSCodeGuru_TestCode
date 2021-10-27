// 1Amazon Cognito 認証情報プロバイダーを初期化します
// Amazon Cognito 認証情報プロバイダーを初期化します
AWS.config.region = 'us-east-2'; // リージョン
AWS.config.credentials = new AWS.CognitoIdentityCredentials({
  IdentityPoolId: 'us-east-2:a4cc3e91-c319-469c-93ce-2d9ca7968708',
});

AWSCognito.config.region = 'us-west-2'; // リージョン(デフォルトだとこのまま)
AWSCognito.config.credentials = new AWS.CognitoIdentityCredentials({
  IdentityPoolId: 'us-east-2:a4cc3e91-c319-469c-93ce-2d9ca7968708', // ID プールのID
});