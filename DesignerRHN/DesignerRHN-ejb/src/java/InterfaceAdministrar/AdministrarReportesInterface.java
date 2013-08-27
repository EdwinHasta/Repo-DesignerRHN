/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empresas;
import Entidades.GruposConceptos;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarReportesInterface {
    
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
    
}
