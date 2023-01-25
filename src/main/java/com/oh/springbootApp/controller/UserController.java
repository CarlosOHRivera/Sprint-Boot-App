package com.oh.springbootApp.controller;

import com.oh.springbootApp.Entity.User;
import com.oh.springbootApp.Repository.RoleRepository;
import com.oh.springbootApp.Service.UserService;
import com.oh.springbootApp.dto.ChangePasswordForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping({"userform", "userForm"})
    public String getUserForm(Model model) {
        //    public String getUserForm(Model model)
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";
	}
	
    @PostMapping("/userForm")

    public String postUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) /*throws Exception*/ {
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("formTab","active");
        } else {
            try {//Aca tendras error porque este metodo no existe, pero lo crearemos en la siguiente seccion.*/
                userService.createUser(user);
                model.addAttribute("userForm", new User());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage",e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab","active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles",roleRepository.findAll());
            }
        }
		
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("editMode", false);//Mira siguiente seccion para mas informacion
        return "user-form/user-view";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception {
        User userToEdit = userService.getUserById(id);
		
        model.addAttribute("userForm", userToEdit);
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("formTab","active");//Activa el tab del formulario.
        model.addAttribute("editMode","true");//Mira siguiente seccion para mas informacion
        model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
        return "user-form/user-view";
    }

	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
            try {
                userService.updateUser(user);
                model.addAttribute("userForm", new User());
                model.addAttribute("listTab", "active");
                model.addAttribute("editMode", "false");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab", "active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles", roleRepository.findAll());
                model.addAttribute("editMode", "true");
                model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
            }
        }

        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form/user-view";

    }

    @GetMapping("/userForm/cancel")
    public String cancelEditUser(ModelMap model) {
        return "redirect:/userForm";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model, @PathVariable(name="id")Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage",e.getMessage());
        }
        return "redirect:/userForm";  //Esto no funciono: getUserForm(model)


    }

    @PostMapping("/editUser/changePassword")
    public ResponseEntity postEditUseChangePassword(@RequestBody ChangePasswordForm form, Errors errors) {
        try {
            if( errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

                throw new Exception(result);
            }
            userService.changePassword(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
}


