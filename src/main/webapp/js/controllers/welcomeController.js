function welcomeController($scope, $http, $cookies, $location, $timeout, urlService, logoutService){
	
	var res = $cookies.getObject('signInResult');
	console.log(res);
	var id = res.responsePayload.userDetailsId;
	
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
			
			$location.path("/welcome");
		}
		
		if(newUrl.indexOf("#/secondaryAuthorization") !== -1){
			
			$location.path("/welcome");
		}
	});
	
	/*back button disable end*/
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}