import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';
import { BackHandler, NativeModules } from 'react-native'
import * as StockService from '../../services/stocks'
const { RNStockModule } = NativeModules
class WidgetStockSearch extends Component {

    processItemSelected = (stock) => {
        StockService.getStockQuote(stock.symbol)
        .then(res => {
            RNStockModule.selectStock(
                res,
                this.props.payload.appWidgetId
                , BackHandler.exitApp);
        });
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