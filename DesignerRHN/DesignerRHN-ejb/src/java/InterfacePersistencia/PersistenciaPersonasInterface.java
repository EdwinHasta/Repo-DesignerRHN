package InterfacePersistencia;

import Entidades.Personas;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaPersonasInterface {
    
    public void crear(Personas personas);
    public void editar(Personas personas);
    public void borrar(Personas personas);
    public Personas buscarPersona(BigInteger id);
    public List<Personas> buscarPersonas();
    public void actualizarFotoPersona(BigInteger identificacion);
    public Personas buscarFotoPersona(BigInteger identificacion);
    public Personas buscarPersonaSecuencia(BigInteger secuencia);

    
}
