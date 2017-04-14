function editUserController($scope, $cookies, $http, $location, urlService, logoutService, successStatusService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	/*$scope.countries = ["AUS","CAN","Germany","India","USA"];*/
	
	$scope.countries = {Australia:"AUS", Canada:"CAN", France:"FRA", India:"IND", USA:"USA"};
	
	$scope.userUpdate = false;
	
		$http({		
			method: 'GET',
			url   : urlService.userByIdUrl(),
			params: {userDetailsId:$cookies.getObject('userId'),authCode:res.responsePayload.authCode}
		}).success(function(data,status){
			
			console.log(data);
			
			$scope.currentUser = data.responsePayload;
			console.log($scope.currentUser)
			
		}).error(function(data,status){			   
			console.log(data);				
		});
	
	
	$scope.update = function(){
		
		console.log($scope.currentUser);
		
		$http({
			method: 'POST',
			url   : urlService.profileUpdateUrl(),
			data  : $scope.currentUser,
			params: {authCode:res.responsePayload.authCode}
		   
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				$scope.userUpdate = false;
				successStatusService.setUserEditStatus(true);
				$location.path("/listUsers");
				}   
		    else if(data.responseCode == 400){
				
				$scope.userUpdate = true;
				}
		}).error(function(data,status){
			
			console.log(data);
			$location.path("/internalError");
	    });
	}
	
	/*$scope.reset = function(){
		console.log("Data reset successfully.");
		$scope.currentUser = {};
		console.log($scope.currentUser);
	};*/
	
	$scope.cancel = function(){
		
		$location.path("/listUsers");
	}
	
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}