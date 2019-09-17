import { NativeModules } from 'react-native';
const { RNStockModule } = NativeModules

export default async (taskData) => {
    console.log('RNHeadless', taskData)
};