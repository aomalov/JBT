//AngularJS App

var couponApp = angular.module('myApp', ['ui.bootstrap','ngRoute']);

//ROUTING

couponApp.config(function ($routeProvider) {
   
    $routeProvider
    
    .when('/', {
        templateUrl: 'index.html', 
        controller: 'mainController'
    })
    
    .when('/signin', {
        templateUrl: 'login.htm',
        controller: 'mainController'
    })
    
    .when('/logout', {
        templateUrl: 'login.htm',
        controller: 'mainController'
    })
    
    .when('/listCompanies', {
        templateUrl: 'index.html',
        controller: 'companyController'
    })
        
});

//CONTROLLERS

couponApp.controller('mainController', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {

    $scope.handle = '';

    $scope.lowercasehandle = function () {
        return $filter('lowercase')($scope.handle);
    };
  
    $scope.characters = 5;
    
    $scope.rules=[{RuleName:"rule1"},{RuleName:"rule2"}];
    
    var aCompany = { COMP_NAME: "Nike", EMAIL: "@@", ID: 3770, PASSWORD: "pASSWORD" };
    
    //console.log(aCompany);

    
}]);

couponApp.controller('companyController', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
    $http.get('rest/company' )
        .success(function (result) {

            console.log(result);
            $scope.templateUrl="index.html"

        })
        .error(function (data, status) {

            console.log(data);

        });
}]);
