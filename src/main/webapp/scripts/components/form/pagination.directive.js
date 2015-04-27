/* globals $ */
'use strict';

angular.module('ircsApp')
    .directive('ircsAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
