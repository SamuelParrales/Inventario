package devapp.inventario.controller;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import devapp.inventario.entities.Categoria;
import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.Proveedor;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.CategoriaRepository;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.EmpleadoRepository;
import devapp.inventario.repositories.ProductoRepository;
import devapp.inventario.repositories.ProveedorRepository;
import devapp.inventario.repositories.RecepPrestRepository;
import devapp.inventario.services.PictureService;
import devapp.inventario.services.RecepPrestService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    PictureService picture;
    @Autowired
    RecepPrestService recepPrestService;

    @Autowired
    RecepPrestRepository recepPrestRepo;

    @Autowired
    ProductoRepository produtoRepo;

    @Autowired
    ClienteRepository clienteRepo;

    @Autowired
    ProveedorRepository provedorRepo;

    @Autowired
    CategoriaRepository categoriaRepo;

    @Autowired
    EmpleadoRepository empleadoRepo;

    @GetMapping()
    public String perfil(Model model)
    {
        Empleado empleado;
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
			}
			String email = userDetails.getUsername();
			empleado = empleadoRepo.findByCorreo(email);
            
		}
		catch(Exception e)
		{
			return null;
		}
        model.addAttribute("usuario", empleado);
        return "perfil";
    }

    @GetMapping("/menu") 
    public String menu()
    {
        return "empleado/menu_empleado";
    }
    
    @GetMapping("/prestacion")
    public String prestacion()
    {
        return "empleado/prestacion";
    }

    @GetMapping("/reservacion/{id}")
    public String reservacion(@PathVariable("id") Long id, Model model)
    {
        RecepPrest reservacion = null;
        try
        {
            reservacion = recepPrestRepo.findById(id).get();
        }
        catch(Exception e)
        {

        }
        model.addAttribute("reservacion", reservacion);
        return "empleado/prestacion";
    }

    @GetMapping("/recepcion")
    public String recepcion(
        @RequestParam(required = false,defaultValue = "") String fechai,
        @RequestParam(required = false,defaultValue = "") String fechaf,
        @RequestParam(required = false,defaultValue = "") Long id,
        @RequestParam(required = false,defaultValue = "") String ci,
        Model model)
    {
        int pag =1;
        Pageable p = PageRequest.of(pag-1, 12);
        List<RecepPrest> prestaciones = new ArrayList<RecepPrest>();

        boolean consultado = false;
        try
        {
            
            if(id!=null)
            {
                
                if(ci==null||ci.equals(""))
                {
                    consultado=true;
                    RecepPrest recepPrest = recepPrestRepo.findById(id).get();
                    if(recepPrest.poseeElEstado(3)&&!recepPrest.poseeElEstado(0))
                        prestaciones.add(recepPrest);
                }
                else
                {
                    Cliente cliente = clienteRepo.findByCi(ci);
                    if(cliente!=null)
                    {
                        consultado=true;
                        RecepPrest recepPrest = recepPrestRepo.findByIdAndCliente(id, cliente);
                        if(recepPrest.poseeElEstado(3)&&!recepPrest.poseeElEstado(0))
                            prestaciones.add(recepPrest);
                    }
                }
            }
            else 
            {
                
                if((!ci.equals("")))
                {
                    
                    consultado = true;
                    Cliente cliente = clienteRepo.findByCi(ci);
                    prestaciones= recepPrestRepo.findPrestacionByIdCliente(cliente.getId(), p);
                
                }
    
                if(!fechai.equals("")||!fechaf.equals(""))
                {
                    consultado = true;
                    if(fechai.equals(""))
                        fechai = "2000-01-01";
        
                    if(fechaf.equals(""))
                    {
                        int diaS = LocalDate.now().getDayOfMonth()+1;
                        fechaf = LocalDate.now().withDayOfMonth(diaS).toString();
                    }
                        
                    prestaciones = recepPrestRepo.findPrestacionByFechas(fechai, fechaf,p);
                }
            }

 
        }
        catch(Exception e)
        {

        }

   
        if(!consultado)
            prestaciones = recepPrestRepo.findAllPrestacion(p);

        model.addAttribute("prestaciones", prestaciones);

        return "empleado/search_prestacion";
    }

    @GetMapping("/recepcion/{id}")
    public String recepcionar(@PathVariable("id") Long id, Model model)
    {
         
        model.addAttribute("prestacion", recepPrestService.getPrestacionById(id));
        return "empleado/recepcion";


    }

    @GetMapping("/recep_prest")
    public String reporteRecepPrest(
        @RequestParam(required = false,defaultValue = "") String fechai,
        @RequestParam(required = false,defaultValue = "") String fechaf,
        @RequestParam(required = false,defaultValue = "") Long id,
        @RequestParam(required = false,defaultValue = "") String ci,
        @RequestParam(required = false,defaultValue = "") Integer estado,
        Model model)
    {
        int pag =1;
        Pageable p = PageRequest.of(pag-1, 12);
        List<RecepPrest> recepPrests = new ArrayList<RecepPrest>();
        try
        {
            Boolean consultado = false;
            if(id!=null)
            {
                recepPrests.add(recepPrestRepo.findById(id).get());
                consultado = true;
            }


            if(!consultado &&!ci.equals(""))
            {
                Cliente cliente = clienteRepo.findByCi(ci);
                recepPrests= recepPrestRepo.findAllByClienteOrderByIdDesc(cliente, p);
                consultado = true;
            }

            if(!consultado&&estado!=null)
            {
                recepPrests= recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(estado, p);
                consultado = true;
            }

            if(!consultado &&(!fechai.equals("")||!fechaf.equals("")))
            {
        
                consultado=true;
                if(fechai.equals(""))
                    fechai = "2000-01-01";
    
                if(fechaf.equals(""))
                {
                    int diaS = LocalDate.now().getDayOfMonth()+1;
                    fechaf = LocalDate.now().withDayOfMonth(diaS).toString();
                }
                
                recepPrests = recepPrestRepo.findByFechas(fechai, fechaf, p);
            }

            if(!consultado)
            {
             
                recepPrests = recepPrestRepo.findAllByEstados_EstadoOrderByIdDesc(5,p);
            }
        }
        catch(Exception e)
        {

        }
 
        model.addAttribute("recePrests", recepPrests);
        return "empleado/search_recepprest";

    }
    @GetMapping("/recep_prest/{id}")
    public String reporteRecepPrest(@PathVariable("id") Long id, Model model)
    {
        
        model.addAttribute("reservacion", recepPrestRepo.findById(id).get());
        return "factura_recep_prest";
    }

    // Productos
    @GetMapping("/producto/add")
    public String registroProducto(Model model)
    {

        model.addAttribute("proveedores", provedorRepo.findAll());
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "empleado/registrar_productos";
    }

    @PostMapping("/producto")
    public String registrarProducto(Model model,
    @RequestParam String nombre,
    @RequestParam(defaultValue = "") String descripcion,
    @RequestParam Double valoru,
    @RequestParam Double valorp,
    @RequestParam Integer cantd,
    @RequestParam Integer categoria,
    @RequestParam(required = false,defaultValue = "") Integer[] proveedores,
    @RequestParam MultipartFile file)
    {
        
  
        //Busqueda de los proveedores
        List<Proveedor> listProveedores = new ArrayList<Proveedor>();
        int loops = proveedores.length;
        for(int i=0;i<loops;i++)
        {
            Proveedor proveedor = provedorRepo.findById(proveedores[i]).get();
            listProveedores.add(proveedor);
        }
        //Busqueda de la categoria
        Categoria cate = categoriaRepo.findById(categoria).get();


        //Generación del producto
        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setValorUnitario(valoru);
        producto.setValorPrestacion(valorp);
        producto.setCantDisponible(cantd);
        producto.setCategoria(cate);
        producto.setEstado(1);
        producto.setProveedores(listProveedores);
        //Guardado de la imagen
        UUID idPic = UUID.randomUUID();
        picture.uploadPicture(file, idPic);
        producto.setImg(idPic);
        produtoRepo.save(producto);
        return "redirect:/empleado/producto";
    }

    //Para el reporte de los productos
    @GetMapping("/producto")
    public String reporteProducto(
        @RequestParam(required = false, defaultValue = "") String nombre,
        @RequestParam(required = false, defaultValue = "") Integer proveedor,
        @RequestParam(required = false, defaultValue = "") Integer categoria,
        @RequestParam(required = false,defaultValue = "1") Integer pag,
        Model model)
    {
        int tamPag = 10;
        Long cantInstancias=(long) 0;
        Pageable p = PageRequest.of(pag-1, tamPag);
        List<Producto> productos =null;
        Boolean consultado = false;
        
        if(!nombre.equals(""))
        {
            
            if(proveedor!=null&&categoria!=null)
            {
         
                productos = produtoRepo.filterByNombreFindCategoria_IdAndProveedor_Id(nombre, categoria, proveedor, p);
                cantInstancias = produtoRepo.countFilterByNombreFindCategoria_IdAndProveedor_Id(nombre,categoria,proveedor);
                consultado = true;
            }

            if(!consultado &&categoria!=null)
            {
                productos = produtoRepo.filterByNombreFindCategoria_Id(nombre, categoria, p);
                cantInstancias = produtoRepo.countFilterByNombreFindCategoria_Id(nombre, categoria);
                consultado = true;
            }

            if(!consultado && proveedor!=null) 
            {
                productos = produtoRepo.filterByNombreFindProveedor_Id(nombre, proveedor, p);
                cantInstancias = produtoRepo.countFilterByNombreFindProveedor_Id(nombre, proveedor);       
                consultado = true;
            }

            if(!consultado)
            {
                productos = produtoRepo.filterByNombre(nombre, p);
                cantInstancias = produtoRepo.countFilterByNombre(nombre);
                consultado = true;
            }

            if(consultado)
                model.addAttribute("productos", productos);
        }

        if(!consultado)
        {
            
            if(proveedor!=null&&categoria!=null)
            {
                Categoria cate = categoriaRepo.findById(categoria).get();
                Proveedor prov = provedorRepo.findByIdAndEstado(proveedor, 1);
                productos = produtoRepo.findAllByCategoriaAndProveedoresAndEstado(cate, prov, 1, p);
                cantInstancias = produtoRepo.countByEstadoAndCategoriaAndProveedores(1, cate, prov);
                consultado = true;
            }

            if(!consultado && proveedor!=null)
            {
                Proveedor prov = provedorRepo.findByIdAndEstado(proveedor, 1);
                productos = produtoRepo.findAllByProveedoresAndEstado(prov, 1, p);
                cantInstancias = produtoRepo.countByEstadoAndProveedores(1, prov);
                consultado = true;
            }
            if(!consultado&&categoria!=null)
            {
                Categoria cate = categoriaRepo.findById(categoria).get();
                productos = produtoRepo.findAllByCategoriaAndEstado(cate, 1, p);
                cantInstancias = produtoRepo.countByEstadoAndCategoria(1, cate);
                consultado = true;
            }

            if(consultado)
                model.addAttribute("productos", productos);
        }
        if(!consultado)
        {
            model.addAttribute("productos",produtoRepo.findAllByEstado(1, p));
            cantInstancias = produtoRepo.countByEstado(1);
        }

        //Cantidad de paginas
        double cantPagina = ((double) cantInstancias/(double) tamPag);
		int cantPaginaRex = (int) Math.ceil(cantPagina);

        //Cantidad de paginas
		if(cantPaginaRex<cantPagina)
            model.addAttribute("cantPag", cantPaginaRex+1);
        else    
            model.addAttribute("cantPag", cantPaginaRex);

        model.addAttribute("pageActive", pag);
        model.addAttribute("proveedores", provedorRepo.findAll());
        model.addAttribute("categorias", categoriaRepo.findAll());

        return "empleado/search_productos";
    }

 
    @GetMapping("/producto/{id}")
    public String getProducto(Model model, @PathVariable("id") Integer id )
    {

        model.addAttribute("proveedores", provedorRepo.findAll());
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("producto", produtoRepo.findById(id).get());
        return "empleado/registrar_productos"; 
    }

    @PostMapping("/producto/{id}")
    String updateProducto(Model model,
    @PathVariable("id") Integer id,
    @RequestParam String nombre,
    @RequestParam(defaultValue = "") String descripcion,
    @RequestParam Double valoru,
    @RequestParam Double valorp,
    @RequestParam Integer cantd,
    @RequestParam Integer categoria,
    @RequestParam(required = false,defaultValue = "") Integer[] proveedores,
    @RequestParam (required = false,defaultValue = "") MultipartFile file)
    {
        //Busqueda el producto a actualizar
        Producto producto = produtoRepo.findById(id).get();

        
                //Busqueda de los proveedores
        List<Proveedor> listProveedores = new ArrayList<Proveedor>();
        int loops = proveedores.length;
        for(int i=0;i<loops;i++)
        {
            Proveedor proveedor = provedorRepo.findById(proveedores[i]).get();
            listProveedores.add(proveedor);
        }

        //Busqueda de la categoria
        Categoria cate = categoriaRepo.findById(categoria).get();

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setValorUnitario(valoru);
        producto.setValorPrestacion(valorp);
        producto.setCantDisponible(cantd);
        producto.setCategoria(cate);
        producto.setEstado(1);
        producto.setProveedores(listProveedores);
        //Guardado de la imagen
        if(!file.isEmpty())
        {
            System.out.println("aaaaaaaaa");
            
            UUID idPic = UUID.randomUUID();
            picture.uploadPicture(file, idPic);
            producto.setImg(idPic);
        }
  
        produtoRepo.save(producto);
        return "redirect:/empleado/producto";
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Integer> deleteProducto(@PathVariable("id") Integer id)
    {
        Producto producto = produtoRepo.findById(id).get();
        producto.setEstado(0);
        produtoRepo.save(producto);
        return new ResponseEntity<>(id, HttpStatus.OK);
        
    }
}
