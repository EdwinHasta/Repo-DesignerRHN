/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosContratos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosContratosInterface {

    /**
     * Método encargado de modificar MotivosContratos.
     *
     * @param listaMotivosContratos Lista MotivosContratos que se van a
     * modificar.
     */
    public void modificarMotivosContratos(List<MotivosContratos> listaMotivosContratos);

    /**
     * Método encargado de borrar MotivosContratos.
     *
     * @param listaMotivosContratos Lista MotivosContratos que se van a borrar.
     */
    public void borrarMotivosContratos(List<MotivosContratos> listaMotivosContratos);

    /**
     * Método encargado de crear MotivosContratos.
     *
     * @param listaMotivosContratos Lista MotivosContratos que se van a crear.
     */
    public void crearMotivosContratos(List<MotivosContratos> listaMotivosContratos);

    /**
     * Método encargado de recuperar las MotivosContratos para una tabla de la
     * pantalla.
     *
     * @return Retorna una lista de MotivosContratos.
     */
    public List<MotivosContratos> consultarMotivosContratos();

    /**
     * Método encargado de recuperar una MotivoContrato dada su secuencia.
     *
     * @param secMotivosContratos Secuencia del MotivoContrato
     * @return Retorna una MotivosContratos.
     */
    public MotivosContratos consultarMotivoContrato(BigInteger secMotivosContratos);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * MotivoContrato específica y algún TiposContratos. Adémas de la
     * revisión, establece cuantas relaciones existen.
     *
     * @param secMotivosContratos Secuencia del MotivoContrato.
     * @return Retorna el número de TiposContratos relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger contarVigenciasTiposContratosMotivoContrato(BigInteger secMotivosContratos);
}
