/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.GruposConceptos;
import Entidades.Operandos;
import Entidades.OperandosGruposConceptos;
import Entidades.Procesos;
import InterfaceAdministrar.AdministrarOperandosGruposConceptosInterface;
import InterfacePersistencia.PersistenciaGruposConceptosInterface;
import InterfacePersistencia.PersistenciaOperandosGruposConceptosInterface;
import InterfacePersistencia.PersistenciaOperandosInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Victor Algarin
 */
@Stateful
public class AdministrarOperandosGruposConceptos implements AdministrarOperandosGruposConceptosInterface {

    @EJB
    PersistenciaOperandosGruposConceptosInterface persistenciaOperandosGruposConceptos;
    @EJB
    PersistenciaGruposConceptosInterface persistenciaGruposConceptos;
    @EJB
    PersistenciaOperandosInterface persistenciaOperandos;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;

//Procesos
    @Override
    public void borrarProcesos(Procesos procesos) {
        persistenciaProcesos.borrar(procesos);
    }

    @Override
    public void crearProcesos(Procesos procesos) {
        persistenciaProcesos.crear(procesos);
    }

    @Override
    public void modificarProcesos(List<Procesos> listaVigenciasRetencionesModificar) {
        for (int i = 0; i < listaVigenciasRetencionesModificar.size(); i++) {

            persistenciaProcesos.editar(listaVigenciasRetencionesModificar.get(i));
        }
    }

    public List<Procesos> consultarProcesos() {
        try {
            List<Procesos> actual = persistenciaProcesos.buscarProcesos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }
    
    public List<Operandos> consultarOperandos(){
        try {
            List<Operandos> actual = persistenciaOperandos.buscarOperandos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }
    
    public List<GruposConceptos> consultarGrupos(){
        try {
            List<GruposConceptos> actual = persistenciaGruposConceptos.buscarGruposConceptos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }

//OperandosGruposConceptos
    
    @Override
    public void borrarOperandosGrupos(OperandosGruposConceptos operandos) {
        persistenciaOperandosGruposConceptos.borrar(operandos);
    }

    @Override
    public void crearOperandosGrupos(OperandosGruposConceptos operandos) {
        persistenciaOperandosGruposConceptos.crear(operandos);
    }

    @Override
    public void modificarOperandosGrupos(List<OperandosGruposConceptos> listaOperandosGruposConceptosModificar) {
        for (int i = 0; i < listaOperandosGruposConceptosModificar.size(); i++) {
            System.out.println("Modificando...");

            persistenciaOperandosGruposConceptos.editar(listaOperandosGruposConceptosModificar.get(i));
        }
    }

    @Override
    public List<OperandosGruposConceptos> consultarOperandosGrupos(BigInteger secProceso) {
        try {
            List<OperandosGruposConceptos> actual = persistenciaOperandosGruposConceptos.buscarOperandosGruposConceptosPorProcesoSecuencia(secProceso);
            return actual;
        } catch (Exception e) {
            System.out.println("Error conceptoActual Admi : " + e.toString());
            return null;
        }
    }
    
    

}
