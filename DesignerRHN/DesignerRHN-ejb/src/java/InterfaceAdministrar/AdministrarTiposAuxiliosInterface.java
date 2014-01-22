/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposAuxilios;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposAuxiliosInterface {

    /**
     * Método encargado de modificar TiposAuxilios.
     *
     * @param listaTiposAuxilios Lista TiposAuxilios que se van a modificar.
     */
    public void modificarTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios);

    /**
     * Método encargado de borrar TiposAuxilios.
     *
     * @param listaTiposAuxilios Lista TiposAuxilios que se van a borrar.
     */
    public void borrarTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios);

    /**
     * Método encargado de crear TiposAuxilios.
     *
     * @param listaTiposAuxilios Lista TiposAuxilios que se van a crear.
     */
    public void crearTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios);

    /**
     * Método encargado de recuperar un TipoAuxilio dada su secuencia.
     *
     * @param secTiposAuxilios Secuencia del TipoAuxilio.
     * @return Retorna el TipoAuxilio cuya secuencia coincida con el valor del
     * parámetro.
     */
    public TiposAuxilios consultarTipoAuxilio(BigInteger secTiposAuxilios);

    /**
     * Metodo encargado de traer todas las TiposAuxilios de la base de datos.
     *
     * @return Lista de TiposAuxilios.
     */
    public List<TiposAuxilios> consultarTiposAuxilios();

    /**
     * Método encargado de contar la cantidad de TablasAuxilios relacionadas con
     * un TipoAuxilio específico.
     *
     * @param secTiposAuxilios Secuencia del TipoAuxilio.
     * @return Retorna un número indicando la cantidad de TablasAuxilios cuya
     * secuencia coincide con el valor del parámetro.
     */
    public BigInteger contarTablasAuxiliosTiposAuxilios(BigInteger secTiposAuxilios);
}
