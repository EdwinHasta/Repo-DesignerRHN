/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

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
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNReportesNominaInterface {
    
    /**
     * 
     * @return 
     */
    public ParametrosInformes parametrosDeReporte();
    /**
     * 
     * @return 
     */
    public List<Inforeportes> listInforeportesUsuario();
    /**
     * 
     * @return 
     */
    public List<Empresas> listEmpresas();
    /**
     * 
     * @return 
     */
    public List<GruposConceptos> listGruposConcetos();
    /**
     * 
     * @param parametroInforme 
     */
    public void modificarParametrosInformes(ParametrosInformes parametroInforme);
    
    public List<Asociaciones> listAsociaciones();
    /**
     * 
     * @return 
     */
    public List<Procesos> listProcesos();
    /**
     * 
     * @return 
     */
    public List<Terceros> listTerceros();
    /**
     * 
     * @return 
     */
    public List<TiposTrabajadores> listTiposTrabajadores();
    /**
     * 
     * @return 
     */
    public List<Estructuras> listEstructuras();
    /**
     * 
     * @return 
     */
    public List<TiposAsociaciones> listTiposAsociaciones();
    /**
     * 
     * @return 
     */
    public List<UbicacionesGeograficas> listUbicacionesGeograficas();
    /**
     * 
     * @return 
     */
    public List<Empleados> listEmpleados();
    
}
