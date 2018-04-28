/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import AppNavigator from "./src/Router";


export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = { 
      
    };
  }

  render() {
    return (
        <AppNavigator />
    );
  }
}
