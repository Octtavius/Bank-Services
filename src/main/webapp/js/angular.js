angular.module('bankwebpage', [])
.controller('Test', function($scope, $http) {
    $http.get('http://localhost:3000/bankingonline/tests/who').
        then(function(response) {
            $scope.userDetails = response.data;
        });
});
