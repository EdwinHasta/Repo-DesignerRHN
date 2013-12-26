/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.TiposChequeos;
import InterfaceAdministrar.AdministrarTiposChequeosInterface;
import InterfacePersistencia.PersistenciaTiposChequeosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposChequeos implements AdministrarTiposChequeosInterface {

  @EJB
    PersistenciaTiposChequeosInterface persistenciaTiposChequeos;
    private TiposChequeos tiposChequeosSeleccionada;
    private TiposChequeos tiposChequeos;
    private List<TiposChequeos> listTiposChequeos;

    public void modificarTiposChequeos(List<TiposChequeos> listTiposChequeosModificadas) {
        for (int i = 0; i < listTiposChequeosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposChequeosSeleccionada = listTiposChequeosModificadas.get(i);
            persistenciaTiposChequeos.editar(tiposChequeosSeleccionada);
        }
    }

    public void borrarTiposChequeos(TiposChequeos tiposChequeos) {
        persistenciaTiposChequeos.borrar(tiposChequeos);
    }

    public void crearTiposChequeos(TiposChequeos tiposChequeos) {
        persistenciaTiposChequeos.crear(tiposChequeos);
    }

    public List<TiposChequeos> mostrarTiposChequeos() {
        listTiposChequeos = persistenciaTiposChequeos.buscarTiposChequeos();
        return listTiposChequeos;
    }

    public TiposChequeos mostrarTipoChequeo(BigInteger secTipoEmpresa) {
        tiposChequeos = persistenciaTiposChequeos.buscarTipoChequeo(secTipoEmpresa);
        return tiposChequeos;
    }
    
         public BigInteger verificarChequeosMedicos(BigInteger secuenciaJuzgados) {
        BigInteger verificarChequeosMedicos = null;
        try {
            System.out.println("Administrar SecuenciaBorrar " + secuenciaJuzgados);
            verificarChequeosMedicos = persistenciaTiposChequeos.contadorChequeosMedicos(secuenciaJuzgados);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSCHEQUEOS VERIFICARCHEQUEOSMEDICOS ERROR :" + e);
        } finally {
            return verificarChequeosMedicos;
        }
    }
         public BigInteger verificarTiposExamenesCargos(BigInteger secuenciaJuzgados) {
        BigInteger verificarTiposExamenesCargos = null;
        try {
            System.out.println("Administrar SecuenciaBorrar " + secuenciaJuzgados);
            verificarTiposExamenesCargos = persistenciaTiposChequeos.contadorTiposExamenesCargos(secuenciaJuzgados);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSCHEQUEOS VERIFICARTIPOSEXAMENESCARGOS ERROR :" + e);
        } finally {
            return verificarTiposExamenesCargos;
        }
    }
}
