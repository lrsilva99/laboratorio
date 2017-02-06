'use strict';

describe('Controller Tests', function() {

    describe('Tbc_tipo_laboratorio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_tipo_laboratorio, MockTbc_instituicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_tipo_laboratorio = jasmine.createSpy('MockTbc_tipo_laboratorio');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_tipo_laboratorio': MockTbc_tipo_laboratorio,
                'Tbc_instituicao': MockTbc_instituicao
            };
            createController = function() {
                $injector.get('$controller')("Tbc_tipo_laboratorioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_tipo_laboratorioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
