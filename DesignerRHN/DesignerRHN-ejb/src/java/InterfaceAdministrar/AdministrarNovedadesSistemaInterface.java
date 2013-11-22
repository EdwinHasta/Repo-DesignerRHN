/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.Motivosdefinitivas;
import Entidades.NovedadesSistema;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNovedadesSistemaInterface {

    public List<NovedadesSistema> novedadesEmpleado(BigInteger secuenciaEmpleado);

    public void borrarNovedades(NovedadesSistema novedades);

    public void crearNovedades(NovedadesSistema novedades);

    public void modificarNovedades(List<NovedadesSistema> listaNovedadesModificar);

    public List<Empleados> buscarEmpleados();
    
    public List<Empleados> lovEmpleados();
    
    public List<Motivosdefinitivas> lovMotivos();
     
    public List<MotivosRetiros> lovRetiros();
    
}
