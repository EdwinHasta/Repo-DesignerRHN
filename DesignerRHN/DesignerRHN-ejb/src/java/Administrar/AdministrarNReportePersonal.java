
package Administrar;

import Entidades.Aficiones;
import Entidades.Ciudades;
import Entidades.Deportes;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.EstadosCiviles;
import Entidades.Estructuras;
import Entidades.Idiomas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarNReportePersonalInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaAficionesInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDeportesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaIdiomasInterface;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarNReportePersonal implements AdministrarNReportePersonalInterface{

    @EJB
    PersistenciaInforeportesInterface persistenciaInforeportes;
    @EJB
    PersistenciaParametrosInformesInterface persistenciaParametrosInformes;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    ///
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaEstadosCivilesInterface persistenciaEstadosCiviles;
    @EJB
    PersistenciaTiposTelefonosInterface persistenciaTiposTelefonos;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    @EJB
    PersistenciaDeportesInterface persistenciaDeportes;
    @EJB
    PersistenciaAficionesInterface persistenciaAficiones;
    @EJB
    PersistenciaIdiomasInterface persistenciaIdiomas;
    
    //
    List<Inforeportes> listInforeportes;
    ParametrosInformes parametroReporte;
    String usuarioActual;
    //
    List<EstadosCiviles> listEstadosCiviles;
    List<TiposTelefonos> listTiposTelefonos;
    List<Empleados> listEmpleados;
    List<Estructuras> listEstructuras;
    List<TiposTrabajadores> listTiposTrabajadores;
    List<Ciudades> listCiudades;
    List<Deportes> listDeportes;
    List<Aficiones> listAficiones;
    List<Idiomas> listIdiomas;
    List<Empresas> listEmpresas;
    
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
            listInforeportes = persistenciaInforeportes.buscarInforeportesUsuarioPersonal();
            return listInforeportes;
        } catch (Exception e) {
            System.out.println("Error listInforeportesUsuario " + e);
            return null;
        }
    }
    
    @Override
    public void modificarParametrosInformes(ParametrosInformes parametroInforme){
        try{
            persistenciaParametrosInformes.editar(parametroInforme);
        }catch(Exception e){
            System.out.println("Error modificarParametrosInformes : "+e.toString());
        }
    }
         
    @Override
    public List<Empresas> listEmpresas() {
        try {
            listEmpresas = persistenciaEmpresas.buscarEmpresas();
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Idiomas> listIdiomas() {
        try {
            listIdiomas = persistenciaIdiomas.buscarIdiomas();
            return listIdiomas;
        } catch (Exception e) {
            System.out.println("Error listIdiomas Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Aficiones> listAficiones() {
        try {
            listAficiones = persistenciaAficiones.buscarAficiones();
            return listAficiones;
        } catch (Exception e) {
            System.out.println("Error listAficiones Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Deportes> listDeportes() {
        try {
            listDeportes = persistenciaDeportes.buscarDeportes();
            return listDeportes;
        } catch (Exception e) {
            System.out.println("Error listDeportes Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Ciudades> listCiudades() {
        try {
            listCiudades = persistenciaCiudades.ciudades();
            return listCiudades;
        } catch (Exception e) {
            System.out.println("Error listCiudades Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<TiposTrabajadores> listTiposTrabajadores() {
        try {
            listTiposTrabajadores = persistenciaTiposTrabajadores.buscarTiposTrabajadores();
            return listTiposTrabajadores;
        } catch (Exception e) {
            System.out.println("Error listTiposTrabajadores Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Estructuras> listEstructuras() {
        try {
            listEstructuras = persistenciaEstructuras.buscarEstructuras();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Error listEstructuras Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Empleados> listEmpleados() {
        try {
            listEmpleados = persistenciaEmpleado.buscarEmpleados();
            return listEmpleados;
        } catch (Exception e) {
            System.out.println("Error listEmpleados Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<TiposTelefonos> listTiposTelefonos() {
        try {
            listTiposTelefonos = persistenciaTiposTelefonos.tiposTelefonos();
            return listTiposTelefonos;
        } catch (Exception e) {
            System.out.println("Error listTiposTelefonos Administrar : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<EstadosCiviles> listEstadosCiviles() {
        try {
            listEstadosCiviles = persistenciaEstadosCiviles.buscarEstadosCiviles();
            return listEstadosCiviles;
        } catch (Exception e) {
            System.out.println("Error listEstadosCiviles Administrar : " + e.toString());
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
