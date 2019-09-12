import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';

class AppStockSearch extends Component {

    processItemSelected = (item) => {
        this.props.navigation.navigate('Detail', { symbol: item.symbol });
    }

    render() {
        console.log(this.initialProps)
        return (
            <StockSearch
                onItemSelected={this.processItemSelected}
            />
        );
    }
}
export default AppStockSearch;