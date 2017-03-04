'use strict';

describe('Controller Tests', function() {

    describe('Tbc_formulario_compnentes Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_formulario_compnentes, MockTbc_formulario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_formulario_compnentes = jasmine.createSpy('MockTbc_formulario_compnentes');
            MockTbc_formulario = jasmine.createSpy('MockTbc_formulario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_formulario_compnentes': MockTbc_formulario_compnentes,
                'Tbc_formulario': MockTbc_formulario
            };
            createController = function() {
                $injector.get('$controller')("Tbc_formulario_compnentesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_formulario_compnentesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
