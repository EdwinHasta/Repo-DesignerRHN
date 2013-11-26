/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Eventos;
import Entidades.VigenciasEventos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarEmplVigenciaEventoInterface {

    public List<VigenciasEventos> listVigenciasEventosEmpleado(BigInteger secuencia);

    public void crearVigenciasEventos(List<VigenciasEventos> listaV);

    public void editarVigenciasEventos(List<VigenciasEventos> listaV);

    public void borrarVigenciasEventos(List<VigenciasEventos> listaV);

    public List<Eventos> listEventos();

    public Empleados empleadoActual(BigInteger secuencia);
}
