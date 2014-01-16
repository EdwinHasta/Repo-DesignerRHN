/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import InterfaceAdministrar.AdministrarNReporteContabilidadInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarNReporteContabilidad implements AdministrarNReporteContabilidadInterface {

    @EJB
    PersistenciaInforeportesInterface persistenciaInforeportes;
    @EJB
    PersistenciaParametrosInformesInterface persistenciaParametrosInformes;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    List<Inforeportes> listInforeportes;
    ParametrosInformes parametroReporte;
    String usuarioActual;
    List<Procesos> listProcesos;
    List<Empleados> listEmpleados;

    @Override
    public ParametrosInformes parametrosDeReporte() {
        try {
            usuarioActual = persistenciaActualUsuario.actualAliasBD();
            parametroReporte = persistenciaParametrosInformes.buscarParametroInformeUsuario(usuarioActual);
            return parametroReporte;
        } catch (Exception e) {
            System.out.println("Error parametrosDeReporte Administrar" + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> listInforeportesUsuario() {
        try {
            listInforeportes = persistenciaInforeportes.buscarInforeportesUsuarioContabilidad();
            return listInforeportes;
        } catch (Exception e) {
            System.out.println("Error listInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public void modificarParametrosInformes(ParametrosInformes parametroInforme) {
        try {
            persistenciaParametrosInformes.editar(parametroInforme);
        } catch (Exception e) {
            System.out.println("Error modificarParametrosInformes : " + e.toString());
        }
    }

    @Override
    public List<Procesos> listProcesos() {
        try {
            listProcesos = persistenciaProcesos.buscarProcesos();
            return listProcesos;
        } catch (Exception e) {
            System.out.println("Error en listProcesos Administrar: " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> listEmpleados() {
        try {
            listEmpleados = persistenciaEmpleado.buscarEmpleados();
            return listEmpleados;
        } catch (Exception e) {
            System.out.println("Error listEmpleados : " + e.toString());
            return null;
        }
    }
    
     @Override 
    public void guardarCambiosInfoReportes(List<Inforeportes> listaIR) {
        try {
            for (int i = 0; i < listaIR.size(); i++) {
                persistenciaInforeportes.editar(listaIR.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambiosInfoReportes Admi : " + e.toString());
        }
    }
}
