import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';
import { BackHandler, NativeModules } from 'react-native'

const { RNStockModule } = NativeModules
class WidgetStockSearch extends Component {

    processItemSelected = (item) => {
        RNStockModule.selectStock(item);
        BackHandler.exitApp();
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