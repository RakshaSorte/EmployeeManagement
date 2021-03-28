package net.javaguides.springbootbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springbootbackend.exception.ResourceNotFoundException;
import net.javaguides.springbootbackend.model.Employee;
import net.javaguides.springbootbackend.repository.EmployeeRepository;
@CrossOrigin(origins="http://localhost:3001")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
private EmployeeRepository employeeRepository;
	//http://localhost:8085/api/v1
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}
	
	 //http://localhost:8085/api/v1/
	@PostMapping("/employees") 
	public Employee createEmployee(@RequestBody Employee employee) { 
		return employeeRepository.save(employee); 
		}
	 //get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee"));
				
	return  ResponseEntity.ok(employee);
	}
	//update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails) throws ResourceNotFoundException
	{
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee"));
	employee.setFirstName(employeeDetails.getFirstName());
	employee.setLastName(employeeDetails.getLastName());
	employee.setEmailId(employeeDetails.getEmailId());
	Employee updatedEmployee =employeeRepository.save(employee);
	return ResponseEntity.ok(updatedEmployee);
	}
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity< Map<String,Boolean>> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException
	{
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not found"));
		
		employeeRepository.delete(employee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted",Boolean.TRUE);
	return ResponseEntity.ok(response);
	}
}
