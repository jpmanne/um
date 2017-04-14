function updateSecondaryAuthorizationController($scope, $location, $cookies, $http, urlService, logoutService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
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
		
	$scope.updateSuccess = false;
	$scope.updateFail = false;
	$scope.questionsVisible = true;
	
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
	
	$scope.update = function(){
		
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
				url   : urlService.secondaryAuthUpdateUrl(),
				data  : $scope.UserSecondaryAuthorizationDetails,
				params: {authCode:res.responsePayload.authCode}
			}).success(function(data,status){
				
				console.log(data);
				if(data.responseCode == 200){
					
					if(data.message == "User secondary authorization updated successfully"){
						
						$scope.msg = data.message;
						
						$scope.updateSuccess = true;
						$scope.questionsVisible = false;
						
						$scope.updateFail = false;
						
					}
					
					else{
						
						$scope.msg = data.message;
						
						$scope.updateFail = true;
						$scope.questionsVisible = true;
						
						$scope.updateSuccess = false;
						
						$scope.selectedFirst = null;
						$scope.firstAnswer = null;
						$scope.selectedSecond = null;
						$scope.secondAnswer = null;
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