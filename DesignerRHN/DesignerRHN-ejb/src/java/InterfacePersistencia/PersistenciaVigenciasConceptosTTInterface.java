/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasConceptosTT;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasConceptosTTInterface {

    public boolean verificacionZonaTipoTrabajador(BigInteger secuenciaConcepto, BigInteger secuenciaTT);

    public void crear(VigenciasConceptosTT conceptosTT);

    public void editar(VigenciasConceptosTT conceptosTT);

    public List<VigenciasConceptosTT> listVigenciasConceptosTTPorConcepto(BigInteger secuenciaC);

    public void borrar(VigenciasConceptosTT conceptosTT);
}
