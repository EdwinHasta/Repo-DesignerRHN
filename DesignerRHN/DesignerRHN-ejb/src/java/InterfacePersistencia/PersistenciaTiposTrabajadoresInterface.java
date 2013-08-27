package InterfacePersistencia;

import Entidades.TiposTrabajadores;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaTiposTrabajadoresInterface {
    
    /**
     * Crea un objeto TiposTrabajadores
     * @param tiposTrabajadores Objeto a crear
     */
    public void crear(TiposTrabajadores tiposTrabajadores);
    /**
     * Edita un objeto TiposTrabajadores
     * @param tiposTrabajadores Objeto a editar
     */
    public void editar(TiposTrabajadores tiposTrabajadores);
    /**
     * Borra un objeto TiposTrabajadores
     * @param tiposTrabajadores Objeto a borrar
     */
    public void borrar(TiposTrabajadores tiposTrabajadores);
    /**
     * Metodo que obtiene un TipoTrabajador por la llave primaria ID
     * @param id Llave Primaria ID
     * @return tipoT Tipo Trabajador que cumple con la condicion de la llave primaria
     */
    public TiposTrabajadores buscarTipoTrabajador(Object id);
    /**
     * Metodo que obtiene todos los TiposTrabajadores
     * @return listTT Lista de Tipos Trabajadores
     */
    public List<TiposTrabajadores> buscarTiposTrabajadores();
    /**
     * Metodo que obtiene un TipoTrabajador por la secuencia
     * @param secuencia Secuencia del TipoTrabajador
     * @return tipoT TipoTrabajador que cumple con la secuencia dada
     */
    public TiposTrabajadores buscarTipoTrabajadorSecuencia(BigInteger secuencia);
    /**
     * Metodo que obtiene un TipoTrabajador por el codigo
     * @param codigo Codigo del TipoTrabajador
     * @return tipoT TipoTrabajador que cumple con el codigo dado
     */
    public TiposTrabajadores buscarTipoTrabajadorCodigo (BigDecimal codigo);
    
}
