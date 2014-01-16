/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNReporteContabilidadInterface {
    
    public ParametrosInformes parametrosDeReporte();
    public List<Inforeportes> listInforeportesUsuario();
    public void modificarParametrosInformes(ParametrosInformes parametroInforme);
    public List<Procesos> listProcesos();
    public List<Empleados> listEmpleados();
    public void guardarCambiosInfoReportes(List<Inforeportes> listaIR);
    
}
