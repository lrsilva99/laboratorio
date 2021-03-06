'use strict';

describe('Controller Tests', function() {

    describe('Tbc_analise_resultado Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_analise_resultado, MockTbc_status;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_analise_resultado = jasmine.createSpy('MockTbc_analise_resultado');
            MockTbc_status = jasmine.createSpy('MockTbc_status');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_analise_resultado': MockTbc_analise_resultado,
                'Tbc_status': MockTbc_status
            };
            createController = function() {
                $injector.get('$controller')("Tbc_analise_resultadoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_analise_resultadoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
