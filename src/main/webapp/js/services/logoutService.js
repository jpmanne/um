function logoutService($http, $cookies, $location, successStatusService){
	
	this.userLogout = function(res, urlValue){
		
		/*console.log(res.responsePayload.authCode);*/
		
		var logoutDetails = new FormData();
		
		logoutDetails.append('authCode', res.responsePayload.authCode);
		
		var url = urlValue;
		
		$http.post(url, logoutDetails, {
			withCredentials : false,
			headers : {
			'Content-Type' : undefined
			},
			transformRequest : angular.identity
			})
			.success(function(data){
				
				console.log(data);
				if(data.responseCode == 200){
					
					console.log($cookies.getObject('signInResult'));
					$cookies.remove('signInResult');
					$cookies.remove('userId');
					$cookies.remove('secondaryAuthorizationCompleted');
					console.log($cookies.getObject('signInResult'));
					console.log($cookies.getObject('userId'));
					console.log($cookies.getObject('secondaryAuthorizationCompleted'));
					
					$cookies.putObject('signOutResult',true);
					
					if(!successStatusService.getSessionExpireStatus()){
						
						successStatusService.setUserLogoutStatus(true);
					}
		
					$location.path("/login");
				}
				
			}).error(function(data){
				
			console.log(data);
			});
	}
	
	
}