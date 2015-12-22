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
        controller: 'loginController'
    })
    
    .when('/logout', {
        templateUrl: 'login.htm',
        controller: 'mainController'
    })
    
    .when('/listCompanies', {
        templateUrl: 'company_report.htm',
        controller: 'companyController'
    })
    .when('/newCompany', {
        templateUrl: 'company_form.htm',
        controller: 'companyNewController'
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

couponApp.controller('loginController', ['$scope', '$filter', '$http','restResponseService', function ($scope, $filter, $http,restResponseService) {
	
	$scope.doLogin = function() {
        var aUser = { userName: $scope.userName, password: $scope.password, clientType: $scope.clientType };
        $http.post('rest/logon',aUser)
        .success(function (result) {
            restResponseService.messageText = result.messageText;
            restResponseService.messageType = result.messageType;
            $scope.$emit('eventRestResponse');
            console.log(result);
        })
        .error(function (data, status) {
            console.log(status);
            restResponseService.messageText  = "Error during logon";
            restResponseService.messageType  = "danger";
            $scope.$emit('eventRestResponse');
            //window.location.href = '/CouponsysREST/'; 
        });
		
	};
    
}]);
couponApp.controller('companyNewController', ['$scope', '$filter', '$http','restResponseService', function ($scope, $filter, $http,restResponseService) {
	
	$scope.createCompany = function() {
		var aCompany = { COMP_NAME: $scope.companyName, EMAIL: $scope.companyEmail, ID: 0, PASSWORD: $scope.password };
		console.log(aCompany);
        
        $http.post('rest/company/new',aCompany)
        .success(function (result) {
            restResponseService.messageText = result.messageText;
            restResponseService.messageType = result.messageType;
            //$scope.$emit('eventRestResponse');
            console.log(result);
        })
        .error(function (data, status) {
            console.log(status);
            restResponseService.messageText  = "Error creating a company";
            restResponseService.messageType  = "danger";
            //window.location.href = '/CouponsysREST/'; 
        });
		
	};
    
}]);

couponApp.controller('companyController', ['$scope', '$filter', '$http','restResponseService', function ($scope, $filter, $http,restResponseService) {
	
    $scope.getCompanies = function() {
    	if($scope.totalItems>0)
          return $scope.companyList.slice(($scope.currentPage-1)*$scope.itemsPerPage,$scope.currentPage*$scope.itemsPerPage);
    };
    
    $scope.totalItems = 0;
        
    $http.get('rest/company/all' )
        .success(function (result) {

            console.log(result);
            if(result.messageText) {
                restResponseService.messageText=result.messageText;
                restResponseService.messageType=result.messageType;
            	console.log(result);
            }
            else {
	            $scope.companyList = result.company;
	            $scope.totalItems = $scope.companyList.length;
	            $scope.itemsPerPage = 3;
	            $scope.currentPage = 1;
            }
        })
        .error(function (data, status) {
            console.log(status);
            //window.location.href = '/CouponsysREST/'; 
        });
}]);

couponApp.controller('restResponseController', ['$scope', 'restResponseService', function($scope, restResponseService) {
    
    $scope.$watch(function () { return restResponseService.messageText; }, function() {
      $scope.messageText = restResponseService.messageText;
      $scope.messageType = restResponseService.messageType;
    });
    
    $scope.$watch('messageText', function() {
       restResponseService.messageText = $scope.messageText; 
    });

    // $scope.$on('eventRestResponse', function() {
    //   $scope.messageText = restResponseService.messageText;
    //   $scope.messageType = restResponseService.messageType;    
    //  });
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


// SERVICES
couponApp.service('restResponseService', function() {
   
    this.messageText = "Welcome to Couponius !";
    this.messageType = "success";
    
});
