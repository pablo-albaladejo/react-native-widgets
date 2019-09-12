import React, { Component } from 'react';
import StockDetail from '../../components/StockDetail';

class AppStockDetail extends Component {

    static navigationOptions = ({ navigation }) => {
        return {
            title: navigation.state.params.symbol,
        };
    };

    render() {
        return (
            <StockDetail
                symbol={this.props.navigation.state.params.symbol}
            />
        )
    }
}
export default AppStockDetail;