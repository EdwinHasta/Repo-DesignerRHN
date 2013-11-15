/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import Entidades.Diagnosticoscategorias;
import Entidades.Empleados;
import Entidades.EnfermeadadesProfesionales;
import Entidades.Enfermedades;
import Entidades.Ibcs;
import Entidades.Soaccidentes;
import Entidades.Soausentismos;
import Entidades.SoausentismosProrrogas;
import Entidades.Terceros;
import Entidades.Tiposausentismos;
import InterfaceAdministrar.AdministrarSoausentismosInterface;
import InterfacePersistencia.PersistenciaCausasAusentismosInterface;
import InterfacePersistencia.PersistenciaClasesAusentismosInterface;
import InterfacePersistencia.PersistenciaDiagnosticosCategoriasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEnfermedadesInterface;
import InterfacePersistencia.PersistenciaEnfermedadesProfesionalesInterface;
import InterfacePersistencia.PersistenciaIBCSInterface;
import InterfacePersistencia.PersistenciaRelacionesIncapacidadesInterface;
import InterfacePersistencia.PersistenciaSoaccidentesInterface;
import InterfacePersistencia.PersistenciaSoausentismosInterface;
import InterfacePersistencia.PersistenciaSoausentismosProrrogasInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTiposAusentismosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarSoausentismos implements AdministrarSoausentismosInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaSoausentismosInterface persistenciaSoausentismos;
    @EJB
    PersistenciaSoaccidentesInterface persistenciaSoaccdicentes;
    @EJB
    PersistenciaTiposAusentismosInterface persistenciaTiposAusentismos;
    @EJB
    PersistenciaClasesAusentismosInterface persistenciaClasesAusentismos;
    @EJB
    PersistenciaCausasAusentismosInterface persistenciaCausasAusentismos;
    @EJB
    PersistenciaIBCSInterface persistenciaIBCS;
    @EJB
    PersistenciaEnfermedadesProfesionalesInterface persistenciaEP;
    @EJB
    PersistenciaDiagnosticosCategoriasInterface persistenciaDiagnosticos;
    @EJB
    PersistenciaEnfermedadesInterface persistenciaEnfermedades;
    @EJB
    PersistenciaSoausentismosProrrogasInterface persistenciaSoausentismosProrrogas;
    @EJB
    PersistenciaRelacionesIncapacidadesInterface persistenciaRelacionesIncapacidades;
    
    //Trae los ausentismos del empleado cuya secuencia se envía como parametro//
    @Override
    public List<Soausentismos> ausentismosEmpleado(BigInteger secuenciaEmpleado) {
        try {
            return persistenciaSoausentismos.ausentismosEmpleado(secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.novedadesEmpleado" + e);
            return null;
        }
    }

    //Lista de Valores Empleados
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.empleadosNovedad();
    }

    public List<Tiposausentismos> lovTiposAusentismos() {
        return persistenciaTiposAusentismos.buscarTiposAusentismos();
    }

    public List<Clasesausentismos> lovClasesAusentismos() {
        return persistenciaClasesAusentismos.buscarClasesAusentismos();
    }
    
    public List<Causasausentismos> lovCausasAusentismos() {
        return persistenciaCausasAusentismos.buscarCausasAusentismos();
    }
    
    public List<Soaccidentes> lovAccidentes(BigInteger secuenciaEmpleado){
        return persistenciaSoaccdicentes.accidentesEmpleado(secuenciaEmpleado);
    }
    
    public List<Terceros> lovTerceros(){
        return persistenciaTerceros.buscarTerceros();
    }
    
    public List<Diagnosticoscategorias> lovDiagnosticos(){
        return persistenciaDiagnosticos.buscarDiagnosticos();
    }
    
    public List<Ibcs> empleadosIBCS(BigInteger secuenciaEmpleado){
        return persistenciaIBCS.buscarIbcsPorEmpleado(secuenciaEmpleado);
    }
    
    public List<Enfermedades> enfermedades(){
        return persistenciaEnfermedades.buscarEnfermedades();
    }
    
    public List<EnfermeadadesProfesionales> empleadosEP(BigInteger secuenciaEmpleado){
        return persistenciaEP.buscarEPPorEmpleado(secuenciaEmpleado);
    }
    
    //MostrarProrroga
    public String mostrarProrroga(BigInteger secuenciaProrroga){
        return persistenciaSoausentismos.prorrogaMostrar(secuenciaProrroga);
    }
    
    //MostrarRelacion
    public String mostrarRelacion(BigInteger secuenciaAusentismo){
        return persistenciaRelacionesIncapacidades.relaciones(secuenciaAusentismo);
    }
    
    //LOV Prorrogas
    public List<Soausentismos> lovProrrogas(BigInteger secEmpleado, BigInteger secuenciaCausa, BigInteger secuenciaAusentismo){
        /*if(secuenciaAusentismo == null){
           secuenciaAusentismo = BigInteger.valueOf(0);
        }*/
        return persistenciaSoausentismos.prorrogas(secEmpleado, secuenciaCausa, secuenciaAusentismo);
    }
}
