function urlService($location){
	//this.baseUrl = $location.protocol() + '://' + $location.host() + ':' + $location.port() + '/um/api';
	this.baseUrl = 'http://localhost:8081/um/api';
	this.registrationUrl = function(){
	
		return this.baseUrl + '/api/user/signup';
	}
	
	this.isUserExistUrl = function(){
	
		return this.baseUrl + '/api/user/isexist';
	}
	
	this.changePasswordUrl = function(){
	
		return this.baseUrl + '/api/user/changepassword';
	}
	
	this.resetPasswordUrl = function(){
	
		return this.baseUrl + '/api/user/resetpassword';
	}
	
	this.changeDefaultPasswordUrl = function(){
	
		return this.baseUrl + '/api/user/firsttimelogin/changepassword';
	}
	
	this.loginUrl = function(){
	
		return this.baseUrl + '/api/user/login';
	}
	
	this.listUsersUrl = function(){
	
		return this.baseUrl + '/api/user/getall';
	}
	
	this.activateUserUrl = function(){
	
		return this.baseUrl + '/api/user/activate';
	}
	
	this.deactivateUserUrl = function(){
	
		return this.baseUrl + '/api/user/delete';
	}
	
	this.userByIdUrl = function(){
	
		return this.baseUrl + '/api/user/get';
	}
	
	this.profileUpdateUrl = function(){
	
		return this.baseUrl + '/api/user/update';
	}
	
	this.secondaryAuthQuestionsListUrl = function(){
	
		return this.baseUrl + '/api/user/secondaryauthorizationdetails/quesitions/get';
	}
	
	this.secondaryAuthSaveUrl = function(){
	
		return this.baseUrl + '/api/user/usersecondaryauthorizationdetails/save';
	}
	
	this.secondaryAuthUpdateUrl = function(){
	
		return this.baseUrl + '/api/user/usersecondaryauthorizationdetails/update';
	}
	
	this.logoutUrl = function(){
		
		return this.baseUrl + '/api/user/logout';
	}
	
	
}