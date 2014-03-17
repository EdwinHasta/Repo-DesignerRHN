/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Bancos;
import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarReportesBancosInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaBancosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarReportesBancos implements AdministrarReportesBancosInterface {

    @EJB
    PersistenciaInforeportesInterface persistenciaInforeportes;
    @EJB
    PersistenciaParametrosInformesInterface persistenciaParametrosInformes;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaBancosInterface persistenciaBancos;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    /////
    List<Inforeportes> listInforeportes;
    ParametrosInformes parametroReporte;
    String usuarioActual;
    /////////Lista de valores
    List<Empresas> listEmpresas;
    List<Empleados> listEmpleados;
    List<TiposTrabajadores> listTiposTrabajadores;
    List<Procesos> listProcesos;
    List<Bancos> listBancos;
    List<Ciudades> listCiudades;

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
            listInforeportes = persistenciaInforeportes.buscarInforeportesUsuarioBanco();
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
    public List<Bancos> listBancos() {
        try {
            listBancos = persistenciaBancos.buscarBancos();
            return listBancos;
        } catch (Exception e) {
            System.out.println("Error listBancos : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Ciudades> listCiudades() {
        try {
            listCiudades = persistenciaCiudades.ciudades();
            return listCiudades;
        } catch (Exception e) {
            System.out.println("Error listCiudades : " + e.toString());
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
