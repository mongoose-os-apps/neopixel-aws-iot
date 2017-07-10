# AWS-IoT-Neopixel-Android

## Step 1: Create a Federated Identity in Cognito
This sample requires Cognito to authorize to AWS IoT in order to access device shadows. 
Use Amazon Cognito to create a new identity pool:

- Head to [Cognito](https://console.aws.amazon.com/cognito) and click on 'Manage Federated Identities'
- Click 'Create New Identity Pool'
- Give your Identity Pool a name and click 'Enable access to unauthenticated identities'
- Click 'Create Pool'
- Click 'Allow' to access your user Roles and click 'Allow' again to use the default Auth and Unauth roles.
- Go back to your Identity Pool and click 'Sample Code'
- Take note of your Identity Pool Id and Region

## Step 2: Consumer IoT Endpoint

- Using the [AWS CLI](https://aws.amazon.com/cli/) type the command 'aws iot describe-endpoint'

## Step 3: Edit Constants.java and strings.xml

- Import the AWS-IoT-Neopixel-Android project into Android Studio
- Edit the /utils/Constants.java file and fill in your Consumer Specific Endpoint, Identity Pool Id, and Region
- Edit the /res/strings.xml and create a new <item> entry for each device in your AWS IoT console
