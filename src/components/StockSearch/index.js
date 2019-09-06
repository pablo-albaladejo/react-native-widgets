import React, { Component } from 'react';

import * as StockService from '../../services/stocks';

import Layout from '../Layout';

import SearchBar from '../SearchBar';
import StockList from '../StockList';

class StockSearch extends Component {
    state = {
        stocks: [],
        isFetching: false,
        text: ''
    }

    searchStock = (str) => {
        this.setState({
            isFetching: true,
            text: str
        })

        StockService.searchStock(str).then(res => {
            this.setState({
                stocks: res,
                isFetching: false
            })
        }).catch(err => {
            this.setState({
                stocks: [],
                isFetching: false
            })
            alert(err)
        })
    }

    render() {
        return (
            <Layout
                header={
                    <SearchBar
                        onChangeText={this.searchStock}
                    />
                }
            >
                <StockList
                    stocks={this.state.text !== '' ? this.state.stocks : null}
                    isFetching={this.state.isFetching}
                    onItemSelected={this.props.onItemSelected}
                />
            </Layout >
        );
    }
}
export default StockSearch;