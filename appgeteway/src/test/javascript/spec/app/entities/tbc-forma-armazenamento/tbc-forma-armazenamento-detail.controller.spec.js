'use strict';

describe('Controller Tests', function() {

    describe('Tbc_forma_armazenamento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_forma_armazenamento, MockTbc_instituicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_forma_armazenamento = jasmine.createSpy('MockTbc_forma_armazenamento');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_forma_armazenamento': MockTbc_forma_armazenamento,
                'Tbc_instituicao': MockTbc_instituicao
            };
            createController = function() {
                $injector.get('$controller')("Tbc_forma_armazenamentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_forma_armazenamentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
