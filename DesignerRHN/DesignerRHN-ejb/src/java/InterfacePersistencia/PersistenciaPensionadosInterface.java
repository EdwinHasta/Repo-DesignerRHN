package InterfacePersistencia;

import Entidades.Pensionados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaPensionadosInterface {
    /**
     * Crea un objeto Pensionados
     * @param pensionados Objeto a crear
     */
    public void crear(Pensionados pensionados);
    /**
     * Edita un objeto Pensionados
     * @param pensionados Objeto a editar
     */
    public void editar(Pensionados pensionados);
    /**
     * Borra un objeto Pensionados
     * @param pensionados Objeto a borrar
     */
    public void borrar(Pensionados pensionados);
    /**
     * Metodo que busca un Pensionado por la llave primaria ID
     * @param id Llave Primaria ID
     * @return pensionado Pensionado que cumple con la llave primaria ID
     */
    public Pensionados buscarPensionado(Object id);
    /**
     * Metodo que busca todos los Pensionados en la tabla
     * @return pensionados Lista Pensionados 
     */
    public List<Pensionados> buscarPensionados();
    /**
     * Metodo que obtiene un Pensionados por la secuencia del Empleado
     * @param secEmpleado Secuencia del empleado
     * @return listaPE Lista de pensiones por un empleado
     */
    public List<Pensionados> buscarPensionadosEmpleado(BigInteger secEmpleado);
    /**
     * Metodo que obtiene una Pension por la secuencia 
     * @param secP Secuencia de la Pension a buscar
     * @return pension Pension que cumple con la secuencia dada
     */
    public Pensionados buscarPensionSecuencia(BigInteger secP);
    /**
     * Metodo que obtiene una Pension por la secuencia de la Vigencia
     * @param secVigencia Secuencia de la Vigencia
     * @return pensionV Pension que posee la vigencia referenciada
     */
    public Pensionados buscarPensionVigenciaSecuencia(BigInteger secVigencia);
    
}
