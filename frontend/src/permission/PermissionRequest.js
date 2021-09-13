const {PermissionsAndroid} = require("react-native");

export async function requestCameraPermission() {
    try {
        const granted = await PermissionsAndroid.request(
            PermissionsAndroid.PERMISSIONS.CAMERA,
            {
                'title': '相机权限',
                'message': 'PassThemOn可能需要使用您的相机权限进行拍照'
            }
        )
        if(granted!==PermissionsAndroid.RESULTS.GRANTED){
            alert("权限不足将无法拍照哦")
        }
    }
    catch (err) {
        console.warn(err)
    }
}
