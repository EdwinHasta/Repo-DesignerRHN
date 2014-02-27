/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposConclusiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposConclusionesInterface {

    public void modificarTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones);

    public void borrarTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones);

    public void crearTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones);

    public List<TiposConclusiones> consultarTiposConclusiones();

    public TiposConclusiones consultarTipoConclusion(BigInteger secTiposConclusiones);

    public BigInteger contarProcesosTipoConclusion(BigInteger secTiposConclusiones);
}
