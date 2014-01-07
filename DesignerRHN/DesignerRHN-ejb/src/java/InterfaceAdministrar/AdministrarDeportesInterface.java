/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.Deportes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarDeportesInterface {

    public void modificarDeportes(List<Deportes> listDeportesModificadas);
    public void borrarDeportes(Deportes deportes);
    public void crearDeportes(Deportes deportes) ;
    public List<Deportes> mostrarDeportes();
    public Deportes mostrarDeporte(BigInteger secDeportes);
    public BigInteger verificarBorradoVigenciasDeportes(BigInteger secuenciaTiposAuxilios);
    public BigInteger contadorDeportesPersonas(BigInteger secuenciaTiposAuxilios);
    public BigInteger contadorParametrosInformes(BigInteger secuenciaTiposAuxilios);
}
