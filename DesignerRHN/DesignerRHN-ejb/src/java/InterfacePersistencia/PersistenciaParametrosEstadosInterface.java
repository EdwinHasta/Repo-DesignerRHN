/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'ParametrosEstados' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaParametrosEstadosInterface {
    /**
     * Método encargado de contar los parametrosestados asociados y autorizados al usuario.
     * @param usuarioBD Alias del usuario que esta usando la base de datos.
     * @return Retorna la cantidad de parametrosestados del usuario o nulo.
     */
    public Integer empleadosParaLiquidar(String usuarioBD);
    /**
     * Método encargado de contar los parametrosestados asociados, autorizados al usuario y que su estado es 'LIQUIDADO'.
     * @param usuarioBD Alias del usuario que esta usando la base de datos.
     * @return Retorna la cantidad de parametrosestados del usuario o nulo.
     */
    public Integer empleadosLiquidados(String usuarioBD);
    /**
     * 
     */
    public void inicializarParametrosEstados();
    /**
     * Método encargado de 
     * @param secuenciaParametro
     * @return 
     */
    public String parametrosComprobantes(BigInteger secuenciaParametro);
}
