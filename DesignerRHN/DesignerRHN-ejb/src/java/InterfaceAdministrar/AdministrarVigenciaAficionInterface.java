/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Aficiones;
import Entidades.Empleados;
import Entidades.VigenciasAficiones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarVigenciaAficionInterface {

    public List<VigenciasAficiones> listVigenciasAficionesPersona(BigInteger secuenciaP);

    public void crearVigenciasAficiones(List<VigenciasAficiones> listVA);

    public void editarVigenciasAficiones(List<VigenciasAficiones> listVA);

    public void borrarVigenciasAficiones(List<VigenciasAficiones> listVA);

    public List<Aficiones> listAficiones();

    public Empleados empleadoActual(BigInteger secuencia);
}
