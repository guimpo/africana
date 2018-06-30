app.controller('planoController', function ($scope, $http, $location, $route, $log) {
    var self = $scope;
    
    self.planoLista = [];
    
    var url = $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/api/plano';
    
    $http.get(url)
      .then(function (response) {
        $log.info(response.data);
        self.planoLista = response.data;
      }, function (errResponse) {
        $log.info(errResponse);
        $log.error("erro");
      });
});
