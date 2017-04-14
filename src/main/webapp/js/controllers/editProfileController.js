function editProfileController($scope, $cookies, $http, $location, urlService, logoutService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	/*previous routing page start*/
	
	var previousUrl;
	
	$scope.$on('$locationChangeStart', function(event, newUrl, oldUrl){
		
		console.log("Old url is "+oldUrl);
		console.log("New url is "+newUrl);
		
		console.log(oldUrl.indexOf("#"));
		/*console.log(oldUrl.substring(oldUrl.indexOf("#")+1,oldUrl.length+1));*/
		
		previousUrl = oldUrl.substring(oldUrl.indexOf("#")+1,oldUrl.length+1);
		
	});
	
	/*previous routing page end*/
	
	var allUsersDetails = [];
	
	/*$scope.countries = ["Australia","Brazil","Germany","India","USA"];*/
	
	$scope.countries = {Australia:"AUS", Canada:"CAN", France:"FRA", India:"IND", USA:"USA"};
	
	$scope.userFail = false;
	$scope.updateResult = false;
	
	
		$http({		
			method: 'GET',
			url   : urlService.userByIdUrl(),
			params: {userDetailsId:res.responsePayload.userDetailsId,authCode:res.responsePayload.authCode}
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
				$scope.userFail = false;
				$scope.updateResult = true;
				console.log(data);
				}   
		    else if(data.responseCode == 400){
				$scope.userFail = true;
				}
		}).error(function(data,status){
			$scope.output = data;
			$location.path("/internalError");
	    });
	}
	
	/*$scope.reset = function(){
		console.log("Data reset successfully.");
		$scope.currentUser = {};
		console.log($scope.currentUser);
	};*/
	
	$scope.cancel = function(){
		
		$location.path(previousUrl);
	}
	
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}