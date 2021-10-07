package Datos;

/**
 *
 * @author user
 */
public class vcliente extends Persona{
    
    private String codigo_cliente;

    public vcliente() {
    }

    public vcliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }
    
    
    
}
