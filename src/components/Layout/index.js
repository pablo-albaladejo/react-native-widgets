
import React, { Fragment } from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    View,
} from 'react-native';
import styles from './styles';

const Layout = (props) =>
    <Fragment >
        <StatusBar/>
        <SafeAreaView style={styles.container}>
            {props.header}
            <ScrollView>
                {props.children}
            </ScrollView>
        </SafeAreaView>
    </Fragment >

export default Layout;