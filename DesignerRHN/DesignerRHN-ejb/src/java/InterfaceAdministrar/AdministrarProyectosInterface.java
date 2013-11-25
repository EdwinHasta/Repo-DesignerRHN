/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empresas;
import Entidades.Monedas;
import Entidades.Proyectos;
import Entidades.PryClientes;
import Entidades.PryPlataformas;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarProyectosInterface {

    public List<Proyectos> Proyectos();

    public List<Proyectos> lovProyectos();

    public void crearProyectos(List<Proyectos> crearList);

    public void editarProyectos(List<Proyectos> editarList);

    public void borrarProyectos(List<Proyectos> borrarList);

    public List<PryClientes> listPryClientes();

    public List<PryPlataformas> listPryPlataformas();

    public List<Empresas> listEmpresas();

    public List<Monedas> listMonedas();
}
