import { AppRegistry } from 'react-native';
import App from './src/navigation';
import { name as appName } from './app.json';
import WidgetStockSearch from './src/screens/WidgetStockSearch';
import WidgetJSTask from './src/widget'

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent("WidgetStockSearch", () => WidgetStockSearch);
AppRegistry.registerHeadlessTask("WidgetJSTask", () => WidgetJSTask);
