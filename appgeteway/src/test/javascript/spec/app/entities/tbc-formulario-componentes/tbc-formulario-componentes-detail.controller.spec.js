'use strict';

describe('Controller Tests', function() {

    describe('Tbc_formulario_componentes Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_formulario_componentes, MockTbc_formulario, MockTbc_tipo_campo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_formulario_componentes = jasmine.createSpy('MockTbc_formulario_componentes');
            MockTbc_formulario = jasmine.createSpy('MockTbc_formulario');
            MockTbc_tipo_campo = jasmine.createSpy('MockTbc_tipo_campo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_formulario_componentes': MockTbc_formulario_componentes,
                'Tbc_formulario': MockTbc_formulario,
                'Tbc_tipo_campo': MockTbc_tipo_campo
            };
            createController = function() {
                $injector.get('$controller')("Tbc_formulario_componentesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_formulario_componentesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
