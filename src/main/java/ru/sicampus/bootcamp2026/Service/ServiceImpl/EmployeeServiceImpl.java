package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.createdEmployeeRequest;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Excepations.EmployeeFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Service.EmployeeService;

import java.lang.module.FindException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Employee getEmployee(String name){
        if(name==null||name.isBlank()){
            throw new IllegalArgumentException("");
        }
        return employeeRepository.findByName(name).orElseThrow(()->new EmployeeNotFound(""));
    }
    @Override
    public List<Employee> getEmployees(){
        List<Employee> employees=employeeRepository.findAll();
        return employees;
    }
    @Override
    public void createdEmployee(createdEmployeeRequest dto){
        employeeRepository.findByMail(dto.getMail()).orElseThrow(()->new EmployeeFound(""));
        Employee employee=new Employee();
        employee.setName(dto.getName().toString());
        employee.setLast_name(dto.getLast_name());
        employee.setFather_name(dto.getFather_name());

    }
}
