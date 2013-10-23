/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Motivosmvrs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosMvrsInterface {

    public void modificarMotivosMvrs(List<Motivosmvrs> listNormasLaboralesModificadas);

    public void borrarMotivosMvrs(Motivosmvrs motivosMvrs);

    public void crearMotivosMvrs(Motivosmvrs notivosMvrs);

    public List<Motivosmvrs> mostrarMotivosMvrs();

    public Motivosmvrs mostrarMotivosMvrs(BigInteger secMotivosMvrs);
}
