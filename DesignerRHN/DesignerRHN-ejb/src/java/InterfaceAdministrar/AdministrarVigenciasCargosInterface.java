package InterfaceAdministrar;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosCambiosCargos;
import Entidades.VWActualesTiposTrabajadores;
import Entidades.VigenciasCargos;
import java.math.BigInteger;
import java.util.List;

public interface AdministrarVigenciasCargosInterface {

    public List<VigenciasCargos> consultarTodo();

    public VigenciasCargos consultarPorSecuencia(BigInteger secuenciaVC);

    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado);

    public void editarVigenciaCargo(VigenciasCargos vigencia);

    public void modificarVC(List<VigenciasCargos> listVCModificadas);

    public void borrarVC(VigenciasCargos vigenciasCargos);

    public void crearVC(VigenciasCargos vigenciasCargos);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public void salir();

    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador();
}
