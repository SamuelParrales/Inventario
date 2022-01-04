package devapp.inventario.services;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.dto.ProductoPresDto;
import devapp.inventario.dto.ReservacionClientDto;
import devapp.inventario.dto.PrestacionEmplDto;
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

    final String valVacio ="Sin especificar" ;
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

    public RecepPrest getById(long id){
        return recepPrestRepo.findById(id).get(); 
    }
                        //Para generar un reservacion por parte del cliente
    public RecepPrest saveReservacionCliente(ReservacionClientDto reservacionDto,int idCliente)
    {
        int est = 5; //5:Reservacion
        String direccionEntrega = reservacionDto.getDirEntrega();
        int idEmpleado = 1;//El empleado 1 es el sistema
        double valorPagado = reservacionDto.getValorPagado();
        double totalprestacion;
        RecepPrest reservacion = addDetalles(reservacionDto.getProductos(),null);
        Date fechaCaducida;
        if(reservacion==null)
            return null;

        totalprestacion = reservacion.getTotalPrestacion();
        if(valorPagado<totalprestacion/2||valorPagado>totalprestacion) //Debe pagar al menos el 50% y no debe ser mayor al total
            return null; 
        
        if(direccionEntrega==null)
        direccionEntrega = valVacio;

        fechaCaducida = expiresFrom(3); //La reservacion expira en 3 dias
        
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        Cliente cliente = clienteRepo.findById(idCliente).get();
            
        EstRecepPrest estado = new EstRecepPrest(reservacion, est, empleado);
    
        reservacion.setCliente(cliente);
        reservacion.setValorPagado(valorPagado);
        reservacion.setFechaCaducida(fechaCaducida);
        reservacion.setDireccionEntrega(direccionEntrega);
        reservacion.setGarantia(valVacio);
        reservacion.prestacion(estado);

        return recepPrestRepo.save(reservacion);
    }

                        //Para generar una Prestacion y/o reservacion por parte de un empleado
    public RecepPrest savePrestacion(PrestacionEmplDto prestacionDTO,int idEmpleado)
    {
        int est;
        double valorPagado = prestacionDTO.getValorPagado();
        int idCliente = prestacionDTO.getIdCliente();
        String direccionEntrega = prestacionDTO.getDirEntrega();
        String garantia = prestacionDTO.getGarantia();
        Long fechaC = prestacionDTO.getFechaCaducida();
        Date fechaCaducida;
        Empleado empleado;
        Cliente cliente;
        try
        {
            empleado = empleadoRepo.findById(idEmpleado).get();
            cliente = clienteRepo.findById(idCliente).get();
        }
        catch(Exception e)
        {
            return null;
        }

        if(idEmpleado==1)   //El sistema no puede realizar prestacion
            return null;
        
        
        
        RecepPrest prestacion = addDetalles(prestacionDTO.getProductos(),null); //Genera la reservacion con los detalles dentro
        if(prestacion==null)
            return null;

        
        double totalprestacion = prestacion.getTotalPrestacion();

        if(valorPagado<totalprestacion/2||valorPagado>totalprestacion) //Debe pagar al menos el 50% y no debe ser mayor al total
            return null;

            
        if(prestacionDTO.isReservacion())
            est = 5;    //5: Reservacion
        else
        {
            if(idEmpleado==1)//idEmpleado=1; es el sistema
                est = 5;   //Reservacion 
            else
                est = 3;    //3: Despacho
        }
           
            //Calculo de la fecha en que caduca la prestacion
        if(fechaC==null||idEmpleado==1) //idEmpleado=1; es el sistema
            fechaCaducida = expiresFrom(3); //Caduca en 3 dias a partir del momento en que se crea
        else
            fechaCaducida = new Date(fechaC);  

        if(direccionEntrega==null)
            direccionEntrega = valVacio;
        
        if(garantia==null)
        {
            garantia =valVacio;
            est = 5; //5: Reservacion
        }

  
        //Obtencion del empleado y cliente

        
        EstRecepPrest estado = new EstRecepPrest(prestacion, est, empleado);
        
		prestacion.setCliente(cliente);
		prestacion.setValorPagado(valorPagado);
        prestacion.setFechaCaducida(fechaCaducida);
		prestacion.setDireccionEntrega(direccionEntrega);
        prestacion.setGarantia(garantia);
        prestacion.prestacion(estado);
        
        return recepPrestRepo.save(prestacion);
    }


    //***********************************************Metodos para cambiar el estado
    
    //Metodo para cancelar la reservacion
    
    public RecepPrest cancelReservacionClient(long idRecepPrest, int idCliente)
    {
        int idEmpleado=1;//1:Sistema
        RecepPrest reservacion;
        Empleado empleado;
        int newEstado =4;//4: Cancelado
        Cliente cliente;
        try
        {
            empleado = empleadoRepo.findById(idEmpleado).get();
            cliente = clienteRepo.findById(idCliente).get();
            reservacion = recepPrestRepo.findByIdAndCliente(idRecepPrest, cliente);
            
        }
        catch(Exception e)
        {
            return null;
        }
        int lastEstado = reservacion.estadoActual();
        if(lastEstado==5)
        {
            EstRecepPrest estado = new EstRecepPrest(reservacion, newEstado, empleado);
            reservacion.cambiarEstado(estado);
            devolverProductos(reservacion);
        }
        else
            return null;


        return recepPrestRepo.save(reservacion);
    }
    
    public RecepPrest cancelReservacionEmpl(long idRecepPrest, int idEmpleado)
    {
        if(idEmpleado==1)
            return null;
        int newEstado =4;//4: Cancelado
        
        RecepPrest reservacion;
        Empleado empleado;
        
        try
        {
            reservacion = recepPrestRepo.findById(idRecepPrest).get();
            empleado = empleadoRepo.findById(idEmpleado).get();
        }
        catch(Exception e)
        {
            return null;
        }
        int lastEstado = reservacion.estadoActual();

        if(lastEstado==5)
        {
            EstRecepPrest estado = new EstRecepPrest(reservacion, newEstado, empleado);
            reservacion.cambiarEstado(estado);
            devolverProductos(reservacion);
        }
        else
            return null;


        return recepPrestRepo.save(reservacion);
        //Para estado
        // 5: Reservado (Debe cancelar el 50% como minimo)
        // 4: Cancelado
    }

            //Metodo que puede despachar una reservacion o añadir nuevos productos a la reservacion
    public RecepPrest actualizarReservacionEmpl(PrestacionEmplDto prestacionDto,long idRecepPrest,int idEmpleado)
    {
        int idCliente = prestacionDto.getIdCliente();
        boolean continuaReservacion =prestacionDto.isReservacion();
        if(idEmpleado==1) //El empleado 1 es el sistema y no puede realizar despachos
            return null;
        
        RecepPrest reservacion;
        Empleado empleado;
        Cliente cliente;
        try
        {
            reservacion = recepPrestRepo.findById(idRecepPrest).get();
            empleado = empleadoRepo.findById(idEmpleado).get();
            cliente = clienteRepo.findById(idCliente).get();
        }
        catch(Exception e)
        {
            return null;
        }

        int lastEstado = reservacion.estadoActual();
        //Boolean continuaReservacion = prestacionDto.isReservacion();

        
        if(lastEstado!=5)
           return null;
        
        double valorPagado = prestacionDto.getValorPagado();
                //Actualizar datos de la reservacion
                //Actualizar detalles
        if(!updateDetalles(prestacionDto.getProductos(), reservacion,valorPagado))
        {
            return null;
        }
       
        
 
            //Actualizar datso generales
         
        String direccionEntrega = prestacionDto.getDirEntrega();  
        
        String garantia = prestacionDto.getGarantia();

      
        
        if(direccionEntrega==null)
            direccionEntrega = valVacio;
    
        if(garantia==null)
            garantia =valVacio;
        
        reservacion.setCliente(cliente);
		reservacion.setValorPagado(valorPagado);
 
        
		reservacion.setDireccionEntrega(direccionEntrega);
        reservacion.setGarantia(garantia);
        
        if(prestacionDto.getFechaCaducida()!=null)
        {
            Date fechaCaducida = new Date(prestacionDto.getFechaCaducida());
            reservacion.setFechaCaducida(fechaCaducida);
        }

        if(!continuaReservacion)
        {
            EstRecepPrest estado = new EstRecepPrest(reservacion, 3, empleado); //3: Prestado
            if(garantia!=valVacio)
                reservacion.cambiarEstado(estado);
            
        }


        
        return recepPrestRepo.save(reservacion);

                //Para estado
        // 5: Reservado
        // 4: Cancelado
        // 3: Despachado (Debe cancelar el 100%)
        // 2: Vencido (Por si se pasa de la fecha)
        // 1: Compensando (para compensar las perdidas)
        // 0: Recepcionado
    } 

    public RecepPrest actualizarReservacionClient(ReservacionClientDto reservacionDto,long idRecepPrest,int idCliente)
    {

        
        RecepPrest reservacion;
        Cliente cliente;      

        try
        {
            cliente = clienteRepo.findById(idCliente).get();
            reservacion = recepPrestRepo.findByIdAndCliente(idRecepPrest, cliente);
        }
        catch(Exception e)
        {
            return null;
        }

        
        double valorPagado = reservacionDto.getValorPagado();
        List<DetRecepPrest> detalles = reservacion.getDetalles();

    
        
        
        if(!sePuedeActualizar(reservacionDto.getProductos(), detalles, valorPagado))
            return null;
        
        if(!updateDetalles(reservacionDto.getProductos(), reservacion, valorPagado))
            return null;
        String direccionEntrega = reservacionDto.getDirEntrega();
        if(direccionEntrega==null)
            direccionEntrega = valVacio;
    
        reservacion.setValorPagado(valorPagado);
        reservacion.setDireccionEntrega(direccionEntrega);
        reservacion.setGarantia(valVacio);

        return recepPrestRepo.save(reservacion);
    }
    
    public RecepPrest recepcionar()
    {

        return null;
    }
    //***********************************************Metodos auxiliares
    private void devolverProductos(RecepPrest recepPrest)
    {
        List<DetRecepPrest> detalles = recepPrest.getDetalles();
        
        for(DetRecepPrest detalle: detalles)
        {
            int cantDevuelta = detalle.getCantidadPrestada() - detalle.getCantidadPerdida();
            detalle.getProducto().devolver(cantDevuelta);   
        }
    }

    private RecepPrest addDetalles(List<ProductoPresDto> productosDto,RecepPrest recepPrest)
    {
        if(productosDto.size()<1)
            return null;

        if(!sePuedePrestar(productosDto))
            return null;
        double totalRecepPrest=0;
        List<DetRecepPrest> detalles = new ArrayList<DetRecepPrest>();
        boolean existenDetalles=true;
        if(recepPrest==null)
        {
            recepPrest = new RecepPrest();
            existenDetalles=false;
        }
            

        for(ProductoPresDto produ: productosDto) 
        {
            int id = produ.getId();
            int cantPrestada= produ.getCantPrestada();
            double totalDetalle;
            Producto producto = productoRepo.findById(id).get();

            producto.prestar(cantPrestada);

            totalDetalle= producto.getValorPrestacion()*cantPrestada;

            detalles.add(new DetRecepPrest(recepPrest, producto, cantPrestada, totalDetalle));
            totalRecepPrest+=totalDetalle;
        }
        if(existenDetalles)
            recepPrest.getDetalles().addAll(detalles);
        else    
            recepPrest.setDetalles(detalles);
        
        recepPrest.setTotalPrestacion(totalRecepPrest);
        return recepPrest;
    }

    private Boolean sePuedePrestar(List<ProductoPresDto> productosDto)
    {
        for(ProductoPresDto produ: productosDto)
        {
            int id = produ.getId();
            int cantPrestada= produ.getCantPrestada();
            Producto producto = productoRepo.findById(id).get();

            if(producto.getCantDisponible()<cantPrestada)
                return false;
        }

        return true;
    }

    private boolean updateDetalles(List<ProductoPresDto> productosDto,RecepPrest recepPrest,double valorPagado)
    {
        
        if(productosDto.size()<1)
            return false;
        
        if(recepPrest.getTotalPrestacion()<=0)
            return false;

        if(recepPrest.getDetalles()==null)
            return false;

        if(!sePuedeActualizar(productosDto, recepPrest.getDetalles(),valorPagado))
            return false;

        
        List<DetRecepPrest> detalles = recepPrest.getDetalles();
            //Se devuelven los productos 
        for (DetRecepPrest detalle:detalles)
        {
            int cantPrestDet = detalle.getCantidadPrestada();
            detalle.getProducto().devolver(cantPrestDet);
        }    
        
        //Se borran todos los detalles
        detalles.removeAll(detalles);
     
        //Se colocan los nuevos cambios
        
        addDetalles(productosDto,recepPrest);
        return true;

    }

    private Boolean sePuedeActualizar(List<ProductoPresDto> productosDto,List<DetRecepPrest> detalles,double valorPagado)
    {
        double totalprestacion=0;
        for(ProductoPresDto produ: productosDto)
        {
            int id = produ.getId();
            int cantPrestada= produ.getCantPrestada();
            Producto producto = productoRepo.findById(id).get();
            Boolean seEncuentra = false;
            
            for(DetRecepPrest detalle: detalles)
            {
                if(detalle.getProducto()==producto)
                {
                    int cantPrestDet =  detalle.getCantidadPrestada();
                    int cantDisponible = cantPrestDet + producto.getCantDisponible();
                     
                    if(cantDisponible<cantPrestada)
                        return false;
                    
                    totalprestacion += cantPrestada*producto.getValorPrestacion();
                    
                    seEncuentra=true;
                    break;
                }
            }

            if(seEncuentra)
                continue;

            if(producto.getCantDisponible()<cantPrestada)
                return false;
            
            totalprestacion+= cantPrestada*producto.getValorPrestacion();
        }
     
        if(valorPagado<totalprestacion/2||valorPagado>totalprestacion) //Debe pagar al menos el 50% y no debe ser mayor al total
            return false;
        
        return true;
    }

    private Date expiresFrom(int dias)
    {
        Date fecha = new Date();
        if (dias==0) 
            return fecha;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); 
        calendar.add(Calendar.DAY_OF_YEAR, dias);  
        
        return calendar.getTime(); 
    }


    
}
