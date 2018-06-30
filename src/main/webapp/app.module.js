var app = angular.module('africana', ['ngRoute', 'ngCookies', 'ngResource']);

app.config(['$locationProvider', function($locationProvider) {
  $locationProvider.hashPrefix('');
}]);