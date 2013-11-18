/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Enfermedades;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaEnfermedadesInterface {

    public void crear(Enfermedades enfermedades);

    public void editar(Enfermedades enfermedades);

    public void borrar(Enfermedades enfermedades);

    public Enfermedades buscarEnfermedad(BigInteger secuencia);

    public List<Enfermedades> buscarEnfermedades();
}
