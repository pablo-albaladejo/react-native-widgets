import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import AppStockSearch from '../screens/AppStockSearch';
import AppStockDetail from '../screens/AppStockDetail';

const MainNavigator = createStackNavigator(
    {
        Search: {
            screen: AppStockSearch,
            navigationOptions: {
                header: null,
            }
        },
        Detail: { screen: AppStockDetail },
    },
    {
        initialRouteName: 'Search',
    }
);

const App = createAppContainer(MainNavigator);

export default App;