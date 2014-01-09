/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MotivosDefinitivas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'MotivosDefinitivas' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaMotivosDefinitivasInterface {

    /**
     * Método encargado de insertar un MotivoDefinitiva en la base de datos.
     *
     * @param motivosDefinitivas MotivoDefinitiva que se quiere crear.
     */
    public void crear(MotivosDefinitivas motivosDefinitivas);

    /**
     * Método encargado de modificar un MotivoDefinitiva de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param motivosDefinitivas MotivoDefinitiva con los cambios que se van a
     * realizar.
     */
    public void editar(MotivosDefinitivas motivosDefinitivas);

    /**
     * Método encargado de eliminar de la base de datos el MotivoDefinitiva que
     * entra por parámetro.
     *
     * @param motivosDefinitivas MotivoDefinitiva que se quiere eliminar.
     */
    public void borrar(MotivosDefinitivas motivosDefinitivas);

    /**
     * Método encargado de buscar todos los Motivosdefinitivas existentes en la
     * base de datos, ordenados por código.
     *
     * @return Retorna una lista de Motivosdefinitivas ordenados por código.
     */
    public List<MotivosDefinitivas> buscarMotivosDefinitivas();

    /**
     * Método encargado de revisar si existe una relacion entre un Motivo
     * Definitiva específica y algúna Novedas Sistema. Adémas de la revisión,
     * cuenta cuantas relaciones existen.
     *
     * @param secuencia Secuencia del Motivo Definitiva.
     * @return Retorna el número de Novedades Sistemas relacionados con la
     * moneda cuya secuencia coincide con el parámetro.
     */
    public BigInteger contadorNovedadesSistema(BigInteger secuencia);

    /**
     * Método encargado de revisar si existe una relacion entre un Motivo
     * Definitiva específica y algúna Parameto Cambio Masivo. Adémas de la
     * revisión, cuenta cuantas relaciones existen.
     *
     * @param secuencia Secuencia del Motivo Definitiva.
     * @return Retorna el número de Parameto Cambio Masivo relacionados con la
     * moneda cuya secuencia coincide con el parámetro.
     */
    public BigInteger contadorParametrosCambiosMasivos(BigInteger secuencia);

    public MotivosDefinitivas buscarMotivoDefinitiva(BigInteger secuenciaME);
}
