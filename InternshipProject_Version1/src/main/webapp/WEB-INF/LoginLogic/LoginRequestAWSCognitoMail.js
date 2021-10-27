const poolData = {
  UserPoolId: 'us-east-2_vhSX4qzi5',
  ClientId: '3m80nvquv890pm3v7vub0poev0'
};

const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);

// Amazon Cognito 認証情報プロバイダーを初期化します
AWS.config.region = 'us-esst-2'; // リージョン
AWS.config.credentials = new AWS.CognitoIdentityCredentials({
  IdentityPoolId: 'us-east-2:a4cc3e91-c319-469c-93ce-2d9ca7968708',
});