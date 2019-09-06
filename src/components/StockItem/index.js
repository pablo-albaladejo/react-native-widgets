import React from 'react';
import { TouchableOpacity, Text } from 'react-native';
import styles from './styles';

const StockItem = (props) =>
    <TouchableOpacity
        style={styles.container}
        onPress={() => props.onItemSelected(props.stock)}
    >
        <Text>
            {[props.stock.symbol, props.stock.name].join(' - ')}
        </Text>
    </TouchableOpacity>

export default StockItem;