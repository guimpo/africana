app.controller('loginController', function ($scope, $http, $location, $log) {
    var self = $scope;
    self.login = function () {
      var url = $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/api/login';
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
