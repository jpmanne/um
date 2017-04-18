function changePasswordController($scope, $location, $cookies, $http, urlService, logoutService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	$scope.passwordSuccess = false;

	$scope.passCheck = function(){
		if($scope.passwordDetails.newPassword === $scope.passwordDetails.confirmPassword){
			$scope.passMsg = "";
			$scope.passwordMatch = true;
		}
		else{
			$scope.passMsg = "Password not matched.";
		}
	};
	
	$scope.changePassword = function(){
		
		console.log($scope.passwordDetails);
		$scope.passwordSuccess = true;
		
		$http({
			method: 'POST',
			url   : urlService.changePasswordUrl(),
			data  : $scope.passwordDetails,
			params: {authCode:res.responsePayload.authCode}
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				console.log(data.message);
				$scope.userFail = false;
				$scope.passwordSuccess = true;
				$scope.passwordDetails = {};
				}   
		    else if(data.responseCode == 400){
				
				$scope.passwordSuccess = false;
				$scope.userFail = true;
				$scope.passwordDetails = {};
				console.log(data);
				}
		}).error(function(data,status){
			
			console.log(data);
			$scope.passwordDetails = {};
			$location.path("/internalError");
	    });
	}
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}