package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetAuthorizedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetEmployeeUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.CreatedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.GetEmployeeResponse;
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

import java.util.ArrayList;
import java.util.List;

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
        Employee employee=employeeRepository.findByName(dto.getName()).orElseThrow(()->new EmployeeNotFound(""));
        List<Contact> contact=contactRepository.findByEmployeeId(employee.getId());
        List<String> contacts = contact.stream()
                .map(Contact::getName)
                .toList();
        String avatar=employee.getAvatar().getName();
        GetEmployeeResponse getEmployeeResponse= new GetEmployeeResponse(employee.getName(),employee.getLast_name(),employee.getFather_name(),employee.getMail(),contacts,avatar);
        return getEmployeeResponse;
    }
    @Override
    public List<GetEmployeeResponse> getEmployees(){
        List<Employee> employee=employeeRepository.findAll();
        List<GetEmployeeResponse> getEmployeeResponses=new ArrayList<>();
        for(Employee employee1:employee){
            List<Contact> contact=contactRepository.findByEmployeeId(employee1.getId());
            List<String> contacts = contact.stream()
                    .map(Contact::getName)
                    .toList();
            String avatar=employee1.getAvatar().getName();
            GetEmployeeResponse getEmployeeResponse=new GetEmployeeResponse(employee1.getName(),employee1.getLast_name(), employee1.getFather_name(), employee1.getMail(),contacts,avatar);
            getEmployeeResponses.add(getEmployeeResponse);
        }
        return getEmployeeResponses;
    }
    @Override
    public CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto){
        if(employeeRepository.existsByMail(dto.getMail())){
            throw new EmployeeFound("");
        }
        Avatar avatar=avatarRepository.findById(dto.getAvatar());
        Employee employee=new Employee(dto.getName(),dto.getLast_name(),dto.getFather_name(),dto.getMail(),avatar,dto.getAge(),dto.getPassword());
        employeeRepository.save(employee);
        String token=tokenAuthService.createToken(dto.getMail());
        return new CreatedEmployeeResponse(token);
    }
    @Override
    public String AuthorizedEmployee(GetAuthorizedEmployeeRequest dto){
        Employee employee=employeeRepository.findByMail(dto.getMail()).orElseThrow(()->new EmployeeNotFound(""));
        if(employee.getPassword().equals(dto.getPassword())) {
            throw new EmployeeNotFound("");
        }
        else{
           return "добро пожаловать";
        }
    }
    @Override
    public void updateEmployee(GetEmployeeUpdateRequest dto) {

    }
}
