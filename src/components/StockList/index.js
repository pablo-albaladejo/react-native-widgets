import React from 'react';
import { View, Text, ActivityIndicator } from 'react-native';
import styles from './styles';
import StockItem from '../StockItem';

const StockList = (props) =>
    <View style={styles.container}>
        <View style={styles.content}>
            {props.isFetching && (
                <ActivityIndicator size="large" color="#0000ff" />
            )}
            {!props.isFetching && props.stocks &&
                props.stocks.map((stock, index) => (
                    <StockItem
                        key={"stock_item_" + index.toString()}
                        stock={stock}
                        onItemSelected={props.onItemSelected}
                    />
                ))}
            {!props.isFetching && props.stocks && props.stocks.length === 0 && (
                <Text>{'No results found'}</Text>
            )}
        </View>
    </View>

export default StockList;