'use strict';

describe('Controller Tests', function() {

    describe('Tbr_analise_resultado Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbr_analise_resultado, MockTbc_status, MockTbc_analises_componente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbr_analise_resultado = jasmine.createSpy('MockTbr_analise_resultado');
            MockTbc_status = jasmine.createSpy('MockTbc_status');
            MockTbc_analises_componente = jasmine.createSpy('MockTbc_analises_componente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbr_analise_resultado': MockTbr_analise_resultado,
                'Tbc_status': MockTbc_status,
                'Tbc_analises_componente': MockTbc_analises_componente
            };
            createController = function() {
                $injector.get('$controller')("Tbr_analise_resultadoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbr_analise_resultadoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
