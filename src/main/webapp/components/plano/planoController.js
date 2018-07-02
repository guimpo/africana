var app = angular.module('africana');

app.controller('planoController', ['$scope', '$http', '$location', '$log', 'planoService',
  function ($scope, $http, $location, $log, planoService) {
    
    $log.info('controller');
    var self = $scope;

    self.planoLista = [];
    
    self.dto = planoService.getStore();
    
    var url = $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/api/plano';

    $http.get(url)
    .then(function (response) {
      $log.info("ctrl response");
      $log.info(response.data);
      self.planoLista = response.data;
    }, function (errResponse) {
      $log.info(errResponse);
      $log.error("erro");
    }); 
            
    self.ver = function(plano) {
      $log.info(plano);
      planoService.store(plano);
      $location.path('/plano/edicao');
    };

    self.editar = function(plano) {
      $log.info('plano put');
      $log.info(plano);
      $http.put(url, plano)
        .then(
          function(response) {
            $log.log('atualizou');
            $log.log(response);
            $location.path('/plano/lista');
          }, function(err) {
              $log.log(err);
            });
    };
}]);
