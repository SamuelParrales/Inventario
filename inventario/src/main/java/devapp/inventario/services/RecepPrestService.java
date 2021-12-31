package devapp.inventario.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.DetRecepPrest;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.EstRecepPrest;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.EmpleadoRepository;
import devapp.inventario.repositories.ProductoRepository;
import devapp.inventario.repositories.RecepPrestRepository;

@Service
public class RecepPrestService {

    @Autowired
    RecepPrestRepository recepPrestRepo;

    @Autowired
    ProductoRepository productoRepo;

    @Autowired
    EmpleadoRepository empleadoRepo;

    @Autowired
    ClienteRepository clienteRepo;

    public ArrayList<RecepPrest> obtenerRecepPrest(){
        return (ArrayList<RecepPrest>)recepPrestRepo.findAll();
    }


    public RecepPrest savePrestacion(List<List<Integer>> productos,double valorPagado,int idEmpleado,int idCliente,
    Date fechaEntrega, String direccionEntrega, String garantia)
    {
        int est;    //Se guardará el estado: 5=Reservado;4=Cancelado;3=Despachado
        List<DetRecepPrest> detalles = new ArrayList<DetRecepPrest>(); //Para almacenar los detalles
		RecepPrest prestacion = new RecepPrest();   //Creacion de prestación
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        Cliente cliente = clienteRepo.findById(idCliente).get();
        

        //Creacion de los detalles de la prestacion
        double totalRecepPrest=0;
        for(List<Integer> produ: productos) 
        {
            int id = produ.get(0);
            int cantPrestada= produ.get(1);
            double totalDetalle;
            Producto producto = productoRepo.findById(id).get();

            if(!producto.prestar(cantPrestada))
                return null;

            totalDetalle= producto.getValorPrestacion()*cantPrestada;

            detalles.add(new DetRecepPrest(prestacion, producto, cantPrestada, totalDetalle));
            totalRecepPrest+=totalDetalle;
        }

        if(valorPagado<totalRecepPrest/2)   //Minimo el 50% para poder reservar
            return null;
                            
        if(valorPagado>totalRecepPrest) //El valor pagado no puede ser superior
            return null;

        
        if(valorPagado<totalRecepPrest)  //Determinar el estado del producto  
            est=5;      //Reservado
        else
        {
            if(idEmpleado==1)   //Empleado=1; Cliente reservó  desde casa
            {
                    est = 5;    //Reservado
                    garantia=null;
            }
            else
            {
                if(garantia==null)
                    est =5;   //Reservado
                else
                    est = 3; //Despachado
            }
                    
        }
            

                //Estado del producto
		EstRecepPrest estado = new EstRecepPrest(prestacion, est, empleado);

		prestacion.setCliente(cliente);
		prestacion.setValorPagado(valorPagado);
		prestacion.setTotalPrestacion(totalRecepPrest);
		prestacion.setDetalles(detalles);
        prestacion.setFechaEntrega(fechaEntrega);
		prestacion.setDireccionEntrega(direccionEntrega);
        prestacion.setGarantia(garantia);
        prestacion.prestacion(estado);
        
		
        return recepPrestRepo.save(prestacion);
    }

    public RecepPrest getById(long id){
        return recepPrestRepo.findById(id).get(); 
    }

    public boolean eliminarRecepPrest(long id){
        try {
            recepPrestRepo.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
}
