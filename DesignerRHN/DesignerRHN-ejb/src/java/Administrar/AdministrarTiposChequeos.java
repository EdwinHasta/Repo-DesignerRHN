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

    @Override
    public void modificarTiposChequeos(List<TiposChequeos> listaTiposChequeos) {
        for (int i = 0; i < listaTiposChequeos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposChequeos.editar(listaTiposChequeos.get(i));
        }
    }
    @Override
    public void borrarTiposChequeos(List<TiposChequeos> listaTiposChequeos) {
        for (int i = 0; i < listaTiposChequeos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposChequeos.borrar(listaTiposChequeos.get(i));
        }
    }
    @Override
    public void crearTiposChequeos(List<TiposChequeos> listaTiposChequeos) {
        for (int i = 0; i < listaTiposChequeos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposChequeos.crear(listaTiposChequeos.get(i));
        }
    }
    @Override
    public List<TiposChequeos> consultarTiposChequeos() {
        List<TiposChequeos> listTiposChequeos;
        listTiposChequeos = persistenciaTiposChequeos.buscarTiposChequeos();
        return listTiposChequeos;
    }

    public TiposChequeos consultarTipoChequeo(BigInteger secTipoEmpresa) {
        TiposChequeos tiposChequeos;
        tiposChequeos = persistenciaTiposChequeos.buscarTipoChequeo(secTipoEmpresa);
        return tiposChequeos;
    }
    @Override
    public BigInteger contarChequeosMedicosTipoChequeo(BigInteger secuenciaJuzgados) {
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
    @Override
    public BigInteger contarTiposExamenesCargosTipoChequeo(BigInteger secuenciaJuzgados) {
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
