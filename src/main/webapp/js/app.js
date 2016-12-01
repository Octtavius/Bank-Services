app = angular.module('bankwebpage', ['ui.utils.masks']);

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


// Define Trasnfer Form
app.controller('TransferForm', function ($scope, $http) {
    $scope.formTransferData = {};

    $scope.processForm = function () {
        console.log("Processing transfer form...");
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/transfer',
            data: $.param({
                'accountId': '1',
                'amount': $scope.formTransferData.amount,
                'recipientAccount': $scope.formTransferData.destAccountId,
                'recipientSortCode': '11'
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            console.log(response);

            if (response.status !== 200) {
                $scope.message = "ERROR: No account found!!!"
            } else {
                //console.log("Name: " + response.data.response);
                $scope.message = response.data.response;
            }
            
        });
    };
});