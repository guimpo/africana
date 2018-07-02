var app = angular.module('africana');

app.controller('usuarioController', function ($scope, $http, $location, $log) {
    var self = $scope;
    self.cadastrar = function () {
      var url = $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/api/usuario';
      $http.post(url, self.usuario)
        .then(function(resp) {
          $log.info(resp);
          $location.path('/plano/lista');
        }, function(err) {
          $log.error(err);
        });
      };
      
    self.resetForm = function () {
        
    };
});