package devapp.inventario.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.EstRecepPrest;
import devapp.inventario.entities.RecepPrest;


@DataJpaTest
public class RecepPrestRepositoryTest {
    @Autowired
    private RecepPrestRepository recepPrestRepo;


    @Autowired 
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Test
    public void testCreateRecerPrest()
    {
        		//Creando prestacion
        RecepPrest recepPrest = new RecepPrest();
		recepPrest = recepPrestRepo.save(recepPrest);

        assertNotNull(recepPrest);
        assertTrue(recepPrest.getId()>0);
    }

    @Test
    public void testFindIdAndCliente()
    {   
        Cliente cliente = clienteRepo.findById(1).get();
        RecepPrest recepPrest = new RecepPrest();
		recepPrest.setCliente(cliente);
        recepPrest = recepPrestRepo.save(recepPrest);

        long id = recepPrest.getId();
        recepPrest = recepPrestRepo.findByIdAndCliente(id, cliente);

        assertNotNull(recepPrest);
        assertTrue(recepPrest.getId()>0);
    }

 
    @Test
    public void testFindAllByClienteOrderByIdDesc()
    {
        Cliente cliente = clienteRepo.findById(1).get();
        RecepPrest recepPrest = new RecepPrest();
        recepPrest.setCliente(cliente);
        recepPrest= recepPrestRepo.save(recepPrest);//Primera
        
        RecepPrest recepPrest2 = new RecepPrest(); //Segunda
        recepPrest2.setCliente(cliente);
        recepPrest = recepPrestRepo.save(recepPrest2);

        List<RecepPrest> recepPrests = recepPrestRepo.findAllByClienteOrderByIdDesc(cliente);
        assertNotNull(recepPrests);
        assertTrue(recepPrests.size()==2);
        assertTrue(recepPrest2==recepPrests.get(0));
    }
 
    @Test
    public void testFindAllByEstados_EstadoOrderByIdDesc()
    {
        List<EstRecepPrest> estados1 = new ArrayList<EstRecepPrest>(); //recepPrest 1
        Empleado empleado = empleadoRepo.findById(1).get();
        RecepPrest recepPrest1 = new RecepPrest();

        estados1.add(new EstRecepPrest(recepPrest1, 5, empleado));
        estados1.add(new EstRecepPrest(recepPrest1, 4, empleado));
        recepPrest1.setEstados(estados1);
        recepPrestRepo.save(recepPrest1);
                                                                        //recepPrest 2             
        RecepPrest recepPrest2 = new RecepPrest();                                                                                    
        List<EstRecepPrest> estados2  = new ArrayList<EstRecepPrest>();
        estados2.add(new EstRecepPrest(recepPrest2, 5, empleado));
        recepPrest2.setEstados(estados2);
        recepPrestRepo.save(recepPrest2);
        
        
        
        List<RecepPrest> recepPrests5 = recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(5);
        System.out.println(recepPrests5.size());
        assertNotNull(recepPrests5);
        assertTrue(recepPrests5.size()==2);
        List<RecepPrest> recepPrests4 = recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(4);
        
        assertNotNull(recepPrests4 );
        assertTrue(recepPrests4.size()==1);
        assertTrue(recepPrest2==recepPrests5.get(0));
    }

    @Test
    public void testFindAllByEstados_EmpleadoOrderByIdDesc()
    {
        //recepPrest 1
        List<EstRecepPrest> estados1 = new ArrayList<EstRecepPrest>(); 
        Empleado empleado = empleadoRepo.findById(1).get();
        RecepPrest recepPrest1 = new RecepPrest();

        estados1.add(new EstRecepPrest(recepPrest1, 5, empleado));
        estados1.add(new EstRecepPrest(recepPrest1, 4, empleado));
        recepPrest1.setEstados(estados1);
        recepPrest1= recepPrestRepo.save(recepPrest1);
        
    //recepPrest 2
        RecepPrest recepPrest2 = new RecepPrest();                                                                                    
        List<EstRecepPrest> estados2  = new ArrayList<EstRecepPrest>();
        estados2.add(new EstRecepPrest(recepPrest2, 5, empleado));
        recepPrest2.setEstados(estados2);
        recepPrest2 = recepPrestRepo.save(recepPrest2);


        Set<RecepPrest> recepPrests  = recepPrestRepo.findAllByEstados_EmpleadoOrderByIdDesc(empleado);

        List<RecepPrest> listRecepPrests = new ArrayList<RecepPrest>(recepPrests);

        assertNotNull(recepPrests );
        assertTrue(recepPrests.size()==2);

        assertTrue(recepPrest2==listRecepPrests.get(0));
    }

}
