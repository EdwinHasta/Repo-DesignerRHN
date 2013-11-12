/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Soaccidentes;
import java.math.BigInteger;
import java.util.List;


/**
 *
 * @author Viktor
 */
public interface PersistenciaSoaccidentesInterface {

    public void crear(Soaccidentes soaccidentes);

    public void editar(Soaccidentes soaccidentes);

    public void borrar(Soaccidentes soaccidentes);

    public List<Soaccidentes> accidentesEmpleado(BigInteger secuenciaEmpleado);
}
