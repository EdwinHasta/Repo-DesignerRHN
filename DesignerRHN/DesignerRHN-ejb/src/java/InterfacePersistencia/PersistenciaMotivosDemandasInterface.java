/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosDemandas;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaMotivosDemandasInterface {

    public List<MotivosDemandas> buscarMotivosDemandas();

    public void borrar(MotivosDemandas motivosDemandas);

    public void editar(MotivosDemandas motivosDemandas);

    public void crear(MotivosDemandas motivosDemandas);
}
