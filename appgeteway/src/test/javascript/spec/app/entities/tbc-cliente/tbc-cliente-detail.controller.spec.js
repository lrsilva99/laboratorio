'use strict';

describe('Controller Tests', function() {

    describe('Tbc_cliente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_cliente, MockTbc_instituicao, MockTbc_grupo_cliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_cliente = jasmine.createSpy('MockTbc_cliente');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_grupo_cliente = jasmine.createSpy('MockTbc_grupo_cliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_cliente': MockTbc_cliente,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_grupo_cliente': MockTbc_grupo_cliente
            };
            createController = function() {
                $injector.get('$controller')("Tbc_clienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_clienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
