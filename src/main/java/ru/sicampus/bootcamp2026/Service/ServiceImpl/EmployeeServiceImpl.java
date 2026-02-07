package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetAuthorizedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.Employee.CreatedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeesResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.UpdateEmployeeResponse;
import ru.sicampus.bootcamp2026.Entity.Avatar;
import ru.sicampus.bootcamp2026.Entity.Contact;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Excepations.EmployeeFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.AvatarRepository;
import ru.sicampus.bootcamp2026.Repository.ContactRepository;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Service.EmployeeService;
import ru.sicampus.bootcamp2026.Service.TokenAuthService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private TokenAuthService tokenAuthService;
    @Override
    public GetEmployeeResponse getEmployee(GetEmployeeRequest dto){
        List<Employee>employee=employeeRepository.findByName(dto.getName());
        List<Employee> employee2 = employee.stream()
                .filter(e -> (dto.getLast_name() != null && dto.getLast_name().equals(e.getLast_name())) && (dto.getFather_name() != null && dto.getFather_name().equals(e.getFather_name()))
                )
                .toList();
        if(employee2.isEmpty()){
            throw new EmployeeNotFound("dfdg");
        }
        List<Map<String,Object>> employees=new ArrayList<>();
        for(Employee employee1: employee){
            Map<String,Object> er=new LinkedHashMap<>();
            er.put("name",employee1.getName());
            er.put("last_name",employee1.getLast_name());
            er.put("father_name",employee1.getFather_name());
            er.put("mail",employee1.getMail());
            er.put("avatar",employee1.getAvatar().getName());
            er.put("age",employee1.getAge());
            List<Map<String,String>> conts=new ArrayList<>();
            List<Contact> contact=contactRepository.findByEmployeeId(employee1.getId());
            for(Contact contact1:contact){
                Map<String,String> cont=new LinkedHashMap<>();
                cont.put(contact1.getContact(),contact1.getName());
                conts.add(cont);
            }
            er.put("contacts",conts);
            employees.add(er);
        }

        GetEmployeeResponse getEmployeeResponse=new GetEmployeeResponse();
        getEmployeeResponse.setEmployees(employees);
        return getEmployeeResponse;
    }
    @Override
    public GetEmployeesResponse getEmployees() {
        List<Employee> employee = employeeRepository.findAll();
        List<Map<String,Object>> employeeList=new ArrayList<>();
        for(Employee employee1 :employee){
            Map<String,Object> e=new LinkedHashMap<>();
            e.put("name",employee1.getName());
            e.put("last_name",employee1.getLast_name());
            e.put("father_name",employee1.getFather_name());
            e.put("age",employee1.getAge());
            e.put("avtar",employee1.getAvatar().getName());
            e.put("mail",employee1.getMail());
            List<Map<String, String>> contact1 = new ArrayList<>();
            List<Contact> contacts = contactRepository.findByEmployeeId(employee1.getId());
            for(Contact contact :contacts){
                Map<String,String> contactList=new LinkedHashMap<>();
                contactList.put("name",contact.getName());
                contactList.put("contact",contact.getContact());
                contact1.add(contactList);
            }
            e.put("contact",contact1);
            employeeList.add(e);
        }
        GetEmployeesResponse getEmployeesResponse = new GetEmployeesResponse();
        getEmployeesResponse.setEmployees(employeeList);
        return getEmployeesResponse;
    }
    @Override
    public CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto){
        if(employeeRepository.existsByMail(dto.getMail())){
            throw new EmployeeFound("");
        }
        Avatar avatar=avatarRepository.findById(dto.getAvatar());
        Employee employee=new Employee();
        employee.setName(dto.getName());
        employee.setLast_name(dto.getLast_name());
        employee.setFather_name(dto.getFather_name());
        employee.setMail(dto.getMail());
        employee.setAvatar(avatar);
        employee.setPassword(dto.getPassword());
        employeeRepository.save(employee);
        String token=tokenAuthService.createToken(dto.getMail());
        return new CreatedEmployeeResponse(token);
    }
    @Override
    public Boolean AuthorizedEmployee(GetAuthorizedEmployeeRequest dto){
        Employee employee=employeeRepository.findByMail(dto.getMail()).orElseThrow(()->new EmployeeNotFound(""));
        if(employee.getPassword().equals(dto.getPassword())) {
            return true;
        }
        else{
            throw new EmployeeNotFound("");
        }
    }
    @Override
    public UpdateEmployeeResponse updateEmployee(GetEmployeeUpdateRequest dto) {
        String token =SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        if (dto.getName() != null && !dto.getName().isBlank()) {
            employee.setName(dto.getName());
        }

        if (dto.getLast_name() != null && !dto.getLast_name().isBlank()) {
            employee.setLast_name(dto.getLast_name());
        }

        if (dto.getFather_name() != null && !dto.getFather_name().isBlank()) {
            employee.setFather_name(dto.getFather_name());
        }

        if (dto.getMail() != null && !dto.getMail().isBlank()) {
            employee.setMail(dto.getMail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            employee.setPassword(dto.getPassword());
        }

        if (dto.getAge() != null) {
            if (dto.getAge() < 18) try {
                throw new BadRequestException("Возраст < 18");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
            employee.setAge(dto.getAge());
        }

        if (dto.getAvatar() != null && !dto.getAvatar().isBlank()) {
            employee.setAvatar(avatarRepository.findByName(dto.getAvatar()));
        }

        employeeRepository.save(employee);
        String token1=tokenAuthService.createToken(dto.getMail());
        return new UpdateEmployeeResponse(token1);
    }

}
