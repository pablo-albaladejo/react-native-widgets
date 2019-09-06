import React, { Component } from 'react';
import { Text, View, ActivityIndicator } from 'react-native';
import * as StockService from '../../services/stocks';
import Layout from '../Layout';

class StockDetail extends Component {

    state = {
        quote: null,
        isFetching: false,
    }

    componentDidMount = () => {

        this.setState({
            stock: null,
            isFetching: true,
        })

        StockService.getStockQuote(this.props.stock.symbol)
            .then(res => {
                this.setState({
                    quote: res,
                    isFetching: false
                })
            }).catch(err => {
                this.setState({
                    quote: null,
                    isFetching: false
                })
                alert(err)
            });
    }


    render() {
        return (
            <Layout>
                {this.state.isFetching && (
                    <ActivityIndicator size="large" color="#0000ff" />
                )}
                {!this.state.isFetching && this.state.quote && (
                    <View>
                        <Text>{'Name: ' + this.props.stock.name}</Text>
                        <Text>{'type: ' + this.props.stock.type}</Text>
                        <Text>{'region: ' + this.props.stock.region}</Text>
                        <Text>{'marketOpen: ' + this.props.stock.marketOpen}</Text>
                        <Text>{'marketClose: ' + this.props.stock.marketClose}</Text>
                        <Text>{'timezone: ' + this.props.stock.timezone}</Text>
                        <Text>{'currency: ' + this.props.stock.currency}</Text>

                        <Text>{'open: ' + this.state.quote.open}</Text>
                        <Text>{'high: ' + this.state.quote.high}</Text>
                        <Text>{'low: ' + this.state.quote.low}</Text>
                        <Text>{'price: ' + this.state.quote.price}</Text>
                        <Text>{'volume: ' + this.state.quote.volume}</Text>
                        <Text>{'change: ' + this.state.quote.change}</Text>

                    </View>
                )}
            </Layout>
        )
    }
}

export default StockDetail;
