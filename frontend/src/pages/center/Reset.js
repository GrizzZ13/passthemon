import React from 'react';
import {
    Input,
    Button,
    NativeBaseProvider,
    FormControl,
    VStack,
    HStack,
    Center,
    Image,
} from 'native-base';
import {GetCode, ResetFunc} from '../../service/UserService';
import MD5 from "react-native-md5"

export function Reset({route, navigation}) {
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [code, setCode] = React.useState('');
    const [errMsg, setErrMsg] = React.useState('');
    const [invalid, setInvalid] = React.useState(false);
    const [loading, setLoading] = React.useState(false);
    const [disabled, setDisalbed] = React.useState(false);
    const [btnTxt, setBtnTxt] = React.useState('获取验证码');

    const onGetCodePress = () => {
        if (email.length === 0) {
            setInvalid(true);
            setErrMsg('请填写邮箱！');
            return;
        }

        if(!(/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@(sjtu.edu.cn)/.test(email))) {
            setInvalid(true);
            setErrMsg('邮箱格式错误 ！');
            return;
        }

        let maxTime = 30;
        setDisalbed(true);
        setInvalid(false);
        setBtnTxt('重新获取(' + maxTime + 's)');
        let timer = setInterval(() => {
            if (maxTime > 0) {
                --maxTime;
                console.log(maxTime);
                setDisalbed(true);
                setBtnTxt('重新获取(' + maxTime + 's)');
            } else {
                console.log('clear interval');
                setDisalbed(false);
                setBtnTxt('获取验证码');
                clearInterval(timer);
            }
        }, 1000);

        let data = {
            email: email,
            opt: 'reset',
        };
        GetCode(data, getCodeCallback);
    };

    const getCodeCallback = msg => {
        console.log(msg);
        if (msg.status <= 0) {
            setInvalid(true);
            setErrMsg(msg.message);
        }
    };

    const onResetPress = () => {
        console.log('on pressed reset');
        if (email.length === 0 || password.length === 0 || code.length === 0) {
            setInvalid(true);
            setErrMsg('请完整填写相关信息！');
            return;
        }

        if(!(/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@(sjtu.edu.cn)/.test(email))) {
            setInvalid(true);
            setErrMsg('邮箱格式错误 ！');
            return;
        }

        if(!(/^[a-zA-Z0-9]{6,12}$/.test(password))) {
            setInvalid(true);
            setErrMsg('密码不能含有非法字符，长度在6-12之间 ！');
            return;
        }

        setLoading(true);
        setTimeout(() => {
            setLoading(false);
        }, 3000);
        let md5password = MD5.hex_md5(password);
        let data = {
            email: email,
            password: md5password,
            code: code,
        };
        ResetFunc(data, resetCallback);
    };

    const resetCallback = msg => {
        console.log(msg);
        setLoading(false);
        if (msg.status > 0) {
            setInvalid(false);
            navigation.navigate('login');
        } else {
            setInvalid(true);
            setErrMsg(msg.message);
        }
    };

    return (
        <NativeBaseProvider>
            <VStack>
                <Center height={240} marginTop={10}>
                    <Image
                        rounded={90}
                        source={require('./icon.jpg')}
                        alt="Alternate Text"
                        size={180}
                    />
                </Center>
                <Center height={100} space={4} alignItems={'center'} marginBottom={16}>
                    <FormControl isInvalid={invalid} width={300} height={120}>
                        <Input
                            variant={'underlined'}
                            type={'text'}
                            placeholder="SJTU-Email"
                            _light={{
                                placeholderTextColor: 'blueGray.400',
                            }}
                            _dark={{
                                placeholderTextColor: 'blueGray.50',
                            }}
                            onChangeText={text => {
                                setEmail(text);
                                setInvalid(false);
                            }}
                        />
                        <Input
                            variant={'underlined'}
                            type={'password'}
                            placeholder="New Password"
                            _light={{
                                placeholderTextColor: 'blueGray.400',
                            }}
                            _dark={{
                                placeholderTextColor: 'blueGray.50',
                            }}
                            onChangeText={text => {
                                setPassword(text);
                                setInvalid(false);
                            }}
                        />
                        <Input
                            variant={'underlined'}
                            type={'text'}
                            placeholder="Verification Code"
                            _light={{
                                placeholderTextColor: 'blueGray.400',
                            }}
                            _dark={{
                                placeholderTextColor: 'blueGray.50',
                            }}
                            onChangeText={text => {
                                setCode(text);
                                setInvalid(false);
                            }}
                        />
                        <FormControl.ErrorMessage>{errMsg}</FormControl.ErrorMessage>
                    </FormControl>
                </Center>
                <Center height={100}>
                    <HStack space={4} alignItems={'center'}>
                        <Button
                            backgroundColor={'#f05000'}
                            isDisabled={disabled}
                            onPress={onGetCodePress}
                            size={'md'}
                            width={40}
                            rounded={20}>
                            {btnTxt}
                        </Button>
                        <Button
                            backgroundColor={'#f07000'}
                            isLoading={loading}
                            onPress={onResetPress}
                            size={'md'}
                            width={32}
                            rounded={20}>
                            重置密码
                        </Button>
                    </HStack>
                </Center>
            </VStack>
        </NativeBaseProvider>
    );
}
