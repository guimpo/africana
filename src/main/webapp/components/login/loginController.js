app.controller('loginController', function ($scope, $http, $location, $route, $log) {
    var self = $scope;
    self.login = function () {
      var url = $location.protocol() + '://'+ $location.host() +':'+  $location.port()  + '/login';
      $http.post(url, self.usuario)
        .then(function(resp) {
          $log.info(resp);
        }, function(err) {
          $log.error(err);
        });
      };
      
    self.resetForm = function () {
        
    };
});
