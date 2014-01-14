/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EvalCompetencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEvalCompetenciasInterface {

    /**
     * Método encargado de insertar un EvalCompetencia en la base de datos.
     *
     * @param evalCompetencias Nibeda que se quiere crear.
     */
    public void crear(EvalCompetencias evalCompetencias);

    /**
     * Método encargado de modificar un EvalCompetencia de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param evalCompetencias EvalCompetencias con los cambios que se van a
     * realizar.
     */
    public void editar(EvalCompetencias evalCompetencias);

    /**
     * Método encargado de eliminar de la base de datos un EvalCompetencia que
     * entra por parámetro.
     *
     * @param evalCompetencias EvalCompetencias que se quiere eliminar.
     */
    public void borrar(EvalCompetencias evalCompetencias);

    /**
     * Método encargado de buscar un EvalCompetencia con la secuencia dada por
     * parámetro.
     *
     * @param secEvalCompetencias Secuencia del EvalCompetencia que se quiere
     * encontrar.
     * @return Retorna un EvalCompetencia identificado con la secuencia dada por
     * parámetro.
     */
    public EvalCompetencias buscarEvalCompetencia(BigInteger secEvalCompetencias);

    /**
     * Método encargado de buscar todas las EvalCompetencias existentes en la
     * base de datos.
     *
     * @return Retorna una lista de EvalCompetencias.
     */
    public List<EvalCompetencias> buscarEvalCompetencias();

    /**
     * Método encargado de revisar si existe una relacion entre una
     * EvalCompetencia específica y algúna CompetenciaCargo. Adémas de la
     * revisión, cuenta cuantas relaciones existen.
     *
     * @param secEvalCompetencias Secuencia de la EvalCompetencias.
     * @return Retorna el número de CompetenciasCargos relacionados con la
     * EvalCompetencias cuya secuencia coincide con el parámetro.
     */
    public BigInteger contadorCompetenciasCargos(BigInteger secEvalCompetencias);
}
