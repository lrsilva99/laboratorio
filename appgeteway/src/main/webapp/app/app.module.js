(function() {
    'use strict';

    angular
        .module('appgetewayApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'ui.select',
            'ngSanitize',
            'angular-loading-bar',
            'ngMaterial',
            'textAngular'
            // jhipster-needle-angularjs-add-module JHipster will add new module here

        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
