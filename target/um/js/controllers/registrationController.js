function registrationController($scope, $location, $cookies, $http, urlService, logoutService, successStatusService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}

	$scope.userDetails = {};
	/*$scope.tempAddr = {};
	$scope.permAddr ={};
	$scope.userDetails.addressDetails = [];*/
	/*$scope.countries = ["Australia","Brazil","Germany","India","USA"];*/
	$scope.countries = {Australia:"AUS", Canada:"CAN", France:"FRA", India:"IND", USA:"USA"};
	/*$scope.anniversary = false;
	$scope.permAddrVisible = false;*/
	$scope.conditions = false;
	/*$scope.submitted = false;*/
	$scope.userFail = false;

	$scope.passCheck = function(){
		if($scope.userDetails.password === $scope.confpass){
			$scope.passMsg = "";
		}
		else{
			$scope.passMsg = "Password not matched.";
		}
	};
	
	/*$scope.marrStatus = function(){
		if($scope.userDetails.maritalStatus === "m"){
			return $scope.anniversary = true;
		}
		else{
			console.log("not married");
			return $scope.anniversary = false,
				   $scope.userDetails.anniversaryDate = null;
		}
	};*/
	
	/*$scope.permAddrValid = function(){
		if($scope.permAddrValidation == "yes"){
			$scope.permAddrVisible = false;
			
			$scope.permAddr = {};
			
			$scope.tempAddr.addressType = "p";
			
			if($scope.userDetails.addressDetails.length == 0){
				
				$scope.userDetails.addressDetails.push($scope.tempAddr);
			}
			
			else if($scope.userDetails.addressDetails.length == 2){
				
				$scope.userDetails.addressDetails.splice(1,1);
			}
			
			else{
				
				$scope.userDetails.addressDetails.length = 0;
			}
			
		}
		
		if($scope.permAddrValidation == "no"){
			$scope.permAddrVisible = true;
			
			$scope.tempAddr.addressType = "t";
			$scope.permAddr.addressType = "p";
			
			if($scope.userDetails.addressDetails.length == 0){
				$scope.userDetails.addressDetails.push($scope.tempAddr);
				$scope.userDetails.addressDetails.push($scope.permAddr);
			}
			
			else if($scope.userDetails.addressDetails.length == 2){}
			
			else{
				
				$scope.userDetails.addressDetails.length = 0;
			}
			
		}
	};*/
	
	/*$scope.agree = function(){
		if($scope.submitted){
			console.log("true changes to false");
			return $scope.submitted = false;
		}
	
		else{
			console.log($scope.submitted);
			console.log("false changes to true");
			return $scope.submitted = true;
		}
	};*/
	
	$scope.userExist = function(){
		
		console.log($scope.userDetails.userName);
		
		if(!angular.isUndefined($scope.userDetails.userName)){
			
			$http({		
			method: 'GET',
			url   : urlService.isUserExistUrl(),
			params: {userName:$scope.userDetails.userName,authCode:res.responsePayload.authCode}
			}).success(function(data,status){
			
			console.log(data);
			$scope.userExistError = data.success;
						
			}).error(function(data,status){			   
			console.log(data);				
			});
		}else{
			
			$scope.userExistError = false;
		}
		
	}
	
	$scope.submit = function(){
		
		console.log($scope.userDetails);
		
		$http({
			method: 'POST',
			url   : urlService.registrationUrl(),
			data  : $scope.userDetails,
			params: {authCode:res.responsePayload.authCode}
		   
		}).success(function(data,status){
			if(data.responseCode == 200){
				$scope.userFail = false;
				console.log(data);
				
				successStatusService.setStatus(true);
				$location.path("/listUsers");
				
				}   
		    else if(data.responseCode == 400){
				$scope.userFail = true;
				}
		}).error(function(data,status){
			$scope.output = data;
			$location.path("/internalError");
	    });
	};
	
	$scope.reset = function(){
		console.log("Data reset successfully.");
		$scope.userDetails = {};
		/*$scope.userDetails.addressDetails = [];
		$scope.tempAddr = {};
		$scope.permAddr = {};*/
		$scope.confpass = null;
		$scope.role = null;
		/*$scope.anniversary = false;
		$scope.permAddrValidation = null;
		$scope.permAddrVisible = false;*/
		$scope.conditions = false;
		/*$scope.submitted = false;*/
		$scope.userFail = false;
		
		console.log($scope.userDetails);
		
		console.log(angular.element('#postal_code_tb').val());
		angular.element('#username_tb').val("");
		angular.element('#password_tb').val("");
		angular.element('#conf_password_tb').val("");
		angular.element('#email_tb').val("");
		angular.element('#cc_email_tb').val("");
		angular.element('#phn_number_tb').val("");
		angular.element('#addr_line1_tb').val("");
		angular.element('#city_tb').val("");
		angular.element('#state_tb').val("");
		angular.element('#postal_code_tb').val("");
		console.log("Postal code value is: "+angular.element('#postal_code_tb').val());
	};
	
	/*$scope.cancel = function(){
		console.log("Back to login page.");
		$location.path("/login");
	};*/
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}