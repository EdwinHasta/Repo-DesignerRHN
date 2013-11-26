/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasDeportes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasDeportesInterface {

    public List<VigenciasDeportes> deportePersona(BigInteger secuenciaPersona);

    public void crear(VigenciasDeportes vigenciasDeportes);

    public void editar(VigenciasDeportes vigenciasDeportes);

    public void borrar(VigenciasDeportes vigenciasDeportes);

    public List<VigenciasDeportes> deportesTotalesSecuenciaPersona(BigInteger secuenciaP);
}
