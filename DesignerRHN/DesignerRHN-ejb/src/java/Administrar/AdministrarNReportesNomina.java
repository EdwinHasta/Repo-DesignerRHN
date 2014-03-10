/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Asociaciones;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Estructuras;
import Entidades.GruposConceptos;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.Terceros;
import Entidades.TiposAsociaciones;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import InterfaceAdministrar.AdministrarNReportesNominaInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaAsociacionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaGruposConceptosInterface;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTiposAsociacionesInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarNReportesNomina implements AdministrarNReportesNominaInterface {
    
    @EJB
    PersistenciaInforeportesInterface persistenciaInforeportes;
    @EJB
    PersistenciaParametrosInformesInterface persistenciaParametrosInformes;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaGruposConceptosInterface persistenciaGruposConceptos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaUbicacionesGeograficasInterface persistenciaUbicacionesGeograficas;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaTiposAsociacionesInterface persistenciaTiposAsociaciones;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaAsociacionesInterface persistenciaAsociaciones;
    
    List<Inforeportes> listInforeportes;
    ParametrosInformes parametroReporte;
    String usuarioActual;
    /////////Lista de valores
    List<Empresas> listEmpresas;
    List<GruposConceptos> listGruposConceptos;
    List<Empleados> listEmpleados;
    List<UbicacionesGeograficas> listUbicacionesGeograficas;
    List<TiposAsociaciones> listTiposAsociaciones;
    List<Estructuras> listEstructuras;
    List<TiposTrabajadores> listTiposTrabajadores;
    List<Terceros> listTerceros;
    List<Procesos> listProcesos;
    List<Asociaciones> listAsociaciones;
    
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
            listInforeportes = persistenciaInforeportes.buscarInforeportesUsuarioNomina();
            System.out.println("Tamaño: " + listInforeportes.size());
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
    public List<Empresas> listEmpresas() {
        try {
            listEmpresas = persistenciaEmpresas.consultarEmpresas();
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<GruposConceptos> listGruposConcetos() {
        try {
            listGruposConceptos = persistenciaGruposConceptos.buscarGruposConceptos();
            return listGruposConceptos;
        } catch (Exception e) {
            System.out.println("Error listGruposConcetos : " + e.toString());
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
    public List<UbicacionesGeograficas> listUbicacionesGeograficas() {
        try {
            listUbicacionesGeograficas = persistenciaUbicacionesGeograficas.consultarUbicacionesGeograficas();
            return listUbicacionesGeograficas;
        } catch (Exception e) {
            System.out.println("Error listUbicacionesGeograficas : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<TiposAsociaciones> listTiposAsociaciones() {
        try {
            listTiposAsociaciones = persistenciaTiposAsociaciones.buscarTiposAsociaciones();
            return listTiposAsociaciones;
        } catch (Exception e) {
            System.out.println("Error listTiposAsociaciones : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Estructuras> listEstructuras() {
        try {
            listEstructuras = persistenciaEstructuras.buscarEstructuras();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Error listEstructuras : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<TiposTrabajadores> listTiposTrabajadores() {
        try {
            listTiposTrabajadores = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return listTiposTrabajadores;
        } catch (Exception e) {
            System.out.println("Error listTiposTrabajadores : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Terceros> listTerceros() {
        try {
            listTerceros = persistenciaTerceros.buscarTerceros();
            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error listTerceros : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Procesos> listProcesos() {
        try {
            listProcesos = persistenciaProcesos.buscarProcesos();
            return listProcesos;
        } catch (Exception e) {
            System.out.println("Error listProcesos : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Asociaciones> listAsociaciones() {
        try {
            listAsociaciones = persistenciaAsociaciones.buscarAsociaciones();
            return listAsociaciones;
        } catch (Exception e) {
            System.out.println("Error listAsociaciones : " + e.toString());
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
