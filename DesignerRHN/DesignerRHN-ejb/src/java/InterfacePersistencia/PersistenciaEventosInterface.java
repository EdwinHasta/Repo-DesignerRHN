/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Eventos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaEventosInterface {

    public void crear(Eventos eventos);

    public void editar(Eventos eventos);

    public void borrar(Eventos eventos);

    public Eventos buscarEvento(BigInteger secuencia);

    public List<Eventos> buscarEventos();
}
