/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosCambiosCargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface AdministrarMotivosCambiosCargosInterface {

    public List<MotivosCambiosCargos> consultarMotivosCambiosCargos();
    public MotivosCambiosCargos consultarMotivosCambiosCargosPorSec(BigInteger secuenciaMCC);
    public List<String> consultarNombresMotivosCambiosCargos();
    public void modificarMotivosCambiosCargos(List<MotivosCambiosCargos> listMotivosCambiosCargosModificadas);
    public void borrarMotivosCambiosCargos(MotivosCambiosCargos motivosCambiosCargos);
    public void crearMotivosCambiosCargos(MotivosCambiosCargos motivosCambiosCargos);
    public BigInteger verificarBorradoVC(BigInteger secuenciaMovitoCambioCargo);
}
