app.config(['$routeProvider', function($routeProvider) {
  $routeProvider
  .when('/', {
    templateUrl: './components/home/home.html'
  })      
  .when('/login', {
    templateUrl: './components/login/loginView.html',
    controller: 'loginController'
  })
  .when('/usuario', {
    templateUrl: 'template/cadastroUsuario.html',
    controller: 'usuarioController'
  })
  .when('/plano/edicao', {
    templateUrl: 'components/plano/formPlanoView.html',
    controller: 'planoController'
  })
  .when('/plano/cadastro', {
    templateUrl: 'components/plano/formPlanoView.html',
    controller: 'planoController'
  })
  .when('/plano/lista', {
    templateUrl: 'components/plano/listaPlanoView.html',
    controller: 'planoController'
  })
  .otherwise({redirectTo: '/'});
}]);