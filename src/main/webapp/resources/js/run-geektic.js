///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular',
        angularMessages: '../bower_components/angular-messages/angular-messages',
        angularRoutes: '../bower_components/angular-route/angular-route',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../bower_components/lodash/dist/lodash",
        frontendServices: 'frontend-services',
        geekticApp: "geektic-app"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        angularRoutes: {
            deps: ['angular']
        },
        frontendServices: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        geekticApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'frontendServices']
        }
    }
});

require(['geekticApp'], function () {

    angular.bootstrap(document.getElementById('geekticApp'), ['geekticApp']);

});