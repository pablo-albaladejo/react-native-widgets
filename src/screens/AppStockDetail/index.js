import React, { Component } from 'react';
import StockDetail from '../../components/StockDetail';

class AppStockDetail extends Component {

    static navigationOptions = ({ navigation }) => {
        return {
            title: navigation.state.params.stock.symbol,
        };
    };

    render() {
        return (
            <StockDetail
                stock={this.props.navigation.state.params.stock}
            />
        )
    }
}
export default AppStockDetail;