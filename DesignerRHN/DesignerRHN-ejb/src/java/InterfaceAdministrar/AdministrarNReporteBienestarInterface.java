/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Actividades;
import Entidades.Empleados;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNReporteBienestarInterface {
    
    public ParametrosInformes parametrosDeReporte();
    public List<Inforeportes> listInforeportesUsuario();
    public void modificarParametrosInformes(ParametrosInformes parametroInforme);
    public List<Actividades> listActividades();
    public List<Empleados> listEmpleados();
    
}
