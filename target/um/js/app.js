var app = angular.module("formApp", ["ui.router", "ngCookies", "angularUtils.directives.dirPagination", "ngStorage"]);

app.config(function($urlRouterProvider, $stateProvider){

	$urlRouterProvider.otherwise("/login");
	
	$stateProvider
	
	.state("login",{
					
					url:"/login",
					templateUrl:"pages/login.html",
					controller:"loginController"})
					
	.state("registration",{
	
						   url:"/registration",
						   templateUrl:"pages/registration.html",
						   controller:"registrationController",
						   authenticate:true})
						   
	.state("welcome",{
	
						   url:"/welcome",
						   templateUrl:"pages/welcome.html",
						   controller:"welcomeController",
						   authenticate:true})
						   
	.state("listUsers",{
	
						   url:"/listUsers",
						   templateUrl:"pages/listUsers.html",
						   controller:"listUsersController",
						   authenticate:true})
		
	.state("editProfile",{
	
						   url:"/editProfile",
						   templateUrl:"pages/editProfile.html",
						   controller:"editProfileController",
						   authenticate:true})
						   
	.state("editUser",{
	
						   url:"/editUser",
						   templateUrl:"pages/editUser.html",
						   controller:"editUserController",
						   authenticate:true})
						   
	.state("changePassword",{
	
						   url:"/changePassword",
						   templateUrl:"pages/changePassword.html",
						   controller:"changePasswordController",
						   authenticate:true})
						   
	.state("internalError",{
	
						   url:"/internalError",
						   templateUrl:"pages/internalError.html",
						   controller:"internalErrorController",
						   authenticate:true})
						   
	.state("changeDefaultPassword",{
	
						   url:"/changeDefaultPassword",
						   templateUrl:"pages/changeDefaultPassword.html",
						   controller:"changeDefaultPasswordController",
						   authenticate:true})
						   
	.state("secondaryAuthorization",{
	
						   url:"/secondaryAuthorization",
						   templateUrl:"pages/secondaryAuthorization.html",
						   controller:"secondaryAuthorizationController",
						   authenticate:true})
						   
	.state("updateSecondaryAuthorization",{
	
						   url:"/updateSecondaryAuthorization",
						   templateUrl:"pages/updateSecondaryAuthorization.html",
						   controller:"updateSecondaryAuthorizationController",
						   authenticate:true})
});



app.controller('loginController',loginController);
app.controller('headerController',headerController);
app.controller('welcomeController',welcomeController);
app.controller('listUsersController',listUsersController);
app.controller('editProfileController',editProfileController);
app.controller('editUserController',editUserController);
app.controller('changePasswordController',changePasswordController);
app.controller('registrationController',registrationController);
app.controller('internalErrorController',internalErrorController);
app.controller('changeDefaultPasswordController',changeDefaultPasswordController);
app.controller('secondaryAuthorizationController',secondaryAuthorizationController);
app.controller('updateSecondaryAuthorizationController',updateSecondaryAuthorizationController);

app.directive('dobDatePicker',dobDatePicker);
app.directive('anniversaryDatePicker',anniversaryDatePicker);
app.directive('loading',loading);

app.service('urlService',['$location', urlService]);
app.service('logoutService',['$http', '$cookies', '$location', 'successStatusService', logoutService]);
app.service('successStatusService',successStatusService);


app.service('userService',[function(){
	
	return {
		
		isLogged:false
	}
}]);

app.run(function ($cookies, $rootScope, $state, $localStorage, $location, $window, $timeout, $interval, userService, urlService, logoutService, successStatusService) {
    $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
        var value = $cookies.get('signInResult');
        if (value != undefined)
            userService.isLogged = value;
        if (toState.authenticate && !userService.isLogged) {
            // User isn’t authenticated
            $state.transitionTo("login");
            event.preventDefault();
        }
		
    });
	
	var idleTime = 0;
		var stopLooping;
		
		$rootScope.mouseMoving = function(){
			
			idleTime = 0;
			console.log("Mouse is moving "+idleTime);			
		}
		
		$rootScope.keyPressed = function(){
			
			idleTime = 0;
			console.log("Key has pressed "+idleTime);
			
		}
		
		$(document).keypress(function(e){
			
			idleTime = 0;
			console.log("Key pressed anywhere on page"+idleTime);
		});
		
		stopLooping = $interval(function(){
			
			idleTime = idleTime + 1;
			
			console.log(idleTime);
            if ((idleTime == 15) && ($location.path() != "/login")) { // 15 minutes
			
                //$state.go('logout');
				
				console.log("logout");
				
				console.log($location.path());
				
				var modalClassName = document.getElementsByClassName("modal");
				
				//var myModalElement = angular.element('#myModal');
				
				// $('[id*="Partial_"]');
				
				var listUsersActivateModalElement = angular.element('#activateModal');
				var listUsersDeactivateModalElement = angular.element('#deactivateModal');
				var listUsersResetPasswordModalElement = angular.element('#resetPassswordModal');
				
				//var myModalElement = angular.element('modalClassName');
				
				listUsersActivateModalElement.modal('hide');
				listUsersDeactivateModalElement.modal('hide');
				listUsersResetPasswordModalElement.modal('hide');
				
				
				$timeout(function(){
					
					var urlValue = urlService.logoutUrl();
		
					var res = $cookies.getObject('signInResult');
		
					console.log($cookies.getObject('signInResult'));
		
					console.log(res);
					
					successStatusService.setSessionExpireStatus(true);
		
					logoutService.userLogout(res, urlValue);
				
				},750);  // 50*15 = 750
				
            }
			
		},59950);  //59950 - considered as 1 minute, remaining 50 is covered in closing the modal 
});

 

