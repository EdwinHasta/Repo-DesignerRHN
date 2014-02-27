/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposConclusiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposConclusionesInterface {

    public void crear(TiposConclusiones tiposConclusiones);

    public void editar(TiposConclusiones tiposConclusiones);

    public void borrar(TiposConclusiones tiposConclusiones);

    public List<TiposConclusiones> consultarTiposConclusiones();

    public TiposConclusiones consultarTipoConclusion(BigInteger secuencia);

    public BigInteger contarChequeosMedicosTipoConclusion(BigInteger secuencia);
}
