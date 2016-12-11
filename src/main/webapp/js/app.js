app = angular
        .module('bankwebpage', ['ui.utils.masks', 'ngRoute', 'ngCookies'])
        .config(config)
        .run(run)
        .controller('LoginController', loginCtrl)
        .controller('MainController', mainCtrl)
        .controller('LogoutController', logoutCtrl);
/*
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
 */

config.$inject = ['$routeProvider', '$locationProvider'];
function config($routeProvider, $locationProvider) {
    $routeProvider
            .when('/', {
                controller: 'MainController',
                templateUrl: 'pages/main.html'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'pages/login.html'
            })

            .when('/logout', {
                controller: 'LogoutController',
                templateUrl: 'pages/login.html'
            })

            .when('/newaccount', {
                controller: 'NewAccountController',
                templateUrl: 'pages/newaccount.html'
            })

            .otherwise({redirectTo: '/login'});
}

run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
function run($rootScope, $location, $cookieStore, $http) {
    // keep user logged in after page refresh
    $rootScope.globals = $cookieStore.get('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login', '/newaccount']) === -1;
        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
            console.log("User not logged in...");
            $location.path('/login');
        }
    });
}
/*------------- Controllers --------------------------------------------------*/

/* Test Controller */
app.controller('Test', function ($scope, $http) {
    $http.get('http://localhost:3000/bankingonline/tests/who').
            then(function (response) {
                $scope.userDetails = response.data;
            });
    $scope.currentDate = Date;
});

/*  Login Controller */
loginCtrl.$inject = ['$rootScope', '$scope', '$location', '$cookieStore', '$http'];
function loginCtrl($rootScope, $scope, $location, $cookieStore, $http) {
    console.log("Processing loginCtrl...");
    $scope.formLoginData = {};
    $scope.userDetails = {};
    $rootScope.globals = $cookieStore.get('globals') || {};
    
    $scope.login = function () {
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
                $scope.message = "ERROR: occurred an error";
            } else {

                if(response.data != 'null' || response.data != '') {
                    //console.log("Name: " + response.data.response);                
                    console.log("Login successfull...");
                    data = response.data;
                    $rootScope.userDetails = {
                        'accountId': $scope.formLoginData.accountId,
                        'firstName': data.firstName,
                        'middleName': data.middleName,
                        'lastName': data.lastName,
                        'address': data.address,
                        'email': data.email,
                        'contactNumber': data.contactNumber
                    };
                    
                    $rootScope.globals.currentUser = true;
                    $cookieStore.put('globals', $rootScope.globals);
                    
                    // Redirect page
                    $location.path("/");
                } else {
                    $scope.message = "Username / Password incorrect";
                }
            }
        });
    };
};


/*  Logout Controller */
logoutCtrl.$inject = ['$rootScope', '$scope', '$location', '$cookieStore', '$http'];
function logoutCtrl($rootScope, $scope, $location, $cookieStore, $http) {
    console.log("Processing logoutCtrl...");    
    $rootScope.globals.currentUser = false;
    $cookieStore.put('globals', '');
    
    $location.path("/");
};


/* Main Controller */
mainCtrl.$inject = ['$rootScope', '$scope', '$location', '$cookieStore', '$http'];
function mainCtrl($rootScope, $scope, $location, $cookieStore, $http) {
    console.log("Processing loginCtrl...");
    getBalance($rootScope, $scope, $http);
    getTransactions($rootScope, $scope, $http);
};


// Define Transfer Form
app.controller('TransferForm', function ($rootScope, $scope, $http) {
    $scope.formTransferData = {};

    $scope.processForm = function () {
        console.log("Processing transfer form...");
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/transfer',
            data: $.param({
                'accountId': $rootScope.userDetails.accountId,
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

// get transactions list
getTransactions.$inject = ['$rootScope', '$scope', '$http'];
function getTransactions($rootScope, $scope, $http) {
    $scope.loadData = function () {
        console.log('Loading transaction data...');
        $scope.transactionList = [];
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/getTransactions',
            data: $.param({
                'accountId': $rootScope.userDetails.accountId
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

};

// get balance
getTransactions.$inject = ['$rootScope', '$scope', '$http'];
function getBalance($rootScope, $scope, $http) {
    $scope.loadData = function () {
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/balance',
            data: $.param({
                'accountId': '303'
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            $scope.accountDetails = response.data;
        });
    };
    
    // Initial run
    $scope.loadData();
};