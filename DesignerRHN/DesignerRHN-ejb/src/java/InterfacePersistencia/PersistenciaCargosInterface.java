package InterfacePersistencia;

import Entidades.Cargos;
import java.util.List;


public interface PersistenciaCargosInterface {
    
    public void crear(Cargos cargos);
    public void editar(Cargos cargos);
    public void borrar(Cargos cargos);
    public Cargos buscarCargo(Object id);
    public List<Cargos> buscarCargos();
    public List<Cargos> cargos();
    public List<Cargos> cargosSalario();
}
    