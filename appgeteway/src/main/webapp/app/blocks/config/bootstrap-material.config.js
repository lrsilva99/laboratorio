(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .config(bootstrapMaterialDesignConfig);

        bootstrapMaterialDesignConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();

