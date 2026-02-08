package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.*;
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
import ru.sicampus.bootcamp2026.Service.AvatarService;
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
    @Autowired
    private AvatarService avatarService;
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
    public GetEmployeesResponse getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Employee> employees = employeeRepository.findAll(pageable);
        List<Map<String,Object>> employeeList = new ArrayList<>();
        for (Employee employee1 : employees.getContent()) {
            Map<String,Object> e = new LinkedHashMap<>();
            e.put("name", employee1.getName());
            e.put("last_name", employee1.getLast_name());
            e.put("father_name", employee1.getFather_name());
            e.put("age", employee1.getAge());
            e.put("avatar", employee1.getAvatar().getName());
            e.put("mail", employee1.getMail());
            List<Map<String,String>> contactsList = new ArrayList<>();
            for (Contact contact : contactRepository.findByEmployeeId(employee1.getId())) {
                Map<String,String> c = new LinkedHashMap<>();
                c.put("name", contact.getName());
                c.put("contact", contact.getContact());
                contactsList.add(c);
            }
            e.put("contact", contactsList);
            employeeList.add(e);
        }
        GetEmployeesResponse response = new GetEmployeesResponse();
        response.setEmployees(employeeList);
        return response;
    }
    @Override
    public CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto){
        if(!Objects.equals(dto.getCode(), 1234)){
            throw new IllegalArgumentException("");
        }
        if(employeeRepository.existsByMail(dto.getMail())){
            throw new EmployeeFound("");
        }

        if (dto.getAvatar() == null || dto.getAvatar().isBlank()) {
            throw new IllegalArgumentException("Avatar is required");
        }

        Avatar avatar = avatarRepository
                .findByName(dto.getAvatar()).orElseGet(() -> avatarRepository.save(
                        new Avatar(dto.getAvatar())
                ));
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
        if(dto.getAvatar()!=null&dto.getAvatar().isBlank()){
                Avatar avatar=avatarRepository.findByName(dto.getAvatar()).orElseGet(()->avatarRepository.save(new Avatar(dto.getAvatar())));
                employee.setAvatar(avatar);
        }
        employeeRepository.save(employee);
        String token1=tokenAuthService.createToken(dto.getMail());
        return new UpdateEmployeeResponse(token1);
    }
    @Override
    public GetYouResponse getYou(){
        String token=SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        List<Contact> contacts=contactRepository.findByEmployeeId(employee.getId());
        List<Map<String,String>> contactList=new ArrayList<>();
        for(Contact contact:contacts){
            Map<String,String> cont=new LinkedHashMap<>();
            cont.put(contact.getContact(),contact.getName());
            contactList.add(cont);
        }
        GetYouResponse getYouResponse=new GetYouResponse(employee.getName(),employee.getLast_name(),employee.getFather_name(),employee.getMail(),employee.getPassword(),employee.getAvatar().getName(),employee.getAge(),contactList);
        return getYouResponse;
    }

}
