/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Motivosmvrs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaMotivosMvrsInterface {

    public void crear(Motivosmvrs motivosMvrs);

    public void editar(Motivosmvrs motivosMvrs);

    public void borrar(Motivosmvrs motivosMvrs);

    public Motivosmvrs buscarMotivosMvrs(BigInteger secuenciaMM);

    public List<Motivosmvrs> buscarMotivosMvrs();
}
