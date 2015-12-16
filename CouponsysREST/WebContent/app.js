//AngularJS App

var couponApp = angular.module('myApp', ['ui.bootstrap','ngRoute']);

//ROUTING

couponApp.config(function ($routeProvider) {
   
    $routeProvider
    
    .when('/', {
        templateUrl: 'index.html', 
        controller: 'mainController'
    })
    
    .when('/login', {
        templateUrl: 'login.htm',
        controller: 'mainController'
    })
    
    .when('/logout', {
        templateUrl: 'login.htm',
        controller: 'mainController'
    })
    
    .when('/listCompanies', {
        templateUrl: 'company_report.htm',
        controller: 'companyController'
    });
        
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
	
    $scope.getCompanies = function() {
    	if($scope.totalItems>0)
          return $scope.companyList.slice(($scope.currentPage-1)*$scope.itemsPerPage,$scope.currentPage*$scope.itemsPerPage);
    };
    
    $scope.totalItems = 0;
    
    $http.get('rest/company/all' )
        .success(function (result) {

        	console.log("result");
            console.log(result);
            if(result.error) 
//TODO - display alert message with bootstrap
            	console.log(result);
            else {
	            $scope.companyList = result.company;
	            $scope.totalItems = $scope.companyList.length;
	            $scope.itemsPerPage = 3;
	            $scope.currentPage = 1;
            }
            //$scope.templateUrl="index.html"

        })
        .error(function (data, status) {

        	console.log("data");
            console.log(data);
            console.log("status");
            console.log(status);
            $scope.totalItems = 0;
            //$scope.templateUrl="page1.htm";
            window.location.href = '/CouponsysREST/'; 

        });
}]);

// DIRECTIVES

couponApp.directive("companyReport", function() {
   return {
       restrict: 'E',
       templateUrl: 'company_card.htm',
       replace: true,
       scope: {
           comp: "=company"
       }
   }
});
