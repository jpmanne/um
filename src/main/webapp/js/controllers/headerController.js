function headerController($scope, $cookies){

	var res = $cookies.getObject('signInResult');
	
	console.log(res);
	
	$scope.roleId = res.responsePayload.roleId;
	$scope.defaultPassword = res.responsePayload.isDefaultPasswordChanged;
	
	if($scope.secondaryAuthorization == 1){
		
		$scope.secondaryAuthorization = 1;
	}else{
		
		$scope.secondaryAuthorization = res.responsePayload.isSecondaryAuthorizationSaved;
	}
	
	
	if($scope.secondaryAuthorization == 0){
		
		if($cookies.getObject('secondaryAuthorizationCompleted')){
			
			console.log("Entered after completing secondary Authorization");
			
			$scope.defaultPassword = 1;
			$scope.secondaryAuthorization = 1;
		}
	}
	
	console.log("defaultPassword: "+$scope.defaultPassword);
	console.log("secondaryAuthorization: "+$scope.secondaryAuthorization);
}
