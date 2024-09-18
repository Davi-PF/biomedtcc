import React from 'react';
import {StatusBar, StyleSheet, View} from 'react-native';
import Toast from 'react-native-toast-message';

import {
  NavigationContainer,
  useNavigationState,
} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';


import Footer from './src/components/Footer';
import Header from './src/components/Header';

import Login from './src/pages/Login';
import Home from './src/pages/Home';
import EmailCheck from './src/pages/EmailCheck';
import RegisterOrChangeUser from './src/pages/RegisterOrChangeUser';
import Register from './src/pages/Register';
import FindDependentLocally from './src/pages/FindDependentLocally';
import AccessRecovery from './src/pages/AccessRecovery';
import ChangePassword from './src/pages/ChangePassword';


import {UserProvider} from './src/contexts/UserContext';
import {COLORS} from './src/constants/constants';


const Stack = createStackNavigator();

export default function App() {
  return (
    <UserProvider>
      <View style={styles.container}>
        <NavigationContainer>
          <StatusBar style='auto' backgroundColor={COLORS.BLUE_MAIN} />
          <Stack.Navigator
            initialRouteName='Login'
            screenOptions={{
              headerShown: false,
            }}>
            <Stack.Screen name='Login' component={Login} />
            <Stack.Screen name='Home' component={Home} />
            <Stack.Screen
              name='RegisterOrChangeUser'
              component={RegisterOrChangeUser}
            />
            <Stack.Screen name='EmailCheck' component={EmailCheck} />
            <Stack.Screen name='Register' component={Register} />
            <Stack.Screen
              name="FindDependentLocally"
              component={FindDependentLocally}
            />
            <Stack.Screen name='AccessRecovery' component={AccessRecovery} />
            <Stack.Screen name='ChangePassword' component={ChangePassword} />
          </Stack.Navigator>
          <Header />
          {/* <Toast ref={(ref) => Toast.setRef(ref)} /> */}
          <Footer />
        </NavigationContainer>
      </View>
    </UserProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
