/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.Eventos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEventosInterface {
     public void modificarEventos(List<Eventos> listDeportesModificadas);
    public void borrarEventos(Eventos deportes);

    public void crearEventos(Eventos deportes);
    public List<Eventos> mostrarEventos();
    public Eventos mostrarEvento(BigInteger secDeportes);

    public BigInteger verificarBorradoVigenciasEventos(BigInteger secuenciaEventos);
}
