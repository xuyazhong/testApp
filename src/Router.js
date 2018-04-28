import React, {Component} from 'react';
import {StackNavigator} from 'react-navigation';
import Info from './Info';

const AppNavigator = StackNavigator({
    Home: {screen: Info}
}, {
    initialRouteName: 'Home',
});

export default AppNavigator;