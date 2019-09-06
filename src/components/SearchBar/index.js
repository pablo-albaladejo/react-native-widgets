import React, { Component } from 'react';
import { View, TextInput } from 'react-native';
import styles from './styles';

import _ from 'lodash';
import Icon from "react-native-vector-icons/MaterialIcons";

class SearchBar extends Component {
    render() {
        return (
            <View style={styles.container}>
                <TextInput
                    style={styles.input}
                    placeholder={'Search stock'}
                    onChangeText={_.debounce(this.props.onChangeText, 1000)}
                />
                <Icon
                    name="search"
                    color="#ccc"
                    size={25}
                />
            </View>
        )
    }
}
export default SearchBar;