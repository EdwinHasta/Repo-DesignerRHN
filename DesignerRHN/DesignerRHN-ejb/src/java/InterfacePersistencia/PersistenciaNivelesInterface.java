/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Niveles;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface PersistenciaNivelesInterface {

    /**
     * Método encargado de insertar un Nivel en la base de datos.
     *
     * @param niveles Nivel que se quiere crear.
     */
    public void crear(Niveles niveles);

    /**
     * Método encargado de modificar un Nivel de la base de datos. Este método
     * recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param niveles Nivel con los cambios que se van a realizar.
     */
    public void editar(Niveles niveles);

    /**
     * Método encargado de eliminar de la base de datos el Nivel que entra por
     * parámetro.
     *
     * @param niveles Nivel que se quiere eliminar.
     */
    public void borrar(Niveles niveles);

    /**
     * Método encargado de buscar todos los Niveles existentes en la base de
     * datos, ordenados por nombre.
     *
     * @return Retorna una lista de Niveles ordenados por nombre.
     */
    public List<Niveles> consultarNiveles();

    /**
     * Método encargado de buscar el Nivel con la secNiveles dada por parámetro.
     *
     * @param secNiveles Secuencia del Nivel que se quiere encontrar.
     * @return Retorna el Nivel identificado con la secNiveles dada por
     * parámetro.
     */
    public Niveles consultarNivel(BigInteger secNiveles);

    /**
     * Método encargado de contar los EvalConvocatorias que están asociados a un
     * Nivel específico.
     *
     * @param secNiveles Secuencia del Nivel.
     * @return Retorna la cantidad de EvalConvocatorias cuyo Nivel tiene como
     * secNiveles el valor dado por parámetro.
     */
    public BigInteger contarEvalConvocatoriasNivel(BigInteger secNiveles);

    /**
     * Método encargado de contar los Plantas que están asociados a un Nivel
     * específico.
     *
     * @param secNiveles Secuencia del Nivel.
     * @return Retorna la cantidad de Plantas cuyo Nivel tiene como secNiveles
     * el valor dado por parámetro.
     */
    public BigInteger contarPlantasNivel(BigInteger secNiveles);

    /**
     * Método encargado de contar los PlantasPersonales que están asociados a un
     * Nivel específico.
     *
     * @param secNiveles Secuencia del Nivel.
     * @return Retorna la cantidad de PlantasPersonales cuyo Nivel tiene como
     * secNiveles el valor dado por parámetro.
     */
    public BigInteger contarPlantasPersonalesNivel(BigInteger secNiveles);
}
