import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';

class AppStockSearch extends Component {

    processItemSelected = (item) => {
        this.onNavigate(item.symbol)
    }

    onNavigate = (symbol) => {
        this.props.navigation.navigate('Detail', { symbol });
    }

    componentDidMount = () => {
        const { screenProps } = this.props;
        if (screenProps.navigation && screenProps.navigation.symbol) {
            this.onNavigate(screenProps.navigation.symbol)
        }
    }

    render() {
        return (
            <StockSearch
                onItemSelected={this.processItemSelected}
            />
        );
    }
}
export default AppStockSearch;