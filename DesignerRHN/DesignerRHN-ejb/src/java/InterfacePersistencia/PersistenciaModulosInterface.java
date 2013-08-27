package InterfacePersistencia;

import Entidades.Modulos;
import java.util.List;

/**
 *
 * @author -Felipphe-
 */
public interface PersistenciaModulosInterface {

    public void crear(Modulos modulos);
    public void editar(Modulos modulos);
    public void borrar(Modulos modulos);
    public Modulos buscarModulos(Object id);
    public List<Modulos> buscarModulos();
}
