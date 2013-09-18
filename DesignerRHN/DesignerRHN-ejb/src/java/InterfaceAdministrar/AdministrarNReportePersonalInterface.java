/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

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
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNReportePersonalInterface {
    
    public ParametrosInformes parametrosDeReporte();
    public List<Inforeportes> listInforeportesUsuario();
    public void modificarParametrosInformes(ParametrosInformes parametroInforme);
    public List<Empresas> listEmpresas();
    public List<Idiomas> listIdiomas();
    public List<Aficiones> listAficiones();
    public List<Deportes> listDeportes();
    public List<Ciudades> listCiudades();
    public List<TiposTrabajadores> listTiposTrabajadores();
    public List<Estructuras> listEstructuras();
    public List<Empleados> listEmpleados();
    public List<TiposTelefonos> listTiposTelefonos();
    public List<EstadosCiviles> listEstadosCiviles();
    
}
