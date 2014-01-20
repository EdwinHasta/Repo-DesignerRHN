/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosDefinitivas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosDefinitivasInterface {

    /**
     * Método encargado de modificar MotivosDefinitivas.
     *
     * @param listaMotivosDefinitivas Lista MotivosDefinitivas que se van a
     * modificar.
     */
    public void modificarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas);

    /**
     * Método encargado de borrar MotivosDefinitivas.
     *
     * @param listaMotivosDefinitivas Lista MotivosDefinitivas que se van a
     * borrar.
     */
    public void borrarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas);

    /**
     * Método encargado de crear MotivosDefinitivas.
     *
     * @param listaMotivosDefinitivas Lista MotivosDefinitivas que se van a
     * crear.
     */
    public void crearMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas);

    /**
     * Método encargado de recuperar las MotivosDefinitivas para una tabla de la
     * pantalla.
     *
     * @return Retorna una lista de MotivosDefinitivas.
     */
    public List<MotivosDefinitivas> mostrarMotivosDefinitivas();

    /**
     * Método encargado de recuperar una MotivoDefinitivas dada su secuencia.
     *
     * @param secMotivosDefinitivas Secuencia del MotivoDefinitivas
     * @return Retorna una MotivosDefinitivas.
     */
    public MotivosDefinitivas mostrarMotivoDefinitiva(BigInteger secMotivosDefinitivas);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * MotivoDefinitivas específica y algún NovedadesSistemas. Adémas de la
     * revisión, establece cuantas relaciones existen.
     *
     * @param secMotivosDefinitivas Secuencia del MotivoDefinitivas.
     * @return Retorna el número de NovedadesSistemas relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger contarNovedadesSistemasMotivoDefinitiva(BigInteger secMotivosDefinitivas);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * MotivoDefinitivas específica y algún ParametrosCambiosMasivos. Adémas de
     * la revisión, establece cuantas relaciones existen.
     *
     * @param secMotivosDefinitivas Secuencia del MotivoDefinitivas.
     * @return Retorna el número de ParametrosCambiosMasivos relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger contarParametrosCambiosMasivosMotivoDefinitiva(BigInteger secMotivosDefinitivas);
}
