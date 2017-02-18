'use strict';

describe('Controller Tests', function() {

    describe('Tbc_plano_teste Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_plano_teste, MockTbc_tipo_cadastro, MockTbc_sub_grupo, MockTbc_instituicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_plano_teste = jasmine.createSpy('MockTbc_plano_teste');
            MockTbc_tipo_cadastro = jasmine.createSpy('MockTbc_tipo_cadastro');
            MockTbc_sub_grupo = jasmine.createSpy('MockTbc_sub_grupo');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_plano_teste': MockTbc_plano_teste,
                'Tbc_tipo_cadastro': MockTbc_tipo_cadastro,
                'Tbc_sub_grupo': MockTbc_sub_grupo,
                'Tbc_instituicao': MockTbc_instituicao
            };
            createController = function() {
                $injector.get('$controller')("Tbc_plano_testeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_plano_testeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
