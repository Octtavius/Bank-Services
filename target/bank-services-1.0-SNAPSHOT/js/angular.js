app = angular.module('bankwebpage', []);

/* Test Controller */
app.controller('Test', function ($scope, $http) {
    $http.get('http://localhost:3000/bankingonline/tests/who').
            then(function (response) {
                $scope.userDetails = response.data;
            });
    $scope.currentDate = Date;
});


/* Balance Controller */
app.controller('Balance', function ($scope, $http) {
    $http({
        method: 'POST',
        url: 'http://localhost:3000/bankingonline/main/balance',
        data: $.param({
            'accountId': '1'
        }),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(function (response) {
        $scope.accountDetails = response.data;
    });
});
