/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.NormasLaborales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarNormasLaboralesInterface {

    public void modificarNormasLaborales(List<NormasLaborales> listNormasLaboralesModificadas);

    public void borrarNormasLaborales(NormasLaborales normasLaborales);

    public void crearNormasLaborales(NormasLaborales normasLaborales);

    public List<NormasLaborales> mostrarNormasLaborales();

    public NormasLaborales mostrarMotivoContrato(BigInteger secNormasLaborales);

    public Long verificarBorradoVNE(BigInteger secuenciaNormasLaborales);
}
