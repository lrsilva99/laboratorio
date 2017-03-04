'use strict';

describe('Controller Tests', function() {

    describe('Tbc_matriz Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_matriz, MockTbc_instituicao, MockTbc_tipo_cadastro;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_matriz = jasmine.createSpy('MockTbc_matriz');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_tipo_cadastro = jasmine.createSpy('MockTbc_tipo_cadastro');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_matriz': MockTbc_matriz,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_tipo_cadastro': MockTbc_tipo_cadastro
            };
            createController = function() {
                $injector.get('$controller')("Tbc_matrizDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_matrizUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
