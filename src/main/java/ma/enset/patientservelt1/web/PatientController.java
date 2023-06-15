package ma.enset.patientservelt1.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.patientservelt1.entities.Patient;
import ma.enset.patientservelt1.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import java.util.List;
/*
statefull: données de la session sont enregistrés côté serveur authentification
stateless: dans un jeton authentification delivre au client
Une session est  un object créer dans la mémoire de serveur
Le serveur va stocker des informations relative au client comme username, session id dans la session
Une session est stocké dans une liste des sessions de serveur, chaque session a un session id unique qui va être envoyer dans
la réponse http sous forme dun  header qui sappelle setcookie
Le browser quand il recoit session id il va le stocker dans les cookies
Les cookies sont des fichiers temporaires qui sont stockés cote navigateur et dans le quel le browser va stocke les informations relative a unclient
*/

@Controller
//injection vers constructeur
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    @PreAuthorize("hasRole('user')")
    //name=page =>param qui s'appelle page affecter le a variable int page
    //on va stocker la liste des patient dans le Model
    public String index(Model model, @RequestParam(name= "page", defaultValue = "0") int page
            ,@RequestParam(name = "size", defaultValue = "4") int size,
    @RequestParam(name="keyword", defaultValue = "") String keyword
    )
    {
       //pagination
        Page<Patient> patientPage=patientRepository.findByNameContains(keyword, PageRequest.of(page,size));
        //Page<Patient> patientPage=patientRepository.findAll(PageRequest.of(page,size));
        // List<Patient> patients=patientRepository.findAll();
       // model.addAttribute("listPatients",patients);
        model.addAttribute("listPatients",patientPage.getContent());
        //pages contient le nombre de page
        model.addAttribute("pages",new int [patientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",keyword);
        //return vue html
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('admin')")

    public String delete(Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return  "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

   @GetMapping("/")
   @PreAuthorize("hasRole('user')")
   public String home(){
        return "redirect:/user/index";
   }

    @GetMapping("/admin/PatientForm")
    @PreAuthorize("hasRole('admin')")

    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "PatientForm";
    }

    //@valid va valider les donnees saisie si il trouve des errors il va les stockes dans bindingresults
    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('admin')")

    public String save(@Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "PatientForm";

        }
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+patient.getName();
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('admin')")

    public String edit(Model model, @RequestParam(name = "id") Long id){
        Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return  "editPatient";
    }

}
