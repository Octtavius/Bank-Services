app = angular
        .module('appBankWebPage', ['ui.utils.masks', 'ngRoute', 'ngCookies'])
        .config(config)
        .run(run)
        .controller('LoginController', loginCtrl)
        .controller('NewAccountController', newAccountCtrl)
        .controller('MainController', mainCtrl)
        .controller('LogoutController', logoutCtrl);

var API_SERVER_URL = "http://localhost:3000/bankingonline/main";

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
    if ($rootScope.globals.userLoggedIn) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.userLoggedIn.authdata; // jshint ignore:line
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login', '/newaccount']) === -1;
        var loggedIn = $rootScope.globals.userLoggedIn;
        if (restrictedPage && !loggedIn) {
            console.log("User not logged in...");
            $location.path('/login');
        }
    });
}
/*------------- Controllers --------------------------------------------------*/

/* Test Controller */
app.controller('TestController', function ($scope, $http) {
    $http.get(API_SERVER_URL + '/bankingonline/tests/who').
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
            url: API_SERVER_URL + '/login',
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
                    $rootScope.globals.userDetails = {
                        'accountId': $scope.formLoginData.accountId,
                        'firstName': data.firstName,
                        'middleName': data.middleName,
                        'lastName': data.lastName,
                        'address': data.address,
                        'email': data.email,
                        'contactNumber': data.contactNumber
                    };
                    // Set Account Details
                    $rootScope.globals.accountDetails = {};
                    $rootScope.globals.accountDetails.accountId = $scope.formLoginData.accountId;
                    $rootScope.globals.userLoggedIn = true;
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
    $rootScope.globals.userLoggedIn = false;
    $cookieStore.put('globals', '');
    
    $location.path("/");
};


/*  New Account Controller */
newAccountCtrl.$inject = ['$rootScope', '$scope', '$location', '$cookieStore', '$http'];
function newAccountCtrl($rootScope, $scope, $location, $cookieStore, $http) {
    console.log("Processing newAccountCtrl...");
    $scope.formNewAccountData = {};
    
    $scope.createAccount = function () {
        console.log("Processing login form...");
        $http({
            method: 'POST',
            url: API_SERVER_URL + '/createAccount',
            data: $.param({
                'firstName': $scope.formCreateAccoundData.firstName,
                'middleName': $scope.formCreateAccoundData.middleName,
                'lastName': $scope.formCreateAccoundData.lastName,
                'password': $scope.formCreateAccoundData.password,
                'email': $scope.formCreateAccoundData.email,
                'address': $scope.formCreateAccoundData.address,
                'contactNumber': $scope.formCreateAccoundData.contactNumber
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
                    console.log("Account created successfully...");
                    data = response.data;
                    $rootScope.globals.userDetails = {
                        'accountId': $scope.formLoginData.accountId,
                        'firstName': data.firstName,
                        'middleName': data.middleName,
                        'lastName': data.lastName,
                        'address': data.address,
                        'email': data.email,
                        'contactNumber': data.contactNumber
                    };
                    
                    // Redirect page
                    $location.path("/");
                } else {
                    $scope.message = "Please check the details";
                }
            }
        });
    };
};

/* Main Controller */
mainCtrl.$inject = ['$rootScope', '$scope', '$location', '$cookieStore', '$http'];
function mainCtrl($rootScope, $scope, $location, $cookieStore, $http) {
    console.log("Processing loginCtrl...");
    getBalance($rootScope, $scope, $cookieStore, $http);
    getTransactions($rootScope, $scope, $cookieStore, $http);
};


// Define Account Actions Form Controller
app.controller('AccountActionsFormController', function ($rootScope, $scope, $cookieStore, $http) {
    $rootScope.globals = $cookieStore.get('globals') || {};
    $scope.formActionsData = {};
    $scope.formActionsData.confirm = false;
    
    $scope.processForm = function () {
        console.log("Processing transfer form...");
        console.log("Action execute: " + $scope.formActionsData.action);
        
        action = "";
        params = {};
        
        params.accountId = $rootScope.globals.userDetails.accountId;
        params.amount = $scope.formActionsData.amount;
        
        switch($scope.formActionsData.action) {
            case "lodgment":
                action = "/lodgment";
                break;
            case "withdraw":
                action = "/withdraw";
                break;
            case "transfer":
                if($scope.formActionsData.confirm == true) {
                    action = "/transferExecute";
                } else {
                    action = "/checkAccountById";
                }
                params.recipientAccount = $scope.formActionsData.destAccountId;
                break;
            default:
                console.log("Unknown requested action");
                break;
        }
        if (action != '') {
            $http({
                method: 'POST',
                url: API_SERVER_URL + action,
                data: $.param(params),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (response) {
                //console.log(response);
                if (response.status == 400) {
                    console.log('No account found...');
                    $scope.message = "ERROR: No account found!!!";
                } else if(response.status == 200) {
                    if(action = "transfer" && $scope.formActionsData.confirm == false) {
                        $scope.formActionsData.confirm = true;
                        $scope.message = "Recipient Name: " + response.data.response;
                    } else {
                        $scope.message = "Transfer completed";
                        $scope.accountActions.$setPristine();
                        $scope.accountActions.selectAction.$setUntouched();
                    }
                    
                    console.log('Refresh UI data...');
                    getBalance($rootScope, $scope, $cookieStore, $http);
                    getTransactions($rootScope, $scope, $cookieStore, $http);
                } else {
                    $scope.message = "ERROR: an error has occurred!!!";
                }
            });
        }
        
        
    };
    

    
});

// get transactions list
getTransactions.$inject = ['$rootScope', '$scope', '$cookieStore', '$http'];
function getTransactions($rootScope, $scope, $cookieStore, $http) {
    $rootScope.globals = $cookieStore.get('globals') || {};
    $scope.loadData = function () {
        console.log('Loading transaction data...');
        $scope.transactionList = [];
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/getTransactions',
            data: $.param({
                'accountId': $rootScope.globals.accountDetails.accountId
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
getBalance.$inject = ['$rootScope', '$scope', '$cookieStore', '$http'];
function getBalance($rootScope, $scope, $cookieStore, $http) {
    $rootScope.globals = $cookieStore.get('globals') || {};
    $scope.loadData = function () {
        $http({
            method: 'POST',
            url: 'http://localhost:3000/bankingonline/main/balance',
            data: $.param({
                'accountId': $rootScope.globals.accountDetails.accountId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            $rootScope.globals.accountDetails.balance = response.data.response;
        });
    };
    
    // Initial run
    $scope.loadData();
};