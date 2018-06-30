app.controller('loginController', function ($scope, $http, $location, $route, $log) {
    var self = $scope;
    self.login = function () {
      $log.info(self.usuario.email);
      $log.info(self.usuario.senha);
      var url = $location.absUrl();
      $log.info(url);
      
      $http({
        method: 'POST',
        url: $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/africana/login',
        params: {email: self.usuario.email, senha: self.usuario.senha}
      }).then(function (response) {
        var credentials = btoa(self.usuario.email + ":" + self.usuario.senha);
        localStorage.setItem("credentials", credentials);
        $log.info("logado");
        return $location.path('/plano');
        
      }, function (errResponse) {
          $log.info(errResponse);
          $log.error("erro");
      });
    }
    self.resetForm = function () {
        
    };
});

app.controller('planoController', function ($scope, $http, $location, $route, $log) {
    var self = $scope;
    
    self.planoLista = [];
    
    $http({
        method: 'GET',
        url: $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/africana/plano/lista',
//          params: {email: self.usuario.email, senha: self.usuario.senha}
    }).then(function (response) {
      $log.info(response.data);
      self.planoLista = response.data;

    }, function (errResponse) {
        $log.info(errResponse);
        $log.error("erro");
    });
    
    self.editar = function(id) {
      $http({
        method: 'GET',
        url: $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/africana/plano/lista',
//          params: {email: self.usuario.email, senha: self.usuario.senha}
    }).then(function (response) {
      $log.info(response.data);
      self.planoLista = response.data;

    }, function (errResponse) {
        $log.info(errResponse);
        $log.error("erro");
    });
    }
});

app.controller('planoCadastroController', function ($scope, $http, $location, $route) {
    $scope.cadastroPlano = function () {
        $http({
          method: 'POST',
          url: $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/africana/plano/cadastro',
          params: {
            componenteCurricular: $scope.componenteCurricular,
            tema: $scope.tema,
            objetivos: $scope.objetivos,
            duracao: $scope.duracao,
            conhecimentosPrevios: $scope.conhecimentosPrevios,
            recursos: $scope.recursos,
            descricao: $scope.descricao,
            avaliacao: $scope.avaliacao
          }
      }).then(function (response) {
//          $location.path("");
//          $route.reload();
//        var credentials = btoa(self.usuario.email + ":" + self.usuario.senha);
//        localStorage.setItem("credentials", credentials);
//        $log.info("logado");
        return $location.path('/plano');
        
      }, function (errResponse) {
          $log.info(errResponse);
          $log.error("erro");
      });
    }
    $scope.resetForm = function () {
        
    };
});

app.controller('usuarioController', function ($scope, $http, $location, $route) {});