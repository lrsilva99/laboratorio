'use strict';

describe('Controller Tests', function() {

    describe('Tbc_formulario Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTbc_formulario, MockTbc_instituicao, MockTbc_sub_grupo, MockTbc_grupo_analise, MockTbc_tipo_cadastro;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTbc_formulario = jasmine.createSpy('MockTbc_formulario');
            MockTbc_instituicao = jasmine.createSpy('MockTbc_instituicao');
            MockTbc_sub_grupo = jasmine.createSpy('MockTbc_sub_grupo');
            MockTbc_grupo_analise = jasmine.createSpy('MockTbc_grupo_analise');
            MockTbc_tipo_cadastro = jasmine.createSpy('MockTbc_tipo_cadastro');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tbc_formulario': MockTbc_formulario,
                'Tbc_instituicao': MockTbc_instituicao,
                'Tbc_sub_grupo': MockTbc_sub_grupo,
                'Tbc_grupo_analise': MockTbc_grupo_analise,
                'Tbc_tipo_cadastro': MockTbc_tipo_cadastro
            };
            createController = function() {
                $injector.get('$controller')("Tbc_formularioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appgetewayApp:tbc_formularioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
