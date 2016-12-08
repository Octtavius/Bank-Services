app = angular.module('bankwebpage', ['ui.utils.masks', 'ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
            .when("/", {
                templateUrl: "pages/login.html"
            })
            .when("/main", {
                templateUrl: "pages/main.html"
            })
            .when("/newaccount", {
                templateUrl: "pages/newaccount.html"
            });
    $routeProvider.otherwise({redirectTo: '/'});
});

/*------------- Controllers --------------------------------------------------*/

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


// Define Login Form
app.controller('LoginForm', function ($scope, $location, $http) {
    $scope.formLoginData = {};
    $scope.userDetails = {};
    
    $scope.processForm = function () {
        console.log("Processing login form...");
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/login',
            data: $.param({
                'accountId': $scope.formLoginData.accountId,
                'password': $scope.formLoginData.password
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }

        }).then(function (response) {
            console.log(response);

            if (response.status !== 200) {
                $scope.message = "ERROR: No account found!!!";
            } else {

                //console.log("Name: " + response.data.response);                
                console.log("Login successfull...");
                data = response.data;
                
                $scope.userDetails = {
                    'accountId': '303',
                    'firstName': data.firstName,
                    'middleName': data.middleName,
                    'lastName': data.lastName,
                    'address': data.address,
                    'email': data.email,
                    'contactNumber': data.contactNumber
                };

                // Redirect page
                $location.path("/SuccessPage");
            }
        });
    };
    
});


// Define Transfer Form
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
                $scope.message = "ERROR: No account found!!!";
            } else {
                //console.log("Name: " + response.data.response);
                $scope.message = response.data.response;
            }

        });
    };
});

// Set controller to get transactions list
app.controller('TransactionsTable', function ($scope, $http) {
    $scope.loadData = function () {
        console.log('Loading transaction data...');
        $scope.transactionList = [];
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/getTransactions',
            data: $.param({
                'accountId': '1'
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            $scope.transactionList = response.data;
        });
    };

    // Initial controller run
    $scope.loadData();

});