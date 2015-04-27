'use strict';

angular.module('ircsApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
