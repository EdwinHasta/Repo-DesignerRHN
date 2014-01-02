/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Enfoques;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEnfoquesInterface {

    public void crear(Enfoques enfoques);

    public void editar(Enfoques enfoques);

    public void borrar(Enfoques enfoques);

    public Enfoques buscarEnfoque(BigInteger secuenciaEnfoques);

    public List<Enfoques> buscarEnfoques();

    public BigInteger contadorTiposDetalles(BigInteger secuencia);
}
