package InterfacePersistencia;

import Entidades.VigenciasTiposTrabajadores;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaVigenciasTiposTrabajadoresInterface {

    /**
     * Crea un objeto VigenciaTipoTrabajador
     *
     * @param vigenciasTiposTrabajadores Objeto a crear
     */
    public void crear(VigenciasTiposTrabajadores vigenciasTiposTrabajadores);

    /**
     * Edita un objeto VigenciaTipoTrabajador
     *
     * @param vigenciasTiposTrabajadores Objeto a editar
     */
    public void editar(VigenciasTiposTrabajadores vigenciasTiposTrabajadores);

    /**
     * Borra un objeto VigenciaTipoTrabajador
     *
     * @param vigenciasTiposTrabajadores Objeto a borrar
     */
    public void borrar(VigenciasTiposTrabajadores vigenciasTiposTrabajadores);

    /**
     * Metodo que obtiene una VigenciaTipoTrabajador por la llave primaria ID
     *
     * @param id Llave Primaria ID
     * @return vigTT VigenciaTipoTrabajador que cumple con la llave primaria
     */
    public VigenciasTiposTrabajadores buscarVigenciaTipoTrabajador(Object id);

    /**
     * Metodo que obtiene todos los elementos de la tabla VigenciaTipoTrabajador
     *
     * @return listVTT Lista de VigenciaTipoTrabajador
     */
    public List<VigenciasTiposTrabajadores> buscarVigenciasTiposTrabajadores();

    /**
     * Metodo que obtiene las VigenciasTiposTrabajador de un Empleado
     *
     * @param secEmpleado Secuencia de Empleado
     * @return listVE Lista de VigenciaTipoTrabajador del Empleado
     */
    public List<VigenciasTiposTrabajadores> buscarVigenciasTiposTrabajadoresEmpleado(BigInteger secEmpleado);

    /**
     * Metodo que obtiene una VigenciaTipoTrabajador pòr la secuencia
     *
     * @param secVTT Secuencia de la VigenciaTipoTrabajador
     * @return vigTT VigenciaTipoTrabajador que cumple con la secuencia dada
     */
    public VigenciasTiposTrabajadores buscarVigenciasTiposTrabajadoresSecuencia(BigInteger secVTT);

    public List<VigenciasTiposTrabajadores> buscarEmpleados();
}
