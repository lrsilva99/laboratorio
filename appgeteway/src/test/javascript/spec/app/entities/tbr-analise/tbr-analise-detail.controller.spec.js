'use strict';

describe('Controller Tests', function() {

    describe('Tbr_analise Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbr_analise, MockTbr_amostra, MockTbc_status, MockTbc_analises;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbr_analise = jasmine.createSpy('MockTbr_analise');
            MockTbr_amostra = jasmine.createSpy('MockTbr_amostra');
            MockTbc_status = jasmine.createSpy('MockTbc_status');
            MockTbc_analises = jasmine.createSpy('MockTbc_analises');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbr_analise': MockTbr_analise,
                'Tbr_amostra': MockTbr_amostra,
                'Tbc_status': MockTbc_status,
                'Tbc_analises': MockTbc_analises
            };
            createController = function() {
                $injector.get('$controller')("Tbr_analiseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbr_analiseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
