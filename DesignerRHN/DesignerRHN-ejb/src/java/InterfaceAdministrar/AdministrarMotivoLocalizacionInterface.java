/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosLocalizaciones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivoLocalizacionInterface {

    public void modificarMotivosLocalizaciones(List<MotivosLocalizaciones> listMotivosCambiosCargosModificadas);

    public void borrarMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones);

    public void crearMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones);

    public void buscarMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones);

    public List<MotivosLocalizaciones> mostrarMotivosCambiosCargos();

    public MotivosLocalizaciones mostrarMotivoCambioCargo(BigInteger secMotivosCambiosCargos);
}
