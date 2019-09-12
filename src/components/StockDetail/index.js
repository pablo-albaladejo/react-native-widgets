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

        StockService.getStockQuote(this.props.symbol)
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
