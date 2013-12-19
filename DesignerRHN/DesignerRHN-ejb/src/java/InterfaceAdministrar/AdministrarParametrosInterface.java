/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import Entidades.Usuarios;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarParametrosInterface {
    public Usuarios usuarioActual();
    public ParametrosEstructuras parametrosLiquidacion();
    public List<Estructuras> lovEstructuras();
    public List<TiposTrabajadores> lovTiposTrabajadores();
    public List<Procesos> lovProcesos();
    public List<Parametros> empleadosParametros();
    public String estadoParametro(BigInteger secuenciaParametro);
    public void crearParametroEstructura(ParametrosEstructuras parametroEstructura);
    public void eliminarParametros(List<Parametros> listaParametros);
    public void crearParametros(List<Parametros> listaParametros);
    public void adicionarEmpleados(BigInteger secParametroEstructura);
    public void borrarParametros(BigInteger secParametroEstructura);
    public Integer empleadosParametrizados(BigInteger secProceso);
    public Integer diferenciaDias(String fechaInicial, String fechaFinal);
    public List<Empleados> empleadosLov();
}
