import { AppRegistry } from 'react-native';
import App from './src/navigation';
import { name as appName } from './app.json';
import WidgetStockSearch from './src/screens/WidgetStockSearch';

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent("WidgetStockSearch", () => WidgetStockSearch);
