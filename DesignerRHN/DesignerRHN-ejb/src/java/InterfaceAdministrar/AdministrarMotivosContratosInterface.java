/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosContratos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosContratosInterface {

    public void modificarMotivosContratos(List<MotivosContratos> listMotivosCambiosCargosModificadas);

    public void borrarMotivosContratos(MotivosContratos motivosCambiosCargos);

    public void crearMotivosContratos(MotivosContratos motivosCambiosCargos);

    public List<MotivosContratos> mostrarMotivosContratos();

    public MotivosContratos mostrarMotivoContrato(BigInteger secMotivosCambiosCargos);

    public BigInteger verificarBorradoVC(BigInteger secuenciaMovitoCambioCargo);
}
