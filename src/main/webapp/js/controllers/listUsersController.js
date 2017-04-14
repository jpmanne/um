function listUsersController($scope, $cookies, $http, $timeout, $location, urlService, logoutService, successStatusService){
	
	var res = $cookies.getObject('signInResult');
	if(res.responsePayload.middleName == null){
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.lastName;
	}else{
		
		$scope.loginUserName = res.responsePayload.firstName+" "+res.responsePayload.middleName+" "+res.responsePayload.lastName;
	}
	
	
	if(successStatusService.getStatus()){
		
		$scope.userRegSuccess = true;
		
		$timeout(function(){
			
			$scope.userRegSuccess = false;
		}, 5000);
	}
	
	if(successStatusService.getUserEditStatus()){
		
		$scope.userUpdateSuccess = true;
		
		$timeout(function(){
			
			$scope.userUpdateSuccess = false;
		}, 5000);
	}
	
	$scope.actiateSuccess = false;
	$scope.deactiateSuccess = false;
	
	$scope.loading = true;
	$scope.resetPasswordLoading = false;
	$scope.usersListLoading = true;
	$scope.noUsersPresent = false;
	$scope.usersPresent = false;
	
	var activateUserId;
	var deactivateUserId;
	var resedPasswordId;
		
	$http({		
			method: 'GET',
			url   : urlService.listUsersUrl(),
			params: {authCode:res.responsePayload.authCode}
		}).success(function(data,status){
			
			$scope.loading = false;
			$scope.usersListLoading = false;
			
			if(data.responsePayload.length == 0){
				
				$scope.noUsersPresent = true;
				$scope.usersPresent = false;
			}else{
				
				$scope.noUsersPresent = false;
				$scope.usersPresent = true;
			}
			$scope.output = data;
			
		}).error(function(data,status){			   
			$scope.output = data;
			$location.path("/internalError");
		});
			   
	$scope.sortColumn = 'name';
	$scope.reverseSort = false;
	
	$scope.sortData = function (column) {
		$scope.reverseSort = ($scope.sortColumn == column) ? !$scope.reverseSort : false;
		$scope.sortColumn = column;
	};
	
	$scope.getSortClass = function (column) {
		if($scope.sortColumn == column) {
			return $scope.reverseSort ? 'arrow_down' : 'arrow_up';
		}
		return '';
	};
	
	var activateElement = angular.element('#activateModal');
	
	activateElement.modal('hide');
	
	$scope.activateUserModal = function(id){
		
		console.log("activate modal");
		
		activateUserId = id;
		
		activateElement.modal('show');
	}
	
	var deactivateElement = angular.element('#deactivateModal');
	
	deactivateElement.modal('hide');
	
	$scope.deactivateUserModal = function(id){
		
		console.log("deactivate modal");
		
		deactivateUserId = id;
		
		deactivateElement.modal('show');
	}
	
	var resetPasswordElement = angular.element('#resetPassswordModal');
	
	resetPasswordElement.modal('hide');
	
	$scope.resetPasswordUserModal = function(id){
		
		console.log("reset password modal");
		
		resedPasswordId = id;
		
		resetPasswordElement.modal('show');
	}
	
	$scope.activateUser = function(){
		
		console.log("Entered into api");
		
		id = activateUserId
		
		console.log(id);
		
		$http({
			method: 'GET',
			url   : urlService.activateUserUrl(),
			params: {authCode:res.responsePayload.authCode, userDetailsId:id}
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				console.log(data.message);
				
				$scope.deactiateSuccess = false;
				$scope.actiateSuccess = true;
				
				listUsersApiCalling();
				
				$timeout(function(){$scope.actiateSuccess = false;},5000);
				
				}   
		    else if(data.responseCode == 400){
				
				console.log(data);
				}
		}).error(function(data,status){
			$scope.output = data;
			$location.path("/internalError");
	    });
	}
	
	$scope.deactivateUser = function(){
		
		console.log("Entered into api");
		
		id = deactivateUserId;
		
		console.log(id);
		
		$http({
			method: 'GET',
			url   : urlService.deactivateUserUrl(),
			params: {authCode:res.responsePayload.authCode, userDetailsId:id}
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				console.log(data.message);
				
				$scope.actiateSuccess = false;
				$scope.deactiateSuccess = true;
				
				listUsersApiCalling();
				
				$timeout(function(){$scope.deactiateSuccess = false;},5000);
				
			}else if(data.responseCode == 400){
				
				console.log(data);
			}
		}).error(function(data,status){
			$scope.output = data;
			$location.path("/internalError");
	    });
	}
	
	$scope.resetPasswordUser = function(){
		
		console.log("Entered into api");
		
		id = resedPasswordId;
		
		console.log(id);
		
		$scope.loading = true;
		$scope.resetPasswordLoading = true;
		$scope.usersListLoading = false;
		
		$http({
			method: 'POST',
			url   : urlService.resetPasswordUrl(),
			params: {authCode:res.responsePayload.authCode, userDetailsId:id}
		}).success(function(data,status){
			if(data.responseCode == 200){
				
				console.log(data);
				console.log(data.message);
				
				$scope.loading = false;
				$scope.resetPasswordLoading = false;
				$scope.userResetPasswordSuccess = true;
				
				listUsersApiCalling();
				
				$timeout(function(){$scope.userResetPasswordSuccess = false;},5000);
				
			}else if(data.responseCode == 400){
				
				console.log(data);
				$scope.resetPasswordLoading = false;
				$scope.loading = false;
			}
		}).error(function(data,status){
			$scope.output = data;
			$location.path("/internalError");
	    });
	}
	
	var listUsersApiCalling = function(){
		
		$http({		
			method: 'GET',
			url   : urlService.listUsersUrl(),
			params: {authCode:res.responsePayload.authCode}
		}).success(function(data,status){
			$scope.loading = false;
			$scope.usersListLoading = false;
			$scope.output = data;				
		}).error(function(data,status){			   
			$scope.output = data;
			$location.path("/internalError");
		});
	}
	
	$scope.editUser = function(id){
		
		console.log(id);
		$cookies.putObject('userId',id);
		$location.path("/editUser");
	}
	
	$scope.logout = function(){
				
		var urlValue = urlService.logoutUrl();
		
		logoutService.userLogout(res, urlValue);
	}
}