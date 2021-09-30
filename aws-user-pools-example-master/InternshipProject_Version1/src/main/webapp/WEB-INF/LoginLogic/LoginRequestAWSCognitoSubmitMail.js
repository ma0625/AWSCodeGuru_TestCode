// Amazon Cognito 認証情報プロバイダーを初期化します
AWS.config.region = 'us-east-2'; // リージョン
AWS.config.credentials = new AWS.CognitoIdentityCredentials({
  IdentityPoolId: 'us-east-2:a4cc3e91-c319-469c-93ce-2d9ca7968708',
});

// Amazon Cognito Userpoolの指定＋クライアントアプリの指定
const poolData = {
  UserPoolId: 'us-east-2_vhSX4qzi5', //ユーザープールのID
  ClientId: '3m80nvquv890pm3v7vub0poev0' //クライアントアプリの設定上のID
};
//ユーザープール＋クライアントアプリの情報を格納
const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
let attributeList = []; //本来であればattributelistに電話番号や住所など入れることも可能（今回はしない）
