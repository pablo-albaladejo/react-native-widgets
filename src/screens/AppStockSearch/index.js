import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';

class AppStockSearch extends Component {

    processItemSelected = (item) => {
        this.props.navigation.navigate('Detail', { stock: item });
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