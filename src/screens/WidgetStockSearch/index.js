import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';

class WidgetStockSearch extends Component {

    processItemSelected = (item) => {
        //this.props.navigation.navigate('Detail', { stock: item });
        alert(JSON.stringify(item))
    }

    render() {
        return (
            <StockSearch
                onItemSelected={this.processItemSelected}
            />
        );
    }
}
export default WidgetStockSearch;