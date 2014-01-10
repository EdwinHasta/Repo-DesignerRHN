/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosDemandas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarMotivosDemandasInterface {

    public void modificarMotivosDemandas(List<MotivosDemandas> listMotivosDemandasModificadas);

    public void borrarMotivosDemandas(MotivosDemandas motivoDemanda);

    public void crearMotivosDemandas(MotivosDemandas motivoDemanda);

    public List<MotivosDemandas> mostrarMotivosDemandas();

    public MotivosDemandas mostrarMotivoDemanda(BigInteger secMotivoDemanda);

    public BigInteger verificarBorradoDemanda(BigInteger secuenciaEventos);
}
