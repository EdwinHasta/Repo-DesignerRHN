package InterfacePersistencia;

import Entidades.VigenciasProrrateos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */

public interface PersistenciaVigenciasProrrateosInterface {
    
    /**
     * Crea una nueva VigenciaProrrateo
     * @param vigenciasProrrateos Objeto a crear
     */
    public void crear(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Edita una VigenciaProrrateo
     * @param vigenciasProrrateos Objeto a editar
     */
    public void editar(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Borra una VigenciaProrrateo
     * @param vigenciasProrrateos Objeto a borrar
     */
    public void borrar(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Busca una VigenciaProrrateo por medio de la llave primaria ID
     * @param id Llave Primaria ID
     * @return vP Vigencia Prorrateo que cumple con la llave primaria
     */
    public VigenciasProrrateos buscarVigenciaProrrateo(Object id);
    /**
     * Obtiene todos los elementos de la tabla VigenciasProrrateos
     * @return listVP Lista de Vigencias Prorrateos
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateos();
    /**
     * Obtiene la lista de VigenciaProrrateo de un Empleado especifico
     * @param secEmpleado Secuencia Empleado
     * @return listVPE Lista de Vigencias Prorrateos de un Empleado
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateosEmpleado(BigInteger secEmpleado);
    /**
     * Obtiene una VigenciaProrrateo por medio de la secuencia 
     * @param secVP Secuencia VigenciaProrrateo
     * @return vP Vigencia Prorrateo que cumple con la secuencia dad
     */
    public VigenciasProrrateos buscarVigenciaProrrateoSecuencia(BigInteger secVP);
    /**
     * Obtiene la lista de VigenciasProrrateos de una VigenciaLocalizacion
     * @param secVigencia Secuencia VigenciaLocalizacion
     * @return listVPVL Lista de Vigencias Prorrateos de una Vigencia Localizacion
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateosVigenciaSecuencia(BigInteger secVigencia);
    
}
