/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Bancos;
import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarReportesBancosInterface {
    
    public ParametrosInformes parametrosDeReporte();
    public List<Inforeportes> listInforeportesUsuario();
    public void modificarParametrosInformes(ParametrosInformes parametroInforme);
    public List<Empresas> listEmpresas();
    public List<Empleados> listEmpleados();
    public List<TiposTrabajadores> listTiposTrabajadores();
    public List<Procesos> listProcesos();
    public List<Bancos> listBancos();
    public List<Ciudades> listCiudades();
    
}
