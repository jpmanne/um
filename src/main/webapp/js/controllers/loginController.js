function loginController($scope, $location, $cookies, $http, urlService, successStatusService){
	
	/*back button disable start*/
	
	var backButtonLogin = false;
	
	$scope.$on('$locationChangeStart', function(event, next, current){
		
		console.log($cookies.getObject('signOutResult'));
		
		backButtonLogin = $cookies.getObject('signOutResult');

		if((backButtonLogin) && (current.indexOf("#/login") !== -1)){
			
			event.preventDefault();
			
			backButtonLogin = false;
			/*$cookies.remove('signOutResult');*/
		}

		/*test*/
		console.log(current.indexOf("#/login") !== -1);
		console.log("Current url is "+current);
		console.log("Next url is "+next);
		/*test*/
	});
	
	$scope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl){
		
		console.log("Old url is "+oldUrl);
		console.log("New url is "+newUrl);
		
	});
	
	/*back button disable end*/
	
	/*User logout msg start*/
	
	$scope.logoutMsg = false;
	
	if(successStatusService.getUserLogoutStatus()){
		
		$scope.logoutMsg = true;
	}
	
	/*User logout msg end*/
	
	/*Session expire msg start*/
	
	$scope.sessionExpireMsg = false;
	
	if(successStatusService.getSessionExpireStatus()){
		
		$scope.sessionExpireMsg = true;
	}
	
	/*Session expire msg end*/
		
	$scope.validCheck = false;
	$scope.passwordMsg = false;
	$scope.usernameMsg = false;
	
	$scope.login = function(){
		/*console.log($scope.user);*/
		
		if(angular.isUndefined($scope.user)){
			
			$scope.usernameMsg = true;
			$scope.passwordMsg = true;
		}
		
		/*console.log($scope.user.userName);*/
		
		if(angular.isUndefined($scope.user.userName)){
			
			$scope.passwordMsg = false;
			$scope.usernameMsg = true;
		}
		
		/*console.log($scope.user.password);*/
		
		if(angular.isUndefined($scope.user.password)){
			
			$scope.passwordMsg = true;
			$scope.usernameMsg = false;
		}
		
		
		if(($scope.user.userName != null) && ($scope.user.password != null)){
			
			$http({
			method: 'POST',
			url   : urlService.loginUrl(),
			data  : $scope.user,
		    headers: 'application/json'
		}).success(function(data,status){
			if(data.responseCode == 200){
				$scope.validCheck = false;
				$scope.activeCheck = false;
				$scope.logoutMsg = false;
				$scope.sessionExpireMsg = false;
				$scope.passwordMsg = false;
				$scope.usernameMsg = false;
				$cookies.putObject('signInResult',data);
				console.log(data);
				
				$cookies.remove('signOutResult');
				
				if(angular.equals(data.message, "User login is successful")){
					
					console.log("login success");
					$location.path("/welcome");
					
					/* change default password start
					if((data.responsePayload.isDefaultPasswordChanged == 1) && (data.responsePayload.isSecondaryAuthorizationSaved == 1)){
						
						$location.path("/welcome");
						
					}else if((data.responsePayload.isDefaultPasswordChanged == 0) && (data.responsePayload.isSecondaryAuthorizationSaved == 0)){
						
						$location.path("/changeDefaultPassword");
					}else if((data.responsePayload.isDefaultPasswordChanged == 1) && (data.responsePayload.isSecondaryAuthorizationSaved == 0)){
						
						$location.path("/secondaryAuthorization");
					}
					
					change default password end*/
					
				}else{
					
					$location.path("/login");
					$scope.activeCheck = true;
					$scope.user = null;
				}
				
				}   
		    else if(data.responseCode == 400){
				$location.path("/login");
				$scope.activeCheck = false;
				$scope.validCheck = true;
				$scope.logoutMsg = false;
				$scope.sessionExpireMsg = false;
				$scope.passwordMsg = false;
				$scope.usernameMsg = false;
				$scope.user = null;
				}
		}).error(function(data,status){
			/*$scope.output = data;*/
			
			$location.path("/internalError");
	    });
			
		}
	}
	
	$scope.removeMsgs = function(){
		
		console.log("removing");
		
		$scope.activeCheck = false;
		$scope.validCheck = false;
		$scope.logoutMsg = false;
		$scope.sessionExpireMsg = false;
		$scope.passwordMsg = false;
		$scope.usernameMsg = false;
	}
	
}