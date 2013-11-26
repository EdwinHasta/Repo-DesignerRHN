/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Deportes;
import Entidades.Empleados;
import Entidades.VigenciasDeportes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarVigenciaDeporteInterface {

    public List<VigenciasDeportes> listVigenciasDeportesPersona(BigInteger secuenciaP);

    public void crearVigenciasDeportes(List<VigenciasDeportes> listaVD);

    public void editarVigenciasDeportes(List<VigenciasDeportes> listaVD);

    public void borrarVigenciasDeportes(List<VigenciasDeportes> listaVD);

    public List<Deportes> listDeportes();
    
    public Empleados empleadoActual(BigInteger secuenciaP);
}
