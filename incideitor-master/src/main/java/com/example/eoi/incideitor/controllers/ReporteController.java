package com.example.eoi.incideitor.controllers;



import com.example.eoi.incideitor.abstractcomponents.MiControladorGenerico;
import com.example.eoi.incideitor.entities.Reporte;
import com.example.eoi.incideitor.entities.Usuario;
import com.example.eoi.incideitor.repositories.ReporteRepository;
import jakarta.annotation.PostConstruct;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador para la entidad Reporte.
 *
 * <p>Esta clase se encarga de manejar las solicitudes relacionadas con la entidad Reporte utilizando la funcionalidad proporcionada por
 * la clase {@link MiControladorGenerico}.</p>
 *
 * <p>Los principales componentes de esta clase son:</p>
 * <ul>
 *     <li>Inversión de control (IoC): La clase extiende MiControladorGenerico y utiliza la funcionalidad proporcionada por ella.
 *     Esto permite que los detalles de implementación sean proporcionados por la clase genérica y facilita la reutilización de código
 *     y la consistencia en la implementación de controladores.</li>
 *     <li>Inyección de dependencias (DI): La clase utiliza inyección de dependencias para obtener el nombre de la entidad gestionada.
 *     El valor de entityName se inyecta utilizando la anotación @Value en la propiedad correspondiente. Esto permite la separación de
 *     responsabilidades y mejora la mantenibilidad y escalabilidad del controlador.</li>
 *     <li>Principio de abstracción: La clase extiende la clase genérica MiControladorGenerico, lo que permite una implementación
 *     común de las operaciones CRUD para la entidad Reporte. Esto facilita la reutilización de código y mejora la consistencia en
 *     la implementación de controladores.</li>
 * </ul>
 *
 * "@param <Reporte>" El tipo de entidad gestionada por el controlador.
 * "@Author Alejandro Teixeira Muñoz
 */
@Controller
@RequestMapping("${url.reporte}")
public class ReporteController extends MiControladorGenerico<Reporte> {

    @Value("${url.reporte}")
    private String url;

    private String entityName = "reporte";

    @Autowired
    ReporteRepository reporteRepository;

    /**
     * Constructor de la clase UsuarioController.
     * Se utiliza para crear una instancia del controlador.
     */
    public ReporteController() {
        super();
    }

    /**
     * Método de inicialización para establecer el valor de entityName y entityPrefix.
     * El valor de entityName se obtiene de una propiedad configurada en un archivo de propiedades utilizando la anotación @Value.
     * Después de la construcción del objeto UsuarioController, se llama a este método para establecer los valores necesarios.
     *
     * @PostConstruct indica que este método debe ejecutarse después de que se haya construido el objeto ReporteController.
     * Es una anotación de JSR-250 y se utiliza para realizar tareas de inicialización una vez que todas las dependencias hayan sido inyectadas.
     * En este caso, se utiliza para asegurar que entityName y entityPrefix se establezcan correctamente después de la construcción del objeto.
     * @Author Alejandro Teixeira Muñoz
     */
    @PostConstruct
    private void init() {
        super.entityName = entityName;
        super.url = url;
    }

    @GetMapping("/create")
    public String mostrarFormulario(Model model) {
        model.addAttribute("entity", new Reporte());
        model.addAttribute("entityName", entityName);
        model.addAttribute("nombreVista", "entity-details");
        return "index";
    }

    @PostMapping("/create")
    public String crearReporte(@ModelAttribute Reporte reporte) {
        service.create(reporte);
        return "redirect:/reporte/admin";
    }

    @Override
    @GetMapping("/admin")
    public String getAllAdmin(Model model) {
        List<Reporte> entities = reporteRepository.getReportesByCategoriaEquals("Error");
        model.addAttribute("entities", entities);
        model.addAttribute("entityName", entityName);
        model.addAttribute("nombreVista", "admin");
        return "index"; // Nombre de la plantilla para mostrar todas las entidades
    }

    @GetMapping("/bugs")
    public String getAllBugs(Model model) {
        List<Reporte> entities = reporteRepository.getReportesByCategoriaEquals("Bug");
        model.addAttribute("entities", entities);
        model.addAttribute("entityName", entityName);
        model.addAttribute("nombreVista", "admin");
        return "index"; // Nombre de la plantilla para mostrar todas las entidades
    }


}