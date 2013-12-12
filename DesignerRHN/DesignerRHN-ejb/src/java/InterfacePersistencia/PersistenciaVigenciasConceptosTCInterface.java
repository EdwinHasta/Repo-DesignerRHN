/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasConceptosTC;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasConceptosTCInterface {

    public boolean verificacionZonaTipoContrato(BigInteger secuenciaConcepto, BigInteger secuenciaTC);

    public List<VigenciasConceptosTC> listVigenciasConceptosTCPorConcepto(BigInteger secuenciaC);

    public void borrar(VigenciasConceptosTC conceptosTC);

    public void editar(VigenciasConceptosTC conceptosTC);

    public void crear(VigenciasConceptosTC conceptosTC);
}
