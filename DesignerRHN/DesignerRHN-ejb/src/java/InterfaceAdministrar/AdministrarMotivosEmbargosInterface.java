/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosEmbargosInterface {

    /**
     * Método encargado de modificar MotivosEmbargos.
     *
     * @param listaMotivosEmbargos Lista MotivosEmbargos que se van a modificar.
     */
    public void modificarMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos);

    /**
     * Método encargado de borrar MotivosEmbargos.
     *
     * @param listaMotivosEmbargos Lista MotivosEmbargos que se van a borrar.
     */
    public void borrarMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos);

    /**
     * Método encargado de crear MotivosEmbargos.
     *
     * @param listaMotivosEmbargos Lista MotivosEmbargos que se van a crear.
     */
    public void crearMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos);

    /**
     * Método encargado de recuperar las MotivosEmbargos para una tabla de la
     * pantalla.
     *
     * @return Retorna una lista de MotivosEmbargos.
     */
    public List<MotivosEmbargos> mostrarMotivosEmbargos();

    /**
     * Método encargado de recuperar una MotivosEmbargos dada su secuencia.
     *
     * @param secMotivoPrestamo Secuencia del MotivosEmbargos
     * @return Retorna una MotivosEmbargos.
     */
    public MotivosEmbargos mostrarMotivoEmbargo(BigInteger secMotivoPrestamo);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * MotivosEmbargos específica y algún EersPrestamos. Adémas de la revisión,
     * establece cuantas relaciones existen.
     *
     * @param secMotivoPrestamo Secuencia del MotivosEmbargos.
     * @return Retorna el número de EersPrestamos relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger contarEersPrestamosMotivoEmbargo(BigInteger secMotivoPrestamo);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * MotivosEmbargos específica y algún Embargos. Adémas de la revisión,
     * establece cuantas relaciones existen.
     *
     * @param secMotivoPrestamo Secuencia del MotivosEmbargos.
     * @return Retorna el número de Embargos relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger contarEmbargosMotivoEmbargo(BigInteger secMotivoPrestamo);
}
