var app = angular.module('myApp', []);

app.controller('myCntrl', function($scope, $http) {

	$scope.getJson = function(URI){
	    $http({
	        method : "GET",
	        url : "http://localhost:4400/rest/2.0" + URI
	    }).then(function (response) {
	        $scope.instanceInfo = response.data;
	    }, function (response) {
	        $scope.instanceInfo = response.statusText;
	    });
	}

});