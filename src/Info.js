import React, { Component } from 'react';
import {TouchableOpacity, NativeModules, Alert} from 'react-native';
import { Container, Header, StyleProvider, Content, Button, Label, Form, Item, Input, Text, Right, Left, View } from 'native-base';
import getTheme from '../native-base-theme/components';
import commonColor from '../native-base-theme/variables/commonColor';
import Tools from "./Tools";

class Info extends Component {
    static navigationOptions = ({ navigation }) => ({
        title: '完善资料',
    });

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            id_Card: '',
            address: '',
            connect: '',
        }
    }

    render() {
        return(
            <StyleProvider style={getTheme(commonColor)}>
                <Container>
                    <Content>
                        {Tools.renderTitle('填写资料')}

                        {Tools._renderScanIDFront('拍照识别身份信息', (front) => {
                            console.log("front:",front);
                            this.setState({
                                name: front["name"],
                                id_Card: front["idNumber"],
                                address: front["address"]
                            })
                        })}
                        <Form>
                            <Item fixedLabel>
                                <Label>姓名</Label>
                                {Tools._renderInputNormalItemValue('姓名', this.state.name, (text) => {
                                    this.setState({
                                        name: text,
                                    })
                                })}
                            </Item>
                            <Item fixedLabel>
                                <Label>身份证号</Label>
                                {Tools._renderInputNormalItemValue('身份证号', this.state.id_Card, (text) => {
                                    this.setState({
                                        id_Card: text,
                                    })
                                })}
                            </Item>
                            <Item fixedLabel>
                                <Label>地址</Label>
                                {Tools._renderInputNormalItemValue('地址', this.state.address, (text) => {
                                    this.setState({
                                        address: text,
                                    })
                                })}
                            </Item>
                            <Item fixedLabel>
                                <Label>手机号</Label>
                                {Tools._renderInputNumberItemValue('手机号', this.state.connect, (text) => {
                                    this.setState({
                                        connect: text,
                                    })
                                })}
                            </Item>

                            <Item style={{height: 60}}>
                                <Label>是否上学</Label>
                                <Right style={{marginRight: 20}}>
                                    {Tools._renderSwitchValue(this.state.status, (v) => {
                                        this.setState({
                                            status: v,
                                        })
                                    })}
                                </Right>
                            </Item>
                        </Form>
                    </Content>
                    <View style={{flexDirection:'row'}}>
                        {Tools._renderBottomButton("提交", () => {
                            Alert.alert("提交");
                        })}
                        {Tools._renderBottomButton("跳过", () => {
                            Alert.alert("跳过");
                        })}
                    </View>
                </Container>
            </StyleProvider>

        );
    }
}
export default Info;

