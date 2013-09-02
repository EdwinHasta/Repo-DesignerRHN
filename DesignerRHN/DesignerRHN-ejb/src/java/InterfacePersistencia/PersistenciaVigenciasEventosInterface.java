/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasEventos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasEventosInterface {

    public List<VigenciasEventos> eventosPersona(BigInteger secuenciaEmpl);
}
