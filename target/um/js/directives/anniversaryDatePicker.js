function anniversaryDatePicker(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element) {
        element.datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: "1950:2050",
			dateFormat: 'yy-mm-dd',
            onSelect: function (date) {
            scope.userDetails.anniversaryDate = date;
            scope.$apply();
            }
            });
        }
    };
}