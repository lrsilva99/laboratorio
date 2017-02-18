'use strict';

describe('Controller Tests', function() {

    describe('Tbc_analises_componente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_analises_componente, MockTbc_instituicao, MockTbc_tipo_campo, MockTbc_analises;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_analises_componente = jasmine.createSpy('MockTbc_analises_componente');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_tipo_campo = jasmine.createSpy('MockTbc_tipo_campo');
            MockTbc_analises = jasmine.createSpy('MockTbc_analises');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_analises_componente': MockTbc_analises_componente,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_tipo_campo': MockTbc_tipo_campo,
                'Tbc_analises': MockTbc_analises
            };
            createController = function() {
                $injector.get('$controller')("Tbc_analises_componenteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_analises_componenteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
