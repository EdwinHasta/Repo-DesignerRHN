/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Juzgados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarJuzgadosInterface {
     public List<Ciudades> buscarCiudades();

    public void modificarJuzgados(Juzgados juzgados);
    public void borrarJuzgados(Juzgados juzgados);
    public void crearJuzgados(Juzgados juzgados) ;
    public List<Juzgados> buscarJuzgadosPorCiudad(BigInteger secCiudad) ;
    public List<Juzgados> buscarJuzgadosPorCiudadGeneral();
    public BigInteger verificarEerPrestamos(BigInteger secuenciaJuzgados);
    public boolean isNumeric(String cadena);
}
