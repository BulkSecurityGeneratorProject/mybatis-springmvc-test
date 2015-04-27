'use strict';

angular.module('ircsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


