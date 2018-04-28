import React, {Component} from 'react';
import {TouchableOpacity, Image, Alert, NativeModules, Keyboard, Dimensions, StyleSheet} from 'react-native';
import {Container, Header, Switch, Content, Button, Label, Form, Item, Input, Text, View, Icon} from 'native-base';
// import NJPicker from '../Util/Picker.js';
import commonColor from "../native-base-theme/variables/commonColor"
import { NavigationActions } from 'react-navigation';

var dv = {
    //获取屏幕宽度
    screenWidth : Dimensions.get('window').width,
    screenHeight : Dimensions.get('window').height,
}
var OCR = NativeModules.AipOCR;

class Tools extends Component {
    static _renderInputNormalItemValue = (text, value, onChange) => (
        <Input
            placeholder={text}
            onChangeText={onChange}
            value={value}
        />
    );
    static _renderInputNumberItemValue = (text, value, onChange) => (
        <Input
            placeholder={text}
            keyboardType='phone-pad'
            value={value}
            onChangeText={onChange}
        />
    );
    static _renderSwitchValue = (v = false, onValueChange) => (
        <Switch
            value={v}
            onValueChange={onValueChange}/>
    );
    static _renderBottomButton = (text, onPress) => (
        <Button block={true} style={{flex: 1, margin: 10}} onPress={onPress}>
            <Text>{text}</Text>
        </Button>
    );
    static renderTitle(text) {
        return (
            <View style={{marginTop: 10, padding: 10, height: 50}}>
                <Text style={{width: 100, color: '#479f99'}}>{text}</Text>
                <View style={{width: dv.screenWidth * 0.9, height: 1, backgroundColor: '#479f99', marginTop: 5, marginBottom: 10}}></View>
            </View>
        )
    }
    static _renderScanIDFront = (text, callback) => (
        <View style={styles.scanStyle}>
            <Button bordered rounded info primary style={styles.scanBtnStyle} onPress={() => {
                Tools.actionScanIdFront(callback);
            }}>
                <Text style={{fontSize: 12, textAlign:'center'}}>{text}</Text>
            </Button>
        </View>
    );
    static actionScanIdFront = (callback) => (
        OCR.actionIDCardOCRFront((err, result) => {
            if (err) {
                Alert.alert("识别失败");
                return;
            }
            console.log(result);
            let res = JSON.parse(result);
            if (res) {
                callback(res)
            } else {
                callback("error");
            }
        })
    );
}

export default Tools;

const styles = StyleSheet.create({
    scanStyle: {
        height: 60,
        justifyContent: 'center',
        alignItems: 'center',
        width: dv.screenWidth,
        alignSelf: "center"
    },
    scanBtnStyle: {
        width: dv.screenWidth-80,
        height: 40,
        alignSelf: "center",
        justifyContent: 'center',
        alignItems: 'center',
    },
    scanTextStyle: {
        fontSize: 12,
        alignSelf: "center",

    },
})