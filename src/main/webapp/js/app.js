var app = angular.module('africana', ['ngRoute']);

app.config(['$locationProvider', function($locationProvider) {
  $locationProvider.hashPrefix('');
}]);

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: './template/login.html',
    controller: 'loginController'
  })
  .when('/usuario', {
    templateUrl: 'template/cadastroUsuario.html',
    controller: 'usuarioController'
  })
  .when('/plano', {
    templateUrl: 'template/listaPlano.html',
    controller: 'planoController'
  })
  .when('/plano/cadastro', {
    templateUrl: 'template/cadastroPlano.html',
    controller: 'planoCadastroController'
  })
  .when('/plano/lista', {
    templateUrl: 'template/listaPlano.html',
    controller: 'planoController'
  })
  .otherwise({redirectTo: '/'});
}]);