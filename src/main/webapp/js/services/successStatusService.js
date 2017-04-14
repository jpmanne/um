function successStatusService(){
	
	var status;
	
	var statusCount;	
	var userEditCount;
	var userLogoutCount;
	var sessionExpireCount;
	var defaultPasswordCount;
	
	this.setStatus = function(value){
		
		console.log("Entered");
		status = value;
		statusCount=0;
		console.log(statusCount);
	}
	
	this.setUserEditStatus = function(value){
		
		console.log("Entered");
		status = value;
		userEditCount=0;
		console.log(userEditCount);
	}
	
	this.setUserLogoutStatus = function(value){
		
		console.log("Entered");
		status = value;
		userLogoutCount=0;
		console.log(userLogoutCount);
	}
	
	this.setSessionExpireStatus = function(value){
		
		console.log("Entered");
		status = value;
		sessionExpireCount=0;
		console.log(sessionExpireCount);
	}
	
	this.setDefaultPasswordStatus = function(value){
		
		console.log("Entered");
		status = value;
		defaultPasswordCount=0;
		console.log(defaultPasswordCount);
	}
	
	this.getStatus = function(){
		
		if(statusCount == 0){
			
			statusCount++;
			console.log(statusCount);
			return status;
		}		
	}

	this.getUserEditStatus = function(){
		
		if(userEditCount == 0){
			
			userEditCount++;
			console.log(userEditCount);
			return status;
		}		
	}
	
	this.getUserLogoutStatus = function(){
		
		if(userLogoutCount == 0){
			
			userLogoutCount++;
			console.log(userLogoutCount);
			return status;
		}
	}
	
	this.getSessionExpireStatus = function(){
		
		if((sessionExpireCount == 0) || (sessionExpireCount == 1)){
			
			sessionExpireCount++;
			console.log(sessionExpireCount);
			return status;
		}
	}
	
	this.getDefaultPasswordStatus = function(){
		
		if(defaultPasswordCount == 0){
			
			defaultPasswordCount++;
			console.log(defaultPasswordCount);
			return status;
		}
	}
}