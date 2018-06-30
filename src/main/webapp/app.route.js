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
  .when('/plano/cadastro', {
    templateUrl: 'template/cadastroPlano.html',
    controller: 'planoCadastroController'
  })
  .when('/plano/lista', {
    templateUrl: 'components/plano/listaPlanoView.html',
    controller: 'planoController'
  })
  .otherwise({redirectTo: '/'});
}]);