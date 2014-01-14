/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosCambiosSueldos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosCambiosSueldosInterface {

    public void modificarMotivosCambiosSueldos(List<MotivosCambiosSueldos> listMotivosCambiosSueldosModificadas);

    public void borrarMotivosCambiosSueldos(MotivosCambiosSueldos motivosCambiosSueldos);

    public void crearMotivosCambiosSueldos(MotivosCambiosSueldos motivosCambiosSueldos);

    public List<MotivosCambiosSueldos> mostrarMotivosCambiosSueldos();

    public MotivosCambiosSueldos mostrarMotivoCambioCargo(BigInteger secMotivosCambiosSueldos);

    public BigInteger verificarBorradoVS(BigInteger secuenciaMovitoCambioSueldo);
}
