package devapp.inventario.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import devapp.inventario.services.FileStorageException;
import devapp.inventario.services.PictureService;

@Controller
public class InventarioController {

    @Autowired
    PictureService pic;

    @GetMapping("/addimage")
	 public String ViewForm() {
	     return "addimage";
	 }

     @PostMapping("/add")
	 public String addImage(@RequestParam("file") MultipartFile file) {
	    UUID idPic = UUID.randomUUID(); //genera el uuid
	     try {
            pic.uploadPicture(file, idPic); //carga la imagen
            System.out.println("Imagen almcenada ya asignada un UUID");
        } catch (FileStorageException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return "addimage"; //redirecciona a addimage.html
	 }
}
