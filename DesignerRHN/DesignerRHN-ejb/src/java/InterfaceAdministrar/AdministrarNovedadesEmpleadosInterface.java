/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.Novedades;
import Entidades.Periodicidades;
import Entidades.PruebaEmpleados;
import Entidades.Terceros;
import Entidades.Usuarios;
import Entidades.VWActualesTiposTrabajadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface AdministrarNovedadesEmpleadosInterface {

    public List<PruebaEmpleados> empleadosNovedad();

    public List<Empleados> lovEmpleados();

    public Empleados elEmpleado(BigInteger secuenciaEmpleado);

    public PruebaEmpleados novedadEmpleado(BigInteger secuenciaEmpleado);

    public List<VWActualesTiposTrabajadores> tiposTrabajadores();

    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado);

    public List<Conceptos> lovConceptos();

    public List<Formulas> lovFormulas();

    public List<Periodicidades> lovPeriodicidades();

    public List<Terceros> lovTerceros();

    public void borrarNovedades(Novedades novedades);

    public void crearNovedades(Novedades novedades);

    public void modificarNovedades(List<Novedades> listaNovedadesModificar);

    public String alias();
     
}
