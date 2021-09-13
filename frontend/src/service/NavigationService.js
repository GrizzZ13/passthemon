import {CommonActions, StackActions} from '@react-navigation/native';
//5.x以下import { NavigationActions ,StackActions} from 'react-navigation';

let navigator;
function setTopLevelNavigator(navigatorRef) {
  navigator = navigatorRef;
}

function navigate(routeName, params = {}) {
  navigator.dispatch(
    CommonActions.navigate({
      name: routeName,
      params: params,
    }),
  );
}

function reset(routeName) {
  navigator.dispatch(StackActions.replace(routeName));
}

function goBack() {
  navigator.dispatch(CommonActions.goBack());
}

export default {
  navigate,
  reset,
  goBack,
  setTopLevelNavigator,
};
