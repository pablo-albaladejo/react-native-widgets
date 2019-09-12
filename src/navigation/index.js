
import React, { Component } from 'react';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import AppStockSearch from '../screens/AppStockSearch';
import AppStockDetail from '../screens/AppStockDetail';

const StackNavigator = createStackNavigator(
    {
        Search: {
            screen: AppStockSearch,
            navigationOptions: {
                header: null,
            }
        },
        Detail: { screen: AppStockDetail },
    },
    {
        initialRouteName: 'Search',
    }
);

//https://github.com/react-navigation/react-navigation/issues/5591#issuecomment-464059406
const RootStack = createAppContainer(StackNavigator)

//https://github.com/react-navigation/react-navigation/issues/634#issuecomment-302945143
class App extends Component {
    
    render() {
        console.log(this.props)
        return <RootStack screenProps={this.props} />;
    }
}

export default App;