describe('Welcome Page', function() {

    beforeEach(angular.mock.module('formApp'));

    var $controller, $scope;

    beforeEach(inject(function(_$controller_, _$rootScope_){
        $scope = _$rootScope_.$new();
        $controller = _$controller_('welcomeController', { $scope: $scope});
    }));

    it('Welcome message should be: Welcome to the demo', function () {
        expect($scope.welcomeMsg).toBe('Welcome to the demo');
    });
});
