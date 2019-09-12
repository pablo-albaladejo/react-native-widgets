import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';
import { BackHandler, NativeModules } from 'react-native'

const { RNStockModule } = NativeModules
class WidgetStockSearch extends Component {

    processItemSelected = (stock) => {
        RNStockModule.selectStock(
            stock.symbol,
            this.props.payload.appWidgetId
            , BackHandler.exitApp);
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