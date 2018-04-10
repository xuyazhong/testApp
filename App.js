/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  Alert,
  View,
  TextInput,
  TouchableOpacity,
  NativeModules,
} from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

var Aip = NativeModules.Aip;

export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = { 
      
    };
  }

  render() {
    return (
      <View style={styles.container}>    
        <TouchableOpacity onPress={() => {
            try {
                Aip.actionPlus((err, result) => {
                    if (err) {
                      Alert.alert("error", "识别失败了");
                      return;
                    } else {
                      Alert.alert("识别成功", result);
                    }
                });
            } catch(e) {
                // handle the error
                console.log("e:", e);
                Alert.alert("error", "result error");
            }
        }}>
        <Text>识别银行卡号</Text>
        </TouchableOpacity>
        
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
