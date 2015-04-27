/* globals $ */
'use strict';

angular.module('ircsApp')
    .directive('ircsAppPager', function() {
        return {
            templateUrl: 'scripts/components/form/pager.html'
        };
    });
