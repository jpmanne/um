function secondaryAuthorizationController($scope, $location, $cookies, $http, $timeout, urlService, logoutService, successStatusService){
	
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
		
		if(newUrl.indexOf("#/changeDefaultPassword") !== -1){
			
			$location.path("/secondaryAuthorization");
		}
		
		signOutButton = $cookies.getObject('signOutResult');
		
		if((newUrl.indexOf("#/login") !== -1) && (!signOutButton)){
			
			$location.path("/secondaryAuthorization");
		}
		
	});
	
	/*back button disable end*/
	
	if(successStatusService.getDefaultPasswordStatus()){
		
		$scope.defaultPasswordSuccess = true;
		
		$timeout(function(){
			
			$scope.defaultPasswordSuccess = false;
		}, 5000);
	}
	
	
	$http({
			method: 'POST',
			url   : urlService.secondaryAuthQuestionsListUrl(),
			params: {userDeatilsId:res.responsePayload.userDetailsId}
		}).success(function(data,status){
			
			console.log(data);
			if(data.responseCode == 200){
				
				$scope.firstQuestion = data.responsePayload.secondaryAuthDetailsFirstQuesitionsGet;
				$scope.secondQuestion = data.responsePayload.secondaryAuthDetailsSecondQuesitionsGet;
				
			}
		}).error(function(data,status){
			
			console.log(data);
			$location.path("/internalError");
	    });
	
	$scope.firstQuestionErrMsg = false;
	$scope.secondQuestionErrMsg = false;
	
	$scope.removeErrMsgOne = function(){
		
		$scope.firstQuestionErrMsg = false;
		$scope.firstAnswer = null;
	}
	
	$scope.removeErrMsgTwo = function(){
		
		$scope.secondQuestionErrMsg = false;
		$scope.secondAnswer = null;
	}
	
	$scope.save = function(){
		
		/*console.log($scope.selectedFirst);*/
		
		if(angular.isUndefined($scope.selectedFirst)){
			
			$scope.firstQuestionErrMsg = true;
			$scope.secondQuestionErrMsg = false;
		}
		
		if(angular.isUndefined($scope.selectedSecond)){
			
			$scope.firstQuestionErrMsg = false;
			$scope.secondQuestionErrMsg = true;
		}
		
		if(angular.isUndefined($scope.selectedFirst) && angular.isUndefined($scope.selectedSecond)){
			
			$scope.firstQuestionErrMsg = true;
			$scope.secondQuestionErrMsg = true;
		}
		
		if(($scope.selectedFirst != null) && ($scope.selectedSecond != null)){
			
			$scope.firstQuestionErrMsg = false;
			$scope.secondQuestionErrMsg = false;
			
			$scope.UserSecondaryAuthorizationDetails = {
													userDetailsId:res.responsePayload.userDetailsId,
													firstQuesitiondId:$scope.selectedFirst.secondaryAuthDetailsId,
													firstQuesitionAnswer:$scope.firstAnswer,
													secondQuesitiondId:$scope.selectedSecond.secondaryAuthDetailsId,
													secondQuesitionAnswer:$scope.secondAnswer
												   };
		
			console.log($scope.UserSecondaryAuthorizationDetails);
			
			$http({
				method: 'POST',
				url   : urlService.secondaryAuthSaveUrl(),
				data  : $scope.UserSecondaryAuthorizationDetails,
				params: {authCode:res.responsePayload.authCode}
			}).success(function(data,status){
				
				console.log(data);
				if(data.responseCode == 200){
					
					if(data.message == "User secondary authorization added successfully"){
						
						$cookies.putObject('secondaryAuthorizationCompleted',true);
						
						$location.path("/welcome");
						
					}
				}
			}).error(function(data,status){
				
				console.log(data);
				$location.path("/internalError");
			});
		}
		
		
	}
	
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}