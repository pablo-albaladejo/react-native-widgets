import React, { Component } from 'react';
import StockSearch from '../../components/StockSearch';
import { BackHandler, NativeModules } from 'react-native'

const { RNStockModule } = NativeModules
class WidgetStockSearch extends Component {

    processItemSelected = (stock) => {
        RNStockModule.selectStock({
            symbol: stock.symbol,
            appWidgetId: this.props.payload.appWidgetId
        }, () => {
            BackHandler.exitApp();
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