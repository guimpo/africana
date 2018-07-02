var app = angular.module('africana', [
  'ngRoute'
]);

app.config(['$locationProvider', function($locationProvider) {
  $locationProvider.hashPrefix('');
}]);