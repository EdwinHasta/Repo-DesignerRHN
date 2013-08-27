package InterfacePersistencia;

import Entidades.Empleados;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaEmpleadoInterface {
    
    public void crear(Empleados empleados);
    public void editar(Empleados empleados);
    public void borrar(Empleados empleados);
    public Empleados buscarEmpleado(Object id);
    public List<Empleados> buscarEmpleados();
    public Empleados buscarEmpleadoSecuencia(BigInteger secuencia);
    public boolean verificarCodigoEmpleado_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa);
    public Empleados buscarEmpleadoCodigo_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa);
    public Empleados buscarEmpleadoCodigo(BigInteger codigoEmpleado);
}
