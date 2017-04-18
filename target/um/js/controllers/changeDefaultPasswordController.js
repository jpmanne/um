function changeDefaultPasswordController($scope, $location, $cookies, $http, urlService, logoutService, successStatusService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	/*back button disable start*/
	
	var signOutButton = false;
	
	$scope.$on('$locationChangeStart', function(event, newUrl, oldUrl){
		
		console.log("Old url is "+oldUrl);
		console.log("New url is "+newUrl);
		
		signOutButton = $cookies.getObject('signOutResult');
		
		if((newUrl.indexOf("#/login") !== -1) && (!signOutButton)){
			
			$location.path("/changeDefaultPassword");
		}
	});
	
	/*back button disable end*/
	
	$scope.changeFail = false;
	
	$scope.passCheck = function(){
		if($scope.changePassword.newPassword === $scope.changePassword.confirmPassword){
			$scope.passMsg = "";
			$scope.passwordMatch = true;
		}
		else{
			$scope.passMsg = "Password not matched.";
			$scope.passwordMatch = false;
		}
	};
	
	$scope.changeDefaultPassword = function(){
		
		$http({
			method: 'POST',
			url   : urlService.changeDefaultPasswordUrl(),
			data  : $scope.changePassword,
			params: {authCode:res.responsePayload.authCode}
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				$scope.msg = "";
				$scope.changeFail = false;
				
				successStatusService.setDefaultPasswordStatus(true);
				$location.path("/secondaryAuthorization");
				
			}else if(data.responseCode == 400){
				
				console.log(data);
				$scope.msg = data.message;
				$scope.changeFail = true;
				$scope.passwordMatch = false;
				$scope.changePassword = {};				
			}
		}).error(function(data,status){
			
			console.log(data);
			$scope.changePassword = {};
			$location.path("/internalError");
	    });
	}
	
	
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}