package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "index";
    }

    @GetMapping("/addDepartment")
    public String addDepartment(Model model){
        model.addAttribute("department", new Department());
        return "adddepartment";
    }

    @PostMapping("/processDepartment")
    public String processDepartment(@Valid Department department, BindingResult result){
        if(result.hasErrors()){
            return "adddepartment";
        }
        departmentRepository.save(department);
        return "redirect:/";
    }

    @RequestMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable("id") long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id));
        return "adddepartment";
    }

    @RequestMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id") long id){
        departmentRepository.deleteById(id);
        return "redirect:/allDepartments";
    }

    @RequestMapping("/allDepartments")
    public String allDepartments(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "alldepartments";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "addemployee";
    }

    @PostMapping("/processEmployee")
    public String processForm(@Valid Employee employee, BindingResult result, @RequestParam("file") MultipartFile file){
        if (result.hasErrors()){
            return "addemployee";
        }
        if(file.isEmpty() && (employee.getPhoto() == null || employee.getPhoto().isEmpty())){
            employee.setPhoto("https://res.cloudinary.com/dkim/image/upload/v1628737068/mlhelhawh1qprfhnzzk5.jpg");
        }
        else if(!file.isEmpty()){
            try{
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                employee.setPhoto(uploadResult.get("url").toString());
            } catch (IOException e){
                e.printStackTrace();
                return "redirect:/addEmployee";
            }
        }
        employeeRepository.save(employee);
        return "redirect:/allEmployees";
    }

    @RequestMapping("/viewEmployees/{id}")
    public String viewDepartmentEmployees(@PathVariable("id") long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "viewemployees";
    }

    @RequestMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "addemployee";
    }

    @RequestMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeRepository.deleteById(id);
        return "redirect:/allEmployees";
    }

    @RequestMapping("/allEmployees")
    public String allEmployees(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        return "allemployees";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }
}
