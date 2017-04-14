
        /* vm.tbr_analises = Tbr_analise.query();*/

(function() {
    'use strict';

    angular
        .module('appgetewayApp')
        .controller('Tbr_amostraDialogController', Tbr_amostraDialogController);

    Tbr_amostraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tbr_amostra', 'Tbc_especie', 'Tbc_sub_grupo', 'Tbc_status', 'Tbc_cliente', 'Tbc_proprietario', 'Tbc_cooperativa', 'Tbc_fazenda', 'Tbc_forma_armazenamento', 'Tbc_formulario', 'Tbc_convenio', 'Tbc_genero', 'Tbc_qualidade_amostra', 'Tbc_formulario_componentes','Tbc_plano_teste','Tbr_analise','Tbc_analisesSearch'];

    function Tbr_amostraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tbr_amostra, Tbc_especie, Tbc_sub_grupo, Tbc_status, Tbc_cliente, Tbc_proprietario, Tbc_cooperativa, Tbc_fazenda, Tbc_forma_armazenamento, Tbc_formulario, Tbc_convenio, Tbc_genero, Tbc_qualidade_amostra,Tbc_formulario_componentes,Tbc_plano_teste, Tbr_analise,Tbc_analisesSearch) {
        var vm = this;

        vm.tbr_amostra = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tbc_especies = Tbc_especie.query();
        vm.tbc_sub_grupos = Tbc_sub_grupo.query();
        vm.tbc_statuses = Tbc_status.query();
        vm.tbc_clientes = Tbc_cliente.query();
        vm.tbc_proprietarios = Tbc_proprietario.query();
        vm.tbc_cooperativas = Tbc_cooperativa.query();
        vm.tbc_fazendas = Tbc_fazenda.query();
        vm.tbc_forma_armazenamentos = Tbc_forma_armazenamento.query();
        vm.tbc_formularios = Tbc_formulario.query();
        vm.tbc_convenios = Tbc_convenio.query();
        vm.tbc_generos = Tbc_genero.query();
        vm.tbc_qualidade_amostras = Tbc_qualidade_amostra.query();


        vm.tbr_analises = Tbr_analise.query({
                                            page: 0,
                                            size: 80,
                                            id_Amostra : vm.tbr_amostra.id
                                            });
         $scope.removeRow = function(name){
         		var index = -1;
         		var comArr = eval( vm.tbr_analises);
         		for( var i = 0; i < comArr.length; i++ ) {
         			if( comArr[i].id === name ) {
         				index = i;
         				break;
         			}
         		}
         		if( index === -1 ) {
         			alert( "Something gone wrong" );
         		}
         		vm.tbr_analises.splice( index, 1 );
         	};
        this.openComponentModal = function () {
                    var modalInstance = $uibModal.open({
                      component: 'modalComponent',
                      resolve: {
                        items: function () {
                          return vm.tbc_qualidade_amostras ;
                        }
                      }
                    });
                    }
        vm.tbr_amostra.recebimento_rec_data = new Date();
        /* \/ Opções de cadastro de amostra*/

        vm.tbc_plano_testes = Tbc_plano_teste.query();
        vm.tbc_plano_teste = [];
        vm.tbc_analises= Tbc_analisesSearch.query({
                                              page: 0,
                                              size: 2000,
                                                 query: "hemograma"
                                              }
                                             );
        vm.tbc_analise = [];
        vm.refresh = function (valor){
        if (valor != ""){
         vm.tbc_analises= Tbc_analisesSearch.query({
                                                    page: 0,
                                                    size: 20,
                                                    query: valor
                                                    }
                                                    );
                                                    }
        }


        /* /\ Opções de cadastro de amostra*/
        /*
           Verificar se a operação e para adicionar um novo registro.
           O parâmetro "$stateParams.tbc_formulario_id" quando é undefined  refere-se a um registro existente.
        */




        vm.tbc_formulario_id = empty($stateParams.tbc_formulario_id)
           function empty(e) {
                 var formulario = '';
                if (e === undefined)
                    return vm.tbr_amostra.tbc_formulario.id;
                 else{
                     formulario = Tbc_formulario.get({id : e});
                      vm.tbr_amostra.tbc_formulario = formulario;
                      return e;
                 }

           }
           vm.requisicao = function (){
                var texto = ' OS: '+ entity.req_texto;
                texto.concat(entity.req_texto);
                if (vm.tbr_amostra.req_texto === null)
                    texto = 'OS: ' + vm.tbr_amostra.tbc_formulario.nome;
             return texto;
            }




        vm.tbc_formulario_componentes = Tbc_formulario_componentes.query2({
                                                                           page: 0,
                                                                           size: 40,
                                                                           sort: "linha,asc",
                                                                           idFormulario:  vm.tbc_formulario_id
                                                                           });



        function transformChip(chip) {
              // If it is an object, it's already a known chip
              if (angular.isObject(chip)) {
                return chip;
              }
            }
            vm.searchText;
           vm.filter =   function querySearch (query) {
                  var results = query ? vm.tbc_plano_testes.filter(createFilterFor(query)) : [];
                  return results;
                }

           function createFilterFor(query) {
                  var lowercaseQuery = angular.lowercase(query);

                  return function filterFn(vegetable) {
                    return (vm.tbc_plano_testes.nome.indexOf(lowercaseQuery) === 0) ||
                        (vegetable._lowertype.indexOf(lowercaseQuery) === 0);
                  };

                }
               vm.transform =  function transformChip(chip) {
                      // If it is an object, it's already a known chip
                      if (angular.isObject(chip)) {
                        return chip;
                      }

                      // Otherwise, create a new one
                      return { name: chip, type: 'new' }
                    }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var i = 0;
            var agrupaIdAnalise = "";
            var agrupaIdPlanoTeste = "";
            for(var i=0; i<vm.tbc_analise.length; i++) {
                agrupaIdAnalise = agrupaIdAnalise.concat(vm.tbc_analise[i].id, "@"); // i é o índice, matriz[i] é o valor
            }
            for(var i=0; i<vm.tbc_plano_teste.length; i++) {
                agrupaIdPlanoTeste = agrupaIdPlanoTeste.concat(vm.tbc_plano_teste[i].id, "@"); // i é o índice, matriz[i] é o valor
            }

           vm.tbr_amostra.plano_teste_a = agrupaIdAnalise;
           vm.tbr_amostra.plano_teste_b = agrupaIdPlanoTeste;

            if (vm.tbr_amostra.id !== null) {
                Tbr_amostra.update(vm.tbr_amostra, onSaveSuccess, onSaveError);
            } else {
                Tbr_amostra.save(vm.tbr_amostra,onSaveSuccess, onSaveError);
            }


        }

        function onSaveSuccess (result) {
            $scope.$emit('appgetewayApp:tbr_amostraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.recebimento_rec_data = false;
        vm.datePickerOpenStatus.coleta_data = false;
        vm.datePickerOpenStatus.autorizado_data = false;
        vm.datePickerOpenStatus.cancelado_data = false;
        vm.datePickerOpenStatus.suspenso_data = false;
        vm.datePickerOpenStatus.rejeitado_data = false;
        vm.datePickerOpenStatus.disponivel_data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        function deletarAnalise (id){
            Tbr_amostra.delete({id: id})
        }
    }
})();

