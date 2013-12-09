/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasConceptosRL;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasConceptosRLInterface {

    public boolean verificacionZonaTipoReformasLaborales(BigInteger secuenciaConcepto, BigInteger secuenciaTS);

    public void crear(VigenciasConceptosRL conceptosRL);

    public void borrar(VigenciasConceptosRL conceptosRL);

    public void editar(VigenciasConceptosRL conceptosRL);

    public List<VigenciasConceptosRL> listVigenciasConceptosRLPorConcepto(BigInteger secuenciaC);
}
