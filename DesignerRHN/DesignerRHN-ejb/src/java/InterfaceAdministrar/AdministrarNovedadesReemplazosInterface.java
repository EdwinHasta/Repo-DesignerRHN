/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.Estructuras;
import Entidades.MotivosReemplazos;
import Entidades.TiposReemplazos;
import java.math.BigInteger;
import java.util.List;

public interface AdministrarNovedadesReemplazosInterface {

    public List<Encargaturas> encargaturasEmpleado(BigInteger secEmpleado);

    public Empleados encontrarEmpleado(BigInteger secEmpleado);

    public List<Empleados> lovEmpleados();

    public List<TiposReemplazos> lovTiposReemplazos();

    public List<MotivosReemplazos> lovMotivosReemplazos();

    public List<Estructuras> lovEstructuras();
    //Falta Cargos

    public void modificarEncargatura(List<Encargaturas> listaEncargaturasModificar);

    public void borrarEncargaturas(Encargaturas encargaturas);

    public void crearEncargaturas(Encargaturas encargaturas);
}
