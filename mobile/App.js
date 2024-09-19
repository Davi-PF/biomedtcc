import React, { useEffect } from 'react';
import {StatusBar, StyleSheet, View} from 'react-native';
import Toast from 'react-native-toast-message';
import messaging from '@react-native-firebase/messaging';
import { PermissionsAndroid } from 'react-native';

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
import NotificationTab from './src/pages/NotificationTab';


const Stack = createStackNavigator();

import { Linking, ActivityIndicator } from 'react-native';

const NAVIGATION_IDS = [
  'Login', 
  'Home', 
  'RegisterOrChangeUser', 
  'EmailCheck', 
  'Register', 
  'FindDependentLocally', 
  'AccessRecovery', 
  'ChangePassword', 
  'NotificationTab'
];

function buildDeepLinkFromNotificationData(data) {
  const navigationId = data?.navigationId;
  
  if (!NAVIGATION_IDS.includes(navigationId)) {
    console.warn('Unverified navigationId', navigationId);
    return null;
  }

  // Gerar deep link baseado no navigationId
  switch (navigationId) {
    case 'Login':
      return 'myapp://Login';
    case 'Home':
      return 'myapp://Home';
    case 'RegisterOrChangeUser':
      return 'myapp://RegisterOrChangeUser';
    case 'EmailCheck':
      return 'myapp://EmailCheck';
    case 'Register':
      return 'myapp://Register';
    case 'FindDependentLocally':
      return 'myapp://FindDependentLocally';
    case 'AccessRecovery':
      return 'myapp://AccessRecovery';
    case 'ChangePassword':
      return 'myapp://ChangePassword';
    case 'NotificationTab':
      return 'myapp://NotificationTab';
    default:
      console.warn('Unhandled navigationId', navigationId);
      return null;
  }
}


const linking = {
  prefixes: ['myapp://'],
  config: {
    screens: {
      Login: 'Login',
      Home: 'Home',
      RegisterOrChangeUser: 'RegisterOrChangeUser',
      EmailCheck: 'EmailCheck',
      Register: 'Register',
      FindDependentLocally: 'FindDependentLocally',
      AccessRecovery: 'AccessRecovery',
      ChangePassword: 'ChangePassword',
      NotificationTab: 'NotificationTab',
      Settings: 'Settings',
    },
  },
  async getInitialURL() {
    const url = await Linking.getInitialURL();
    if (typeof url === 'string') {
      return url;
    }

    // getInitialNotification: Quando o aplicativo é aberto a partir de um estado quitado.
    const message = await messaging().getInitialNotification();
    const deeplinkURL = buildDeepLinkFromNotificationData(message?.data);
    if (typeof deeplinkURL === 'string') {
      return deeplinkURL;
    }
  },
  subscribe(listener) {
    const onReceiveURL = ({url}) => listener(url);

    // Ouve links de entrada via deep linking
    const linkingSubscription = Linking.addEventListener('url', onReceiveURL);

    // background messages
    messaging().setBackgroundMessageHandler(async remoteMessage => {
      console.log('Message handled in the background!', remoteMessage);
      // Handle background messages here
    });

    const foreground = messaging().onMessage(async remoteMessage => {
      console.log('A new FCM message arrived!', remoteMessage)});

    // onNotificationOpenedApp: Quando o aplicativo está em execução, mas em segundo plano.
    const unsubscribe = messaging().onNotificationOpenedApp(remoteMessage => {
      const url = buildDeepLinkFromNotificationData(remoteMessage.data);
      if (typeof url === 'string') {
        listener(url);
      }
    });

    return () => {
      linkingSubscription.remove();
      unsubscribe();
      foreground();
    };
  },
};


export default function App() {

  useEffect(() => {
    const requestUserPermission = async () => {
      PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.POST_NOTIFICATIONS,
      );
      const authStatus = await messaging().requestPermission();
      const enabled =
        authStatus === messaging.AuthorizationStatus.AUTHORIZED ||
        authStatus === messaging.AuthorizationStatus.PROVISIONAL;

      if (enabled) {
        console.log('Authorization status:', authStatus);
        const token = await messaging().getToken();
        console.log('FCM token:', token);

        requestUserPermission();
      }
    };
  }, []);

  return (
      <UserProvider>
        <View style={styles.container}>
          <NavigationContainer linking={linking}>
            <StatusBar style='auto' backgroundColor={COLORS.BLUE_MAIN} />
            <Stack.Navigator
              initialRouteName='NotificationTab'
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
              <Stack.Screen name='NotificationTab' component={NotificationTab} />
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
