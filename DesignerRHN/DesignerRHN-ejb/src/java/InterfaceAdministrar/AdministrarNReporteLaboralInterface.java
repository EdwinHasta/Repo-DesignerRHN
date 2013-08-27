/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Cargos;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface AdministrarNReporteLaboralInterface {
    /**
     * Obtiene los parametros de reporte laboral
     * @return Objeto ParametrosInformes para los reportes
     */
    public ParametrosInformes parametrosDeReporte();
    /**
     * Obtiene la lista de inforeportes para los reportes laborales
     * @return Lista de Inforeportes para el usuario actual
     */
    public List<Inforeportes> listInforeportesUsuario();
    /**
     * Obtiene la lista de cargos
     * @return Lista de Cargos
     */
    public List<Cargos> listCargos();
    
}
