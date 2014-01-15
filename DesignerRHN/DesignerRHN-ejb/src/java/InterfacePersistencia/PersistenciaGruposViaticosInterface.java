/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposViaticos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaGruposViaticosInterface {

    /**
     * Método encargado de insertar un Grupo Viatico en la base de datos.
     *
     * @param gruposViaticos GruposViaticos que se quiere crear.
     */
    public void crear(GruposViaticos gruposViaticos);

    /**
     * Método encargado de modificar un GrupoViatico de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param gruposViaticos GruposViaticos con los cambios que se van a
     * realizar.
     */
    public void editar(GruposViaticos gruposViaticos);

    /**
     * Método encargado de eliminar de la base de datos un GrupoViatico que
     * entra por parámetro.
     *
     * @param gruposViaticos GruposViaticos que se quiere eliminar.
     */
    public void borrar(GruposViaticos gruposViaticos);

    /**
     * Método encargado de buscar el GrupoViatico con la secuencia dada por
     * parámetro.
     *
     * @param secGruposViaticos Secuencia del GrupoViatico que se quiere
     * encontrar.
     * @return Retorna el GrupoViatico identificado con la secuencia dada por
     * parámetro.
     */
    public GruposViaticos buscarGrupoViatico(BigInteger secGruposViaticos);

    /**
     * Método encargado de buscar todas los GruposViaticos existentes en la base
     * de datos.
     *
     * @return Retorna una lista de GruposViaticos.
     */
    public List<GruposViaticos> buscarGruposViaticos();

    /**
     * Método encargado de revisar si existe una relacion entre un Grupo Viatico
     * específica y algún Cargo. Adémas de la revisión, cuenta cuantas
     * relaciones existen.
     *
     * @param secGruposViaticos Secuencia del GrupoViatico.
     * @return Retorna el número de Cargos relacionados con el GrupoViatico cuya
     * secGruposViaticos coincide con el parámetro.
     */
    public BigInteger contadorCargos(BigInteger secGruposViaticos);

    /**
     * Método encargado de revisar si existe una relacion entre un Grupo Viatico
     * específica y algúna Planta. Adémas de la revisión, cuenta cuantas
     * relaciones existen.
     *
     * @param secGruposViaticos Secuencia del GrupoViatico.
     * @return Retorna el número de Plantas relacionados con el GrupoViatico
     * cuya secGruposViaticos coincide con el parámetro.
     */
    public BigInteger contadorPlantas(BigInteger secGruposViaticos);

    /**
     * Método encargado de revisar si existe una relacion entre un Grupo Viatico
     * específica y algúna TablaViatico. Adémas de la revisión, cuenta cuantas
     * relaciones existen.
     *
     * @param secGruposViaticos Secuencia del GrupoViatico.
     * @return Retorna el número de TablasViaticos relacionados con el
     * GrupoViatico cuya secGruposViaticos coincide con el parámetro.
     */
    public BigInteger contadorTablasViaticos(BigInteger secGruposViaticos);

    /**
     * Método encargado de revisar si existe una relacion entre un Grupo Viatico
     * específica y algún EersViaticos. Adémas de la revisión, cuenta cuantas
     * relaciones existen.
     *
     * @param secGruposViaticos Secuencia del GrupoViatico.
     * @return Retorna el número de EersViaticos relacionados con el
     * GrupoViatico cuya secGruposViaticos coincide con el parámetro.
     */
    public BigInteger contadorEersViaticos(BigInteger secGruposViaticos);
}
