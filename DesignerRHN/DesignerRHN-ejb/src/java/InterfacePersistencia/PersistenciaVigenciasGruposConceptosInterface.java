/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasGruposConceptos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaVigenciasGruposConceptosInterface {

    public boolean verificacionZonaTipoTrabajador(BigInteger secuenciaConcepto);

    public void crear(VigenciasGruposConceptos gruposConceptos);

    public void editar(VigenciasGruposConceptos gruposConceptos);

    public List<VigenciasGruposConceptos> listVigenciasGruposConceptosPorConcepto(BigInteger secuenciaC);

    public void borrar(VigenciasGruposConceptos gruposConceptos);
}
