function internalErrorController($scope, $cookies, urlService, logoutService){

	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}

}